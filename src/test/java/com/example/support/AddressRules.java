package com.example.support;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pure logic for random-address selection: no Selenium, no config. Kept separate so it can be reasoned about
 * (and unit tested) without a browser.
 *
 * "Mainland US" here means the 48 contiguous states plus DC. Everything else is rejected:
 * Alaska, Hawaii, the territories, and military mail (APO/FPO/DPO use the state codes AA, AE and AP).
 */
public final class AddressRules {

    private static final Set<String> NON_MAINLAND_STATE_CODES =
            Set.of("AK", "HI", "PR", "VI", "GU", "AS", "MP", "UM", "AA", "AE", "AP");

    /** P.O. Box, PO Box, P O Box, POBOX, Post Office Box, PO Drawer. */
    private static final Pattern PO_BOX = Pattern.compile(
            "\\b(P\\.?\\s*O\\.?\\s*(BOX|DRAWER)|POST\\s+OFFICE\\s+(BOX|DRAWER))\\b",
            Pattern.CASE_INSENSITIVE);

    /** A trailing two-letter state code, optionally followed by a ZIP or ZIP+4, at the end of the suggestion text. */
    private static final Pattern TRAILING_STATE = Pattern.compile(
            "(?:^|[\\s,])([A-Z]{2})(?:\\s+\\d{5}(?:-\\d{4})?)?\\s*$");

    /** Smarty shows "(12 more entries)" for a building with several units; picking it opens a second list. */
    private static final Pattern MORE_ENTRIES = Pattern.compile("\\(\\s*\\d+\\s+more\\s+entr(?:y|ies)\\s*\\)",
            Pattern.CASE_INSENSITIVE);

    private AddressRules() {
    }

    /** A random street number of 3, 4 or 5 digits (each length equally likely), never starting with zero. */
    public static String randomStreetNumber(Random random) {
        int digits = 3 + random.nextInt(3);          // 3, 4 or 5
        int min = (int) Math.pow(10, digits - 1);    // 100, 1000, 10000
        int max = (int) Math.pow(10, digits);        // 1000, 10000, 100000 (exclusive)
        return String.valueOf(min + random.nextInt(max - min));
    }

    public static boolean isPoBox(String suggestion) {
        return PO_BOX.matcher(suggestion).find();
    }

    public static boolean hasMoreEntries(String suggestion) {
        return MORE_ENTRIES.matcher(suggestion).find();
    }

    /**
     * The state code at the end of the suggestion, or null when none can be read.
     * The "(N more entries)" marker is ignored so it can never be mistaken for the state.
     */
    public static String stateOf(String suggestion) {
        String text = MORE_ENTRIES.matcher(suggestion).replaceAll(" ").trim();
        Matcher m = TRAILING_STATE.matcher(text);
        return m.find() ? m.group(1) : null;
    }

    /**
     * True when the suggestion is a normal street address in the mainland US that can be picked with one click.
     * A suggestion whose state cannot be read is rejected: we cannot prove it is on the mainland.
     */
    public static boolean isEligible(String suggestion) {
        if (suggestion == null || suggestion.isBlank()) {
            return false;
        }
        if (isPoBox(suggestion) || hasMoreEntries(suggestion)) {
            return false;
        }
        String state = stateOf(suggestion);
        return state != null && !NON_MAINLAND_STATE_CODES.contains(state);
    }

    /** Index of the first eligible suggestion, in dropdown order, or -1 when there is none. */
    public static int firstEligibleIndex(List<String> suggestions) {
        for (int i = 0; i < suggestions.size(); i++) {
            if (isEligible(suggestions.get(i))) {
                return i;
            }
        }
        return -1;
    }
}

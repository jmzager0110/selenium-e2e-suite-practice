package com.example.support;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Fills a street-address field by typing a random 3-5 digit street number and picking the first suggestion in
 * the Smarty dropdown that is a real mainland-US street address (not a PO Box, not AK/HI/territories/military).
 *
 * Use it wherever a page has an address field:
 *
 *     SelectedAddress picked = new AddressAutocomplete(driver, STREET_INPUT).fillRandom();
 *
 * The driver must already be in the right frame (for the Add Location iframe, call it between switchIn()
 * and switchOut()). The component keeps no state besides the shared random generator, so create one per use.
 *
 * Reproducing a run: the seed is printed once per JVM ("Address seed: ..."). Re-run with -Daddress.seed=<that value>
 * to get the same street numbers in the same order.
 */
public class AddressAutocomplete {

    // TODO: locator for ONE suggestion row in the dropdown. Must match every row, in display order.
    // Smarty's own widget uses something like "ul.smarty-autocomplete-list li"; check yours in dev tools.
    private static final By SUGGESTION_ITEMS = By.cssSelector("TODO-suggestion-row-selector");

    /** New street numbers to try before giving up. Each attempt types a fresh random number. */
    private static final int MAX_ATTEMPTS = 6;

    private static final Random RANDOM = createRandom();

    private final WebDriver driver;
    private final WebDriverWait suggestionWait;
    private final WebDriverWait pickWait;
    private final By streetInput;

    public AddressAutocomplete(WebDriver driver, By streetInput) {
        this.driver = driver;
        this.streetInput = streetInput;
        this.suggestionWait = new WebDriverWait(driver, Duration.ofSeconds(suggestionWaitSeconds()));
        this.pickWait = new WebDriverWait(driver, Config.waitTimeout());
    }

    /** What ended up in the page: the dropdown row that was clicked and the street field's value afterwards. */
    public record SelectedAddress(String suggestion, String streetValue) {
        @Override
        public String toString() {
            return "'" + suggestion + "' (street field now: '" + streetValue + "')";
        }
    }

    public SelectedAddress fillRandom() {
        List<String> attempts = new ArrayList<>();
        List<String> lastSeen = List.of();

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String number = AddressRules.randomStreetNumber(RANDOM);
            type(number);

            List<String> suggestions = waitForSuggestionsFor(number);
            lastSeen = suggestions;
            attempts.add(number + " -> " + suggestions.size() + " suggestion(s)");

            int index = AddressRules.firstEligibleIndex(suggestions);
            if (index >= 0) {
                String chosen = suggestions.get(index);
                click(index);
                String streetValue = waitForStreetFieldToChange(number);
                SelectedAddress result = new SelectedAddress(chosen, streetValue);
                System.out.println("Address selected: " + result);
                return result;
            }
        }

        fail("No eligible address (mainland US, not a PO Box) was offered after " + MAX_ATTEMPTS
                + " attempts. Tried: " + attempts
                + ". Suggestions on the last attempt: " + lastSeen
                + ". If these look fine, check SUGGESTION_ITEMS and the suggestion text format in AddressRules.");
        return null; // unreachable: fail() throws
    }

    // ------------------------------------------------------------------ steps

    private void type(String text) {
        WebElement input = pickWait.until(ExpectedConditions.visibilityOfElementLocated(streetInput));
        input.clear();
        input.sendKeys(text);
    }

    /**
     * Waits until the dropdown shows results for what was just typed. Rows start with the street number, so
     * "first row starts with the number" tells a fresh list from the previous attempt's leftovers.
     * Returns an empty list when nothing usable shows up in time (the caller then tries a new number).
     */
    private List<String> waitForSuggestionsFor(String number) {
        try {
            return suggestionWait.until(d -> {
                List<String> texts = suggestionTexts();
                return !texts.isEmpty() && texts.get(0).startsWith(number) ? texts : null;
            });
        } catch (TimeoutException e) {
            return List.of();
        }
    }

    private List<String> suggestionTexts() {
        List<String> texts = new ArrayList<>();
        try {
            for (WebElement row : driver.findElements(SUGGESTION_ITEMS)) {
                texts.add(row.getText().trim());
            }
        } catch (StaleElementReferenceException e) {
            return List.of(); // the list re-rendered mid-read; the wait will poll again
        }
        return texts;
    }

    /** Clicks the row at the given position, re-finding it so a re-render between read and click is harmless. */
    private void click(int index) {
        for (int tries = 0; tries < 3; tries++) {
            try {
                List<WebElement> rows = driver.findElements(SUGGESTION_ITEMS);
                if (index < rows.size()) {
                    rows.get(index).click();
                    return;
                }
            } catch (StaleElementReferenceException e) {
                // fall through and retry
            }
        }
        fail("The suggestion list changed before row " + index + " could be clicked.");
    }

    /** After a pick, the page replaces the typed number with the full street line. */
    private String waitForStreetFieldToChange(String typedNumber) {
        try {
            return pickWait.until(d -> {
                String value = d.findElement(streetInput).getDomProperty("value");
                value = value == null ? "" : value.trim();
                return !value.isEmpty() && !value.equals(typedNumber) ? value : null;
            });
        } catch (TimeoutException e) {
            fail("Clicked a suggestion, but the street field still holds '" + typedNumber + "'. "
                    + "Check that the click hit the row and that the page fills the field on selection.");
            return null; // unreachable
        }
    }

    // ------------------------------------------------------------------ config

    private static int suggestionWaitSeconds() {
        String value = Config.get("address.suggestion.wait.seconds");
        return value == null || value.isBlank() ? 5 : Integer.parseInt(value.trim());
    }

    private static Random createRandom() {
        String configured = Config.get("address.seed");
        long seed = configured == null || configured.isBlank() ? System.nanoTime() : Long.parseLong(configured.trim());
        System.out.println("Address seed: " + seed);
        return new Random(seed);
    }
}

package com.example.portalUsers.models;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * One set of values for the "Add Location" iframe form.
 * TODO: replace/extend these fields with whatever the real form asks for, and keep fromRow() in step.
 */
public record LocationData(
        String name,                       // TODO: free-text field
        String street,                     // TODO: free-text field
        String city,                       // TODO: free-text field
        String state,                      // TODO: free-text field
        String zip,                        // TODO: free-text field
        String classification,             // TODO: dropdown selection
        String coverageType,               // TODO: radio selection
        List<String> additionalOptions) {  // TODO: checkbox selections

    /** Builds a location from one row of a Gherkin data table (header row = column names). */
    public static LocationData fromRow(Map<String, String> row) {
        return new LocationData(
                required(row, "name"),
                required(row, "street"),
                required(row, "city"),
                required(row, "state"),
                required(row, "zip"),
                required(row, "classification"),
                required(row, "coverage type"),
                splitList(row.get("additional options")));
    }

    private static String required(Map<String, String> row, String column) {
        String value = row.get(column);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Location table is missing a value for column '" + column + "'. Columns present: " + row.keySet());
        }
        return value.trim();
    }

    private static List<String> splitList(String commaSeparated) {
        if (commaSeparated == null || commaSeparated.isBlank()) {
            return List.of();
        }
        return Arrays.stream(commaSeparated.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}

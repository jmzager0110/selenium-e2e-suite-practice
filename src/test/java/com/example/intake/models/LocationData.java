package com.example.intake.models;

/**
 * One set of values for the "Add Location" iframe form.
 * TODO: replace/extend these fields with whatever the real form asks for.
 */
public class LocationData {

    private final String name;      // TODO: free-text field
    private final String type;      // TODO: dropdown selection
    private final String category;  // TODO: radio / second selection

    public LocationData(String name, String type, String category) {
        this.name = name;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}

package com.example.portalUsers.notInUse.model;

import java.util.List;

/**
 * Everything the shared end-to-end flow needs to type or select.
 * TODO: replace the placeholder fields with the real fields of your application.
 */
public record ProposalData(
        PageOne pageOne,
        PageTwo pageTwo,
        List<Location> locations,
        String selectionOption,
        String paymentOption) {

    /** Fields on application page one. */
    public record PageOne(String applicantName, String contactEmail, String phone) {}

    /** Fields on application page two. */
    public record PageTwo(String effectiveDate, String industry, String description) {}

    /** One pass through the "Add Location" iframe: text fields, a dropdown, a radio and checkboxes. */
    public record Location(
            String name,
            String street,
            String city,
            String state,
            String zip,
            String classification,
            String coverageType,
            List<String> additionalOptions) {}
}

package com.example.portalUsers.notInUse.model;

import java.util.List;

/** Builds test data. Two locations with different data/selections, as the flow requires. */
public final class TestDataFactory {

    private TestDataFactory() {}

    /** Standard proposal. paymentOption is null, so the payment step accepts the default. */
    public static ProposalData standardProposal() {
        return build(null);
    }

    /** Same proposal, but the payment step explicitly selects the given option. */
    public static ProposalData proposalWithPaymentOption(String paymentOption) {
        return build(paymentOption);
    }

    private static ProposalData build(String paymentOption) {
        String suffix = String.valueOf(System.currentTimeMillis() % 100_000);

        ProposalData.PageOne pageOne = new ProposalData.PageOne(
                "Acme Test " + suffix,
                "qa+" + suffix + "@example.com",
                "5555550100");

        ProposalData.PageTwo pageTwo = new ProposalData.PageTwo(
                "01/01/2027",
                "Retail",
                "Automated E2E proposal " + suffix);

        ProposalData.Location first = new ProposalData.Location(
                "Location A " + suffix,
                "100 Main St",
                "Springfield",
                "IL",
                "62701",
                "Class A",
                "Standard",
                List.of("Option 1"));

        ProposalData.Location second = new ProposalData.Location(
                "Location B " + suffix,
                "200 Oak Ave",
                "Chicago",
                "IL",
                "60601",
                "Class B",
                "Premium",
                List.of("Option 2", "Option 3"));

        return new ProposalData(pageOne, pageTwo, List.of(first, second), "Option A", paymentOption);
    }
}

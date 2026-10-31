package com.example.portalUsers.notInUse.flows;

import com.example.e2e.model.ProposalData;
import com.example.e2e.pages.ApplicationPageOne;
import com.example.e2e.pages.ApplicationPageTwo;
import com.example.e2e.pages.ConfirmationPage;
import com.example.e2e.pages.LocationFrame;
import com.example.e2e.pages.PaymentOptionsPage;
import com.example.e2e.pages.SelectionPage;
import com.example.e2e.pages.SubmitPage;
import org.testng.Reporter;

import java.util.List;

/** The part of the journey that is identical for client and agent, from application page one to the proposal id. */
public class ProposalFlow {

    public String complete(ApplicationPageOne pageOne, ProposalData data) {
        // Pages one and two
        pageOne.fillAll(data.pageOne());
        pageOne.assertFilled(data.pageOne());

        ApplicationPageTwo pageTwo = pageOne.clickNext();
        pageTwo.fillAll(data.pageTwo());
        pageTwo.assertFilled(data.pageTwo());

        // Add Location (iframe), once per location. The test data supplies two, with different data and selections.
        List<ProposalData.Location> locations = data.locations();
        for (int i = 0; i < locations.size(); i++) {
            ProposalData.Location location = locations.get(i);

            LocationFrame frame = pageTwo.clickAddLocation();
            frame.fill(location);
            frame.assertFilled(location);
            frame.save();

            pageTwo.assertLocationListed(i + 1, location.name());
        }

        // Continue, make a selection, continue
        SelectionPage selection = pageTwo.clickContinue();
        selection.select(data.selectionOption());

        // Payment options: explicit selection, or accept the default when none is given
        PaymentOptionsPage payment = selection.clickContinue();
        String paymentUsed = payment.chooseOrAcceptDefault(data.paymentOption());
        Reporter.log("Payment option used: " + paymentUsed, true);

        // Submit, then read the proposal id from the final page
        SubmitPage submit = payment.clickContinue();
        ConfirmationPage confirmation = submit.clickSubmit();
        return confirmation.getProposalId();
    }
}

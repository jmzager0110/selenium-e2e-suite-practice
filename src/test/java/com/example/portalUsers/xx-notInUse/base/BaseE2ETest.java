package com.example.portalUsers.notInUse.base;

import com.example.e2e.config.TestConfig;
import com.example.e2e.flows.EntryFlow;
import com.example.e2e.flows.ProposalFlow;
import com.example.e2e.model.ProposalData;
import com.example.e2e.model.Role;
import com.example.e2e.pages.ApplicationPageOne;
import com.example.e2e.pages.LoginPage;
import org.testng.Reporter;

/** The complete journey: navigate, log in, role-specific entry, shared proposal flow, proposal id back. */
public abstract class BaseE2ETest extends BaseTest {

    /** Returns the proposal id. The browser is closed by BaseTest.tearDown once the test ends. */
    protected String runEndToEnd(Role role, EntryFlow entryFlow, ProposalData data) {
        // Navigate and log in (shared by both variants)
        LoginPage login = new LoginPage(driver).open();
        login.assertLoaded();
        login.login(TestConfig.credentials(role));

        // Role-specific steps, ending on application page one
        ApplicationPageOne pageOne = entryFlow.startApplication();

        // Shared remainder of the flow, ending on the confirmation page
        String proposalId = new ProposalFlow().complete(pageOne, data);

        Reporter.log(role + " proposal id: " + proposalId, true);
        Reporter.getCurrentTestResult().setAttribute("proposalId", proposalId);
        return proposalId;
    }
}

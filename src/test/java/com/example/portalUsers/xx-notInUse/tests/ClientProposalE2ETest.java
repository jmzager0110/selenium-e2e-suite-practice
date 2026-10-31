package com.example.portalUsers.notInUse.tests;

import com.example.e2e.base.BaseE2ETest;
import com.example.e2e.flows.ClientEntryFlow;
import com.example.e2e.model.Role;
import com.example.e2e.model.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClientProposalE2ETest extends BaseE2ETest {

    @Test(description = "Client: Accounts > first result > Add Locations > full proposal > proposal id")
    public void clientCanSubmitProposal() {
        String proposalId = runEndToEnd(
                Role.CLIENT,
                new ClientEntryFlow(driver),
                TestDataFactory.standardProposal());

        Assert.assertFalse(proposalId.isBlank(), "Client flow should return a proposal id");
    }
}

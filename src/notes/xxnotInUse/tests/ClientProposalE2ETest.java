package com.example.portalUsers.xxnotInUse.tests;

import com.example.portalUsers.xxnotInUse.base.BaseE2ETest;
import com.example.portalUsers.xxnotInUse.flows.ClientEntryFlow;
import com.example.portalUsers.xxnotInUse.model.Role;
import com.example.portalUsers.xxnotInUse.model.TestDataFactory;
//import org.testng.Assert;
//import org.testng.annotations.Test;

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

package com.example.portalUsers.xxnotInUse.tests;

import com.example.portalUsers.xxnotInUse.base.BaseE2ETest;
import com.example.portalUsers.xxnotInUse.flows.AgentEntryFlow;
import com.example.portalUsers.xxnotInUse.model.Role;
import com.example.portalUsers.xxnotInUse.model.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AgentProposalportalUsers.xxnotInUseTest extends BaseportalUsers.BaseE2ETest {

    @Test(description = "Agent: New Application > full proposal > proposal id (default payment option)")
    public void agentCanSubmitProposal() {
        String proposalId = runEndToEnd(
                Role.AGENT,
                new AgentEntryFlow(driver),
                TestDataFactory.standardProposal());

        Assert.assertFalse(proposalId.isBlank(), "Agent flow should return a proposal id");
    }

    // Uncomment to cover an explicit payment selection (replace the value with a real option):
    // @Test(description = "Agent: explicit payment option")
    // public void agentCanSubmitProposalWithExplicitPayment() {
    //     String proposalId = runEndToEnd(
    //             Role.AGENT,
    //             new AgentEntryFlow(driver),
    //             TestDataFactory.proposalWithPaymentOption("monthly"));
    //     Assert.assertFalse(proposalId.isBlank());
    // }
}

package com.example.portalUsers.notInUse.flows;

import com.example.e2e.pages.ApplicationPageOne;

/**
 * The role-specific steps between logging in and landing on application page one.
 * Everything after that is identical for both roles (see ProposalFlow).
 */
public interface EntryFlow {

    /** Runs from the post-login landing page to application page one, validating each page on the way. */
    ApplicationPageOne startApplication();
}

package com.example.proposal.support;

import com.example.proposal.models.Role;
import org.openqa.selenium.WebDriver;

/**
 * Per-scenario state. PicoContainer creates one instance per scenario and injects the
 * same instance into every step class and hook class that asks for it in its constructor.
 * This is how Hooks (which creates the driver) and Steps (which uses it) share one browser.
 */
public class TestContext {

    private WebDriver driver;
    private Role role;
    private String proposalId;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /** Who is logged in. Decides how "start a new application" is carried out. */
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /** The result of the run: read from the final page. */
    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }
}

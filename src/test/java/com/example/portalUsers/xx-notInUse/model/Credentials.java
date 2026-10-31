package com.example.portalUsers.notInUse.model;

public record Credentials(String username, String password) {

    /** Keeps the password out of logs and failure messages. */
    @Override
    public String toString() {
        return "Credentials[username=" + username + ", password=****]";
    }
}

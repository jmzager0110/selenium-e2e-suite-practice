package com.example.proposal.models;

/** The two user variants. The lower-case name is the prefix of the config keys, e.g. client.username. */
public enum Role {
    CLIENT,
    AGENT;

    public static Role from(String text) {
        return switch (text.trim().toLowerCase()) {
            case "client" -> CLIENT;
            case "agent" -> AGENT;
            default -> throw new IllegalArgumentException("Unknown role '" + text + "'. Use client or agent.");
        };
    }

    public String configPrefix() {
        return name().toLowerCase();
    }
}

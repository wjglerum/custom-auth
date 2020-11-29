package nl.wjglerum.security;

public enum APIKeyUser {
    ADMIN("Admin"),
    USER("User");

    private final String name;

    APIKeyUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package model;

public record User(String username, String password, Role role, String fullName, String id) {
    String toFileString() {
        return String.join(",",
                username,
                password,
                String.valueOf(role),
                fullName,
                id
        );
    }
}

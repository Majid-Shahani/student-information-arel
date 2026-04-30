package model;

public record User(String username, String password, Role role, String fullName, String id) {
    public String toFileString() {
        return String.join(",",
                username,
                password,
                String.valueOf(role),
                fullName,
                id
        );
    }
    public static User fromLine(String line) {
        String[] p = line.split(",");
        return new User(
                p[0],
                p[1],
                Role.valueOf(p[2]),
                p[3],
                p[4]
        );
    }
}

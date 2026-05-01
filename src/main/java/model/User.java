package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record User(String username, String password, Role role, String fullName, String id) {
    @Contract(" -> new")
    public @NotNull String toFileString() {
        return String.join(",",
                username,
                password,
                String.valueOf(role),
                fullName,
                id
        );
    }
    @Contract("_ -> new")
    public static @NotNull User fromLine(@NotNull String line) {
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

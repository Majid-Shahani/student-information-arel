package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record StudentProfile(String studentID, String fullName, String department, int year, String username) {
    @Contract(" -> new")
    public @NotNull String toFileString() {
        return String.join(",",
                studentID,
                fullName,
                department,
                String.valueOf(year),
                username
        );
    }
    @Contract("_ -> new")
    public static @NotNull StudentProfile fromLine(@NotNull String line) {
        String[] p = line.split(",");
        return new StudentProfile(
                p[0],
                p[1],
                p[2],
                Integer.parseInt(p[3]),
                p[4]
        );
    }
}

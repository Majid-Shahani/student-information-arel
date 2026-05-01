package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Enrollment(String studentUsername, String courseCode) {
    @Contract(" -> new")
    public @NotNull String toFileString() {
        return String.join(",",
                studentUsername,
                courseCode
        );
    }

    @Contract("_ -> new")
    public static @NotNull Enrollment fromLine(String line) {
        String[] p = line.split(",");
        return new Enrollment(
                p[0],
                p[1]
        );
    }
}

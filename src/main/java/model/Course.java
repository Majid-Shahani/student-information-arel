package model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Course(String courseCode, String courseName, int credit, int quota, String instructorUsername) {
    @Contract(" -> new")
    public @NotNull String toFileString() {
        return String.join(",",
                courseCode,
                courseName,
                String.valueOf(credit),
                String.valueOf(quota),
                instructorUsername
        );
    }

    @Contract("_ -> new")
    public static @NotNull Course fromLine(@NotNull String line) {
        String[] p = line.split(",");
        return new Course(
                p[0],
                p[1],
                Integer.parseInt(p[2]),
                Integer.parseInt(p[3]),
                p[4]
        );
    }
}

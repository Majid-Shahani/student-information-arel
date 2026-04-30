package model;

public record Enrollment(String studentUsername, String courseCode) {
    public String toFileString() {
        return String.join(",",
                studentUsername,
                courseCode
        );
    }

    public static Enrollment fromLine(String line) {
        String[] p = line.split(",");
        return new Enrollment(
                p[0],
                p[1]
        );
    }
}

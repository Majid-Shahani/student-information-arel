package model;

public record Enrollment(String studentUsername, String courseCode) {
    public String toFileString() {
        return String.join(",",
                studentUsername,
                courseCode
        );
    }
}

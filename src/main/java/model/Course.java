package model;

public record Course(String courseCode, String courseName, int credit, int quota, String instructorUsername) {
    public String toFileString() {
        return String.join(",",
                courseCode,
                courseName,
                String.valueOf(credit),
                String.valueOf(quota),
                instructorUsername
        );
    }
}

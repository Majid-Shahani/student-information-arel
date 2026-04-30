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

    public static Course fromLine(String line) {
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

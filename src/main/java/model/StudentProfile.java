package model;

public record StudentProfile(String studentID, String fullName, String department, int year, String username) {
    public String toFileString() {
        return String.join(",",
                studentID,
                fullName,
                department,
                String.valueOf(year),
                username
        );
    }
    public static StudentProfile fromLine(String line) {
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

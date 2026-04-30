package model;

public record GradeRecord(String studentUsername, String courseCode, double midterm, double finalExam) {
    public double calculateAverage() { return 0.4 * midterm + 0.6 * finalExam; }
    public String getLetterGrade() {
        double avg = calculateAverage();

        if (avg >= 90) return "AA";
        if (avg >= 85) return "BA";
        if (avg >= 80) return "BB";
        if (avg >= 75) return "CB";
        if (avg >= 70) return "CC";
        if (avg >= 65) return "DC";
        if (avg >= 60) return "DD";
        if (avg >= 50) return "FD";
        return "FF";
    }
    public String toFileString() {
        return String.join("",
                studentUsername,
                courseCode,
                String.valueOf(midterm),
                String.valueOf(finalExam)
        );
    }
    public static GradeRecord fromLine(String line) {
        String[] p = line.split(",");
        return new GradeRecord(
                p[0],
                p[1],
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3])
        );
    }
}

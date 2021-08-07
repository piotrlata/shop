package com.example.shop.test;

import java.util.List;

public class Logic {
    public static Student findSecondTheBestStudent(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return null;
        }
        Student bestStudent = students.get(0);
        Student secondBestStudent = null;
        for (Student student : students) {
            if (student.getAvgMarks() > bestStudent.getAvgMarks()) {
                secondBestStudent = bestStudent;
                bestStudent = student;
            } else if (secondBestStudent == null) {
                if (student.getAvgMarks() == bestStudent.getAvgMarks()) {
                    if (student.getDateOfBirth().isAfter(bestStudent.getDateOfBirth())) {
                        bestStudent = student;
                    } else {
                        continue;
                    }
                } else if (student.getAvgMarks() < bestStudent.getAvgMarks()) {
                    secondBestStudent = student;
                } else {
                    secondBestStudent = student;
                }
            } else if (student.getAvgMarks() == secondBestStudent.getAvgMarks() && secondBestStudent.getDateOfBirth().isBefore(student.getDateOfBirth())) {
                secondBestStudent = student;
            } else if (secondBestStudent.getAvgMarks() < student.getAvgMarks() && student.getAvgMarks() < bestStudent.getAvgMarks()) {
                secondBestStudent = student;
            } else if (bestStudent.getAvgMarks() == student.getAvgMarks() && student.getDateOfBirth().isAfter(bestStudent.getDateOfBirth())) {
                bestStudent = student;
            }
        }
        return secondBestStudent;
    }
}

package com.example.shop.test;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode
public class Student {
    String name;
    String surname;
    LocalDate dateOfBirth;
    double avgMarks;

    public Student(String name, String surname, LocalDate dateOfBirth, double avgMarks) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.avgMarks = avgMarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getAvgMarks() {
        return avgMarks;
    }

    public void setAvgMarks(double avgMarks) {
        this.avgMarks = avgMarks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", avgMarks=" + avgMarks +
                '}';
    }
}

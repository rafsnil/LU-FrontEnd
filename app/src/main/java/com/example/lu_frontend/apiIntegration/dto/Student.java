package com.example.lu_frontend.apiIntegration.dto;

import java.util.List;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Course> courseList;

    public Student(String id, List<Course> courseList, String username, String email, String lastName, String firstName) {
        this.id = id;
        this.courseList = courseList;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}

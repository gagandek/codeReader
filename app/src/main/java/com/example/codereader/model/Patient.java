package com.example.codereader.model;

public class Patient {
//    private int uniqueID;
    private String uniqueID, fullName, firstName, lastName, dob, gender;

    public Patient(String uniqueID, String fullName, String firstName, String lastName, String dob, String gender) {
        this.uniqueID = uniqueID;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "[" + uniqueID
                + ", "
                +fullName
                +", "
                +gender
                +", "
                +dob
                +"]";
    }
}

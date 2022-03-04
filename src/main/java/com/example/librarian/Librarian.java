package com.example.librarian;

public class Librarian {
    private int librarianID;
    private String firstName;
    private String lastName;
    private String libName;
    private String date;
    private int phoneNumber;
    private String address;
    private String email;

    public Librarian(int librarianID, String fName, String lName, String libName, String date, int phoneNumber, String address, String email) {
        this.librarianID = librarianID;
        this.firstName = fName;
        this.lastName = lName;
        this.libName = libName;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public int getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(int librarianID) {
        this.librarianID = librarianID;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}

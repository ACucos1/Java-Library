package com.example.librarian;

public class Rentals {
    private int isbn;
    private int studentID;


    public Rentals(int isbn, int studentID) {
        this.isbn = isbn;
        this.studentID = studentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}

package com.example.librarian;

public class Books {
    private int isbn;
    private String type;
    private String title;
    private String author;
    private int year;
    private int pages;
    private int copies;

    public Books(int isbn, String type, String title, String author, int year, int pages, int copies){
        this.isbn = isbn;
        this.type = type;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.copies = copies;
    }


    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCopies() {
        return copies;
    }
}

package Entities;

public class Book {
    public String bname;
    public String author;
    public String publisher;
    public String category;
    public int year;

    public Book(String bname, String author, String publisher, String category, int year) {
        this.bname = bname;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.year = year;
    }
}

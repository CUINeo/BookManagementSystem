package Entities;

public class Book {
    public String bname;
    public String author;
    public String publisher;
    public String category;
    public int year;
    public boolean available;

    public Book(String bname, String author, String publisher, String category, int year, boolean available) {
        this.bname = bname;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.year = year;
        this.available = available;
    }
}

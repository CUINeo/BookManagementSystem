package Entities;

public class Transaction {
    public String uname;
    public Book book;
    public String dueDate;

    public Transaction(String uname, Book book, String dueDate) {
        this.uname = uname;
        this.book = book;
        this.dueDate = dueDate;
    }
}

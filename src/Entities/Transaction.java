package Entities;

public class Transaction {
    public String uname;
    public String bname;
    public Book book;
    public String borrowDate;
    public String dueDate;

    public Transaction(String uname, String bname, Book book, String borrowDate, String dueDate) {
        this.uname = uname;
        this.bname = bname;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }
}

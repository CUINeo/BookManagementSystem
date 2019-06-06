package Entities;

import java.util.ArrayList;

public class User {
    public String uname;
    public String contact;
    public String address;
    public ArrayList<Transaction> transactions;

    public User(String uname, String contact, String address, ArrayList<Transaction> transactions) {
        this.uname = uname;
        this.contact = contact;
        this.address = address;
        this.transactions = transactions;
    }
}

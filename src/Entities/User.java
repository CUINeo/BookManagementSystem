package Entities;

import java.util.ArrayList;

public class User {
    public String uname;
    public String contact;
    public String address;
    public boolean license;
    public ArrayList<Transaction> transactions;

    public User(String uname, String contact, String address, boolean license, ArrayList<Transaction> transactions) {
        this.uname = uname;
        this.contact = contact;
        this.address = address;
        this.license = license;
        this.transactions = transactions;
    }
}

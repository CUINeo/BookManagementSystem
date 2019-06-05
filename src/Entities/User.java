package Entities;

import java.util.ArrayList;

public class User {
    public String uname;
    public String passwd;
    public String contact;
    public boolean license;
    public ArrayList<Transaction> transactions;

    public User(String uname, String passwd, String contact, boolean license, ArrayList<Transaction> transactions) {
        this.uname = uname;
        this.passwd = passwd;
        this.contact = contact;
        this.license = license;
        this.transactions = transactions;
    }
}

package dbOperation;

import Entities.Book;
import Entities.User;
import Entities.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class dbHandler {
    private static Connection connection;

    // Init the database handler, get the connection
    public dbHandler() {
        String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String connDB = "jdbc:sqlserver://127.0.0.1:1433;DataBaseName=BookManagementSystem";

        try {
            Class.forName(JDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            String user = "sa";
            String password = "123456";

            connection = DriverManager.getConnection(connDB, user, password);
            System.out.println("Connect to the database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginQuery(String name, String pwd) {
        try (Statement stmt = connection.createStatement()) {
            String SQL = "select * from users where uname = '" + name + "' and passwd = '" + pwd + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            return rs.isBeforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerQuery(String name, String pwd, String address, String contact) {
        try (PreparedStatement stmt = connection.prepareStatement("insert into users " +
                "values('" + name + "', '" + pwd + "', 1, '" + address + "', '" + contact + "')")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<Book> getBooks() {
        try (Statement stmt = connection.createStatement()) {
            String SQL = "select * from books";
            ResultSet rs = stmt.executeQuery(SQL);

            ArrayList<Book> books = new ArrayList<>();

            while (rs.next()) {
                String name = rs.getString(1);
                String publisher = rs.getString(2);
                String author = rs. getString(3);
                int year = rs.getInt(4);
                int available = rs.getInt(5);
                String category = rs.getString(6);

                boolean a;
                if (available == 1)
                    a = true;
                else
                    a = false;

                Book book = new Book(name, author, publisher, category, year, a);
                books.add(book);
            }

            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean insertBook(Book book) {
        try (PreparedStatement stmt = connection.prepareStatement("insert into books " +
                "values('" + book.bname + "', '" + book.publisher + "', '"
                + book.author + "', " + book.year + ", '" + book.category + "')")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteBook(String bname) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "delete from books where name = '" + bname + "'")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static User getUserByName(String uname) {
        try (Statement stmt = connection.createStatement()) {
            String userSQL = "select address, contact from users where uname = '" + uname +"'";
            ResultSet rs = stmt.executeQuery(userSQL);

            String address = "";
            String contact = "";

            while (rs.next()) {
                address = rs.getString(1);
                contact = rs.getString(2);
            }

            String transactionSQL = "select bname, author, year, publisher, category, dueDate, available " +
                    "from books, users, transactions " +
                    "where books.name = transactions.bname and transactions.uname = users.uname " +
                    "and users.uname = '" + uname + "'";
            rs = stmt.executeQuery(transactionSQL);

            ArrayList<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                String bname = rs.getString(1);
                String author = rs.getString(2);
                int year = rs.getInt(3);
                String publisher = rs.getString(4);
                String category = rs.getString(5);
                String dueDate = rs.getString(6);
                int available = rs.getInt(7);

                boolean a;
                if (available == 1)
                    a = true;
                else
                    a = false;

                transactions.add(new Transaction(uname, new Book(bname, author, publisher, category, year, a), dueDate));
            }

            return new User(uname, contact, address, transactions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean insertTransaction(String uname, String bname) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into transactions values ('" + uname + "', '" + bname + "', '2020-01-01')")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteTransaction(String uname, String bname) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "delete from transactions where uname = '" + uname + "' and bname = '" + bname + "'")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setAvailable(String bname) {
        try (Statement stmt = connection.createStatement()) {
            String SQL = "update books set available = 1 where name = '" + bname + "'";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNotAvailable(String bname) {
        try (Statement stmt = connection.createStatement()) {
            String SQL = "update books set available = 0 where name = '" + bname + "'";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> searchBook(String name, String author, String publisher, String category, int year) {
        if (name.length() == 0 && author.length() == 0 && publisher.length() == 0 && category.length() == 0 && year < 0)
            return new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            StringBuilder SQL = new StringBuilder("select * from books where ");

            if (name.length() > 0)
                SQL.append("name = '").append(name).append("'");
            if (author.length() > 0)
                SQL.append("author = '").append(author).append("'");
            if (publisher.length() > 0)
                SQL.append("publisher = '").append(publisher).append("'");
            if (category.length() > 0)
                SQL.append("category = '").append(category).append("'");
            if (year > 0)
                SQL.append("year = ").append(year);

            ResultSet rs = stmt.executeQuery(SQL.toString());
            ArrayList<Book> books = new ArrayList<>();

            while (rs.next()) {
                String _name = rs.getString(1);
                String _publisher = rs.getString(2);
                String _author = rs.getString(3);
                int _year = rs.getInt(4);
                int _available = rs.getInt(5);
                String _category = rs.getString(6);

                Book book;
                if (_available == 1)
                    book = new Book(_name, _author, _publisher, _category, _year, true);
                else
                    book = new Book(_name, _author, _publisher, _category, _year, false);

                books.add(book);
            }

            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

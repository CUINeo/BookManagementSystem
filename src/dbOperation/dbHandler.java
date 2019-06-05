package dbOperation;

import Entities.Book;

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
            System.exit(0);
        }
    }

    public boolean loginQuery(String name, String pwd) {
        try (Statement stmt = connection.createStatement()) {
            String SQL = "select * from users where uname = '" + name + "' and passwd = '" + pwd + "'";
            ResultSet rs = stmt.executeQuery(SQL);

            return rs.isBeforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return false;
    }

    public boolean registerQuery(String name, String pwd, String address, String contact) {
        try (PreparedStatement stmt = connection.prepareStatement("insert users values('" + name + "', '" + pwd + "', 1, '" + address + "', '" + contact + "')")) {
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
                String category = rs.getString(5);

                Book book = new Book(name, author, publisher, category, year);
                books.add(book);
            }

            return books;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }

    public static boolean insertBook(Book book) {
        try (PreparedStatement stmt = connection.prepareStatement("")) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteBook(String bname) {
        try (PreparedStatement stmt = connection.prepareStatement("delete from books where bname = " + bname)) {
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }
}

package Viewer;

import Entities.Book;
import dbOperation.dbHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class adminPanel extends JPanel {
    private JScrollPane bookInfoPanel;
    private JPanel newPanel;

    private JTextArea nameArea;
    private JTextArea authorArea;
    private JTextArea publisherArea;
    private JTextArea yearArea;
    private JTextArea categoryArea;

    adminPanel() {
        bookInfoPanel = new JScrollPane();

        setLayout(new BorderLayout());
        add(bookInfoPanel, BorderLayout.CENTER);

        getBookInfo();
        setNewPanel();

        add(newPanel, BorderLayout.SOUTH);

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // ------------------------------ Set new button ---------------------------------------
    private void setNewPanel() {
        // New panel
        newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(2, 1));

        // Book info of new book
        JLabel nameLabel = new JLabel("书名：");
        JLabel authorLabel = new JLabel("作者：");
        JLabel publisherLabel = new JLabel("出版商：");
        JLabel yearLabel = new JLabel("出版年份：");
        JLabel categoryLabel = new JLabel("类别：");

        nameArea = new JTextArea();
        authorArea = new JTextArea();
        publisherArea = new JTextArea();
        yearArea = new JTextArea();
        categoryArea = new JTextArea();

        JPanel newBookInfoPanel = new JPanel();
        newBookInfoPanel.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(nameLabel);
        panel1.add(nameArea);
        panel1.add(authorLabel);
        panel1.add(authorArea);
        panel1.setBorder(new EmptyBorder(0, 100, 0, 100));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(publisherLabel);
        panel2.add(publisherArea);
        panel2.add(yearLabel);
        panel2.add(yearArea);
        panel2.add(categoryLabel);
        panel2.add(categoryArea);
        panel2.setBorder(new EmptyBorder(0, 20, 0, 20));

        // New button
        JButton newButton = new JButton("新建");
        newButton.setBackground(new Color(0, 191, 255));
        newPanel.add(newButton);

        newPanel.setBorder(new EmptyBorder(5, 400, 5, 400));

        // Insert new book into database
        newButton.addActionListener(e -> insertBook());
    }

    private void insertBook() {
        String name = nameArea.getText();
        String author = authorArea.getText();
        String publisher = publisherArea.getText();
        String category = categoryArea.getText();

        int year;

        try {
            year = Integer.valueOf(yearArea.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "出版年份必须是数字！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.length() == 0 || author.length() == 0 || publisher.length() == 0 || category.length() == 0) {
            JOptionPane.showMessageDialog(null, "请将必要信息补充完整！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dbHandler.insertBook(new Book(name, author, publisher, category, year))) {
            JOptionPane.showMessageDialog(null, "书名重复，无法插入！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        getBookInfo();
    }

    // ------------------------------ Get book information ---------------------------------
    private void getBookInfo() {
        bookInfoPanel.removeAll();

        ArrayList<Book> books = dbHandler.getBooks();

        for (Book book : books) {
            JPanel panel = getOneBookInfoPanel(book);
            bookInfoPanel.add(panel);
        }
    }

    private JPanel getOneBookInfoPanel(Book book) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel(book.bname);
        JLabel authorLabel = new JLabel(book.author);
        JLabel publisherLabel = new JLabel(book.publisher);
        JLabel categoryLabel = new JLabel(book.category);
        JLabel yearLabel = new JLabel(Integer.valueOf(book.year).toString());

        panel.add(nameLabel);
        panel.add(authorLabel);
        panel.add(publisherLabel);
        panel.add(yearLabel);
        panel.add(categoryLabel);

        JButton deleteButton = getDeleteButton(book);
        panel.add(deleteButton);

        return panel;
    }

    // ------------------------------ Set delete button ------------------------------------
    private JButton getDeleteButton(Book book) {
        JButton deleteButton =  new JButton("删除");
        deleteButton.setBackground(new Color(0, 191, 255));

        // Delete book records in the database
        deleteButton.addActionListener(e -> deleteBook(book));

        return deleteButton;
    }

    private void deleteBook(Book book) {
        if (!dbHandler.deleteBook(book.bname)) {
            JOptionPane.showMessageDialog(null, "选中的图书无法删除！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        getBookInfo();
    }
}

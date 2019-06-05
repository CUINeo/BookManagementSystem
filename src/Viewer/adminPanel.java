package Viewer;

import Entities.Book;
import dbOperation.dbHandler;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.util.ArrayList;

public class adminPanel extends JPanel {
    private JScrollPane scrollPane;

    private JPanel bookInfoPanel;
    private JPanel newPanel;

    private JTextArea nameArea;
    private JTextArea authorArea;
    private JTextArea publisherArea;
    private JTextArea yearArea;
    private JTextArea categoryArea;

    adminPanel() {
        bookInfoPanel = new JPanel();
        bookInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bookInfoPanel.setPreferredSize(new Dimension(980, 100000));

        scrollPane = new JScrollPane(bookInfoPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());

        // Show book information
        getBookInfo();
        add(scrollPane, BorderLayout.CENTER);

        // Show how to new a book
        setNewPanel();
        add(newPanel, BorderLayout.SOUTH);

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // ------------------------------ Set new button ---------------------------------------
    private void setNewPanel() {
        // New panel
        newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(3, 1));

        // Book info of new book
        JLabel nameLabel = new JLabel("书名：", SwingConstants.RIGHT);
        JLabel authorLabel = new JLabel("作者：", SwingConstants.RIGHT);
        JLabel publisherLabel = new JLabel("出版商：", SwingConstants.RIGHT);
        JLabel yearLabel = new JLabel("出版年份：", SwingConstants.RIGHT);
        JLabel categoryLabel = new JLabel("类别：", SwingConstants.RIGHT);

        nameLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        authorLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        publisherLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        yearLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        categoryLabel.setFont(new Font("黑体", Font.PLAIN, 15));

        nameArea = new JTextArea();
        authorArea = new JTextArea();
        publisherArea = new JTextArea();
        yearArea = new JTextArea();
        categoryArea = new JTextArea();

        nameArea.setFont(new Font("黑体", Font.PLAIN, 15));
        authorArea.setFont(new Font("黑体", Font.PLAIN, 15));
        publisherArea.setFont(new Font("黑体", Font.PLAIN, 15));
        yearArea.setFont(new Font("黑体", Font.PLAIN, 15));
        categoryArea.setFont(new Font("黑体", Font.PLAIN, 15));

        nameArea.setBorder(new EtchedBorder());
        authorArea.setBorder(new EtchedBorder());
        publisherArea.setBorder(new EtchedBorder());
        yearArea.setBorder(new EtchedBorder());
        categoryArea.setBorder(new EtchedBorder());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 4));
        panel1.add(nameLabel);
        panel1.add(nameArea);
        panel1.add(authorLabel);
        panel1.add(authorArea);
        panel1.setBorder(new EmptyBorder(13, 270, 13, 270));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 6));
        panel2.add(publisherLabel);
        panel2.add(publisherArea);
        panel2.add(yearLabel);
        panel2.add(yearArea);
        panel2.add(categoryLabel);
        panel2.add(categoryArea);
        panel2.setBorder(new EmptyBorder(13, 160, 13, 160));

        newPanel.add(panel1);
        newPanel.add(panel2);

        // New button panel
        JButton newButton = new JButton("新建");
        newButton.setFont(new Font("黑体", Font.PLAIN, 15));
        newButton.setBackground(new Color(0, 191, 255));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(newButton, BorderLayout.CENTER);
        buttonPanel.setBorder(new EmptyBorder(10, 420, 10, 420));

        newPanel.add(buttonPanel);
        newPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

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

        JOptionPane.showMessageDialog(null, "插入成功！",
                "消息", JOptionPane.INFORMATION_MESSAGE);
        getBookInfo();
    }

    // ------------------------------ Get book information ---------------------------------
    private void getBookInfo() {
        bookInfoPanel.removeAll();

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 6));

        JLabel label1 = new JLabel("书名", SwingConstants.CENTER);
        JLabel label2 = new JLabel("作者", SwingConstants.CENTER);
        JLabel label3 = new JLabel("出版商", SwingConstants.CENTER);
        JLabel label4 = new JLabel("出版年份", SwingConstants.CENTER);
        JLabel label5 = new JLabel("类别", SwingConstants.CENTER);
        JLabel label6 = new JLabel("操作", SwingConstants.CENTER);

        label1.setFont(new Font("黑体", Font.PLAIN, 15));
        label2.setFont(new Font("黑体", Font.PLAIN, 15));
        label3.setFont(new Font("黑体", Font.PLAIN, 15));
        label4.setFont(new Font("黑体", Font.PLAIN, 15));
        label5.setFont(new Font("黑体", Font.PLAIN, 15));
        label6.setFont(new Font("黑体", Font.PLAIN, 15));

        headerPanel.add(label1);
        headerPanel.add(label2);
        headerPanel.add(label3);
        headerPanel.add(label4);
        headerPanel.add(label5);
        headerPanel.add(label6);

        headerPanel.setBorder(new EtchedBorder());
        headerPanel.setPreferredSize(new Dimension(940, 40));

        bookInfoPanel.add(headerPanel);

        ArrayList<Book> books = dbHandler.getBooks();

        for (Book book : books) {
            JPanel panel = getOneBookInfoPanel(book);
            bookInfoPanel.add(panel);
        }

        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMinimum());

        bookInfoPanel.validate();
        repaint();
    }

    private JPanel getOneBookInfoPanel(Book book) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JLabel nameLabel = new JLabel(book.bname, SwingConstants.CENTER);
        JLabel authorLabel = new JLabel(book.author, SwingConstants.CENTER);
        JLabel publisherLabel = new JLabel(book.publisher, SwingConstants.CENTER);
        JLabel categoryLabel = new JLabel(book.category, SwingConstants.CENTER);
        JLabel yearLabel = new JLabel(Integer.valueOf(book.year).toString(), SwingConstants.CENTER);

        nameLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        authorLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        publisherLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        categoryLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        yearLabel.setFont(new Font("黑体", Font.PLAIN, 15));

        panel.add(nameLabel);
        panel.add(authorLabel);
        panel.add(publisherLabel);
        panel.add(yearLabel);
        panel.add(categoryLabel);

        JButton deleteButton = getDeleteButton(book);
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setLayout(new BorderLayout());
        deleteButtonPanel.setBorder(new EmptyBorder(5, 40, 5, 40));
        deleteButtonPanel.add(deleteButton, BorderLayout.CENTER);

        panel.add(deleteButtonPanel);

        panel.setBorder(new EtchedBorder());
        panel.setPreferredSize(new Dimension(940, 40));

        return panel;
    }

    // ------------------------------ Set delete button ------------------------------------
    private JButton getDeleteButton(Book book) {
        JButton deleteButton =  new JButton("删除");
        deleteButton.setFont(new Font("黑体", Font.PLAIN, 15));
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

        JOptionPane.showMessageDialog(null, "删除成功！",
                "消息", JOptionPane.INFORMATION_MESSAGE);
        getBookInfo();
    }
}

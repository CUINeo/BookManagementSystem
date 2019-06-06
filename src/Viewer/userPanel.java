package Viewer;

import Entities.Book;
import Entities.User;
import Entities.Transaction;

import dbOperation.dbHandler;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class userPanel extends JPanel {
    public JButton logoutButton;

    private JScrollPane scrollPane;

    private JPanel transactionInfoPanel;
    private JPanel searchPanel;
    private JPanel logoutPanel;

    private JTextArea nameArea;
    private JTextArea authorArea;
    private JTextArea publisherArea;
    private JTextArea yearArea;
    private JTextArea categoryArea;

    private User user;

    userPanel(String uname) {
        user = dbHandler.getUserByName(uname);

        transactionInfoPanel = new JPanel();
        transactionInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        transactionInfoPanel.setPreferredSize(new Dimension(980, 100000));

        scrollPane = new JScrollPane(transactionInfoPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());

        // Show log out button
        initLogout();
        add(logoutPanel, BorderLayout.NORTH);

        // Show transaction information
        getTransactionInfo();
        add(scrollPane, BorderLayout.CENTER);

        // Show how to search for a book
        setSearchPanel();
        add(searchPanel, BorderLayout.SOUTH);

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // ---------------------------- Init the logout panel ----------------------------------
    private void initLogout() {
        logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Init labels
        JLabel jLabel = new JLabel(user.uname);
        jLabel.setFont(new Font("黑体", Font.PLAIN, 15));

        // Init the button
        logoutButton = new JButton("退出登录");
        logoutButton.setFont(new Font("黑体", Font.PLAIN, 15));
        logoutButton.setBackground(new Color(0, 191, 255));

        // Add elements to logout panel
        logoutPanel.add(jLabel);
        logoutPanel.add(logoutButton);
    }

    // ---------------------------- Set transaction panel ----------------------------------
    private void getTransactionInfo() {
        transactionInfoPanel.removeAll();

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 6));

        JLabel label1 = new JLabel("书名", SwingConstants.CENTER);
        JLabel label2 = new JLabel("作者", SwingConstants.CENTER);
        JLabel label3 = new JLabel("出版商", SwingConstants.CENTER);
        JLabel label4 = new JLabel("出版年份", SwingConstants.CENTER);
        JLabel label5 = new JLabel("归还时间", SwingConstants.CENTER);
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

        transactionInfoPanel.add(headerPanel);

        user = dbHandler.getUserByName(user.uname);

        assert user != null;
        assert user.transactions != null;
        for (Transaction transaction : user.transactions) {
            JPanel panel = getOneTransactionInfoPanel(transaction);
            transactionInfoPanel.add(panel);
        }

        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMinimum());

        transactionInfoPanel.validate();
        repaint();
    }

    private JPanel getOneTransactionInfoPanel(Transaction transactions) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JLabel nameLabel = new JLabel(transactions.book.bname, SwingConstants.CENTER);
        JLabel authorLabel = new JLabel(transactions.book.author, SwingConstants.CENTER);
        JLabel publisherLabel = new JLabel(transactions.book.publisher, SwingConstants.CENTER);
        JLabel yearLabel = new JLabel(Integer.valueOf(transactions.book.year).toString(), SwingConstants.CENTER);
        JLabel dueDateLabel = new JLabel(transactions.dueDate, SwingConstants.CENTER);

        nameLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        authorLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        publisherLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        yearLabel.setFont(new Font("黑体", Font.PLAIN, 15));
        dueDateLabel.setFont(new Font("黑体", Font.PLAIN, 15));

        panel.add(nameLabel);
        panel.add(authorLabel);
        panel.add(publisherLabel);
        panel.add(yearLabel);
        panel.add(dueDateLabel);

        JButton returnButton = getReturnButton(transactions);
        JPanel returnButtonPanel = new JPanel();
        returnButtonPanel.setLayout(new BorderLayout());
        returnButtonPanel.setBorder(new EmptyBorder(5, 40, 5, 40));
        returnButtonPanel.add(returnButton, BorderLayout.CENTER);

        panel.add(returnButtonPanel);

        panel.setBorder(new EtchedBorder());
        panel.setPreferredSize(new Dimension(940, 40));

        return panel;
    }

    private JButton getReturnButton(Transaction transaction) {
        JButton returnButton =  new JButton("归还");
        returnButton.setFont(new Font("黑体", Font.PLAIN, 15));
        returnButton.setBackground(new Color(0, 191, 255));

        // Delete book records in the database
        returnButton.addActionListener(e -> returnBook(transaction));

        return returnButton;
    }

    private void returnBook(Transaction transaction) {
        // Delete the transaction record and set available of this book to true
        if (deleteTransaction(transaction)) {
            setAvailable(transaction.book);

            JOptionPane.showMessageDialog(null, "图书归还成功！",
                    "消息", JOptionPane.INFORMATION_MESSAGE);

            getTransactionInfo();
            return;
        }

        JOptionPane.showMessageDialog(null, "该图书无法归还！",
                "错误", JOptionPane.ERROR_MESSAGE);
    }

    private boolean deleteTransaction(Transaction transaction) {
        return dbHandler.deleteTransaction(transaction.uname, transaction.book.bname);
    }

    private void setAvailable(Book book) {
        dbHandler.setAvailable(book.bname);
    }

    // ------------------------------ Set search panel -------------------------------------
    private void setSearchPanel() {
        // Search panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 1));

        // Book info of new book
        JLabel nameLabel = new JLabel("书名：", SwingConstants.RIGHT);
        JLabel authorLabel = new JLabel("作者：", SwingConstants.RIGHT);
        JLabel publisherLabel = new JLabel("出版商：", SwingConstants.RIGHT);
        JLabel yearLabel = new JLabel("出版年份：", SwingConstants.RIGHT);
        JLabel categoryLabel = new JLabel("类别：", SwingConstants.RIGHT);

        nameLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        authorLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        publisherLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        yearLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        categoryLabel.setFont(new Font("黑体", Font.PLAIN, 14));

        nameArea = new JTextArea();
        authorArea = new JTextArea();
        publisherArea = new JTextArea();
        yearArea = new JTextArea();
        categoryArea = new JTextArea();

        nameArea.setFont(new Font("黑体", Font.PLAIN, 14));
        authorArea.setFont(new Font("黑体", Font.PLAIN, 14));
        publisherArea.setFont(new Font("黑体", Font.PLAIN, 14));
        yearArea.setFont(new Font("黑体", Font.PLAIN, 14));
        categoryArea.setFont(new Font("黑体", Font.PLAIN, 14));

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

        searchPanel.add(panel1);
        searchPanel.add(panel2);

        // New button panel
        JButton newButton = new JButton("搜索");
        newButton.setFont(new Font("黑体", Font.PLAIN, 14));
        newButton.setBackground(new Color(0, 191, 255));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(newButton, BorderLayout.CENTER);
        buttonPanel.setBorder(new EmptyBorder(10, 420, 10, 420));

        searchPanel.add(buttonPanel);
        searchPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Insert new book into database
        newButton.addActionListener(e -> searchBook());
    }

    private void searchBook() {
        String name = nameArea.getText();
        String author = authorArea.getText();
        String publisher = publisherArea.getText();
        String category = categoryArea.getText();

        int year = -1;

        if (!yearArea.getText().equals("")) {
            try {
                year = Integer.valueOf(yearArea.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "出版年份必须是数字！",
                        "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        ArrayList<Book> books = dbHandler.searchBook(name, author, publisher, category, year);

        assert books != null;
        showSearchResult(books);
    }

    private void showSearchResult(ArrayList<Book> books) {
        if (books.size() == 0) {
            JOptionPane.showMessageDialog(null, "没有符合条件的图书！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Discard old elements
        remove(logoutPanel);
        remove(scrollPane);
        remove(searchPanel);

        // Create search result panel
        JPanel searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchResultPanel.setPreferredSize(new Dimension(980, 100000));

        JScrollPane searchResultScrollPane = new JScrollPane(searchResultPanel);
        searchResultScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        searchResultScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 6));

        JLabel label1 = new JLabel("书名", SwingConstants.CENTER);
        JLabel label2 = new JLabel("作者", SwingConstants.CENTER);
        JLabel label3 = new JLabel("出版商", SwingConstants.CENTER);
        JLabel label4 = new JLabel("出版年份", SwingConstants.CENTER);
        JLabel label5 = new JLabel("状态", SwingConstants.CENTER);
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

        searchResultPanel.add(headerPanel);

        for (Book book : books) {
            JPanel panel = getOneBookInfoPanel(book);
            searchResultPanel.add(panel);
        }

        JScrollBar vertical = searchResultScrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMinimum());

        // Escape panel
        JButton escapeButton = new JButton("返回");
        escapeButton.setFont(new Font("黑体", Font.PLAIN, 15));
        escapeButton.setBackground(new Color(0, 191, 255));
        escapeButton.addActionListener(e -> escapeFromSearch());
        JPanel escapePanel = new JPanel(new BorderLayout());
        escapePanel.add(escapeButton, BorderLayout.CENTER);
        escapePanel.setBorder(new EmptyBorder(15, 425, 15, 425));

        add(searchResultScrollPane, BorderLayout.CENTER);
        add(escapePanel, BorderLayout.SOUTH);

        setVisible(true);
        repaint();
    }

    private JPanel getOneBookInfoPanel(Book book) {
        return new JPanel();
    }

    private void escapeFromSearch() {
        removeAll();

        add(logoutPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        setVisible(true);
        repaint();
    }
}

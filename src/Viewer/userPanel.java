package Viewer;

import Entities.User;
import Entities.Transaction;

import dbOperation.dbHandler;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class userPanel extends JPanel {
    public JButton logoutButton;

    private JScrollPane scrollPane;

    private JPanel transactionInfoPanel;
    private JPanel searchPanel;
    private JPanel logoutPanel;

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
        logoutPanel.setLayout(new FlowLayout());

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

    private JPanel getOneTransactionInfoPanel(Transaction transaction) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JLabel nameLabel = new JLabel(transaction.book.bname, SwingConstants.CENTER);
        JLabel authorLabel = new JLabel(transaction.book.author, SwingConstants.CENTER);
        JLabel publisherLabel = new JLabel(transaction.book.publisher, SwingConstants.CENTER);
        JLabel yearLabel = new JLabel(Integer.valueOf(transaction.book.year).toString(), SwingConstants.CENTER);
        JLabel dueDateLabel = new JLabel(transaction.dueDate, SwingConstants.CENTER);

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

        JButton deleteButton = getReturnButton(transaction);
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setLayout(new BorderLayout());
        deleteButtonPanel.setBorder(new EmptyBorder(5, 40, 5, 40));
        deleteButtonPanel.add(deleteButton, BorderLayout.CENTER);

        panel.add(deleteButtonPanel);

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

    }

    // ------------------------------ Set search panel -------------------------------------
    private void setSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setBackground(Color.green);
    }
}

package Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Display extends JFrame {
    // Four main panels
    private logPanel log;
    private adminPanel admin;
    private registerPanel register;
    private userPanel user;

    // Elements in log panel
    private JButton loginButton;
    private JButton registerButton;
    private JTextArea userName;
    private JPasswordField passwd;

    // Elements in register panel
    private JButton trueRegisterButton;
    private JTextArea regUserName;
    private JPasswordField regPasswd;

    private Display() {
        super("图书管理系统");

        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        // Initial all panels
        initLog();
        initAdmin();
        initRegister();
        initUser();
    }

    // -------------------- Init the login panel -----------------------
    private void initLog() {
        log = new logPanel();

        loginButton = log.loginButton;
        registerButton = log.registerButton;
        userName = log.userName;
        passwd = log.passwd;

        loginButton.addActionListener(e -> Login());
        registerButton.addActionListener(e -> switchToRegister());
    }

    private void Login() {
        // Query the database and load the user panel or admin panel
        String name = userName.getText();
        String pwd = Arrays.toString(passwd.getPassword());
    }

    private void switchToRegister() {
        // Load the register panel
        remove(log);
        add(register, BorderLayout.CENTER);
        setVisible(true);
        repaint();
    }

    // -------------------- Init the admin panel -----------------------
    private void initAdmin() {
        admin = new adminPanel();
    }

    // -------------------- Init the register panel --------------------
    private void initRegister() {
        register = new registerPanel();
        trueRegisterButton = register.registerButton;
        regUserName = register.userName;
        regPasswd = register.passwd;

        trueRegisterButton.addActionListener(e -> Register());
    }

    private void Register() {
        String name = regUserName.getText();
        String pwd = Arrays.toString(regPasswd.getPassword());
    }

    // -------------------- Init the user panel ------------------------
    private void initUser() {
        user = new userPanel();
    }

    // -------------------- Show the main frame ------------------------
    private void showFrame() {
        setLayout(new BorderLayout());
        add(log, BorderLayout.CENTER);

        JLabel infoLabel = new JLabel("图书管理系统");
        add(infoLabel, BorderLayout.SOUTH);

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.showFrame();
    }
}

package Viewer;

import java.awt.*;
import javax.swing.*;

import Entities.User;
import dbOperation.dbHandler;

public class Display extends JFrame {
    // Database handler
    static dbHandler dbH = new dbHandler();

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
    private JTextArea regContact;
    private JTextArea regAddress;

    // Elements in user panel
    private User userInfo;

    // Elements in admin panel
//    private ArrayList<Book> books;

    private Display() {
        super("图书管理系统");

        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(icon);

        // Initial all panels
        initLog();
        initRegister();
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
        String pwd = new String(passwd.getPassword());

        boolean ret = dbH.loginQuery(name, pwd);
        if (ret) {
            // Login success
            remove(log);

            if (name.equals("admin")) {
                // Init the admin panel and switch to it
                initAdmin();

                add(admin, BorderLayout.CENTER);
            }
            else {
                // Init the user panel and switch to it

                add(user, BorderLayout.CENTER);
            }

            setVisible(true);
            repaint();
        } else {
            // Login fail
            JOptionPane.showMessageDialog(null, "用户名与密码不匹配！",
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void switchToRegister() {
        // Load the register panel
        remove(log);
        add(register, BorderLayout.CENTER);
        setVisible(true);
        repaint();
    }

    // -------------------- Init the register panel --------------------
    private void initRegister() {
        register = new registerPanel();
        trueRegisterButton = register.registerButton;
        regUserName = register.userName;
        regPasswd = register.passwd;
        regAddress = register.address;
        regContact = register.contact;

        trueRegisterButton.addActionListener(e -> Register());
    }

    private void Register() {
        String name = regUserName.getText();
        String pwd = new String(regPasswd.getPassword());
        String address = regAddress.getText();
        String contact = regContact.getText();

        if (dbH.registerQuery(name, pwd, address, contact)) {
            JOptionPane.showMessageDialog(null, "注册成功！",
                    "消息", JOptionPane.ERROR_MESSAGE);

            // Return to login panel
            remove(register);
            add(log, BorderLayout.CENTER);
            setVisible(true);
            repaint();
        }
        else
            JOptionPane.showMessageDialog(null, "用户名被占用或输入格式有误！",
                    "错误", JOptionPane.ERROR_MESSAGE);
    }


    // -------------------- Init the admin panel -----------------------
    private void initAdmin() {
        admin = new adminPanel();
    }

    // -------------------- Init the user panel ------------------------
    private void initUser() {
        user = new userPanel(userInfo);
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

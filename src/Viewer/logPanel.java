package Viewer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class logPanel extends JPanel {
    public JPanel main;
    public JPanel login;
    public JTextArea userName;
    public JPasswordField passwd;
    public JButton loginButton;
    public JButton registerButton;

    logPanel() {
        initMain();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(main, BorderLayout.CENTER);
    }

    // Init the login panel
    private void initLogin() {
        login = new JPanel();

        JPanel userNamePanel = new JPanel();
        JPanel passwdPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        userNamePanel.setLayout(new GridLayout(1, 2));
        JLabel userNameLabel = new JLabel("用户名：");
        userNameLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        userName = new JTextArea();
        userName.setBorder(new EtchedBorder());
        userName.setFont(new Font("黑体", Font.PLAIN, 14));
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userName);
        userNamePanel.setBorder(new EmptyBorder(30, 400, 30, 400));

        passwdPanel.setLayout(new GridLayout(1, 2));
        JLabel passwdLabel = new JLabel("密码：");
        passwdLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        passwd = new JPasswordField();
        passwd.setBorder(new EtchedBorder());
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwd);
        passwdPanel.setBorder(new EmptyBorder(30, 400, 30, 400));

        buttonPanel.setLayout(new GridLayout(2, 1));
        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        loginButton.setFont(new Font("黑体", Font.PLAIN, 14));
        registerButton.setFont(new Font("黑体", Font.PLAIN, 14));
        loginButton.setBackground(new Color(0, 191, 255));
        registerButton.setBackground(new Color(0, 191, 255));
        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        loginButtonPanel.add(loginButton);
        registerButtonPanel.add(registerButton);
        buttonPanel.add(loginButtonPanel);
        buttonPanel.add(registerButtonPanel);
        buttonPanel.setBorder(new EmptyBorder(0, 400, 0, 400));

        login.setLayout(new GridLayout(3, 1));
        login.add(userNamePanel);
        login.add(passwdPanel);
        login.add(buttonPanel);
        login.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // Init the main panel
    private void initMain() {
        main = new JPanel();

        String ImageUrl = "login.png";
        ImageIcon icon = new ImageIcon(ImageUrl);
        icon = new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));

        JLabel image = new JLabel(icon);
        image.setSize(200, 200);

        initLogin();

        main.setLayout(new GridLayout(2, 1));
        main.add(image);
        main.add(login);
    }
}

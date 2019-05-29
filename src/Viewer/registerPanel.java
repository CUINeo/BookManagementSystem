package Viewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class registerPanel extends JPanel {
    public JPanel main;
    public JPanel login;
    public JTextArea userName;
    public JPasswordField passwd;
    public JButton registerButton;

    registerPanel() {
        initMain();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(main, BorderLayout.CENTER);
    }

    // Init the register panel
    private void initRegister() {
        login = new JPanel();

        JPanel userNamePanel = new JPanel();
        JPanel passwdPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        userNamePanel.setLayout(new GridLayout(1, 2));
        JLabel userNameLabel = new JLabel("用户名：");
        userName = new JTextArea();
        userName.setBorder(new LineBorder(Color.BLACK));
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userName);
        userNamePanel.setBorder(new EmptyBorder(30, 400, 30, 400));

        passwdPanel.setLayout(new GridLayout(1, 2));
        JLabel passwdLabel = new JLabel("密码：");
        passwd = new JPasswordField();
        passwd.setBorder(new LineBorder(Color.BLACK));
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwd);
        passwdPanel.setBorder(new EmptyBorder(30, 400, 30, 400));

        buttonPanel.setLayout(new GridLayout(2, 1));
        registerButton = new JButton("注册");
        registerButton.setBackground(new Color(0, 191, 255));
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        registerButtonPanel.add(registerButton);
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

        initRegister();

        main.setLayout(new GridLayout(2, 1));
        main.add(image);
        main.add(login);
    }
}

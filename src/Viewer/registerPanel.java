package Viewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class registerPanel extends JPanel {
    public JPanel register;
    public JTextArea userName;
    public JPasswordField passwd;
    public JTextArea address;
    public JTextArea contact;
    public JButton registerButton;

    registerPanel() {
        String ImageUrl = "login.png";
        ImageIcon icon = new ImageIcon(ImageUrl);
        icon = new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));

        JLabel image = new JLabel(icon);
        image.setSize(200, 200);

        initRegister();

        setLayout(new GridLayout(2, 1));
        add(image);
        add(register);

        setBorder(new EmptyBorder(0, 10, 0, 10));
    }

    // Init the register panel
    private void initRegister() {
        register = new JPanel();

        JPanel userNamePanel = new JPanel();
        JPanel passwdPanel = new JPanel();
        JPanel addressPanel = new JPanel();
        JPanel contactPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        userNamePanel.setLayout(new GridLayout(1, 2));
        JLabel userNameLabel = new JLabel("用户名：");
        userName = new JTextArea();
        userName.setBorder(new LineBorder(Color.BLACK));
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userName);
        userNamePanel.setBorder(new EmptyBorder(17, 400, 16, 400));

        passwdPanel.setLayout(new GridLayout(1, 2));
        JLabel passwdLabel = new JLabel("密码：");
        passwd = new JPasswordField();
        passwd.setBorder(new LineBorder(Color.BLACK));
        passwdPanel.add(passwdLabel);
        passwdPanel.add(passwd);
        passwdPanel.setBorder(new EmptyBorder(17, 400, 16, 400));

        addressPanel.setLayout(new GridLayout(1, 2));
        JLabel addressLabel = new JLabel("地址：");
        address = new JTextArea();
        address.setBorder(new LineBorder(Color.BLACK));
        addressPanel.add(addressLabel);
        addressPanel.add(address);
        addressPanel.setBorder(new EmptyBorder(17, 400, 16, 400));

        contactPanel.setLayout(new GridLayout(1, 2));
        JLabel contactLabel = new JLabel("联系方式：");
        contact = new JTextArea();
        contact.setBorder(new LineBorder(Color.BLACK));
        contactPanel.add(contactLabel);
        contactPanel.add(contact);
        contactPanel.setBorder(new EmptyBorder(17, 400, 16, 400));

        buttonPanel.setLayout(new GridLayout(2, 1));
        registerButton = new JButton("注册");
        registerButton.setBackground(new Color(0, 191, 255));
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.add(registerButton);
        buttonPanel.add(registerButtonPanel);
        buttonPanel.setBorder(new EmptyBorder(0, 400, 0, 400));

        register.setLayout(new GridLayout(5, 1));
        register.add(userNamePanel);
        register.add(passwdPanel);
        register.add(addressPanel);
        register.add(contactPanel);
        register.add(buttonPanel);
        register.setBorder(new EmptyBorder(0, 10, 0, 10));
    }
}

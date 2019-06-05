package Viewer;

import Entities.User;

import javax.swing.*;
import java.awt.*;

public class userPanel extends JPanel {
    private User user;

    userPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        JPanel logoutPanel = initLogout();
    }

    // Init the logout panel
    private JPanel initLogout() {
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout());

        // Init labels
        JLabel jLabel1 = new JLabel("用户：");
        JLabel jLabel2 = new JLabel(user.uname);

        // Init the button
        JButton logoutButton = new JButton("退出登录");
        logoutButton.setBackground(new Color(0, 191, 255));

        // Add elements to logout panel
        logoutPanel.add(jLabel1);
        logoutPanel.add(jLabel2);
        logoutPanel.add(logoutButton);

        return logoutPanel;
    }
}

import java.awt.*;

import javax.swing.*;
import java.sql.*;

public class SignUp {
    SignUp() {
        JFrame f = new JFrame("Sign Up");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            signUpAction(usernameField.getText(), emailField.getText(), new String(passwordField.getPassword()));
            f.dispose();
        });

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(signUpButton, gbc);

        f.add(panel);
        f.pack();

        f.setVisible(true);
    }

    private void signUpAction(String username, String email, String password) {
        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sign up successful");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sign up failed: " + ex.getMessage());
        }
    }

    private Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/java_db_test";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }
}

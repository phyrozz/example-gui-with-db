import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.sql.ResultSet;

public class Login {
    Login() {
        JFrame frame = new JFrame("Login");

        // Define Login field and buttons
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            signInAction(emailField.getText(), new String(passwordField.getPassword()));
        });
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            new SignUp();
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(signUpButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);



        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    private void signInAction(String email, String password) {
        String url = "jdbc:mysql://localhost:3306/java_db_test";
        String user = "root";

        try (Connection conn = DriverManager.getConnection(url, user, "")) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?")) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String username = rs.getString("username");
                    JOptionPane.showMessageDialog(null, "Welcome, " + username + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Sign up failed");
            }
        } catch (HeadlessException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

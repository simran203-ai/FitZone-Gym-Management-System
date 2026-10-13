
import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public LoginFrame() {

        setTitle("FitZone - Login");
        setSize(450, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(30, 45, 65));

        JLabel titleLabel = new JLabel(
                "FITZONE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitleLabel = new JLabel(
                "Gym Management System", SwingConstants.CENTER);
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        roleBox = new JComboBox<>(new String[]{
                "Admin", "Member"
        });

        usernameField = new JTextField(18);
        passwordField = new JPasswordField(18);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        addFormRow(formPanel, gbc, 0, "Login as:", roleBox);
        addFormRow(formPanel, gbc, 1, "Username:", usernameField);
        addFormRow(formPanel, gbc, 2, "Password:", passwordField);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> login());

        getRootPane().setDefaultButton(loginButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            java.awt.Component field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void login() {

        String username = usernameField.getText().trim();
        String password =
                new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if ("Admin".equals(role)) {

                AdminDAO adminDAO = new AdminDAO();
                Admin admin = adminDAO.login(username, password);

                if (admin != null) {
                    JOptionPane.showMessageDialog(
                            this, "Admin login successful!");

                    dispose();
                    new DashboardFrame();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid admin username or password.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {

                MemberDAO memberDAO = new MemberDAO();

                Member member =
                        memberDAO.loginMember(username, password);

                if (member != null) {
                    JOptionPane.showMessageDialog(
                            this, "Member login successful!");

                    dispose();
                    new UserDashboardFrame(member);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid member name or registered mobile number.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to complete login.\n" +
                    "Check the database connection and try again.\n\n" +
                    ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

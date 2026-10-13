
import java.awt.*;
import javax.swing.*;

public class DashboardFrame extends JFrame {

    private final Color backgroundColor = new Color(245, 247, 248);
    private final Color sidebarColor = new Color(28, 32, 35);
    private final Color accentColor = new Color(190, 245, 70);
    private final Color textColor = new Color(40, 45, 48);

    public DashboardFrame() {
        setTitle("FitZone - Dashboard");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(225, 680));
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 15, 20, 15));

        JLabel logo = new JLabel("FITZONE");
        logo.setFont(new Font("Arial", Font.BOLD, 27));
        logo.setForeground(accentColor);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("GYM MANAGEMENT");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(45));

        JButton dashboardButton = createMenuButton("◆  Dashboard", true);
        JButton membersButton = createMenuButton("♟  Members", false);
        JButton membershipButton = createMenuButton("▣  Memberships", false);
        JButton workoutButton = createMenuButton("✦  AI Workout", false);
        JButton aboutButton = createMenuButton("ⓘ  About", false);

        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(membersButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(membershipButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(workoutButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(aboutButton);

        sidebar.add(Box.createVerticalGlue());

        JButton logoutButton = createMenuButton("↪  Logout", false);
        sidebar.add(logoutButton);

        // MAIN CONTENT
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(textColor);

        JLabel dateLabel = new JLabel("FITNESS • MEMBERS • PROGRESS");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        dateLabel.setForeground(new Color(100, 110, 115));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(dateLabel);

        topPanel.add(headingPanel, BorderLayout.WEST);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // WELCOME BANNER
        JPanel centerPanel = new JPanel(new BorderLayout(0, 22));
        centerPanel.setOpaque(false);

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(sidebarColor);
        welcomePanel.setBorder(
                BorderFactory.createEmptyBorder(24, 25, 24, 20)
        );

        JPanel welcomeText = new JPanel();
        welcomeText.setLayout(new BoxLayout(welcomeText, BoxLayout.Y_AXIS));
        welcomeText.setOpaque(false);

        JLabel welcome = new JLabel("Welcome to FitZone!");
        welcome.setFont(new Font("Arial", Font.BOLD, 25));
        welcome.setForeground(Color.WHITE);

        JLabel message = new JLabel(
                "Manage your gym members and workout plans in one place."
        );
        message.setFont(new Font("Arial", Font.PLAIN, 13));
        message.setForeground(new Color(220, 225, 225));

        welcomeText.add(welcome);
        welcomeText.add(Box.createVerticalStrut(10));
        welcomeText.add(message);

        JLabel accent = new JLabel("FIT");
        accent.setFont(new Font("Arial", Font.BOLD, 30));
        accent.setForeground(accentColor);

        welcomePanel.add(welcomeText, BorderLayout.CENTER);
        welcomePanel.add(accent, BorderLayout.EAST);

        // DATABASE STATISTICS
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        statsPanel.setOpaque(false);

        int memberCount = MemberDAO.getMemberCount();
        int membershipCount = MembershipDAO.getMembershipCount();

        statsPanel.add(createCard("TOTAL MEMBERS", memberCount, "Registered members"));
        statsPanel.add(createCard("TOTAL MEMBERSHIPS", membershipCount, "Membership records"));

        // QUICK ACTIONS
        JPanel actionsPanel = new JPanel(new BorderLayout(0, 12));
        actionsPanel.setOpaque(false);

        JLabel actionsHeading = new JLabel("Quick Actions");
        actionsHeading.setFont(new Font("Arial", Font.BOLD, 19));
        actionsHeading.setForeground(textColor);

        JPanel actionButtons = new JPanel(new GridLayout(1, 3, 12, 0));
        actionButtons.setOpaque(false);

        JButton addMemberButton = createActionButton("+  Members");
        JButton manageMembershipButton = createActionButton("▣  Membership");
        JButton aiButton = createActionButton("✦  AI Workout");

        actionButtons.add(addMemberButton);
        actionButtons.add(manageMembershipButton);
        actionButtons.add(aiButton);

        actionsPanel.add(actionsHeading, BorderLayout.NORTH);
        actionsPanel.add(actionButtons, BorderLayout.CENTER);

        centerPanel.add(welcomePanel, BorderLayout.NORTH);
        centerPanel.add(statsPanel, BorderLayout.CENTER);
        centerPanel.add(actionsPanel, BorderLayout.SOUTH);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // BUTTON ACTIONS
        membersButton.addActionListener(e -> new MemberFrame().setVisible(true));
        membershipButton.addActionListener(e -> new MembershipFrame().setVisible(true));
        workoutButton.addActionListener(e -> new AIWorkoutFrame().setVisible(true));

        addMemberButton.addActionListener(e -> new MemberFrame().setVisible(true));
        manageMembershipButton.addActionListener(e -> new MembershipFrame().setVisible(true));
        aiButton.addActionListener(e -> new AIWorkoutFrame().setVisible(true));
        aboutButton.addActionListener(e -> {
    new AboutFrame().setVisible(true);
});
        
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createMenuButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(195, 44));
        button.setPreferredSize(new Dimension(195, 44));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(selected ? accentColor : Color.WHITE);
        button.setBackground(selected ? new Color(54, 62, 62) : sidebarColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 5));
        return button;
    }

    private JPanel createCard(String title, int value, String description) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 230)),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(new Color(95, 105, 105));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 38));
        valueLabel.setForeground(textColor);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(new Color(115, 125, 125));

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.setOpaque(false);
        lowerPanel.add(valueLabel);
        lowerPanel.add(Box.createVerticalStrut(5));
        lowerPanel.add(descriptionLabel);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(lowerPanel, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(textColor);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 225, 215)),
                BorderFactory.createEmptyBorder(15, 8, 15, 8)
        ));
        return button;
    }
}
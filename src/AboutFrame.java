import java.awt.*;
import javax.swing.*;

public class AboutFrame extends JFrame {

    public AboutFrame() {
        setTitle("FitZone - About Us");
        setSize(760, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color dark = new Color(28, 32, 35);
        Color green = new Color(190, 245, 70);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(245, 247, 248));
        main.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(dark);
        header.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel logo = new JLabel("FITZONE");
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(green);

        JLabel subtitle = new JLabel("GYM MANAGEMENT SYSTEM");
        subtitle.setFont(new Font("Arial", Font.BOLD, 15));
        subtitle.setForeground(Color.WHITE);

        header.add(logo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitle);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel welcome = new JLabel("Welcome to FitZone!");
        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(dark);

        JLabel description = new JLabel(
            "<html><div style='width:500px;'>" +
            "FitZone is a Gym Management System designed to simplify " +
            "gym operations. It helps administrators manage members, " +
            "memberships, and AI-based workout recommendations." +
            "</div></html>"
        );
        description.setFont(new Font("Arial", Font.PLAIN, 14));
           
JLabel features = new JLabel(
    "<html><div style='width:500px;'>" +
    "<b style='font-size:15px;'>KEY FEATURES</b><br><br>" +

    "<b>1. Member Management</b><br>" +
    "Register new gym members, view member records, update details, " +
    "and delete records when required.<br><br>" +

    "<b>2. Membership Management</b><br>" +
    "Manage membership plans, select members, maintain membership " +
    "records, and track plan details and payment status.<br><br>" +

    "<b>3. AI Workout Recommendation</b><br>" +
    "Generate rule-based workout recommendations using the member's " +
    "fitness goal, fitness level, and available workout days.<br><br>" +

    "<b>4. Dashboard and Statistics</b><br>" +
    "View member and membership counts from the database in one place " +
    "for convenient gym administration.<br><br>" +

    "<b>5. Database Management</b><br>" +
    "Store and retrieve member, membership, and workout recommendation " +
    "records using MySQL and JDBC.<br><br>" +

    "<b>6. Admin Login</b><br>" +
    "Provide an administrator login screen to access the gym management " +
    "application.<br><br>" +

    "<b>TECHNOLOGY STACK</b><br><br>" +
    "• Frontend: Java Swing GUI<br>" +
    "• Programming Language: Java<br>" +
    "• Database: MySQL<br>" +
    "• Database Connectivity: JDBC<br><br>" +

    "<b>PROJECT OBJECTIVE</b><br><br>" +
    "To simplify daily gym operations, organize member records, " +
    "manage memberships, and provide personalized workout guidance " +
    "through a user-friendly desktop application." +
    "</div></html>"
);
features.setFont(new Font("Arial", Font.PLAIN, 13));
         info.add(welcome);
        info.add(Box.createVerticalStrut(15));
        info.add(description);
        info.add(Box.createVerticalStrut(25));
        info.add(features);

        JButton close = new JButton("Close");
        close.setBackground(green);
        close.setFocusPainted(false);
        close.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(close);

        main.add(header, BorderLayout.NORTH);
        main.add(info, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        add(main);
        setVisible(true);
    }
}

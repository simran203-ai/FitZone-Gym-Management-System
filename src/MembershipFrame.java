
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class MembershipFrame extends JFrame {

    private JComboBox<String> memberBox;
    private JComboBox<String> planBox;
    private JComboBox<String> paymentBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JLabel priceLabel;

    private final Color DARK = new Color(31, 38, 45);
    private final Color GREEN = new Color(170, 230, 60);
    private final Color BACKGROUND = new Color(245, 247, 249);
    private final Color TEXT = new Color(45, 52, 60);

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MembershipFrame() {

        setTitle("FitZone - Membership Management");
        setSize(720, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // Dark header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK);
        header.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JLabel title = new JLabel("Membership Management");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage plans, dates and payments");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(205, 213, 220));

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);
        heading.add(title);
        heading.add(Box.createVerticalStrut(7));
        heading.add(subtitle);

        JLabel logo = new JLabel("FITZONE");
        logo.setFont(new Font("Arial", Font.BOLD, 19));
        logo.setForeground(GREEN);

        header.add(heading, BorderLayout.WEST);
        header.add(logo, BorderLayout.EAST);

        // Main form card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 234)),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        memberBox = new JComboBox<>();
        planBox = new JComboBox<>(
                new String[]{"Basic", "Standard", "Premium"}
        );
        paymentBox = new JComboBox<>(
                new String[]{"Paid", "Pending"}
        );

        startDateField = new JTextField();
        endDateField = new JTextField();

        startDateField.setEditable(false);
        endDateField.setEditable(false);

        styleInput(memberBox);
        styleInput(planBox);
        styleInput(paymentBox);
        styleInput(startDateField);
        styleInput(endDateField);

        priceLabel = new JLabel("₹1,000");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        priceLabel.setForeground(new Color(75, 135, 20));

        addFormRow(card, gbc, 0, "Select Member", memberBox);
        addFormRow(card, gbc, 1, "Membership Plan", planBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.35;
        card.add(makeLabel("Membership Price"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        card.add(priceLabel, gbc);

        // Start date and picker button
        JPanel startPanel = new JPanel(new BorderLayout(8, 0));
        startPanel.setOpaque(false);
        startPanel.add(startDateField, BorderLayout.CENTER);

        JButton startPicker = makeButton("Choose Date", DARK);
        startPanel.add(startPicker, BorderLayout.EAST);

        addFormRow(card, gbc, 3, "Start Date", startPanel);

        // End date is calculated automatically
        addFormRow(card, gbc, 4, "End Date (Automatic)", endDateField);
        addFormRow(card, gbc, 5, "Payment Status", paymentBox);

        JLabel note = new JLabel(
                "Choose a start date; the end date updates automatically."
        );
        note.setFont(new Font("Arial", Font.ITALIC, 12));
        note.setForeground(new Color(100, 110, 120));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        card.add(note, gbc);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(22, 28, 12, 28));
        content.add(card, BorderLayout.CENTER);

        // Bottom buttons
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 18));
        footer.setBackground(BACKGROUND);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 20, 8, 28));

        JButton saveButton = makeButton("Save Membership", new Color(95, 150, 30));
        JButton closeButton = makeButton("Close", DARK);

        footer.add(closeButton);
        footer.add(saveButton);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);

        // Events
        planBox.addActionListener(e -> {
            updatePrice();
            updateEndDate();
        });

        startPicker.addActionListener(e -> chooseStartDate());

        saveButton.addActionListener(e -> saveMembership());
        closeButton.addActionListener(e -> dispose());

        loadMembers();
        updatePrice();

        startDateField.setText(LocalDate.now().format(formatter));
        updateEndDate();

        setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            Component field) {

        gbc.gridwidth = 1;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.35;
        panel.add(makeLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(field, gbc);
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT);
        return label;
    }

    private void styleInput(JComponent input) {
        input.setFont(new Font("Arial", Font.PLAIN, 14));
        input.setBackground(Color.WHITE);
        input.setForeground(TEXT);
        input.setPreferredSize(new Dimension(220, 36));

        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 222)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private JButton makeButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(11, 17, 11, 17));
        return button;
    }

    private void loadMembers() {

        String sql = "SELECT id, name FROM members ORDER BY name";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            memberBox.removeAllItems();

            while (rs.next()) {
                memberBox.addItem(
                        rs.getInt("id") + " - " + rs.getString("name")
                );
            }

            if (memberBox.getItemCount() == 0) {
                memberBox.addItem("No members available");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load members. Please check the database connection.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void updatePrice() {

        Object selected = planBox.getSelectedItem();
        if (selected == null) {
            return;
        }

        String plan = selected.toString();

        if (plan.equals("Basic")) {
            priceLabel.setText("₹1,000");
        } else if (plan.equals("Standard")) {
            priceLabel.setText("₹2,000");
        } else {
            priceLabel.setText("₹3,000");
        }
    }

    private int getPlanMonths() {

        String plan = planBox.getSelectedItem().toString();

        if (plan.equals("Basic")) {
            return 1;
        } else if (plan.equals("Standard")) {
            return 3;
        } else {
            return 6;
        }
    }

    private void updateEndDate() {

        try {
            LocalDate start = LocalDate.parse(
                    startDateField.getText(), formatter
            );

            LocalDate end = start.plusMonths(getPlanMonths());
            endDateField.setText(end.format(formatter));

        } catch (Exception e) {
            endDateField.setText("");
        }
    }

    private void chooseStartDate() {

        LocalDate initialDate;

        try {
            initialDate = LocalDate.parse(
                    startDateField.getText(), formatter
            );
        } catch (Exception e) {
            initialDate = LocalDate.now();
        }

        LocalDate chosen = showCalendar(initialDate);

        if (chosen != null) {
            startDateField.setText(chosen.format(formatter));
            updateEndDate();
        }
    }

    // Built-in Swing calendar; no additional JAR is needed.
    private LocalDate showCalendar(LocalDate initialDate) {

        final LocalDate[] selectedDate = {null};
        final YearMonth[] displayedMonth = {
                YearMonth.from(initialDate)
        };

        JDialog dialog = new JDialog(
                this, "Choose Start Date", true
        );
        dialog.setSize(340, 330);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new BorderLayout(8, 0));
        JButton previous = new JButton("<");
        JButton next = new JButton(">");
        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 16));

        top.add(previous, BorderLayout.WEST);
        top.add(monthLabel, BorderLayout.CENTER);
        top.add(next, BorderLayout.EAST);
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel calendar = new JPanel(new GridLayout(0, 7, 4, 4));
        calendar.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));

        Runnable refreshCalendar = () -> {
            calendar.removeAll();

            monthLabel.setText(displayedMonth[0].getMonth()
                    .toString() + " " + displayedMonth[0].getYear());

            String[] weekdays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

            for (String day : weekdays) {
                JLabel label = new JLabel(day, SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 11));
                calendar.add(label);
            }

            LocalDate first = displayedMonth[0].atDay(1);
            int offset = first.getDayOfWeek().getValue() - 1;

            for (int i = 0; i < offset; i++) {
                calendar.add(new JLabel(""));
            }

            for (int day = 1; day <= displayedMonth[0].lengthOfMonth(); day++) {
                final int selectedDay = day;
                JButton dayButton = new JButton(String.valueOf(day));
                dayButton.setMargin(new Insets(2, 2, 2, 2));

                if (displayedMonth[0].atDay(day).equals(LocalDate.now())) {
                    dayButton.setBackground(GREEN);
                }

                dayButton.addActionListener(e -> {
                    selectedDate[0] = displayedMonth[0].atDay(selectedDay);
                    dialog.dispose();
                });

                calendar.add(dayButton);
            }

            calendar.revalidate();
            calendar.repaint();
        };

        previous.addActionListener(e -> {
            displayedMonth[0] = displayedMonth[0].minusMonths(1);
            refreshCalendar.run();
        });

        next.addActionListener(e -> {
            displayedMonth[0] = displayedMonth[0].plusMonths(1);
            refreshCalendar.run();
        });

        refreshCalendar.run();

        dialog.add(top, BorderLayout.NORTH);
        dialog.add(calendar, BorderLayout.CENTER);
        dialog.setVisible(true);

        return selectedDate[0];
    }

    private void saveMembership() {

        try {
            if (memberBox.getSelectedItem() == null
                    || memberBox.getSelectedItem().toString()
                    .equals("No members available")) {

                JOptionPane.showMessageDialog(
                        this, "Please register a member first."
                );
                return;
            }

            int memberId = Integer.parseInt(
                    memberBox.getSelectedItem().toString().split(" - ")[0]
            );

            String plan = planBox.getSelectedItem().toString();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            String paymentStatus = paymentBox.getSelectedItem().toString();

            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            if (end.isBefore(start)) {
                JOptionPane.showMessageDialog(
                        this, "End date cannot be before start date."
                );
                return;
            }

            double amount;

            if (plan.equals("Basic")) {
                amount = 1000;
            } else if (plan.equals("Standard")) {
                amount = 2000;
            } else {
                amount = 3000;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Member ID: " + memberId
                            + "\nPlan: " + plan
                            + "\nPrice: ₹" + amount
                            + "\nStart: " + startDate
                            + "\nEnd: " + endDate
                            + "\nPayment: " + paymentStatus
                            + "\n\nSave this membership?",
                    "Confirm Membership",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = MembershipDAO.addMembership(
                    memberId,
                    plan,
                    startDate,
                    endDate,
                    amount,
                    paymentStatus
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Membership added successfully!",
                        "FitZone",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to add membership. Please check the database.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please check the membership information and date format.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
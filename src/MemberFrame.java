
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberFrame extends JFrame {

    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JLabel memberCountLabel;
    private JTextField searchField;

    private final Color dark = new Color(28, 32, 35);
    private final Color green = new Color(190, 245, 70);
    private final Color background = new Color(245, 247, 248);
    private final Color textColor = new Color(40, 48, 48);

    public MemberFrame() {

        setTitle("FitZone - Member Management");
        setSize(1150, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(background);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        // ================= HEADER =================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(dark);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        );

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setOpaque(false);

        JLabel title = new JLabel("FITZONE  |  MEMBER MANAGEMENT");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(green);

        JLabel subtitle = new JLabel(
                "Register, view and manage your gym members"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.WHITE);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subtitle);

        memberCountLabel = new JLabel("TOTAL MEMBERS: 0");
        memberCountLabel.setFont(new Font("Arial", Font.BOLD, 13));
        memberCountLabel.setForeground(green);

        headerPanel.add(headingPanel, BorderLayout.WEST);
        headerPanel.add(memberCountLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= TABLE =================

        String[] columns = {
            "ID", "Name", "Age", "Gender", "Phone",
            "Email", "Weight", "Height", "BMI",
            "Fitness Level", "Goal"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        memberTable = new JTable(tableModel);
        memberTable.setRowHeight(31);
        memberTable.setFont(new Font("Arial", Font.PLAIN, 13));
        memberTable.setForeground(textColor);
        memberTable.setBackground(Color.WHITE);
        memberTable.setSelectionBackground(new Color(225, 245, 195));
        memberTable.setSelectionForeground(dark);
        memberTable.setGridColor(new Color(232, 235, 235));
        memberTable.setShowVerticalLines(false);
        memberTable.setFillsViewportHeight(true);
        memberTable.setAutoCreateRowSorter(true);

        memberTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );
        memberTable.getTableHeader().setBackground(dark);
        memberTable.getTableHeader().setForeground(green);
        memberTable.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );
        memberTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(memberTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 225, 225)
                )
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        // ================= TABLE AREA =================

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setBackground(background);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(background);

        JLabel searchLabel = new JLabel("Search members:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchLabel.setForeground(textColor);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 34));
        searchField.setToolTipText(
                "Search by name, phone, email or member ID"
        );

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, false);

        JButton showAllButton = new JButton("Show All");
        styleButton(showAllButton, false);

        JPanel searchInputPanel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 8, 0
        ));
        searchInputPanel.setOpaque(false);
        searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);
        searchInputPanel.add(showAllButton);

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchInputPanel, BorderLayout.CENTER);

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // ================= BUTTONS =================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 8)
        );
        buttonPanel.setBackground(background);

        JButton addButton = new JButton("+ Add Member");
        JButton updateButton = new JButton("Update Member");
        JButton deleteButton = new JButton("Delete Member");
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        styleButton(addButton, true);
        styleButton(updateButton, false);
        styleButton(deleteButton, false);
        styleButton(refreshButton, false);
        styleButton(closeButton, false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ================= BUTTON ACTIONS =================

        addButton.addActionListener(e -> showAddMemberDialog());

        updateButton.addActionListener(e -> {
            int selectedRow = memberTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a member from the table first.",
                        "No Member Selected",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int modelRow = memberTable.convertRowIndexToModel(
                    selectedRow
            );

            int memberId = Integer.parseInt(
                    tableModel.getValueAt(modelRow, 0).toString()
            );

            showUpdateMemberDialog(memberId);
        });

        deleteButton.addActionListener(e -> deleteMember());

        refreshButton.addActionListener(e -> {
            loadMembers();
            searchField.setText("");
        });

        closeButton.addActionListener(e -> dispose());

        searchButton.addActionListener(e -> searchMembers());

        searchField.addActionListener(e -> searchMembers());

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadMembers();
        });

        add(mainPanel);

        loadMembers();
        setVisible(true);
    }

    // ================= BUTTON STYLE =================

    private void styleButton(JButton button, boolean primary) {

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(145, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        if (primary) {
            button.setBackground(green);
            button.setForeground(dark);
            button.setBorder(BorderFactory.createEmptyBorder(
                    8, 12, 8, 12
            ));
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(textColor);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            new Color(210, 220, 215)
                    ),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
    }

    // ================= LOAD MEMBERS =================

    private void loadMembers() {

        tableModel.setRowCount(0);

        MemberDAO dao = new MemberDAO();
        List<Member> members = dao.getAllMembers();

        for (Member member : members) {
            tableModel.addRow(new Object[]{
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getGender(),
                member.getPhone(),
                member.getEmail(),
                member.getWeight(),
                member.getHeight(),
                member.getBmi(),
                member.getFitnessLevel(),
                member.getGoal()
            });
        }

        memberCountLabel.setText(
                "TOTAL MEMBERS: " + members.size()
        );
    }

    // ================= SEARCH MEMBERS =================

    private void searchMembers() {

        String query = searchField.getText()
                .trim()
                .toLowerCase();

        tableModel.setRowCount(0);

        MemberDAO dao = new MemberDAO();
        List<Member> members = dao.getAllMembers();

        int count = 0;

        for (Member member : members) {

            String rowText = (
                    member.getId() + " " +
                    member.getName() + " " +
                    member.getPhone() + " " +
                    member.getEmail() + " " +
                    member.getGoal()
            ).toLowerCase();

            if (rowText.contains(query)) {
                tableModel.addRow(new Object[]{
                    member.getId(),
                    member.getName(),
                    member.getAge(),
                    member.getGender(),
                    member.getPhone(),
                    member.getEmail(),
                    member.getWeight(),
                    member.getHeight(),
                    member.getBmi(),
                    member.getFitnessLevel(),
                    member.getGoal()
                });

                count++;
            }
        }

        memberCountLabel.setText(
                "SHOWING: " + count + " MEMBERS"
        );
    }

    // ================= ADD MEMBER =================

    private void showAddMemberDialog() {

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField heightField = new JTextField();

        JComboBox<String> genderBox = new JComboBox<>(
                new String[]{"Male", "Female", "Other"}
        );

        JComboBox<String> fitnessBox = new JComboBox<>(
                new String[]{"Beginner", "Intermediate", "Advanced"}
        );

        JComboBox<String> goalBox = new JComboBox<>(
                new String[]{"Muscle Gain", "Weight Loss", "Fitness"}
        );

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(
                12, 12, 12, 12
        ));

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);

        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email Address:"));
        panel.add(emailField);

        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);

        panel.add(new JLabel("Height (cm):"));
        panel.add(heightField);

        panel.add(new JLabel("Fitness Level:"));
        panel.add(fitnessBox);

        panel.add(new JLabel("Fitness Goal:"));
        panel.add(goalBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Register New Member",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String weightText = weightField.getText().trim();
            String heightText = heightField.getText().trim();

            if (name.isEmpty()) {
                showWarning("Please enter the member's name.");
                return;
            }

            int age = Integer.parseInt(ageText);

            if (age < 16 || age > 100) {
                showWarning("Age must be between 16 and 100.");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                showWarning(
                        "Phone number must contain exactly 10 digits."
                );
                return;
            }

            if (!email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            )) {
                showWarning("Please enter a valid email address.");
                return;
            }

            double weight = Double.parseDouble(weightText);

            if (weight < 30 || weight > 250) {
                showWarning("Weight must be between 30 and 250 kg.");
                return;
            }

            double height = Double.parseDouble(heightText);

            if (height < 100 || height > 250) {
                showWarning("Height must be between 100 and 250 cm.");
                return;
            }

            String gender = genderBox.getSelectedItem().toString();
            String fitnessLevel = fitnessBox.getSelectedItem().toString();
            String goal = goalBox.getSelectedItem().toString();

            double heightMeter = height / 100.0;
            double bmi = weight / (heightMeter * heightMeter);

            Member member = new Member(
                    name,
                    age,
                    gender,
                    phone,
                    email,
                    weight,
                    height,
                    bmi,
                    fitnessLevel,
                    goal
            );

            MemberDAO dao = new MemberDAO();

            if (dao.memberExists(phone, email)) {
                JOptionPane.showMessageDialog(
                        this,
                        "This phone number or email is already registered.",
                        "Duplicate Member",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            dao.addMember(member);

            JOptionPane.showMessageDialog(
                    this,
                    "Member registered successfully!\nBMI: "
                            + String.format("%.2f", bmi),
                    "FitZone",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadMembers();

        } catch (NumberFormatException ex) {
            showWarning(
                    "Please enter valid numbers for age, weight and height."
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not add the member. Please check the database connection.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ================= UPDATE MEMBER =================

    private void showUpdateMemberDialog(int memberId) {

        int row = -1;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (Integer.parseInt(
                    tableModel.getValueAt(i, 0).toString()
            ) == memberId) {
                row = i;
                break;
            }
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Member not found in the current table."
            );
            return;
        }

        JTextField nameField = new JTextField(
                tableModel.getValueAt(row, 1).toString()
        );

        JTextField ageField = new JTextField(
                tableModel.getValueAt(row, 2).toString()
        );

        JTextField phoneField = new JTextField(
                tableModel.getValueAt(row, 4).toString()
        );

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(
                12, 12, 12, 12
        ));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        int choice = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Update Member Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());

            if (name.isEmpty()) {
                showWarning("Name cannot be empty.");
                return;
            }

            if (age < 16 || age > 100) {
                showWarning("Age must be between 16 and 100.");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                showWarning(
                        "Phone number must contain exactly 10 digits."
                );
                return;
            }

            boolean updated = MemberDAO.updateMember(
                    memberId,
                    name,
                    age,
                    phone,
                    Double.parseDouble(
                            tableModel.getValueAt(row, 6).toString()
                    ),
                    Double.parseDouble(
                            tableModel.getValueAt(row, 7).toString()
                    ),
                    tableModel.getValueAt(row, 8).toString(),
                    tableModel.getValueAt(row, 9).toString()
            );

            if (updated) {
                JOptionPane.showMessageDialog(
                        this,
                        "Member updated successfully!",
                        "FitZone",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Member was not updated.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            showWarning("Please enter a valid age.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update member details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    // ================= DELETE MEMBER =================

    private void deleteMember() {

        int selectedRow = memberTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member from the table to delete.",
                    "No Member Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = memberTable.convertRowIndexToModel(
                selectedRow
        );

        int memberId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString()
        );

        String memberName = tableModel.getValueAt(
                modelRow, 1
        ).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete member: " + memberName + " (ID: "
                        + memberId + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = MemberDAO.deleteMember(memberId);

            if (deleted) {
                JOptionPane.showMessageDialog(
                        this,
                        "Member deleted successfully!",
                        "FitZone",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Member was not found or could not be deleted.",
                        "Delete Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    // ================= WARNING MESSAGE =================

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Check Input",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
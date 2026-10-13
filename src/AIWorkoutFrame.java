import java.awt.*;
import javax.swing.*;

public class AIWorkoutFrame extends JFrame {

    private JComboBox<String> memberBox;
    private java.util.List<Member> members;

    private JTextField ageField;
    private JTextField weightField;
    private JTextField heightField;

    private JComboBox<String> fitnessBox;
    private JComboBox<String> goalBox;
    private JComboBox<String> daysBox;

    private JTextArea resultArea;
    private JButton saveButton;

    private String generatedPlan;
    private int generatedAge;
    private double generatedWeight;
    private double generatedHeight;
    private String generatedFitness;
    private String generatedGoal;
    private int generatedDays;
    private int generatedMemberId = -1;
    private String generatedGender;

    private final Color DARK = new Color(31, 38, 45);
    private final Color GREEN = new Color(170, 230, 60);
    private final Color BACKGROUND = new Color(245, 247, 249);
    private final Color TEXT = new Color(45, 52, 60);

    public AIWorkoutFrame() {

         setTitle("FitZone - AI Workout Recommendation");
         setSize(1000, 850);
         setMinimumSize(new Dimension(900, 750));
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK);
        header.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JLabel title = new JLabel("AI Workout Recommendation");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Personalized workout plans based on fitness goals"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(205, 213, 220));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(7));
        heading.add(subtitle);

        JLabel logo = new JLabel("FITZONE");
        logo.setFont(new Font("Arial", Font.BOLD, 19));
        logo.setForeground(GREEN);

        header.add(heading, BorderLayout.WEST);
        header.add(logo, BorderLayout.EAST);

        // Input card
        JPanel inputCard = new JPanel(new GridBagLayout());
        inputCard.setBackground(Color.WHITE);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 234)),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Member Fitness Details");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        formTitle.setForeground(DARK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputCard.add(formTitle, gbc);

        memberBox = new JComboBox<String>();
        ageField = new JTextField();
        weightField = new JTextField();
        heightField = new JTextField();

        fitnessBox = new JComboBox<String>(
                new String[]{"Beginner", "Intermediate", "Advanced"}
        );

        goalBox = new JComboBox<String>(
                new String[]{"Muscle Gain", "Weight Loss", "Fitness"}
        );

        daysBox = new JComboBox<String>(
                new String[]{"3", "4", "5"}
        );

        styleInput(memberBox);
        styleInput(ageField);
        styleInput(weightField);
        styleInput(heightField);
        styleInput(fitnessBox);
        styleInput(goalBox);
        styleInput(daysBox);

        addFormRow(inputCard, gbc, 1, "Select Member", memberBox);
        addFormRow(inputCard, gbc, 2, "Age", ageField);
        addFormRow(inputCard, gbc, 3, "Weight (kg)", weightField);
        addFormRow(inputCard, gbc, 4, "Height (cm)", heightField);
        addFormRow(inputCard, gbc, 5, "Fitness Level", fitnessBox);
        addFormRow(inputCard, gbc, 6, "Fitness Goal", goalBox);
        addFormRow(inputCard, gbc, 7, "Days Per Week", daysBox);

        // Buttons
        JButton generateButton = makeButton(
                "Generate Workout", new Color(95, 150, 30)
        );

        saveButton = makeButton("Save Recommendation", DARK);
        saveButton.setEnabled(false);

        JButton closeButton = makeButton("Close", new Color(95, 102, 110));

        JPanel actionPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        actionPanel.setBackground(BACKGROUND);
        actionPanel.add(generateButton);
        actionPanel.add(saveButton);
        actionPanel.add(closeButton);

        // Results card
        JPanel resultCard = new JPanel(new BorderLayout(8, 10));
        resultCard.setBackground(Color.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 234)),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JLabel resultTitle = new JLabel("Your Recommended Workout Plan");
        resultTitle.setFont(new Font("Arial", Font.BOLD, 18));
        resultTitle.setForeground(DARK);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        resultArea.setMargin(new Insets(16, 18, 16, 18));
        resultArea.setForeground(TEXT);
        resultArea.setBackground(new Color(250, 251, 252));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setMargin(new Insets(12, 12, 12, 12));
        resultArea.setText(
                "Welcome to FitZone!\n\n"
                + "1. Select a registered member.\n"
                + "2. Check the fitness details.\n"
                + "3. Choose a fitness goal and weekly schedule.\n"
                + "4. Click Generate Workout to see the recommendation.\n"
                + "5. Review the plan and click Save Recommendation."
        );

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(new Color(230, 234, 238))
        );

        resultCard.add(resultTitle, BorderLayout.NORTH);
        resultCard.add(scrollPane, BorderLayout.CENTER);

        // Main content
       // Main content - give the workout result more space
          JPanel content = new JPanel(new BorderLayout(15, 15));
          content.setBackground(BACKGROUND);
          content.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

           inputCard.setPreferredSize(new Dimension(820, 330));
           content.add(inputCard, BorderLayout.NORTH);

            resultCard.setMinimumSize(new Dimension(820, 300));
             content.add(resultCard, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BACKGROUND);
        bottom.add(actionPanel, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);

        // Load members before registering the selection listener.
        loadMembers();

        memberBox.addActionListener(e -> loadSelectedMember());

        generateButton.addActionListener(e -> generateWorkout());
        saveButton.addActionListener(e -> saveRecommendation());
        closeButton.addActionListener(e -> dispose());

        if (memberBox.getItemCount() > 0) {
            memberBox.setSelectedIndex(0);
            loadSelectedMember();
        }

        setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            Component field) {

        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.35;
        panel.add(makeLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(field, gbc);
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT);
        return label;
    }

    private void styleInput(JComponent input) {
        input.setFont(new Font("Arial", Font.PLAIN, 14));
        input.setPreferredSize(new Dimension(230, 34));
        input.setBackground(Color.WHITE);
        input.setForeground(TEXT);
    }

    private JButton makeButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(11, 16, 11, 16));
        return button;
    }

    private void loadMembers() {

        try {
            MemberDAO dao = new MemberDAO();
            members = dao.getAllMembers();

            memberBox.removeAllItems();

            if (members != null) {
                for (Member member : members) {
                    memberBox.addItem(
                            member.getId() + " - " + member.getName()
                    );
                }
            }

            if (members == null || members.isEmpty()) {
                memberBox.addItem("No members available");
                memberBox.setEnabled(false);

                JOptionPane.showMessageDialog(
                        this,
                        "No registered members were found. Add a member first.",
                        "FitZone",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load members from the database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void loadSelectedMember() {

        int index = memberBox.getSelectedIndex();

        if (members == null || index < 0 || index >= members.size()) {
            return;
        }

        Member member = members.get(index);

        ageField.setText(String.valueOf(member.getAge()));
        weightField.setText(String.valueOf(member.getWeight()));
        heightField.setText(String.valueOf(member.getHeight()));

        if (member.getFitnessLevel() != null) {
            fitnessBox.setSelectedItem(member.getFitnessLevel());
        }

        if (member.getGoal() != null) {
            goalBox.setSelectedItem(member.getGoal());
        }

        generatedPlan = null;
        saveButton.setEnabled(false);
    }

    private void generateWorkout() {

        try {
            int index = memberBox.getSelectedIndex();

            if (members == null || index < 0 || index >= members.size()) {
                JOptionPane.showMessageDialog(
                        this, "Please select a registered member."
                );
                return;
            }

            int age = Integer.parseInt(ageField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            double height = Double.parseDouble(heightField.getText().trim());

            if (age < 16 || age > 100) {
                JOptionPane.showMessageDialog(
                        this, "Age must be between 16 and 100."
                );
                return;
            }

            if (weight < 30 || weight > 250) {
                JOptionPane.showMessageDialog(
                        this, "Weight must be between 30 and 250 kg."
                );
                return;
            }

            if (height < 100 || height > 250) {
                JOptionPane.showMessageDialog(
                        this, "Height must be between 100 and 250 cm."
                );
                return;
            }

            Member selectedMember = members.get(index);

            String fitness = fitnessBox.getSelectedItem().toString();
            String goal = goalBox.getSelectedItem().toString();
            int days = Integer.parseInt(daysBox.getSelectedItem().toString());

            double heightMeter = height / 100.0;
            double bmi = weight / (heightMeter * heightMeter);

            String bmiCategory;

            if (bmi < 18.5) {
                bmiCategory = "Underweight";
            } else if (bmi < 25) {
                bmiCategory = "Normal";
            } else if (bmi < 30) {
                bmiCategory = "Overweight";
            } else {
                bmiCategory = "Obese";
            }

            StringBuilder plan = new StringBuilder();

            plan.append("FITZONE | PERSONAL WORKOUT PLAN\n");
            plan.append("========================================\n\n");
            plan.append("Member: ").append(selectedMember.getName()).append("\n");
            plan.append("Age: ").append(age).append("\n");
            plan.append("Weight: ").append(weight).append(" kg\n");
            plan.append("Height: ").append(height).append(" cm\n");
            plan.append("Fitness Level: ").append(fitness).append("\n");
            plan.append("Goal: ").append(goal).append("\n");
            plan.append("Training Days: ").append(days).append(" days/week\n");
            plan.append("BMI: ").append(String.format("%.2f", bmi)).append("\n");
            plan.append("BMI Category: ").append(bmiCategory).append("\n\n");

            if (goal.equals("Muscle Gain")) {

                plan.append("RECOMMENDATION: STRENGTH TRAINING\n\n");

                if (days == 3) {
                    plan.append("MONDAY     - Chest + Triceps\n");
                    plan.append("WEDNESDAY  - Back + Biceps\n");
                    plan.append("FRIDAY     - Legs + Shoulders\n");
                } else if (days == 4) {
                    plan.append("MONDAY     - Chest + Triceps\n");
                    plan.append("TUESDAY    - Back + Biceps\n");
                    plan.append("THURSDAY   - Legs\n");
                    plan.append("FRIDAY     - Shoulders + Abs\n");
                } else {
                    plan.append("MONDAY     - Chest + Triceps\n");
                    plan.append("TUESDAY    - Back + Biceps\n");
                    plan.append("WEDNESDAY  - Legs\n");
                    plan.append("THURSDAY   - Shoulders + Abs\n");
                    plan.append("FRIDAY     - Full Body\n");
                }

            } else if (goal.equals("Weight Loss")) {

                plan.append("RECOMMENDATION: CARDIO + FULL BODY\n\n");

                if (days == 3) {
                    plan.append("MONDAY     - Full Body + Cardio\n");
                    plan.append("WEDNESDAY  - Cardio + Core\n");
                    plan.append("FRIDAY     - Full Body + Cardio\n");
                } else if (days == 4) {
                    plan.append("MONDAY     - Full Body + Cardio\n");
                    plan.append("TUESDAY    - Cardio + Core\n");
                    plan.append("THURSDAY   - Full Body + Cardio\n");
                    plan.append("FRIDAY     - Cardio + Abs\n");
                } else {
                    plan.append("MONDAY     - Full Body + Cardio\n");
                    plan.append("TUESDAY    - Cardio + Core\n");
                    plan.append("WEDNESDAY  - Full Body\n");
                    plan.append("THURSDAY   - Cardio + Abs\n");
                    plan.append("FRIDAY     - Full Body + Cardio\n");
                }

            } else {

                plan.append("RECOMMENDATION: GENERAL FITNESS\n\n");

                if (days == 3) {
                    plan.append("MONDAY     - Full Body\n");
                    plan.append("WEDNESDAY  - Cardio + Core\n");
                    plan.append("FRIDAY     - Full Body + Cardio\n");
                } else if (days == 4) {
                    plan.append("MONDAY     - Full Body\n");
                    plan.append("TUESDAY    - Cardio + Core\n");
                    plan.append("THURSDAY   - Full Body\n");
                    plan.append("FRIDAY     - Cardio + Abs\n");
                } else {
                    plan.append("MONDAY     - Full Body\n");
                    plan.append("TUESDAY    - Cardio + Core\n");
                    plan.append("WEDNESDAY  - Full Body\n");
                    plan.append("THURSDAY   - Cardio\n");
                    plan.append("FRIDAY     - Full Body + Abs\n");
                }
            }

            plan.append("\nREST AND RECOVERY\n");
            plan.append("----------------------------------------\n");
            plan.append("Take rest days between demanding sessions.\n\n");

            plan.append("FITNESS TIPS\n");
            plan.append("----------------------------------------\n");
            plan.append("Warm up for 5-10 minutes before exercise.\n");
            plan.append("Use proper exercise technique.\n");
            plan.append("Stay hydrated and eat balanced meals.\n");
            plan.append("Adjust intensity to your ability and recovery.\n");
            plan.append("Consult a qualified professional if unsure.");

            generatedPlan = plan.toString();
            generatedAge = age;
            generatedWeight = weight;
            generatedHeight = height;
            generatedFitness = fitness;
            generatedGoal = goal;
            generatedDays = days;
            generatedMemberId = selectedMember.getId();
            generatedGender = selectedMember.getGender();

            resultArea.setText(generatedPlan);
            resultArea.setCaretPosition(0);

            saveButton.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers for age, weight and height.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate the workout plan.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void saveRecommendation() {

        if (generatedPlan == null || generatedMemberId < 0) {
            JOptionPane.showMessageDialog(
                    this, "Generate a workout plan before saving."
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Save this workout recommendation to the database?",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean saved = WorkoutRecommendationDAO.saveRecommendation(
                generatedMemberId,
                generatedAge,
                generatedGender,
                generatedWeight,
                generatedHeight,
                generatedFitness,
                generatedGoal,
                generatedDays,
                generatedPlan
        );

        if (saved) {
            JOptionPane.showMessageDialog(
                    this,
                    "Workout recommendation saved successfully!",
                    "FitZone",
                    JOptionPane.INFORMATION_MESSAGE
            );

            saveButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "The workout was generated, but could not be saved. "
                    + "Please check the database connection.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
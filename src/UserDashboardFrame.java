import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserDashboardFrame extends JFrame {

    private final Member member;
    private final CardLayout pageLayout = new CardLayout();
    private final JPanel pageContainer = new JPanel(pageLayout);

    private final Color NAVY = new Color(18, 29, 48);
    private final Color NAVY_LIGHT = new Color(29, 44, 68);
    private final Color GREEN = new Color(45, 205, 125);
    private final Color BG = new Color(241, 245, 249);
    private final Color TEXT = new Color(39, 52, 72);
    private final Color MUTED = new Color(115, 129, 148);
    private final Color WHITE = Color.WHITE;

    // Membership data
    private JLabel dashPlan = new JLabel("Loading...");
    private JLabel dashDays = new JLabel("Loading...");
    private JLabel dashPayment = new JLabel("Loading...");

    private JLabel planValue = new JLabel("Loading...");
    private JLabel startValue = new JLabel("Loading...");
    private JLabel expiryValue = new JLabel("Loading...");
    private JLabel daysValue = new JLabel("Loading...");
    private JLabel paymentValue = new JLabel("Loading...");
    private JLabel membershipStatus = new JLabel("Loading...");

    // BMI page
    private JTextField weightField;
    private JTextField heightField;
    private JLabel bmiResult = new JLabel("Enter your details and calculate BMI.");

    
  // Workout page
private JComboBox<String> fitnessLevelCombo;
private JComboBox<String> fitnessGoalCombo;
private JComboBox<Integer> workoutDays;
private JTextArea workoutResult;

    public UserDashboardFrame(Member member) {

        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }

        this.member = member;

        setTitle("FitZone - Member Portal");
        setSize(1150, 760);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        root.add(createSidebar(), BorderLayout.WEST);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setBackground(BG);
        rightSide.add(createTopBar(), BorderLayout.NORTH);

        // Create all pages before loading membership data.
        pageContainer.setBackground(BG);
        pageContainer.add(createDashboardPage(), "Dashboard");
        pageContainer.add(createProfilePage(), "Profile");
        pageContainer.add(createMembershipPage(), "Membership");
        pageContainer.add(createBMIPage(), "BMI");
        pageContainer.add(createWorkoutPage(), "Workout");

        rightSide.add(pageContainer, BorderLayout.CENTER);
        root.add(rightSide, BorderLayout.CENTER);

        setContentPane(root);

        // All membership labels now exist before this call.
        refreshMembership();
        setVisible(true);
    }

    // ---------------- SIDEBAR ----------------

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBorder(new EmptyBorder(25, 16, 22, 16));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("FITZONE");
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(GREEN);

        JLabel sub = new JLabel("MEMBER FITNESS PORTAL");
        sub.setFont(new Font("Arial", Font.PLAIN, 10));
        sub.setForeground(new Color(180, 194, 211));

        top.add(logo);
        top.add(Box.createVerticalStrut(6));
        top.add(sub);
        top.add(Box.createVerticalStrut(42));

        JLabel menuHeading = new JLabel("MAIN MENU");
        menuHeading.setForeground(new Color(145, 161, 181));
        menuHeading.setFont(new Font("Arial", Font.BOLD, 11));
        top.add(menuHeading);

        sidebar.add(top, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(15, 0, 0, 0));

        addNavButton(menu, "Dashboard", "Dashboard");
        addNavButton(menu, "My Profile", "Profile");
        addNavButton(menu, "Membership", "Membership");
        addNavButton(menu, "BMI Calculator", "BMI");
        addNavButton(menu, "Workout Plan", "Workout");

        sidebar.add(menu, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel loggedIn = new JLabel("LOGGED IN AS");
        loggedIn.setFont(new Font("Arial", Font.BOLD, 10));
        loggedIn.setForeground(new Color(145, 161, 181));

        JLabel name = new JLabel(member.getName());
        name.setFont(new Font("Arial", Font.BOLD, 14));
        name.setForeground(WHITE);

        JButton logout = new JButton("Logout");
        styleButton(logout, new Color(49, 65, 88), WHITE);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);

        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        bottom.add(loggedIn);
        bottom.add(Box.createVerticalStrut(6));
        bottom.add(name);
        bottom.add(Box.createVerticalStrut(16));
        bottom.add(logout);

        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private void addNavButton(
            JPanel menu, String title, String pageName) {

        JButton button = new JButton(title);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(new Color(225, 233, 243));
        button.setBackground(NAVY);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(14, 12, 14, 8));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            pageLayout.show(pageContainer, pageName);

            if ("Membership".equals(pageName)) {
                refreshMembership();
            }
        });

        menu.add(button);
        menu.add(Box.createVerticalStrut(5));
    }

    // ---------------- TOP BAR ----------------

    private JPanel createTopBar() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(WHITE);
        top.setBorder(new EmptyBorder(16, 25, 16, 25));

        JLabel heading = new JLabel("FITNESS MANAGEMENT SYSTEM");
        heading.setFont(new Font("Arial", Font.BOLD, 14));
        heading.setForeground(TEXT);

        JLabel user = new JLabel("Member: " + member.getName());
        user.setFont(new Font("Arial", Font.PLAIN, 12));
        user.setForeground(MUTED);

        top.add(heading, BorderLayout.WEST);
        top.add(user, BorderLayout.EAST);

        return top;
    }

    // ---------------- DASHBOARD PAGE ----------------

    private JPanel createDashboardPage() {

        JPanel page = createPage();
        page.setLayout(new BorderLayout(0, 20));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = pageTitle("Welcome back, " + member.getName());
        JLabel subtitle = pageSubtitle(
                "Your fitness journey starts here. Track your progress "
                + "and manage your membership.");

        heading.add(title);
        heading.add(Box.createVerticalStrut(7));
        heading.add(subtitle);

        page.add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 20));
        center.setOpaque(false);

        JPanel summaries = new JPanel(new GridLayout(1, 3, 15, 0));
        summaries.setOpaque(false);

        summaries.add(summaryCard("MEMBERSHIP PLAN", dashPlan, GREEN));
        summaries.add(summaryCard("DAYS REMAINING", dashDays,
                new Color(65, 125, 230)));
        summaries.add(summaryCard("PAYMENT STATUS", dashPayment,
                new Color(221, 153, 30)));

        center.add(summaries, BorderLayout.NORTH);

        JPanel shortcuts = new JPanel(new GridLayout(2, 2, 15, 15));
        shortcuts.setOpaque(false);

        shortcuts.add(actionCard(
                "My Profile",
                "View your personal and fitness information",
                () -> pageLayout.show(pageContainer, "Profile")));

        shortcuts.add(actionCard(
                "Membership",
                "Check plan details and expiry date",
                () -> {
                    refreshMembership();
                    pageLayout.show(pageContainer, "Membership");
                }));

        shortcuts.add(actionCard(
                "BMI Calculator",
                "Calculate your body mass index",
                () -> pageLayout.show(pageContainer, "BMI")));

        shortcuts.add(actionCard(
                "Workout Plan",
                "Create a weekly workout schedule",
                () -> pageLayout.show(pageContainer, "Workout")));

       
center.add(shortcuts, BorderLayout.CENTER);

page.add(center, BorderLayout.CENTER);

return page;

    }

    private JPanel summaryCard(
            String heading, JLabel value, Color accent) {

        JPanel card = whiteCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 18, 20, 18));

        JLabel label = new JLabel(heading);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setForeground(MUTED);

        value.setFont(new Font("Arial", Font.BOLD, 21));
        value.setForeground(accent);

        card.add(label);
        card.add(Box.createVerticalStrut(17));
        card.add(value);

        return card;
    }

    private JPanel actionCard(
            String title, String description, Runnable action) {

        JPanel card = whiteCard();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel name = new JLabel(title);
        name.setFont(new Font("Arial", Font.BOLD, 18));
        name.setForeground(TEXT);

        JLabel desc = new JLabel(
                "<html><body style='width:190px'>"
                + description + "</body></html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 12));
        desc.setForeground(MUTED);

        JButton open = new JButton("Open Section  >");
        styleButton(open, GREEN, NAVY);
        open.setAlignmentX(Component.LEFT_ALIGNMENT);
        open.addActionListener(e -> action.run());

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(name);
        info.add(Box.createVerticalStrut(9));
        info.add(desc);

        card.add(info, BorderLayout.CENTER);
        card.add(open, BorderLayout.SOUTH);

        return card;
    }

    // ---------------- PROFILE PAGE ----------------

    private JPanel createProfilePage() {

        JPanel page = createPage();
        page.setLayout(new BorderLayout(0, 20));

        page.add(pageHeading(
                "My Personal Profile",
                "Your registered information and fitness details."),
                BorderLayout.NORTH);

        JPanel profile = whiteCard();
        profile.setLayout(new BorderLayout(0, 20));
        profile.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel banner = new JPanel(new BorderLayout(15, 0));
        banner.setBackground(NAVY);
        banner.setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel avatar = new JLabel(
                initials(member.getName()), SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(GREEN);
        avatar.setForeground(NAVY);
        avatar.setFont(new Font("Arial", Font.BOLD, 24));
        avatar.setPreferredSize(new Dimension(65, 65));

        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(safe(member.getName()));
        name.setForeground(WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 23));

        JLabel id = new JLabel("Member ID: " + member.getId());
        id.setForeground(new Color(191, 205, 221));
        id.setFont(new Font("Arial", Font.PLAIN, 13));

        namePanel.add(name);
        namePanel.add(Box.createVerticalStrut(7));
        namePanel.add(id);

        banner.add(avatar, BorderLayout.WEST);
        banner.add(namePanel, BorderLayout.CENTER);

        JPanel details = new JPanel(new GridLayout(0, 2, 20, 18));
        details.setOpaque(false);
        details.setBorder(new EmptyBorder(8, 0, 8, 0));

        addInfo(details, "FULL NAME", member.getName());
        addInfo(details, "MEMBER ID", String.valueOf(member.getId()));
        addInfo(details, "AGE", member.getAge() + " years");
        addInfo(details, "GENDER", member.getGender());
        addInfo(details, "PHONE NUMBER", member.getPhone());
        addInfo(details, "EMAIL ADDRESS", member.getEmail());
        addInfo(details, "WEIGHT", member.getWeight() + " kg");
        addInfo(details, "HEIGHT", member.getHeight() + " cm");
        addInfo(details, "FITNESS LEVEL", member.getFitnessLevel());
        addInfo(details, "FITNESS GOAL", member.getGoal());

        profile.add(banner, BorderLayout.NORTH);
        profile.add(details, BorderLayout.CENTER);

        page.add(profile, BorderLayout.CENTER);
        
JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
bottomBar.setOpaque(false);
bottomBar.add(createBackButton());
page.add(bottomBar, BorderLayout.SOUTH);

        return page;
    }

    private void addInfo(JPanel panel, String title, String value) {

        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));

        JLabel key = new JLabel(title);
        key.setFont(new Font("Arial", Font.BOLD, 11));
        key.setForeground(MUTED);

        JLabel val = new JLabel(safe(value));
        val.setFont(new Font("Arial", Font.BOLD, 15));
        val.setForeground(TEXT);

        item.add(key);
        item.add(Box.createVerticalStrut(7));
        item.add(val);

        panel.add(item);
    }

    // ---------------- MEMBERSHIP PAGE ----------------

    private JPanel createMembershipPage() {

        JPanel page = createPage();
        page.setLayout(new BorderLayout(0, 20));

        page.add(pageHeading(
                "My Membership",
                "View your membership plan, expiry date and payment details."),
                BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);

        JPanel banner = new JPanel(new BorderLayout(15, 0));
        banner.setBackground(NAVY);
        banner.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel bannerText = new JPanel();
        bannerText.setOpaque(false);
        bannerText.setLayout(new BoxLayout(bannerText, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("YOUR CURRENT PLAN");
        label.setForeground(GREEN);
        label.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel plan = new JLabel("Membership Overview");
        plan.setForeground(WHITE);
        plan.setFont(new Font("Arial", Font.BOLD, 25));

        bannerText.add(label);
        bannerText.add(Box.createVerticalStrut(10));
        bannerText.add(plan);

        banner.add(bannerText, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Details");
        styleButton(refresh, GREEN, NAVY);
        refresh.addActionListener(e -> refreshMembership());
        banner.add(refresh, BorderLayout.EAST);

        JPanel details = whiteCard();
        details.setLayout(new GridLayout(0, 2, 20, 20));
        details.setBorder(new EmptyBorder(25, 25, 25, 25));

        addMembershipInfo(details, "MEMBERSHIP PLAN", planValue);
        addMembershipInfo(details, "START DATE", startValue);
        addMembershipInfo(details, "EXPIRY DATE", expiryValue);
        addMembershipInfo(details, "DAYS REMAINING", daysValue);
        addMembershipInfo(details, "PAYMENT STATUS", paymentValue);
        addMembershipInfo(details, "MEMBERSHIP STATUS", membershipStatus);

        body.add(banner, BorderLayout.NORTH);
        body.add(details, BorderLayout.CENTER);

        page.add(body, BorderLayout.CENTER);
        
JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
bottomBar.setOpaque(false);
bottomBar.add(createBackButton());
page.add(bottomBar, BorderLayout.SOUTH);

        return page;
    }

    private void addMembershipInfo(
            JPanel panel, String heading, JLabel value) {

        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(heading);
        title.setFont(new Font("Arial", Font.BOLD, 11));
        title.setForeground(MUTED);

        value.setFont(new Font("Arial", Font.BOLD, 17));
        value.setForeground(TEXT);

        item.add(title);
        item.add(Box.createVerticalStrut(9));
        item.add(value);

        panel.add(item);
    }

    private void refreshMembership() {

        try {
            MembershipDAO.MembershipInfo info =
                    MembershipDAO.getLatestMembership(member.getId());

            if (info == null) {
                setMembershipValues(
                        "No record", "Not available", "Not available",
                        "N/A", "N/A", "No membership found");
                return;
            }

            String plan = safe(info.plan);
            String start = safe(info.startDate);
            String expiry = safe(info.endDate);
            String payment = safe(info.paymentStatus);

            String remaining = "Unknown";
            String status = "Expiry date unavailable";

            if (info.endDate != null
                    && !info.endDate.trim().isEmpty()) {

                String dateText = info.endDate.trim();

                if (dateText.length() >= 10) {
                    dateText = dateText.substring(0, 10);
                }

                LocalDate expiryDate = LocalDate.parse(dateText);
                long days = ChronoUnit.DAYS.between(
                        LocalDate.now(), expiryDate);

                if (days < 0) {
                    remaining = "Expired";
                    status = "Expired";
                } else if (days == 0) {
                    remaining = "0 days";
                    status = "Expires today";
                } else {
                    remaining = days + " days";
                    status = "Active";
                }
            }

            setMembershipValues(
                    plan, start, expiry, remaining, payment, status);

        } catch (Exception ex) {
            setMembershipValues(
                    "Unavailable", "Unavailable", "Unavailable",
                    "Unavailable", "Unavailable", "Could not load data");

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load membership details.\n"
                    + ex.getMessage(),
                    "Membership Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setMembershipValues(
            String plan, String start, String expiry,
            String days, String payment, String status) {

        planValue.setText(plan);
        startValue.setText(start);
        expiryValue.setText(expiry);
        daysValue.setText(days);
        paymentValue.setText(payment);
        membershipStatus.setText(status);

        dashPlan.setText(plan);
        dashDays.setText(days);
        dashPayment.setText(payment);
    }

    // ---------------- BMI PAGE ----------------

    private JPanel createBMIPage() {

        JPanel page = createPage();
        page.setLayout(new BorderLayout(0, 20));

        page.add(pageHeading(
                "BMI Calculator",
                "Enter your height and weight to calculate your BMI."),
                BorderLayout.NORTH);

        JPanel card = whiteCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 30, 28, 30));

        JLabel heading = new JLabel("Calculate Your Body Mass Index");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(TEXT);

        JLabel description = pageSubtitle(
                "Use your current measurements for this calculation.");

        JPanel form = new JPanel(new GridLayout(0, 2, 18, 18));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(25, 0, 20, 0));

        weightField = new JTextField(
                String.valueOf(member.getWeight()));
        heightField = new JTextField(
                String.valueOf(member.getHeight()));

        styleInput(weightField);
        styleInput(heightField);

        form.add(formLabel("Weight (kg)"));
        form.add(weightField);
        form.add(formLabel("Height (cm)"));
        form.add(heightField);

        JButton calculate = new JButton("Calculate BMI");
        styleButton(calculate, GREEN, NAVY);
        calculate.setPreferredSize(new Dimension(180, 45));
        calculate.setAlignmentX(Component.LEFT_ALIGNMENT);
        calculate.addActionListener(e -> calculateBMI());

        JPanel resultCard = new JPanel(new BorderLayout());
        resultCard.setBackground(new Color(231, 249, 239));
        resultCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        bmiResult.setFont(new Font("Arial", Font.BOLD, 17));
        bmiResult.setForeground(new Color(25, 115, 75));

        resultCard.add(bmiResult, BorderLayout.CENTER);

        card.add(heading);
        card.add(Box.createVerticalStrut(8));
        card.add(description);
        card.add(form);
        card.add(calculate);
        card.add(Box.createVerticalStrut(20));
        card.add(resultCard);

        page.add(card, BorderLayout.CENTER);
        
JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
bottomBar.setOpaque(false);
bottomBar.add(createBackButton());
page.add(bottomBar, BorderLayout.SOUTH);

        return page;
    }

    private void calculateBMI() {

        try {
            double weight = Double.parseDouble(
                    weightField.getText().trim());

            double height = Double.parseDouble(
                    heightField.getText().trim());

            if (!Double.isFinite(weight)
                    || !Double.isFinite(height)
                    || weight < 20 || weight > 350
                    || height < 80 || height > 250) {

                JOptionPane.showMessageDialog(
                        this,
                        "Weight must be 20-350 kg and height "
                        + "must be 80-250 cm.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double metres = height / 100.0;
            double bmi = weight / (metres * metres);

            String category;

            if (bmi < 18.5) {
                category = "Below the usual adult BMI range";
            } else if (bmi < 25) {
                category = "Within the usual adult BMI range";
            } else if (bmi < 30) {
                category = "Above the usual adult BMI range";
            } else {
                category = "High adult BMI range";
            }

            bmiResult.setText(String.format(
                    "<html>Your BMI: %.2f<br>%s"
                    + "<br><br><font size='3'>BMI is a general "
                    + "screening measure, not a diagnosis.</font></html>",
                    bmi, category));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers for weight and height.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // ---------------- WORKOUT PAGE ----------------

    
private JPanel createWorkoutPage() {

    JPanel page = createPage();
    page.setLayout(new BorderLayout(0, 20));

    page.add(pageHeading(
            "Workout Recommendation",
            "Create a weekly workout schedule for your fitness goal."),
            BorderLayout.NORTH);

    JPanel card = whiteCard();
    card.setLayout(new BorderLayout(0, 18));
    card.setBorder(new EmptyBorder(25, 25, 25, 25));

    JPanel details = new JPanel(new GridLayout(0, 2, 15, 15));
    details.setOpaque(false);

    // Member Name
    details.add(formLabel("Member Name"));

    JTextField name = new JTextField(member.getName());
    name.setEditable(false);
    styleInput(name);
    details.add(name);

    // Fitness Level Dropdown
    details.add(formLabel("Fitness Level"));

    String[] levels = {
        "Beginner",
        "Intermediate",
        "Advanced"
    };

    fitnessLevelCombo = new JComboBox<>(levels);
    fitnessLevelCombo.setFont(new Font("Arial", Font.PLAIN, 14));
    fitnessLevelCombo.setSelectedItem("Beginner");
    details.add(fitnessLevelCombo);

    // Fitness Goal Dropdown
    details.add(formLabel("Fitness Goal"));

    String[] goals = {
        "Muscle Gain",
        "Weight Loss",
        "General Fitness"
    };

    fitnessGoalCombo = new JComboBox<>(goals);
    fitnessGoalCombo.setFont(new Font("Arial", Font.PLAIN, 14));
    fitnessGoalCombo.setSelectedItem("Muscle Gain");
    details.add(fitnessGoalCombo);

    // Workout Days
    details.add(formLabel("Workout Days Per Week"));

    Integer[] dayOptions = {1, 2, 3, 4, 5, 6, 7};

    workoutDays = new JComboBox<>(dayOptions);
    workoutDays.setSelectedItem(4);
    details.add(workoutDays);

    // Generate Button
    JButton generate = new JButton("Generate Workout Plan");
    styleButton(generate, GREEN, NAVY);
    generate.setPreferredSize(new Dimension(230, 43));
    generate.addActionListener(e -> generateWorkout());

    // Workout Result Area
    workoutResult = new JTextArea(10, 35);
    workoutResult.setEditable(false);
    workoutResult.setLineWrap(true);
    workoutResult.setWrapStyleWord(true);
    workoutResult.setFont(new Font("Arial", Font.PLAIN, 14));
    workoutResult.setForeground(TEXT);
    workoutResult.setBackground(new Color(248, 250, 252));
    workoutResult.setBorder(new EmptyBorder(15, 15, 15, 15));
    workoutResult.setText(
            "Select your fitness level, goal and workout days, "
            + "then click Generate Workout Plan.");

    card.add(details, BorderLayout.NORTH);
    card.add(new JScrollPane(workoutResult), BorderLayout.CENTER);
    card.add(generate, BorderLayout.SOUTH);

    page.add(card, BorderLayout.CENTER);

    JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottomBar.setOpaque(false);
    bottomBar.add(createBackButton());
    page.add(bottomBar, BorderLayout.SOUTH);

    return page;
}

   
private void generateWorkout() {

    int days = (Integer) workoutDays.getSelectedItem();

    String level = (String) fitnessLevelCombo.getSelectedItem();
    String goal = (String) fitnessGoalCombo.getSelectedItem();

    String[] schedule;

    if ("Muscle Gain".equals(goal)) {

        schedule = new String[] {
            "Day 1: Chest and Triceps - Strength Training",
            "Day 2: Back and Biceps - Strength Training",
            "Day 3: Legs and Core",
            "Day 4: Shoulders and Abs",
            "Day 5: Full Body Strength Training",
            "Day 6: Light Cardio and Mobility",
            "Day 7: Full Body Workout"
        };

    } else if ("Weight Loss".equals(goal)) {

        schedule = new String[] {
            "Day 1: Cardio and Full Body Exercises",
            "Day 2: Upper Body Strength Training",
            "Day 3: Brisk Walking and Core Exercises",
            "Day 4: Lower Body Training",
            "Day 5: Cardio and Bodyweight Exercises",
            "Day 6: Light Cardio and Stretching",
            "Day 7: Full Body Cardio"
        };

    } else {

        schedule = new String[] {
            "Day 1: Full Body Workout",
            "Day 2: Cardio and Walking",
            "Day 3: Upper Body Exercises",
            "Day 4: Lower Body Exercises",
            "Day 5: Core and Balance Training",
            "Day 6: Light Cardio and Stretching",
            "Day 7: Full Body Fitness"
        };
    }

    StringBuilder plan = new StringBuilder();

    plan.append("YOUR WEEKLY WORKOUT PLAN\n\n");
    plan.append("Member: ").append(member.getName()).append("\n");
    plan.append("Fitness Level: ").append(level).append("\n");
    plan.append("Fitness Goal: ").append(goal).append("\n");
    plan.append("Selected Days: ").append(days).append("\n\n");

    plan.append("WORKOUT SCHEDULE\n\n");

    for (int i = 0; i < days; i++) {
        plan.append(schedule[i]).append("\n");
    }

    plan.append("\nFITNESS TIPS\n\n");

    if ("Beginner".equals(level)) {
        plan.append("Start with light exercises and learn the correct form.\n");
    } else if ("Intermediate".equals(level)) {
        plan.append("Use moderate intensity and increase difficulty gradually.\n");
    } else {
        plan.append("Choose challenging exercises while maintaining proper form.\n");
    }

    plan.append("Warm up before exercising and stay hydrated.\n");
    plan.append("Allow adequate rest and recovery between workouts.");

    workoutResult.setText(plan.toString());
    workoutResult.setCaretPosition(0);
}


    // ---------------- COMMON UI HELPERS ----------------

    private JPanel createPage() {

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(25, 25, 25, 25));

        return page;
    }

    private JPanel pageHeading(String title, String subtitle) {

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        heading.add(pageTitle(title));
        heading.add(Box.createVerticalStrut(7));
        heading.add(pageSubtitle(subtitle));

        return heading;
    }

    private JLabel pageTitle(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 27));
        label.setForeground(TEXT);

        return label;
    }

    private JLabel pageSubtitle(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(MUTED);

        return label;
    }

    private JPanel whiteCard() {

        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)),
                new EmptyBorder(1, 1, 1, 1)));

        return panel;
    }

    private JLabel formLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT);

        return label;
    }

    private void styleInput(JTextField field) {

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 219, 230)),
                new EmptyBorder(10, 10, 10, 10)));
    }

    private void styleButton(
            JButton button, Color background, Color foreground) {

        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String safe(String value) {

        if (value == null || value.trim().isEmpty()) {
            return "Not available";
        }

        return value;
    }

    private String initials(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "M";
        }

        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }

        return (parts[0].substring(0, 1)
                + parts[parts.length - 1].substring(0, 1))
                .toUpperCase();
    }
    
private JButton createBackButton() {
    JButton backButton = new JButton("← Back to Dashboard");

    backButton.setFont(new Font("Arial", Font.BOLD, 13));
    backButton.setForeground(NAVY);
    backButton.setBackground(GREEN);
    backButton.setFocusPainted(false);
    backButton.setBorderPainted(false);
    backButton.setOpaque(true);
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    backButton.addActionListener(e ->
        pageLayout.show(pageContainer, "Dashboard")
    );

    return backButton;
}

}

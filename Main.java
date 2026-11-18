import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Main extends JFrame {

    // ---------- COLORS ----------
    private final Color NAVY = new Color(23, 32, 65);
    private final Color PURPLE = new Color(111, 82, 220);
    private final Color BLUE = new Color(55, 132, 230);
    private final Color GREEN = new Color(35, 177, 135);
    private final Color ORANGE = new Color(245, 158, 66);
    private final Color BACKGROUND = new Color(23, 32, 65);
    private final Color TEXT = new Color(39, 48, 76);
    

    // ---------- MAIN LAYOUT ----------
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContent = new JPanel(cardLayout);

    private JLabel pageTitle;
    private JLabel studentValue;
    private JLabel facultyValue;
    private JLabel courseValue;
    private JLabel roomValue;

    private String currentPage = "Dashboard";

    // Store module tables
    private Map<String, DefaultTableModel> tableModels =
            new LinkedHashMap<>();

    private Map<String, JTable> moduleTables =
            new LinkedHashMap<>();

    // Module information
    private final String[] modules = {
        "Dashboard",
        "Student Management",
        "Faculty Management",
        "Course Management",
        "Classroom Management",
        "Laboratory Management",
        "Equipment Management",
        "Campus Activities",
        "Resource Booking"
    };

    public Main() {

        setTitle("NEXORA | Smart Campus Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1400, 850);

        setMinimumSize(new Dimension(1000, 650));

        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);

        setLayout(new BorderLayout());

        // Sidebar
        add(createSidebar(), BorderLayout.WEST);

        // Main content
        add(mainContent, BorderLayout.CENTER);

        // Dashboard
        mainContent.add(createDashboard(), "Dashboard");

        // Other modules
        for (String module : modules) {

            if (!module.equals("Dashboard")) {
                mainContent.add(createModulePage(module), module);
            }
        }
        updateStatistics();

        showPage("Dashboard");

        // Open in maximized screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
    }

    // ---------- SIDEBAR ----------

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(265, 0));
        sidebar.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        // Brand
        JPanel brand = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 15, 20));

        brand.setOpaque(false);

        JLabel logo = new JLabel("N");
        logo.setFont(new Font("Arial", Font.BOLD, 27));
        logo.setForeground(Color.WHITE);

        logo.setOpaque(true);
        logo.setBackground(PURPLE);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(48, 48));

        JLabel name = new JLabel(
                "<html><b>NEXORA</b><br>"
                + "<font size='2' color='#BFC9E8'>SMART CAMPUS</font></html>");

        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 19));

        brand.add(logo);
        brand.add(name);

        top.add(brand);

        JLabel menuTitle = new JLabel("   MAIN MENU");

        menuTitle.setForeground(new Color(153, 166, 201));
        menuTitle.setFont(new Font("Arial", Font.BOLD, 11));
        menuTitle.setBorder(new EmptyBorder(18, 0, 12, 0));

        top.add(menuTitle);

        // Navigation
        JPanel navigation = new JPanel();
        navigation.setOpaque(false);
        navigation.setLayout(new BoxLayout(
                navigation, BoxLayout.Y_AXIS));

        String[] icons = {
            "▦", "♟", "♙", "▤", "▣",
            "⚗", "▧", "✦", "▣"
        };

        for (int i = 0; i < modules.length; i++) {

            String module = modules[i];

            JButton button = new JButton(
                    icons[i] + "    " + module);

            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setForeground(Color.BLACK);
            button.setHorizontalAlignment(SwingConstants.LEFT);

            button.setMaximumSize(new Dimension(245, 48));
            button.setPreferredSize(new Dimension(245, 48));

            button.setBackground(NAVY);
            button.setBorder(new EmptyBorder(10, 18, 10, 5));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setOpaque(true);

            button.addActionListener(e -> showPage(module));

            navigation.add(button);
            navigation.add(Box.createVerticalStrut(5));
        }

        top.add(navigation);

        sidebar.add(top, BorderLayout.NORTH);

        // Bottom card
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(15, 15, 20, 15));

        JPanel campusCard = new JPanel();
        campusCard.setLayout(new BoxLayout(
                campusCard, BoxLayout.Y_AXIS));

        campusCard.setBackground(new Color(47, 56, 101));
        campusCard.setBorder(new EmptyBorder(15, 14, 15, 14));

        JLabel star = new JLabel("✦  SMART CAMPUS");
        star.setForeground(new Color(190, 177, 255));
        star.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel message = new JLabel(
                "<html><font color='white'>"
                + "<b>Everything connected.</b><br>"
                + "One intelligent campus."
                + "</font></html>");

        message.setFont(new Font("Arial", Font.PLAIN, 12));

        campusCard.add(star);
        campusCard.add(Box.createVerticalStrut(10));
        campusCard.add(message);

        JLabel version = new JLabel("NEXORA  |  VERSION 1.0");
        version.setForeground(new Color(145, 158, 192));
        version.setFont(new Font("Arial", Font.PLAIN, 10));
        version.setBorder(new EmptyBorder(18, 0, 0, 0));

        // Logout button
JButton logoutButton = new JButton("↪  Logout");
logoutButton.setBackground(Color.WHITE);
logoutButton.setForeground(Color.BLACK);
logoutButton.setOpaque(true);
logoutButton.setBorderPainted(false);

logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
logoutButton.setBackground(Color.BLACK);
logoutButton.setForeground(Color.WHITE);
logoutButton.setFocusPainted(false);
logoutButton.setBorder(new EmptyBorder(12, 15, 12, 15));
logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
logoutButton.setMaximumSize(new Dimension(230, 45));

logoutButton.addActionListener(e -> {
    int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION
    );

    if (choice == JOptionPane.YES_OPTION) {
        dispose();
        showLogin();
    }
});

bottom.add(campusCard);
bottom.add(version);
bottom.add(Box.createVerticalStrut(15));
bottom.add(logoutButton);

sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    // ---------- DASHBOARD ----------

    private JPanel createDashboard() {

        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(BACKGROUND);
        dashboard.setBackground(BACKGROUND);

        JPanel content = new JPanel();
        content.setBackground(BACKGROUND);

        content.setLayout(new BoxLayout(
                content, BoxLayout.Y_AXIS));

        content.setBorder(new EmptyBorder(22, 28, 30, 28));

        // Header
        JPanel header = createHeader("Dashboard Overview");

        content.add(header);
        content.add(Box.createVerticalStrut(25));

        // Welcome text
        JLabel welcome = new JLabel("Welcome to NEXORA");
        welcome.setFont(new Font("Arial", Font.BOLD, 27));
        welcome.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Manage your campus activities from one place.");

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 130, 150));

        content.add(welcome);
        content.add(Box.createVerticalStrut(6));
        content.add(subtitle);

        content.add(Box.createVerticalStrut(28));

        // Statistics cards
        JPanel stats = new JPanel(new GridLayout(1, 4, 18, 0));
        stats.setBackground(BACKGROUND);

        studentValue = new JLabel("0");
        facultyValue = new JLabel("0");
        courseValue = new JLabel("0");
        roomValue = new JLabel("0");

        stats.add(createStatCard(
                "Total Students", studentValue, "♟", BLUE));

        stats.add(createStatCard(
                "Total Faculty", facultyValue, "♙", PURPLE));

        stats.add(createStatCard(
                "Total Courses", courseValue, "▤", GREEN));

        stats.add(createStatCard(
                "Classrooms", roomValue, "▣", ORANGE));

        content.add(stats);

        content.add(Box.createVerticalStrut(30));

        // Quick access
        JLabel quickTitle = new JLabel("Quick Access");
        quickTitle.setFont(new Font("Arial", Font.BOLD, 20));
        quickTitle.setForeground(NAVY);

        content.add(quickTitle);
        content.add(Box.createVerticalStrut(15));

        JPanel quickPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        quickPanel.setBackground(BACKGROUND);

        String[] quickModules = {
            "Student Management",
            "Faculty Management",
            "Course Management",
            "Classroom Management",
            "Equipment Management",
            "Resource Booking"
        };

        String[] quickIcons = {
            "♟", "♙", "▤", "▣", "▧", "✦"
        };

        for (int i = 0; i < quickModules.length; i++) {

            String module = quickModules[i];

            JButton quickButton = new JButton(
                    quickIcons[i] + "   " + module);

            quickButton.setFont(
                    new Font("Arial", Font.BOLD, 14));

            quickButton.setForeground(NAVY);
            quickButton.setBackground(Color.WHITE);
            quickButton.setFocusPainted(false);
            quickButton.setBorder(
                    new CompoundBorder(
                            new LineBorder(new Color(225, 229, 240)),
                            new EmptyBorder(20, 15, 20, 15)));

            quickButton.setCursor(
                    new Cursor(Cursor.HAND_CURSOR));

            quickButton.addActionListener(
                    e -> showPage(module));

            quickPanel.add(quickButton);
        }

        content.add(quickPanel);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        dashboard.add(scrollPane, BorderLayout.CENTER);

        return dashboard;
    }    // ---------- HEADER ----------

    private JPanel createHeader(String title) {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(18, 28, 18, 28));

        pageTitle = new JLabel(title);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 21));
        pageTitle.setForeground(TEXT);

        JPanel right = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 15, 0));

        right.setOpaque(false);

        JButton refresh = new JButton("↻ Refresh");
        refresh.setBackground(new Color(238, 234, 255));
        refresh.setForeground(PURPLE);
        refresh.setFocusPainted(false);

        refresh.addActionListener(e -> {
            updateStatistics();

            JOptionPane.showMessageDialog(
                    this, "NEXORA refreshed successfully!");
        });

        JLabel admin = new JLabel(
                "<html><b>Administrator</b><br>"
                + "<font color='gray' size='2'>Campus Admin</font></html>");

        right.add(refresh);
        right.add(admin);

        header.add(pageTitle, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    // ---------- STATISTIC CARD ----------

    private JPanel createStatCard(
            String title, JLabel value,
            String icon, Color color) {

        JPanel card = new JPanel(new BorderLayout(10, 10));

        card.setBackground(Color.WHITE);

        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 233, 243)),
                new EmptyBorder(20, 18, 20, 18)));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 12));
        heading.setForeground(new Color(112, 120, 145));

        JLabel symbol = new JLabel(icon);
        symbol.setForeground(color);
        symbol.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        top.add(heading, BorderLayout.CENTER);
        top.add(symbol, BorderLayout.EAST);

        value.setFont(new Font("Arial", Font.BOLD, 30));
        value.setForeground(TEXT);

        card.add(top, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    // ---------- MODULE PAGE ----------

    private JPanel createModulePage(String module) {

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BACKGROUND);
        page.setBackground(BACKGROUND);

        page.add(createHeader(module), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 28, 25, 28));

        // Heading
        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(module);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage, search, add, edit and delete records.");

        subtitle.setForeground(Color.GRAY);

        labels.add(title);
        labels.add(Box.createVerticalStrut(7));
        labels.add(subtitle);

        JButton addButton = new JButton("+ Add New");

addButton.setBackground(Color.BLACK);
addButton.setForeground(Color.WHITE);
addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
addButton.setFocusPainted(false);
addButton.setBorderPainted(false);
addButton.setOpaque(true);
addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addButton.setBackground(PURPLE);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        addButton.setFocusPainted(false);
        addButton.setBorder(new EmptyBorder(12, 20, 12, 20));

        addButton.addActionListener(e -> addRecord(module));

        heading.add(labels, BorderLayout.WEST);
        heading.add(addButton, BorderLayout.EAST);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(12, 15, 12, 15));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(new EmptyBorder(8, 8, 8, 8));
        searchField.setToolTipText("Enter text to search");

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(238, 234, 255));
        searchButton.setForeground(PURPLE);
        searchButton.setFocusPainted(false);

        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Table columns
        String[] columns;

if (module.equals("Student Management")) {

    columns = new String[]{
        "Student ID",
        "Full Name",
        "Department",
        "Academic Year",
        "Email",
        "Phone"
    };

} else if (module.equals("Faculty Management")) {

    columns = new String[]{
        "Faculty ID",
        "Full Name",
        "Department",
        "Designation",
        "Email",
        "Phone"
    };

} else {

    columns = new String[]{
        "ID",
        "Name / Title",
        "Department / Category",
        "Status"
    };
}

        DefaultTableModel model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Load saved records
        try {

            boolean dataExists =
                    DatabaseManager.loadTable(module, model);

            if (!dataExists) {
                addSampleRecords(module, model);
                DatabaseManager.saveTable(module, model);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load saved data: " + e.getMessage(),
                    "Storage Error",
                    JOptionPane.ERROR_MESSAGE);

            addSampleRecords(module, model);
        }

        // JTable
        JTable table = new JTable(model);

        table.setRowHeight(42);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setGridColor(new Color(235, 237, 244));
        table.setSelectionBackground(new Color(230, 224, 255));
        table.setSelectionForeground(TEXT);

        JTableHeader tableHeader = table.getTableHeader();

        tableHeader.setPreferredSize(new Dimension(0, 43));
        tableHeader.setBackground(new Color(241, 243, 250));
        tableHeader.setForeground(TEXT);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));

        // Search functionality
        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(model);

        table.setRowSorter(sorter);

        searchButton.addActionListener(e -> {

            String query = searchField.getText().trim();

            if (query.isEmpty()) {

                sorter.setRowFilter(null);

            } else {

                sorter.setRowFilter(
                        RowFilter.regexFilter(
                                "(?i)" +
                                java.util.regex.Pattern.quote(query)));
            }
        });

        searchField.addActionListener(
                e -> searchButton.doClick());

        // Delete button
        JButton deleteButton = new JButton("Delete Selected");

        deleteButton.setBackground(new Color(255, 235, 235));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);

        deleteButton.addActionListener(e -> {

            int selected = table.getSelectedRow();

            if (selected == -1) {

                JOptionPane.showMessageDialog(
                        this, "Please select a record first.");

                return;
            }

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {

                int modelRow =
                        table.convertRowIndexToModel(selected);

                model.removeRow(modelRow);

                saveModuleData(module, model);

                updateStatistics();
            }
        });

        // Edit button
        JButton editButton = new JButton("Edit Selected");

        editButton.setBackground(new Color(230, 240, 255));
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);

        editButton.addActionListener(e ->
                editRecord(module, table, model));

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel tableTitle = new JLabel("Records");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        buttons.setOpaque(false);
        buttons.add(editButton);
        buttons.add(deleteButton);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.add(buttons, BorderLayout.SOUTH);

        // Center layout
        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setOpaque(false);

        center.add(searchPanel, BorderLayout.NORTH);
        center.add(tablePanel, BorderLayout.CENTER);

        content.add(heading, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);

        page.add(content, BorderLayout.CENTER);

        tableModels.put(module, model);
        moduleTables.put(module, table);

        return page;
    }    // ---------- SAMPLE RECORDS ----------

    private void addSampleRecords(
            String module, DefaultTableModel model) {

        String[][] sample;

        switch (module) {

            case "Student Management":

                sample = new String[][]{
                    {
                        "ST001", "Aarav Sharma",
                        "Computer Science", "2nd Year",
                        "aarav@gmail.com", "9876543210"
                    },
                    {
                        "ST002", "Ananya Rao",
                        "Artificial Intelligence", "2nd Year",
                        "ananya@gmail.com", "9876543211"
                    },
                    {
                        "ST003", "Rahul Kumar",
                        "Information Science", "3rd Year",
                        "rahul@gmail.com", "9876543212"
                    }
                };
                break;

            case "Faculty Management":

    sample = new String[][]{
        {
            "FC001",
            "Dr. Meera",
            "Computer Science",
            "Professor",
            "meera@gmail.com",
            "9876543210"
        },
        {
            "FC002",
            "Prof. Ramesh",
            "Mathematics",
            "Associate Professor",
            "ramesh@gmail.com",
            "9876543211"
        },
        {
            "FC003",
            "Dr. Priya",
            "Artificial Intelligence",
            "Assistant Professor",
            "priya@gmail.com",
            "9876543212"
        }
    };

    break;

            case "Course Management":

                sample = new String[][]{
                    {"CR001", "Java Programming", "CSE", "Active"},
                    {"CR002", "Database Management", "ISE", "Active"},
                    {"CR003", "Machine Learning", "AIML", "Active"}
                };
                break;

            case "Classroom Management":

                sample = new String[][]{
                    {"RM001", "Room 101", "Capacity: 60", "Available"},
                    {"RM002", "Room 202", "Capacity: 80", "Available"},
                    {"RM003", "Room 303", "Capacity: 50", "Booked"}
                };
                break;

            case "Laboratory Management":

                sample = new String[][]{
                    {"LB001", "Computer Lab 1", "Computers: 40", "Available"},
                    {"LB002", "AI Laboratory", "Computers: 30", "Available"},
                    {"LB003", "Physics Lab", "Equipment: 25", "Available"}
                };
                break;

            case "Equipment Management":

                sample = new String[][]{
                    {"EQ001", "Projector", "Quantity: 10", "Available"},
                    {"EQ002", "Laptop", "Quantity: 25", "Available"},
                    {"EQ003", "Printer", "Quantity: 5", "Maintenance"}
                };
                break;

            case "Campus Activities":

                sample = new String[][]{
                    {"AC001", "Tech Fest", "Technical Event", "Upcoming"},
                    {"AC002", "Sports Day", "Sports", "Upcoming"},
                    {"AC003", "Workshop", "Academic", "Completed"}
                };
                break;

            default:

                sample = new String[][]{
                    {"BK001", "Computer Lab 1",
                        "09:00 AM - 11:00 AM", "Approved"},
                    {"BK002", "Room 202",
                        "11:00 AM - 01:00 PM", "Pending"},
                    {"BK003", "Seminar Hall",
                        "02:00 PM - 04:00 PM", "Approved"}
                };
                break;
        }

        for (String[] record : sample) {
            model.addRow(record);
        }
    }

    // ---------- ADD RECORD ----------

private void addRecord(String module) {

    boolean isStudent = module.equals("Student Management");
    boolean isFaculty = module.equals("Faculty Management");

    String[] labels;

    if (isStudent) {

        labels = new String[]{
            "Student ID:",
            "Full Name:",
            "Department:",
            "Academic Year:",
            "Email:",
            "Phone:"
        };

    } else if (isFaculty) {

        labels = new String[]{
            "Faculty ID:",
            "Full Name:",
            "Department:",
            "Designation:",
            "Email:",
            "Phone:"
        };

    } else {

        labels = new String[]{
            "ID:",
            "Name / Title:",
            "Department / Category:"
        };
    }

    JTextField[] fields = new JTextField[labels.length];

    JPanel form = new JPanel(
            new GridLayout(labels.length, 2, 12, 12));

    for (int i = 0; i < labels.length; i++) {

        fields[i] = new JTextField();
        fields[i].setFont(new Font("Arial", Font.PLAIN, 14));

        form.add(new JLabel(labels[i]));
        form.add(fields[i]);
    }

    String dialogTitle;

    if (isStudent) {
        dialogTitle = "Register New Student";
    } else if (isFaculty) {
        dialogTitle = "Add New Faculty";
    } else {
        dialogTitle = "Add New Record - " + module;
    }

    int result = JOptionPane.showConfirmDialog(
            this,
            form,
            dialogTitle,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String[] values = new String[fields.length];

    for (int i = 0; i < fields.length; i++) {

        values[i] = fields[i].getText().trim();

        if (values[i].isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all the fields.");

            return;
        }
    }

    // Validate email for students and faculty
    if ((isStudent || isFaculty)
            && !values[4].matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid email address.");

        return;
    }

    // Validate phone number
    if ((isStudent || isFaculty)
            && !values[5].matches("\\d{10}")) {

        JOptionPane.showMessageDialog(
                this,
                "Phone number must contain exactly 10 digits.");

        return;
    }

    DefaultTableModel model = tableModels.get(module);

    // Check duplicate IDs
    for (int i = 0; i < model.getRowCount(); i++) {

        if (model.getValueAt(i, 0).toString()
                .equalsIgnoreCase(values[0])) {

            JOptionPane.showMessageDialog(
                    this,
                    "This ID already exists.");

            return;
        }
    }

    // Add record
    if (isStudent || isFaculty) {

        model.addRow(values);

    } else {

        model.addRow(new Object[]{
            values[0],
            values[1],
            values[2],
            "Active"
        });
    }

    saveModuleData(module, model);

    updateStatistics();

    JOptionPane.showMessageDialog(
            this,
            isStudent
                    ? "Student registered successfully!"
                    : isFaculty
                            ? "Faculty added successfully!"
                            : "Record added successfully!");
}
    // ---------- EDIT RECORD ----------

private void editRecord(
        String module,
        JTable table,
        DefaultTableModel model) {

    boolean isStudent = module.equals("Student Management");
    boolean isFaculty = module.equals("Faculty Management");

    int selectedRow = table.getSelectedRow();

    if (selectedRow == -1) {

        JOptionPane.showMessageDialog(
                this,
                "Please select a record to edit.");

        return;
    }

    int modelRow = table.convertRowIndexToModel(selectedRow);

    String[] labels;

    if (isStudent) {

        labels = new String[]{
            "Student ID:",
            "Full Name:",
            "Department:",
            "Academic Year:",
            "Email:",
            "Phone:"
        };

    } else if (isFaculty) {

        labels = new String[]{
            "Faculty ID:",
            "Full Name:",
            "Department:",
            "Designation:",
            "Email:",
            "Phone:"
        };

    } else {

        labels = new String[]{
            "ID:",
            "Name / Title:",
            "Department / Category:"
        };
    }

    JTextField[] fields = new JTextField[labels.length];

    JPanel form = new JPanel(
            new GridLayout(labels.length, 2, 12, 12));

    for (int i = 0; i < labels.length; i++) {

        Object oldValue = model.getValueAt(modelRow, i);

        fields[i] = new JTextField(
                oldValue == null ? "" : oldValue.toString());

        fields[i].setFont(new Font("Arial", Font.PLAIN, 14));

        form.add(new JLabel(labels[i]));
        form.add(fields[i]);
    }

    String dialogTitle;

    if (isStudent) {
        dialogTitle = "Edit Student Details";
    } else if (isFaculty) {
        dialogTitle = "Edit Faculty Details";
    } else {
        dialogTitle = "Edit Record - " + module;
    }

    int result = JOptionPane.showConfirmDialog(
            this,
            form,
            dialogTitle,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String[] values = new String[fields.length];

    for (int i = 0; i < fields.length; i++) {

        values[i] = fields[i].getText().trim();

        if (values[i].isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all the fields.");

            return;
        }
    }

    // Validate email
    if ((isStudent || isFaculty)
            && !values[4].matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid email address.");

        return;
    }

    // Validate phone number
    if ((isStudent || isFaculty)
            && !values[5].matches("\\d{10}")) {

        JOptionPane.showMessageDialog(
                this,
                "Phone number must contain exactly 10 digits.");

        return;
    }

    // Check duplicate IDs
    for (int i = 0; i < model.getRowCount(); i++) {

        if (i != modelRow
                && model.getValueAt(i, 0).toString()
                .equalsIgnoreCase(values[0])) {

            JOptionPane.showMessageDialog(
                    this,
                    "This ID already exists.");

            return;
        }
    }

    // Update record
    for (int i = 0; i < values.length; i++) {

        model.setValueAt(values[i], modelRow, i);
    }

    saveModuleData(module, model);

    updateStatistics();

    JOptionPane.showMessageDialog(
            this,
            isStudent
                    ? "Student details updated successfully!"
                    : isFaculty
                            ? "Faculty details updated successfully!"
                            : "Record updated successfully!");
}
    // ---------- SAVE DATA ----------
private void saveModuleData(String module, DefaultTableModel model) {

    try {
        DatabaseManager.saveTable(module, model);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Could not save " + module + " data:\n" + e.getMessage(),
                "Storage Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


// ---------- UPDATE DASHBOARD STATISTICS ----------
private void updateStatistics() {

    if (studentValue == null) {
        return;
    }

    studentValue.setText(String.valueOf(
            tableModels.get("Student Management").getRowCount()
    ));

    facultyValue.setText(String.valueOf(
            tableModels.get("Faculty Management").getRowCount()
    ));

    courseValue.setText(String.valueOf(
            tableModels.get("Course Management").getRowCount()
    ));

    roomValue.setText(String.valueOf(
            tableModels.get("Classroom Management").getRowCount()
    ));
}


// ---------- NAVIGATION ----------
private String getModulePage(String name) {

    switch (name) {

        case "Students":
            return "Student Management";

        case "Faculty":
            return "Faculty Management";

        case "Courses":
            return "Course Management";

        case "Classrooms":
            return "Classroom Management";

        case "Laboratories":
            return "Laboratory Management";

        case "Equipment":
            return "Equipment Management";

        case "Activities":
            return "Campus Activities";

        case "Bookings":
            return "Resource Booking";

        default:
            return "Dashboard";
    }
}


private void showPage(String page) {

    currentPage = page;

    cardLayout.show(mainContent, page);

    if (pageTitle != null) {

        pageTitle.setText(
                page.equals("Dashboard")
                        ? "Dashboard Overview"
                        : page
        );
    }

    mainContent.revalidate();
    mainContent.repaint();
}


// ---------- COMMON DESIGN ----------
private JPanel sectionTitle(String title, String subtitle) {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setOpaque(false);

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 23));
    titleLabel.setForeground(NAVY);

    JLabel subtitleLabel = new JLabel(subtitle);
    subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    subtitleLabel.setForeground(Color.GRAY);

    panel.add(titleLabel);
    panel.add(Box.createVerticalStrut(5));
    panel.add(subtitleLabel);

    return panel;
}


private JPanel whitePanel() {

    JPanel panel = new JPanel(new BorderLayout());

    panel.setBackground(Color.WHITE);

    panel.setBorder(
            new EmptyBorder(18, 18, 18, 18)
    );

    return panel;
}


private JScrollPane createScrollable(JPanel panel) {

    JScrollPane scrollPane = new JScrollPane(panel);

    scrollPane.setBorder(null);

    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    return scrollPane;
} // ---------- LOGIN SCREEN ----------
private static void showLogin() {

    JFrame loginFrame = new JFrame("NEXORA | Admin Login");

    loginFrame.setSize(500, 420);
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginFrame.setLocationRelativeTo(null);
    loginFrame.setResizable(false);

    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(new Color(23, 32, 65));
    mainPanel.setLayout(new BoxLayout(
            mainPanel, BoxLayout.Y_AXIS));

    mainPanel.setBorder(new EmptyBorder(35, 55, 35, 55));

    JLabel logo = new JLabel("NEXORA");
    logo.setFont(new Font("Arial", Font.BOLD, 30));
    logo.setForeground(Color.WHITE);
    logo.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitle = new JLabel("SMART CAMPUS MANAGEMENT");
    subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
    subtitle.setForeground(new Color(190, 177, 255));
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel welcome = new JLabel("Administrator Login");
    welcome.setFont(new Font("Arial", Font.BOLD, 21));
    welcome.setForeground(Color.WHITE);
    welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

    JTextField username = new JTextField();
    username.setMaximumSize(new Dimension(350, 42));
    username.setFont(new Font("Arial", Font.PLAIN, 15));

    JPasswordField password = new JPasswordField();
    password.setMaximumSize(new Dimension(350, 42));
    password.setFont(new Font("Arial", Font.PLAIN, 15));

    JLabel userLabel = new JLabel("Username");
    userLabel.setForeground(Color.WHITE);
    userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel passLabel = new JLabel("Password");
    passLabel.setForeground(Color.WHITE);
    passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton loginButton = new JButton("LOGIN");

    loginButton.setMaximumSize(new Dimension(350, 45));
    loginButton.setBackground(Color.WHITE);
    loginButton.setForeground(Color.BLACK);
    loginButton.setFont(new Font("Arial", Font.BOLD, 15));
    loginButton.setFocusPainted(false);
    loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    loginButton.addActionListener(e -> {

        String enteredUsername = username.getText().trim();

        String enteredPassword =
                new String(password.getPassword());

        if (enteredUsername.equals("admin")
                && enteredPassword.equals("admin123")) {

            loginFrame.dispose();

            SwingUtilities.invokeLater(() -> new Main());

        } else {

            JOptionPane.showMessageDialog(
                    loginFrame,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            password.setText("");
        }
    });

    password.addActionListener(e -> loginButton.doClick());

    mainPanel.add(logo);
    mainPanel.add(Box.createVerticalStrut(5));
    mainPanel.add(subtitle);
    mainPanel.add(Box.createVerticalStrut(30));
    mainPanel.add(welcome);
    mainPanel.add(Box.createVerticalStrut(25));

    mainPanel.add(userLabel);
    mainPanel.add(Box.createVerticalStrut(7));
    mainPanel.add(username);
    mainPanel.add(Box.createVerticalStrut(15));

    mainPanel.add(passLabel);
    mainPanel.add(Box.createVerticalStrut(7));
    mainPanel.add(password);
    mainPanel.add(Box.createVerticalStrut(25));

    mainPanel.add(loginButton);

    loginFrame.add(mainPanel);
    loginFrame.setVisible(true);
}
// ---------- MAIN METHOD ----------

// ---------- MAIN METHOD ----------
// ---------- MAIN METHOD ----------
public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.out.println("Using default appearance.");
        }

        showLogin();
    });
}

}
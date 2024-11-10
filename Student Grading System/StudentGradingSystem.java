import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student {
    String name;
    int[] marks;
    int totalMarks;
    String grade;

    public Student(String name, int[] marks, int totalMarks, String grade) {
        this.name = name;
        this.marks = marks;
        this.totalMarks = totalMarks;
        this.grade = grade;
    }
}

public class StudentGradingSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JTextField usernameField, passwordField;
    private static JTextField studentNameField;
    private static JTextField[] subjectFields;
    private static JTable studentTable;
    private static DefaultTableModel tableModel;

    private static final String[] subjects = {"Math", "Science", "English", "Computer Science", "Art"};

    public static void main(String[] args) {
        frame = new JFrame("Student Grading System");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.LIGHT_GRAY);

        // Add Top Image
        JLabel logoLabel = new JLabel(new ImageIcon("logo.png")); // Replace with your logo path
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(logoLabel, BorderLayout.NORTH);

        // Grading Classification Image on the Left
        JLabel gradingImageLabel = new JLabel(new ImageIcon("lo.png")); // Replace with grading image path
        gradingImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(gradingImageLabel, BorderLayout.WEST);

        // Form panel for login fields on the right of the grading image
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);

        // Username field
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100, 25)); // Smaller size
        usernameField.setMaximumSize(new Dimension(100, 25));
        formPanel.add(usernameField);

        // Password field
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 25)); // Smaller size
        passwordField.setMaximumSize(new Dimension(100, 25));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        formPanel.add(loginButton);

        loginPanel.add(formPanel, BorderLayout.CENTER);

        frame.add(loginPanel, "login");

        // Student Entry and Display Panel
        JPanel studentPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // Add Grading Result Image to the Top Left of the Student Panel
        JLabel gradingResultImageLabel = new JLabel(new ImageIcon("resize-17311382791901063469ass.png")); // Replace with your grading result image path
        gradingResultImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        studentPanel.add(gradingResultImageLabel, BorderLayout.WEST);

        JLabel nameLabel = new JLabel("Enter Student Name:");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(nameLabel);

        studentNameField = new JTextField();
        studentNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, studentNameField.getPreferredSize().height));
        studentNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(studentNameField);

        subjectFields = new JTextField[5];
        for (int i = 0; i < subjects.length; i++) {
            JLabel subjectLabel = new JLabel(subjects[i] + " Marks:");
            subjectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            inputPanel.add(subjectLabel);

            JTextField field = new JTextField();
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            subjectFields[i] = field;
            inputPanel.add(field);
        }

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());
        buttonPanel.add(submitButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearInputFields());
        buttonPanel.add(clearButton);

        inputPanel.add(buttonPanel);
        studentPanel.add(inputPanel, BorderLayout.CENTER);

        // Table to display student details
        String[] columnNames = {"Name", "Math", "Science", "English", "Computer Science", "Art", "Total Marks", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true);
        studentPanel.add(new JScrollPane(studentTable), BorderLayout.SOUTH);

        frame.add(studentPanel, "studentForm");

        // Show login panel first
        cardLayout.show(frame.getContentPane(), "login");

        // Setup frame properties
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("password")) {
            cardLayout.show(frame.getContentPane(), "studentForm");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid login details.");
        }
    }

    private static void handleSubmit() {
        String studentName = studentNameField.getText();
        int[] marks = new int[subjects.length];
        int totalMarks = 0;
        for (int i = 0; i < subjectFields.length; i++) {
            try {
                marks[i] = Integer.parseInt(subjectFields[i].getText());
                if (marks[i] < 0 || marks[i] > 100) throw new NumberFormatException();
                totalMarks += marks[i];
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid marks for " + subjects[i]);
                return;
            }
        }
        String grade = calculateGrade(totalMarks);
        Student student = new Student(studentName, marks, totalMarks, grade);
        students.add(student);
        tableModel.addRow(new Object[]{studentName, marks[0], marks[1], marks[2], marks[3], marks[4], totalMarks, grade});
    }

    private static void clearInputFields() {
        studentNameField.setText("");
        for (JTextField field : subjectFields) {
            field.setText("");
        }
    }

    private static String calculateGrade(int totalMarks) {
        if (totalMarks >= 450) return "A+";
        if (totalMarks >= 400) return "A";
        if (totalMarks >= 350) return "B+";
        if (totalMarks >= 300) return "B";
        if (totalMarks >= 250) return "C+";
        if (totalMarks >= 200) return "C";
        return "D";
    }
}


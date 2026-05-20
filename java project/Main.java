
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 220);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(40, 40, 120));
        titleLabel.setBackground(new Color(240, 248, 255));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        buttonPanel.setBackground(new Color(245, 245, 255));

        JButton studentBtn = createStyledButton("Manage Students");
        JButton courseBtn = createStyledButton("Manage Courses");
        JButton enrollmentBtn = createStyledButton("Manage Enrollments");

        studentBtn.addActionListener(e -> new StudentForm().setVisible(true));
        courseBtn.addActionListener(e -> new CourseForm().setVisible(true));
        enrollmentBtn.addActionListener(e -> new EnrollmentForm().setVisible(true));

        buttonPanel.add(studentBtn);
        buttonPanel.add(courseBtn);
        buttonPanel.add(enrollmentBtn);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }
}
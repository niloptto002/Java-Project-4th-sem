import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnrollmentForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, studentIdField, courseIdField;
    private JSpinner dateSpinner;
    private JComboBox<Grade> gradeCombo;
    private List<Enrollment> enrollments;

    public EnrollmentForm() {
        setTitle("Enrollment Management System");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 13);

        model = new DefaultTableModel(
                new String[]{"Enrollment ID", "Student ID", "Course ID", "Date", "Grade"}, 0
        );

        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(26);
        table.setSelectionBackground(new Color(204, 229, 255));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);

        idField = new JTextField(10);
        studentIdField = new JTextField(10);
        courseIdField = new JTextField(10);

        idField.setFont(fieldFont);
        studentIdField.setFont(fieldFont);
        courseIdField.setFont(fieldFont);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setFont(fieldFont);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        gradeCombo = new JComboBox<>(Grade.values());
        gradeCombo.setFont(fieldFont);

        JLabel idLabel = new JLabel("Enrollment ID:");
        JLabel studentLabel = new JLabel("Student ID:");
        JLabel courseLabel = new JLabel("Course ID:");
        JLabel dateLabel = new JLabel("Enrollment Date:");
        JLabel gradeLabel = new JLabel("Grade:");

        idLabel.setFont(labelFont);
        studentLabel.setFont(labelFont);
        courseLabel.setFont(labelFont);
        dateLabel.setFont(labelFont);
        gradeLabel.setFont(labelFont);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        styleButton(addButton, new Color(46, 204, 113), buttonFont);
        styleButton(editButton, new Color(241, 196, 15), buttonFont);
        styleButton(deleteButton, new Color(231, 76, 60), buttonFont);

        addButton.addActionListener(e -> addEnrollment());
        editButton.addActionListener(e -> editEnrollment());
        deleteButton.addActionListener(e -> deleteEnrollment());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(245, 247, 250));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 2;
        inputPanel.add(studentLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(courseLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(courseIdField, gbc);

        gbc.gridx = 2;
        inputPanel.add(dateLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(dateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(gradeLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(gradeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(editButton, gbc);
        gbc.gridx = 2;
        inputPanel.add(deleteButton, gbc);

        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        loadData();
    }

    private void styleButton(JButton button, Color color, Font font) {
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
    }

    private void loadData() {
        try {
            enrollments = DataManager.loadEnrollments();
            model.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Enrollment e : enrollments) {
                model.addRow(new Object[]{
                        e.getEnrollmentId(),
                        e.getStudentId(),
                        e.getCourseId(),
                        sdf.format(e.getEnrollmentDate()),
                        e.getGrade()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load enrollments");
        }
    }

    private void addEnrollment() {
        try {
            enrollments.add(new Enrollment(
                    Integer.parseInt(idField.getText()),
                    Integer.parseInt(studentIdField.getText()),
                    courseIdField.getText(),
                    (Date) dateSpinner.getValue(),
                    (Grade) gradeCombo.getSelectedItem()
            ));
            DataManager.saveEnrollments(enrollments);
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void editEnrollment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an enrollment to edit");
            return;
        }
        try {
            Enrollment e = enrollments.get(row);
            e.setEnrollmentId(Integer.parseInt(idField.getText()));
            e.setStudentId(Integer.parseInt(studentIdField.getText()));
            e.setCourseId(courseIdField.getText());
            e.setEnrollmentDate((Date) dateSpinner.getValue());
            e.setGrade((Grade) gradeCombo.getSelectedItem());
            DataManager.saveEnrollments(enrollments);
            loadData();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Edit failed");
        }
    }

    private void deleteEnrollment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an enrollment to delete");
            return;
        }
        enrollments.remove(row);
        try {
            DataManager.saveEnrollments(enrollments);
            loadData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void clearFields() {
        idField.setText("");
        studentIdField.setText("");
        courseIdField.setText("");
        dateSpinner.setValue(new Date());
        gradeCombo.setSelectedIndex(0);
    }
}

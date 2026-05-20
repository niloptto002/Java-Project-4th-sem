import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CourseForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, titleField;
    private List<Course> courses;

    public CourseForm() {
        setTitle("Course Management System");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 13);

        model = new DefaultTableModel(new String[]{"Course ID", "Course Title"}, 0);
        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(60, 63, 65));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);

        idField = new JTextField(12);
        titleField = new JTextField(16);
        idField.setFont(fieldFont);
        titleField.setFont(fieldFont);

        JLabel idLabel = new JLabel("Course ID:");
        JLabel titleLabel = new JLabel("Title:");
        idLabel.setFont(labelFont);
        titleLabel.setFont(labelFont);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        styleButton(addButton, new Color(46, 204, 113), buttonFont);
        styleButton(editButton, new Color(241, 196, 15), buttonFont);
        styleButton(deleteButton, new Color(231, 76, 60), buttonFont);

        addButton.addActionListener(e -> addCourse());
        editButton.addActionListener(e -> editCourse());
        deleteButton.addActionListener(e -> deleteCourse());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void loadData() {
        try {
            courses = DataManager.loadCourses();
            model.setRowCount(0);
            for (Course c : courses) {
                model.addRow(new Object[]{c.getCourseId(), c.getTitle()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses");
        }
    }

    private void addCourse() {
        try {
            Course c = new Course(idField.getText(), titleField.getText());
            courses.add(c);
            DataManager.saveCourses(courses);
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void editCourse() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to edit");
            return;
        }
        courses.get(row).setCourseId(idField.getText());
        courses.get(row).setTitle(titleField.getText());
        try {
            DataManager.saveCourses(courses);
            loadData();
            clearFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Edit failed");
        }
    }

    private void deleteCourse() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
            return;
        }
        courses.remove(row);
        try {
            DataManager.saveCourses(courses);
            loadData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
    }
}

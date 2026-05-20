import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField;
    private JSpinner dobSpinner;
    private List<Student> students;

    public StudentForm() {
        setTitle("Student Management System");
        setSize(700, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 13);

        model = new DefaultTableModel(new String[]{"Student ID", "Name", "Date of Birth"}, 0);
        table = new JTable(model);
        table.setFont(tableFont);
        table.setRowHeight(26);
        table.setSelectionBackground(new Color(186, 225, 255));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);

        idField = new JTextField(12);
        nameField = new JTextField(16);
        idField.setFont(fieldFont);
        nameField.setFont(fieldFont);

        dobSpinner = new JSpinner(new SpinnerDateModel());
        dobSpinner.setFont(fieldFont);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd");
        dobSpinner.setEditor(editor);

        JLabel idLabel = new JLabel("Student ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel dobLabel = new JLabel("Date of Birth:");

        idLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        dobLabel.setFont(labelFont);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        styleButton(addButton, new Color(46, 204, 113), buttonFont);
        styleButton(editButton, new Color(241, 196, 15), buttonFont);
        styleButton(deleteButton, new Color(231, 76, 60), buttonFont);

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());

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

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(dobLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dobSpinner, gbc);

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
            students = DataManager.loadStudents();
            model.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Student s : students) {
                model.addRow(new Object[]{
                        s.getStudentId(),
                        s.getName(),
                        sdf.format(s.getDob())
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load students");
        }
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            Date dob = (Date) dobSpinner.getValue();
            students.add(new Student(id, name, dob));
            DataManager.saveStudents(students);
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to edit");
            return;
        }
        try {
            students.get(row).setStudentId(Integer.parseInt(idField.getText()));
            students.get(row).setName(nameField.getText());
            students.get(row).setDob((Date) dobSpinner.getValue());
            DataManager.saveStudents(students);
            loadData();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Edit failed");
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete");
            return;
        }
        students.remove(row);
        try {
            DataManager.saveStudents(students);
            loadData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        dobSpinner.setValue(new Date());
    }
}

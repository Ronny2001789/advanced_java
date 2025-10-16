package advanced_java.project;

// StudentManagerApp.java
import java.util.*;
import java.util.stream.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;

// Generic Student class
class Student<T> {
    private T id;
    private String name;
    private double grade;

    public Student(T id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public T getId() { return id; }
    public String getName() { return name; }
    public double getGrade() { return grade; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + grade + ")";
    }
}

// Main Application
public class StudentManagerApp {
    // use java.util.List to avoid confusion with java.awt.List
    private java.util.List<Student<Integer>> students = new java.util.ArrayList<>();

    public static void main(String[] args) {
//        makes sure that your GUI (Graphical User Interface) code runs safely
        SwingUtilities.invokeLater(() -> new StudentManagerApp().createGUI());
    }

    // Create GUI
    private void createGUI() {
        JFrame frame = new JFrame("Student Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField gradeField = new JTextField(5);
        JTextArea output = new JTextArea(10, 30);
        JButton addButton = new JButton("Add Student");
        JButton showButton = new JButton("Show Students");
        JButton filterButton = new JButton("Filter (Grade > 75)");

        frame.add(new JLabel("ID:"));
        frame.add(idField);
        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        frame.add(addButton);
        frame.add(showButton);
        frame.add(filterButton);
        frame.add(new JScrollPane(output));

        // Add Student Button
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double grade = Double.parseDouble(gradeField.getText());

                students.add(new Student<>(id, name, grade));
                output.append("Added: " + name + "\n");

                idField.setText("");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show All Students (using forEach + lambda)
        showButton.addActionListener(e -> {
            output.setText("All Students:\n");
            students.forEach(s -> output.append(s + "\n"));
        });

        // Filter Students using Stream API (fixed type inference issue)
        filterButton.addActionListener(e -> {
            output.setText("Students with Grade > 75:\n");
            students.stream()
                    .filter(s -> s.getGrade() > 75)
                    .sorted(Comparator.comparingDouble(Student<Integer>::getGrade).reversed())
                    .forEach(s -> output.append(s + "\n"));
        });

        frame.setVisible(true);
    }
}


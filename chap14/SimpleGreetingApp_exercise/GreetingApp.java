package advanced_java.chap14.SimpleGreetingApp_exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GreetingApp {
    public static void main(String[] args) {
        // Create the frame (window)
        JFrame frame = new JFrame("Greeting App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Create components
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(15);
        JButton button = new JButton("Say Hello");
        JLabel resultLabel = new JLabel("");

        // Add action (when button is clicked)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                if (!name.isEmpty()) {
                    resultLabel.setText("Hello, " + name + "!");
                } else {
                    resultLabel.setText("Please enter your name!");
                }
            }
        });

        // Add components to frame
        frame.add(label);
        frame.add(textField);
        frame.add(button);
        frame.add(resultLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}

package advanced_java.Chap15.exercise;

import javax.swing.*;
import java.awt.*;

public class exer2 {
    public exer2() {
        JFrame frame = new JFrame("exer2 Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.lightGray);
        JButton btn1 = new JButton("Button 1");
        JButton btn2 = new JButton("Button 2");
        topPanel.add(btn1);
        topPanel.add(btn2);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.darkGray);
        JButton btn3 = new JButton("Button 3");
        JButton btn4 = new JButton("Button 4");
        bottomPanel.add(btn3);
        bottomPanel.add(btn4);

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new exer2();
    }
}

package advanced_java.Chap15.exercise;

import javax.swing.*;
import java.awt.*;

public class exer3 {
    public exer3() {
        JFrame frame = new JFrame("exer3 Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");

        panel.add(buttonTwo);


        frame.getContentPane().add(button, BorderLayout.CENTER); // main button in center
        frame.getContentPane().add(panel, BorderLayout.EAST);    // gray panel on the right

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new exer3();
    }
}

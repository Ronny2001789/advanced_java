package advanced_java.Chap15.exercise;

import javax.swing.*;
import java.awt.*;

public class exer1 {

    public exer1() {
        JFrame frame = new JFrame("Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");

        panel.add(button);
        panel.add(buttonTwo);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new exer1();  // run constructor to create GUI
    }
}


package test;

import javax.swing.*;
import java.awt.*;

public class main {

    private JFrame frame;
    private JPanel p = new JPanel();
    private Image img;
    private JScrollPane scroll;

    public static void main(String[] args) {
        new main().createAndShowGui();
    }

    public void createAndShowGui() {
        frame = new JFrame("JFrame with bg example");

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        for (int i = 0; i < 20; i++) {
            p.add(new JLabel("label " + i));
        }
        scroll = new JScrollPane(p);
        frame.add(scroll);
        frame.setSize(frame.getPreferredSize());
        frame.setVisible(true);
        frame.setSize(600,600);
        p.setBounds(305,5,275,495);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
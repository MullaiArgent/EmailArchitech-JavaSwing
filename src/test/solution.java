package test;

import javax.swing.*;
import java.awt.*;

class solution extends JFrame {

    // frame
    static JFrame f;

    // label to display text
    static JLabel l;

    // main class
    public static void main(String[] args)
    {
        // create a new frame
        f = new JFrame("frame");

        // set layout of frame
        f.setLayout(new FlowLayout());

        // create a internal frame
        JInternalFrame in = new JInternalFrame("frame 1", true, false, true, true);

        // create a internal frame


        // create a Button
        JButton b = new JButton("button");


        // create a label to display text
        l = new JLabel("This is a JInternal Frame no 1 ");


        // create a panel
        JScrollPane scroll;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        for (int i = 0; i < 20; i++) {
            p.add(new JLabel("label " + i));
        }
        scroll = new JScrollPane(p);
        in.add(scroll);
        //in.setSize(in.getPreferredSize());
        in.setVisible(true);
        in.setResizable(true);
        in.setSize(600,600);
        p.setBounds(305,5,275,495);
        in.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add label and button to panel
        p.add(l);


        // set visibility internal frame
        in.setVisible(true);


        // add panel to internal frame
        in.add(p);


        // add internal frame to frame
        f.add(in);


        // set the size of frame
        f.setSize(300, 300);

        f.show();
    }
}

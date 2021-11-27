package ChatGUI;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {
    JScrollPane scroll;
    JTextArea textArea;
    JButton send;
    public ClientWindow(){
        scroll = new JScrollPane();
        textArea = new JTextArea();
        send = new JButton("Send");
        JLabel label = new JLabel();
        label.setBackground(Color.green);
        label.setSize(50, 50);
        label.setVisible(true);
        label.setLayout(null);
        scroll.add(label);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.GRAY);
        //this.setResizable(false);
        this.setSize(300, 600);
        this.setVisible(true);
        scroll.setBounds(5,5,275,495);
        textArea.setBackground(Color.white);
        textArea.setBounds(10,515,175,40);
        send.setBounds(210, 515, 70, 40);
        send.setFocusPainted(false);
        this.add(scroll);
        this.add(send);
        this.add(textArea);
    }

    public static void main(String[] args) {
        new ClientWindow();
    }
}

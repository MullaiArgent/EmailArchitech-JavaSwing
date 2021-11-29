package ChatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientWindow extends JFrame {
    JScrollPane scroll;
    JTextArea textArea;
    JButton send;
    public ClientWindow(String userName) throws SQLException, ClassNotFoundException {
        newUser();
        chattersWindow(userName);
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
        this.setResizable(false);
        this.setSize(600, 600);
        this.setVisible(true);
        this.setTitle(userName);
        scroll.setBounds(305,5,275,495);
        textArea.setBackground(Color.white);
        textArea.setBounds(305,515,195,40);
        send.setBounds(510, 515, 70, 40);
        send.setFocusPainted(false);
        this.add(scroll);
        this.add(send);
        this.add(textArea);
    }
    public void chattersWindow(String userName) throws SQLException, ClassNotFoundException {
        ResultSet noOfChatters = new Jdbc().dql("SELECT * FROM username", "chat");
        int y = 55;
        while(noOfChatters.next()) {
            JPanel chatterTab = new JPanel();
            JLabel chatterName = new JLabel();
            chatterTab.setBounds(5, y, 295, 50);
            chatterTab.setLayout(null);
            chatterName.setText(noOfChatters.getString(1));
            chatterName.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            chatterName.setBounds(10, y, 300, 50);
            chatterTab.setBackground(Color.lightGray);
            chatterName.setForeground(Color.BLUE);
            super.add(chatterName);
            super.add(chatterTab);
            chatterTab.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    chatterName.setForeground(Color.black);
                    chatterName.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    chatterName.setForeground(Color.blue);
                    chatterName.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
                }
            });

            y += 55;
        }
    }
    public void newUser(){
        Icon addUser = new ImageIcon("images/R.jpg");
        JLabel icon = new JLabel(addUser);
        icon.setBounds(5,5,40,40);
        icon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String chatter = JOptionPane.showInputDialog("Enter the UserName to chat with ");
                try {
                    ResultSet rs = new Jdbc().dql("SELECT USERNAME FROM USER","CHAT");
                    // TODO check for existing user
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                icon.setSize(45,45);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                icon.setSize(40,40);
            }
        });
        super.add(icon);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new ClientWindow("Test");
    }
}

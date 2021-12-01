package ChatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientWindow extends JFrame {

    JTextArea textArea;
    JButton send;
    JTextArea chatArea;
    String chatter;

    public ClientWindow(String userName, int port) throws SQLException, ClassNotFoundException, IOException {

        String ip = "localhost";
        int serverPort = 8888;
        Socket socket = new Socket(ip, serverPort, InetAddress.getByName(ip), port);
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        chatter = "";

        newChatter(userName);
        chattersWindow(userName);
        Icon addUser = new ImageIcon("images/reload.jpg");
        JLabel addUserIcon = new JLabel(addUser);
        addUserIcon.setBounds(45,5,40,40);
        addUserIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    renderChatArea("chatData/" + userName + "-" + chatter + ".txt");
                } catch (IOException ex) {
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
                addUserIcon.setSize(45,45);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addUserIcon.setSize(40,40);
            }
        });
        super.add(addUserIcon);

        chatArea = new JTextArea();
        textArea = new JTextArea();
        send = new JButton("Send");

        JLabel label = new JLabel();
        label.setBackground(Color.green);
        label.setSize(50, 50);
        label.setVisible(true);
        label.setLayout(null);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.GRAY);
        this.setResizable(false);
        this.setSize(600, 600);
        this.setVisible(true);
        this.setTitle(userName);

        chatArea.setLineWrap(true);
        chatArea.setBounds(305,5,275,495);
        chatArea.setLayout(new FlowLayout());
        textArea.setBackground(Color.white);
        textArea.setBounds(305,515,195,40);
        textArea.setLineWrap(true);
        send.setBounds(510, 515, 70, 40);
        send.setFocusPainted(false);
        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chatter.equals("")){
                String message = textArea.getText();
                if(!message.equals("")){
                textArea.setText("");
                    String address =  userName.trim() + "-" + chatter.trim();
                    String data = "You:" + chatter + ":" + message + "\n";
                    try {
                        ResultSet rs = new DataManagement().dql("SELECT * FROM USER","CHAT");
                        int r_port = 0;
                        while(rs.next()){
                            if(rs.getString(1).equals(chatter.toString())){
                                r_port = rs.getInt(5);
                            }
                        }
                        new DataManagement().dml("INSERT INTO "+ address +" VALUES("+ "" +
                                "" + chatter + "," +
                                ""+ r_port + "" +
                                ""+ data +"","CHAT");

                        renderChatArea(address);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }

                    out.println(chatter + ":" + userName + ":" + message); // to the server
                    out.flush();

                }
        }else{
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(null, "Select or Add a Chat");
                }
    }
});
        this.add(chatArea);
        this.add(send);
        this.add(textArea);
    }

    public void chattersWindow(String userName) throws SQLException, ClassNotFoundException {

        ResultSet noOfChatters = new DataManagement().dql("SELECT * FROM friend_list_" +userName , "chat");

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
                String address = "chatData/" + userName.trim() + "-" + chatterName.getText().trim() + ".txt";
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatter = chatterName.getText();
                    chatArea.setText("");
                    try {
                        new DataManagement().dml("CREATE TABLE "+ address + "(" +
                                " chatter VARCHAR(45) NOT NULL," +
                                " r_port INT NULL," +
                                " chat_data VARCHAR(255) NOT NULL)" , "chat");
                        renderChatArea(address);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
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

    public void newChatter(String userName){
        Icon addUser = new ImageIcon("images/addUser.jpg");
        JLabel icon = new JLabel(addUser);
        icon.setBounds(5,5,40,40);
        icon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String chatter = JOptionPane.showInputDialog("Enter the UserName to chat with ");
                try {
                    ResultSet rs = new DataManagement().dql("SELECT USERNAME FROM USER","CHAT");

                    Boolean chatterExist = false;

                    while(rs.next()){
                        if(chatter.equals(rs.getString(1))) {

                            chatterExist = true;

                            // TODO NEW FRIEND TO THE USER ADDED HERE.

                            new DataManagement().dml("INSERT INTO "+ "friend_list_"+ userName +" VALUES(" +
                                    ""+ chatter + ")","CHAT");

                            chattersWindow(userName);

                            break;
                        }
                    }if(!chatterExist){
                        JOptionPane.showMessageDialog(null,"UserName Doesn't Exist");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
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

    public void renderChatArea(String filename) throws IOException {

    // TODO LABELS FROM DB
        }
    // TODO Try adding lable into the scroll pane.
    // TODO Try adding the data into the DB instead of Text File

}
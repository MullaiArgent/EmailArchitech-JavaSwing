package ChatGUI;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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
    //JScrollPane scrollPane;

    public ClientWindow(String userName, int port) throws SQLException, ClassNotFoundException, IOException {
        //scrollPane = new JScrollPane();
        //scrollPane.setBounds(305,5,275,495);
        ImageIcon mastericon = new ImageIcon("images\\mastericon.png");
        this.setIconImage(mastericon.getImage());
        String ip = "localhost";
        int serverPort = 8888;
        Socket socket = new Socket(ip, serverPort, InetAddress.getByName(ip), port);
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        chatter = "";

        newChatter(userName);  // add user icon
        chattersWindow(userName);  // chatters list from db
        multiMediaClib();  // clib icon to share
        Icon addUser = new ImageIcon("images/reload.jpg");
        JLabel addUserIcon = new JLabel(addUser);
        addUserIcon.setBounds(45,5,40,40);
        addUserIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    renderChatArea(userName + "_" + chatter);
                    chattersWindow(userName);
                } catch (IOException | SQLException | ClassNotFoundException ex) {
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
        textArea.setBounds(340,515,195,40);
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
                    String address =  userName.trim() + "_" + chatter.trim();
                    String data = userName +":" + chatter + ":" + message + "\n";
                    try {
                        ResultSet rs = new DataManagement().dql("SELECT * FROM USER","CHAT");
                        int r_port = 0;
                        while(rs.next()){
                            if(rs.getString(1).equals(chatter.toString())){
                                r_port = rs.getInt(5);
                            }
                        }
                        // TODO CHAINS FROM THE CREATION BUG
                        System.out.println(data);

                        new DataManagement().dml("INSERT INTO "+ address +" VALUES("+
                                "'" + chatter + "'," +
                                "" + r_port + "," +
                                "'"+ data +"');","CHAT");

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
        //this.add(scrollPane);
    }

    private void multiMediaClib() {
        Icon clib = new ImageIcon("images\\clib.jpg");
        Icon sendImage = new ImageIcon("images\\sendImage.jpg");
        Icon hyperlink = new ImageIcon("images\\hyperlink.jpg");

        JLabel cliblable = new JLabel(clib);
        JLabel sendImageLabel = new JLabel(sendImage);
        JLabel hyperlinkLabel = new JLabel(hyperlink);

        sendImageLabel.setVisible(false);
        hyperlinkLabel.setVisible(false);



        cliblable.setBounds(305,515,40,40);
        sendImageLabel.setBounds(265, 480, 40,40);
        hyperlinkLabel.setBounds(305,480,40,40);

        cliblable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!sendImageLabel.isVisible()) {
                    sendImageLabel.setVisible(true);
                    hyperlinkLabel.setVisible(true);
                }else{
                    sendImageLabel.setVisible(false);
                    hyperlinkLabel.setVisible(false);
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
                cliblable.setBounds(305,515,45,45);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cliblable.setBounds(305,515,40,40);
            }
        });

        sendImageLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser j = new JFileChooser("./");
                j.showOpenDialog(null);
                j.getSelectedFile().getAbsolutePath(); // Selected Image
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sendImageLabel.setBounds(265, 480, 45,45);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendImageLabel.setBounds(265, 480, 40,40);
            }
        });
        hyperlinkLabel.addMouseListener(new MouseListener() {
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
                hyperlinkLabel.setBounds(305,480,45,45);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hyperlinkLabel.setBounds(305,480,40,40);
            }
        });
        this.add(cliblable);
        this.add(sendImageLabel);
        this.add(hyperlinkLabel);

    }

    public void chattersWindow(String userName) throws SQLException, ClassNotFoundException {

        ResultSet noOfChatters = new DataManagement().dql("SELECT * FROM friend_list_" +userName , "chat");ResultSet noOfChatters = new DataManagement().dql("SELECT * FROM friend_list_" +userName , "chat");

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
                String address = userName.trim() + "_" + chatterName.getText().trim();
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatter = chatterName.getText();
                    //chatArea.setText("");
                    try {

                        renderChatArea(address);

                    } catch (FileNotFoundException | SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
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
                if(!(chatter == null)) {
                    try {
                        ResultSet rs = new DataManagement().dql("SELECT USERNAME FROM USER", "CHAT");

                        Boolean chatterExist = false;

                        while (rs.next()) {
                            if (chatter.equals(rs.getString(1))) {

                                chatterExist = true;
                                System.out.println("userName " + userName);
                                System.out.println("chatter " + chatter);
                                String address_s = userName.trim() + "_" + chatter.trim();
                                String address_r = chatter.trim() + "_" + userName.trim();

                                new DataManagement().dml("INSERT INTO friend_list_" + userName.trim() + " VALUES ('" + chatter + "');", "CHAT");
                                new DataManagement().dml("INSERT INTO friend_list_" + chatter.trim() + " VALUES ('" + userName + "');", "CHAT");
                                new DataManagement().dml("CREATE TABLE " + address_s + "(" +
                                        " chatter VARCHAR(45) NOT NULL," +
                                        " r_port INT NULL," +
                                        " chat_data VARCHAR(255) NOT NULL);", "chat");
                                new DataManagement().dml("CREATE TABLE " + address_r + "(" +
                                        " chatter VARCHAR(45) NOT NULL," +
                                        " r_port INT NULL," +
                                        " chat_data VARCHAR(255) NOT NULL);", "chat");

                                chattersWindow(userName);

                                break;
                            }
                        }
                        if (!chatterExist) {
                            JOptionPane.showMessageDialog(null, "UserName Doesn't Exist");
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
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

    public void renderChatArea(String filename) throws IOException, SQLException, ClassNotFoundException {

            ResultSet chats = new DataManagement().dql("SELECT chat_data FROM "+ filename , "CHAT");
            int y = 5;
            while(chats.next()){
                String chat = chats.getString(1);
                    JLabel l = new JLabel(chat);
                    l.setBounds(5,y, 230, 25);
                    //scrollPane.add(l);
                    chatArea.setText(chat);
                    y += 25;
         }
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        new ClientWindow("mullai", 6789);
    }
}
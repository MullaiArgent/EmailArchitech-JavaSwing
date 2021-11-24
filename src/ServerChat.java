
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends JFrame{
    JTextField msgText;
    JTextArea msgArea;
    JButton send;

    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dOut;

    public ServerChat() {
        msgArea = new JTextArea();
        msgText = new JTextField();
        send = new JButton();
        this.getContentPane().setBackground(Color.BLACK);
        this.setTitle("Server");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        msgArea.setSize(200, 200);
        msgArea.setBounds(20,20,200, 200);
        send.setText("Send");
        send.setBounds(200, 190, 100, 40);
        msgText.setBounds(20, 201, 200, 40);
        this.add(send);
        this.add(msgArea);
        this.add(msgText);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgOut = "";
                msgOut = msgText.getText().trim();
                try {
                    dOut.writeUTF(msgOut);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void  main() {
        String msgIn = "";
        try{
            ss = new ServerSocket(8888); //server
            s = ss.accept();

            din  = new DataInputStream(s.getInputStream());
            dOut = new DataOutputStream(s.getOutputStream());

            while (!msgIn.equals("exit")){
                msgIn = din.readUTF();
                msgArea.setText(msgText.getText().trim() + "\n" + msgIn);
            }
        }catch (Exception e){
            System.out.println();
        }
    }
}
class MainServer {
    public static void main(String[] args) throws Exception {
        new ServerChat().main();
    }
}

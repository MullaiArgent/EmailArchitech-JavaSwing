import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientChat extends JFrame{
    JTextField msgText;
    JTextArea msgArea;
    JButton send;

    static Socket s;
    static DataInputStream dIn;
    static DataOutput dOut;

    public ClientChat() {
        msgArea = new JTextArea();
        msgText = new JTextField();
        send = new JButton();
        this.getContentPane().setBackground(Color.BLACK);
        this.setTitle("Client");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        msgArea.setSize(200, 200);
        msgArea.setBounds(20, 20, 200, 200);
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

    public void main() throws Exception {
        try{
            s = new Socket("localhost", 8888);
            dIn = new DataInputStream(s.getInputStream());
            dOut = new DataOutputStream(s.getOutputStream());
            String msgIn = "";
            while(!msgIn.equals("exit")){
                msgIn = dIn.readUTF();
                msgArea.setText(msgArea.getText().trim()+"Server:\t"+msgIn);

            }
        }catch (Exception e){
            System.out.println("e"+ e);
        }
    }
}
class MainClient {
    public static void main(String[] args) throws Exception {
        new ClientChat().main();
    }
}
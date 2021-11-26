package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientChat1 extends JFrame {
    JTextField msgText;
    JTextArea msgArea;
    JButton send;

    static Socket s;
    static DataInputStream dIn;
    static DataOutput dOut;

    public ClientChat1() throws IOException {
        msgArea = new JTextArea();
        msgText = new JTextField();
        send = new JButton();

        this.getContentPane().setBackground(Color.GRAY);
        this.setTitle("Client");
        this.setVisible(true);
        this.setSize(300, 450);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        msgArea.setBounds(20, 20, 200, 200);
        send.setText("Send");
        send.setBounds(130, 230, 100, 40);
        msgText.setBounds(20, 300, 200, 40);
        this.setLayout(null);
        this.add(send);
        this.add(msgArea);
        this.add(msgText);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgOut = "yeah";
                msgOut = msgText.getText().trim();
                msgText.setText("");
                System.out.println(msgOut);
                try {

                    OutputStreamWriter opw = new OutputStreamWriter(s.getOutputStream());

                    PrintWriter pw = new PrintWriter(opw);

                    pw.write(msgOut);

                    pw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        s = new Socket("localhost", 8888);
        dIn = new DataInputStream(s.getInputStream());
        //dOut = new DataOutputStream(s.getOutputStream());
        String msgIn = "";
        while (!msgIn.equals("exit")) {
            msgIn = dIn.readUTF();
            msgArea.setText(msgArea.getText().trim() + "test.Server:\t" + msgIn);
        }
    }

    static class MainClient {
        public static void main(String[] args) throws Exception {
            new ClientChat1();
        }
    }
}
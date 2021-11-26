package c2c;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends JFrame {
    private static Client client;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String userName;
    private JTextArea msgArea = new JTextArea();
    private JTextArea textArea = new JTextArea();
    private JButton send = new JButton("Send");
    String messageToSend;
    static Boolean flag = false;
    public Client (Socket socket) throws IOException {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 600);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);
        textArea.setBackground(Color.white);
        textArea.setBounds(5,5,275,500);
        msgArea.setBounds(5, 515, 205, 40);
        send.setBounds(210, 515, 70, 40);
        send.setFocusPainted(false);
        msgArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                client.sendMessage();
                client.listenForMessage();
            }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        this.add(textArea);
        this.add(msgArea);
        //this.add(send);
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try{
            if(socket != null){
                socket.close();
            }if (bufferedReader != null){
                bufferedReader.close();
            }if (bufferedWriter != null){
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        try{
                messageToSend = msgArea.getText();
            System.out.println(messageToSend);
                msgArea.setText("");
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();


        }catch (IOException e){
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()){
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        String temp = textArea.getText();
                        textArea.setText(" 54 ");
                        client.textArea.setText(temp + "\n" + msgFromGroupChat);
                        System.out.println(msgFromGroupChat);
                        System.out.println(temp);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }

                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Enter the Username");
        String username = scanner.nextLine();
        client = new Client(socket);
        client.userName = username;
        client.textArea.setText("Enter the Username and Password");
    }


}

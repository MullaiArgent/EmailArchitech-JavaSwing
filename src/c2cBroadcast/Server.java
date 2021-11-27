package c2cBroadcast;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {
    public ServerSocket serverSocket;
    private JTextArea textArea;
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 150);
        this.setVisible(true);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);
        this.setTitle("Server");
        textArea = new JTextArea("No Clients Available");
        textArea.setBounds(5,5,250,140);
        textArea.setBackground(Color.white);
        this.add(textArea);
    }

    public void startServer(){
            try {
                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    textArea.setText("\r");
                    textArea.setText("A New Chat has been entered");
                    ClientHandler clientHandler = new ClientHandler(socket);
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

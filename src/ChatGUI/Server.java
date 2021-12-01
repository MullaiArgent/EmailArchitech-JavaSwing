package ChatGUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void closeEverything(Socket socket) throws IOException {
        socket.close();
    }
    Server() throws IOException {
        Socket socket = new Socket();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8888);
            while(!Thread.interrupted()){
                socket = serverSocket.accept();
                System.out.println("A Client has been connected to the Server");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeEverything(socket);
        }
    }
    public static void main(String[] args) throws IOException {
        while(true) {
            new Server();
        }
    }
}

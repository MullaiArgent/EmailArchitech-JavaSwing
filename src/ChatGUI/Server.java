package ChatGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void closeEverything(Socket socket) throws IOException {
        socket.close();
    }
    Server() throws IOException {
        Socket socket = new Socket();
        BufferedReader bufferedReader;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8888);
            while(!Thread.interrupted()){
                socket = serverSocket.accept();
                System.out.println("A Client has been connected to the server");
                while (!socket.isClosed()) {

                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientsData = bufferedReader.readLine();
                    System.out.println(clientsData);

                }
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

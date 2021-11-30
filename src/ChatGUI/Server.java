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
                System.out.println("A Client has been connected to the Server");
                while (!socket.isClosed()) {

                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientsData = bufferedReader.readLine();
                    System.out.println(clientsData);

                    StringBuilder address1 = new StringBuilder("chatData/");
                    StringBuilder address2 = new StringBuilder();
                    StringBuilder data = new StringBuilder();
                    int counter = 0;
                    for(int i = 0; i < clientsData.length();i++){
                        if(clientsData.charAt(i) == ':'){
                            counter++;
                            continue;
                        }
                        if(counter==0){
                            address1.append(clientsData.charAt(i));
                        }
                        if (counter==1){
                            address2.append(clientsData.charAt(i));
                        }
                        if (counter==2){
                            data.append(clientsData.charAt(i));
                        }
                    }
                    String address  = address1.toString().trim() + "-" +address2.toString().trim() + ".txt";

                    new DataManagement().fileUpdate(address,address2 + ":You:" + data.toString());
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

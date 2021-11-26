package c2c;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String clientUserName;
    public ClientHandler(Socket socket) throws IOException {
        try {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server :" + clientUserName + "has entered the chat");

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void broadcastMessage(String messageFromClient) {
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientUserName.equals(clientUserName)){

                    clientHandler.bufferedWriter.write(clientUserName+": "+messageFromClient);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }if (bufferedWriter != null){
                bufferedWriter.close();
            }if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("Server : " + clientUserName + " has left the Chat");
    }


    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

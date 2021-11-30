package ChatGUI;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    BufferedReader bufferedReader;
    public ClientHandler(Socket socket) throws IOException {
        try {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientHandlers.add(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(socket.isConnected()){
            try{
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8888);
        System.out.println("1");
        while(!Thread.interrupted()) {
            Socket s = ss.accept();
            DataInputStream dIn = new DataInputStream(s.getInputStream());
            DataOutputStream dOut = new DataOutputStream((s.getOutputStream()));
            dOut.writeUTF("vOUFUV");

            String str = "";

            try {
                str = dIn.readUTF();
                System.out.println(str);

            } catch (Exception e) {
                System.out.println("5.2" + e);
            }
            System.out.println(str);
        }

    }
}

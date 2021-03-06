package ChatGUI;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Registration {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        String userName;
        String mailId;
        String password;
        int port;
        Boolean newUser = true;
        ResultSet rs = new DataManagement().dql("SELECT * FROM USER", "CHAT");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the UserName : ");
        userName = scanner.nextLine();
        System.out.print("Enter the Email ID : ");
        mailId = scanner.nextLine();

        while(rs.next()){
            if(rs.getString(2).equals(mailId)){
                newUser = false;
                System.out.println("Already a User");
                System.out.print("Enter your Password : ");
                int attemptLeft = 3;
                while(attemptLeft >= 1){

                    if(rs.getString(3).equals(scanner.nextLine())){
                        port = rs.getInt(5);
                        new ClientWindow(userName, port);
                        attemptLeft=0;
                    }else{
                        System.out.println("Attempts left : " + attemptLeft);
                    }

                    attemptLeft--;
                }
            }
        }
        if (newUser) {
            System.out.print("Enter your Password : ");
            password = scanner.nextLine();
            System.out.print("Enter the port number you want to work with : ");
            port = Integer.parseInt(scanner.nextLine());
            // CAST(N'2012-06-18 10:34:09.000' AS DateTime),
            String stamp = "CAST(N'"+ LocalDateTime.now() + "' AS DATETIME)";

            new DataManagement().dml("CREATE TABLE "+ "friend_list_"+ userName.trim() + "(" +
                    " chatters VARCHAR(45) NOT NULL)" , "chat");

            int rowsAffacted = new DataManagement().dml("INSERT INTO USER VALUES ('" + userName.trim() + "'," +
                                                                             "'" + mailId.trim()   + "'," +
                                                                             "'" + password.trim() + "'," +
                                                                             " " + stamp.trim()    + ","  +
                                                                             " " + port            + ")","chat");
            System.out.println("Number of rows affected : "+ rowsAffacted);

            new ClientWindow(userName.trim(), port);
        }
    }
}

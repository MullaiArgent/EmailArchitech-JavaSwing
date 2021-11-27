package ChatGUI;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;
class Jdbc {
    String username = "root";
    String password = "@TeslaMysql2000";

    public ResultSet dql (String query, String db) throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/" + db.trim();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,username, password);
        Statement st = con.createStatement();
        return st.executeQuery(query);
    }
    public int dml (String query, String db) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/" + db.trim();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,username, password);
        Statement st = con.createStatement();
        return st.executeUpdate(query);
    }
}
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String userName;
        String mailId;
        String password;
        int port;
        Boolean newUser = true;
        ResultSet rs = new Jdbc().dql("select * from user", "chat");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Name : ");
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
                        //TODO ALREADY A USER PORTION
                    }
                    System.out.println("Attempts left : " + attemptLeft);
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
            int rowsAffacted = new Jdbc().dml("INSERT INTO USER VALUES ('" + userName.trim() + "'," +
                                                                             "'" + mailId.trim()   + "'," +
                                                                             "'" + password.trim() + "'," +
                                                                             " " + stamp.trim()    + "," +
                                                                             " " + port            + ")","chat");
            System.out.println("number of rows affacted : "+ rowsAffacted);
        }


    }

}

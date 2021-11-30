package ChatGUI;

import java.io.*;
import java.sql.*;

public class DataManagement {
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
    public void fileUpdate(String path, String text) throws IOException {
            FileWriter fw = new FileWriter(path, true);
            fw.write(text+ "\n");
            fw.close();
    }
}


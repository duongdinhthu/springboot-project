package springboot.springboot.relearnConectCRUD.connectDTB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {
    
    public static Connection getMySQLConnection() throws SQLException {
        Connection conn = null;
        String hostName = "127.0.0.1";//127.0.1 hoac localhost
        String dbName = "ordermanager";
        String userName = "root";
        String password = "";
        //String connection( chuoi ket noi )
        String connectionURL = "jdbc:mysql://"+hostName+":3306/"+dbName;
        conn = DriverManager.getConnection(connectionURL,userName,password);
        return conn;
    }
//    public static void main(String[] args) throws SQLException {
//        if (getMySQLConnection() != null) {
//            System.out.println("Connect Database success!");
//        }
//        //get connection:goi doi tuong ket noi la connection de su dung
//        Connection connection = getMySQLConnection();// tra ve 1 doi tuong connection
//
//    }

}

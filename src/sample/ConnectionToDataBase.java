package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDataBase {
    //подключение к базе данных
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/dima","root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("не подключился!");
        }

        return connection;
    }
}

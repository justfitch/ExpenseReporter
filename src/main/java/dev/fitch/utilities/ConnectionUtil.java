//This class handles the connection to the database

package dev.fitch.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){

        try {
            //jdbc:postgresql://fitch-db.cm6g5z6kg1yo.us-west-1.rds.amazonaws.com/expensedb?user=postgres&password=AWSpassword1
            //Connection conn = DriverManager.getConnection(System.getenv("expensedb"));
            Connection conn = DriverManager.getConnection("jdbc:postgresql://fitch-db.cm6g5z6kg1yo.us-west-1.rds.amazonaws.com/expensedb?user=postgres&password=AWSpassword1");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

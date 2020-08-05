package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DBConst.DBURL, DBConst.USERNAME, DBConst.PASSWORD);
        return connection;
    }
}

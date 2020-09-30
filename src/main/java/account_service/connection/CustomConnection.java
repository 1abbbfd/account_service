package account_service.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomConnection {
    final static String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;" +
            "INIT=RUNSCRIPT FROM './src/main/resources/schema.sql'\\;" +
            "RUNSCRIPT FROM './src/main/resources/data.sql'";
    private static Connection connection;

    private CustomConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url);
                return connection;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}

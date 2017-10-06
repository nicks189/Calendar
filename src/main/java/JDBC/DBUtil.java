package JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class DBUtil {
    public static Connection getConnection(DBType type) throws SQLException {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            switch (type) {
                // Currently only support MySQL
                case MYSQL:
                    in = new FileInputStream("src/main/resources/db.properties");
                    props.load(in);
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String passwd = props.getProperty("db.passwd");
                    return DriverManager.getConnection(url, user, passwd);
            }
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(RetrieveAll.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger lgr = Logger.getLogger(RetrieveAll.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        System.exit(-1);
        // Redundant return stmt
        return null;
    }

    public static void processException(SQLException e) {
        Logger lgr = Logger.getLogger(RetrieveAll.class.getName());
        lgr.log(Level.SEVERE, e.getMessage(), e);
        lgr.log(Level.SEVERE, e.getSQLState(), e);
    }
}

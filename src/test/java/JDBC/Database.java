package JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class Database {
    private static Database instance = new Database();

    public static Database getSingleton() { return instance; }

    private DBType dbType = DBType.MYSQL;

    private Connection conn = null;

    private Database() {}

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    private boolean openConnection() {
        Properties props = new Properties();
        FileInputStream in = null;

        try {

            switch (dbType) {
                // Currently only supports MySQL
                case MYSQL:
                    in = new FileInputStream("src/main/resources/db.properties");
                    props.load(in);
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String passwd = props.getProperty("db.passwd");
                    conn = DriverManager.getConnection(url, user, passwd);
            }

        } catch (IOException | SQLException ex) {

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

        return conn != null;
    }

    public Connection getConnection() {
        if (conn == null) {
            if (openConnection()) {
                return conn;
            } else {
                return null;
            }
        }
        return conn;
    }

    public void close() {
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {}
    }

    public static void processException(SQLException e) {
        Logger lgr = Logger.getLogger(RetrieveAll.class.getName());
        lgr.log(Level.SEVERE, e.getMessage(), e);
        lgr.log(Level.SEVERE, e.getSQLState(), e);
    }
}

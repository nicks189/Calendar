package JDBC;

import java.sql.*;

public class RetrieveAll {

    public static void main(String[] args) throws SQLException {
        Connection conn = Database.getSingleton().getConnection();

        try (
                CallableStatement cst = conn.prepareCall("{CALL GET_ALL_NOTES()}",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = cst.executeQuery();
                ) {

            System.out.print(Notes.displayData(rs));

        } catch (SQLException ex) {

            Database.processException(ex);

        }
    }
}

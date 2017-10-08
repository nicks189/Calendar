package JDBC;

import java.sql.*;

public class RetrieveAll {

    public static void main(String[] args) throws SQLException {

        try (
                Connection con = DBUtil.getConnection(DBType.MYSQL);
                CallableStatement cst = con.prepareCall("{CALL GET_ALL_NOTES()}",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = cst.executeQuery();
                ) {

            System.out.print(Notes.displayData(rs));

        } catch (SQLException ex) {

            DBUtil.processException(ex);

        }
    }
}

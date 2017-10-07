package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveAll {

    public static void main(String[] args) throws SQLException {

        try (
                Connection con = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM notes");
                ResultSet rs = pst.executeQuery();
                ) {

            while (rs.next()) {
                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.print(rs.getString("date"));
                System.out.print(" ");
                System.out.println(rs.getString(3));
            }

        } catch (SQLException ex) {

            DBUtil.processException(ex);

        }
    }
}

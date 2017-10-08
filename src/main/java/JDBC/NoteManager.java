package JDBC;

import JDBC.beans.Note;
import com.sun.org.apache.regexp.internal.RESyntaxException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteManager {

    public static String getAllRows() throws SQLException {
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                CallableStatement stmt = conn.prepareCall("{CALL GET_ALL_NOTES()}");
                ResultSet rs = stmt.executeQuery();
                ) {

            StringBuffer buffer = new StringBuffer();
            while (rs.next()) {

                buffer.append("ID: " + rs.getInt("id") + " - ");
                buffer.append("Date: " + rs.getString("date") + " - ");
                buffer.append("Info: " + rs.getString("info"));
                buffer.append("\n");

            }
            return buffer.toString();
        }
    }

    public static Note getRow(int id) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM notes WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement pst = conn.prepareStatement(sql);
                ) {

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                return new Note(id, rs.getDate("date"), rs.getString("info"));
            } else {
                return null;
            }

        } catch(SQLException e) {
             DBUtil.processException(e);
             return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static boolean insert(Note n) throws SQLException {
        ResultSet keys = null;
        String sql = "INSERT INTO notes (date, info) VALUES (?, ?)";

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {

            pst.setDate(1, n.getDate());
            pst.setString(2, n.getInfo());
            int affected = pst.executeUpdate();

            if (affected == 1) {
                keys = pst.getGeneratedKeys();
                keys.next(); // Move result set to first and only element
                n.setId(keys.getInt(1)); // Set Notes id to matching database id
            } else {
                return false;
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
        return true;
    }
}

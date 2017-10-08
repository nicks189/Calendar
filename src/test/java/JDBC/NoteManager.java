package JDBC;

import JDBC.beans.Note;

import java.sql.*;

public class NoteManager {

    private static Connection conn = Database.getSingleton().getConnection();

    public static String getAllRows() throws SQLException {
        try (
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
             Database.processException(e);
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
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {

            pst.setDate(1, n.getDate());
            pst.setString(2, n.getInfo());

            if (pst.executeUpdate() == 1) {
                keys = pst.getGeneratedKeys();
                keys.next(); // Move result set to first and only element
                n.setId(keys.getInt(1)); // Set Notes id to matching database id
            } else {
                return false;
            }

        } catch (SQLException e) {
            Database.processException(e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
        return true;
    }

    public static boolean update(Note n) {
        String sql = "UPDATE notes SET date = ?, info = ? WHERE id = ?";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ) {

            pst.setDate(1, n.getDate());
            pst.setString(2, n.getInfo());
            pst.setInt(3, n.getId());

            if(pst.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Database.processException(e);
            return false;
        }
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ) {

            pst.setInt(1, id);

            if(pst.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Database.processException(e);
            return false;
        }
    }
}

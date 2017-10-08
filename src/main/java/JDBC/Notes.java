package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Notes {

    public static String displayData(ResultSet rs) throws SQLException {
        StringBuffer buffer = new StringBuffer();
        if(!rs.next()) {
            buffer.append("No notes were found.");
        } else {

            do {
                buffer.append(rs.getString("date") + ": ");
                buffer.append(rs.getString("info"));
                buffer.append("\n");
            } while (rs.next());

        }

        return buffer.toString();
    }
}

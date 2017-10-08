package JDBC;

import JDBC.beans.Note;

import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.print(NoteManager.getAllRows());

        Note n = new Note();
        Date date = InputHelper.getDateInput("Date (yyyy-MM-dd): ");

        if (date == null) {
            System.out.println("Couldn't parse date from input.");
            return;
        }

        n.setDate(date);
        n.setInfo(InputHelper.getInput("Info: "));

        if (NoteManager.insert(n)) {
            System.out.println("New row with primary key " + n.getId() + " was inserted.");
        }
    }
}

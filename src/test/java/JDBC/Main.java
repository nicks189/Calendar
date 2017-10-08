package JDBC;

import JDBC.beans.Note;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.print(NoteManager.getAllRows());

        Database.getSingleton().setDbType(DBType.MYSQL);

        // ### INSERT ###
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

        // ### DELETE ###
        // int id = InputHelper.getIntegerInput("Select a row to delete: ");
        // if(NoteManager.delete(id)) {
        //     System.out.println("Row deleted successfully.");
        // } else {
        //     System.out.println("Deletion failed.");
        // }

        Database.getSingleton().close();
    }
}

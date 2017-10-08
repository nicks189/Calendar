package JDBC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InputHelper {

    public static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static int getIntegerInput(String prompt) {
        String intput = getInput(prompt);
        return Integer.parseInt(intput);
    }

    public static Date getDateInput(String prompt) {
        Scanner scnr = new Scanner(System.in);
        System.out.print(prompt);

        String d = scnr.next();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return new java.sql.Date(format.parse(d).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}

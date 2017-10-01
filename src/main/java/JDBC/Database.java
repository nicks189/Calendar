package JDBC;

public class Database {
    private static Database instance = new Database();

    public static Database getSingleton() { return instance; }

    private Database() {

    }
}

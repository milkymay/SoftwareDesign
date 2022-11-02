import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabase {
    static void create() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void add(String name, String price) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void clear() throws SQLException {
        try (Statement statement = DriverManager.getConnection("jdbc:sqlite:test.db").createStatement()) {
            String sqlQuery = "DELETE FROM PRODUCT";
            statement.executeUpdate(sqlQuery);
        }
    }

    static void drop() throws SQLException {
        try (Statement statement = DriverManager.getConnection("jdbc:sqlite:test.db").createStatement()) {
            String sqlQuery = "DROP TABLE PRODUCT";
            statement.executeUpdate(sqlQuery);
        }
    }
}

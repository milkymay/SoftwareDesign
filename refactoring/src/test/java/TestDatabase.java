import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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

    static Set<Utils.Pair> get() {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "SELECT * FROM PRODUCT";
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                Set<Utils.Pair> items = new HashSet<>();
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    items.add(new Utils.Pair(name, price));
                }
                stmt.close();
                return items;
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

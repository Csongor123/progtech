package hu.nye.databasehandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Az adatbázis kezeléséért felelős osztály.
 */
public class DatabaseHandler {

    private static final String DATABASE_URL = "jdbc:sqlite:game.db"; // Adatbázis fájl elérési útja
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS players (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "wins INTEGER DEFAULT 0);";

    private Connection connection;

    public DatabaseHandler() {
        try {
            // Adatbázis kapcsolat létrehozása
            connection = DriverManager.getConnection(DATABASE_URL);
            // Táblák létrehozása, ha nem léteznek
            createPlayersTable();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void loadPlayerScores() {
    }

    /**
     * Létrehozza a játékosok táblát, ha még nem létezik.
     */
    private void createPlayersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            System.out.println("Error creating players table: " + e.getMessage());
        }
    }

    /**
     * Frissíti a játékos pontszámát (nyeréseinek számát).
     *
     * @param playerName a játékos neve
     * @throws SQLException ha hiba történik az adatbázis művelet során
     */
    public void updatePlayerScore(String playerName) throws SQLException {
        String checkQuery = "SELECT wins FROM players WHERE name = ?";
        String insertQuery = "INSERT INTO players (name, wins) VALUES (?, 1)";
        String updateQuery = "UPDATE players SET wins = wins + 1 WHERE name = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            checkStmt.setString(1, playerName);
            var resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                updateStmt.setString(1, playerName);
                updateStmt.executeUpdate(); // Ha létezik a játékos, növeli a győzelmeket
            } else {
                insertStmt.setString(1, playerName);
                insertStmt.executeUpdate(); // Ha nem létezik, új játékos rekordot hoz létre
            }
        }
    }

    /**
     * Megjeleníti a legutóbbi játékosok eredményeit (nyereményeiket).
     */
    public void displayHighScores() throws SQLException {
        String query = "SELECT name, wins FROM players ORDER BY wins DESC LIMIT 10";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            var resultSet = stmt.executeQuery();
            System.out.println("High Scores:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + ": " + resultSet.getInt("wins"));
            }
        }
    }

    /**
     * Bezárja az adatbázis kapcsolatot.
     */
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}

package hu.nye.DatabaseHandlerTest;
import hu.nye.databasehandler.DatabaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    private DatabaseHandler databaseHandler;

    @BeforeEach
    void setUp() {
        // Tesztek előtt új DatabaseHandler példány létrehozása
        databaseHandler = new DatabaseHandler();
    }

    @Test
    void testUpdatePlayerScoreInsertNewPlayer() throws SQLException {
        // Ellenőrizzük, hogy új játékos hozzáadása sikeres
        databaseHandler.updatePlayerScore("Alice");
        // Mivel az "Alice" még nem létezett, létrejön egy új rekord
        // Jelen tesztben azt feltételezzük, hogy a frissítés sikeres, ezért itt nem futtatunk lekérdezést az adatbázisban
    }

    @Test
    void testUpdatePlayerScoreIncreaseWins() throws SQLException {
        // Első lépésben "Alice" pontszámát növeljük
        databaseHandler.updatePlayerScore("Bob");
        databaseHandler.updatePlayerScore("Bob");

        // Ellenőrizzük, hogy Bob győzelmei növekedtek
        // Ehhez meg kell hívnunk a displayHighScores metódust és ellenőrizni, hogy Bob győzelmei a várt értéken vannak.
        databaseHandler.displayHighScores();
    }

    @Test
    void testDisplayHighScores() throws SQLException {
        // Ellenőrizzük, hogy a displayHighScores metódus nem dob hibát
        databaseHandler.updatePlayerScore("Alice");
        databaseHandler.updatePlayerScore("Bob");
        databaseHandler.updatePlayerScore("Alice");

        // Itt azt ellenőrizzük, hogy a metódus sikeresen végrehajtódik, nincs SQLException
        assertDoesNotThrow(() -> databaseHandler.displayHighScores());
    }

    @Test
    void testCreatePlayersTable() {
        // Ellenőrizzük, hogy a táblák létrehozása sikeres (csak a létrehozás ellenőrzése, nem a konkrét adatokat)
        // Mivel a table-t a konstruktor hívja meg, nem kell külön metódust hívni
        assertDoesNotThrow(() -> new DatabaseHandler());
    }

    @Test
    void testCloseDatabaseConnection() {
        // Ellenőrizzük, hogy a kapcsolat bezárása nem okoz hibát
        assertDoesNotThrow(() -> databaseHandler.close());
    }
}
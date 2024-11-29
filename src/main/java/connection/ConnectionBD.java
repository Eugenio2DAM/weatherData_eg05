package connection;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dataConnection.DataAccesFunctionsConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.LoggerFactory;

/**
 * Classe per gestionar les connexions a bases de dades MySQL i MongoDB. Conté
 * mètodes per obtenir connexions, tancar recursos i gestionar la connexió amb
 * les bases de dades.
 *
 * @author eugenio
 */
public class ConnectionBD {

    private static MongoDatabase database;
    private static MongoClient mongo;

    private static final String DB_NAME = "WeatherData"; // Nom de la base de dades
    private static final String JDBC_URL = buildConnectionString(DB_NAME);//"jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "MENXureta82";
    private static Connection connectionMySQL;
    private static boolean isConnected = false;

    /**
     * Obté la connexió a la base de dades MySQL. Si la connexió no existeix o
     * està tancada, la crea.
     *
     * @return Connexió a la base de dades MySQL.
     */
    public static Connection getConnectionMySQL() {

        //Tools.createFileCreateDatebase(FILE_DATEBASE_CREATED);
        try {
            if (connectionMySQL == null || connectionMySQL.isClosed()) {
                if (!DataAccesFunctionsConnection.checkedDataBase()) {
                    DataAccesFunctionsConnection.createDataBase();
                }
                connectToMySQL(); // Crear connexió si no existeix o està tancada
            }
        } catch (SQLException ex) {
            System.err.println("\nError al verificar la connexió: " + ex.getMessage());
        }
        if (connectionMySQL == null) {
            throw new IllegalStateException("No s'ha pogut establir la connexió a MySQL.");
        }
        return connectionMySQL;
    }

    /**
     * Connecta a la base de dades MySQL amb les credencials proporcionades.
     *
     * @throws SQLException Si hi ha un error en establir la connexió.
     */
    private static void connectToMySQL() throws SQLException {
        connectionMySQL = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        if (!isConnected) {
            System.out.println("\nConnexió establerta correctament a " + DB_NAME + " correctament.\n");
            isConnected = true;
            DataAccesFunctionsConnection.createtableUser();
            DataAccesFunctionsConnection.createtableCredentials();
            DataAccesFunctionsConnection.createtableWeatherData();
        }
    }

    /**
     * Tanca el recurs ResultSet de la base de dades.
     *
     * @param result El ResultSet a tancar.
     */
    public static void closeResource(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException ex) {
                System.err.println("\nError tancant ResultSet: " + ex.getMessage());
            }
        }
    }

    /**
     * Tanca el recurs Statement de la base de dades.
     *
     * @param statement El Statement a tancar.
     */
    public static void closeResource(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.err.println("\nError tancant Statement: " + ex.getMessage());
            }
        }
    }

    /**
     * Tanca la connexió a la base de dades. Si la connexió és a MySQL, també la
     * reinicia.
     *
     * @param connection La connexió a tancar.
     */
    public static void closeResource(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                if (connection == connectionMySQL) {
                    connectionMySQL = null; // Si tanquem la connexió estàtica, reiniciar-la
                }
            } catch (SQLException ex) {
                System.err.println("\nError tancant Connection: " + ex.getMessage());
            }
        }
    }

    /**
     * Construeix la cadena de connexió JDBC per a MySQL.
     *
     * @param dbName Nom de la base de dades.
     * @return La cadena de connexió JDBC.
     */
    private static String buildConnectionString(String dbName) {
        if (dbName == null || dbName.isEmpty()) {
            throw new IllegalArgumentException("El nom de la bas de dades es obligatori.");
        }
        String host = "localhost"; // Direcció del servidor
        int port = 3306; // Port per defecte de MySQL
        String useSSL = "false"; // No utilitzar SSL
        String useTimezone = "true"; // Utilitzar la zona horària
        String serverTimezone = "UTC"; // Establir la zona horària en UTC
        String allowPublicKeyRetrieval = "true"; // Permetre la recuperació de clau pública

        // Construir i retornar la cadena de connexió sense contrasenya
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useSSL=" + useSSL
                + "&useTimezone=" + useTimezone
                + "&serverTimezone=" + serverTimezone
                + "&allowPublicKeyRetrieval=" + allowPublicKeyRetrieval;
    }

    /**
     * Connecta a la base de dades MongoDB. Si la connexió ja està establerta,
     * no fa res.
     */
    private static void connectMongo() {
        if (mongo == null) {
            try {
                disableMongoLogging();
                mongo = MongoClients.create("mongodb://localhost:27017");
                database = mongo.getDatabase(DB_NAME);
                DataAccesFunctionsConnection.createCollection();
                System.out.println("\nConnexió establerta correctament a " + database.getName() + " correctament.\n");
            } catch (Exception e) {
                System.out.println("\nImpossible realitzar la connexió: " + e.getMessage());
            }
        }
    }

    /**
     * Obté la connexió a la base de dades MongoDB. Si la connexió no està
     * establerta, la crea.
     *
     * @return La connexió a la base de dades MongoDB.
     */
    public static MongoDatabase getConnectionMongoBD() {
        if (database == null) {
            connectMongo();
        }
        return database;
    }

    /**
     * Tanca la connexió a la base de dades MongoDB.
     *
     */
    public static void closeConnection() {
        if (mongo != null) {
            mongo.close();
            mongo = null;
            database = null;
            System.out.println("\nConnexió tancada.");
        }
    }

    /**
     * Desactiva els logs de MongoDB per evitar que es mostrin massa detalls.
     */
    private static void disableMongoLogging() {
        ((LoggerContext) LoggerFactory.getILoggerFactory())
                .getLogger("org.mongodb.driver").setLevel(Level.ERROR);
    }
}

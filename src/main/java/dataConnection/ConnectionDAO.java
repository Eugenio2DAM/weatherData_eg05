
package dataConnection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import connection.ConnectionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Classe que gestiona la creació de la taula i la col·lecció per a emmagatzemar 
 * les dades meteorològiques en les bases de dades MySQL i MongoDB.
 *
 * @author Eugenio Gimeno Dolz
 * @version 1.0
 */
public class ConnectionDAO {

    private static final String DB_NAME = "WeatherData";
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "MENXureta82";

    private static final String COLLECTION_NAME = "WeatherData_eg05";

    // Sentència SQL per a la creació de la base 'WeatherData' i les taules en MySQL
    private static final String SQL_CREATE_DATEBASE = "CREATE DATABASE IF NOT EXISTS ";
    private static final String SQL_CREATE_WEATHERDATE = "CREATE TABLE IF NOT EXISTS WeatherData_eg05 ("
            + "record_id INT PRIMARY KEY,"
            + "city VARCHAR(100),"
            + "country VARCHAR(100),"
            + "latitude DECIMAL(10, 8),"
            + "longitude DECIMAL(11, 8),"
            + "date DATE,"
            + "temperature_celsius DECIMAL(5, 2),"
            + "humidity_percent INT,"
            + "precipitation_mm DECIMAL(5, 2),"
            + "wind_speed_kmh DECIMAL(5, 2),"
            + "weather_condition VARCHAR(100),"
            + "forecast TEXT,"
            + "updated DATE"
            + ");";
    private static final String SQL_CREATE_USERS = "CREATE TABLE IF NOT EXISTS users ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "dni VARCHAR(10) NOT NULL UNIQUE,"
            + "first_name VARCHAR(50),"
            + "last_name VARCHAR(50),"
            + "city VARCHAR(100)"
            + ");";
    private static final String SQL_CREATE_CREDENTIALS = "CREATE TABLE IF NOT EXISTS credentials ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "user_id INT NOT NULL,"
            + "password VARCHAR(255) NOT NULL,"
            + "FOREIGN KEY (user_id) REFERENCES users(id)"
            + ");";
    private static final String SQL_INSERT_USERS = "INSERT IGNORE INTO users (dni, first_name, last_name, city) VALUES "
            + "('12345678Z', 'JUAN', 'PÉREZ SANCHO', 'MADRID'),"
            + "('44517410J', 'EUGENIO', 'GIMENO DOLZ', 'VALENCIA'),"
            + "('29202969F', 'CARMEN', 'ALANDES NAVARRO', 'BARCELONA'),"
            + "('27371331C', 'CAVID', 'GIMENO ALANDES', 'CASTELLON DE LA PLANA'),"
            + "('27371333E', 'EMMA', 'GIMENO ALANDES', 'SEVILLA');";
    private static final String SQL_INSERT_CREDENTIALS = "INSERT IGNORE INTO credentials (user_id, password) VALUES "
            + "(1, 'password123'),"
            + "(2, 'password456'),"
            + "(3, 'password789'),"
            + "(4, 'password012'),"
            + "(5, 'password345');";

    protected static boolean checkDatabase() throws SQLException {
        Connection initialConnection = null;
        Statement aStatement = null;
        try {
            initialConnection = DriverManager.getConnection(URL_MYSQL, JDBC_USER, JDBC_PASSWORD);
            aStatement = initialConnection.createStatement();
            int records = aStatement.executeUpdate(SQL_CREATE_DATEBASE + DB_NAME);
            if (records < 0) {
                return false;
            }
            // Assegurar-se que la connexió apunta a la base de dades correcta
            DriverManager.getConnection(URL_MYSQL + DB_NAME, JDBC_USER, JDBC_PASSWORD);
            return true;
        } finally {
            // Tanquem les connexions en el bloc finally
            if (aStatement != null) {
                try {
                    aStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Statement: " + e.getMessage());
                }
            }
            if (initialConnection != null) {
                try {
                    initialConnection.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Connection: " + e.getMessage());
                }
            }
        }
    }

    protected static void createDatabaseIfNotExists() {
        Connection connection = null;
        Statement aStatement = null;
        try {
            connection = DriverManager.getConnection(URL_MYSQL, JDBC_USER, JDBC_PASSWORD);
            aStatement = connection.createStatement();
            String createDatabaseSQL = SQL_CREATE_DATEBASE + DB_NAME;
            aStatement.executeUpdate(createDatabaseSQL);
            System.out.println("\nBase de dades MySQL '" + DB_NAME + "' creada correctament.\n");
        } catch (SQLException e) {
            System.err.println("\nError en crear/verificar la base de dades MySQL: " + e.getMessage());
        } finally {
            if (aStatement != null) {
                try {
                    aStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Crea la taula 'WeatherData_eg05' en la base de dades MySQL si no existeix.
     *
     * @throw SQLException Si ocorre un error en executar la sentència SQL.
     */
    protected static void createTableWeatherDateIfNotExists() {
        Connection connection = null;
        PreparedStatement situationStateWeatherData = null;

        try {
            connection = ConnectionBD.getConnectionMySQL();
            situationStateWeatherData = connection.prepareStatement(SQL_CREATE_WEATHERDATE);

            // Crear taula WeatherData
            situationStateWeatherData.execute();
            System.out.println("\nLa taula 'WeatherData_eg05' ha sigut verificada o creada.");

        } catch (SQLException e) {
            System.err.println("\nError en crear/verificar la taula: " + e.getMessage());
        }
    }

    protected static void createTableUsersIfNotExists() {
        Connection connection = null;
        PreparedStatement situationUsers = null;

        try {
            connection = ConnectionBD.getConnectionMySQL();
            situationUsers = connection.prepareStatement(SQL_CREATE_USERS);

            // Crear taula Users i les seues dades
            situationUsers.execute();
            situationUsers.executeUpdate(SQL_INSERT_USERS);
            System.out.println("\nLa taula 'users' ha sigut verificada o creada amb usuaris.");

        } catch (SQLException e) {
            System.err.println("\nError en crear/verificar la taula: " + e.getMessage());
        } finally {
            if (situationUsers != null) {
                try {
                    situationUsers.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Connection: " + e.getMessage());
                }
            }
        }

    }

    protected static void createTableCredentialsIfNotExists() {
        Connection connection = null;
        PreparedStatement situationCredentials = null;

        try {
            connection = ConnectionBD.getConnectionMySQL();
            situationCredentials = connection.prepareStatement(SQL_CREATE_CREDENTIALS);

            // Crear taula Credentials i les seues dades
            situationCredentials.execute();
            situationCredentials.executeUpdate(SQL_INSERT_CREDENTIALS);
            System.out.println("\nLa taula 'credentials' ha sigut verificada o creada amb credencials.");

        } catch (SQLException e) {
            System.err.println("\nError en crear/verificar la taula: " + e.getMessage());
        } finally {
            if (situationCredentials != null) {
                try {
                    situationCredentials.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error en tancar Connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Crea la col·lecció 'WeatherData_eg05' en MongoDB si no existeix.
     */
    protected static void createCollectionIfNotExists() {
        MongoDatabase mongoDB = ConnectionBD.getConnectionMongoBD();
        MongoCollection<Document> collection = mongoDB.getCollection("WeatherData_eg05");

        if (collection == null) {
            // La col·lecció no existeix, pots crear-la si és necessari
            mongoDB.createCollection("WeatherData_eg05");
            System.out.println("\nLa col·lecció " + COLLECTION_NAME + " ha sigut creada.");
        } else {
            System.out.println("\nLa col·lecció " + COLLECTION_NAME + " ja existia.");
        }
    }
}

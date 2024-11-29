
package dataMySQL;

import domain.Credential;
import domain.User;
import domain.WeatherData_eg05;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Funcions d'accés a dades per a gestionar les operacions amb la base de dades MySQL.
 * Aquesta classe conté mètodes per a gestionar dades meteorològiques, credencials i usuaris.
 */
public class DataAccessFunctionsMySQL {

    /**
     * Recupera les dades meteorològiques des de la base de dades.
     * 
     * @return Llista d'objectes WeatherData_eg05.
     */
    public static List<WeatherData_eg05> listData() {
        List<WeatherData_eg05> weatherData_eg05s = new ArrayList<>();
        WeatherData_eg05MysqlDAO aWeatherData_eg05DAO = new WeatherData_eg05MysqlDAO();
        try {
            weatherData_eg05s = aWeatherData_eg05DAO.select();
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut generar les dades meteorològiques de MySQL. " + ex.getMessage());
        }

        return weatherData_eg05s;
    }

    /**
     * Insereix dades meteorològiques a la base de dades.
     * 
     * @param aWeartherData_eg05 Dades meteorològiques a insertar.
     */
    public static void insertData(WeatherData_eg05 aWeartherData_eg05) {
        if (aWeartherData_eg05 == null) {
            throw new IllegalArgumentException("\nLes dades meteorològiques estan buides.");
        }
        WeatherData_eg05MysqlDAO aWeatherData_eg05DAO = new WeatherData_eg05MysqlDAO();
        try {
            aWeatherData_eg05DAO.insert(aWeartherData_eg05);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut inserir les dades meteorològiques. " + ex.getMessage());
        }
    }

    /**
     * Elimina dades meteorològiques de la base de dades per ID.
     * 
     * @param recordId ID del registre a eliminar.
     */
    public static void deleteData(int recordId) {
        if (recordId < 0) {
            throw new IllegalArgumentException("\nL'ID dels registres meteorològics no pot ser inferior a zero.");
        }
        WeatherData_eg05MysqlDAO aWeatherData_eg05DAO = new WeatherData_eg05MysqlDAO();
        try {
            aWeatherData_eg05DAO.delete(recordId);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut eliminar les dades meteorològiques. " + ex.getMessage());
        }
    }
    
    /**
     * Actual.litza dades meteorològiques de la base de dades per ID.
     * 
     * @param recordId ID del registre a eliminar.
     * @param city
     * @param country
     * @param latitude
     * @param longitude
     * @param date
     * @param temperatureCelsius
     * @param humidityPercent
     * @param precipitationMm
     * @param windSpeedKmh
     * @param weatherCondition
     * @param forecast
     * @param updated
     */
    public static void updateData(Integer recordId, String city, String country, Double latitude, Double longitude,
            Date date, Double temperatureCelsius, Integer humidityPercent, Double precipitationMm, Double windSpeedKmh, 
            String weatherCondition, String forecast, Date updated) {
        if (recordId < 0) {
            throw new IllegalArgumentException("\nL'ID dels registres meteorològics no pot ser inferior a zero.");
        }
        WeatherData_eg05MysqlDAO aWeatherData_eg05DAO = new WeatherData_eg05MysqlDAO();
        try {
            aWeatherData_eg05DAO.update(recordId, city, country, latitude, longitude, date, temperatureCelsius, humidityPercent,
                    precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut eliminar les dades meteorològiques. " + ex.getMessage());
        }
    }

    /**
     * Recupera totes les credencials de la base de dades.
     * 
     * @return Llista d'objectes Credential.
     */
    public static List<Credential> listCredentials() {

        List<Credential> credentials = new ArrayList<>();
        CredentialDAO aCredentialDAO = new CredentialDAO();

        try {
            credentials = aCredentialDAO.select();
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut generar les credencials. " + ex.getMessage());
        }

        return credentials;
    }

    /**
     * Insereix una nova credencial a la base de dades.
     * 
     * @param aCredential Objecte Credential a inserir.
     */
    public static void insertCredential(Credential aCredential) {
        if (aCredential == null) {
            throw new IllegalArgumentException("\nLes dades de les credencials estan buides.");
        }
        CredentialDAO aCredentialDAO = new CredentialDAO();
        try {
            aCredentialDAO.insert(aCredential);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut inserir les dades de les credencials. " + ex.getMessage());
        }
    }

    /**
     * Elimina una credencial de la base de dades per ID.
     * 
     * @param credentialId ID de la credencial a eliminar.
     */
    public static void deleteCredential(int credentialId) {
        if (credentialId < 0) {
            throw new IllegalArgumentException("\nL'ID de les credencials no pot ser inferior a zero.");
        }
        CredentialDAO aCredentialDAO = new CredentialDAO();
        try {
            aCredentialDAO.delete(credentialId);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut eliminar la credencial. " + ex.getMessage());
        }
    }

    /**
     * Recupera tots els usuaris de la base de dades.
     * 
     * @return Llista d'objectes User.
     */
    public static List<User> listUsers() {

        List<User> users = new ArrayList<>();
        UserDAO aUserDAO = new UserDAO();

        try {
            users = aUserDAO.select();
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut generar els usuaris. " + ex.getMessage());
        }

        return users;
    }

    /**
     * Insereix un nou usuari a la base de dades.
     * 
     * @param aUser Objecte User a inserir.
     */
    public static void insertUser(User aUser) {
        if (aUser == null) {
            throw new IllegalArgumentException("\nLes dades dels usuaris estan buides.");
        }
        UserDAO aUserDAO = new UserDAO();
        try {
            aUserDAO.insert(aUser);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut inserir les dades de l'usuari. " + ex.getMessage());
        }
    }

    /**
     * Elimina un usuari de la base de dades per ID.
     * 
     * @param userId ID de l'usuari a eliminar.
     */
    public static void deleteUser(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("\nL'ID dels usuaris no pot ser inferior a zero.");
        }
        UserDAO aUserDAO = new UserDAO();
        try {
            aUserDAO.delete(userId);
        } catch (SQLException ex) {
            System.out.println("\nNo s'han pogut eliminar l'usuari. " + ex.getMessage());
        }
    }
}


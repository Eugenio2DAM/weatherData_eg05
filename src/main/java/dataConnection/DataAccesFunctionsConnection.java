
package dataConnection;

import java.sql.SQLException;


/**
 * Funcions per gestionar la creació de la taula a MySQL i la col·lecció a MongoDB.
 * 
 * @author Eugenio Gimeno Dolz
 * @version 1.0
 */
public class DataAccesFunctionsConnection {

    /**
     * Verifica o crea la taula a MySQL mitjançant la funció de ConnectionDAO.
     */
    public static void createtableWeatherData(){
         try {
            System.out.println("\nCreant/verificant les taules a MySQL...");
            ConnectionDAO.createTableWeatherDateIfNotExists();
        } catch (Exception e) {
            System.err.println("\nError al verificar/crear la taula a MySQL. " + e.getMessage());
        }
    }
    
    /**
     * Verifica o crea la taula a MySQL mitjançant la funció de ConnectionDAO.
     */
    public static void createtableUser(){
         try {
            System.out.println("\nCreant/verificant les taules a MySQL...");
            ConnectionDAO.createTableUsersIfNotExists();
        } catch (Exception e) {
            System.err.println("\nError al verificar/crear la taula a MySQL. " + e.getMessage());
        }
    }
    
    /**
     * Verifica o crea la taula a MySQL mitjançant la funció de ConnectionDAO.
     */
    public static void createtableCredentials(){
         try {
            System.out.println("\nCreant/verificant les taules a MySQL...");
            ConnectionDAO.createTableCredentialsIfNotExists();
        } catch (Exception e) {
            System.err.println("\nError al verificar/crear la taula a MySQL. " + e.getMessage());
        }
    }
    
    /**
     * Crea la base de dades a MySQL mitjançant la funció de ConnectionDAO.
     */
    public static void createDataBase(){
        ConnectionDAO.createDatabaseIfNotExists();
    }
    
    /**
     * Verifica la base de dades a MySQL mitjançant la funció de ConnectionDAO.
     * @return true si la base de dades a MySQL está creada
     */
    public static boolean checkedDataBase(){
        try {
            if(ConnectionDAO.checkDatabase()){
                return true;
            }
        } catch (SQLException e) {
            System.err.println("\nError al verificar la base de dades a MySQL. " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Verifica o crea la col·lecció a MongoDB mitjançant la funció de ConnectionDAO.
     */
    public static void createCollection(){
        try {
            ConnectionDAO.createCollectionIfNotExists();
        } catch (Exception e) {
            System.err.println("\nError al verificar/crear la col·lecció a MongoDB. " + e.getMessage());
        }
    }
   
}


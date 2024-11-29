
package dataMySQL;

import connection.ConnectionBD;
import domain.Credential;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) per a gestionar les credencials en la base de dades MySQL.
 * Proposa mètodes per a insertar, actualitzar, seleccionar i eliminar credencials.
 */
public class CredentialDAO {

    // Consultes SQL per a les operacions CRUD
    private static final String SQL_INSERT = "INSERT INTO credentials (user_id, password) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE credentials SET password = ? WHERE id = ?";
    private static final String SQL_SELECT = "SELECT id, user_id, password FROM credentials";
    private static final String SQL_DELETE = "DELETE FROM credentials WHERE id = ?";

    /**
     * Recupera totes les credencials de la base de dades.
     * 
     * @return Llista d'objectes Credential.
     * @throws SQLException si ocorre un error SQL.
     */
    protected List<Credential> select() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Credential credential;
        List<Credential> credentials = new ArrayList<>();

        try {
            // Obtenir la connexió a la base de dades
            connection = ConnectionBD.getConnectionMySQL();
            // Preparar la consulta SQL
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            // Executar la consulta
            resultSet = preparedStatement.executeQuery();

            // Processar el conjunt de resultats
            while (resultSet.next()) {
                Integer credentialId = resultSet.getInt("id");
                Integer userId = resultSet.getInt("user_id");
                String password = resultSet.getString("password");

                // Crear un nou objecte Credential i afegir-lo a la llista
                credential = new Credential(credentialId, userId, password);
                credentials.add(credential);
            }
        } catch (SQLException ex) {
            System.err.println("\nNo s'ha pogut obtenir els registres de les credencials: " + ex.getMessage());
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(resultSet);
                ConnectionBD.closeResource(preparedStatement);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError en tancar els recursos: " + ex.getMessage());
            }
        }

        return credentials;
    }

    /**
     * Insereix una nova credencial a la base de dades.
     * 
     * @param aCredential L'objecte Credential a inserir.
     * @return Nombre de registres inserits.
     * @throws SQLException si ocorre un error SQL.
     */
    protected int insert(Credential aCredential) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int records = 0;

        try {
            // Obtenir la connexió a la base de dades
            connection = ConnectionBD.getConnectionMySQL();
            // Preparar la consulta SQL
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            // Establir els valors per a la consulta
            preparedStatement.setInt(1, aCredential.getUserId());
            preparedStatement.setString(2, aCredential.getPassword());
            // Executar la consulta i obtenir el nombre de registres inserits
            records = preparedStatement.executeUpdate();

            if (records > 0) {
                System.out.println("\nLa credencial s'ha inserit correctament. ");
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("\nError en inserir la credencial: " + e.getMessage());
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(preparedStatement);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError en tancar els recursos: " + ex.getMessage());
            }
        }

        return records;
    }

    /**
     * Actualitza una credencial existent a la base de dades.
     * 
     * @param credentialId L'ID de la credencial a actualitzar.
     * @param userId L'ID de l'usuari associat amb la credencial.
     * @param password La nova contrasenya a establir.
     * @throws SQLException si ocorre un error SQL.
     */
    protected void update(int credentialId, int userId, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Obtenir la connexió a la base de dades
            connection = ConnectionBD.getConnectionMySQL();
            // Preparar la consulta SQL
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            // Establir els valors per a la consulta
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, credentialId);
            // Executar la consulta
            int updatedRecord = preparedStatement.executeUpdate();

            if (updatedRecord > 0) {
                System.out.println("\nL'usuari ha estat actualitzat correctament. ");
                System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println("\nActualització de l'usuari no realitzada. " + ex.getMessage());
            System.out.println();
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(preparedStatement);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError en tancar els recursos: " + ex.getMessage());
            }
        }
    }

    /**
     * Elimina una credencial de la base de dades per la seva ID.
     * 
     * @param credentialId L'ID de la credencial a eliminar.
     * @throws SQLException si ocorre un error SQL.
     */
    protected void delete(int credentialId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Obtenir la connexió a la base de dades
            connection = ConnectionBD.getConnectionMySQL();
            // Preparar la consulta SQL
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            // Establir el valor per a la consulta
            preparedStatement.setInt(1, credentialId);
            // Executar la consulta i obtenir el nombre de registres eliminats
            int deletedRecords = preparedStatement.executeUpdate();

            if (deletedRecords > 0) {
                System.out.println("\nLa credencial ha estat eliminada amb èxit. ");
                System.out.println();
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Cannot delete or update a parent row")) {
                throw new SQLException("\nLa credencial no es pot eliminar. " + ex.getMessage());
            } else {
                throw new IllegalStateException("\nNo s'ha pogut eliminar la credencial. " + ex.getMessage());
            }
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(preparedStatement);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError en tancar els recursos: " + ex.getMessage());
            }
        }
    }
}

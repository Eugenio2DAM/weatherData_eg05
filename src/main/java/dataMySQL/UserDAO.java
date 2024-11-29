
package dataMySQL;

import connection.ConnectionBD;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquesta classe gestiona les operacions CRUD (crear, llegir, actualitzar i eliminar) sobre la taula d'usuaris a la base de dades MySQL.
 * Conté mètodes per obtenir, afegir, actualitzar i eliminar usuaris.
 * 
 * @author eugenio
 */
public class UserDAO {

    // Consultes SQL per a cada operació
    private static final String SQL_SELECT = "SELECT id, dni, first_name, last_name, city FROM users";
    private static final String SQL_INSERT = "INSERT INTO users (dni, first_name, last_name, city) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE users SET first_name = ?, last_name = ?, city = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";

    /**
     * Recupera la llista d'usuaris de la base de dades.
     * 
     * @return una llista amb els usuaris obtinguts de la base de dades.
     * @throws SQLException si hi ha un error en la consulta SQL.
     */
    protected List<User> select() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionBD.getConnectionMySQL();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
             ResultSet result = statement.executeQuery()) {

            // Processar els resultats de la consulta
            while (result.next()) {
                Integer id = result.getInt("id");
                String dni = result.getString("dni");
                String name = result.getString("first_name");
                String surname = result.getString("last_name");
                String city = result.getString("city");

                // Crear un objecte User i afegir-lo a la llista
                User user = new User(id, dni, name, surname, city);
                users.add(user);
            }

        } catch (SQLException ex) {
            System.err.println("\nNo s'ha pogut obtenir els registres dels usuaris: " + ex.getMessage());
        }
        return users;
    }

    /**
     * Insereix un nou usuari a la base de dades.
     * 
     * @param aUser l'usuari que es vol inserir.
     * @return el nombre de registres afectats per l'operació d'inserció.
     * @throws SQLException si hi ha un error en la consulta SQL.
     */
    protected int insert(User aUser) throws SQLException {
        int records = 0;
        try (Connection connection = ConnectionBD.getConnectionMySQL();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, aUser.getDni());
            statement.setString(2, aUser.getName());
            statement.setString(3, aUser.getSurname());
            statement.setString(4, aUser.getCity());
            records = statement.executeUpdate();

            if (records > 0) {
                System.out.println("\nL'usuari s'ha inserit correctament.");
            }
        } catch (SQLException e) {
            System.err.println("\nError en inserir l'usuari: " + e.getMessage());
        }
        return records;
    }

    /**
     * Actualitza un usuari existent a la base de dades.
     * 
     * @param userId l'ID de l'usuari a actualitzar.
     * @param dni el DNI de l'usuari.
     * @param name el nom de l'usuari.
     * @param surname el cognom de l'usuari.
     * @param cityId l'ID de la ciutat associada a l'usuari.
     * @throws SQLException si hi ha un error en la consulta SQL.
     */
    protected void update(int userId, String dni, String name, String surname, int cityId) throws SQLException {
        try (Connection connection = ConnectionBD.getConnectionMySQL();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setInt(3, cityId);
            statement.setInt(4, userId);

            int updatedRecord = statement.executeUpdate();
            if (updatedRecord > 0) {
                System.out.println("\nL'usuari ha estat actualitzat correctament.");
            }
        } catch (SQLException ex) {
            System.out.println("\nActualització de l'usuari no realitzada: " + ex.getMessage());
        }
    }

    /**
     * Elimina un usuari de la base de dades.
     * 
     * @param userId l'ID de l'usuari a eliminar.
     * @throws SQLException si hi ha un error en la consulta SQL.
     */
    protected void delete(int userId) throws SQLException {
        try (Connection connection = ConnectionBD.getConnectionMySQL();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, userId);
            int deletedRecords = statement.executeUpdate();

            if (deletedRecords > 0) {
                System.out.println("\nL'usuari ha estat eliminat amb èxit.");
            }
        } catch (SQLException ex) {
            System.out.println("\nL'usuari no es pot eliminar: " + ex.getMessage());
        }
    }
}


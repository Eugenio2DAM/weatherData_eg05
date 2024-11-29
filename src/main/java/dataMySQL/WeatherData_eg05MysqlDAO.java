package dataMySQL;

import connection.ConnectionBD;
import domain.WeatherData_eg05;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquesta classe gestiona les operacions relacionades amb la taula
 * WeatherData_eg05 a la base de dades MySQL. Inclou operacions com ara la
 * selecció, inserció, actualització i eliminació de dades meteorològiques.
 *
 * @author eugenio
 */
public class WeatherData_eg05MysqlDAO {

    private static final String SQL_INSERT = "INSERT INTO WeatherData_eg05 (record_id, city, country, latitude, longitude, date, temperature_celsius, humidity_percent, precipitation_mm, wind_speed_kmh, weather_condition, forecast, updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE WeatherData_eg05 SET country = ?, latitude = ?, longitude = ?, date = ?, temperature_celsius = ?, humidity_percent = ?, precipitation_mm = ?, wind_speed_kmh = ?, weather_condition = ?, forecast = ?, updated = ? WHERE record_id = ?";
    private static final String SQL_SELECT = "SELECT record_id, city, country, latitude, longitude, date, temperature_celsius, humidity_percent, precipitation_mm, wind_speed_kmh, weather_condition, forecast, updated FROM WeatherData_eg05";
    private static final String SQL_DELETE = "DELETE FROM WeatherData_eg05 WHERE record_id = ?";

    /**
     * Selecciona totes les dades meteorològiques de la taula WeatherData_eg05.
     *
     * @return Una llista amb els objectes WeatherData_eg05 obtinguts de la base
     * de dades.
     * @throws SQLException Si ocorre un error en l'accés a la base de dades.
     */
    protected List<WeatherData_eg05> select() throws SQLException {
        List<WeatherData_eg05> weartherDataList = new ArrayList<>();

        try (Connection connection = ConnectionBD.getConnectionMySQL();
                PreparedStatement stmt = connection.prepareStatement(SQL_SELECT);
                ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Integer recordId = result.getInt("record_id");
                String city = result.getString("city");
                String country = result.getString("country");
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");
                Date date = result.getDate("date");
                Double temperatureCelsius = result.getDouble("temperature_celsius");
                Integer humidityPercent = result.getInt("humidity_percent");
                Double precipitationMm = result.getDouble("precipitation_mm");
                Double windSpeedKmh = result.getDouble("wind_speed_kmh");
                String weatherCondition = result.getString("weather_condition");
                String forecast = result.getString("forecast");
                Date updated = result.getDate("updated");

                // Crear l'objecte WeatherData_eg05
                WeatherData_eg05 weartherData = new WeatherData_eg05(
                        recordId, city, country, latitude, longitude, date, temperatureCelsius, humidityPercent,
                        precipitationMm, windSpeedKmh, weatherCondition, forecast, updated
                );

                weartherDataList.add(weartherData);
            }

        } catch (SQLException ex) {
            System.err.println("Error al obtenir les dades meteorològiques: " + ex.getMessage());
        }

        return weartherDataList;
    }

    /**
     * Insereix un registre de dades meteorològiques a la taula
     * WeatherData_eg05.
     *
     * @param aWeatherData_eg05 L'objecte WeatherData_eg05 amb les dades a
     * insertar.
     * @return El nombre de registres insertats.
     * @throws SQLException Si ocorre un error en l'inserció del registre.
     */
    protected int insert(WeatherData_eg05 aWeatherData_eg05) throws SQLException {
        Connection connection = null;
        PreparedStatement situationState = null;
        int records = 0;
        try {
            connection = ConnectionBD.getConnectionMySQL();
            situationState = connection.prepareStatement(SQL_INSERT);
            situationState.setInt(1, aWeatherData_eg05.getRecordId());

            // Inserir els altres camps que poden ser nuls
            if (aWeatherData_eg05.getCity() != null) {
                situationState.setString(2, aWeatherData_eg05.getCity());
            } else {
                situationState.setNull(2, java.sql.Types.VARCHAR);
            }

            if (aWeatherData_eg05.getCountry() != null) {
                situationState.setString(3, aWeatherData_eg05.getCountry());
            } else {
                situationState.setNull(3, java.sql.Types.VARCHAR);
            }

            if (aWeatherData_eg05.getLatitude() != null) {
                situationState.setDouble(4, aWeatherData_eg05.getLatitude());
            } else {
                situationState.setNull(4, java.sql.Types.DOUBLE);
            }

            if (aWeatherData_eg05.getLongitude() != null) {
                situationState.setDouble(5, aWeatherData_eg05.getLongitude());
            } else {
                situationState.setNull(5, java.sql.Types.DOUBLE);
            }

            if (aWeatherData_eg05.getDate() != null) {
                situationState.setDate(6, aWeatherData_eg05.getDate());
            } else {
                situationState.setNull(6, java.sql.Types.DATE);
            }

            if (aWeatherData_eg05.getTemperatureCelsius() != null) {
                situationState.setDouble(7, aWeatherData_eg05.getTemperatureCelsius());
            } else {
                situationState.setNull(7, java.sql.Types.DOUBLE);
            }

            if (aWeatherData_eg05.getHumidityPercent() != null) {
                situationState.setInt(8, aWeatherData_eg05.getHumidityPercent());
            } else {
                situationState.setNull(8, java.sql.Types.INTEGER);
            }

            if (aWeatherData_eg05.getPrecipitationMm() != null) {
                situationState.setDouble(9, aWeatherData_eg05.getPrecipitationMm());
            } else {
                situationState.setNull(9, java.sql.Types.DOUBLE);
            }

            if (aWeatherData_eg05.getWindSpeedKmh() != null) {
                situationState.setDouble(10, aWeatherData_eg05.getWindSpeedKmh());
            } else {
                situationState.setNull(10, java.sql.Types.DOUBLE);
            }

            if (aWeatherData_eg05.getWeatherCondition() != null) {
                situationState.setString(11, aWeatherData_eg05.getWeatherCondition());
            } else {
                situationState.setNull(11, java.sql.Types.VARCHAR);
            }

            if (aWeatherData_eg05.getForecast() != null) {
                situationState.setString(12, aWeatherData_eg05.getForecast());
            } else {
                situationState.setNull(12, java.sql.Types.VARCHAR);
            }

            if (aWeatherData_eg05.getUpdated() != null) {
                situationState.setDate(13, aWeatherData_eg05.getUpdated());
            } else {
                situationState.setNull(13, java.sql.Types.DATE);
            }

            // Executar la inserció
            records = situationState.executeUpdate();

            if (records > 0) {
                System.out.println("\nLes dades meteorològiques de " + aWeatherData_eg05.getCity()
                        + " ID: " + aWeatherData_eg05.getRecordId() + " s'han insertat correctament.");
            }
        } catch (SQLException e) {
            System.err.println("\nError en insertar el registre: " + e.getMessage());
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(situationState);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError al tancar els recursos: " + ex.getMessage());
            }
        }
        return records;
    }

    /**
     * Actualitza un registre de dades meteorològiques a la taula
     * WeatherData_eg05.
     *
     * @param recordId L'identificador del registre a actualitzar.
     * @param city La ciutat.
     * @param country El país.
     * @param latitude La latitud.
     * @param longitude La longitud.
     * @param date La data.
     * @param temperatureCelsius La temperatura en graus Celsius.
     * @param humidityPercent El percentatge d'humitat.
     * @param precipitationMm La precipitació en mil·límetres.
     * @param windSpeedKmh La velocitat del vent en km/h.
     * @param weatherCondition La condició meteorològica.
     * @param forecast La previsió meteorològica.
     * @param update La data d'actualització.
     * @throws SQLException Si ocorre un error en l'actualització del registre.
     */
    protected void update(Integer recordId, String city, String country, Double latitude, Double longitude,
            Date date, Double temperatureCelsius, Integer humidityPercent, Double precipitationMm, Double windSpeedKmh,
            String weatherCondition, String forecast, Date update) throws SQLException {

        Connection connection = null;
        PreparedStatement situationState = null;

        try {

            connection = ConnectionBD.getConnectionMySQL();
            situationState = connection.prepareStatement(SQL_UPDATE);

            situationState.setString(1, country != null ? country : null);
            situationState.setObject(2, latitude, java.sql.Types.DOUBLE);
            situationState.setObject(3, longitude, java.sql.Types.DOUBLE);
            situationState.setObject(4, date != null ? new java.sql.Date(date.getTime()) : null, java.sql.Types.DATE);
            situationState.setObject(5, temperatureCelsius, java.sql.Types.DOUBLE);
            situationState.setObject(6, humidityPercent, java.sql.Types.INTEGER);
            situationState.setObject(7, precipitationMm, java.sql.Types.DOUBLE);
            situationState.setObject(8, windSpeedKmh, java.sql.Types.DOUBLE);
            situationState.setString(9, weatherCondition != null ? weatherCondition : null);
            situationState.setString(10, forecast != null ? forecast : null);
            situationState.setObject(11, update != null ? new java.sql.Date(update.getTime()) : null, java.sql.Types.DATE);
            situationState.setInt(12, recordId);

            int rowsUpdated = situationState.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Dades meteorològiques actualitzades correctament.");
            }
        } catch (SQLException e) {
            System.err.println("Error en actualitzar el registre: " + e.getMessage());
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(situationState);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError al tancar els recursos: " + ex.getMessage());
            }
        }
    }

    /**
     * Elimina un registre de dades meteorològiques de la taula
     * WeatherData_eg05.
     *
     * @param recordId L'identificador del registre a eliminar.
     * @return El nombre de registres eliminats.
     * @throws SQLException Si ocorre un error en l'eliminació del registre.
     */
    protected int delete(Integer recordId) throws SQLException {
        int rowsDeleted = 0;
        Connection connection = null;
        PreparedStatement situationState = null;
        try {
            connection = ConnectionBD.getConnectionMySQL();
            situationState = connection.prepareStatement(SQL_DELETE);

            situationState.setInt(1, recordId);
            rowsDeleted = situationState.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Les dades meteorològiques amb ID: " + recordId + " s'han eliminat correctament.");
            }
        } catch (SQLException e) {
            System.err.println("Error en eliminar el registre: " + e.getMessage());
        } finally {
            // Tancar els recursos
            try {
                ConnectionBD.closeResource(situationState);
                ConnectionBD.closeResource(connection);
            } catch (Exception ex) {
                System.err.println("\nError al tancar els recursos: " + ex.getMessage());
            }
        }
        return rowsDeleted;
    }
}

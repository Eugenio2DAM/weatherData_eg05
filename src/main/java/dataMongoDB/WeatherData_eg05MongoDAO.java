package dataMongoDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import connection.ConnectionBD;
import domain.WeatherData_eg05;
import org.bson.Document;
import com.mongodb.client.model.UpdateOptions;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Funcions per gestionar les dades meteorològiques a la col·lecció
 * WeatherData_eg05 de MongoDB.
 *
 * @author Eugenio Gimeno Dolz
 * @version 1.0
 */
public class WeatherData_eg05MongoDAO {

    private static final MongoCollection<Document> collection = ConnectionBD.getConnectionMongoBD().getCollection("WeatherData_eg05");

    /**
     * Obté tots els registres de dades meteorològiques.
     *
     * @return Llista de dades meteorològiques.
     */
    protected List<WeatherData_eg05> select() {
        List<WeatherData_eg05> weatherDataList = new ArrayList<>();
        MongoCursor<Document> cursor = null;
        try {
            cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                WeatherData_eg05 data = new WeatherData_eg05(
                        doc.getInteger("record_id"),
                        doc.getString("city"),
                        doc.getString("country"),
                        doc.getDouble("latitude"),
                        doc.getDouble("longitude"),
                        doc.getDate("date") != null ? new Date(doc.getDate("date").getTime()) : null,
                        doc.getDouble("temperature_celsius"),
                        doc.getInteger("humidity_percent"),
                        doc.getDouble("precipitation_mm"),
                        doc.getDouble("wind_speed_kmh"),
                        doc.getString("weather_condition"),
                        doc.getString("forecast"),
                        doc.getDate("updated") != null ? new Date(doc.getDate("updated").getTime()) : null
                );
                weatherDataList.add(data);
            }
        } catch (Exception e) {
            System.err.println("Ha ocorregut un error inesperat. Reviseu els detalls per a més informació: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return weatherDataList;
    }

    /**
     * Insereix un registre de dades meteorològiques a MongoDB.
     *
     * @param aWeatherData_eg05 Les dades meteorològiques a inserir.
     */
    protected void insert(WeatherData_eg05 aWeatherData_eg05) {
        Document aRecord = new Document("record_id", aWeatherData_eg05.getRecordId())
                .append("city", aWeatherData_eg05.getCity())
                .append("country", aWeatherData_eg05.getCountry())
                .append("latitude", aWeatherData_eg05.getLatitude())
                .append("longitude", aWeatherData_eg05.getLongitude())
                .append("date", aWeatherData_eg05.getDate())
                .append("temperature_celsius", aWeatherData_eg05.getTemperatureCelsius())
                .append("humidity_percent", aWeatherData_eg05.getHumidityPercent())
                .append("precipitation_mm", aWeatherData_eg05.getPrecipitationMm())
                .append("wind_speed_kmh", aWeatherData_eg05.getWindSpeedKmh())
                .append("weather_condition", aWeatherData_eg05.getWeatherCondition())
                .append("forecast", aWeatherData_eg05.getForecast())
                .append("updated", aWeatherData_eg05.getUpdated());

        collection.insertOne(aRecord);
        System.out.println("\nLes dades meteorològiques de " + aWeatherData_eg05.getCity()
                + " ID: " + aWeatherData_eg05.getRecordId() + " s'han insertat correctament.");
        System.out.println();
    }

    /**
     * Actualitza un registre de dades meteorològiques a MongoDB.
     *
     * @param recordId ID del registre a actualitzar.
     * @param city Ciutat associada al registre.
     * @param country País associat al registre.
     * @param latitude Latitud associada al registre.
     * @param longitude Longitud associada al registre.
     * @param date Data associada al registre.
     * @param temperatureCelsius Temperatura en graus Celsius associada al
     * registre.
     * @param humidityPercent Humitat en percentatge associada al registre.
     * @param precipitationMm Precipitació en mil·límetres associada al
     * registre.
     * @param windSpeedKmh Velocitat del vent en km/h associada al registre.
     * @param weatherCondition Condicions meteorològiques associades al
     * registre.
     * @param forecast Previsió meteorològica associada al registre.
     * @param updated Data d'actualització associada al registre.
     */
    protected void update(Integer recordId, String city, String country, Double latitude, Double longitude,
            java.util.Date date, Double temperatureCelsius, Integer humidityPercent, Double precipitationMm,
            Double windSpeedKmh, String weatherCondition, String forecast, java.util.Date updated) {

        collection.updateOne(
                Filters.eq("record_id", recordId),
                Updates.combine(
                        Updates.set("city", city),
                        Updates.set("country", country),
                        Updates.set("latitude", latitude),
                        Updates.set("longitude", longitude),
                        Updates.set("date", date),
                        Updates.set("temperature_celsius", temperatureCelsius),
                        Updates.set("humidity_percent", humidityPercent),
                        Updates.set("precipitation_mm", precipitationMm),
                        Updates.set("wind_speed_kmh", windSpeedKmh),
                        Updates.set("weather_condition", weatherCondition),
                        Updates.set("forecast", forecast),
                        Updates.set("updated", updated)));
    }

    /**
     * Elimina un registre de dades meteorològiques a MongoDB.
     *
     * @param recordId ID del registre a eliminar.
     */
    protected void delete(int recordId) {
        collection.deleteOne(Filters.eq("record_id", recordId));
    }

    /**
     * Inserta o actualitza un registre de dades meteorològiques a MongoDB.
     *
     * @param aWeatherData_eg05 Les dades meteorològiques a inserir o
     * actualitzar.
     */
    protected void upsert(WeatherData_eg05 aWeatherData_eg05) {
        Document filter = new Document("record_id", aWeatherData_eg05.getRecordId());
        Document update = new Document("$set", new Document("city", aWeatherData_eg05.getCity())
                .append("country", aWeatherData_eg05.getCountry())
                .append("latitude", aWeatherData_eg05.getLatitude())
                .append("longitude", aWeatherData_eg05.getLongitude())
                .append("date", aWeatherData_eg05.getDate())
                .append("temperature_celsius", aWeatherData_eg05.getTemperatureCelsius())
                .append("humidity_percent", aWeatherData_eg05.getHumidityPercent())
                .append("precipitation_mm", aWeatherData_eg05.getPrecipitationMm())
                .append("wind_speed_kmh", aWeatherData_eg05.getWindSpeedKmh())
                .append("weather_condition", aWeatherData_eg05.getWeatherCondition())
                .append("forecast", aWeatherData_eg05.getForecast())
                .append("updated", aWeatherData_eg05.getUpdated()));

        collection.updateOne(filter, update, new UpdateOptions().upsert(true));
        System.out.println("\nDades insertades o actualitzades correctament a " + aWeatherData_eg05.getCity());
    }

}

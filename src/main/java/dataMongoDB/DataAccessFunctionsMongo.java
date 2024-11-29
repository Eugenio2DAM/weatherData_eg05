
package dataMongoDB;

import domain.WeatherData_eg05;
import java.util.List;
import java.util.Date;

/**
 * Funcions per gestionar les dades meteorològiques a MongoDB.
 * 
 * @author Eugenio Gimeno Dolz
 * @version 1.0
 */
public class DataAccessFunctionsMongo {

    /**
     * Obté una llista de dades meteorològiques des de MongoDB.
     * 
     * @return Llista d'objectes WeatherData_eg05.
     */
    public static List<WeatherData_eg05> listData() {
  
        WeatherData_eg05MongoDAO aWeatherData_eg05DAO = new WeatherData_eg05MongoDAO();
        return aWeatherData_eg05DAO.select();
    }

    /**
     * Insereix dades meteorològiques a MongoDB.
     * 
     * @param aWeatherData_eg05 Dades meteorològiques a inserir.
     * @throw IllegalArgumentException Si les dades estan buides.
     */
    public static void insertData(WeatherData_eg05 aWeatherData_eg05) {
        if (aWeatherData_eg05 == null) {
            throw new IllegalArgumentException("\nLes dades atmosfèriques estan buides.");
        }
        
        WeatherData_eg05MongoDAO aWeatherData_eg05DAO = new WeatherData_eg05MongoDAO();
        aWeatherData_eg05DAO.insert(aWeatherData_eg05);
    }

    /**
     * Elimina dades meteorològiques de MongoDB per ID de registre.
     * 
     * @param recordId ID del registre a eliminar.
     * @throw IllegalArgumentException Si l'ID és inferior a zero.
     */
    public static void deleteData(int recordId) {
        if (recordId < 0) {
            throw new IllegalArgumentException("\nL'ID dels registres atmosfèrics no pot ser inferior a zero.");
        }
        
        WeatherData_eg05MongoDAO aWeatherData_eg05DAO = new WeatherData_eg05MongoDAO();
        aWeatherData_eg05DAO.delete(recordId);
    }
    
    /**
     * Actualitza o insereix dades meteorològiques a MongoDB.
     * 
     * @param aWeatherData_eg05 Dades meteorològiques a actualitzar o inserir.
     * @throw IllegalArgumentException Si les dades estan buides.
     */
    public static void upsertWeatherDataMongo(WeatherData_eg05 aWeatherData_eg05){
        if (aWeatherData_eg05 == null) {
            throw new IllegalArgumentException("\nLes dades atmosfèriques estan buides.");
        }
        
        WeatherData_eg05MongoDAO aWeatherData_eg05DAO = new WeatherData_eg05MongoDAO();
        aWeatherData_eg05DAO.upsert(aWeatherData_eg05);
    }
    
    public static void update(Integer recordId, String city, String country, Double latitude, Double longitude,
            Date date, Double temperatureCelsius, Integer humidityPercent, Double precipitationMm,
            Double windSpeedKmh, String weatherCondition, String forecast, Date updated){
        if(recordId == null || recordId < 0){
            System.out.println("El ID no puede ser nulo ni ser inferior a cero.");
        }
        
        WeatherData_eg05MongoDAO aWeatherData_eg05DAO = new WeatherData_eg05MongoDAO();
        aWeatherData_eg05DAO.update(recordId, city, country, latitude, longitude,
            date, temperatureCelsius, humidityPercent, precipitationMm,
            windSpeedKmh, weatherCondition, forecast, updated);
    }
}

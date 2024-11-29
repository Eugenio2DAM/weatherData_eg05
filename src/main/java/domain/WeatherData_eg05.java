package domain;

import java.sql.Date;

/**
 * Classe que representa dades meteorològiques amb informació com la ciutat,
 * el país, la latitud, longitud, temperatura, humitat, precipitacions, 
 * velocitat del vent, condicions meteorològiques i pronòstic.
 * També conté mètodes per obtenir i establir valors, així com per convertir
 * les dades a format JSON.
 * 
 * @author eugenio
 */
public class WeatherData_eg05 {

    private Integer recordId;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private Date date;
    private Double temperatureCelsius;
    private Integer humidityPercent;
    private Double precipitationMm;
    private Double windSpeedKmh;
    private String weatherCondition;
    private String forecast;
    private Date updated;

    // Constructor sense arguments necessari per al volcat de .json
    public WeatherData_eg05() {
    }

    /**
     * Constructor amb tots els paràmetres per inicialitzar una instància de 
     * WeatherData_eg05.
     * 
     * @param recordId ID del registre.
     * @param city Nom de la ciutat.
     * @param country Nom del país.
     * @param latitude Latitud de la ciutat.
     * @param longitude Longitud de la ciutat.
     * @param date Data de la mesura.
     * @param temperatureCelsius Temperatura en graus Celsius.
     * @param humidityPercent Percentatge d'humitat.
     * @param precipitationMm Precipitació en mil·límetres.
     * @param windSpeedKmh Velocitat del vent en km/h.
     * @param weatherCondition Condicions meteorològiques.
     * @param forecast Pronòstic meteorològic.
     * @param updated Data d'actualització.
     */
    public WeatherData_eg05(Integer recordId, String city, String country, Double latitude, Double longitude,
            Date date, Double temperatureCelsius, Integer humidityPercent, Double precipitationMm, Double windSpeedKmh, 
            String weatherCondition, String forecast, Date updated) {

        this.setRecordId(recordId);
        this.setCity(city);
        this.setCountry(country);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setDate(date);
        this.setTemperatureCelsius(temperatureCelsius);
        this.setHumidityPercent(humidityPercent);
        this.setPrecipitationMm(precipitationMm);
        this.setWindSpeedKmh(windSpeedKmh);
        this.setWeatherCondition(weatherCondition);
        this.setForecast(forecast);
        this.setUpdated(updated);
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Date getDate() {
        return date;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public Integer getHumidityPercent() {
        return humidityPercent;
    }

    public Double getPrecipitationMm() {
        return precipitationMm;
    }

    public Double getWindSpeedKmh() {
        return windSpeedKmh;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getForecast() {
        return forecast;
    }

    public Date getUpdated() {
        return updated;
    }

    /**
     * Estableix l'ID del registre.
     * Si l'ID és nul o negatiu, llença una excepció.
     * 
     * @param recordId ID del registre.
     * @throws IllegalArgumentException Si l'ID és nul o negatiu.
     */
    public void setRecordId(Integer recordId) {
        if (recordId == null || recordId < 0) {
            throw new IllegalArgumentException("L'ID dels registres no pot ser menor que zero ni ser nul. ");
        }
        this.recordId = recordId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTemperatureCelsius(Double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public void setHumidityPercent(Integer humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public void setPrecipitationMm(Double precipitationMm) {
        this.precipitationMm = precipitationMm;
    }

    public void setWindSpeedKmh(Double windSpeedKmh) {
        this.windSpeedKmh = windSpeedKmh;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "\n\t--- Dades atmosfèriques de " + this.getCity() + " ---\n"
                + "\n\tNom de la ciutat: " + this.getCity()
                + "\n\tID del registre: " + this.getRecordId()
                + "\n\tPaís: " + this.getCountry()
                + "\n\tLatitud: " + this.getLatitude()
                + "\n\tLongitud: " + this.getLongitude()
                + "\n\tData: " + this.getDate()
                + "\n\tTemperatura (°C): " + this.getTemperatureCelsius()
                + "\n\tHumedat (%): " + this.getHumidityPercent()
                + "\n\tPrecipitacions (mm): " + this.getPrecipitationMm()
                + "\n\tVelocitat del vent (Km/h): " + this.getWindSpeedKmh()
                + "\n\tCondicions meteorològiques: " + this.getWeatherCondition()
                + "\n\tPronòstic: " + this.getForecast()
                + "\n\tData d'actualització: " + this.getUpdated();
    }

    /**
     * Converteix les dades meteorològiques a format JSON.
     * 
     * @return Dades en format JSON.
     */
    public String toJson() {
        return "\t{"
                + "\n\t  \"recordId\": " + this.getRecordId() + ","
                + "\n\t  \"city\": " +"\""+ this.getCity() +"\","
                + "\n\t  \"country\": " +"\""+ this.getCountry() + "\","
                + "\n\t  \"latitude\": " + this.getLatitude() + ","
                + "\n\t  \"longitude\": " + this.getLongitude() + ","
                + "\n\t  \"date\": " +"\""+ this.getDate() +"\","
                + "\n\t  \"temperatureCelsius\": " + this.getTemperatureCelsius() + ","
                + "\n\t  \"humidityPercent\": " + this.getHumidityPercent() + ","
                + "\n\t  \"precipitationMm\": " + this.getPrecipitationMm() + ","
                + "\n\t  \"windSpeedKmh\": " + this.getWindSpeedKmh() + ","
                + "\n\t  \"weatherCondition\": " +"\""+ this.getWeatherCondition() + "\","
                + "\n\t  \"forecast\": " +"\""+ this.getForecast() + "\","
                + "\n\t  \"updated\": " +"\""+ this.getUpdated() + "\"\n";
    }
}


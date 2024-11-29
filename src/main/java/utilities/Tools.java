/**
 * En esta classe trobaràs tots els mètodes públics utilitzats per recollir
 * informació de l'usuari i validar-la.
 * */
package utilities;

import dataMySQL.DataAccessFunctionsMySQL;
import domain.User;
import domain.WeatherData_eg05;
import java.util.Arrays;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataMongoDB.DataAccessFunctionsMongo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class Tools {

    public static void main(String[] args) {

    }

    /**
     * Sol·licita el nom de l'usuari.
     *
     * @return El nom de l'usuari en majúscules.
     */
    public static String requestUserFirstName() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el nom de l'usuari.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita els cognoms de l'usuari.
     *
     * @return Els cognoms de l'usuari en majúscules.
     */
    public static String requestUserLastName() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca els cognoms de l'usuari.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita el nom de la ciutat.
     *
     * @return El nom de la ciutat en majúscules.
     */
    public static String requestCityName() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el nom de la ciutat.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita els noms de les ciutats separats per coma.
     *
     * @return Els noms de les ciutats en majúscules.
     */
    public static String requestCitiesName() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el nom de les ciutats separades per \", \".");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita el nom del país.
     *
     * @return El nom del país en majúscules.
     */
    public static String requestCountry() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el nom del país.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita una data.
     *
     * @return La data introduïda pel usuari.
     */
    public static String requestDate() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca la data.");

        return lector.nextLine();
    }

    /**
     * Sol·licita els elements que es volen visualitzar.
     *
     * @return Els elements que el usuari vol visualitzar.
     */
    public static String requestElements() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca els elements que desitja visualitzar.");

        return lector.nextLine();
    }

    /**
     * Sol·licita un ID de registre.
     *
     * @return Un ID de registre enter introduït pel usuari.
     */
    public static Integer requestRecordId() {
        Scanner lector = new Scanner(System.in);
        Integer recordId = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("\nIntroduïsca un ID.");
            try {
                recordId = Integer.parseInt(lector.nextLine());
                if (recordId >= 0) {
                    isValidInput = true;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("\nEspera un número enter positiu. " + nfe.getMessage());
            }
        }
        return recordId;
    }

    /**
     * Sol·licita la condició climàtica.
     *
     * @return La condició climàtica en majúscules.
     */
    public static String requestWeatherCondition() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca la condició climàtica.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita el pronòstic del temps.
     *
     * @return El pronòstic del temps en majúscules.
     */
    public static String requestForecast() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el pronòstic.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita la contrasenya.
     *
     * @return La contrasenya introduïda.
     */
    public static String requestPassword() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca la contrasenya.");

        return lector.nextLine();
    }

    /**
     * Sol·licita el DNI.
     *
     * @return El DNI introduït en majúscules.
     */
    public static String requestDni() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca el DNI.");

        return lector.nextLine().toUpperCase();
    }

    /**
     * Sol·licita la data d'actualització.
     *
     * @return La data d'actualització introduïda.
     */
    public static String requestDateUpdate() {
        Scanner lector = new Scanner(System.in);
        System.out.println("\nIntroduïsca la data d'actualització.");

        return lector.nextLine();
    }

    /**
     * Sol·licita el valor de la latitud.
     *
     * @return El valor de la latitud introduïda.
     */
    public static double requestLatitude() {

        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        double latitude = 0.0;

        System.out.println("\nIntroduïsca el valor de la latitud.");
        while (!isValidInput) {
            String input = lector.nextLine();

            if (input.isEmpty()) {
                latitude = 0.0;
                isValidInput = true;
            } else if (input.matches("^-?\\d+(\\.\\d+)?$")) {
                try {
                    latitude = Double.parseDouble(input);
                    isValidInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el valor a número.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegure's d'introduir un número decimal o enter.");
            }
        }

        return latitude;
    }

    /**
     * Sol·licita el valor de la longitud.
     *
     * @return El valor de la longitud introduïda.
     */
    public static double requestLongitude() {

        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        double longitude = 0.0;

        while (!isValidInput) {
            System.out.println("\nIntroduïsca el valor de la longitud.");
            String input = lector.nextLine();

            if (input.isEmpty()) {
                longitude = 0.0;
                isValidInput = true;
            } else if (input.matches("^-?\\d+(\\.\\d+)?$")) {
                try {
                    longitude = Double.parseDouble(input);
                    isValidInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el valor a número.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegure's d'introduir un número decimal o enter.");
            }
        }

        return longitude;
    }

    /**
     * Sol·licita la temperatura en graus Celsius.
     *
     * @return La temperatura introduïda.
     */
    public static double requestTemperatureCelsius() {

        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        double temperature = 0.0;

        while (!isValidInput) {
            System.out.println("\nIntroduïsca la temperatura.");
            String input = lector.nextLine();

            if (input.isEmpty()) {
                temperature = 0.0;
                isValidInput = true;
            } else if (input.matches("^-?\\d+(\\.\\d+)?$")) {
                try {
                    temperature = Double.parseDouble(input);
                    isValidInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el valor a número.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegure's d'introduir un número decimal o enter.");
            }
        }

        return temperature;
    }

    /**
     * Sol·licita el percentatge d'humitat.
     *
     * @return El percentatge d'humitat introduït.
     */
    public static Integer requestHumidityPercent() {

        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        Integer humidityPercent = null;

        while (!isValidInput) {
            System.out.println("\nIntroduïsca el percentatge d'humitat.");
            String input = lector.nextLine();

            if (input.isEmpty()) {
                humidityPercent = null;
                isValidInput = true;
            } else if (input.matches("^-?\\d+$")) {
                humidityPercent = Integer.parseInt(input);

                if (humidityPercent >= 0) {
                    isValidInput = true;
                } else {
                    System.out.println("S'espera que el valor siga un número positiu.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegure's d'introduir un número enter positiu.");
            }
        }

        return humidityPercent;
    }

    /**
     * Sol·licita la quantitat de precipitació en mil·límetres.
     *
     * @return La quantitat de precipitació introduïda.
     */
    public static Double requestPrecipitationMm() {
        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        Double precipitation = null;

        while (!isValidInput) {
            System.out.println("\nIntroduïsca les precipitacions.");
            String input = lector.nextLine();

            if (input.isEmpty()) {
                precipitation = null;
                isValidInput = true;
            } else if (input.matches("^[0-9]*\\.?[0-9]+$")) {
                precipitation = Double.parseDouble(input);

                if (precipitation >= 0) {
                    isValidInput = true;
                } else {
                    System.out.println("S'espera que el valor siga un número positiu.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegure's d'introduir un número positiu.");
            }
        }

        return precipitation;
    }

    /**
     * Sol·licita la velocitat del vent en km/h a l'usuari. La velocitat ha de
     * ser un número positiu. Si l'usuari introdueix un valor invàlid, se li
     * tornarà a sol·licitar fins que introduïsca un valor vàlid.
     *
     * @return La velocitat del vent introduïda en km/h.
     */
    public static Double requestWindSpeedKmh() {
        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        Double windSpeed = null;

        while (!isValidInput) {
            System.out.println("\nIntroduïu la velocitat del vent (en km/h).");
            String input = lector.nextLine();

            if (input.isEmpty()) {
                windSpeed = null;
                isValidInput = true;
            } else if (input.matches("^[0-9]*\\.?[0-9]+$")) {
                windSpeed = Double.parseDouble(input);

                if (windSpeed >= 0) {
                    isValidInput = true;
                } else {
                    System.out.println("S'espera que el valor siga un número positiu.");
                }
            } else {
                System.out.println("Valor no vàlid. Assegureu-vos d'introduir un número positiu.");
            }
        }

        return windSpeed;
    }

    /**
     * Sol·licita l'ID de l'usuari. L'ID ha de ser un número enter positiu. Si
     * l'usuari introdueix un valor invàlid, se li tornarà a sol·licitar fins
     * que introduïsca un valor vàlid.
     *
     * @return L'ID de l'usuari introduït.
     */
    public static Integer requestUserId() {
        Scanner lector = new Scanner(System.in);
        Integer userId = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("\nIntroduïu l'ID de l'usuari.");
            try {
                userId = Integer.parseInt(lector.nextLine());
                if (userId > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("L'ID ha de ser un número enter positiu.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introduïu un número enter positiu. " + nfe.getMessage());
            }
        }
        return userId;
    }

    /**
     * Sol·licita l'ID de la credencial. L'ID ha de ser un número enter positiu.
     * Si l'usuari introdueix un valor invàlid, se li tornarà a sol·licitar fins
     * que introduïsca un valor vàlid.
     *
     * @return L'ID de la credencial introduït.
     */
    public static Integer requestCredentialId() {
        Scanner lector = new Scanner(System.in);
        Integer credentialId = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("\nIntroduïu l'ID de la credencial.");
            try {
                credentialId = Integer.parseInt(lector.nextLine());
                if (credentialId > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("L'ID ha de ser un número enter positiu.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introduïu un número enter positiu. " + nfe.getMessage());
            }
        }
        return credentialId;
    }

    /**
     * Realitza una confirmació de 'sí' o 'no'.
     *
     * @param missatge El missatge que es vol validar.
     * @return 'true' si la resposta és afirmativa, 'false' si és negativa.
     */
    public static boolean confirmation(String missatge) {
        if (missatge == null || missatge.isEmpty()) {
            throw new IllegalArgumentException("El missatge no pot estar buit");
        }

        Scanner lector = new Scanner(System.in);
        boolean valido = false;
        boolean confirmacion = false;

        while (!valido) {
            if (missatge.matches("^\\s*(?i)s([ií]*)?\\s*$")) {
                confirmacion = true;
                valido = true;
            } else if (missatge.matches("^\\s*(?i)n[o]?\\s*$")) {
                confirmacion = false;
                valido = true;
            } else {
                System.out.println("\nEntrada no vàlida. Respon amb 'sí' o 'no'.");
                System.out.print("Torna a introduir el missatge: ");
                missatge = lector.nextLine();
            }
        }
        return confirmacion;
    }

    /**
     * Sol·licita al usuari si esta segur de esborrar les dades Si l'usuari
     * introdueix un valor invàlid, se li tornarà a sol·licitar fins que
     * introduïsca un valor vàlid.
     *
     * @return boolean delete true si el usuari escriu si
     */
    public static boolean requestAlertDelete() {
        Scanner lector = new Scanner(System.in);
        boolean delete = false;
        System.out.println("\nVols realment eliminal les dades? (si/no)");
        String resposta = lector.nextLine();

        if (confirmation(resposta)) {
            delete = true;
            System.out.println("Eliminant dades....");
        } else {
            System.out.println("Eliminació cancel·lada.");
        }
        return delete;
    }

    /**
     * Sol·licita al usuari si esta segur de sincronitzar les dades amb l'altra
     * base de dades Si l'usuari introdueix un valor invàlid, se li tornarà a
     * sol·licitar fins que introduïsca un valor vàlid.
     *
     * @return boolean delete true si el usuari escriu si
     */
    public static boolean requestAlertSynchronize() {
        Scanner lector = new Scanner(System.in);
        boolean synchronive = false;
        System.out.println("\nVols realment sincronitzar les bases de dades? (si/no)");
        String resposta = lector.nextLine();

        if (confirmation(resposta)) {
            synchronive = true;
            System.out.println("Sincronizant dades....");
        } else {
            System.out.println("Sincronització cancel·lada.");
        }
        return synchronive;
    }

    /**
     * Sol·licita al usuari si esta segur de importar l'arxiu .xml Si l'usuari
     * introdueix un valor invàlid, se li tornarà a sol·licitar fins que
     * introduïsca un valor vàlid.
     *
     * @return boolean delete true si el usuari escriu si
     */
    public static boolean requestAlertImportXml() {
        Scanner lector = new Scanner(System.in);
        boolean importing = false;
        System.out.println("\nVols realment importar el .xml? (si/no)");
        String resposta = lector.nextLine();

        if (confirmation(resposta)) {
            importing = true;
            System.out.println("Important dades.....");
        } else {
            System.out.println("Importació cancel·lada.");
        }
        return importing;
    }

    /**
     * Converteix una cadena de text amb noms de ciutats separades per coma en
     * una llista de ciutats.
     *
     * @param citiesString La cadena de ciutats separades per coma.
     * @return Una llista de noms de ciutats.
     */
    public static ArrayList<String> getCitiesFromString(String citiesString) {
        if (citiesString == null || citiesString.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> cities = new ArrayList<>(Arrays.asList(citiesString.split("\\s*,\\s*")));
        ArrayList<String> filteredCities = new ArrayList<>();

        for (String city : cities) {
            filteredCities.add(city.trim());
        }

        return filteredCities;
    }

    /**
     * Converteix una cadena de text amb números separades per coma en una
     * llista de elements WeatherData_eg05.
     *
     * @param elementsInteger La cadena de elements separades per coma.
     * @return Una llista de noms en la BD de els elements.
     */
    public static ArrayList<Integer> getElementsFromString(String elementsInteger) {
        if (elementsInteger == null || elementsInteger.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<Integer> elements = new ArrayList<>();
        String[] parts = elementsInteger.split("\\s*,\\s*");
        for (String part : parts) {
            elements.add(Integer.parseInt(part.trim()));
        }
        return elements;
    }

    /**
     * Filtra una llista de dades meteorològiques per ciutat.
     *
     * @param dataList La llista de dades meteorològiques.
     * @param city El nom de la ciutat per filtrar les dades.
     * @return Una llista d'IDs de registre que pertanyen a la ciutat indicada.
     */
    public static List<Integer> data(List<WeatherData_eg05> dataList, String city) {

        List<Integer> cityIdList = new ArrayList<>();
        boolean cityExist = false;
        for (WeatherData_eg05 weatherData : dataList) {
            if (weatherData.getCity().equals(city)) {
                cityIdList.add(weatherData.getRecordId());
                cityExist = true;
            }
        }
        if (!cityExist) {
            System.out.println("\nImposible mostrar dades, " + city + " no existex a la base de dades.");
        }
        return cityIdList;
    }

    /**
     * Filtra una llista de dades meteorològiques per ciutat.
     *
     * @param dataList La llista de dades meteorològiques.
     * @param cities Els noms de les ciutats per filtrar les dades.
     * @return Una llista d'IDs de registre que pertanyen a la ciutat indicada.
     */
    public static List<Integer> recordsIdByCities(List<WeatherData_eg05> dataList, List<String> cities) {

        List<Integer> cityIdList = new ArrayList<>();
        for (WeatherData_eg05 weatherData : dataList) {
            for (String city : cities) {
                if (weatherData.getCity().equals(city)) {
                    cityIdList.add(weatherData.getRecordId());
                }
            }

        }
        return cityIdList;
    }

    /**
     * Obté el nom de la ciutat associada a un ID de registre.
     *
     * @param recordId ID del registre per cercar.
     * @return El nom de la ciutat associada a l'ID de registre.
     * @throw IllegalArgumentException Si l'ID és nul o inferior a zero.
     */
    public static String data(Integer recordId) {
        if (recordId == null || recordId < 0) {
            throw new IllegalArgumentException("\nL'ID de la ciutat no pot estar buit ni ser menor que zero.");
        }
        String city = null;
        List<WeatherData_eg05> dataList = DataAccessFunctionsMongo.listData();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getRecordId().equals(recordId)) {
                city = dataList.get(i).getCity();
            }
        }
        return city;
    }

    /**
     * Obté la ciutat a partir de la llista de dades meteorològiques.
     *
     * @param dataList Llista de dades meteorològiques.
     * @param recordId ID del registre per a cercar la ciutat.
     * @return El nom de la ciutat corresponent al registre.
     * @throws IllegalArgumentException Si el ID del registre és invàlid.
     */
    public static String data(List<WeatherData_eg05> dataList, Integer recordId) {
        if (recordId == null || recordId < 0) {
            throw new IllegalArgumentException("L'ID de la ciutat no pot estar buit ni ser menor que zero.");
        }
        String city = null;

        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getRecordId().equals(recordId)) {
                city = dataList.get(i).getCity();
            }
        }
        return city;
    }

    /**
     * Obté el nom d'usuari a partir de l'ID d'usuari.
     *
     * @param userId ID de l'usuari per a cercar el nom.
     * @return El nom de l'usuari corresponent a l'ID.
     * @throws IllegalArgumentException Si l'ID de l'usuari és invàlid.
     */
    public static String user(Integer userId) {
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("L'ID de l'usuari no pot estar buit ni ser menor que zero.");
        }
        String userName = null;
        List<User> userList = DataAccessFunctionsMySQL.listUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(userId)) {
                userName = userList.get(i).getName();
            }
        }
        return userName;
    }

    /**
     * Llegeix les dades meteorològiques des d'un arxiu JSON i les retorna com a
     * llista d'objectes.
     *
     * @return Llista d'objectes de tipus WeatherData_eg05.
     */
    public static List<WeatherData_eg05> readWeatherData() {
        List<WeatherData_eg05> weatherDataList = new ArrayList<>();
        BufferedReader reader = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            reader = new BufferedReader(new FileReader("WeatherData_eg05.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JsonNode rootNode = mapper.readTree(jsonContent.toString());

            JsonNode weatherArray = rootNode.get("WeatherDate_eg05");

            if (weatherArray != null && weatherArray.isArray()) {
                for (JsonNode node : weatherArray) {
                    WeatherData_eg05 weatherData = mapper.treeToValue(node, WeatherData_eg05.class);
                    weatherDataList.add(weatherData);
                }
            }
        } catch (IOException e) {
            System.out.println("\nNo s'ha pogut llegir l'arxiu \".json\". " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("\nError al tancar l'arxiu de lectura: " + e.getMessage());
            }
        }
        return weatherDataList;
    }

    /**
     * Extreu les dades meteorològiques d'un arxiu XML i les retorna com a
     * llista d'objectes.
     *
     * @return Llista d'objectes de tipus WeatherData_eg05.
     */
    public static List<WeatherData_eg05> extractWeatherData() {

        List<WeatherData_eg05> weatherRecords = new ArrayList<>();
        StringBuilder xmlContent = new StringBuilder();
        String line;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("WeatherData_eg_05.xml"));
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line);
            }
        } catch (IOException e) {
            System.err.println("\nError en llegir l'arxiu: " + e.getMessage());
        }

        String content = xmlContent.toString();

        String recordStartTag = "<WeatherRecord_eg05>";
        String recordEndTag = "</WeatherRecord_eg05>";

        int start = content.indexOf(recordStartTag);
        while (start != -1) {
            int end = content.indexOf(recordEndTag, start);
            if (end == -1) {
                break;
            }

            String recordContent = content.substring(start + recordStartTag.length(), end);
            WeatherData_eg05 aWeatherData_eg05 = new WeatherData_eg05();

            try {
                aWeatherData_eg05.setRecordId(Integer.parseInt(getTagValue(recordContent, "RecordId")));
                aWeatherData_eg05.setCity(getTagValue(recordContent, "City"));
                aWeatherData_eg05.setCountry(getTagValue(recordContent, "Country"));
                aWeatherData_eg05.setLatitude(Double.parseDouble(getTagValue(recordContent, "Latitude")));
                aWeatherData_eg05.setLongitude(Double.parseDouble(getTagValue(recordContent, "Longitude")));
                aWeatherData_eg05.setDate(new java.sql.Date(convertStringToDate(getTagValue(recordContent, "Date")).getTime()));
                aWeatherData_eg05.setTemperatureCelsius(Double.parseDouble(getTagValue(recordContent, "TemperatureCelsius")));
                aWeatherData_eg05.setHumidityPercent(Integer.parseInt(getTagValue(recordContent, "HumidityPercent")));
                aWeatherData_eg05.setPrecipitationMm(Double.parseDouble(getTagValue(recordContent, "PrecipitationMm")));
                aWeatherData_eg05.setWindSpeedKmh(Double.parseDouble(getTagValue(recordContent, "WindSpeedKmh")));
                aWeatherData_eg05.setWeatherCondition(getTagValue(recordContent, "WeatherCondition"));
                aWeatherData_eg05.setForecast(getTagValue(recordContent, "Forecast"));
                aWeatherData_eg05.setUpdated(new java.sql.Date(convertStringToDate(getTagValue(recordContent, "Updated")).getTime()));
            } catch (NumberFormatException | ParseException e) {
                System.err.println("\nError en processar el registre: " + e.getMessage());
            }

            weatherRecords.add(aWeatherData_eg05);

            start = content.indexOf(recordStartTag, end + recordEndTag.length());
        }

        return weatherRecords;
    }

    /**
     * Converteix una cadena de text a una data.
     *
     * @param dateString La cadena de text amb la data.
     * @return La data convertida.
     * @throws ParseException Si el format de la cadena no és correcte.
     */
    private static Date convertStringToDate(String dateString) throws ParseException {
        if (dateString == null || dateString.isEmpty()) {
            throw new IllegalArgumentException("\nLa fecha no pot estar buida.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.parse(dateString);
    }

    /**
     * Obté el valor d'una etiqueta XML donada.
     *
     * @param content El contingut XML.
     * @param tagName El nom de l'etiqueta.
     * @return El valor dins de l'etiqueta.
     */
    private static String getTagValue(String content, String tagName) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("\nEl contingunt del tag no pot estar buit.");
        }
        if (tagName == null || tagName.isEmpty()) {
            throw new IllegalArgumentException("\nEl nom del tag no pot estar buit.");
        }

        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";
        int start = content.indexOf(openTag);
        int end = content.indexOf(closeTag, start);
        if (start == -1 || end == -1) {
            return "";
        }
        return content.substring(start + openTag.length(), end).trim();
    }

    /**
     * Mostra un menú amb opcions numerades per seleccionar elements separats
     * per comes.
     *
     * @return Una cadena amb els números seleccionats, separats per comes.
     */
    public static String elementsMenu() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Trieu els elements que vulgueu separats per comes i premeu enter.");
        String elements = null;
        System.out.println("\n\t1.- ID del registre: "
                + "\n\t2.- Nom de la ciutat: "
                + "\n\t3.- País: "
                + "\n\t4.- Latitud: "
                + "\n\t5.- Longitud: "
                + "\n\t6.- Data: "
                + "\n\t7.- Temperatura (°C): "
                + "\n\t8.- Humitat (%): "
                + "\n\t9.- Precipitacions (mm): "
                + "\n\t10.- Velocitat del vent (Km/h): "
                + "\n\t11.- Condicions meteorològiques: "
                + "\n\t12.- Pronòstic: "
                + "\n\t13.- Data d'actualització: ");
        System.out.println("\nSelecció:");
        boolean isInputValue = false;
        do {
            try {
                elements = lector.nextLine();
                isInputValue = true;
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introduïsca un número enter. " + nfe.getMessage());
            }
            if (!isInputValue) {
                System.out.println("\nTorne a intentar-ho.");
            }
        } while (!isInputValue);
        return elements;
    }

    /**
     * Inverteix una selecció d'elements. Retorna una llista amb els números que
     * no han estat seleccionats.
     *
     * @param numbers Llista d'enters seleccionats.
     * @return Llista d'enters no seleccionats.
     * @throws IllegalArgumentException Si la llista proporcionada és nul·la o
     * buida.
     */
    public static List<Integer> invertSelection(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("\nLa llista de números no pot estar buida.");
        }
        List<Integer> allNumbers = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            allNumbers.add(i);
        }

        allNumbers.removeAll(numbers);

        return allNumbers;
    }

    /**
     * Valida si una cadena proporcionada correspon a un DNI vàlid.
     *
     * @param dni La cadena a validar.
     * @return Cert si el DNI és vàlid.
     * @throws IllegalArgumentException Si la cadena no és vàlida com a DNI.
     */
    public static boolean isValidDni(String dni) {
        if (dni == null || dni.trim().length() < 8 || dni.trim().length() > 9) {
            throw new IllegalArgumentException("La cadena de caràcters proporcionada no correspon a un DNI.");
        }

        String regularExpresion = "^\\d{7,8}[A-Z]$";

        if (!dni.toUpperCase().matches(regularExpresion)) {
            throw new IllegalArgumentException("La cadena introduïda no correspon a un DNI.");
        }

        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumber = Integer.parseInt(dni.substring(0, dni.length() - 1));
        char correctLetter = letters.charAt(dniNumber % 23);

        if (dni.charAt(dni.length() - 1) != correctLetter) {
            throw new IllegalArgumentException("La lletra del DNI no coincideix amb els càlculs.");
        }

        return true;
    }

    /**
     * Genera la data actual en format SQL.
     *
     * @return Un objecte {@code java.sql.Date} amb la data actual.
     */
    public static java.sql.Date weatherDateNow() {
        LocalDate localDate = LocalDate.now();
        return java.sql.Date.valueOf(localDate);
    }

}

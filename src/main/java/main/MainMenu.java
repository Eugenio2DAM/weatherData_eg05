package main;

import connection.ConnectionBD;
import dataMySQL.DataAccessFunctionsMySQL;
import dataMongoDB.DataAccessFunctionsMongo;
import domain.Credential;
import domain.User;
import domain.WeatherData_eg05;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import utilities.Tools;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import static utilities.Tools.confirmation;
import static utilities.Tools.data;

/**
 *
 * @author eugenio
 */
public class MainMenu {

    private static boolean exit = false;
    private static boolean back = false;
    private static boolean isConnectedToMySQL = true;
    private static boolean isConnect;
    private static final String JSON_FILE_NAME = "WeatherData_eg05.json";

    /**
     * Mètode principal que gestiona l'execució del programa i el menú
     * d'opcions. El programa permet interactuar amb bases de dades MySQL i
     * MongoDB, realitzant operacions com insercions, eliminacions, llistats,
     * importacions des de fitxers XML i sincronització de dades.
     *
     * @param args arguments de línia de comandes (no utilitzats).
     */
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int option = 0;
        while (!exit) {
            System.out.println("\n************************");
            System.out.println("***  MENÚ PRINCIPAL  ***");
            System.out.println("************************\n");

            // Comprova si està connectat a MySQL o MongoDB i mostra el nombre de registres
            if (isConnectedToMySQL && isConnect) {
                System.out.println("---- Connectat a MySQL ----> Total de registres: " + DataAccessFunctionsMySQL.listData().size() + " ----\n");
            } else if (isConnect) {
                System.out.println("---- Connectat a MongoDB ---- Total de registres: " + DataAccessFunctionsMongo.listData().size() + " ----\n");
            }
            if (isConnect) {
                if (isConnectedToMySQL) {
                    if (DataAccessFunctionsMySQL.listData().size() < DataAccessFunctionsMongo.listData().size()) {
                        System.out.println("1.- Identificar-se");
                        System.out.println("2.- Canviar base de dades");
                        System.out.println("3.- Inserir dades atmosfèriques");
                        System.out.println("4.- Llistar totes les dades atmosfèriques");
                        System.out.println("5.- Eliminar totes les dades atmosfèriques");
                        System.out.println("6.- Llistar elements de les dades atmosfèriques per ciutat.");
                        System.out.println("7.- Eliminar elements de les dades atmosfèriques per ciutat");
                        System.out.println("8.- Importar dades des de XML");
                        System.out.println("9.- Sincronitzar bases de dades");
                        System.out.println("0.- Eixir");
                        System.out.println("\nTria una opció: ");
                    } else {
                        System.out.println("1.- Identificar-se");
                        System.out.println("2.- Canviar base de dades");
                        System.out.println("3.- Inserir dades atmosfèriques");
                        System.out.println("4.- Llistar totes les dades atmosfèriques");
                        System.out.println("5.- Eliminar totes les dades atmosfèriques");
                        System.out.println("6.- Llistar elements de les dades atmosfèriques per ciutat.");
                        System.out.println("7.- Eliminar elements de les dades atmosfèriques per ciutat");
                        System.out.println("8.- Importar dades des de XML");
                        System.out.println("0.- Eixir");
                        System.out.println("\nTria una opció: ");
                    }
                } else {
                    if (DataAccessFunctionsMySQL.listData().size() > DataAccessFunctionsMongo.listData().size()) {
                        System.out.println("1.- Identificar-se");
                        System.out.println("2.- Canviar base de dades");
                        System.out.println("3.- Inserir dades atmosfèriques");
                        System.out.println("4.- Llistar totes les dades atmosfèriques");
                        System.out.println("5.- Eliminar totes les dades atmosfèriques");
                        System.out.println("6.- Llistar elements de les dades atmosfèriques per ciutat.");
                        System.out.println("7.- Eliminar elements de les dades atmosfèriques per ciutat");
                        System.out.println("8.- Importar dades des de XML");
                        System.out.println("9.- Sincronitzar bases de dades");
                        System.out.println("10.- Realitzar Upsert d'un element donat.");
                        System.out.println("0.- Eixir");
                        System.out.println("\nTria una opció: ");
                    } else {
                        System.out.println("1.- Identificar-se");
                        System.out.println("2.- Canviar base de dades");
                        System.out.println("3.- Inserir dades atmosfèriques");
                        System.out.println("4.- Llistar totes les dades atmosfèriques");
                        System.out.println("5.- Eliminar totes les dades atmosfèriques");
                        System.out.println("6.- Llistar elements de les dades atmosfèriques per ciutat.");
                        System.out.println("7.- Eliminar elements de les dades atmosfèriques per ciutat");
                        System.out.println("8.- Importar dades des de XML");
                        System.out.println("10.- Realitzar Upsert d'un element donat.");
                        System.out.println("0.- Eixir");
                        System.out.println("\nTria una opció: ");
                    }
                }
            } else {
                System.out.println("1.- Identificar-se");
                System.out.println("0.- Eixir");
                System.out.println("\nTria una opció: ");
            }
            boolean isInputValue = false;
            do {
                try {
                    option = Integer.parseInt(lector.nextLine());
                    isInputValue = true;
                } catch (NumberFormatException nfe) {
                    System.out.println("\nS'espera que introduïsca un número enter. " + nfe.getMessage());
                }
                if (!isInputValue) {
                    System.out.println("\nTorne a intentar-ho.");
                }
            } while (!isInputValue);

            handleOption(option);
        }
    }

    /**
     * Gestiona les opcions seleccionades pel menú principal. Depenent de
     * l'opció triada, executa les accions corresponents, com
     * connectar/desconnectar bases de dades, inserir dades, eliminar-les,
     * importar des de XML o sincronitzar bases de dades.
     *
     * @param option l'opció seleccionada pel menú.
     */
    private static void handleOption(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("La opció deu de ser major a zero.");
        }

        switch (option) {
            case 1:
                login();
                break;
            case 2:
                if (isConnectedToMySQL) {

                    if (DataAccessFunctionsMySQL.listData().size() > DataAccessFunctionsMongo.listData().size()) {
                        exportWeatherDataJson();
                    }
                    System.out.println("\nConnectant a MongoDB....");
                    ConnectionBD.getConnectionMongoBD();
                } else {
                    if (DataAccessFunctionsMongo.listData().size() > DataAccessFunctionsMySQL.listData().size()) {
                        exportWeatherDataJson();
                    }
                    System.out.println("\nConnectant a MySQL....");
                    ConnectionBD.getConnectionMySQL();
                }
                isConnectedToMySQL = !isConnectedToMySQL;
                break;
            case 3:
                insertDataToIdCero();
                break;
            case 4:
                listMenu();
                break;
            case 5:
                deleteMenu();
                break;
            case 6:
                listMenuByElements();
                break;
            case 7:
                deleteMenuByElements();
                break;
            case 8:
                importDataFromXML();
                break;
            case 9:
                System.out.println("\nSincronitzant bases de dades....");

                if (Tools.requestAlertSynchronize()) {

                    deleteAllData();
                    copyData();

                } else {
                    System.out.println("\nSincronització anul·lada");
                }
                break;
            case 10:
                upsertData();
                break;
            case 0:
                if (isConnectedToMySQL) {
                    if (DataAccessFunctionsMySQL.listData().size() >= DataAccessFunctionsMongo.listData().size()) {
                        exportWeatherDataJson();

                    }
                } else {
                    if (DataAccessFunctionsMongo.listData().size() >= DataAccessFunctionsMySQL.listData().size()) {
                        exportWeatherDataJson();
                    }
                }
                System.out.println("\nEixint de l'aplicació...");
                exit = true;
                break;
            default:
                System.out.println("\nOpció no vàlida.");
        }
    }

    /**
     * Gestiona el procés d'autenticació de l'usuari. Demana el DNI i la
     * contrasenya, valida el format del DNI i comprova les credencials. Si
     * l'autenticació és correcta, estableix la connexió com a activa.
     */
    private static void login() {
        String dni = null;
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                dni = Tools.requestDni();
                if (!Tools.isValidDni(dni)) {
                    System.out.println("\nEl DNI no és correcte. Torneu a escriure'l.");
                } else {
                    isValidInput = true;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Format de DNI incorrecte. " + iae.getMessage());
            }

            String password = Tools.requestPassword();

            if (authenticateUser(dni, password)) {
                isConnect = true;
                System.out.println("\nAutenticació realitzada amb èxit.");
            } else {
                System.out.println("\nContrasenya incorrecta.");
            }
        }
    }

    /**
     * Permet introduir dades meteorològiques al sistema, assignant un ID únic a
     * cada registre. Els usuaris poden proporcionar informació com ara ciutat,
     * país, coordenades, condicions meteorològiques i previsió. Les dades es
     * guarden a la base de dades seleccionada (MySQL o MongoDB).
     */
    private static void insertDataToIdCero() {
        Integer recordId = 1;
        System.out.println("\nInsereix dades, premeu \"0\" per tornar al menú.");

        while (recordId != 0) {
            recordId = Tools.requestRecordId();

            if (recordId == 0) {
                System.out.println("\nEixint ....");
                break;
            }
            boolean isValidInput = false;
            int count = 0;
            while (!isValidInput) {
                if (count >= 1) {
                    recordId = newRecordId(recordId);
                }

                if (isValidrecordId(recordId)) {
                    isValidInput = true;
                }

                count++;
            }

            String city = Tools.requestCityName();
            String country = Tools.requestCountry();
            Double latitude = Tools.requestLatitude();
            Double longitude = Tools.requestLongitude();
            Date date = Tools.weatherDateNow();
            Double temperatureCelsius = Tools.requestTemperatureCelsius();
            Integer humidityPercent = Tools.requestHumidityPercent();
            Double precipitationMm = Tools.requestPrecipitationMm();
            Double windSpeedKmh = Tools.requestWindSpeedKmh();
            String weatherCondition = Tools.requestWeatherCondition();
            String forecast = Tools.requestForecast();
            Date updated = Tools.weatherDateNow();

            WeatherData_eg05 aWetherData_eg5 = new WeatherData_eg05(recordId, city, country,
                    latitude, longitude, date, temperatureCelsius, humidityPercent, precipitationMm,
                    windSpeedKmh, weatherCondition, forecast, updated);

            if (isConnectedToMySQL) {
                DataAccessFunctionsMySQL.insertData(aWetherData_eg5);
            } else {
                DataAccessFunctionsMongo.insertData(aWetherData_eg5);
            }

        }
    }

    /**
     * Realitza una operació d'actualització o inserció (upsert) d'un registre
     * meteorològic. Si l'ID proporcionat ja existeix a la base de dades, el
     * registre s'actualitza amb les noves dades. Si no existeix, s'afegeix un
     * nou registre a la llista.
     */
    public static void upsertData() {
        System.out.println("\nInsereix l'ID del registre a actualitzar; si no existira, s'afegirà a la llista.");

        Integer recordId = Tools.requestRecordId();
        String city = Tools.requestCityName();
        String country = Tools.requestCountry();
        Double latitude = Tools.requestLatitude();
        Double longitude = Tools.requestLongitude();
        Date date = searchInsertDate();
        Double temperatureCelsius = Tools.requestTemperatureCelsius();
        Integer humidityPercent = Tools.requestHumidityPercent();
        Double precipitationMm = Tools.requestPrecipitationMm();
        Double windSpeedKmh = Tools.requestWindSpeedKmh();
        String weatherCondition = Tools.requestWeatherCondition();
        String forecast = Tools.requestForecast();
        Date updated = Tools.weatherDateNow();

        WeatherData_eg05 aWetherData_eg5 = new WeatherData_eg05(recordId, city, country,
                latitude, longitude, date, temperatureCelsius, humidityPercent, precipitationMm,
                windSpeedKmh, weatherCondition, forecast, updated);

        DataAccessFunctionsMongo.upsertWeatherDataMongo(aWetherData_eg5);
    }

    /**
     * Comprova si l'ID del registre proporcionat és vàlid. Un ID és considerat
     * vàlid si no existeix actualment en la base de dades seleccionada.
     *
     * @param aRecordId l'ID del registre a comprovar.
     * @return true si l'ID és vàlid (no existeix a la base de dades), false en
     * cas contrari.
     */
    private static boolean isValidrecordId(Integer aRecordId) {
        if (aRecordId < 0) {
            throw new IllegalArgumentException("El iD deu de ser major a zero.");
        }
        boolean isRecordId = true;
        List<WeatherData_eg05> WeatherDataList;

        // Obté la llista de dades meteorològiques de la base de dades activa.
        if (isConnectedToMySQL) {
            WeatherDataList = DataAccessFunctionsMySQL.listData();
        } else {
            WeatherDataList = DataAccessFunctionsMongo.listData();
        }

        // Recorre la llista per verificar si l'ID ja existeix.
        for (int i = 0; i < WeatherDataList.size(); i++) {
            if (WeatherDataList.get(i).getRecordId().equals(aRecordId)) {
                isRecordId = false; // L'ID ja existeix, no és vàlid.
                break;
            }
        }

        return isRecordId; // Retorna true si l'ID no existeix; false si ja existeix.
    }

    /**
     * Genera un nou ID de registre per evitar duplicats. Aquesta funció
     * incrementa l'ID del registre fins que troba un valor únic que no existeix
     * a la base de dades.
     *
     * @param recordId l'ID inicial a partir del qual es comença a cercar un ID
     * únic.
     * @return un ID de registre nou i únic.
     */
    private static int newRecordId(Integer recordId) {
        if (recordId < 0) {
            throw new IllegalArgumentException("El iD deu de ser major a zero.");
        }
        int newRecordId = recordId;
        List<WeatherData_eg05> WeatherDataList;

        // Obté la llista de dades meteorològiques de la base de dades activa.
        if (isConnectedToMySQL) {
            WeatherDataList = DataAccessFunctionsMySQL.listData();
        } else {
            WeatherDataList = DataAccessFunctionsMongo.listData();
        }

        // Comprova si l'ID del registre ja existeix a la llista, i si és així, incrementa'l.
        for (int i = 0; i < WeatherDataList.size(); i++) {
            if (WeatherDataList.get(i).getRecordId().equals(recordId)) {
                newRecordId++; // Si l'ID existeix, incrementa'l per generar un ID únic.
            }
        }

        return newRecordId; // Retorna el nou ID únic.
    }

    /**
     * Copia les dades meteorològiques des d'una font i les insereix a la base
     * de dades activa. Aquesta funció llegeix les dades meteorològiques i les
     * insereix una per una en la base de dades, ja sigui MySQL o MongoDB,
     * segons la connexió activa.
     */
    private static void copyData() {
        List<WeatherData_eg05> aWeatherDataList = Tools.readWeatherData();

        for (int i = 0; i < aWeatherDataList.size(); i++) {
            if (isConnectedToMySQL) {
                DataAccessFunctionsMySQL.insertData(aWeatherDataList.get(i));
            } else {
                DataAccessFunctionsMongo.insertData(aWeatherDataList.get(i));
            }
        }
    }

    /**
     * Copia les dades meteorològiques d'una llista a la base de dades activa,
     * evitant duplicats en MongoDB. Si la connexió és a MySQL, inserta les
     * dades directament a MySQL. Si la connexió és a MongoDB, comprova si hi ha
     * duplicats abans d'inserir les dades.
     *
     * @param listWeatherData Llista de dades meteorològiques a inserir a la
     * base de dades.
     */
    private static void copyData(List<WeatherData_eg05> listWeatherData) {
        if (listWeatherData == null) {
            throw new IllegalArgumentException("La llista proporcionada está buida.");
        }

        List<WeatherData_eg05> listData = new ArrayList<>();

        if (!isConnectedToMySQL) {
            listData = DataAccessFunctionsMongo.listData();
        }

        for (WeatherData_eg05 weatherData : listWeatherData) {
            if (isConnectedToMySQL) {
                DataAccessFunctionsMySQL.insertData(weatherData);
            } else {
                boolean isDuplicate = false;
                for (WeatherData_eg05 existingData : listData) {
                    if (existingData.getRecordId().equals(weatherData.getRecordId())) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate) {
                    System.out.println("\nError al insertar el registro: Duplicate entry '"
                            + weatherData.getRecordId()
                            + "' for key 'WeatherData_eg05'.");
                } else {
                    DataAccessFunctionsMongo.insertData(weatherData);
                }
            }
        }
    }

    /**
     * Importa dades meteorològiques des d'un arxiu XML i les copia a la base de
     * dades activa. Si l'usuari confirma l'importació, es procedeix a llegir i
     * copiar les dades, en cas contrari, es cancela l'operació.
     */
    private static void importDataFromXML() {
        System.out.println("\nImportant dades des de XML...");

        if (Tools.requestAlertImportXml()) {
            try {
                List<WeatherData_eg05> aWeatherData_eg05List = Tools.extractWeatherData();
                copyData(aWeatherData_eg05List);
            } catch (Exception ex) {
                System.out.println("\nNo s'ha pogut importar el \".xml\"" + ex.getMessage());
            }
        } else {
            System.out.println("\nImportació anul·lada");
        }
    }

    /**
     * Autentica a l'usuari amb el seu DNI i contrasenya. Si les credencials són
     * correctes, es mostra un missatge de benvinguda amb la temperatura de la
     * seva ciutat. Si les credencials són incorrectes, es demana intentar-ho de
     * nou.
     *
     * @param dni El DNI de l'usuari.
     * @param password La contrasenya de l'usuari.
     * @return Retorna true si l'autenticació és correcta, false en cas
     * contrari.
     */
    private static boolean authenticateUser(String dni, String password) {
        if (dni == null || password == null) {
            throw new IllegalArgumentException("Algún del parametres introduïts esta buit.");
        }

        Integer userId;
        String city = null;
        String savedPassword = "";
        String firstName = null;
        String lastName = null;
        Double temperature = null;

        // Obtenim les llistes d'usuaris, dades meteorològiques i credencials des de la base de dades.
        List<User> listUsers = DataAccessFunctionsMySQL.listUsers();
        List<WeatherData_eg05> listWeatherData = DataAccessFunctionsMySQL.listData();
        List<Credential> listCredentials = DataAccessFunctionsMySQL.listCredentials();

        // Busquem l'usuari amb el DNI proporcionat.
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getDni().equals(dni)) {

                // Obtenim les dades personals de l'usuari.
                userId = listUsers.get(i).getUserId();
                firstName = listUsers.get(i).getName();
                lastName = listUsers.get(i).getSurname();
                city = listUsers.get(i).getCity();

                // Busquem la temperatura associada a la ciutat de l'usuari (última en entrar).
                for (int k = listWeatherData.size() - 1; k >= 0; k--) {
                    if (city.equals(listWeatherData.get(k).getCity())) {
                        temperature = listWeatherData.get(k).getTemperatureCelsius();
                    }
                }

                // Obtenim la contrasenya de l'usuari.
                for (int l = 0; l < listCredentials.size(); l++) {
                    if (userId.equals(listCredentials.get(l).getUserId())) {
                        savedPassword = listCredentials.get(l).getPassword();
                    }
                }
            }
        }

        if (savedPassword.equals(password)) {
            showWelcomeMessage(firstName, lastName, city, temperature);
            return true;
        } else {
            System.out.println("\nCredencials incorrectes. Torna-ho a intentar.");
            return false;
        }

    }

    /**
     * Mostra un missatge de benvinguda amb el nom de l'usuari, la seva ciutat i
     * la temperatura actual si està disponible. Si la temperatura no està
     * disponible, es mostra un missatge indicant-ho.
     *
     * @param firstName El primer nom de l'usuari.
     * @param lastName El cognom de l'usuari.
     * @param cityName El nom de la ciutat de l'usuari.
     * @param temperature La temperatura actual de la ciutat de l'usuari, pot
     * ser null si no està disponible.
     */
    private static void showWelcomeMessage(String firstName, String lastName, String cityName, Double temperature) {

        System.out.print("\nBenvingut " + firstName + " " + lastName);

        if (temperature != null) {
            System.out.println(" a la teva ciutat " + cityName + ", hi ha una temperatura de " + temperature + "°C.\n");
        } else {
            System.out.println(" a la teva ciutat " + cityName + ", actualment no tenim dades de la temperatura.\n");
        }
    }

    /**
     * Cerca la primera data de la llista de dades meteorològiques disponibles.
     * Recupera la data del primer element de la llista de dades de la base de
     * dades seleccionada (MySQL o MongoDB).
     *
     * @return La data del primer element de la llista de dades meteorològiques,
     * o null si no hi ha dades.
     */
    private static Date searchInsertDate() {
        // Es llegeixen les dades meteorològiques de la base de dades segons la connexió actual.
        List<WeatherData_eg05> WeatherDataList;
        if (isConnectedToMySQL) {
            WeatherDataList = DataAccessFunctionsMySQL.listData();
        } else {
            WeatherDataList = DataAccessFunctionsMongo.listData();
        }

        Date date = null;
        // Es recupera la data del primer element de la llista.
        for (int i = 0; i < WeatherDataList.size(); i++) {
            date = WeatherDataList.get(i).getDate();
            break; // Només es retorna la data del primer element.
        }
        return date;
    }

    /**
     * Exporta les dades meteorològiques a un arxiu JSON. La funció escriu les
     * dades meteorològiques des de la base de dades seleccionada (MySQL o
     * MongoDB) en un arxiu JSON.
     */
    private static void exportWeatherDataJson() {
        BufferedWriter writeFile = null;
        // Capçalera i peu del fitxer JSON.
        String headerJson = "{\n  \"WeatherDate_eg05\": [\n";
        String footerData = "\t},\n";
        String lastFooterData = "\t}\n";
        String footerJson = "  ]\n}\n";

        try {
            // Obrim el fitxer per escriure-hi.
            writeFile = new BufferedWriter(new FileWriter(JSON_FILE_NAME));
            writeFile.write(headerJson);

            // Obtenim les dades de la base de dades.
            List<WeatherData_eg05> aWeatherDataList;
            if (DataAccessFunctionsMySQL.listData().size() > DataAccessFunctionsMongo.listData().size()) {
                aWeatherDataList = DataAccessFunctionsMySQL.listData();
            } else {
                aWeatherDataList = DataAccessFunctionsMongo.listData();
            }

            // Escriu les dades de cada registre en el fitxer.
            for (int i = 0; i < aWeatherDataList.size(); i++) {
                writeFile.write(aWeatherDataList.get(i).toJson());
                if (aWeatherDataList.size() - 1 > i) {
                    writeFile.write(footerData);
                } else if (aWeatherDataList.size() - 1 == i) {
                    writeFile.write(lastFooterData);
                }
            }
            writeFile.write(footerJson);
            writeFile.flush(); // Es força a escriure el contingut en el fitxer.
        } catch (IOException ex) {
            System.out.println("\nNo s'ha pogut escriure en l'arxiu \".json\". " + ex.getMessage());
        } finally {
            try {
                // Tanquem el fitxer després d'escriure les dades.
                if (writeFile != null) {
                    writeFile.close();
                }
            } catch (IOException e) {
                System.out.println("\nError al tancar l'arxiu \"json\". " + e.getMessage());
            }
        }
    }

    /**
     * Mètode per mostrar el menú de llistat d'opcions i gestionar la selecció
     * de l'usuari. El mètode permet al usuari elegir entre diferents opcions
     * per llistar les dades meteorològiques.
     */
    private static void listMenu() {
        Scanner lector = new Scanner(System.in);
        int option = 0;
        back = false;
        while (!back) {
            System.out.println("\n**********************");
            System.out.println("***  MENÚ LLISTAR  ***");
            System.out.println("**********************\n");

            // Comprova si està connectat a MySQL o MongoDB i mostra el nombre de registres
            if (isConnectedToMySQL && isConnect) {
                System.out.println("---- Connectat a MySQL ----> Total de Registres: " + DataAccessFunctionsMySQL.listData().size() + " ----\n");
            } else if (isConnect) {
                System.out.println("---- Connectat a MongoDB ----> Total de Registres: " + DataAccessFunctionsMongo.listData().size() + " ----\n");
            }

            System.out.println("1.- Llistar per ciutat.");
            System.out.println("2.- Llistar per conjunt de ciutats.");
            System.out.println("3.- Llistar totes les ciutats.");
            System.out.println("4.- Enrere");
            System.out.println("\nTria una opció: ");

            try {
                option = Integer.parseInt(lector.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introduïsca un número enter. " + nfe.getMessage());
            }

            listOption(option);
        }
    }

    /**
     * Mètode per gestionar les diferents opcions del menú de llistat.
     *
     * @param option L'opció escollida per l'usuari.
     */
    private static void listOption(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("Deu de ingrsar un número major de cero.");
        }
        List<WeatherData_eg05> listData;
        if (isConnectedToMySQL) {
            listData = DataAccessFunctionsMySQL.listData();
        } else {
            listData = DataAccessFunctionsMongo.listData();
        }
        switch (option) {
            case 1:
                String city = Tools.requestCityName();
                listDataByCityIds(listData, Tools.data(listData, city));
                break;
            case 2:
                System.out.println("\nIntroduïsca les ciutats per a les quals desitja dades meteorològiques.");
                String citiesName = Tools.requestCitiesName();
                List<String> citiesNameList;
                // Obtenir la llista de ciutats i llistar per cada ciutat
                citiesNameList = Tools.getCitiesFromString(citiesName);
                for (String cityName : citiesNameList) {
                    listDataByCityIds(listData, Tools.data(listData, cityName));
                }
                break;
            case 3:
                listAllData(listData);
                break;
            case 4:
                System.out.println("\nTornant al menú principal...");
                back = true;
                break;
            default:
                System.out.println("\nOpció no vàlida.");
        }
    }

    /**
     * Mètode per mostrar totes les dades meteorològiques en la llista
     * proporcionada. Mostra les dades de manera ordenada per ciutat i permet a
     * l'usuari navegar entre els resultats.
     *
     * @param listData Llista de dades meteorològiques a mostrar.
     */
    private static void listAllData(List<WeatherData_eg05> listData) {
        if (listData == null || listData.isEmpty()) {
            throw new IllegalArgumentException("La llista proporcionada esta buida.");
        }
        System.out.println("\nMostrant dades...");
        Scanner lector = new Scanner(System.in);
        int count = 0;

        if (listData.isEmpty()) {
            System.out.println("\nLa base de dades està buida.");
        } else {
            // Ordenar la llista d'objectes WeatherData_eg05 per la ciutat alfabèticament
            Collections.sort(listData, new Comparator<WeatherData_eg05>() {
                @Override
                public int compare(WeatherData_eg05 aWeatherData_eg05, WeatherData_eg05 otherWeatherData_eg05) {
                    return aWeatherData_eg05.getCity().compareTo(otherWeatherData_eg05.getCity());
                }
            });

            // Mostrar les dades de dos en dos i esperar la resposta de l'usuari per continuar
            for (int i = 0; i < listData.size(); i++) {
                System.out.println(listData.get(i).toString());
                count++;
                if (count == 2) {
                    System.out.println("\nPrem \"Intro\" per continuar o prem \"S\" per eixir.");
                    String next = lector.nextLine().toUpperCase();
                    if (next.equals("S")) {
                        break;
                    }
                    count = 0;
                }
            }
        }
    }

    /**
     * Mètode per llistar les dades meteorològiques per un conjunt de ciutats
     * identificades per les seues IDs. Mostra les dades per cada ciutat segons
     * els seus identificadors en la llista proporcionada.
     *
     * @param listData Llista de dades meteorològiques a mostrar.
     * @param cityIds Llista d'IDs de les ciutats per les quals es volen mostrar
     * les dades.
     */
    private static void listDataByCityIds(List<WeatherData_eg05> listData, List<Integer> cityIds) {
        System.out.println("\nMostrant dades...");
        Scanner lector = new Scanner(System.in);

        int count = 0;
        // Recórrer totes les ciutats identificades per les IDs proporcionades
        for (int i = 0; i < cityIds.size(); i++) {
            boolean isDateWithCity = false;
            // Buscar les dades de cada ciutat per la seua ID
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(j).getRecordId().equals(cityIds.get(i))) {
                    System.out.println(listData.get(j));
                    isDateWithCity = true;
                    count++;
                    if (count == 2) {
                        System.out.println("\nPrem \"Intro\" per continuar o prem \"S\" per eixir.");
                        String next = lector.nextLine().toUpperCase();
                        if (next.equals("S")) {
                            break;
                        }
                        count = 0;
                        break;
                    }
                }
            }
            if (!isDateWithCity) {
                System.out.println("\nLa ciutat no està a la base de dades.");
            }
        }
    }

    /**
     * Métode principal del menú de eliminació de dades meteorológiques.
     * Permeteix eliminar registros meteorológics en funció de diferents
     * criteris.
     */
    private static void deleteMenu() {
        Scanner lector = new Scanner(System.in);
        int option = 0;
        back = false;
        while (!back) {
            System.out.println("\n***********************");
            System.out.println("***  MENÚ ELIMINAR  ***");
            System.out.println("***********************\n");

            // Comprova si està connectat a MySQL o MongoDB i mostra el nombre de registres
            if (isConnectedToMySQL && isConnect) {
                System.out.println("---- Conectat a MySql ----> Total de Registres: " + DataAccessFunctionsMySQL.listData().size() + " ----\n");
            } else if (isConnect) {
                System.out.println("---- Conectat a MongoDB ----> Total de Registres: " + DataAccessFunctionsMongo.listData().size() + " ----\n");
            }
            System.out.println("1.- Eliminar per ciutat.");
            System.out.println("2.- Eliminar per conjunt de ciutats.");
            System.out.println("3.- Eliminar totes les ciutats amb confirmació de borrat.");
            System.out.println("4.- Eliminar totes les ciutats sense confirmació de borrat.");
            System.out.println("5.- Enrere");
            System.out.println("\nTria una opció: ");
            try {
                option = Integer.parseInt(lector.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Opció no vàlida. Torna a provar. " + nfe.getMessage());
            }

            deleteOption(option);
        }
    }

    /**
     * Tracta la selecció de l'usuari en el menú d'eliminació.
     *
     * @param option L'opció triada per l'usuari.
     */
    private static void deleteOption(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("Deu de ingrsar un número major de cero.");
        }
        switch (option) {
            case 1:
                String city = Tools.requestCityName();
                List<WeatherData_eg05> listData;
                if (isConnectedToMySQL) {
                    listData = DataAccessFunctionsMySQL.listData();
                } else {
                    listData = DataAccessFunctionsMongo.listData();
                }

                if (cityHasData(listData, city)) {
                    deleteDataByCityIds(Tools.data(listData, city));
                } else {
                    System.out.println("\n" + city + " no es troba a la base de dades.");
                }
                break;
            case 2:
                System.out.println("\nIntrodueix les ciutats de les quals desitges dades atmosfèriques.");
                String cities = Tools.requestCitiesName();

                if (isConnectedToMySQL) {
                    listData = DataAccessFunctionsMySQL.listData();
                } else {
                    listData = DataAccessFunctionsMongo.listData();
                }

                List<String> citiesName = Tools.getCitiesFromString(cities);

                List<Integer> recordsId = new ArrayList<>();

                for (int i = 0; i < citiesName.size(); i++) {
                    if (cityHasData(listData, citiesName.get(i))) {
                        List<Integer> cityId = Tools.data(listData, citiesName.get(i));
                        for (int j = 0; j < cityId.size(); j++) {
                            recordsId.add(cityId.get(j));
                        }
                    } else {
                        System.out.println("\n" + citiesName.get(i) + " no es troba a la base de dades.");
                    }
                }
                deleteDataByCityIds(recordsId);
                break;
            case 3:
                deleteAllDataWithConfirmation();
                break;
            case 4:
                deleteAllData();
                break;
            case 5:
                System.out.println("\nTornant al menú principal...");
                back = true;
                break;
            default:
                System.out.println("\nOpció no vàlida.");
        }
    }

    /**
     * Comprova si una ciutat té dades a la llista proporcionada.
     *
     * @param listData La llista de dades meteorològiques.
     * @param city El nom de la ciutat a cercar.
     * @return Verifica si la ciutat té dades a la llista.
     */
    private static boolean cityHasData(List<WeatherData_eg05> listData, String city) {
        if (listData == null || listData.isEmpty()) {
            throw new IllegalArgumentException("La llista de dades esta buida.");
        }
        boolean cityHasData = false;

        List<String> citiesName = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {

            if (listData.get(i).getCity().equals(city)) {
                citiesName.add(listData.get(i).getCity());
            }
        }

        for (int i = 0; i < citiesName.size(); i++) {
            for (int j = 0; j < listData.size(); j++) {
                if (listData.get(j).getCity().equals(citiesName.get(i))) {
                    cityHasData = true;
                }
            }
        }
        return cityHasData;
    }

    /**
     * Elimina totes les dades meteorològiques amb confirmació de l'usuari.
     * Abans de l'eliminació, sol·licita una confirmació per part de l'usuari.
     * Un cop confirmat, elimina tots els registres de la base de dades.
     */
    private static void deleteAllDataWithConfirmation() {
        Scanner lector = new Scanner(System.in);
        boolean isValidInput = false;
        String alertResponse;

        System.out.println("Estic preparant l'eliminació de totes les dades.");

        // Sol·licita una alerta de confirmació abans de continuar
        while (!isValidInput) {
            System.out.println("\nVols realment eliminar les dades? (si/no)");
            alertResponse = lector.nextLine().trim().toLowerCase();

            if (confirmation(alertResponse)) {
                isValidInput = true;
            } else {
                System.out.println("Resposta no vàlida. Introdueix 'si' o 'no'.");
            }
        }

        List<WeatherData_eg05> dataList;

        if (isConnectedToMySQL) {
            dataList = DataAccessFunctionsMySQL.listData();
        } else {
            dataList = DataAccessFunctionsMongo.listData();
        }

        System.out.println("\nEliminant " + dataList.size() + " registres ...");

        int count = 0;

        // Elimina cada registre
        for (WeatherData_eg05 data : dataList) {
            int recordId = data.getRecordId();
            if (isConnectedToMySQL) {
                DataAccessFunctionsMySQL.deleteData(recordId);
            } else {
                DataAccessFunctionsMongo.deleteData(recordId);
            }
            count++; // Compta els registres eliminats
        }

        // Mostra el resultat de l'eliminació
        if (count == dataList.size()) {
            System.out.println("\nS'han esborrat tots els registres. Total eliminats " + (count));
        } else {
            System.out.println("\nNo s'han pogut esborrar tots els registres. Total eliminats " + (count));
        }
    }

    /**
     * Elimina totes les dades sense confirmació de l'usuari.
     */
    private static void deleteAllData() {
        try {
            List<WeatherData_eg05> dataList;
            int totalDeleted = 0;

            do {
                // Obtenir la llista de registres
                if (isConnectedToMySQL) {
                    dataList = DataAccessFunctionsMySQL.listData();
                } else {
                    dataList = DataAccessFunctionsMongo.listData();
                }

                System.out.println("\nEliminant " + dataList.size() + " registres ...");

                for (int i = 0; i < dataList.size(); i++) {
                    int recordId = dataList.get(i).getRecordId();
                    // Eliminar el registre segons la connexió activa
                    if (isConnectedToMySQL) {
                        DataAccessFunctionsMySQL.deleteData(recordId);
                    } else {
                        DataAccessFunctionsMongo.deleteData(recordId);
                    }

                    totalDeleted++;
                }

            } while (!dataList.isEmpty());

            System.out.println("\nTots els registres s'han eliminat. Total eliminats: " + totalDeleted);

        } catch (Exception e) {
            System.err.println("\nError durant l'eliminació: " + e.getMessage());
        }
    }

    /**
     * Elimina els registres de dades meteorològiques per una llista d'IDs de
     * ciutats. Si la llista d'IDs és nula, llença una excepció. Després de la
     * confirmació de l'usuari, elimina els registres corresponents.
     *
     * @param recordsId Llista d'IDs de registres (ciutats) que es volen
     * eliminar.
     * @throws IllegalArgumentException Si la llista d'IDs és nul·la.
     */
    private static void deleteDataByCityIds(List<Integer> recordsId) {
        if (recordsId == null) {
            throw new IllegalArgumentException("\nLa llista proporcionada esta buida.");
        }
        Scanner lector = new Scanner(System.in);
        int count = 0;
        boolean isValidInput = false;
        String alertResponse;
        System.out.println("\nEliminant " + recordsId.size() + " registres ...");

        // Sol·licita una alerta de confirmació abans de continuar
        while (!isValidInput) {
            System.out.println("\nVols realment eliminar les dades? (si/no)");
            alertResponse = lector.nextLine().trim().toLowerCase();

            if (confirmation(alertResponse)) {
                isValidInput = true;
            } else {
                System.out.println("Resposta no vàlida. Introdueix 'si' o 'no'.");
            }
        }

        // Elimina cada registre per ID
        for (Integer recordId : recordsId) {
            if (isConnectedToMySQL) {
                DataAccessFunctionsMySQL.deleteData(recordId);
            } else {
                DataAccessFunctionsMongo.deleteData(recordId);
            }
            count++; // Compta els registres eliminats
        }

        if (recordsId.size() == count) {
            System.out.println("\nS'han esborrat tots els registres desitjats. Total eliminats " + (count));
        } else {
            System.out.println("\nNo s'han esborrat tots els registres desitjats. Total eliminats " + (count));
        }
    }

    private static void listMenuByElements() {
        Scanner lector = new Scanner(System.in);
        int option = 0;
        back = false;
        while (!back) {
            System.out.println("\n*******************************************");
            System.out.println("***  MENÚ LLISTAR ELEMENTS PER CIUTAT  ****");
            System.out.println("*******************************************\n");

            // Comprova si està connectat a MySQL o MongoDB i mostra el nombre de registres
            if (isConnectedToMySQL && isConnect) {
                System.out.println("---- Connectat a MySql ----> Total de Registres: " + DataAccessFunctionsMySQL.listData().size() + " ----\n");
            } else if (isConnect) {
                System.out.println("---- Connectat a MongoDB ----> Total de Registres: " + DataAccessFunctionsMongo.listData().size() + " ----\n");
            }

            System.out.println("1.- Llistar per ciutat.");
            System.out.println("2.- Llistar per conjunt de ciutats.");
            System.out.println("3.- Llistar totes les ciutats.");
            System.out.println("4.- Enrere");
            System.out.println("\nTria una opció: ");

            try {
                option = Integer.parseInt(lector.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introdueixca un nombre enter. " + nfe.getMessage());
            }

            listByElementsOption(option);
        }
    }

    private static void listByElementsOption(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("La opció deu de ser major a zero.");
        }
        switch (option) {
            case 1:
                // Opció per llistar per ciutat
                Scanner lector = new Scanner(System.in);
                String city = Tools.requestCityName();  // Demana el nom de la ciutat
                List<WeatherData_eg05> listData;

                // Comprova la base de dades a la qual està connectat
                if (isConnectedToMySQL) {
                    listData = DataAccessFunctionsMySQL.listData();
                } else {
                    listData = DataAccessFunctionsMongo.listData();
                }

                // Filtra els registres per ciutat
                List<Integer> recordsId = Tools.data(listData, city);

                // Demana els elements que l'usuari vol veure
                String elementsSelected = Tools.elementsMenu();
                List<Integer> optionElements = Tools.getElementsFromString(elementsSelected);
                boolean isDateWithCity = false;
                boolean stop = false;
                int count = 0;

                System.out.println("\nSeleccione de la llista separant per una coma els elements a visualitzar.");

                // Processa els registres per ciutat
                for (int i = 0; i < listData.size(); i++) {
                    for (int j = 0; j < recordsId.size(); j++) {
                        if (recordsId.get(j).equals(listData.get(i).getRecordId())) {
                            isDateWithCity = true;
                            count++;
                            System.out.println("\n****  DADES DE " + listData.get(i).getCity()
                                    + " AMD ID " + listData.get(i).getRecordId() + "  ****\n");

                            // Mostra els elements seleccionats
                            for (int k = 0; k < optionElements.size(); k++) {
                                String elements = selectElementsName(optionElements.get(k));
                                System.out.println(elements + selectElements(listData.get(i), optionElements.get(k)));
                            }

                            // Pregunta si vol continuar veient més registres o sortir
                            if (count == 2) {
                                System.out.println("\nPrem \"Intro\" per continuar, prem \"S\" per sortir.");
                                String next = lector.nextLine().toUpperCase();
                                if (next.equals("S")) {
                                    stop = true;
                                    break;
                                }
                                count = 0;  // Reseteja el comptador
                            }
                        }
                    }
                    if (stop) {
                        break;
                    }
                }

                // Si no es troba la ciutat en la base de dades
                if (!isDateWithCity) {
                    System.out.println("La ciutat no es troba en la base de dades");
                }

                break;

            case 2:
                // Opció per llistar per conjunt de ciutats
                lector = new Scanner(System.in);
                System.out.println("\nIntrodueix les ciutats de les quals desitges dades atmosfèriques.");
                String citiesName = Tools.requestCitiesName();  // Demana els noms de les ciutats
                if (isConnectedToMySQL) {
                    listData = DataAccessFunctionsMySQL.listData();
                } else {
                    listData = DataAccessFunctionsMongo.listData();
                }

                elementsSelected = Tools.elementsMenu();  // Demana els elements a mostrar
                optionElements = Tools.getElementsFromString(elementsSelected);
                isDateWithCity = false;
                stop = false;
                count = 0;

                System.out.println("\nSeleccione de la llista separant per una coma els elements a visualitzar.");

                // Obté la llista de ciutats
                List<String> cities = Tools.getCitiesFromString(citiesName);

                // Processa cada ciutat
                for (int i = 0; i < cities.size(); i++) {
                    List<Integer> recordIds = data(listData, cities.get(i));
                    for (int j = 0; j < listData.size(); j++) {
                        if (recordIds.contains(listData.get(j).getRecordId())) {
                            isDateWithCity = true;
                            count++;
                            System.out.println("\n****  DADES DE " + listData.get(j).getCity() + " AMD ID " + listData.get(j).getRecordId() + "  ****\n");

                            // Mostra els elements seleccionats
                            for (int l = 0; l < optionElements.size(); l++) {
                                String elements = selectElementsName(optionElements.get(l));
                                System.out.println(elements + selectElements(listData.get(j), optionElements.get(l)));
                            }

                            // Pregunta si vol continuar veient més registres o sortir
                            if (count == 2) {
                                System.out.println("\nPrem \"Intro\" per continuar, prem \"S\" per sortir.");
                                String next = lector.nextLine().toUpperCase();
                                if (next.equals("S")) {
                                    stop = true;
                                    break;
                                }
                                count = 0;
                            }
                        }
                    }
                    if (stop) {
                        break;
                    }
                }

                // Si no es troba cap dada per les ciutats
                if (!isDateWithCity) {
                    System.out.println("La ciutat no es troba en la base de dades");
                }

                break;

            case 3:
                // Opció per llistar totes les ciutats
                lector = new Scanner(System.in);
                if (isConnectedToMySQL) {
                    listData = DataAccessFunctionsMySQL.listData();
                } else {
                    listData = DataAccessFunctionsMongo.listData();
                }

                // Si la base de dades està buida
                if (listData.isEmpty()) {
                    System.out.println("\nLa base de dades està buida.");
                } else {
                    // Ordena les ciutats per ordre alfabètic
                    Collections.sort(listData, new Comparator<WeatherData_eg05>() {
                        @Override
                        public int compare(WeatherData_eg05 aWeatherData_eg05, WeatherData_eg05 otherWeatherData_eg05) {
                            return aWeatherData_eg05.getCity().compareTo(otherWeatherData_eg05.getCity());
                        }
                    });

                    elementsSelected = Tools.elementsMenu();
                    optionElements = Tools.getElementsFromString(elementsSelected);
                    count = 0;
                    stop = false;

                    System.out.println("\nSeleccione de la llista separant per una coma els elements a visualitzar.");

                    // Processa cada registre
                    for (int j = 0; j < listData.size(); j++) {
                        count++;
                        System.out.println("\n****  DADES DE " + listData.get(j).getCity()
                                + " AMD ID " + listData.get(j).getRecordId() + "  ****\n");
                        for (int l = 0; l < optionElements.size(); l++) {
                            String elements = selectElementsName(optionElements.get(l));
                            System.out.println(elements + selectElements(listData.get(j), optionElements.get(l)));
                        }

                        // Pregunta si vol continuar veient més registres o sortir
                        if (count == 2) {
                            System.out.println("\nPrem \"Intro\" per continuar, prem \"S\" per sortir.");
                            String next = lector.nextLine().toUpperCase();
                            if (next.equals("S")) {
                                stop = true;
                                break;
                            }
                            count = 0;
                        }
                    }
                    if (stop) {
                        break;
                    }
                }

                break;

            case 4:
                // Opció per tornar al menú anterior
                System.out.println("\nTornant al menú principal...");
                back = true;
                break;

            default:
                System.out.println("\nOpció no vàlida.");
                break;
        }
    }

    /**
     * Mostra el menú per eliminar elements per ciutat i gestiona les opcions
     * seleccionades.
     */
    private static void deleteMenuByElements() {
        Scanner lector = new Scanner(System.in);
        int option = 0;
        back = false;

        // Bucle que mostra el menú fins que l'usuari selecciona l'opció d'enrere
        while (!back) {
            System.out.println("\n*********************************************");
            System.out.println("***  MENÚ ELIMINAR ELEMENTS PER CIUTAT  ****");
            System.out.println("*********************************************\n");

            // Mostra la quantitat de registres segons la base de dades connectada
            if (isConnectedToMySQL && isConnect) {
                System.out.println("---- Connectat a MySql ----> Total de Registres: " + DataAccessFunctionsMySQL.listData().size() + " ----\n");
            } else if (isConnect) {
                System.out.println("---- Connectat a MongoDB ----> Total de Registres: " + DataAccessFunctionsMongo.listData().size() + " ----\n");
            }

            // Opcions del menú
            System.out.println("1.- Eliminar per ciutat.");
            System.out.println("2.- Eliminar per conjunt de ciutats.");
            System.out.println("3.- Eliminar totes les ciutats.");
            System.out.println("4.- Enrere");
            System.out.println("\nTria una opció: ");

            // Captura d'errors si l'usuari no introdueix un número enter
            try {
                option = Integer.parseInt(lector.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("\nS'espera que introduïsques un número enter. " + nfe.getMessage());
            }

            // Executa l'eliminació segons l'opció seleccionada
            deleteByElementsOption(option);
        }
    }

    /**
     * Executa l'eliminació d'elements en funció de l'opció seleccionada.
     *
     * @param option L'opció seleccionada pel usuari en el menú.
     */
    private static void deleteByElementsOption(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("La opció deu de ser major a zero.");
        }
        List<WeatherData_eg05> listData;

        // Selecciona la font de dades segons la base de dades connectada
        if (isConnectedToMySQL) {
            listData = DataAccessFunctionsMySQL.listData();
        } else {
            listData = DataAccessFunctionsMongo.listData();
        }

        // Executa l'acció segons l'opció seleccionada
        switch (option) {
            case 1:

                String city = Tools.requestCityName();
                List<Integer> recordsId = Tools.data(listData, city);

                // Confirmació per cada element abans d'eliminar-lo
                System.out.println("\nSeleccioneu de la llista separant per una coma els elements a eliminar.");
                String elementsSelected = Tools.elementsMenu();
                List<Integer> optionElements = Tools.getElementsFromString(elementsSelected);

                // Inverteix l'elecció per obtenir els elements a mantenir
                List<Integer> invertSelectionElements = Tools.invertSelection(optionElements);

                // Variables per a actualitzar els registres seleccionats
                Integer recordId;
                city = null;
                String country = null;
                Double latitude = null;
                Double longitude = null;
                Date date = null;
                Double temperatureCelsius = null;
                Integer humidityPercent = null;
                Double precipitationMm = null;
                Double windSpeedKmh = null;
                String weatherCondition = null;
                String forecast = null;
                Date updated = null;

                // Demana a l'usuari el vist i plau per a eliminar
                if (Tools.requestAlertDelete()) {
                    // Itera pels registres per eliminar-los
                    for (int i = 0; i < listData.size(); i++) {
                        if (recordsId.contains(listData.get(i).getRecordId())) {
                            // Actualitza el registre segons els elements seleccionats
                            for (int j = 0; j < invertSelectionElements.size(); j++) {
                                recordId = listData.get(i).getRecordId();
                                switch (invertSelectionElements.get(j)) {
                                    case 2:
                                        city = listData.get(i).getCity();
                                        break;
                                    case 3:
                                        country = listData.get(i).getCountry();
                                        break;
                                    case 4:
                                        latitude = listData.get(i).getLatitude();
                                        break;
                                    case 5:
                                        longitude = listData.get(i).getLongitude();
                                        break;
                                    case 6:
                                        date = listData.get(i).getDate();
                                        break;
                                    case 7:
                                        temperatureCelsius = listData.get(i).getTemperatureCelsius();
                                        break;
                                    case 8:
                                        humidityPercent = listData.get(i).getHumidityPercent();
                                        break;
                                    case 9:
                                        precipitationMm = listData.get(i).getPrecipitationMm();
                                        break;
                                    case 10:
                                        windSpeedKmh = listData.get(i).getWindSpeedKmh();
                                        break;
                                    case 11:
                                        weatherCondition = listData.get(i).getWeatherCondition();
                                        break;
                                    case 12:
                                        forecast = listData.get(i).getForecast();
                                        break;
                                    case 13:
                                        updated = listData.get(i).getUpdated();
                                        break;
                                }
                                // Actualitza les dades a la base de dades
                                if (isConnectedToMySQL) {
                                    DataAccessFunctionsMySQL.updateData(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                            humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                                } else {
                                    DataAccessFunctionsMongo.update(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                            humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("S'ha anul.lat l'operació.");
                }
                break;

            case 2:
                // Eliminar per conjunt de ciutats
                System.out.println("\nIntroduïu les ciutats de les quals desitgeu dades atmosfèriques.");
                String citiesName = Tools.requestCitiesName();
                List<String> citiesNameList = Tools.getCitiesFromString(citiesName);

                recordsId = Tools.recordsIdByCities(listData, citiesNameList);

                // Demana els elements a eliminar
                System.out.println("\nSeleccioneu de la llista separant per una coma els elements a visualitzar.");
                elementsSelected = Tools.elementsMenu();
                optionElements = Tools.getElementsFromString(elementsSelected);
                invertSelectionElements = Tools.invertSelection(optionElements);

                // Obtén la llista de ciutats
                List<String> cities = Tools.getCitiesFromString(citiesName);

                // Variables per a actualitzar els registres
                recordId = null;
                city = null;
                country = null;
                latitude = null;
                longitude = null;
                date = null;
                temperatureCelsius = null;
                humidityPercent = null;
                precipitationMm = null;
                windSpeedKmh = null;
                weatherCondition = null;
                forecast = null;
                updated = null;

                if (Tools.requestAlertDelete()) {
                    int count = 0;

                    for (int i = 0; i < cities.size(); i++) {
                        for (int j = 0; j < listData.size(); j++) {
                            if (recordsId.contains(listData.get(j).getRecordId())) {
                                count++;
                                // Actualitza els registres de cada ciutat
                                for (int k = 0; k < invertSelectionElements.size(); k++) {
                                    recordId = listData.get(j).getRecordId();
                                    switch (invertSelectionElements.get(k)) {
                                        case 2:
                                            city = listData.get(i).getCity();
                                            break;
                                        case 3:
                                            country = listData.get(i).getCountry();
                                            break;
                                        case 4:
                                            latitude = listData.get(i).getLatitude();
                                            break;
                                        case 5:
                                            longitude = listData.get(i).getLongitude();
                                            break;
                                        case 6:
                                            date = listData.get(i).getDate();
                                            break;
                                        case 7:
                                            temperatureCelsius = listData.get(i).getTemperatureCelsius();
                                            break;
                                        case 8:
                                            humidityPercent = listData.get(i).getHumidityPercent();
                                            break;
                                        case 9:
                                            precipitationMm = listData.get(i).getPrecipitationMm();
                                            break;
                                        case 10:
                                            windSpeedKmh = listData.get(i).getWindSpeedKmh();
                                            break;
                                        case 11:
                                            weatherCondition = listData.get(i).getWeatherCondition();
                                            break;
                                        case 12:
                                            forecast = listData.get(i).getForecast();
                                            break;
                                        case 13:
                                            updated = listData.get(i).getUpdated();
                                            break;
                                    }
                                }
                                // Actualitza a la base de dades
                                if (isConnectedToMySQL) {
                                    DataAccessFunctionsMySQL.updateData(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                            humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                                } else {
                                    DataAccessFunctionsMongo.update(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                            humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                                }
                            }
                        }
                    }
                    if (count == 0) {
                        System.out.println("La ciutat no es troba a la base de dades.");
                    }
                } else {
                    System.out.println("S'ha anul.lat l'operació.");
                }

                break;

            case 4:
                // Enrere, torna a la pantalla anterior
                back = true;
                break;
            case 3:

                if (Tools.requestAlertDelete()) {
                    city = Tools.requestCityName();
                    recordsId = Tools.data(listData, city);
                    int count = 0;
                    for (int i = 0; i < listData.size(); i++) {
                        if (recordsId.contains(listData.get(i).getRecordId())) {
                            count++;
                            recordId = listData.get(i).getRecordId();
                            city = null;
                            country = null;
                            latitude = null;
                            longitude = null;
                            date = null;
                            temperatureCelsius = null;
                            humidityPercent = null;
                            precipitationMm = null;
                            windSpeedKmh = null;
                            weatherCondition = null;
                            forecast = null;
                            updated = null;
                            if (isConnectedToMySQL) {
                                DataAccessFunctionsMySQL.updateData(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                        humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                            } else {
                                DataAccessFunctionsMongo.update(recordId, city, country, latitude, longitude, date, temperatureCelsius,
                                        humidityPercent, precipitationMm, windSpeedKmh, weatherCondition, forecast, updated);
                            }
                        }
                    }
                    if (count == 0) {
                        System.out.println("La ciutat no es troba a la base de dades.");
                    }
                }
                break;
            default:
                System.out.println("Selecció incorrecta. Proveu una altra vegada.");
        }
    }

    public static String selectElementsName(Integer elements) {
        if (elements < 0) {
            throw new IllegalArgumentException("La opció deu de ser major a zero.");
        }
        switch (elements) {
            case 1:
                return "ID del registre: ";
            case 2:
                return "Nom de la ciutat: ";
            case 3:
                return "País: ";
            case 4:
                return "Latitud: ";
            case 5:
                return "Longitud: ";
            case 6:
                return "Data: ";
            case 7:
                return "Temperatura (°C): ";
            case 8:
                return "Humedat (%): ";
            case 9:
                return "Precipitacions (mm): ";
            case 10:
                return "Velocitat del vent (Km/h): ";
            case 11:
                return "Condicions meteorològiques: ";
            case 12:
                return "Pronòstic: ";
            case 13:
                return "Data d'actualització: \n";
            default:
                throw new IllegalArgumentException("La opcion introducida no es válida. ");
        }
    }

    public static String selectElements(WeatherData_eg05 aWeatherData_eg05, Integer elements) {
        if (elements < 0) {
            throw new IllegalArgumentException("La opció deu de ser major a zero.");
        }
        if (aWeatherData_eg05 == null) {
            throw new IllegalArgumentException("La data atmosférica suministrada estaa buida.");
        }
        switch (elements) {
            case 1:
                return aWeatherData_eg05.getRecordId().toString();
            case 2:
                return aWeatherData_eg05.getCity();
            case 3:
                return aWeatherData_eg05.getCountry();
            case 4:
                return aWeatherData_eg05.getLatitude().toString();
            case 5:
                return aWeatherData_eg05.getLongitude().toString();
            case 6:
                return aWeatherData_eg05.getDate().toString();
            case 7:
                return aWeatherData_eg05.getTemperatureCelsius().toString();
            case 8:
                return aWeatherData_eg05.getHumidityPercent().toString();
            case 9:
                return aWeatherData_eg05.getPrecipitationMm().toString();
            case 10:
                return aWeatherData_eg05.getWindSpeedKmh().toString();
            case 11:
                return aWeatherData_eg05.getWeatherCondition();
            case 12:
                return aWeatherData_eg05.getForecast();
            case 13:
                return aWeatherData_eg05.getUpdated().toString();
            default:
                throw new IllegalArgumentException("La opció introduïda no es válida. ");
        }
    }

    public static void selectElements(WeatherData_eg05 aWeatherData_eg05, Integer elements, Integer recordId, String city,
            String country, Double latitude, Double longitude, Date date, Double temperatureCelsius, Integer humidityPercent,
            Double precipitationMm, Double windSpeedKmh, String weatherCondition, String forecast, Date updated) {
        switch (elements) {
            case 1:
                aWeatherData_eg05.setRecordId(elements);
                break;
            case 2:
                aWeatherData_eg05.setCity(city);
                break;
            case 3:
                aWeatherData_eg05.setCountry(country);
                break;
            case 4:
                aWeatherData_eg05.setLatitude(latitude);
                break;
            case 5:
                aWeatherData_eg05.setLongitude(longitude);
                break;
            case 6:
                aWeatherData_eg05.setDate(date);
                break;
            case 7:
                aWeatherData_eg05.setTemperatureCelsius(temperatureCelsius);
                break;
            case 8:
                aWeatherData_eg05.setHumidityPercent(humidityPercent);
                break;
            case 9:
                aWeatherData_eg05.setPrecipitationMm(precipitationMm);
                break;
            case 10:
                aWeatherData_eg05.setWindSpeedKmh(windSpeedKmh);
                break;
            case 11:
                aWeatherData_eg05.setWeatherCondition(weatherCondition);
                break;
            case 12:
                aWeatherData_eg05.setForecast(forecast);
                break;
            case 13:
                aWeatherData_eg05.setUpdated(updated);
                break;
            default:
                throw new IllegalArgumentException("La opció introduïda no es válida. ");
        }
    }
}

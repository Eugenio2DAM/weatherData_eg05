/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Classe que representa un usuari amb informació personal com el DNI, nom,
 * cognoms i ciutat.
 * Conté mètodes per obtenir i establir valors, amb validacions per garantir
 * que els valors són vàlids.
 * 
 * @author eugenio
 */
public class User {
    private Integer userId;
    private String dni;
    private String name;
    private String surname;
    private String city;

    /**
     * Constructor que inicialitza un usuari amb la seva informació personal.
     * 
     * @param userId L'ID de l'usuari.
     * @param dni El DNI de l'usuari.
     * @param name El nom de l'usuari.
     * @param surname Els cognoms de l'usuari.
     * @param city La ciutat de residència de l'usuari.
     */
    public User(Integer userId, String dni, String name, String surname, String city) {
        this.setUserId(userId);
        this.setDni(dni);
        this.setName(name);
        this.setSurname(surname);
        this.setCity(city);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    /**
     * Estableix l'ID de l'usuari.
     * Si l'ID és nul o negatiu, llença una excepció.
     * 
     * @param userId L'ID de l'usuari.
     * @throws IllegalArgumentException Si l'ID és nul o negatiu.
     */
    public void setUserId(Integer userId) {
        if(userId == null || userId < 0){
            throw new IllegalArgumentException("L'ID de l'usuari no pot ser nul ni menor que zero. ");
        }
        this.userId = userId;
    }

    /**
     * Estableix el DNI de l'usuari.
     * Si el DNI és nul o buit, llença una excepció.
     * 
     * @param dni El DNI de l'usuari.
     * @throws IllegalArgumentException Si el DNI és nul o buit.
     */
    public void setDni(String dni) {
        if(dni == null || dni.isEmpty()){
            throw new IllegalArgumentException("El DNI no pot estar buit.");
        }
        this.dni = dni;
    }

    /**
     * Estableix el nom de l'usuari.
     * Si el nom és nul o buit, llença una excepció.
     * 
     * @param name El nom de l'usuari.
     * @throws IllegalArgumentException Si el nom és nul o buit.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("El nom no pot estar buit.");
        }
        this.name = name;
    }

    /**
     * Estableix els cognoms de l'usuari.
     * Si els cognoms són nul·les o buits, llença una excepció.
     * 
     * @param surname Els cognoms de l'usuari.
     * @throws IllegalArgumentException Si els cognoms són nuls o buits.
     */
    public void setSurname(String surname) {
        if(surname == null || surname.isEmpty()){
            throw new IllegalArgumentException("Els cognoms no poden estar buits.");
        }
        this.surname = surname;
    }

    /**
     * Estableix la ciutat de l'usuari.
     * Si la ciutat és nul·la o buida, llença una excepció.
     * 
     * @param city La ciutat de l'usuari.
     * @throws IllegalArgumentException Si la ciutat és nul·la o buida.
     */
    public void setCity(String city) {
        if(city == null || city.isEmpty()){
            throw new IllegalArgumentException("El nom de la ciutat no pot estar buit.");
        }
        this.city = city;
    }
}

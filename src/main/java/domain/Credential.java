
package domain;

/**
 * Classe que representa les credencials d'un usuari.
 * Conté informació sobre l'ID de les credencials, l'ID de l'usuari i la contrasenya.
 * Realitza validacions per assegurar que els valors no siguin invàlids.
 * 
 * @author eugenio
 */
public class Credential {
    private Integer credentialId;
    private Integer userId;
    private String password;

    /**
     * Constructor que inicialitza els valors de les credencials.
     * 
     * @param credentialId L'ID de les credencials.
     * @param userId L'ID de l'usuari al qual pertanyen les credencials.
     * @param password La contrasenya associada a les credencials.
     */
    public Credential(Integer credentialId, Integer userId, String password) {
        this.credentialId = credentialId;
        this.userId = userId;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Estableix l'ID de les credencials.
     * Si l'ID és negatiu, llença una excepció.
     * 
     * @param credentialId El nou ID de les credencials.
     * @throws IllegalArgumentException Si l'ID és negatiu.
     */
    public void setCredentialId(Integer credentialId) {
        if(credentialId < 0){
            throw new IllegalArgumentException("L'ID de les credencials no pot ser menor que zero. ");
        }
        this.credentialId = credentialId;
    }

    /**
     * Estableix l'ID de l'usuari al qual pertanyen les credencials.
     * Si l'ID és negatiu o nul, llença una excepció.
     * 
     * @param userId El nou ID de l'usuari.
     * @throws IllegalArgumentException Si l'ID és negatiu o nul.
     */
    public void setUserId(Integer userId) {
        if(userId == null){
            throw new IllegalArgumentException("L'ID d'un usuari és obligatori. ");
        }
        if(userId < 0){
            throw new IllegalArgumentException("L'ID de l'usuari no pot ser menor que zero. ");
        }
        this.userId = userId;
    }

    /**
     * Estableix la contrasenya de les credencials.
     * Si la contrasenya està buida o té una longitud menor a 1, llença una excepció.
     * 
     * @param password La nova contrasenya.
     * @throws IllegalArgumentException Si la contrasenya és buida o massa curta.
     */
    public void setPassword(String password) {
        if(password == null || password.isEmpty() || password.length() < 1){
            throw new IllegalArgumentException("Cal escriure la contrasenya per iniciar sessió. ");
        }
        this.password = password;
    }  
}

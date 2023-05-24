package xss.it.conf;

import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 * JConfig is a utility class that provides functionality for managing application settings using Properties.
 * It supports saving and loading settings to/from a file, as well as encryption and decryption of sensitive data.
 */
public final class JConfig {
    private final Properties properties;
    private final String filePath;
    private final String secretKey;

    /**
     * Constructor for the JConfig class.
     *
     * @param filePath   the path to store or load the settings
     * @param secretKey  the secret key used for encryption (can be null or empty)
     */
    public JConfig(String filePath, @Nullable String secretKey) {
        this.properties = new Properties();
        this.filePath = filePath;
        this.secretKey = (secretKey==null || secretKey.isEmpty() || secretKey.isBlank()) ? null : padSecretKey(secretKey);

        /*
         * Load the file if exist
         */
        if (exist()){
            loadFromFile();
        }
    }

    /**
     * Get the properties object.
     *
     * @return the properties object containing the settings
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Get the file path where the settings are stored or loaded.
     *
     * @return the file path as a string
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Get the secret key used for encryption.
     *
     * @return the secret key as a string
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Copy properties from an InputStream representing a resource file into the current Properties object.
     *
     * @param stream the InputStream representing the resource file to copy from
     * @param refresh flag indicating whether to refresh the existing properties or only add missing ones
     * @throws IOException if an I/O error occurs while reading the resource file or saving the properties to file
     */
    public void copyFromResource(InputStream stream, boolean refresh) throws IOException {
        Properties others = readFromResources(stream);
        if (refresh){
            properties.clear();
            properties.putAll(others);
        }else {
            others.forEach((k, v) -> {
                if (!properties.containsKey(k)) {
                    properties.put(k, v);
                }
            });
        }
        saveToFile();
    }

    /**
     * Read properties from an InputStream representing a resource file.
     *
     * @param stream the InputStream representing the resource file to read from
     * @return a Properties object containing the properties read from the resource file
     * @throws IOException if an I/O error occurs while reading the resource file
     */
    public Properties readFromResources(InputStream stream) throws IOException {
        Properties copy = new Properties();
        copy.load(stream);
        return copy;
    }

    /**
     * Set or update the value of a property.
     *
     * @param key   the key of the property
     * @param value the value to be set
     */
    public void set(String key, String value){
        properties.setProperty(key, value);
        saveToFile();
    }

    /**
     * Set or update the value of a property with encryption.
     *
     * @param key   the key of the property
     * @param value the value to be set
     * @throws NullSecretKeyException if the secret key is null
     */
    public void setEncrypted(String key, String value) {
        if (secretKey==null){
            throw new NullSecretKeyException();
        }
        String encryptedKey = encrypt(key);
        String encryptedValue = encrypt(value);
        set(encryptedKey, encryptedValue);
    }

    /**
     * Set or update the value of a property as an integer.
     *
     * @param key   the key of the property
     * @param value the integer value to be set
     */
    public void setInteger(String key, int value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update the value of a property as a double.
     *
     * @param key   the key of the property
     * @param value the double value to be set
     */
    public void setDouble(String key, double value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update the value of a property as a long.
     *
     * @param key   the key of the property
     * @param value the long value to be set
     */
    public void setLong(String key, long value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update the value of a key as a float.
     *
     * @param key   the key of the property
     * @param value the value to be set as a float
     */
    public void setFloat(String key, float value){
        set(key, String.valueOf(value));
    }

    /**
     * Set or update the value of a property as a boolean.
     *
     * @param key   the key of the property
     * @param value the boolean value to be set
     */
    public void setBoolean(String key, boolean value) {
        set(key, String.valueOf(value));
    }

    /**
     * Get the value of a property as a string.
     *
     * @param key the key of the property
     * @return the value of the property as a string, or null if the property does not exist
     */
    public String get(String key){
        return properties.getProperty(key);
    }

    /**
     * Get the decrypted value of a property.
     *
     * @param key the key of the property
     * @return the decrypted value of the property as a string, or null if the property does not exist or secret key is null
     * @throws NullSecretKeyException if the secret key is null
     */
    public String getDecrypted(String key) {
        if (secretKey==null){
            throw new NullSecretKeyException();
        }
        String encryptedKey = encrypt(key);
        String encryptedValue = get(encryptedKey);
        if (encryptedValue != null) {
            return decrypt(encryptedValue);
        }
        return null;
    }

    /**
     * Get the value of a property as an integer.
     *
     * @param key the key of the property
     * @return the value of the property as an integer, or -1 if the property does not exist or cannot be parsed as an integer
     */
    public int getInteger(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Integer.parseInt(value);
            }catch (Exception ignored){}
        }
        return -1;
    }

    /**
     * Get the value of a property as a double.
     *
     * @param key the key of the property
     * @return the value of the property as a double, or -1.0 if the property does not exist or cannot be parsed as a double
     */
    public double getDouble(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Double.parseDouble(value);
            }catch (Exception ignored){}
        }
        return -1d;
    }

    /**
     * Get the value of a property as a long.
     *
     * @param key the key of the property
     * @return the value of the property as a long, or -1L if the property does not exist or cannot be parsed as a long
     */
    public long getLong(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Long.parseLong(value);
            }catch (Exception ignored){}
        }
        return -1L;
    }


    /**
     * Get the value of a property as a float.
     *
     * @param key the key of the property
     * @return the value of the property as a float, or -1.0 if the property does not exist or cannot be parsed as a float
     */
    public float getFloat(String key){
        String value = get(key);
        if (value != null) {
            try{
                return Float.parseFloat(value);
            }catch (Exception ignored){}
        }
        return -1F;
    }

    /**
     * Checks if a value is set for the specified key.
     *
     * @param key The key to check.
     * @return {@code true} if a value is set for the key, {@code false} otherwise.
     */
    public boolean isSet(String key){
        String v1=get(key);
        String v2=getDecrypted(key);
        return (v1!=null && !v1.isBlank() && !v1.isEmpty()) || (v2!=null && !v2.isBlank() && !v2.isEmpty());
    }

    /**
     * Get the value of a property as a boolean.
     *
     * @param key the key of the property
     * @return the value of the property as a boolean, or false if the property does not exist or cannot be parsed as a boolean
     */
    public Boolean getBoolean(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Boolean.parseBoolean(value);
            }catch (Exception ignored){}
        }
        return false;
    }

    /**
     * Saves the properties to a file.
     * The properties are stored in the specified file path with a comment.
     * If an IOException occurs during the file saving process, the exception is printed.
     */
    void saveToFile() {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, "Application Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the properties from a file.
     * The properties are read from the specified file path.
     * If an IOException occurs during the file loading process, the exception is printed.
     */
    void loadFromFile() {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypts the given value using AES encryption with a secret key.
     * The value is converted to bytes, encrypted using AES/ECB/PKCS5Padding algorithm,
     * and then encoded in Base64 for secure representation.
     * If an exception occurs during the encryption process, the exception is printed and null is returned.
     *
     * @param value The value to be encrypted
     * @return The encrypted value in Base64 format, or null if encryption fails
     */
    private String encrypt(String value) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypts the given encrypted value using AES decryption with a secret key.
     * The encrypted value is expected to be in Base64 format.
     * The decrypted value is obtained by decoding the Base64 string,
     * applying AES/ECB/PKCS5Padding decryption algorithm, and converting the decrypted bytes to a string.
     * If an exception occurs during the decryption process, the exception message is printed,
     * and null is returned.
     *
     * @param encryptedValue The encrypted value in Base64 format
     * @return The decrypted value as a string, or null if decryption fails
     */
    private String decrypt(String encryptedValue) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println("Error decrypting value: " + e.getMessage());
            return null;
        }
    }

    /**
     * Pads the secret key with "X" characters to ensure it has a length of 16 bytes.
     * If the secret key length is less than 16, the method appends "X" characters
     * to the key until it reaches a length of 16.
     * If the secret key length is already 16 or greater, the method returns the first 16 characters of the key.
     *
     * @param secretKey The secret key to pad
     * @return The padded secret key with a length of 16 bytes
     */
    private String padSecretKey(String secretKey) {
        int keyLength = secretKey.length();
        if (keyLength < 16) {
            int paddingLength = 16 - keyLength;
            return secretKey + "X".repeat(paddingLength);
        }
        return secretKey.substring(0, 16);
    }


    /**
     * Checks if the settings file exists at the specified file path.
     *
     * @return {@code true} if the settings file exists, {@code false} otherwise
     */
    boolean exist(){
        return Files.exists(Paths.get(filePath));
    }
}

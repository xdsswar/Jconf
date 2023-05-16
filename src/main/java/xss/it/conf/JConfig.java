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
 * Utility class to manage Application settings. *
 * Be aware that trying to get and encrypted value with the get method or trying to get a non-encrypted value
 * with the getDecrypted method will return null or may give an Exception, be suer to use the appropiate method.
 * NOTE:
 * ONLY Strings DATA TYPES ARE RECOMMENDED TO BE ENCRYPTED IF SENSITIVE INFORMATION, DO NOT USE THE
 * ENCRYPTION/DECRYPTION METHODS WITH OTHER DATA TYPES LIKE Numbers AND Booleans.
 */
public final class JConfig {
    private final Properties properties;
    private final String filePath;
    private final String secretKey;

    /**
     * Constructor
     * @param filePath String path to store or load the settings
     * @param secretKey String secret key
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
     * Get the Properties
     * @return String
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Get the properties location
     * @return String
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Get the secret key
     * @return String
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Create properties from InputStream
     * @param stream InputStream
     * @param refresh boolean refresh, if true the properties will be cleared and restored from the stream, if false
     * just the missing keys will be added with their respective values
     * @throws IOException err
     */
    public void fromResource(InputStream stream, boolean refresh) throws IOException {
        Properties others = new Properties();
        others.load(stream);
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
     * Set or update key to String value
     * @param key String key
     * @param value String value
     */
    public void set(String key, String value){
        properties.setProperty(key, value);
        saveToFile();
    }

    /**
     * Set and Encrypt the key and value
     * @param key Sting  key
     * @param value String value
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
     * Set or update key to int value
     * @param key String key
     * @param value int value
     */
    public void setInteger(String key, int value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update key to double value
     * @param key String key
     * @param value String value
     */
    public void setDouble(String key, double value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update key to long value
     * @param key String key
     * @param value String value
     */
    public void setLong(String key, long value) {
        set(key, String.valueOf(value));
    }

    /**
     * Set or update key to boolean value
     * @param key String key
     * @param value String value
     */
    public void setBoolean(String key, boolean value) {
        set(key, String.valueOf(value));
    }

    /**
     * Get String value
     * @param key String key
     * @return String value
     */
    public String get(String key){
        return properties.getProperty(key);
    }

    /**
     * Get and decrypt a String value
     * @param key String key
     * @return String value
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
     * Get Integer value
     * @param key Key
     * @return Integer
     */
    public int getInteger(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Integer.parseInt(value);
            }catch (Exception ignored){}
        }
        return 0;
    }

    /**
     * Get Double value
     * @param key Key
     * @return Double
     */
    public double getDouble(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Double.parseDouble(value);
            }catch (Exception ignored){}
        }
        return 0d;
    }

    /**
     * Get Long value
     * @param key Key
     * @return Long
     */
    public long getLong(String key) {
        String value = get(key);
        if (value != null) {
            try{
                return Long.parseLong(value);
            }catch (Exception ignored){}
        }
        return 0L;
    }

    /**
     * Get Boolean value
     * @param key Key
     * @return Boolean
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
     * Save settings to file
     */
    void saveToFile() {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, "Application Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Settings from file
     */
    void loadFromFile() {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt the given value, then perform base64
     * @param value String
     * @return String encrypted
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
     * Base64 decode the given value , then decrypt
     * @param encryptedValue String hash
     * @return String
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
     * Fix the key for 16 bits
     * @param secretKey String
     * @return String
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
     * Check if the settings file exist
     * @return boolean
     */
    boolean exist(){
        return Files.exists(Paths.get(filePath));
    }
}

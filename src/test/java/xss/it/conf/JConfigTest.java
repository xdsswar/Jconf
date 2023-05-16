package xss.it.conf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public class JConfigTest {
    private static final String TEST_FILE_PATH = "test.properties";
    private static final String SECRET_KEY = "MySecretKey";
    private JConfig jConfig;

    @BeforeEach
    public void setup() throws IOException {
        // Create a new JConfig instance for each test
        jConfig = new JConfig(TEST_FILE_PATH, SECRET_KEY);
    }

    @Test
    public void testSetAndGet() {
        // Set a property
        jConfig.set("name", "John Doe");

        // Get the property value
        String name = jConfig.get("name");

        // Verify the value is correct
        assertEquals("John Doe", name);
    }

    @Test
    public void testSetEncryptedAndGetDecrypted() {
        // Set an encrypted property
        jConfig.setEncrypted("password", "s3cr3t");

        // Get the decrypted property value
        String decryptedPassword = jConfig.getDecrypted("password");

        // Verify the decrypted value is correct
        assertEquals("s3cr3t", decryptedPassword);
    }

    @Test
    public void testSetAndGetInteger() {
        // Set an integer property
        jConfig.setInteger("count", 10);

        // Get the integer property value
        int count = jConfig.getInteger("count");

        // Verify the value is correct
        assertEquals(10, count);
    }

    @Test
    public void testSetAndGetDouble() {
        // Set a double property
        jConfig.setDouble("pi", 3.14159);

        // Get the double property value
        double pi = jConfig.getDouble("pi");

        // Verify the value is correct
        assertEquals(3.14159, pi, 0.00001);
    }

    @Test
    public void testSetAndGetLong() {
        // Set a long property
        jConfig.setLong("timestamp", 1625698800000L);

        // Get the long property value
        long timestamp = jConfig.getLong("timestamp");

        // Verify the value is correct
        assertEquals(1625698800000L, timestamp);
    }

    @Test
    public void testSetAndGetFloat() {
        // Set a float value
        jConfig.setFloat("floatKey", 3.14f);

        // Retrieve the value and check if it matches the expected value
        float actualValue = jConfig.getFloat("floatKey");
        float expectedValue = 3.14f;
        assertEquals(expectedValue, actualValue);

        // Set a string value
        jConfig.set("floatKey", "2.718");

        // Retrieve the value as float and check if it matches the expected value
        actualValue = jConfig.getFloat("floatKey");
        expectedValue = 2.718f;
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testSetAndGetBoolean() {
        // Set a boolean property
        jConfig.setBoolean("active", true);

        // Get the boolean property value
        boolean active = jConfig.getBoolean("active");

        // Verify the value is correct
        assertTrue(active);
    }

    @Test
    public void testSaveAndLoadFromFile() throws IOException {
        // Set properties
        jConfig.set("name", "John Doe");
        jConfig.set("age", "30");
        jConfig.set("country", "USA");

        // Save properties to file
        jConfig.saveToFile();

        // Create a new JConfig instance for loading from file
        JConfig loadedConfig = new JConfig(TEST_FILE_PATH, SECRET_KEY);

        // Load properties from file
        loadedConfig.loadFromFile();

        // Verify the loaded properties
        assertEquals("John Doe", loadedConfig.get("name"));
        assertEquals("30", loadedConfig.get("age"));
        assertEquals("USA", loadedConfig.get("country"));
    }


    @Test
    public void testExist() throws IOException {
        // Create a temporary file
        Path tempFilePath = Files.createTempFile(null, null);

        // Create a new JConfig instance with the temporary file path
        JConfig tempJConfig = new JConfig(tempFilePath.toString(), SECRET_KEY);

        // Check if the file exists
        assertTrue(tempJConfig.exist());

        // Delete the temporary file
        Files.deleteIfExists(tempFilePath);

        // Check if the file does not exist
        assertFalse(tempJConfig.exist());
    }
}

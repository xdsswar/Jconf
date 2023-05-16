## JConf

JConfig is a utility class that provides functionality for managing application settings using Properties.

### Features
* It supports saving and loading settings to/from a file.
* Encryption and decryption of sensitive data for specific keys-values (Only for String values).
* Data types as ```Strings, long, float, double, int , boolean``` as key values.
* Read and copy ```.properties```  files from resources and any location.

## Utilization

* ### Copy settings from ```.properties``` file in resources.

```java
public class Main {
    public static void main(String[] args) {
        // Specify the file path for the configuration file
        String filePath = "config.properties";

        // Create an instance of JConfig with the specified file path
        //Specify key if there is encrypted data in the properties file
        JConfig config = new JConfig(filePath,null);

        try {
            // Load properties from a resource file
            InputStream inputStream = Main.class.getResourceAsStream("/config2.properties");
            config.copyFromResource(inputStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get properties
        String appName = config.get("app.name");
        String appVersion = config.get("app.version");
        String appAuthor = config.get("app.author");
        int timeout = config.getInteger("app.timeout");
        boolean debugMode = config.getBoolean("app.debug");

        // Print the retrieved properties
        System.out.println("App Name: " + appName);
        System.out.println("App Version: " + appVersion);
        System.out.println("App Author: " + appAuthor);
        System.out.println("Timeout: " + timeout);
        System.out.println("Debug Mode: " + debugMode);

    }
}
```

* ### Example of the mysql connection settings using password encryption.

```java
public class Main {
    public static void main(String[] args) {
        // Specify the file path for the configuration file
        String filePath = "config.properties";

        // Encryption key for password
        String encryptionKey = "MyKey";

        // Create an instance of JConfig with the specified file path and encryption key
        JConfig config = new JConfig(filePath, encryptionKey);

        // Set properties
        config.set("database.url", "jdbc:mysql://localhost:3306/mydb");
        config.set("database.username", "admin");
        config.setEncrypted("database.password", "password");
        config.setInteger("app.timeout", 30);
        config.setBoolean("app.debug", true);

        // Get properties
        String dbUrl = config.get("database.url");
        String dbUsername = config.get("database.username");
        String dbPassword = config.getDecrypted("database.password");
        int timeout = config.getInteger("app.timeout");
        boolean debugMode = config.getBoolean("app.debug");

        // Print the retrieved properties
        System.out.println("Database URL: " + dbUrl);
        System.out.println("Database Username: " + dbUsername);
        System.out.println("Database Password: " + dbPassword);
        System.out.println("Timeout: " + timeout);
        System.out.println("Debug Mode: " + debugMode);
    }
}  
```

* ### Example of application settings without encryption.

```java
public class Main {
    public static void main(String[] args) {
        // Specify the file path for the configuration file
        String filePath = "config2.properties";

        // Create an instance of JConfig with the specified file path
        JConfig config = new JConfig(filePath,null);

        // Set properties
        config.set("app.name", "MyApp");
        config.set("app.version", "1.0.0");
        config.set("app.author", "John Doe");
        config.setInteger("app.timeout", 30);
        config.setBoolean("app.debug", true);

        // Get properties
        String appName = config.get("app.name");
        String appVersion = config.get("app.version");
        String appAuthor = config.get("app.author");
        int timeout = config.getInteger("app.timeout");
        boolean debugMode = config.getBoolean("app.debug");

        // Print the retrieved properties
        System.out.println("App Name: " + appName);
        System.out.println("App Version: " + appVersion);
        System.out.println("App Author: " + appAuthor);
        System.out.println("Timeout: " + timeout);
        System.out.println("Debug Mode: " + debugMode);
    }
}
```

If there is any issue or recommendation, please create issue or pull request. Enjoy

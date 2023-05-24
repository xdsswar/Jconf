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

* ### JavaFX Settings example

```java
public class Main extends Application {
    //Declare the Jconfig
    public static final JConfig JCONFIG;

    //Initialize the Jconfig 
    static {
        String folder=".app-folder-name-at-uer-dir";
        String CONFIG_PATH=String.format(
                "%s%s%s%s",
                JPlatform.getUserDir(),
                folder,
                JPlatform.getDirSeparator(),
                "settings-name.properties"
        );

        //Create the folder to store the settings
        JPlatform.createDirs(JPlatform.getUserDir(),folder);
        //Initialize, pass a key if you want encryption for some String values  
        JCONFIG=new JConfig(CONFIG_PATH,null);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Test by printing some setting values
        System.out.println(JCONFIG.get("app.main.dir"));
        System.out.println(JCONFIG.getInteger("int.key"));
        System.out.println(JCONFIG.getDouble("double.key"));
        System.out.println(JCONFIG.getLong("long.key"));
        System.out.println(JCONFIG.getFloat("float.key"));
        System.out.println(JCONFIG.getBoolean("bool.key"));
        
        //If encrypted values , use this method. ONLY FOR Strings
        System.out.println(JCONFIG.getDecrypted("encrypted.key")); //For encrypted value
        
        //Show Stage,etc
    }


    @Override
    public void init() throws Exception {
        super.init();
        initializeSettings();
    }

    /**
     * Initialize all
     */
    public static synchronized void initializeSettings() throws IOException {

        final String dataFolder="Folder name"; //Root Folder to save Application data like db, reports, etc
        final String reportsFolder="Reports"; //Reports subfolder
        final String dbFolder="Local Db"; // Local Db subfolder

        //Declare and create the root Folder
        final String dataFolderPath= JPlatform.getDocumentsDir()+dataFolder+JPlatform.getDirSeparator();
        JPlatform.createDirs(JPlatform.getDocumentsDir(),dataFolder);

        //Declare and create the sub-dirs
        final String reportsDir=dataFolderPath+reportsFolder+JPlatform.getDirSeparator();
        final String dbDir=dataFolderPath+dbFolder+JPlatform.getDirSeparator();
        JPlatform.createDirs(dataFolderPath,reportsFolder,dbFolder);

        //Add all those folders to the settings if they are not already set
        if (!JCONFIG.isSet("app.main.dir")) {
            JCONFIG.set("app.main.dir", dataFolderPath);
        }
        if (!JCONFIG.isSet("app.reports.dir")) {
            JCONFIG.set("app.reports.dir", reportsDir);
        }
        if (!JCONFIG.isSet("app.local.db.dir")) {
            JCONFIG.set("app.local.db.dir", dbDir);
        }

        /*
         * Copy the rest of the settings from the default properties. Since we already have props at this point, we just copy
         * the key-values that we don't have, by passing false to the refresh param
         */
        JCONFIG.copyFromResource(loadStream("/default.properties"),false);
    }

     /**
     * Loads the properties input stream from the specified location inside the resources folder
     */
    public static InputStream loadStream(String location){
        return Main.class.getResourceAsStream(location);
    }

}

```

If there is any issue or recommendation, please create issue or pull request. Enjoy

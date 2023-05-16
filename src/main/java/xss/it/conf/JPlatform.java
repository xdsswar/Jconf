package xss.it.conf;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public final class JPlatform {
    private static final String _OS = System.getProperty("os.name").toLowerCase();
    private static final String _ARCH = System.getProperty("os.arch");
    private static final String _DIR_SEPARATOR = System.getProperty("file.separator");
    private static final String _USER_DIR = System.getProperty("user.home");

    /**
     * Check if Windows
     * @return boolean
     */
    public static boolean isWindows() {
        return _OS.toLowerCase().contains("windows");
    }


    /**
     * Check if Mac OSX
     * @return boolean
     */
    public static boolean isMac() {
        return _OS.toLowerCase().contains("mac");
    }
    /**
     * Check if Linux
     * @return boolean
     */
    public static boolean isLinux() {
        return _OS.toLowerCase().contains("linux");
    }


    /**
     * Check if teh system is 32 bits
     * @return boolean
     */
    public static boolean is32Bit() {
        return _ARCH.contains("86") || _ARCH.contains("32");
    }

    /**
     * Check if System is 64 bits
     * @return boolean
     */
    public static boolean is64Bit() {
        return _ARCH.contains("64");
    }

    /**
     * Get the current OS Documents path
     * @return String
     */
    public static String getDocumentsDir(){
        return FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+_DIR_SEPARATOR;
    }

    /**
     * Create multiple folders inside a specific directory
     * @param baseDirPath String base directory path
     * @param folders String[] array of folder names to create
     * @return boolean true if folders are created successfully, false otherwise
     */
    public static boolean createDirs(String baseDirPath, String... folders) {
        File baseDir = new File(baseDirPath);

        // Create the base directory if it doesn't exist
        if (!baseDir.exists()) {
            if (!baseDir.mkdirs()) {
                return false;
            }
        }

        // Create the sub-folders inside the base directory
        for (String folderName : folders) {
            File folder = new File(baseDir, folderName);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the OS dir separator
     * @return String
     */
    public static String getDirSeparator(){
        return _DIR_SEPARATOR;
    }

    /**
     * Get the current user dir path
     * @return String
     */
    public static String getUserDir(){
        return _USER_DIR+_DIR_SEPARATOR;
    }


    /**
     * Get current OS
     * @return OS
     */
    public static OS getOS(){
        if (isWindows()) {
            return OS.WINDOWS;
        }
        else if (isMac()) {
            return OS.OSX;
        }
        else if (isLinux()) {
            return OS.LINUX;
        }
        {
            return OS.UNKNOWN;
        }
    }

    /**
     * Enum OS
     */
    public enum OS{
        WINDOWS,
        OSX,
        LINUX,
        UNKNOWN
    }

}

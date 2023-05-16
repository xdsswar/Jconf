package xss.it.conf;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 * Utility class for platform-specific operations and information.
 */
public final class JPlatform {
    private static final String _OS = System.getProperty("os.name").toLowerCase();
    private static final String _ARCH = System.getProperty("os.arch");
    private static final String _DIR_SEPARATOR = System.getProperty("file.separator");
    private static final String _USER_DIR = System.getProperty("user.home");

    /**
     * Checks if the current operating system is Windows.
     *
     * @return {@code true} if the current operating system is Windows, {@code false} otherwise.
     */
    public static boolean isWindows() {
        return _OS.toLowerCase().contains("windows");
    }


    /**
     * Checks if the current operating system is Mac.
     *
     * @return {@code true} if the current operating system is Mac, {@code false} otherwise.
     */
    public static boolean isMac() {
        return _OS.toLowerCase().contains("mac");
    }


    /**
     * Checks if the current operating system is Linux.
     *
     * @return {@code true} if the current operating system is Linux, {@code false} otherwise.
     */
    public static boolean isLinux() {
        return _OS.toLowerCase().contains("linux");
    }


    /**
     * Checks if the current operating system is 32-bit.
     *
     * @return {@code true} if the current operating system is 32-bit, {@code false} otherwise.
     */
    public static boolean is32Bit() {
        return _ARCH.contains("86") || _ARCH.contains("32");
    }


    /**
     * Checks if the current operating system is 64-bit.
     *
     * @return {@code true} if the current operating system is 64-bit, {@code false} otherwise.
     */
    public static boolean is64Bit() {
        return _ARCH.contains("64");
    }


    /**
     * Retrieves the path of the Documents directory for the current user.
     *
     * @return The path of the Documents directory.
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
     * Retrieves the directory separator for the current operating system.
     *
     * @return The directory separator string.
     */
    public static String getDirSeparator(){
        return _DIR_SEPARATOR;
    }

    /**
     * Retrieves the path of the user's home directory.
     *
     * @return The path of the user's home directory.
     */
    public static String getUserDir(){
        return _USER_DIR+_DIR_SEPARATOR;
    }


    /**
     * Retrieves the current operating system.
     *
     * @return The current operating system.
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
     * Represents different operating systems.
     */
    public enum OS{
        WINDOWS,
        OSX,
        LINUX,
        UNKNOWN
    }

}

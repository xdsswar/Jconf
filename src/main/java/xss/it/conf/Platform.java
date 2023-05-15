package xss.it.conf;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public final class Platform {
    private static final String _OS = System.getProperty("os.name").toLowerCase();

    /**
     * Check if Windows
     * @return boolean
     */
    public static boolean isWindows() {
        return _OS.contains("win");
    }

    /**
     * Check if Mac OSX
     * @return boolean
     */
    public static boolean isMac() {
        return _OS.contains("mac");
    }

    /**
     * Check if Unix
     * @return boolean
     */
    public static boolean isUnix() {
        return (_OS.contains("nix") || _OS.contains("nux") || _OS.contains("aix"));
    }

    /**
     * Check if Solaris
     * @return boolean
     */
    public static boolean isSolaris() {
        return _OS.contains("sunos");
    }

    /**
     * Get current OS
     * @return OS
     */
    public static OS getOS(){
        if (isWindows()) {
            return OS.WINDOWS;
        } else if (isMac()) {
            return OS.OSX;
        } else if (isUnix()) {
            return OS.UNIX;
        } else if (isSolaris()) {
            return OS.SOLARIS;
        } else {
            return OS.UNKNOWN;
        }
    }

    /**
     * Enum OS
     */
    public enum OS{
        WINDOWS,
        OSX,
        UNIX,
        SOLARIS,
        UNKNOWN
    }
}

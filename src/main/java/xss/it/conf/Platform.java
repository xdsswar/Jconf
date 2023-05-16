package xss.it.conf;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public final class Platform {
    private static final String _OS = System.getProperty("os.name").toLowerCase();
    private static final String _ARCH = System.getProperty("os.arch");

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
     * Check if unix
     * @return boolean
     */
    public static boolean isUnix() {
        return _OS.toLowerCase().contains("nix") || _OS.toLowerCase().contains("nux");
    }


    /**
     * Check if Solaris
     * @return boolean
     */
    public static boolean isSolaris() {
        return _OS.toLowerCase().contains("sunos") || _OS.toLowerCase().contains("solaris");
    }

    /**
     * Check if it's freeBSD
     * @return boolean
     */
    public static boolean isFreeBSD() {
        return _OS.toLowerCase().contains("freebsd");
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
        else if (isSolaris()) {
            return OS.SOLARIS;
        }
        else if (isUnix()){
            return OS.UNIX;
        }
        else if (isFreeBSD()){
            return OS.FREE_BSD;
        }
        else
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
        SOLARIS,
        UNIX,
        FREE_BSD,
        UNKNOWN
    }



}

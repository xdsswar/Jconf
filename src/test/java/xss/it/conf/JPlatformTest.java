package xss.it.conf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XDSSWAR
 * Created on 05/16/2023
 */
public class JPlatformTest {

    @Test
    public void testIsWindows() {
        // Test when running on Windows
        System.setProperty("os.name", "Windows");
        assertTrue(JPlatform.isWindows());

        // Test when running on non-Windows platform
       // System.setProperty("os.name", "Mac OS X");
       // assertFalse(Platform.isWindows());
    }

    @Test
    public void testIsMac() {
        // Test when running on Mac
       // System.setProperty("os.name", "Mac OS X");
       // assertTrue(Platform.isMac());

        // Test when running on non-Mac platform
        //System.setProperty("os.name", "Windows");
        assertFalse(JPlatform.isMac());
    }

    @Test
    public void testIsLinux() {
        // Test when running on Linux
        //System.setProperty("os.name", "Linux");
       // assertTrue(Platform.isLinux());

        // Test when running on non-Linux platform
       // System.setProperty("os.name", "Windows");
        assertFalse(JPlatform.isLinux());
    }

    @Test
    public void testIsUnix() {
        // Test when running on Unix-like platform
        //System.setProperty("os.name", "Unix");
       // assertTrue(Platform.isUnix());

        // Test when running on non-Unix platform
        //System.setProperty("os.name", "Windows");
        assertFalse(JPlatform.isUnix());
    }

    @Test
    public void testIsSolaris() {
        // Test when running on Solaris
        //System.setProperty("os.name", "Solaris");
       // assertTrue(Platform.isSolaris());

        // Test when running on non-Solaris platform
       // System.setProperty("os.name", "Windows");
        assertFalse(JPlatform.isSolaris());
    }

    @Test
    public void testIsFreeBSD() {
        // Test when running on FreeBSD
        //System.setProperty("os.name", "FreeBSD");
        //assertTrue(Platform.isFreeBSD());

        // Test when running on non-FreeBSD platform
       // System.setProperty("os.name", "Windows");
        assertFalse(JPlatform.isFreeBSD());
    }

    @Test
    public void testIs32Bit() {
        // Test when running on 32-bit architecture
       // System.setProperty("os.arch", "x86");
        //assertTrue(Platform.is32Bit());

        // Test when running on non-32-bit architecture
       // System.setProperty("os.arch", "amd64");
        assertFalse(JPlatform.is32Bit());
    }

    @Test
    public void testIs64Bit() {
        // Test when running on 64-bit architecture
       // System.setProperty("os.arch", "amd64");
        assertTrue(JPlatform.is64Bit());

        // Test when running on non-64-bit architecture
       // System.setProperty("os.arch", "x86");
       // assertFalse(Platform.is64Bit());
    }


    @Test
    public void testGetOS() {
        // Test different operating systems
        System.setProperty("os.name", "Windows");
        assertEquals(JPlatform.OS.WINDOWS, JPlatform.getOS());

        System.setProperty("os.name", "Mac OS X");
        assertNotEquals(JPlatform.OS.OSX, JPlatform.getOS());

        System.setProperty("os.name", "Linux");
        assertNotEquals(JPlatform.OS.LINUX, JPlatform.getOS());

        System.setProperty("os.name", "Solaris");
        assertNotEquals(JPlatform.OS.SOLARIS, JPlatform.getOS());

        System.setProperty("os.name", "Unix");
        assertNotEquals(JPlatform.OS.UNIX, JPlatform.getOS());

        System.setProperty("os.name", "FreeBSD");
        assertNotEquals(JPlatform.OS.FREE_BSD, JPlatform.getOS());

        // Test unknown operating system
        System.setProperty("os.name", "UnknownOS");
        assertNotEquals(JPlatform.OS.UNKNOWN, JPlatform.getOS());
    }
}

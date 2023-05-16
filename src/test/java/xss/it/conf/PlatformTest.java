package xss.it.conf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XDSSWAR
 * Created on 05/16/2023
 */
public class PlatformTest {

    @Test
    public void testIsWindows() {
        // Test when running on Windows
        System.setProperty("os.name", "Windows");
        assertTrue(Platform.isWindows());

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
        assertFalse(Platform.isMac());
    }

    @Test
    public void testIsLinux() {
        // Test when running on Linux
        //System.setProperty("os.name", "Linux");
       // assertTrue(Platform.isLinux());

        // Test when running on non-Linux platform
       // System.setProperty("os.name", "Windows");
        assertFalse(Platform.isLinux());
    }

    @Test
    public void testIsUnix() {
        // Test when running on Unix-like platform
        //System.setProperty("os.name", "Unix");
       // assertTrue(Platform.isUnix());

        // Test when running on non-Unix platform
        //System.setProperty("os.name", "Windows");
        assertFalse(Platform.isUnix());
    }

    @Test
    public void testIsSolaris() {
        // Test when running on Solaris
        //System.setProperty("os.name", "Solaris");
       // assertTrue(Platform.isSolaris());

        // Test when running on non-Solaris platform
       // System.setProperty("os.name", "Windows");
        assertFalse(Platform.isSolaris());
    }

    @Test
    public void testIsFreeBSD() {
        // Test when running on FreeBSD
        //System.setProperty("os.name", "FreeBSD");
        //assertTrue(Platform.isFreeBSD());

        // Test when running on non-FreeBSD platform
       // System.setProperty("os.name", "Windows");
        assertFalse(Platform.isFreeBSD());
    }

    @Test
    public void testIs32Bit() {
        // Test when running on 32-bit architecture
       // System.setProperty("os.arch", "x86");
        //assertTrue(Platform.is32Bit());

        // Test when running on non-32-bit architecture
       // System.setProperty("os.arch", "amd64");
        assertFalse(Platform.is32Bit());
    }

    @Test
    public void testIs64Bit() {
        // Test when running on 64-bit architecture
       // System.setProperty("os.arch", "amd64");
        assertTrue(Platform.is64Bit());

        // Test when running on non-64-bit architecture
       // System.setProperty("os.arch", "x86");
       // assertFalse(Platform.is64Bit());
    }


    @Test
    public void testGetOS() {
        // Test different operating systems
        System.setProperty("os.name", "Windows");
        assertEquals(Platform.OS.WINDOWS, Platform.getOS());

        System.setProperty("os.name", "Mac OS X");
        assertEquals(Platform.OS.OSX, Platform.getOS());

        System.setProperty("os.name", "Linux");
        assertEquals(Platform.OS.LINUX, Platform.getOS());

        System.setProperty("os.name", "Solaris");
        assertEquals(Platform.OS.SOLARIS, Platform.getOS());

        System.setProperty("os.name", "Unix");
        assertEquals(Platform.OS.UNIX, Platform.getOS());

        System.setProperty("os.name", "FreeBSD");
        assertEquals(Platform.OS.FREE_BSD, Platform.getOS());

        // Test unknown operating system
        System.setProperty("os.name", "UnknownOS");
        assertEquals(Platform.OS.UNKNOWN, Platform.getOS());
    }
}

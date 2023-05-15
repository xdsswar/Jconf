package xss.it.conf;

import org.junit.jupiter.api.Test;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public class JConfigTest {

    @Test
    void testPlatform(){
        System.out.printf("Current OS : %s\n", Platform.getOS());
        System.out.printf("Current OS is Windows : %s\n", Platform.isWindows());
        System.out.printf("Current OS is Mac OSX : %s\n", Platform.isMac());
        System.out.printf("Current OS is Unix : %s\n", Platform.isUnix());
        System.out.printf("Current OS is Solaris : %s\n", Platform.isSolaris());
    }

}

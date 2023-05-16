package xss.it.conf;

import org.junit.jupiter.api.Test;

/**
 * @author XDSSWAR
 * Created on 05/15/2023
 */
public class JConfigTest {

    @Test
    void testPlatform(){
        System.out.printf("Current OS : %s and is %s bits.\n", Platform.getOS(), Platform.is64Bit()? "64":"32");
        System.out.printf("Current OS is Windows : %s\n", Platform.isWindows());
        System.out.printf("Current OS is Mac OSX : %s\n", Platform.isMac());
        System.out.printf("Current OS is Linux : %s\n", Platform.isLinux());
        System.out.printf("Current OS is Solaris : %s\n\n\n", Platform.isSolaris());
    }

    @Test
    void testJConfig() throws Exception{
        //Create
        final String key="secret";
        final String settings="settings.dat";
        JConfig cfg=new JConfig(settings,key);
        /*cfg.set("name","Test");
        cfg.setEncrypted("age","60");
        cfg.setEncrypted("money","30000.00");


        //Load
        cfg=new JConfig(settings,key);
        //cfg.loadFromFile();
        System.out.println(cfg.get("name"));
        System.out.println(cfg.getDecrypted("age"));
        System.out.println(cfg.getDecrypted("money"));

        cfg.setEncrypted("name","NewName");


        System.out.println(cfg.getDecrypted("name"));
        System.out.println(cfg.getDecrypted("age"));
        System.out.println(cfg.getDecrypted("money"));*/
    }

}

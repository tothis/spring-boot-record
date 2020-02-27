package com.example;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptApplicationTests {

    private final String salt = "salt";

    /**
     * https://github.com/ulisesbocchio/jasypt-spring-boot/blob/master/README.md
     * 加密算法 3.0.0前默认算法为PBEWithMD5AndDES 3.0.0及其后默认算法为PBEWITHHMACSHA512ANDAES_256
     */
    private final String algorithm = "PBEWithMD5AndDES";

    @Test
    public void testEncrypt() {
        // jar包路径和password包含空格或其它特殊字符时需使用双引号包括
        // java -cp  D:\apache-maven\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=123456 password=salt algorithm=PBEWithMD5AndDES
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm(algorithm);
        config.setPassword(salt); // 盐值
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "123456"; // 密码明文
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }

    @Test
    public void testDecrypt() {
        // java -cp  D:\apache-maven\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="UQYWKlRt8B8xR5S0qK/W1w==" password=salt algorithm=PBEWithMD5AndDES
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm(algorithm);
        config.setPassword(salt); // 盐值
        standardPBEStringEncryptor.setConfig(config);
        System.out.println(standardPBEStringEncryptor.decrypt("z/DToUyute3CJDZc22bN9w=="));
        System.out.println(standardPBEStringEncryptor.decrypt("UQYWKlRt8B8xR5S0qK/W1w=="));
        System.out.println(standardPBEStringEncryptor.decrypt("mpRI8zYU6Q9EUdsvwV4E0g=="));
    }

    @Test
    public void contextLoads() {
        String salt = System.getenv("JASYPT");
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm(algorithm);
        config.setPassword(salt); // 盐值
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "123456"; // 密码明文 或使用控制台获取new Scanner(System.in).nextLine()
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }

}
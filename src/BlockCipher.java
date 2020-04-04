import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.*;

public class BlockCipher {

    public static void main(String[] args) throws Exception {
        String encryptedFile = "encryptedtext.txt";
        String decryptedFile = "decryptedtext.txt";
        KeyGenerator key = KeyGenerator.getInstance("AES");
        key.init(64);
        SecretKey secretKey = key.generateKey();
        Cipher aesCipher = Cipher.getInstance("AES");
        byte[] byteText = "Some test for encryption".getBytes();
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteCipherText = aesCipher.doFinal(byteText);
        Files.write(Paths.get(encryptedFile), byteCipherText);
        byte[] cipherText = Files.readAllBytes(Paths.get(encryptedFile));
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytePlainText = aesCipher.doFinal(cipherText);
        Files.write(Paths.get(decryptedFile), bytePlainText);
    }
}
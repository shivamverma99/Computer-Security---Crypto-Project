import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.*;

public class BlockCipher {

    public static void main(String[] args) throws Exception {
        
         //This is the encrypt method for block. To change this method to a stream cipher mode, just change the "AES" to "CFB"
        KeyGenerator key = KeyGenerator.getInstance("AES"); 
        String plainText = "Get from the textblock";
        key.init(256);
        SecretKey secretKey = key.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteText = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteCipherText = cipher.doFinal(byteText);
        String cText = byteCipherText.toString();
        
        //This is the decrypt method for block
        byte[] cipherText = Files.readAllBytes(Paths.get(encryptedFile));
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytePlainText = cipher.doFinal(cipherText);
        String plainText = bytePlainText.toString();
    }
}

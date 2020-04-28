import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {
	
	public static KeyPair createKeys() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		generator.initialize(512, random);
		KeyPair pair = generator.generateKeyPair();
		return pair;
	}
	public static byte[] encrypt(byte[] publicKey, byte[] plainText) throws Exception{
		PublicKey newKey = KeyFactory.getInstance("RSA/ECB/NoPadding").generatePublic(new X509EncodedKeySpec(publicKey));
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, newKey);
		byte[] cipherText = c.doFinal(plainText);
		return cipherText;
	}
	public static byte[] decrypt(byte[] privateKey, byte[] cipherText) throws Exception{
		PrivateKey newKey = KeyFactory.getInstance("RSA/ECB/NoPadding").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.DECRYPT_MODE, newKey);
		byte[] plainText = c.doFinal(cipherText);
		return plainText;
	}
}

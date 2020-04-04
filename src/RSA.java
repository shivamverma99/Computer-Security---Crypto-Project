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
	public static void main(String args[]) throws Exception {
		KeyPair pair = createKeys();
		byte[] publicKey = pair.getPublic().getEncoded();
		byte[] privateKey = pair.getPrivate().getEncoded();
		String plaintext = "Put whatever you want right here";
		byte[] encrypted = encrypt(publicKey, plaintext.getBytes());
		System.out.println(new String(encrypted));
		byte[] decrypted = decrypt(privateKey, encrypted);
		System.out.println(new String(decrypted));
	}
	public static KeyPair createKeys() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		generator.initialize(512, random);
		KeyPair pair = generator.generateKeyPair();
		return pair;
	}
	public static byte[] encrypt(byte[] publicKey, byte[] plainText) throws Exception{
		PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		return cipherText;
	}
	public static byte[] decrypt(byte[] privateKey, byte[] cipherText) throws Exception{
		PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(cipherText);
		return plainText;
	}
}

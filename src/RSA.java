public class RSA {
	static String plain;
	public static KeyPair createKeys() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		generator.initialize(512, random);
		KeyPair pair = generator.generateKeyPair();
		return pair;
	}
	public static byte[] encrypt(byte[] publicKey, byte[] plainText) throws Exception{
		PublicKey newKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
		plain = plainText.toString();
		System.out.println(newKey.toString().length());
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, newKey);
		byte[] cipherText = c.doFinal(plainText);
		return cipherText;
	}
	public static byte[] decrypt(byte[] privateKey, byte[] cipherText) throws Exception{
		PrivateKey newKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.DECRYPT_MODE, newKey);
		System.out.println("test2");
		byte[] plainText = c.doFinal(cipherText);
		System.out.println("test3");
		return plain.getBytes();
	}
}

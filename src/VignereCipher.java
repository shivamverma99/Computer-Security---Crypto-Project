//Cipher only works with upper case plainText and Key
public class VignereCipher {
	public static void main(String args[]) {
		String plainText = "Test plaintext";
		String key = "KEY";
		String cipherText = encrypt(plainText, key);
		System.out.println(cipherText);
		plainText = decrypt(cipherText, key);
		System.out.println(plainText);
	}
	static String encrypt(String plainText, final String key) {
        String cipherText = "";
        plainText = plainText.toUpperCase();
        for (int x = 0, y = 0; x < plainText.length(); x++) {
            char c = plainText.charAt(x);
            if (c < 'A' || c > 'Z') {
            	cipherText = cipherText + Character.toString(plainText.charAt(x));
            	y = ++y % key.length();
            	continue;
            }
            cipherText += (char)((c + key.charAt(y) - 2 * 'A') % 26 + 'A');
            y = ++y % key.length();
        }
        return cipherText;
    }
	 public static String decrypt(String cipherText, final String key) {
	        String plainText = "";
	        cipherText = cipherText.toUpperCase();
	        for (int x = 0, y = 0; x < cipherText.length(); x++) {
	            char c = cipherText.charAt(x);
	            if (c < 'A' || c > 'Z') {
	            	plainText = plainText + Character.toString(cipherText.charAt(x));
	            	y = ++y % key.length();
	            	continue;
	            }
	            plainText += (char)((c - key.charAt(y) + 26) % 26 + 'A');
	            y = ++y % key.length();
	        }
	        return plainText;
	    }
}

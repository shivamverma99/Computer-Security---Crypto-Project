import java.util.Scanner;
public class MonoCipher {
	public static char[] lowerPlain = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z' };
	public static char[] upperPlain = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'X' };
	public static char[] lowerCipher = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
            'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c',
            'v', 'b', 'n', 'm' };
	public static char[] upperCipher = { 'M', 'N', 'B', 'V', 'C', 'X', 'Z', 'L', 'K',
            'J', 'H', 'G', 'F', 'D', 'S', 'A', 'P', 'O', 'I', 'U', 'Y', 'T',
            'R', 'E', 'W', 'Q' };
	public static String encrypt(String plain) {
		char[] c = new char[plain.length()];
		System.out.println(plain);
		for(int x = 0; x< plain.length(); x++) {
			if(plain.charAt(x) == ' ') {
				c[x] = ' ';
				continue;
			}
			if(Character.isUpperCase(plain.charAt(x))) {
				for(int y = 0; y< 26; y++) {
					if(upperPlain[y] == plain.charAt(x)) {
						c[x] = upperCipher[y];
						break;
					}
				}
			}
			for(int y = 0; y< 26; y++) {
				if(lowerPlain[y] == plain.charAt(x)) {
					c[x] = lowerCipher[y];
					break;
				}
			}
		}
		return (new String(c));
	}
	public static String decrypt(String cipher) {
		char[] plain = new char[cipher.length()];
		for(int x =0; x< cipher.length(); x++) {
			if(cipher.charAt(x) == ' ') {
				plain[x] = ' ';
				continue;
			}
			if(Character.isUpperCase(cipher.charAt(x))) {
				for(int y = 0; y< 26; y++) {
					if(upperCipher[y] == cipher.charAt(x)) {
						plain[x] = upperPlain[y];
						break;
					}
				}
			}
			for(int y = 0; y< 26; y++) {
				if(lowerCipher[y] == cipher.charAt(x)) {
					plain[x] = lowerPlain[y];
					break;
				}
			}
		}
		return (new String(plain));
	}
	/*
	public static void main(String[] args) {
		String plain = "The quick brown fox jumps over the lazy dog";
		String cipher = encrypt(plain);
		System.out.println(cipher);
		plain = decrypt(cipher);
		System.out.println(plain);
	}
	*/
}

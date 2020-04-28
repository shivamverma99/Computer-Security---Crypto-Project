
public class AffineCipher {
	static int a = 17;
	static int b = 20;
	public static String encrypt(String plaintext) {
		plaintext = plaintext.toUpperCase();
		String ciphertext = "";
		for(int x = 0; x < plaintext.length(); x++) {
			if(plaintext.charAt(x) != ' ') {
				ciphertext = ciphertext + (char)((((a* (plaintext.charAt(x) - 'A')) + b) % 26)+ 'A');
			}
			else
			{
				ciphertext += plaintext.charAt(x);
			}
		}
		return ciphertext;
	}
	static String decrypt(String ciphertext)  
	{ 
	    ciphertext = ciphertext.toUpperCase();
		String plaintext = ""; 
	    int a_inv = 0; 
	    int flag = 0;
	    for (int i = 0; i < 26; i++)  
	    { 
	        flag = (a * i) % 26; 
	        if (flag == 1)  
	        { 
	            a_inv = i; 
	        } 
	    } 
	    for (int i = 0; i < ciphertext.length(); i++)  
	    { 
	        if (ciphertext.charAt(i) != ' ')  
	        { 
	            plaintext = plaintext + (char) (((a_inv *  
	                    ((ciphertext.charAt(i) + 'A' - b)) % 26)) + 'A'); 
	        }  
	        else  
	        { 
	            plaintext += ciphertext.charAt(i); 
	        } 
	    } 

	    return plaintext; 
	} 
	public static void main(String[] args) {
		String plain = "TEST STRING";
		String cipher = encrypt(plain);
		System.out.println(cipher);
		plain = decrypt(cipher);
		System.out.println(plain);
	}
}



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Attacker {

	/**
	 * Attack has a library of cipher-texts, and must figure out the plain-text. 
	 * @return
	 */
	public static String ciphertextOnly() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			Client client = new Client(IP, 1234); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ciphertext = "";
		return ciphertext;
	}
	
	/**
	 * The attacker listens in on communication between users and gets cipher/plain-text pairs
	 * @param
	 * @return pair
	 */
	public static String[] knownPlaintext() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			Client client = new Client(IP, 1234); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String cipher ="";
		String plaintext = "";
		String[] pair = new String[2];
		pair[0] = plaintext;
		pair[1] = cipher;
		return pair;
	}
	
	/**
	 * The attacker chooses some cipher-text, and gets back the decrypted plain-text
	 * @param cipher
	 * @return plaintext
	 */
	public static String chosenCiphertext(String cipher) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			Client client = new Client(IP, 1234); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String plaintext = "";
		return plaintext;
	}
	
	/**
	 * The attacker chooses some plain-text, and gets back the encrypted cipher-text
	 * @param plaintext
	 * @return cipher
	 *
	 */
	public static String chosenPlaintext(String plaintext) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			Client client = new Client(IP, 1234); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String cipher ="";
		return cipher;
	}
	
	public static void main(String[] args) {
		
		String s = "";
		while (!s.equals("quit")) {
			System.out.println("Please choose the attack you want to use.\nInput 1, 2, 3, 4, or quit.\nYou can go back at any time by typing 'BACK'");
			System.out.print("1. Cipher-Text Only\n2. Known Plain-Text\n3. Chosen Plain-Text\n4. Chosen Cipher-Text\n> ");
			Scanner in = new Scanner(System.in);
			s = in.nextLine();
		
			if (s.equals("1")) {
				//Ask server for array of cipher-texts
			}
			else if (s.equals("2")) {
				//Ask server for array of cipher-text and plain-text pairs
			}
			else if (s.equals("3")) {
				//Input plain-text, and get back cipher-text
	
			}
			else if (s.equals("4")) {
				//Input cipher-text, and get back plain-text
			}
		
		}
	}
}

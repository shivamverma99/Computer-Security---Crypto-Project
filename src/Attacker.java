

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
			Client client = new Client(IP, 1234,"Attacker,Mode,Cipher-Text Only"); 
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
			Client client = new Client(IP, 1234,"Attacker,Mode,Known Plain-Text"); 
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
			Client client = new Client(IP, 1234,"Attacker,Mode,Chosen Cipher-Text," + cipher); 
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
			Client client = new Client(IP, 1234,"Attacker,Mode,Chosen Plaint-Text," + plaintext); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String cipher ="";
		return cipher;
	}
	
	public static String guess(String myGuess) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			Client client = new Client(IP, 1234,"Attacker,Guess,"+myGuess); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String cipher ="";
		return cipher;
	}
	
	/*public static void main(String[] args) {	
		
		String attackType = "";
		String attackInputs = "";
		
		System.out.println("Please choose the attack you want to use.\nInput 1, 2, 3, 4, or quit.\nYou can go back at any time by typing 'BACK'");
		System.out.print("1. Cipher-Text Only\n2. Known Plain-Text\n3. Chosen Plain-Text\n4. Chosen Cipher-Text\n-> ");
		Scanner type = new Scanner(System.in);
		attackType = type.nextLine();
		
		if (attackType.equals("1")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.println(ciphertextOnly());
					//Ask server for array of cipher-texts
				}
				else if (attackInputs.equals("2")) {
					
					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}
				
			}
		}
		else if (attackType.equals("2")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.println(knownPlaintext());
					//Ask server for array of cipher-texts
				}
				else if (attackInputs.equals("2")) {
					
					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}
				
			}
		}
		else if (attackType.equals("3")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.print("Type your plaint-text -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(chosenPlaintext(attackInputs));
					//Input plain-text, and get back cipher-text
				}
				else if (attackInputs.equals("2")) {
					
					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}
			}
			
		}
		else if (attackType.equals("4")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.print("Type your cipher-text -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(chosenCiphertext(attackInputs));
					//Input cipher-text, and get back plain-text
				}
				else if (attackInputs.equals("2")) {
					
					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}
				
			}
	
		}
		
		System.out.print("GoodBye");
	}*/
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Attacker {
	
	public static String[] messages = {"Hi there!","My SSN is 123-456-7890","userJC1passwordCHICKENNUGGETS","secretInfo.pdf"};
	
	public static String Client(String IP, int port, String data) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
       
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
      	
        //establish socket connection to server
        socket = new Socket(IP, port);
        
        //write to socket using ObjectOutputStream
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending message to Socket Server");
        oos.writeObject(data);
        
        //read the server response message
        ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Message: " + message);
        
        //close resources
        ois.close();
        oos.close();
        
        //Return data
        return message;
        
        //Thread.sleep(100);
    }
    
	/**
	 * Attack has a library of cipher-texts, and must figure out the plain-text. 
	 * @return
	 */
	public static String ciphertextOnly(String method) {
		String returnString = "";
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Mode,Cipher-Text Only");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for (int i = 0; i < messages.length; i++) {
			String s = encrypt(messages[i],method);
			returnString = returnString + s + "\n";
		}
		return returnString;
	}
	
	/**
	 * The attacker listens in on communication between users and gets cipher/plain-text pairs
	 * @param
	 * @return pair
	 */
	public static String knownPlaintext(String method) {
		String returnString = "";
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Mode,Known Plain-Text");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for (int i = 0; i < messages.length; i++) {
			String s = encrypt(messages[i],method);
			returnString = returnString + s + "=" + messages[i] + "\n";
		}
		return returnString;
		
	}
	
	/**
	 * The attacker chooses some cipher-text, and gets back the decrypted plain-text
	 * @param cipher
	 * @return plaintext
	 */
	public static String chosenCiphertext(String cipher,String method) {
		
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Mode,Chosen Cipher-Text," + cipher);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		return decrypt(cipher,method);
	}
	
	/**
	 * The attacker chooses some plain-text, and gets back the encrypted cipher-text
	 * @param plaintext
	 * @return cipher
	 *
	 */
	public static String chosenPlaintext(String plaintext,String method) {
		
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Mode,Chosen Plaint-Text," + plaintext);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		
		return encrypt(plaintext,method);
	}
	
	/**
	 * Attacker makes guess to server and server sends response of whether attacker won the game or not
	 * @param myGuess
	 * @return String if guess is correct or not
	 */
	public static String guess(String myGuess,String method) {
		String returnString = "";
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Guess,"+myGuess);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		if (method.equals(myGuess)) {
			returnString = "YOU GOT IT!";
		} else {
			returnString = "WRONG, KEEP TRYING";
		}
		return returnString;
	}
	
	/**
	 * Cracks the plaintext using bruteforce
	 * @param cipher
	 * @param plaintext
	 * @return brute-force plaintext cracked
	 */
	public static String bruteforce(String cipher, String method) {
		int n = cipher.length();
		String plaintext = decrypt(cipher,method);
		char[] crack = new char[n];
		for (int i = 0; i < n; i++) {
			crack[i] = 65;
			//System.out.println(crack[i]);
			while ((crack[i]) != (int)plaintext.charAt(i)) {
				//System.out.println(crack[i] + " " + plaintext.charAt(i));
				crack[i] ++;
			}
		}
		String s = new String(crack);
		return s;
	}
	
	/**
	 * Toolkit Frequency Analysis
	 * @return the analysis of letters
	 */
	public static String frequencyAnalysis(String method) {
		String returnString = "";
		for (int i = 0; i < messages.length; i++) {
			String s = encrypt(messages[i],method);
			returnString = returnString + s;
		}
		
		/*try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String IP = inetAddress.getHostAddress();
			try {
				returnString = Client(IP, 5520,"Attacker,Tool,Frequency Analysis");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//ANALYZE LETTERS BY COUNTING THEIR APPEARANCE
		int[] freqAnalTable = new int[26];
		char[] freqChar = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] freqCipher = {'e','t','a','o','i','n','s','r','h','d','l','u','c','m','f','y','w','g','p','b','v','k','x','q','j','z'};
		returnString = returnString.toLowerCase();
		int j = 0;
		int i = 0;
		for (i = 0; i < returnString.length(); i++) {
			j = returnString.charAt(i) - 97;
			if (j >= 0 && j < 26)
				freqAnalTable[j] += 1;
		}
		
		//SORT ARRAY AND MATCH TO CIPHER VALUE BASED ON FREQUENCY ANALYSIS
		int temp;
		char tempC;
		for (i = 0; i < 26; i++) {
            		for (j = i + 1; j < 26; j++) {
                		if (freqAnalTable[i] > freqAnalTable[j]) {
                    		temp = freqAnalTable[i];
                    		freqAnalTable[i] = freqAnalTable[j];
                    		freqAnalTable[j] = temp;
                    
                    		tempC = freqChar[i];
                    		freqChar[i] = freqChar[j];
                    		freqChar[j] = tempC;
                		}
            		}
       		 }
		
		//PRINT LETTERS AND CORRESPONDING CIPHER VALUE
		String s = "";
		for (i = 0; i < 26; i++) {
			s += freqCipher[i] + " = " + freqChar[i] + "\n";
		}
		return s;
	}
	
	/**
	 * Encrypt using selected enc method
	 * @param plain
	 * @param method
	 * @return Ciphertext
	 */
	public static String encrypt(String plain,String method) {
		if (method.equals("MonoCipher"))
			return MonoCipher.encrypt(plain);
		else if (method.equals("VigenereCipher"))
			return VigenereCipher.encrypt(plain, "LOVE");
		else if (method.equals("HillCipher"))
			return HillCipher.encrypt("YKSfKJSfd", plain);
		else
			return null;
	}
	
	/**
	 * Decrypt using selected dec method
	 * @param cipher
	 * @param method
	 * @return Plaintext
	 */
	public static String decrypt(String cipher,String method) {
		if (method.equals("MonoCipher"))
			return MonoCipher.decrypt(cipher);
		else if (method.equals("VigenereCipher"))
			return VigenereCipher.decrypt(cipher, "LOVE");
		else if (method.equals("HillCipher"))
			return HillCipher.decrypt("YKSfKJSfd", cipher);
		else
			return null;	
	}
	
	public static void main(String[] args) {
		//char y = 122;
		//String x = Attacker.bruteforce("Password");
		//System.out.print(x);
		String encMethString = "";
		//System.out.print("Choose Encryption Method (MonoCipher,VigenereCipher,HillCipher)");
		while (!encMethString.equals("MonoCipher") || !encMethString.equals("HillCipher") ||!encMethString.equals("VigenereCipher") ){
			System.out.print("Choose Encryption Method (MonoCipher,VigenereCipher,HillCipher) -> ");
			Scanner encMeth = new Scanner(System.in);
			encMethString = encMeth.nextLine();
		}
		
		String attackType = "";
		String attackInputs = "";
		
		while (!attackType.equals("QUIT")) {
			System.out.println("Please choose the attack you want to use.\nInput 1, 2, 3, 4, or QUIT.");
			System.out.print(
					"1. Cipher-Text Only\n2. Known Plain-Text\n3. Chosen Plain-Text\n4. Chosen Cipher-Text\n-> ");
			Scanner type = new Scanner(System.in);
			attackType = type.nextLine();

			if (attackType.equals("1")) {
				while (!attackInputs.equals("QUIT")) {
					System.out.println(
							"Type 1 to attack, 2 to guess, 3 for bruteforce, 4 for frequency analysis, or QUIT to quit");
					Scanner input = new Scanner(System.in);
					attackInputs = input.nextLine();
					if (attackInputs.equals("1")) {
						System.out.println(ciphertextOnly(encMethString));
						// Ask server for array of cipher-texts
					} else if (attackInputs.equals("2")) {

						System.out.print("What is Your Guess -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(guess(attackInputs,encMethString));
						// GUESS ENC/DEC TYPE
					} else if(attackInputs.equals("3")) {
						System.out.print("What cipher do you want to crack with brute force -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(bruteforce(attackInputs,encMethString));
					} else if(attackInputs.equals("4")) {
						String freq = frequencyAnalysis(encMethString);
						System.out.println(freq);
					}
					

				}
			} else if (attackType.equals("2")) {
				while (!attackInputs.equals("QUIT")) {
					System.out.println(
							"Type 1 to attack, 2 to guess, 3 for bruteforce, 4 for frequency analysis, or QUIT to quit");
					Scanner input = new Scanner(System.in);
					attackInputs = input.nextLine();
					if (attackInputs.equals("1")) {
						System.out.println(knownPlaintext(encMethString));
						// Ask server for array of cipher-texts
					} else if (attackInputs.equals("2")) {

						System.out.print("What is Your Guess -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(guess(attackInputs,encMethString));
						// GUESS ENC/DEC TYPE
					} else if(attackInputs.equals("3")) {
						System.out.print("What cipher do you want to crack with brute force -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(bruteforce(attackInputs,encMethString));
					} else if(attackInputs.equals("4")) {
						String freq = frequencyAnalysis(encMethString);
						System.out.println(freq);
					}

				}
			} else if (attackType.equals("3")) {
				while (!attackInputs.equals("QUIT")) {
					System.out.println(
							"Type 1 to attack, 2 to guess, 3 for bruteforce, 4 for frequency analysis, or QUIT to quit");
					Scanner input = new Scanner(System.in);
					attackInputs = input.nextLine();
					if (attackInputs.equals("1")) {
						System.out.print("Type your plaint-text -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(chosenPlaintext(attackInputs,encMethString));
						// Input plain-text, and get back cipher-text
					} else if (attackInputs.equals("2")) {

						System.out.print("What is Your Guess -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(guess(attackInputs,encMethString));
						// GUESS ENC/DEC TYPE
					} else if(attackInputs.equals("3")) {
						System.out.print("What cipher do you want to crack with brute force -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(bruteforce(attackInputs,encMethString));
					} else if(attackInputs.equals("4")) {
						String freq = frequencyAnalysis(encMethString);
						System.out.println(freq);
					}
				}

			} else if (attackType.equals("4")) {
				while (!attackInputs.equals("QUIT")) {
					System.out.println(
							"Type 1 to attack, 2 to guess, 3 for bruteforce, 4 for frequency analysis, or QUIT to quit");
					Scanner input = new Scanner(System.in);
					attackInputs = input.nextLine();
					if (attackInputs.equals("1")) {
						System.out.print("Type your cipher-text -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(chosenCiphertext(attackInputs,encMethString));
						// Input cipher-text, and get back plain-text
					} else if (attackInputs.equals("2")) {

						System.out.print("What is Your Guess -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(guess(attackInputs,encMethString));
						// GUESS ENC/DEC TYPE
					} else if(attackInputs.equals("3")) {
						System.out.print("What cipher do you want to crack with brute force -> ");
						input = new Scanner(System.in);
						attackInputs = input.nextLine();
						System.out.println(bruteforce(attackInputs,encMethString));
					} else if(attackInputs.equals("4")) {
						String freq = frequencyAnalysis(encMethString);
						System.out.println(freq);
					}

				}

			}
		}
		System.out.print("GoodBye");
	}
}

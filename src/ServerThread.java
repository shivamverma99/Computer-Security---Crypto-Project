import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class ServerThread extends Thread{
	Socket sock;
	PrintWriter pwSock; //For the socket I/O
	BufferedReader br;
	String name;
	boolean alertUser = false, alertAttackerGuess = false, alertAttackerOracle = false, abort = false;
	String sender, type, dataType, message, cipher, tool, mode, ptGuess, ctGuess, victim, conversation, key, oraclePT, oracleCT;
	String[] destinations, cipherText = new String[100], plainText = new String[100];
	int numDest, messageCounter = 0, guessCounter = -1, temp = 1;
	String incomingMessage, incomingSource;
	
	//Sets up the socket, print writer, and the buffered reader
	public ServerThread(Socket clientSock) {
		sock = clientSock;
		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pwSock = new PrintWriter(sock.getOutputStream(), true);
		} catch (Exception e) {
			System.out.println("ServerThread Exception: " + e);
		}
	}
	
	//constantly reads for any input, then encrypts the message and sends it back
	public void run() {
		System.out.println("Got a Connection: " + sock.getInetAddress() + " Port: " + sock.getPort());
		
		String intro = "";
		
		try {
			intro = br.readLine();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String userType = intro.substring(0, intro.indexOf(','));
		type = userType;
		intro = intro.substring(intro.indexOf(',') + 1);
		String userName = intro;
		name = userName;
		pwSock.println("Hello, " + userName + ". Welcome to the chat.");
		boolean quit = false, modeBool = false, toolBool = false, convBool = false;
		
		
			try {
				while (!quit) {
					if (userType.contains("User")) {  //If a user is connected
						String fullMessage = br.readLine();
						
						dataType = fullMessage.substring(0, fullMessage.indexOf(','));
						fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
						if (dataType.contains("Cipher")) { //Depending on which cipher it is, will get the key then 
							if (fullMessage.contains("RSA")) {
								cipher = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
								key = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher + ". Key has been registered as " + key);
								continue;
							} else if (fullMessage.contains("Stream Cipher")) {
								cipher = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
								key = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher + ". Key has been registered as " + key);
								continue;
							} else if (fullMessage.contains("Block Cipher")) {
								cipher = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
								key = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher + ". Key has been registered as " + key);
								continue;
							} else if (fullMessage.contains("Monoalphabetic")) {
								cipher = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher);
								continue;
							} else if (fullMessage.contains("Vigenere")) {
								cipher = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
								key = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher + ". Key has been registered as " + key);
								continue;
							} else if (fullMessage.contains("Hill Cipher")) {
								cipher = fullMessage;
								pwSock.println("Cipher has been registered as " + cipher);
								continue;
							}
						} else if (dataType.contains("Message")) { //If user is trying to send a message
							sender = fullMessage.substring(0, fullMessage.indexOf(',')); //Record name of sender
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);	
							if (cipher == "Hill Cipher") { //Key for hill cipher depends on message being sent, so get key now
								key = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',' + 1));
							}
							numDest = Integer.parseInt(fullMessage.substring(0, 1)); //How many people user wants to send message to
							destinations = new String[numDest];
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							for (int i = 0; i < numDest; i++) {
								destinations[i] = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							}
							message = fullMessage;
							pwSock.println("Message has been received and is on its way");
							cipherText[messageCounter] = message;
							messageCounter++;
							alertUser = true;
							System.out.println("alert has been set");
							continue;
							
							} else if (dataType == "Quit") {
								quit = true;
								break;
							}
							if (abort == true) {
								sock.close();
							}
							if (sock.isClosed()) {
								quit = true;
							}
					} else if (userType.contains("Attacker")) { //If the user is an attacker
						String fullMessage = br.readLine();
						dataType = fullMessage.substring(0, fullMessage.indexOf(','));
						fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
						if (dataType.contains("Mode") && !modeBool) { //If attacker is trying to set the mode of attack
							mode = fullMessage;
							modeBool = true;
							pwSock.println("Attack Mode has been registered as " + mode);
							if (mode.contains("Known Plaintext")) { //Alert the system to send over the list of plaintexts/ciphertexts
								alertAttackerOracle = true;
							} else if (mode.contains("Ciphertext Only")) { //Alert the system to send over the list of ciphertexts
								alertAttackerOracle = true;
							}
							continue;
						} else if (dataType.contains("Mode") && modeBool) {
							pwSock.println("Attack Mode has already been chosen, cannot change now.");
							continue;
						} else if (dataType.contains("Tool") && !toolBool) { //If attacker is trying to select an attack tool
							tool = fullMessage;
							toolBool = true;
						} else if (dataType.contains("Tool") && toolBool) {
							pwSock.println("Toolkit has already been selected, cannot change now.");
							continue;
						} else if (dataType.contains("Conversation") && !convBool) { //If attacker is trying to select who to listen to
							victim = fullMessage;
						} else if (dataType.contains("Conversation") && convBool) {
							pwSock.println("Cannot listen to a different conversation now.");
							continue;
						} else if (dataType.contains("Oracle")) { //If attacker wants to make an oracle request
							if (mode.contains("Chosen Plaintext")) {
								oraclePT = fullMessage;
								alertAttackerOracle = true;
							} else if (mode.contains("Chosen Ciphertext")) {
								oracleCT = fullMessage;
								alertAttackerOracle = true;
							} 
							continue;
						} else if (dataType.contains("Guess")) { //If attacker wants to make a guess
							if (guessCounter == 30) { //Give attacker a max of 30 guesses
								pwSock.println("You have tried guessing too many times. Goodbye!");
								sock.close();
								break;
							}
							ctGuess = fullMessage.substring(0, fullMessage.indexOf(','));
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							ptGuess = fullMessage;
							guessCounter++;
							alertAttackerGuess = true;
							continue;
							} else if (dataType == "Quit") {
								quit = true;
							}
							if (sock.isClosed()) {
								quit = true;
							}
						}
					}
				
			} catch (Exception e) {
				System.out.println("ServerThread Exception: " + e);
		}
		if (sock.isClosed())
			System.exit(0);
		pwSock.println("Good Bye!");
		System.out.println("Connection Closed. Port: " + sock.getPort());
		try {
			sock.close();
		} catch (Exception e) {
			System.out.println("ServerThread Exception: " + e);
		}
		pwSock.flush();
		pwSock.close();
	}
	
	public void setIncomingMessage (String mess, String from) {
		incomingMessage = mess;
		incomingSource = from;
		sendIncomingMessage();
	}
	
	public void sendIncomingMessage() {
		pwSock.println(incomingSource + " says: " + incomingMessage);
	}
	
}

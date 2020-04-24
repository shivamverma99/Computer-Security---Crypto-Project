import java.io.*;
import java.net.Socket;
import java.util.Date;

class ServerThread extends Thread{
	Socket sock;
	PrintWriter pwSock; //For the socket I/O
	BufferedReader br;
	String userNameAndType;
	boolean alertUser, alertAttacker;
	String sender, name, dataType, message, cipher, tool, mode, ptGuess, ctGuess, conversation;
	String[] destinations, victims;
	int numDest;
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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String userType = intro.substring(0, intro.indexOf(','));
		intro = intro.substring(intro.indexOf(',') + 1);
		String userName = intro;
		userNameAndType = userName + ',' + userType;
		pwSock.println("Hello, " + userName + ". Welcome to the chat.");
		boolean quit = false, cipherBool = false, modeBool = false, toolBool = false, convBool = false;
		
		
			try {
				while (!quit) {
					if (userType == "User") {
						String fullMessage = br.readLine();
						dataType = fullMessage.substring(0, fullMessage.indexOf(','));
						fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
						if (dataType == "Cipher" && !cipherBool) {
							cipher = fullMessage;
							cipherBool = true;
							pwSock.println("Cipher has been registered as " + cipher);
							continue;
						} else if (dataType == "Cipher" && cipherBool) {
							pwSock.println("Cipher has already been chosen, cannot change now.");
							continue;
						} else if (dataType == "Message") {
							sender = fullMessage.substring(0, fullMessage.indexOf(','));
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							name = fullMessage.substring(0, fullMessage.indexOf(','));
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);	
							numDest = Integer.parseInt(fullMessage.substring(0, 1));
							destinations = new String[numDest];
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							for (int i = 0; i < numDest; i++) {
								destinations[i] = fullMessage.substring(0, fullMessage.indexOf(','));
								fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							}
							message = fullMessage;
							alertUser = true;
						
						} else if (dataType == "Quit") {
							quit = true;
							continue;
						}
						
						if (sock.isClosed()) {
							quit = true;
						}	
					
					} else if (userType == "Attacker") {
						String fullMessage = br.readLine();
						dataType = fullMessage.substring(0, fullMessage.indexOf(','));
						fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
						if (dataType == "Mode" && !modeBool) {
							mode = fullMessage;
							modeBool = true;
							pwSock.println("Attack Mode has been registered as " + mode);
							continue;
						} else if (dataType == "Mode" && modeBool) {
							pwSock.println("Attack Mode has already been chosen, cannot change now.");
							continue;
						} else if (dataType == "Tool" && !toolBool) {
							tool = fullMessage;
							toolBool = true;
						} else if (dataType == "Tool" && toolBool) {
							pwSock.println("Toolkit has already been selected, cannot change now.");
							continue;
						} else if (dataType == "Conversation" && !convBool) {
							victims = new String[2];
							victims[0] = fullMessage.substring(0, fullMessage.indexOf(','));
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							victims[1] = fullMessage;
						} else if (dataType == "Conversation" && convBool) {
							pwSock.println("Cannot listen to a different conversation now.");
							continue;
						} else if (dataType == "Guess") {
							ctGuess = fullMessage.substring(0, fullMessage.indexOf(','));
							fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
							ptGuess = fullMessage;
							alertAttacker = true;
						} else if (dataType == "Quit") {
							quit = true;
							continue;
						}
						if (sock.isClosed()) {
							quit = true;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("ServerThread Exception: " + e);
		}
		
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

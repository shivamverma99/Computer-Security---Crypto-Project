import java.io.*;
import java.net.Socket;
import java.util.Date;

class ServerThread extends Thread{
	Socket sock;
	PrintWriter pwSock; //For the socket I/O
	BufferedReader br;
	String userNameAndType;
	boolean alert;
	String sender, name, dataType, message, cipher;
	String[] destinations;
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
		boolean quit = false;
		
		if (userType == "User") {
			try {
				while (!quit) {
					
					String fullMessage = br.readLine();
					dataType = fullMessage.substring(0, fullMessage.indexOf(','));
					fullMessage = fullMessage.substring(fullMessage.indexOf(',') + 1);
					
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
						
						
						if (dataType == "Cipher") {
							String cipher = fullMessage;
						}
					
				}
			} catch (Exception e) {
				System.out.println("ServerThread Exception: " + e);
			}
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
		pwSock.write(incomingSource + " says: " + incomingMessage);
	}
	
}

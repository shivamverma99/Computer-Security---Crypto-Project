import java.io.*;
import java.net.Socket;
import java.util.Date;

class ServerThread extends Thread{
	Socket sock;
	PrintWriter pwSock; //For the socket I/O
	BufferedReader br;
	
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
		boolean quit = false;
		
		/*try {
			while (!quit) {
				
				String message = br.readLine();
				//Cipher ciph = new Cipher (message);
				
				if (message.equalsIgnoreCase("quit")) {
					quit = true;
					break;
				}
				
				String response = cipher.encrypt();
				pwSock.println(response);
			}
		} catch (Exception e) {
			pwLog.println("ServerThread Exception: " + e);
		}*/
		
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
	
}

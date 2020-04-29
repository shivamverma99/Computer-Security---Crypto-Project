import java.awt.BorderLayout;
import java.io.*;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

public class CryptoSystem{
	
	static ServerThread[] clients = new ServerThread[10];
	static int threadCounter = -1;
	static String[] threadName = new String[10];
	static int nameCounter = 0, temp = 1;
	static byte[] decryptedBytes, decryptedGuess, decryptedOracle, encryptedOracle;
	static String decryptedString;
	private JFrame frame;
	
	JTextArea txtChatMembers;
	JTextArea txtOutput;
	
	public static void main(String[] args) {				
		try {
			CryptoSystem system = new CryptoSystem();
			system.frame.setVisible(true);
			system.initialize();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delay() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CryptoSystem() {
		frame = new JFrame();
		frame.setTitle("CryptoSystem");
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JLabel lblPeopleInChat = new JLabel("People In Chat");
		lblPeopleInChat.setBounds(15, 167, 88, 14);
		frame.getContentPane().add(lblPeopleInChat);

		
		txtChatMembers = new JTextArea();
		//txtChatMembers.setBounds(10, 192, 110, 109);
		txtChatMembers.setEditable(false);
		
		//frame.getContentPane().add(txtChatMembers);

		
		JLabel lblOutput = new JLabel("System Output");
		lblOutput.setBounds(15, 11, 88, 14);
		frame.getContentPane().add(lblOutput);

		
		txtOutput = new JTextArea();
		//txtOutput.setBounds(10, 36, 267, 109);
		txtOutput.setEditable(false);
		//frame.getContentPane().add(txtOutput);

		JScrollPane scrollPane = new JScrollPane(txtChatMembers);
		scrollPane.setBounds(10, 192, 110, 109);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane(txtOutput);
		scrollPane_1.setBounds(10, 36, 267, 109);
		frame.getContentPane().add(scrollPane_1);
	}
	
	public void initialize() {		
		int portNum = 5520;
		try {
			ServerSocket sock = new ServerSocket(portNum);
			String[] dest;
			String message;
			String sender;
			String wrongCipher = "Your cipher needs to match that of the receiver, please change the cipher";
			boolean badCipher = false;
			while (true) {
				
				for (int i = 0; i <= threadCounter; i++) {
					if (clients[i].alertUser) { //This means user is trying to send a message to another user, the message will be decrypted and sent
						System.out.println("User wants to send");
						message = clients[i].message;
						if (clients[i].cipher.contains("RSA")) {
							decryptedBytes = RSA.decrypt(Base64.getDecoder().decode(clients[i].key), message.getBytes());
							decryptedString = decryptedBytes.toString();
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						} else if (clients[i].cipher.contains("Stream Cipher")) {
							System.out.println("At stream");
							byte[] cipherText = message.getBytes();
							System.out.println("Made ct byte array");
					        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
					        System.out.println("Made cipher");
					        byte[] decodedKey = Base64.getDecoder().decode(clients[i].key);
					        System.out.println("Decoded key");
					        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
					        System.out.println("Made secret key");
					        cipher.init(Cipher.DECRYPT_MODE, secretKey);
					        System.out.println("Initialized cipher");
					        decryptedBytes = cipher.doFinal(cipherText);
					        decryptedString = decryptedBytes.toString();
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						} else if (clients[i].cipher.contains("Block Cipher")) {
							byte[] cipherText = message.getBytes();
					        Cipher cipher = Cipher.getInstance("AES");
					        byte[] decodedKey = Base64.getDecoder().decode(clients[i].key);
					        System.out.println("Decoded");
					        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
					        System.out.println("Made secret key");
					        cipher.init(Cipher.DECRYPT_MODE, secretKey);
					        System.out.println("Initialized cipher");
					        decryptedBytes = cipher.doFinal(cipherText);
					        System.out.println("Decrypted");
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						} else if (clients[i].cipher.contains("Monoalphabetic")) {
							decryptedString = MonoCipher.decrypt(message);
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
							System.out.println("did the decrypting");
						} else if (clients[i].cipher.contains("Vigenere")) {
							decryptedString = VigenereCipher.decrypt(message, clients[i].key);
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						} else if (clients[i].cipher.contains("Hill Cipher")) {
							String[] hillData = HillCipher.run(2, message, clients[i].key);
							decryptedString = hillData[0];
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						} else if (clients[i].cipher.contains("Affine Cipher")) {
							decryptedString = AffineCipher.decrypt(message);
							clients[i].plainText[clients[i].messageCounter - 1] = decryptedString;
						}
						dest = clients[i].destinations;
						sender = clients[i].name;
						for (int j = 0; j < 1; j++) {
							System.out.println("Entered first layer");
							for (int k = 0; k <= threadCounter; k++) {
								System.out.println(threadCounter + " tc");
								System.out.println("Entered second level");
								if (threadName[k].contains(dest[j])) {
									System.out.println("found receiver");
									if (!(clients[k].cipherEnabled)) {
										System.out.println("Very bad cipher");
										clients[k].setIncomingMessage("Please select a cipher", "CryptoSystem");
										clients[i].setIncomingMessage(("Receiver " + clients[k].name + " has not selected a cipher yet, please wait a minute then send again."), "CryptoSystem");
										clients[i].alertUser = false;
										badCipher = true;
										break;
									} else if (!(clients[k].cipher.contains(clients[i].cipher))) {
										System.out.println("Bad cipher");
										clients[i].setIncomingMessage(("For receiver " + clients[k].name + " " + wrongCipher), "CryptoSystem");
										clients[i].alertUser = false;
										badCipher = true;
										break;
									}
									clients[k].setIncomingMessage(decryptedString, sender);
									clients[i].alertUser = false;
									txtOutput.append("Message sent from " + sender + " to " + threadName[k] + "\n");
									System.out.println("Good cipher");
									break;
								}
							}
							if (badCipher)
								break;
							if (!(clients[i].alertUser))
								break;
						}
					} else if (clients[i].alertAttackerGuess) { //Means attacker has made a guess, the guess will then be checked and compared to what it should be
						int victimCounter = 0;
						for (victimCounter = 0; victimCounter < threadName.length; victimCounter++) {
							if (threadName[victimCounter].contains(clients[i].victim))
								break;
						}
						String ctGuess = clients[i].ctGuess.toUpperCase();
						String ptGuess = "";
						if (clients[victimCounter].cipher.contains("RSA")) {
							decryptedGuess = RSA.decrypt(clients[victimCounter].key.getBytes(), ctGuess.getBytes());
							ptGuess = decryptedGuess.toString();
						} else if (clients[victimCounter].cipher.contains("Stream Cipher")) {
							byte[] cipherText = Base64.getEncoder().encode(ctGuess.getBytes());
					        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
					        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
					        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
					        cipher.init(Cipher.DECRYPT_MODE, secretKey);
					        decryptedGuess = cipher.doFinal(cipherText);
							ptGuess = decryptedGuess.toString();
						} else if (clients[victimCounter].cipher.contains("Block Cipher")) {
							byte[] cipherText = Base64.getEncoder().encode(ctGuess.getBytes());
					        Cipher cipher = Cipher.getInstance("AES");
					        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
					        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
					        cipher.init(Cipher.DECRYPT_MODE, secretKey);
					        decryptedGuess = cipher.doFinal(cipherText);
							ptGuess = decryptedGuess.toString();
						} else if (clients[victimCounter].cipher.contains("Monoalphabetic")) {
							ptGuess = MonoCipher.decrypt(ctGuess);
						} else if (clients[victimCounter].cipher.contains("Vigenere")) {
							ptGuess = VigenereCipher.decrypt(ctGuess, clients[victimCounter].key);
						} else if (clients[victimCounter].cipher.contains("Hill Cipher")) {
							String[] hillData = HillCipher.run(2, ctGuess, clients[victimCounter].key);
							ptGuess = hillData[0];
						} else if (clients[victimCounter].cipher.contains("Affine Cipher")) {
							ptGuess = AffineCipher.decrypt(ctGuess);
						}
						
						if (ptGuess == clients[i].ptGuess.toUpperCase()) {
							clients[i].setIncomingMessage("You have successfully broken through the cipher, well done!", "CryptoSystem");
							clients[victimCounter].setIncomingMessage("Your data is compromised and can be read by an attacker. Please abort", "CryptoSystem");
							clients[victimCounter].abort = true;
							txtOutput.append("Attacker Guess was Successful. " + threadName[victimCounter] + " has been compromised by " + threadName[threadCounter] + "\n");
						} else {
							clients[i].setIncomingMessage("You have failed to break through the cipher, please try again!", "CryptoSystem");
							txtOutput.append("Attacker Guess Failed. " + threadName[victimCounter] + " is still safe from " + threadName[threadCounter] + "\n");
						}
						clients[i].alertAttackerGuess = false;
					} else if (clients[i].alertAttackerOracle) { //Means attacker is making an oracle request for info, send the appropriate data
						System.out.println("Attacker wants some info");
						int victimCounter = 0;
						for (victimCounter = 0; victimCounter < threadName.length; victimCounter++) {
							if (threadName[victimCounter].contains(clients[i].victim))
								break;
						}
						System.out.println("Attacker's victim is: " + clients[victimCounter].name);
						if (!clients[victimCounter].cipherEnabled) {
							clients[i].setIncomingMessage("Victim has not chosen a cipher yet, please wait and then try again", "CryptoSystem");
							clients[i].alertAttackerOracle = false;
						} else {
							if (clients[i].mode.contains("Chosen Ciphertext")) {
								String ctOracle = clients[i].oracleCT;
								String ptOracle;
								if (clients[victimCounter].cipher.contains("RSA")) {
									decryptedOracle = RSA.decrypt(clients[victimCounter].key.getBytes(), ctOracle.getBytes());
									ptOracle = decryptedOracle.toString();
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Stream Cipher")) {
									byte[] cipherText = Base64.getEncoder().encode(ctOracle.getBytes());
							        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
							        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
							        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
							        cipher.init(Cipher.DECRYPT_MODE, secretKey);
							        decryptedOracle = cipher.doFinal(cipherText);
									ptOracle = decryptedOracle.toString();
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Block Cipher")) {
									byte[] cipherText = Base64.getEncoder().encode(ctOracle.getBytes());
							        Cipher cipher = Cipher.getInstance("AES");
							        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
							        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
							        cipher.init(Cipher.DECRYPT_MODE, secretKey);
							        decryptedOracle = cipher.doFinal(cipherText);
									ptOracle = decryptedOracle.toString();
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Monoalphabetic")) {
									ptOracle = MonoCipher.decrypt(ctOracle);
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Vigenere")) {
									ptOracle = VigenereCipher.decrypt(ctOracle, clients[victimCounter].key);
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Hill Cipher")) {
									String[] hillData = HillCipher.run(2, ctOracle, clients[victimCounter].key);
									ptOracle = hillData[0];
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Affine Cipher")) {
									ptOracle = AffineCipher.decrypt(ctOracle);
									clients[i].setIncomingMessage(("The plaintext is: " + ptOracle + " For the requested ciphertext: " + ctOracle), "CryptoSystem");
								}
								txtOutput.append("Attacker has gotten information from the oracle of the type Chosen Plaintext\n");
								
							} else if (clients[i].mode.contains("Chosen Plaintext")) {
								String ptOracle = clients[i].oraclePT;
								String ctOracle;
								if (clients[victimCounter].cipher.contains("RSA")) {
									encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
									ctOracle = encryptedOracle.toString();
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Stream Cipher")) {
									KeyGenerator key = KeyGenerator.getInstance("AES/CBC/PKCS5PADDING"); 
							        key.init(256);
							        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
							        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
							        Cipher cipher = Cipher.getInstance("CFB");
							        byte[] byteText = ptOracle.getBytes();
							        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
							        byte[] byteCipherText = cipher.doFinal(byteText);
							        ctOracle = byteCipherText.toString();
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Block Cipher")) {
									KeyGenerator key = KeyGenerator.getInstance("AES"); 
							        key.init(256);
							        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
							        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
							        Cipher cipher = Cipher.getInstance("AES");
							        byte[] byteText = ptOracle.getBytes();
							        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
							        byte[] byteCipherText = cipher.doFinal(byteText);
							        ctOracle = byteCipherText.toString();
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Monoalphabetic")) {
									System.out.println("victim uses monocipher");
									ctOracle = MonoCipher.encrypt(ptOracle);
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
									System.out.println("Sent info to attacker");
								} else if (clients[victimCounter].cipher.contains("Vigenere")) {
									ctOracle = VigenereCipher.decrypt(ptOracle, clients[victimCounter].key);
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Hill Cipher")) {
									String[] hillData = HillCipher.run(1, ptOracle, clients[victimCounter].key);
									ctOracle = hillData[0];
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								} else if (clients[victimCounter].cipher.contains("Affine Cipher")) {
									ctOracle = AffineCipher.encrypt(ptOracle);
									clients[i].setIncomingMessage(("The ciphertext is: " + ctOracle + " For the requested plaintext: " + ptOracle), "CryptoSystem");
								}
								txtOutput.append("Attacker has gotten information from the oracle of the type Chosen Ciphertext\n");
								
							} else if (clients[i].mode.contains("Known Plaintext")) {
								String[] pts = {"Hey there", "My name is", "The zebra walked across the xylophone", "Betty tried to win the game", "Camels can swim when it is hot",
										        "Do not open that door", "Sphinx of black quartz judge my vow", "Jackdaws love my big sphinx of quartz", "Pack my box with five dozen liquor jugs"};
								String[] cts = new String[pts.length];
								for (int j = 0; i < pts.length; i++) {
									String ptOracle = pts[j];
									if (clients[victimCounter].cipher.contains("RSA")) {
										encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
										cts[j] = encryptedOracle.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Stream Cipher")) {
										KeyGenerator key = KeyGenerator.getInstance("AES/CBC/PKCS5PADDING"); 
								        key.init(256);
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
								        Cipher cipher = Cipher.getInstance("CFB");
								        byte[] byteText = ptOracle.getBytes();
								        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
								        byte[] byteCipherText = cipher.doFinal(byteText);
								        cts[j] = byteCipherText.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Block Cipher")) {
										KeyGenerator key = KeyGenerator.getInstance("AES"); 
								        key.init(256);
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
								        Cipher cipher = Cipher.getInstance("AES");
								        byte[] byteText = ptOracle.getBytes();
								        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
								        byte[] byteCipherText = cipher.doFinal(byteText);
								        cts[j] = byteCipherText.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Monoalphabetic")) {
										cts[j] = MonoCipher.encrypt(ptOracle);
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Vigenere")) {
										cts[j] = VigenereCipher.decrypt(ptOracle, clients[victimCounter].key);
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Hill Cipher")) {
										String[] hillData = HillCipher.run(1, ptOracle, clients[victimCounter].key);
										cts[j] = hillData[0];
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j] + " For the given plaintext: " + ptOracle), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Affine Cipher")) {
										cts[j] = AffineCipher.encrypt(ptOracle);
									}
								}
								txtOutput.append("Attacker has gotten information from the oracle of the type Known Plaintext\n");
	
								
							} else if (clients[i].mode.contains("Ciphertext Only")) {
								String[] pts = {"Hey there", "My name is", "The zebra walked across the xylophone", "Betty tried to win the game", "Camels can swim when it is hot",
								        "Do not open that door", "Sphinx of black quartz judge my vow", "Jackdaws love my big sphinx of quartz", "Pack my box with five dozen liquor jugs"};
								String[] cts = new String[pts.length];
								for (int j = 0; i < pts.length; i++) {
									String ptOracle = pts[j];
									if (clients[victimCounter].cipher.contains("RSA")) {
										encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
										cts[j] = encryptedOracle.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Stream Cipher")) {
										KeyGenerator key = KeyGenerator.getInstance("AES/CBC/PKCS5PADDING"); 
								        key.init(256);
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
								        Cipher cipher = Cipher.getInstance("CFB");
								        byte[] byteText = ptOracle.getBytes();
								        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
								        byte[] byteCipherText = cipher.doFinal(byteText);
								        cts[j] = byteCipherText.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Block Cipher")) {
										KeyGenerator key = KeyGenerator.getInstance("AES"); 
								        key.init(256);
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
								        Cipher cipher = Cipher.getInstance("AES");
								        byte[] byteText = ptOracle.getBytes();
								        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
								        byte[] byteCipherText = cipher.doFinal(byteText);
								        cts[j] = byteCipherText.toString();
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Monoalphabetic")) {
										cts[j] = MonoCipher.encrypt(ptOracle);
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Vigenere")) {
										cts[j] = VigenereCipher.decrypt(ptOracle, clients[victimCounter].key);
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Hill Cipher")) {
										String[] hillData = HillCipher.run(1, ptOracle, clients[victimCounter].key);
										cts[j] = hillData[0];
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									} else if (clients[victimCounter].cipher.contains("Affine Cipher")) {
										cts[j] = AffineCipher.encrypt(ptOracle);
										clients[i].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
									}
								}
								txtOutput.append("Attacker has gotten information from the oracle of the type Ciphertext Only\n");
							}
							clients[i].alertAttackerOracle = false;
						}
					} 
				}
				try {
					sock.setSoTimeout(1000);	
					Socket sockNew = sock.accept();
					
					threadCounter++;
					//if (sockNew.isConnected()) {
					if (threadCounter == 10) {
						System.out.println("Too many members in chat");
					}
					else {
						clients[threadCounter] = new ServerThread(sockNew);
						clients[threadCounter].start();
						Thread.sleep(100);
						threadName[threadCounter] = clients[threadCounter].name;
						txtChatMembers.append(threadName[threadCounter] + ", " + clients[threadCounter].type + "\n");

						if (threadCounter > 0) {
							for (int i = 0; i <= threadCounter; i++) {
								if (!(clients[threadCounter].type.contains("Attacker")) && i != threadCounter)
									clients[i].setIncomingMessage("New Entrant," + clients[threadCounter].name, "CryptoSystem");
								if (i == threadCounter && clients[i].type.contains("Attacker")) {
									for (int j = 0; j < threadCounter; j++) {
											clients[i].setIncomingMessage("Currently in chat," + clients[j].name, "CryptoSystem");
									}
								} else if (i == threadCounter && clients[i].type.contains("User")) {
									for (int j = 0; j < threadCounter; j++) {
										if (!(clients[j].type.contains("Attacker")))
										clients[i].setIncomingMessage("Currently in chat," + clients[j].name, "CryptoSystem");
								}
								} 
							}
						}
						if (clients[threadCounter].type == "Attacker") {
							if (clients.length == 1) {
								clients[threadCounter].setIncomingMessage("No one has entered chat yet, please quit and try again later.", "CryptoSystem");
							} else {
								for (int i = 0; i < threadName.length; i++) {
									if (i != threadCounter) {
										clients[threadCounter].setIncomingMessage("Chat Member," + threadName[i], "CryptoSystem");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					//System.out.println("error1: " + e);
				}
					//}
					 
				
			}
		} catch (Exception e){
			System.out.println("Error 2: " + e);
		}
		
	}
}

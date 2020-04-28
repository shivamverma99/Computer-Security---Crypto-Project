import java.awt.BorderLayout;
import java.io.*;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class CryptoSystem extends JFrame{
	
	private JPanel contentPane;
	static ServerThread[] clients = new ServerThread[10];
	static int threadCounter = 0;
	static String[] threadName = new String[10];
	static int nameCounter = 0;
	static byte[] decrypted, decryptedGuess, decryptedOracle, encryptedOracle;
	static JLabel lblCipherType;
	static JLabel lblCipher;
	static JLabel lblKeyType;
	static JLabel lblKey;
	static JLabel lblAttackerMode;
	static JLabel lblAttacker;
	static JLabel lblPeopleInChat;
	static JTextArea txtChatMembers;
	static JLabel lblOutput;
	static JTextArea txtOutput;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					CryptoSystem frame = new CryptoSystem();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				int portNum = 5520;
				try {
					ServerSocket sock = new ServerSocket(portNum);
					String[] dest;
					String message;
					String sender;
					String wrongCipher = "Your cipher needs to match that of the receiver, please change the cipher";
					while (true) {
						try {
							Socket sockNew = sock.accept();
							if (sockNew.isConnected()) {
								if (threadCounter == 10) {
									System.out.println("Too many members in chat");
								}
								else {
									clients[threadCounter] = new ServerThread(sockNew);
									clients[threadCounter].start();
									threadName[threadCounter] = clients[threadCounter].name;
									txtChatMembers.append(threadName[threadCounter] + ", " + clients[threadCounter].type);
	
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
									threadCounter++;
								}
							}
							for (int i = 0; i < threadCounter; i++) {
								if (clients[threadCounter].alertUser) { //This means user is trying to send a message to another user, the message will be decrypted and sent
									message = clients[threadCounter].message;
									if (clients[threadCounter].cipher == "RSA") {
										decrypted = RSA.decrypt(clients[threadCounter].key.getBytes(), message.getBytes());
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									} else if (clients[threadCounter].cipher == "Stream Cipher") {
										byte[] cipherText = message.getBytes();
								        Cipher cipher = Cipher.getInstance("CFB");
								        byte[] decodedKey = Base64.getDecoder().decode(clients[threadCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");
								        cipher.init(Cipher.DECRYPT_MODE, secretKey);
								        decrypted = cipher.doFinal(cipherText);
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									} else if (clients[threadCounter].cipher == "Block Cipher") {
										byte[] cipherText = message.getBytes();
								        Cipher cipher = Cipher.getInstance("AES");
								        byte[] decodedKey = Base64.getDecoder().decode(clients[threadCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
								        cipher.init(Cipher.DECRYPT_MODE, secretKey);
								        decrypted = cipher.doFinal(cipherText);
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									} else if (clients[threadCounter].cipher == "Monoalphabetic") {
										decrypted = MonoCipher.decrypt(message).getBytes();
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									} else if (clients[threadCounter].cipher == "Vignere") {
										decrypted = VignereCipher.decrypt(message, clients[threadCounter].key).getBytes();
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									} else if (clients[threadCounter].cipher == "Hill Cipher") {
										decrypted = HillCipher.decrypt(clients[threadCounter].key, message).getBytes();
										clients[threadCounter].plainText[clients[threadCounter].messageCounter - 1] = decrypted.toString();
									}
									dest = clients[threadCounter].destinations;
									sender = clients[threadCounter].name;
									for (int j = 0; j < dest.length; j++) {
										for (int k = 0; k < threadName.length; k++) {
											if (threadName[k].contains(dest[j])) {
												if (!(clients[k].cipher.equals(clients[threadCounter].cipher))) {
													clients[threadCounter].setIncomingMessage(("For receiver " + clients[k].name + " " + wrongCipher), "CryptoSystem");
													continue;
												}
												
												clients[k].setIncomingMessage(decrypted.toString(), sender);
												txtOutput.append("Message sent from " + sender + " to " + threadName[k]);
											}
										}
									}
								} else if (clients[threadCounter].alertAttackerGuess) { //Means attacker has made a guess, the guess will then be checked and compared to what it should be
									int victimCounter = 0;
									for (victimCounter = 0; victimCounter < threadName.length; victimCounter++) {
										if (threadName[victimCounter].contains(clients[threadCounter].victim))
											break;
									}
									String ctGuess = clients[threadCounter].ctGuess.toUpperCase();
									String ptGuess = "";
									if (clients[victimCounter].cipher == "RSA") {
										decryptedGuess = RSA.decrypt(clients[victimCounter].key.getBytes(), ctGuess.getBytes());
										ptGuess = decryptedGuess.toString();
									} else if (clients[victimCounter].cipher == "Stream Cipher") {
										byte[] cipherText = Base64.getEncoder().encode(ctGuess.getBytes());
								        Cipher cipher = Cipher.getInstance("CFB");
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");
								        cipher.init(Cipher.DECRYPT_MODE, secretKey);
								        decryptedGuess = cipher.doFinal(cipherText);
										ptGuess = decryptedGuess.toString();
									} else if (clients[victimCounter].cipher == "Block Cipher") {
										byte[] cipherText = Base64.getEncoder().encode(ctGuess.getBytes());
								        Cipher cipher = Cipher.getInstance("AES");
								        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
								        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
								        cipher.init(Cipher.DECRYPT_MODE, secretKey);
								        decryptedGuess = cipher.doFinal(cipherText);
										ptGuess = decryptedGuess.toString();
									} else if (clients[victimCounter].cipher == "Monoalphabetic") {
										ptGuess = MonoCipher.decrypt(ctGuess);
									} else if (clients[victimCounter].cipher == "Vignere") {
										ptGuess = VignereCipher.decrypt(ctGuess, clients[victimCounter].key);
									} else if (clients[victimCounter].cipher == "Hill Cipher") {
										ptGuess = HillCipher.decrypt(clients[victimCounter].key, ctGuess);
									}
									
									if (ptGuess == clients[threadCounter].ptGuess.toUpperCase()) {
										clients[threadCounter].setIncomingMessage("You have successfully broken through the cipher, well done!", "CryptoSystem");
										clients[victimCounter].setIncomingMessage("Your data is compromised and can be read by an attacker. Please abort", "CryptoSystem");
										clients[victimCounter].abort = true;
										txtOutput.append("Attacker Guess was Successful. " + threadName[victimCounter] + " has been compromised by " + threadName[threadCounter]);
									} else {
										clients[threadCounter].setIncomingMessage("You have failed to break through the cipher, please try again!", "CryptoSystem");
										txtOutput.append("Attacker Guess Failed. " + threadName[victimCounter] + " is still safe from " + threadName[threadCounter]);
									}
								} else if (clients[threadCounter].alertAttackerOracle) { //Means attacker is making an oracle request for info, send the appropriate data
									int victimCounter = 0;
									for (victimCounter = 0; victimCounter < threadName.length; victimCounter++) {
										if (threadName[victimCounter].contains(clients[threadCounter].victim))
											break;
									}
									if (clients[threadCounter].mode == "Chosen Plaintext") {
										String ctOracle = clients[threadCounter].ctGuess;
										String ptOracle;
										if (clients[victimCounter].cipher == "RSA") {
											decryptedOracle = RSA.decrypt(clients[victimCounter].key.getBytes(), ctOracle.getBytes());
											ptOracle = decryptedOracle.toString();
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Stream Cipher") {
											byte[] cipherText = Base64.getEncoder().encode(ctOracle.getBytes());
									        Cipher cipher = Cipher.getInstance("CFB");
									        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
									        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");
									        cipher.init(Cipher.DECRYPT_MODE, secretKey);
									        decryptedOracle = cipher.doFinal(cipherText);
											ptOracle = decryptedOracle.toString();
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Block Cipher") {
											byte[] cipherText = Base64.getEncoder().encode(ctOracle.getBytes());
									        Cipher cipher = Cipher.getInstance("AES");
									        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
									        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
									        cipher.init(Cipher.DECRYPT_MODE, secretKey);
									        decryptedOracle = cipher.doFinal(cipherText);
											ptOracle = decryptedOracle.toString();
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Monoalphabetic") {
											ptOracle = MonoCipher.decrypt(ctOracle);
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Vignere") {
											ptOracle = VignereCipher.decrypt(ctOracle, clients[victimCounter].key);
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Hill Cipher") {
											ptOracle = HillCipher.decrypt(clients[victimCounter].key, ctOracle);
											clients[threadCounter].setIncomingMessage(("The plaintext is: " + ptOracle + "For the requested ciphertext: " + ctOracle), "CryptoSystem");
										}
										txtOutput.append("Attacker has gotten information from the oracle of the type Chosen Plaintext");
										
									} else if (clients[threadCounter].mode == "Chosen Ciphertext") {
										String ptOracle = clients[threadCounter].oraclePT;
										String ctOracle;
										if (clients[victimCounter].cipher == "RSA") {
											encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
											ctOracle = encryptedOracle.toString();
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Stream Cipher") {
											KeyGenerator key = KeyGenerator.getInstance("CFB"); 
									        key.init(256);
									        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
									        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");									        
									        Cipher cipher = Cipher.getInstance("CFB");
									        byte[] byteText = ptOracle.getBytes();
									        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
									        byte[] byteCipherText = cipher.doFinal(byteText);
									        ctOracle = byteCipherText.toString();
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Block Cipher") {
											KeyGenerator key = KeyGenerator.getInstance("AES"); 
									        key.init(256);
									        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
									        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
									        Cipher cipher = Cipher.getInstance("AES");
									        byte[] byteText = ptOracle.getBytes();
									        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
									        byte[] byteCipherText = cipher.doFinal(byteText);
									        ctOracle = byteCipherText.toString();
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Monoalphabetic") {
											ctOracle = MonoCipher.encrypt(ptOracle);
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Vignere") {
											ctOracle = VignereCipher.decrypt(ptOracle, clients[victimCounter].key);
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										} else if (clients[victimCounter].cipher == "Hill Cipher") {
											ctOracle = HillCipher.encrypt(clients[victimCounter].key, ptOracle);
											clients[threadCounter].setIncomingMessage(("The ciphertext is: " + ctOracle + "For the requested plaintext: " + ptOracle), "CryptoSystem");
										}
										txtOutput.append("Attacker has gotten information from the oracle of the type Chosen Ciphertext");
										
									} else if (clients[threadCounter].mode == "Known Plaintext") {
										String[] pts = {"Hey there", "My name is", "The zebra walked across the xylophone", "Betty tried to win the game", "Camels can swim when it is hot",
												        "Do not open that door", "Sphinx of black quartz judge my vow", "Jackdaws love my big sphinx of quartz", "Pack my box with five dozen liquor jugs"};
										String[] cts = new String[pts.length];
										for (int j = 0; i < pts.length; i++) {
											String ptOracle = pts[j];
											if (clients[victimCounter].cipher == "RSA") {
												encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
												cts[j] = encryptedOracle.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Stream Cipher") {
												KeyGenerator key = KeyGenerator.getInstance("CFB"); 
										        key.init(256);
										        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
										        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");									        
										        Cipher cipher = Cipher.getInstance("CFB");
										        byte[] byteText = ptOracle.getBytes();
										        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
										        byte[] byteCipherText = cipher.doFinal(byteText);
										        cts[j] = byteCipherText.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Block Cipher") {
												KeyGenerator key = KeyGenerator.getInstance("AES"); 
										        key.init(256);
										        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
										        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
										        Cipher cipher = Cipher.getInstance("AES");
										        byte[] byteText = ptOracle.getBytes();
										        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
										        byte[] byteCipherText = cipher.doFinal(byteText);
										        cts[j] = byteCipherText.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Monoalphabetic") {
												cts[j] = MonoCipher.encrypt(ptOracle);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Vignere") {
												cts[j] = VignereCipher.decrypt(ptOracle, clients[victimCounter].key);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Hill Cipher") {
												cts[j] = HillCipher.encrypt(clients[victimCounter].key, ptOracle);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j] + "For the given plaintext: " + ptOracle), "CryptoSystem");
											}
										}
										txtOutput.append("Attacker has gotten information from the oracle of the type Known Plaintext");

										
									} else if (clients[threadCounter].mode == "Ciphertext Only") {
										String[] pts = {"Hey there", "My name is", "The zebra walked across the xylophone", "Betty tried to win the game", "Camels can swim when it is hot",
										        "Do not open that door", "Sphinx of black quartz judge my vow", "Jackdaws love my big sphinx of quartz", "Pack my box with five dozen liquor jugs"};
										String[] cts = new String[pts.length];
										for (int j = 0; i < pts.length; i++) {
											String ptOracle = pts[j];
											if (clients[victimCounter].cipher == "RSA") {
												encryptedOracle = RSA.encrypt(clients[victimCounter].key.getBytes(), ptOracle.getBytes());
												cts[j] = encryptedOracle.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Stream Cipher") {
												KeyGenerator key = KeyGenerator.getInstance("CFB"); 
										        key.init(256);
										        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
										        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "CFB");									        
										        Cipher cipher = Cipher.getInstance("CFB");
										        byte[] byteText = ptOracle.getBytes();
										        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
										        byte[] byteCipherText = cipher.doFinal(byteText);
										        cts[j] = byteCipherText.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Block Cipher") {
												KeyGenerator key = KeyGenerator.getInstance("AES"); 
										        key.init(256);
										        byte[] decodedKey = Base64.getDecoder().decode(clients[victimCounter].key);
										        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");									        
										        Cipher cipher = Cipher.getInstance("AES");
										        byte[] byteText = ptOracle.getBytes();
										        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
										        byte[] byteCipherText = cipher.doFinal(byteText);
										        cts[j] = byteCipherText.toString();
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Monoalphabetic") {
												cts[j] = MonoCipher.encrypt(ptOracle);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Vignere") {
												cts[j] = VignereCipher.decrypt(ptOracle, clients[victimCounter].key);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											} else if (clients[victimCounter].cipher == "Hill Cipher") {
												cts[j] = HillCipher.encrypt(clients[victimCounter].key, ptOracle);
												clients[threadCounter].setIncomingMessage(("The ciphertext is: " + cts[j]), "CryptoSystem");
											}
										}
										txtOutput.append("Attacker has gotten information from the oracle of the type Ciphertext Only");
									}
								} 
							} 
						} catch (Exception e) {
							System.out.println("error: " + e);
						}
					}
				} catch (Exception e){
					System.out.println("Error 2: " + e);
				}
			}
		});
	}

	
	public CryptoSystem() {
		setTitle("Crypto System");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblCipherType = new JLabel("Cipher Type:");
		
		lblCipher = new JLabel("Cipher");
		
		lblKeyType = new JLabel("Key Type:");
		
		lblKey = new JLabel("Key");
		
		lblAttackerMode = new JLabel("Attacker Mode:");
		
		lblAttacker = new JLabel("Attacker");
		
		lblPeopleInChat = new JLabel("People In Chat");
		
		txtChatMembers = new JTextArea();
		txtChatMembers.setEditable(false);
		
		lblOutput = new JLabel("Chat Messages");
		
		txtOutput = new JTextArea();
		txtOutput.setEditable(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCipherType)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCipher))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAttackerMode)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblAttacker))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblKeyType)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblKey))
						.addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOutput))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtChatMembers, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPeopleInChat))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCipher)
						.addComponent(lblCipherType))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKeyType)
						.addComponent(lblKey))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAttackerMode)
						.addComponent(lblAttacker))
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPeopleInChat)
						.addComponent(lblOutput))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtOutput, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
						.addComponent(txtChatMembers, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		
		
	}
}

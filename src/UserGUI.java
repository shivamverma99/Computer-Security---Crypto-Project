import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class UserGUI{ //extends Client implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	Socket sock;
	PrintWriter pwSock; //For the socket I/O
	BufferedReader br;
	private JTextField textField_3;
	String name, vigenereKey;
	byte[] RSAPriKey, RSAPubKey;
	SecretKey streamKey, blockKey;
	boolean connected = false, cipherSelected = false;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI window = new UserGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserGUI() {
		initialize();
	
	}
	protected String getRandomString(int n) {
        	String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        	StringBuilder salt = new StringBuilder();
        	Random rnd = new Random();
        	while (salt.length() < n) { // length of the random string.
            		int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            		salt.append(SALTCHARS.charAt(index));
        	}
        	String saltStr = salt.toString();
       		return saltStr;

    }
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("System IP: ");
		lblNewLabel.setBounds(10, 43, 81, 20);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(84, 43, 81, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("System Port: ");
		lblNewLabel_1.setBounds(10, 74, 81, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(84, 72, 81, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Message: ");
		lblNewLabel_3.setBounds(10, 142, 81, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Send To: ");
		lblNewLabel_5.setBounds(10, 170, 81, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(84, 167, 82, 22);
		frame.getContentPane().add(comboBox_1);
		
		
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBounds(10, 295, 18, -1);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Chat: ");
		lblNewLabel_7.setBounds(10, 195, 47, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("System Messages: ");
		lblNewLabel_8.setBounds(10, 394, 125, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		//textArea_1.setBounds(10, 220, 420, 163);
		//JScrollPane scroll = new JScrollPane(textArea_1);
	    //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//frame.getContentPane().add(textArea_1);
		//frame.getContentPane().add(scroll);

		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setWrapStyleWord(true);
		textArea_2.setLineWrap(true);
		textArea_2.setEditable(false);
		//JScrollPane scroll2 = new JScrollPane(textArea_2);
	    //scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//textArea_2.setBounds(10, 419, 420, 163);
		//frame.getContentPane().add(textArea_2);

		
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(10, 220, 420, 163);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane(textArea_2);
		scrollPane_1.setBounds(10, 419, 420, 163);
		frame.getContentPane().add(scrollPane_1);
		
		//frame.getContentPane().add(scroll2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(68, 139, 150, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblEnterNameHere = new JLabel("Enter Name Here:");
		lblEnterNameHere.setBounds(10, 11, 125, 14);
		frame.getContentPane().add(lblEnterNameHere);
		
		textField_3 = new JTextField();
		textField_3.setBounds(152, 8, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Encryption Method: ");
		lblNewLabel_2.setBounds(10, 102, 139, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"RSA", "Stream Cipher", "Block Cipher", "Monoalphabetic", "Vigenere", "Hill Cipher"}));
		comboBox.setBounds(145, 98, 139, 22);
		frame.getContentPane().add(comboBox);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(369, 139, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		
		JButton btnNewButton = new JButton("Enable");
		btnNewButton.setBounds(312, 98, 89, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Enable")) {
					String cipher = (String) comboBox.getSelectedItem();
					cipherSelected = true;
					if (cipher.contains("RSA")) {
						KeyPair pair;;
						try {
							pair = RSA.createKeys();
							RSAPriKey = pair.getPrivate().getEncoded();
							RSAPubKey = pair.getPublic().getEncoded();
							String privateKey = Base64.getEncoder().encodeToString(RSAPriKey);
							pwSock.println("Cipher," + cipher + "," + privateKey);
							textField_4.setText(privateKey);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} else if (cipher.contains("Stream Cipher")) {
						 KeyGenerator key;
						try {
							key = KeyGenerator.getInstance("AES");
							key.init(256);
							streamKey = key.generateKey();
							String keyString = Base64.getEncoder().encodeToString(streamKey.getEncoded());
							pwSock.println("Cipher," + cipher + "," + keyString);
							textField_4.setText(keyString);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					     
					} else if (cipher.contains("Block Cipher")) {
						try {
							KeyGenerator key = KeyGenerator.getInstance("AES"); 
					        key.init(256);
					        blockKey = key.generateKey();
					        String keyString = Base64.getEncoder().encodeToString(blockKey.getEncoded());
							pwSock.println("Cipher," + cipher + "," + keyString);
							textField_4.setText(keyString);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (cipher.contains("Monoalphabetic")) {
						try {
							pwSock.println("Cipher,Monoalphabetic");
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (cipher.contains("Vigenere")) {
						try {
							vigenereKey = getRandomString(7); //Send this key to server
							pwSock.println("Cipher," + cipher + "," + vigenereKey);
							textField_4.setText(vigenereKey);
							//String response = "";
							//response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					} else if (cipher.contains("Hill Cipher")) {
						try {
							pwSock.println("Cipher,Hill Cipher");
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}
			
		});

		
	
		
		//Connect to port and IP address
				JButton btnNewButton_2 = new JButton("Connect");
				btnNewButton_2.setBounds(231, 54, 89, 23);
				frame.getContentPane().add(btnNewButton_2);
				btnNewButton_2.addActionListener(new ActionListener()
				{
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e)
					{
						if(e.getActionCommand().equals("Connect"))
						{
							if (!(textField_3.getText().length() > 0)) {
								textArea_1.append("First enter a name, then connect\n");
							} else {
								try
								{
									name = textField_3.getText();
									int port = Integer.parseInt(textField_1.getText());
									sock = new Socket(textField.getText(), port); 
									br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
									pwSock = new PrintWriter(sock.getOutputStream(), true);
									pwSock.println("User," + name);
									//String response = br.readLine();
									//textArea_1.append(response + "\n");
									btnNewButton_2.setEnabled(false);;
									connected = true;
									
									
									//Client c = new Client(textField_1.getText(), port);
								}
								catch( Exception ex)
								{
									textArea_1.append("Error: " + ex + "\n");
								}
							}
						}
					}
				});
		
		JButton btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setBounds(202, 166, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(334, 142, 36, 14);
		frame.getContentPane().add(lblKey);
		
		
		
		
		//be able to encrypt the message based on the cipher chosen by user
		btnNewButton_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (cipherSelected) {
					String plainText = textField_2.getText();
					String sendTo = (String) comboBox_1.getSelectedItem();
					plainText = plainText.replaceAll("\\p{Punct}","");
					
					if(comboBox.getSelectedItem().equals("RSA"))
					{
						try {
							byte[] plainTextBytes = plainText.getBytes();
							byte[] encryptedBytes = RSA.encrypt(RSAPubKey, plainTextBytes);
							String encryptedText = encryptedBytes.toString();			
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + encryptedText + ") to " + sendTo + "\n");
							pwSock.println("Message," + name + ",1," + sendTo + "," + encryptedText);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
						//calling the RSA class
						//RSA encrypt = new RSA(textField_2.getText());
						
					}
					else if(comboBox.getSelectedItem().equals("Stream Cipher"))
					{
						try {
					        Cipher cipher = Cipher.getInstance("CFB");
					        byte[] byteText = plainText.getBytes();
					        cipher.init(Cipher.ENCRYPT_MODE, streamKey);
					        byte[] byteCipherText = cipher.doFinal(byteText);
					        String cText = byteCipherText.toString();
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + cText + ") to " + sendTo + "\n");
					        pwSock.println("Message," + name + ",1," + sendTo + "," + cText);
					        //String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else if(comboBox.getSelectedItem().equals("Block Cipher"))
					{
				      
				        try {
					        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
					        byte[] byteText = plainText.getBytes();
					        cipher.init(Cipher.ENCRYPT_MODE, blockKey);
					        byte[] byteCipherText = cipher.doFinal(byteText);
					        String cText = byteCipherText.toString();
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + cText + ") to " + sendTo + "\n");
					        pwSock.println("Message," + name + ",1," + sendTo + "," + cText);
					        //String response = br.readLine();
							//textArea_1.append(response + "\n");
				        } catch (Exception ex) {
							ex.printStackTrace();
						}
				        
				    }
					else if(comboBox.getSelectedItem().equals("Monoalphabetic"))
					{
						try {
							//calling the Monoalphabetic class
							String cipherText = MonoCipher.encrypt(plainText);
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + cipherText + ") to " + sendTo + "\n");
							pwSock.println("Message," + name + ",1," + sendTo + "," +cipherText);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
					}
					else if(comboBox.getSelectedItem().equals("Vigenere"))
					{
						try {
							String cipherText = VigenereCipher.encrypt(plainText, vigenereKey);
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + cipherText + ") to " + sendTo + "\n");
							pwSock.println("Message," + name + ",1," + sendTo + ","+ cipherText);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
							//calling the Vigenere Class
							//VigenereCipher encrypt = new VigenereCipher(textField_2.getText());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} 
					else if (comboBox.getSelectedItem().equals("Hill Cipher")) {
						try {
							String key = getRandomString(plainText.length() * plainText.length());
							String cipherText = HillCipher.encrypt(key, plainText);
							textArea_1.append("Sent (PT: " + plainText + ", CT: " + cipherText + ") to " + sendTo + "\n");
							pwSock.println("Message," + name + "," + key + ",1," + sendTo + "," + cipherText);
							textField_4.setText(key);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {
				textArea_1.append("Please enable a cipher before sending a message\n");
			}
		} 
	});
		
		
		ActionListener refresh = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					if (connected && br.ready()) {
						String response = br.readLine();
						if (response.contains("New Entrant") || response.contains("Currently in")) {
							response = response.substring(response.indexOf(',') + 1);
							comboBox_1.addItem(response);
						} else {
							textArea_1.append(response + "\n");
						}
					}
					
				} catch (Exception e1) {
					System.out.println("Error Here" + e1);
			}
		}
			
	};
		Timer st = new Timer(1000, refresh);
		st.setRepeats(true);
		st.start();
		
		

		
	}
}
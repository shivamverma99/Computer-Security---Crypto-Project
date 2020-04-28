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
import javax.swing.JTextField;
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
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Alice", "Bob", "Dan"}));
		comboBox_1.setBounds(84, 167, 82, 22);
		frame.getContentPane().add(comboBox_1);
		
		
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBounds(10, 295, 18, -1);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Chat: ");
		lblNewLabel_7.setBounds(10, 195, 47, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("System Messages: ");
		lblNewLabel_8.setBounds(10, 394, 108, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setBounds(10, 220, 420, 163);
		frame.getContentPane().add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setWrapStyleWord(true);
		textArea_2.setLineWrap(true);
		textArea_2.setEditable(false);
		textArea_2.setBounds(10, 419, 420, 163);
		
		frame.getContentPane().add(textArea_2);
		
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
		
		
		JButton btnNewButton = new JButton("Enable");
		btnNewButton.setBounds(312, 98, 89, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Enable")) {
					String cipher = (String) comboBox.getSelectedItem();
					if (cipher.contains("RSA")) {
						KeyPair pair;;
						try {
							pair = RSA.createKeys();
							RSAPriKey = pair.getPrivate().getEncoded();
							RSAPubKey = pair.getPublic().getEncoded();
							String privateKey = RSAPriKey.toString();
							pwSock.println("Cipher," + cipher + "," + privateKey);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} else if (cipher.contains("Stream Cipher")) {
						 KeyGenerator key;
						try {
							key = KeyGenerator.getInstance("CFB");
							key.init(256);
							streamKey = key.generateKey();
							String keyString = Base64.getEncoder().encodeToString(streamKey.getEncoded());
							pwSock.println("Cipher," + cipher + "," + keyString);
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
							String response = "";
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
									btnNewButton_2.disable();
									
									
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
		
		ActionListener refresh = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String response = br.readLine();
					textArea_1.append(response + "\n");
				} catch (Exception e1) {
					
				}
			}
			
		};
		Timer st = new Timer(1000, refresh);
		
		

		
	}
}

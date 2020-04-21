import java.awt.BorderLayout;
import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
	static String[] threadNameType = new String[10];
	static int nameCounter = 0;
	
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
					while (true) {
						try {
							Socket sockNew = sock.accept();
							if (threadCounter == 10) {
								System.out.println("Too many members in chat");
							}
							else {
								clients[threadCounter] = new ServerThread(sockNew);
								clients[threadCounter].start();
								threadNameType[threadCounter] = clients[threadCounter].userNameAndType;
								threadCounter++;
							}
							for (int i = 0; i < threadCounter; i++) {
								if (clients[threadCounter].alert) {
									message = clients[threadCounter].message;
									dest = clients[threadCounter].destinations;
									sender = clients[threadCounter].name;
									for (int j = 0; j < dest.length; j++) {
										for (int k = 0; k < threadNameType.length; k++) {
											if (threadNameType[k].contains(dest[j])) {
												clients[k].setIncomingMessage(message, sender);
											}
										}
									}
								}
							}
						} catch (Exception e) {
							System.out.println("error: " + e);
						}
					}
				}
				catch (Exception e){
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
		
		JLabel lblCipherType = new JLabel("Cipher Type:");
		
		JLabel lblCipher = new JLabel("Cipher");
		
		JLabel lblKeyType = new JLabel("Key Type:");
		
		JLabel lblKey = new JLabel("Key");
		
		JLabel lblAttackerMode = new JLabel("Attacker Mode:");
		
		JLabel lblAttacker = new JLabel("Attacker");
		
		JLabel lblPeopleInChat = new JLabel("People In Chat");
		
		JTextArea txtChatMembers = new JTextArea();
		txtChatMembers.setEditable(false);
		for (int i = 0; i < threadCounter; i++) {
			txtChatMembers.append(threadNameType[i]);
		}
		JLabel lblChatMessages = new JLabel("Chat Messages");
		
		JTextArea txtChat = new JTextArea();
		txtChat.setEditable(false);
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
						.addComponent(txtChat, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChatMessages))
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
						.addComponent(lblChatMessages))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtChat, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
						.addComponent(txtChatMembers, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		
		
	}
}

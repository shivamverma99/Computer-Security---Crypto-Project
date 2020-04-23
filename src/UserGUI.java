import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class UserGUI{ //extends Client implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 427, 513);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("System IP: ");
		lblNewLabel.setBounds(10, 11, 81, 20);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(84, 11, 81, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("System Port: ");
		lblNewLabel_1.setBounds(10, 42, 81, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(94, 39, 81, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Encryption Method: ");
		lblNewLabel_2.setBounds(10, 83, 139, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"RSA", "Stream Cipher", "Block Cipher", "Monoalphabetic", "Vigenere"}));
		comboBox.setBounds(146, 79, 139, 22);
		frame.getContentPane().add(comboBox);
		
		
		JButton btnNewButton = new JButton("Enable");
		btnNewButton.setBounds(311, 79, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Message: ");
		lblNewLabel_3.setBounds(10, 142, 81, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("Encrypt");
		btnNewButton_1.setBounds(267, 138, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		//be able to encrypt the message based on the cipher choosen by user
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(comboBox.getSelectedItem().equals("RSA"))
				{
					//calling the RSA class
					//RSA encrypt = new RSA(textField_2.getText());
					
				}
				else if(e.getSelectedItem().equals("Stream Cipher"))
				{
					//calling the Stream Cipher class
					//StreamCipher encrypt = new StreamCipher(textField_2.getText());
				}
				else if(e.getSelectedItem().equals("Block Cipher"))
				{
					//calling the Block cipher class
					//BlockCipher encrypt = new BlockCipher(textField_2.getText());
				}
				else if(e.getSelectedItem().equals("Monoalphabetic"))
				{
					//calling the Monoalphabetic class
				}
				else if(e.getSelectedItem().equals("Vigenere"))
				{
					//calling the Vignere Class
					//VignereCipher encrypt = new VignereCipher(textField_2.getText());
				}
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setBounds(68, 139, 150, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		//Connect to port and IP address
		JButton btnNewButton_2 = new JButton("Connect");
		btnNewButton_2.setBounds(237, 10, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		/*btnNewButton_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(e.getActionCommand().equals("Connect"))
				{
					try
					{
						int port = Integer.parseInt(textField.getText());
						Client c = new Client(textField_1.getText(), port);
					}
					catch( Exception ex)
					{
						textArea.append("Error: " + ex);
					}
				}
			}
		});*/

		JLabel lblNewLabel_4 = new JLabel("Encyrpted Message: ");
		lblNewLabel_4.setBounds(10, 183, 125, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(130, 178, 183, 22);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel_5 = new JLabel("Send To: ");
		lblNewLabel_5.setBounds(10, 225, 81, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Alice", "Bob", "Dan"}));
		comboBox_1.setBounds(83, 221, 82, 22);
		frame.getContentPane().add(comboBox_1);
		
		JButton btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setBounds(205, 221, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBounds(10, 295, 18, -1);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Chat: ");
		lblNewLabel_7.setBounds(10, 284, 47, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("System Messages: ");
		lblNewLabel_8.setBounds(205, 284, 108, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 305, 183, 163);
		frame.getContentPane().add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(205, 305, 183, 163);
		frame.getContentPane().add(textArea_2);
	}

}

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class AttackerGUI {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=-338,350
	 */
	private final JTextField textField = new JTextField();
	private JTextField textField_1;
	private JTextField textField_2;
	private final JLabel lblNewLabel_3 = new JLabel("New label");
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttackerGUI window = new AttackerGUI();
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
	public AttackerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 384, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("System IP: ");
		lblNewLabel.setBounds(10, 11, 76, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("System Port:");
		lblNewLabel_1.setBounds(10, 36, 87, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(79, 11, 99, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(89, 33, 42, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(225, 10, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Attacker Mode:");
		lblNewLabel_2.setBounds(10, 79, 99, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Ciphertext-Only:", "Known-Plaintext: ", "Chosen-Ciphertext", "Chosen-Plaintext: "}));
		comboBox.setSelectedIndex(1);
		comboBox.setToolTipText("dads\r\n");
		comboBox.setBounds(102, 75, 129, 22);
		frame.getContentPane().add(comboBox);
		lblNewLabel_3.setBounds(114, -40, 148, 40);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("Enable");
		btnNewButton_1.setBounds(258, 75, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("Chosen Text:");
		lblNewLabel_4.setBounds(10, 117, 87, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(82, 114, 96, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Toolbox:");
		lblNewLabel_5.setBounds(10, 160, 76, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Bruteforce", "Frequency Analysis"}));
		comboBox_1.setBounds(91, 156, 110, 22);
		frame.getContentPane().add(comboBox_1);
		
		JButton btnNewButton_2 = new JButton("Use Tool");
		btnNewButton_2.setBounds(211, 156, 87, 21);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_6 = new JLabel("Toolbox Data:");
		lblNewLabel_6.setBounds(10, 203, 87, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(20, 222, 327, 103);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel_7 = new JLabel("System Messages: ");
		lblNewLabel_7.setBounds(10, 340, 121, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(20, 356, 327, 113);
		frame.getContentPane().add(textArea_1);

		//TODO: work out spacing between fields and buttons
		//label for info given by server (such as plaintext received)
		JLabel serverOutput = new JLabel("");
		serverOutput.setBounds(20,340,121,14);
		//
		JTextField input = new JTextField("User Input: Type in guess or cipher/plain text for attack");
		input.setBounds(10,380,200,30);
		input.setEditable(true);

		JButton attackButton = new JButton("Attack");
		attackButton.setBounds(267, 138, 89, 23);
		frame.getContentPane().add(attackButton);
		attackButton.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent e)
		{
			if(comboBox.getSelectedItem().equals("Ciphertext-Only:"))
			{
				//simply print out any ciphertext given by server
				serverOutput.setText(Attacker.ciphertextOnly());
			}
			else if(comboBox.getSelectedItem().equals("Known-Plaintext: "))
			{
				serverOutput.setText(Attacker.knownPlaintext());
			}
			else if(comboBox.getSelectedItem().equals("Chosen-Ciphertext"))
			{
				serverOutput.setText(Attacker.chosenCiphertext(input.getText()));
			}
			else if(comboBox.getSelectedItem().equals("Chosen-Plaintext: "))
			{
				serverOutput.setText(Attacker.chosenPlaintext(input.getText()));
			}
		}});
		JButton guessButton = new JButton("Guess");
		attackButton.setBounds(350, 138, 89, 23);
		frame.getContentPane().add(guessButton);
		guessButton.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent e)
		{
			serverOutput.setText(Attacker.guess(input.getText()));
		}});



	/*public static void main(String[] args) {

		String attackType = "";
		String attackInputs = "";

		System.out.println("Please choose the attack you want to use.\nInput 1, 2, 3, 4, or quit.\nYou can go back at any time by typing 'BACK'");
		System.out.print("1. Cipher-Text Only\n2. Known Plain-Text\n3. Chosen Plain-Text\n4. Chosen Cipher-Text\n-> ");
		Scanner type = new Scanner(System.in);
		attackType = type.nextLine();

		if (attackType.equals("1")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.println(ciphertextOnly());
					//Ask server for array of cipher-texts
				}
				else if (attackInputs.equals("2")) {

					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}

			}
		}
		else if (attackType.equals("2")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.println(knownPlaintext());
					//Ask server for array of cipher-texts
				}
				else if (attackInputs.equals("2")) {

					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}

			}
		}
		else if (attackType.equals("3")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.print("Type your plaint-text -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(chosenPlaintext(attackInputs));
					//Input plain-text, and get back cipher-text
				}
				else if (attackInputs.equals("2")) {

					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}
			}

		}
		else if (attackType.equals("4")) {
			while(!attackInputs.equals("QUIT")) {
				System.out.println("Type 1 to attack, 2 to guess, or QUIT to quit");
				Scanner input = new Scanner(System.in);
				attackInputs = input.nextLine();
				if (attackInputs.equals("1")) {
					System.out.print("Type your cipher-text -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(chosenCiphertext(attackInputs));
					//Input cipher-text, and get back plain-text
				}
				else if (attackInputs.equals("2")) {

					System.out.print("What is Your Guess -> ");
					input = new Scanner(System.in);
					attackInputs = input.nextLine();
					System.out.println(guess(attackInputs));
					//GUESS ENC/DEC TYPE
				}

			}

		}

		System.out.print("GoodBye");
	}*/
	}
}

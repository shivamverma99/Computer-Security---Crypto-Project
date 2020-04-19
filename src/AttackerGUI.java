import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
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
		
		
	}
}

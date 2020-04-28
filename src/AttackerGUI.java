import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class AttackerGUI {

    private JFrame frame;
    /**
     * @wbp.nonvisual location=-338,350
     */
    private final JTextField textField = new JTextField();

    private final JLabel lblNewLabel_3 = new JLabel("New label");
    Socket sock;
    PrintWriter pwSock; //For the socket I/O
    BufferedReader br;
    private JTextField textField_3;
    String name, mode;
    boolean connected, attackMode = false, victimSelected = false;

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
        frame.setBounds(100, 100, 384, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("System IP: ");
        lblNewLabel.setBounds(10, 36, 76, 21);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("System Port:");
        lblNewLabel_1.setBounds(10, 68, 87, 14);
        frame.getContentPane().add(lblNewLabel_1);

        JTextField textField_1 = new JTextField();
        textField_1.setBounds(71, 36, 99, 20);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JTextField textField_2 = new JTextField();
        textField_2.setBounds(107, 65, 42, 20);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JButton connectBtn = new JButton("Connect");
        connectBtn.setBounds(225, 48, 89, 23);
        frame.getContentPane().add(connectBtn);

        JLabel lblNewLabel_2 = new JLabel("Attacker Mode:");
        lblNewLabel_2.setBounds(10, 128, 99, 14);
        frame.getContentPane().add(lblNewLabel_2);

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Ciphertext-Only:", "Known-Plaintext: ", "Chosen-Ciphertext", "Chosen-Plaintext: "}));
        comboBox.setSelectedIndex(0);
        comboBox.setToolTipText("dads\r\n");
        comboBox.setBounds(100, 124, 129, 22);
        frame.getContentPane().add(comboBox);
        lblNewLabel_3.setBounds(114, -40, 148, 40);
        frame.getContentPane().add(lblNewLabel_3);

        JButton attackButton = new JButton("Attack");
        attackButton.setBounds(258, 124, 89, 23);
        frame.getContentPane().add(attackButton);

        JLabel inputLbl = new JLabel("Chosen Text:");
        inputLbl.setBounds(10, 153, 160, 14);
        frame.getContentPane().add(inputLbl);

        JTextField input = new JTextField();
        input.setBounds(107, 153, 150, 20);
        frame.getContentPane().add(input);
        input.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Tool Kit");
        lblNewLabel_5.setBounds(10, 178, 76, 14);
        frame.getContentPane().add(lblNewLabel_5);

        //JComboBox comboBox_1 = new JComboBox();
        //comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Bruteforce", "Frequency Analysis"}));
        //comboBox_1.setBounds(91, 156, 110, 22);
        //frame.getContentPane().add(comboBox_1);

        JButton freqButton = new JButton("Frequency Analysis");
        freqButton.setBounds(100, 175, 200, 21);
        frame.getContentPane().add(freqButton);
	
	JButton bruteforceButton = new JButton("Brute Force");
        bruteforceButton.setBounds(300, 175, 200, 21);
        frame.getContentPane().add(bruteforceButton);
	
        JLabel lblNewLabel_6 = new JLabel("Toolbox Data:");
        lblNewLabel_6.setBounds(10, 203, 87, 14);
        frame.getContentPane().add(lblNewLabel_6);

        JTextArea textArea = new JTextArea();
        //textArea.setBounds(20, 222, 327, 103);
        //frame.getContentPane().add(textArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 222, 327, 103);
		frame.getContentPane().add(scrollPane);

        JLabel serverOutputlbl = new JLabel("System Messages: ");
        serverOutputlbl.setBounds(10, 340, 121, 14);
        frame.getContentPane().add(serverOutputlbl);

        JTextArea serverOutput = new JTextArea();
        serverOutput.setWrapStyleWord(true);
        serverOutput.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(serverOutput);
		scrollPane1.setBounds(20, 356, 327, 113);
		frame.getContentPane().add(scrollPane1);  
        //frame.getContentPane().add(serverOutput);

        JLabel guessLbl = new JLabel("Enter Guess Below: ");
        guessLbl.setBounds(10, 480, 121, 14);
        frame.getContentPane().add(guessLbl);

        JTextArea guessArea = new JTextArea();
        JScrollPane scrollPane2 = new JScrollPane(textArea);
		scrollPane2.setBounds(20, 500, 327, 113);
		frame.getContentPane().add(scrollPane2);
        //frame.getContentPane().add(guessArea);

        JButton guessButton = new JButton("Guess");
        guessButton.setBounds(130, 620, 81, 25);
        frame.getContentPane().add(guessButton);
        
        JLabel lblEnterNameHere = new JLabel("Enter Name Here:");
        lblEnterNameHere.setBounds(10, 11, 121, 14);
        frame.getContentPane().add(lblEnterNameHere);
        
        textField_3 = new JTextField();
        textField_3.setBounds(124, 8, 86, 20);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);
        
        JLabel lblChooseVictim = new JLabel("Choose Victim");
        lblChooseVictim.setBounds(10, 93, 87, 14);
        frame.getContentPane().add(lblChooseVictim);
        
        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(91, 93, 99, 20);
        frame.getContentPane().add(comboBox_1);
        
        JButton btnChoose = new JButton("Choose");
        btnChoose.setBounds(209, 90, 89, 23);
        frame.getContentPane().add(btnChoose);
        
        JButton btnAskOracle = new JButton("Ask Oracle");
        btnAskOracle.setBounds(268, 149, 89, 23);
        frame.getContentPane().add(btnAskOracle);
        btnAskOracle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!attackMode) {
					serverOutput.append("First choose an attacker mode\n");
				} else {
					String oracleText = input.getText();
					if (!(oracleText.length() > 0)) {
						serverOutput.append("Please type what to send to the oracle first.");
					} else {
						if (mode.contains("Chosen Ciphertext")) {
							pwSock.println("Oracle," + oracleText);
						} else if (mode.contains("Chosen Plaintext")) {
							pwSock.println("Oracle," + oracleText);
						}
					}
				}
			}
        	
        });
        
        btnChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String victim = (String)comboBox_1.getSelectedItem();
				victimSelected = true;
				pwSock.println("Conversation," + victim);
			}
        	
        });

        //TODO: work out spacing between fields and buttons
        //label for info given by server (such as plaintext received)
        // JLabel serverOutput = new JLabel("");
        // serverOutput.setBounds(10, 100, 121, 14);
        //frame.getContentPane().add(serverOutput);
        //
        // JTextField input = new JTextField("User Input: Type in guess or cipher/plain text for attack");
        //input.setBounds(10, 200, 200, 30);
        //input.setEditable(true);
        // frame.getContentPane().add(input);
        //TODO: do we need a connect button?
        connectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Connect")) {
                	if (!(textField_3.getText().length() > 0)) {
						serverOutput.append("First enter a name, then connect\n");
					} else {
						try {

							name = textField_3.getText();
							int port = Integer.parseInt(textField_2.getText());
							sock = new Socket(textField_1.getText(), port); 
							br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
							pwSock = new PrintWriter(sock.getOutputStream(), true);
							pwSock.println("Attacker," + name);
							//String response = br.readLine();
							//textArea_1.append(response + "\n");
							connectBtn.setEnabled(false);;
							connected = true;
	                        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	                        pwSock = new PrintWriter(sock.getOutputStream(), true);
	                        //Attacker.Client(ip,port,data)
	                        //Client c = new Client(textField_1.getText(), port);
	                        //Attacker.Client(textField_1.getText(),port,"");
                    } catch (Exception ex) {
                        serverOutput.append("Error: " + ex + "\n");
                    }
                }
            }
            }
        });

        //freq button press
        freqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //serverOutput.setText(Attacker.frequencyAnalysis());
                pwSock.println("Tool,Frequency Analysis");
            }
        });
        attackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (!victimSelected) {
            		serverOutput.append("Please choose a victim(conversation) to listen to first");
            	} else {
	            	attackMode = true;
	                if (comboBox.getSelectedItem().equals("Ciphertext-Only:")) {
	                    //simply print out any ciphertext given by server
	                	mode = "Ciphertext Only";
	                	input.setEnabled(false);
	                    //serverOutput.setText(Attacker.ciphertextOnly());
	                    pwSock.println("Mode,Ciphertext Only");
	                } else if (comboBox.getSelectedItem().equals("Known-Plaintext: ")) {
	                	mode = "Known Plaintext";
	                	input.setEnabled(false);
	                    //serverOutput.setText(Attacker.knownPlaintext());
	                    pwSock.println("Mode,Known Plaintext");
	                } else if (comboBox.getSelectedItem().equals("Chosen-Ciphertext")) {
	                	mode = "Chosen Ciphertext";	
	                    //serverOutput.setText(Attacker.chosenCiphertext(input.getText()));
	                    pwSock.println("Mode,Chosen Ciphertext");
	                	
	                } else if (comboBox.getSelectedItem().equals("Chosen-Plaintext: ")) {
	                	mode = "Chosen Plaintext";
	                	pwSock.println("Mode,Chosen Plaintext");
	                    //serverOutput.setText(Attacker.chosenPlaintext(input.getText()));
	                	
	                }
            	}
            }
        });
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //serverOutput.setText(Attacker.guess(guessArea.getText()));
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
							serverOutput.append(response + "\n");
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

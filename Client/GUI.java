package Client;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 * this class acts as the client of the game and is what the user interacts with to send
 * the move to the controller
 * @author Leon
 */
public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextArea textField_2;
	
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JButton btnNewButton_9;
	
	private Socket aSocket;
	private String Mark;
	private String opponentMark;
	private String state;
	
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public static void main(String[] args) {
		GUI test = new GUI();
	}
	/**
	 * this is the default constructor for class GUI and first prompts the user to enter their name before
	 * displaying the GUI 
	 */
	public GUI() {
		
		String playerName;
		playerName = JOptionPane.showInputDialog("Enter your name.");
		
		
		setTitle("Tic-Tac-Toe Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Player:");
		lblNewLabel.setBounds(266, 22, 41, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(319, 17, 31, 26);
		contentPane.add(textField);
		textField.setColumns(10);
	
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(266, 50, 61, 16);
		contentPane.add(lblName);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(319, 45, 158, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(playerName);
		
		JLabel lblMessageWindow = new JLabel("Message Window:");
		lblMessageWindow.setBounds(266, 83, 124, 16);
		contentPane.add(lblMessageWindow);
		
		
		textField_2 = new JTextArea();
		textField_2.setEditable(false);
		textField_2.setLineWrap(true);
		textField_2.setWrapStyleWord(true);
		textField_2.setBounds(266, 99, 211, 182);
		contentPane.add(textField_2);
		textField_2.setColumns(100);
		textField_2.setText(playerName + ", welcome to the game!");
		
		
		JPanel panel = new JPanel();
		panel.setBounds(19, 22, 225, 259);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_3,0,2);
			}
		});
		
		btnNewButton_1 = new JButton("");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_1,0,0);
			}
		});
		
		btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_2,0,1);
			}
		});
		panel.add(btnNewButton_2);
		panel.add(btnNewButton_3);
		
		btnNewButton_9 = new JButton("");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_9,2,2);
			}
		});
		
		btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_4,1,0);
			}
		});
		panel.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_5,1,1);
			}
		});
		panel.add(btnNewButton_5);
		
		btnNewButton_6 = new JButton("");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_6,1,2);
			}
		});
		panel.add(btnNewButton_6);
		
		btnNewButton_7 = new JButton("");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_7,2,0);
			}
		});
		panel.add(btnNewButton_7);
		
		btnNewButton_8 = new JButton("");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check(btnNewButton_8,2,1);

			}
		});
		panel.add(btnNewButton_8);
		panel.add(btnNewButton_9);
				
		
		setVisible(true);
		try {
			aSocket = new Socket("localhost", 9898);
			socketOut = new PrintWriter((this.aSocket.getOutputStream()), true);
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			
			getMarksFromServer();
			setEnabled(false);
			state = "waiting";
			
			play();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * this method receives the GUI mark from the server 
	 * @throws IOException
	 */
	public void getMarksFromServer() throws IOException {
		Mark = socketIn.readLine();
		textField.setText(Mark);
		
		if (Mark.equals("X")) 
			opponentMark = "O";
		else
			opponentMark = "X";
		
	}
	
	/**
	 * this method receives the commands from the game and sets the GUI to enabled or disabled depending on if 
	 * they receive the command PLAY or the command WAIT
	 * @throws IOException
	 */
	public void play() throws IOException {
		String input = socketIn.readLine(); //you have connected successfully with another player
		textField_2.setText(input);
		
		while(true) {
			input = socketIn.readLine(); //play or wait
			
			if (input.equals("PLAY")) {
				state = "playing";
				setEnabled(true);
				textField_2.setText("Please make your move!"); //please make your move
				
				while(true) {
					state = getS();
					if (!state.equals("playing")) 
						break;
				}
				
				
			}
			else if (input.equals("WAIT")) {
				setEnabled(false);
				textField_2.setText("Please wait while your opponent makes their move!"); //please wait while opp makes move
				
				input = socketIn.readLine(); //reads in the opponents move and maps to board
				markOpponentMove(input);
				
			}
			
			else  {
				setEnabled(false);
				break;
			}
		}
		
		input = socketIn.readLine();
		textField_2.setText(input);
		
		socketIn.close();
		socketOut.close();
		
	}
	
	/**
	 * this method marks the board on the GUI depending on the input received which represents a row and column number
	 * @param input this parameter is the input that represents a row and column number
	 */
	public void markOpponentMove(String input) {
		
		switch (input) { 
	        case "0 0": 
	        	btnNewButton_1.setText(opponentMark);
	            break; 
	        case "0 1": 
	        	btnNewButton_2.setText(opponentMark);
	            break; 
	        case "0 2": 
	        	btnNewButton_3.setText(opponentMark);
	            break; 
	        case "1 0": 
	        	btnNewButton_4.setText(opponentMark);
	            break; 
	        case "1 1": 
	        	btnNewButton_5.setText(opponentMark);
	            break; 
	        case "1 2": 
	        	btnNewButton_6.setText(opponentMark);
	            break; 
	        case "2 0": 
	        	btnNewButton_7.setText(opponentMark);
	            break; 
	        case "2 1": 
	        	btnNewButton_8.setText(opponentMark);
	            break; 
	        case "2 2": 
	        	btnNewButton_9.setText(opponentMark);
	            break; 
        } 
		
		
	}
	
	
	/**
	 * The method check is invoked every time a button has been pressed by the user to determine what should be
	 * displayed by the GUI  
	 * @throws IOException **/
	public void check(JButton button, int row, int col) {
		socketOut.println(row + " " + col); //goes to string move
		
		String input;
		try {
			input = socketIn.readLine();

			if (input.equals("MARK")) {
				button.setText(Mark);
				state = "moveMade";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //mark
		
	}
	
	/**
	 * this method returns the state of the GUI 
	 * @return String, this string represents the state of the GUI
	 */
	public String getS() {
		return state;
	}
}

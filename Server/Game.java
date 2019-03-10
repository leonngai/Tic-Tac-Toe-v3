package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;



/**
 * this class acts as the controller in the system and takes inputs from the GUIs and 
 * passes that to the board class to mark down the moves made by the players 
 * @author Leon
 */
public class Game implements Constants, Runnable {

	private Board theBoard;
	private Socket socketX;
	private Socket socketO;
	private String currentPlayer;
	private BufferedReader inX;
	private BufferedReader inO;
	private PrintWriter outX;
	private PrintWriter outO;

	/**
	 * this is the constructor for class game which creates a new instance of a board and assigns the two sockets to the 
	 * socket member variables 
	 * @param playerX this variable is the socket for player X
	 * @param playerO this variable is the socket for player O 
	 */
	public Game(Socket playerX, Socket playerO) { // throws IOException {
		try {
			theBoard  = new Board();
			
			socketX = playerX;
			socketO = playerO;
			
			inX = new BufferedReader(new InputStreamReader(socketX.getInputStream()));
			outX = new PrintWriter((socketX.getOutputStream()), true);
			
			inO = new BufferedReader(new InputStreamReader(socketO.getInputStream()));
			outO = new PrintWriter((socketO.getOutputStream()), true);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

    
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * the run method for this runnable class is what controls the game. it constantly waits for the GUI to send their moves
	 * to this class and acts accordingly by marking the moves down and then sending messages back to the GUI classes 
	 */
	@Override
	public void run() {
		outX.println("You have connected successfully with another player and the game will start now.");
		outO.println("You have connected successfully with another player and the game will start now.");
		
		currentPlayer = "X";
		
		while(!theBoard.xWins() && !theBoard.oWins() && !theBoard.isFull()) {
			try {
				if (currentPlayer.equals("X")) {
					outX.println("PLAY");
					outO.println("WAIT");
				
					String move = inX.readLine(); //socketout print row and col
					
					while(true) {
						String[] splitted = move.split("\\s+");
						String validation = validateMove(splitted[0], splitted[1], 'X');
						
						if (validation.equals("VALID")) {
							outX.println("MARK");
							outO.println(move);
							break;
						}
						else {
							outX.println("NO MARK");
							move = inX.readLine();
						}
					}
					
					
					
					currentPlayer = "O";
				}
				else {
					outO.println("PLAY");
					outX.println("WAIT");
					
					
					String move = inO.readLine();
					while(true) {
						String[] splitted = move.split("\\s+");
						String validation = validateMove(splitted[0], splitted[1], 'O');
						
						if (validation.equals("VALID")) {
							outO.println("MARK");
							outX.println(move);
							break;
						}
						else {
							outO.println("NO MARK");
							move = inO.readLine();
						}
					}
					
					currentPlayer = "X";
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		outX.println("END");
		outO.println("END");
		
		if (theBoard.xWins()) {
			outX.println("Congratulations, you have won the game!");
			outO.println("You have lost the game, better luck next time!");
		}
		else if (theBoard.oWins()) {
			outO.println("Congratulations, you have won the game!");
			outX.println("You have lost the game, better luck next time!");
		}
		else {
			outX.println("The game ended in a tie!");
			outO.println("The game ended in a tie!");
		}
		
		
	}
	
	/**
	 * this method validates the move that was made by the user by checking if the space is free 
	 * @param row this represents the row of the move that was made by the user
	 * @param col this represents the column of the move that was made by the user
	 * @param mark this represents the mark 
	 * @return String, this method returns VALID if it was a valid move and INVALID if it was not
	 */
	public String validateMove(String row, String col, char mark) {
		int rowNumber = Integer.parseInt(row);
		int colNumber = Integer.parseInt(col);
		
		if (theBoard.getMark(rowNumber, colNumber) == SPACE_CHAR) {
			theBoard.addMark(rowNumber, colNumber, mark);
			
			return "VALID";
		}
		else {
			return "INVALID";
		}
	}
	
}

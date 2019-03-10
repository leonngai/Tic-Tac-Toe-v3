package Server;
/**
 * @author Leon
 * the Board class keeps track of where the moves are marked by the client and 
 * includes functionality to check if the X player or O player won and if the game ended
 * in a tie
 */
public class Board implements Constants {
	private char theBoard[][];
	private int markCount;

	/**
	 * This is the default constructor for the Class Board
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * this method returns the mark that is currently on the board given the row and column number
	 * @param row this represents the row number
	 * @param col this represents the column number
	 * @return char, this method would return the mark whether it is an X, O or space character
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * 	this method checks if the board is full of marks or not
	 * @return boolean, this returns true if the board is full and false if the board still has free spaces
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * this method passes X to the method check winner where check winner will pass back an integer to represent if they have won
	 * @return boolean, returns true if X player has won, false if they have not
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	
	/**
	 * this method passes O to the method check winner where check winner will pass back an integer to represent if they have won
	 * @return boolean, returns true if O player has won, false if they have not
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	
	/**
	 * this method adds the mark onto the board
	 * @param row this represents the row number that the mark will be placed on 
	 * @param col this represents the column number that the mark will be placed on 
	 * @param mark this represents the mark that will be placed onto the board
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	
	/**
	 * this method checks if there is a winner on the board, either vertically, horizontally or diagonally
	 * @param mark this is the mark that the method checks for that has won
	 * @return int, this method will return 1 if there is a winner and 0 if there is no winner
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
}

package application;

import java.util.ArrayList;

/* This class will represent the game board
 * 
 * */
public abstract class Board {
	
	//The game board will be represented as a 2D char array that will consist of S's and O's.
	private char[][] sosBoard;
	
	//This is the size of the game board;
	private int sizeOfBoard;
	private final int MIN_BOARD_SIZE = 3;
	private final int MAX_BOARD_SIZE = 12; 
	
	//This will keep track of the who the current player is. Blue will get to go first.
	protected String currentPlayer = "Blue";
	
	//Boolean for determining if the game is over.
	protected boolean gameIsOver = false;
	
	//Boolean for determining if a game ends in a draw.
	protected boolean drawGame = false;
	
	protected String winner = "";
	
	//There may be a case where 2 solutions are found in one turn. We'll store the solutions in an ArrayList.
	protected ArrayList<Directions> solutions = new ArrayList<Directions>();
	
	//Default constructor used for testing purposes.
	public Board() {
		sizeOfBoard = 3;
		sosBoard = new char [sizeOfBoard][sizeOfBoard];
		
		//Initialize the board so that all spaces are blank
		for(int i = 0; i < sizeOfBoard; i++) {
			for(int j = 0; j < sizeOfBoard; j++) {
				sosBoard[i][j] = ' ';
			}
		}
	} 
	
	//Constructor that takes board size as a parameter.
	public Board(int size) {
		//Check to see if the size is valid.
		if(size <= MIN_BOARD_SIZE - 1 || size > MAX_BOARD_SIZE) {
			size = 3;
		}
		
		//When a board gets initialized, make all the spaces blank.
	    sizeOfBoard = size;
		sosBoard = new char [sizeOfBoard][sizeOfBoard];
		
		//Initialize the board so that all spaces are blank
		for(int i = 0; i < sizeOfBoard; i++) {
			for(int j = 0; j < sizeOfBoard; j++) {
				sosBoard[i][j] = ' ';
			}
		}
	}
	
	//Virtual method for processing a turn depending on what game mode is selected.
	public abstract void makeMove(int row, int col, char startingPiece);
	
	//Check for a solution in every direction possible, store each solution in the ArrayList.
	protected void checkForSOS(int row, int col, char startingPiece) {
		//int direction = -1;
		
		/* If the player placed an S, then there are 8 directions we must check for a possible SOS sequence.
		 * The directions, clockwise, are top, top-right, left, bottom-left, bottom, bottom-right, right, and top-right.
		 */
		if(startingPiece == 'S') {
				
			if(getCellValue(row - 1, col) != '-' && getCellValue(row - 1, col) == 'O') { 
				if(getCellValue(row - 2, col) != '-' && getCellValue(row - 2, col) == 'S') {
					solutions.add(Directions.TOP);
							
				}
			}
			
			if(getCellValue(row - 1, col + 1) != '-' && getCellValue(row - 1, col + 1) == 'O') {
				if(getCellValue(row - 2, col + 2) != '-' && getCellValue(row - 2, col + 2) == 'S') {
					solutions.add(Directions.TOP_RIGHT);
							
				}
			}
			
			if(getCellValue(row, col + 1) != '-' && getCellValue(row, col + 1) == 'O') {
				if(getCellValue(row, col + 2) != '-' && getCellValue(row, col + 2) == 'S') {
					solutions.add(Directions.RIGHT);
						
				}
			}
			
			if(getCellValue(row + 1, col + 1) != '-' && getCellValue(row + 1, col + 1) == 'O') {
				if(getCellValue(row + 2, col + 2) != '-' && getCellValue(row + 2, col + 2) == 'S') {
					solutions.add(Directions.BOTTOM_RIGHT);
							
				}
			}
			
			if(getCellValue(row + 1, col) != '-' && getCellValue(row + 1, col) == 'O') {
				if(getCellValue(row + 2, col) != '-' && getCellValue(row + 2, col) == 'S') {
					solutions.add(Directions.BOTTOM);
							
				}
			}
			
			if(getCellValue(row + 1, col - 1) != '-' && getCellValue(row + 1, col - 1) == 'O') {
				if(getCellValue(row + 2, col - 2) != '-' && getCellValue(row + 2, col - 2) == 'S') {
					solutions.add(Directions.BOTTOM_LEFT);
							
				}
			}
			
			if(getCellValue(row, col - 1) != '-' && getCellValue(row, col - 1) == 'O') {
				if(getCellValue(row, col - 2) != '-' && getCellValue(row, col - 2) == 'S') {
					solutions.add(Directions.LEFT);
							
				}
			}
			
			if(getCellValue(row - 1, col - 1) != '-' && getCellValue(row - 1, col - 1) == 'O') {
				if(getCellValue(row - 2, col - 2) != '-' && getCellValue(row - 2, col - 2) == 'S') {
					solutions.add(Directions.TOP_LEFT);
							
				}
			}
				
				
		/* If the player placed an O piece, then there are only 4 ways to check for an SOS sequence.
		 * The directions are top and bottom, top-right and bottom-left, left and right, and bottom-right and top-left
		 */
		}else if(startingPiece == 'O') {
			
			if(getCellValue(row - 1, col) != '-' && getCellValue(row - 1, col) == 'S') {
				if(getCellValue(row + 1, col) != '-' && getCellValue(row + 1, col) == 'S'){
					solutions.add(Directions.TOP);
				}
			} 
			
			if(getCellValue(row - 1, col + 1) != '-' && getCellValue(row - 1, col + 1) == 'S') {
				if(getCellValue(row + 1, col - 1) != '-' && getCellValue(row + 1, col - 1) == 'S') {
					solutions.add(Directions.TOP_RIGHT);
				}
				
			}
			
			if(getCellValue(row, col + 1) != '-' && getCellValue(row, col + 1) == 'S') {
				if(getCellValue(row, col - 1) != '-' && getCellValue(row, col - 1) == 'S') {
					solutions.add(Directions.RIGHT);
				}
				
			}
			
			if(getCellValue(row + 1, col + 1) != '-' && getCellValue(row + 1, col + 1) == 'S') {
				if(getCellValue(row - 1, col - 1) != '-' && getCellValue(row - 1, col - 1) == 'S') {
					solutions.add(Directions.BOTTOM_RIGHT);
							
				}
			}
			
		}
		
		//return direction;
	}
	
	//Method that makes a move from the button that the player clicked on.
	protected boolean placePieceOnBoard(int row, int col, char piece) {
		boolean sucessfullyMadeMove = false;
		
		//Make sure the row and column index are in bounds.
		if(row >= 0 && row < sizeOfBoard && col >= 0 && col < sizeOfBoard ) {
			
			//Make sure the space is blank before placing the piece
			if(sosBoard[row][col] == ' ') {
				sosBoard[row][col] = piece;
				sucessfullyMadeMove = true;
				 
			}
					
		}
		
		return sucessfullyMadeMove; 
		
	}
	
	//Check to see if every space is filled up
	public boolean isBoardFull() {
		int spaceCount = 0;
		for(int i = 0; i < sizeOfBoard; i++) {
			for(int j = 0; j < sizeOfBoard; j++) {
				if(sosBoard[i][j] == 'S' || sosBoard[i][j] == 'O') {
					spaceCount++;
				}
			}
		}
		
		
		if(spaceCount == sizeOfBoard * sizeOfBoard) {
			return true;
		}else {
			return false;
		}
	}
	
	//Method for clearing the board
	public void clearBoard() {
		currentPlayer = "Blue";
		gameIsOver = false;
		drawGame = false;
		//sizeOfBoard = 3;
		
		for(int i = 0; i < sizeOfBoard; i++) {
			for(int j = 0; j < sizeOfBoard; j++) {
				sosBoard[i][j] = ' ';
			}
		}
		
	}
	

	//Returns the value at a given cell in the game board.
	public char getCellValue(int row, int col) {
		if(row >= 0 && row < sizeOfBoard && col >= 0 && col < sizeOfBoard ) { 
			return sosBoard[row][col];
		}else {
			return '-';
		}
		
	}
	
	//Prints the board, blanks are shown as underscores
	public void printBoard() {
		for(int i = 0; i < sizeOfBoard; i++) {
			for(int j = 0; j < sizeOfBoard; j++) {
				if(sosBoard[i][j] == ' ') {
					System.out.print('_' + " ");
				}else {
					System.out.print(sosBoard[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//Returns the currentPlayer
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	//Returns the size of the board.
	public int getBoardSize() {
		return sizeOfBoard;
	}
	
	
	public boolean isGameOver() {
		return gameIsOver;
	}
	
	public boolean isDrawGame() {
		return drawGame;
	}
	
	public String getWinner() {
		return winner;
	}


}

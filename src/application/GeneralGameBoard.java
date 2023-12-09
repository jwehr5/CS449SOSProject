package application;

public class GeneralGameBoard extends Board{
	
	//Variables for keeping track of the score for both players.
	private int bluePoints = 0;
	private int redPoints = 0;
	
	public GeneralGameBoard(int size) {
		super(size);
	}
	
	public GeneralGameBoard() {
		super(3); 
	}
	
	
	@Override
	public void makeMove(int row, int col, char startingPiece) { 
		
		//If we have successfully placed the piece on the board, then process the turn.
		if(placePieceOnBoard(row, col, startingPiece)) {
			
			//Clear the list of solutions each turn.
			solutions.clear();
			
			 checkForSOS(row, col, startingPiece); 
			
			
			/*If the size of solutions is not equal to 0, that means a solution was found.
			 * Increment the player's score by the number of solutions found.
			 */
			if(solutions.size() != 0) {
				if(currentPlayer == "Blue") {
					bluePoints += solutions.size();
				}else if(currentPlayer == "Red") {
					redPoints += solutions.size();
				}
			
			//If the size of solutions is equal to 0, then no solutions were found. Change the turn.
			}else if(solutions.size() == 0){
				//System.out.println("Changing turn...");
				if(currentPlayer == "Blue") {
					currentPlayer = "Red";
				}else if(currentPlayer == "Red") {
					currentPlayer = "Blue";
				}
				
			}
			
			//The game will be over once the board is full.
			if(isBoardFull()) {
				gameIsOver = true;
				if(bluePoints == redPoints) {
					drawGame = true;
					
				}else if(bluePoints > redPoints) {
					winner = "Blue";
					
				}else if(redPoints > bluePoints) {
					winner = "Red";
				}
			}
			
			printBoard(); 
			
		}
			
	}
	
	
	
	public int getBluePoints() {
		return bluePoints;
	}
	
	public int getRedPoints() {
		return redPoints;
	}

}

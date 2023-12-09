package application;

public class SimpleGameBoard extends Board {
	
	 public SimpleGameBoard(int size){
		super(size);
	}
	 
	 public SimpleGameBoard() {
		super(3);
	}
	
	@Override
	public void makeMove(int row, int col, char startingPiece) {
		
		if(placePieceOnBoard(row, col, startingPiece)) {
			
			//Clear the list of solutions each turn.
			solutions.clear();
		
			checkForSOS(row, col, startingPiece);
			
			//If the size of solutions is equal to 0, that means solution was not found. Check for a draw game.
			if(solutions.size() == 0 && isBoardFull() == true) {
				gameIsOver = true;
				drawGame = true;
			
			//If the board is not full yet, then change the turn.
			}else if(solutions.size() == 0){
				//System.out.println("Changing turn...");
				if(currentPlayer == "Blue") {
					currentPlayer = "Red";
				}else if(currentPlayer == "Red") {
					currentPlayer = "Blue";
				}
			
			//If the size of solutions is greater than 0, then a solutions was found and the game is now over.
			}else {
				gameIsOver = true;
				if(currentPlayer == "Blue") {
					winner = "Blue";
				}else {
					winner = "Red";
				}
			}
			
			printBoard();
			
			
		}
		
		
	}
	
	
}

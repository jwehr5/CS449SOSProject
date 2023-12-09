package application;

import java.util.Random;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ComputerPlayer extends Player {
	private Random rand = new Random();
	
	private char recentlyPlacedPiece;
	private int recentRowIndexMove;
	private int recentColIndexMove;
	
	public ComputerPlayer(String playerColor, String playerType) {
		super(playerColor, playerType);
		
	}
	
	public Button makeAStrategicMove(Board b, GridPane gameBoard) {
		//This will be the cell location of where the computer will make its move
		int rowIndexOfMove = -1;
		int colIndexOfMove = -1;
		
		String letter = " "; 
		
		for(int row = 0; row < b.getBoardSize(); row++) {
			for(int col = 0; col < b.getBoardSize(); col++) {
				
				if(b.getCellValue(row, col) == 'S') {
					
					if(b.getCellValue(row - 1, col) != '-' && b.getCellValue(row - 1, col) == 'O') { 
						if(b.getCellValue(row - 2, col) != '-' && b.getCellValue(row - 2, col) == ' ') {
							rowIndexOfMove = row - 2;
							colIndexOfMove = col;
							letter = "S";
									
						}
					}
					
					if(b.getCellValue(row - 1, col + 1) != '-' && b.getCellValue(row - 1, col + 1) == 'O') {
						if(b.getCellValue(row - 2, col + 2) != '-' && b.getCellValue(row - 2, col + 2) == ' ') {
							rowIndexOfMove = row - 2;
							colIndexOfMove = col + 2;
							letter = "S";
									
						}
					}
					
					if(b.getCellValue(row, col + 1) != '-' && b.getCellValue(row, col + 1) == 'O') {
						if(b.getCellValue(row, col + 2) != '-' && b.getCellValue(row, col + 2) == ' ') {
							rowIndexOfMove = row;
							colIndexOfMove = col + 2;
							letter = "S";
								
						}
					}
					
					if(b.getCellValue(row + 1, col + 1) != '-' && b.getCellValue(row + 1, col + 1) == 'O') {
						if(b.getCellValue(row + 2, col + 2) != '-' && b.getCellValue(row + 2, col + 2) == ' ') {
							rowIndexOfMove = row + 2;
							colIndexOfMove = col + 2;
							letter = "S";
									
						}
					}
					
					if(b.getCellValue(row + 1, col) != '-' && b.getCellValue(row + 1, col) == 'O') {
						if(b.getCellValue(row + 2, col) != '-' && b.getCellValue(row + 2, col) == ' ') {
							rowIndexOfMove = row + 2;
							colIndexOfMove = col;
							letter = "S";
									
						}
					}
					
					if(b.getCellValue(row + 1, col - 1) != '-' && b.getCellValue(row + 1, col - 1) == 'O') {
						if(b.getCellValue(row + 2, col - 2) != '-' && b.getCellValue(row + 2, col - 2) == ' ') {
							rowIndexOfMove = row + 2;
							colIndexOfMove = col - 2;
							letter = "S";
									
						}
					}
					
					if(b.getCellValue(row, col - 1) != '-' && b.getCellValue(row, col - 1) == 'O') {
						if(b.getCellValue(row, col - 2) != '-' && b.getCellValue(row, col - 2) == ' ') {
							rowIndexOfMove = row;
							colIndexOfMove = col - 2;
							letter = "S"; 
									
						}
					}
					
					if(b.getCellValue(row - 1, col - 1) != '-' && b.getCellValue(row - 1, col - 1) == 'O') {
						if(b.getCellValue(row - 2, col - 2) != '-' && b.getCellValue(row - 2, col - 2) == ' ') {
							rowIndexOfMove = row - 2;
							colIndexOfMove = col - 2;
							letter = "S";
									
						}
						
					// Re-check again but this time check if an 'O' needs to be placed to complete and SOS sequence
					}
					
					if(b.getCellValue(row - 1, col) != '-' && b.getCellValue(row - 1, col) == ' ') {
						if(b.getCellValue(row - 2, col) != '-' && b.getCellValue(row - 2, col) == 'S') {
							rowIndexOfMove = row - 1;
							colIndexOfMove = col;
							letter = "O";
									
						}
					}
					
					if(b.getCellValue(row - 1, col + 1) != '-' && b.getCellValue(row - 1, col + 1) == ' ') {
						if(b.getCellValue(row - 2, col + 2) != '-' && b.getCellValue(row - 2, col + 2) == 'S') {
							rowIndexOfMove = row - 1;
							colIndexOfMove = col + 1;
							letter = "O";
									
						}
					}
					
					if(b.getCellValue(row, col + 1) != '-' && b.getCellValue(row, col + 1) == ' ') {
						if(b.getCellValue(row, col + 2) != '-' && b.getCellValue(row, col + 2) == 'S') {
							rowIndexOfMove = row;
							colIndexOfMove = col + 1;
							letter = "O";
								
						}
					}
					
					if(b.getCellValue(row + 1, col + 1) != '-' && b.getCellValue(row + 1, col + 1) == ' ') {
						if(b.getCellValue(row + 2, col + 2) != '-' && b.getCellValue(row + 2, col + 2) == 'S') {
							rowIndexOfMove = row + 1;
							colIndexOfMove = col + 1;
							letter = "O";
									
						}
					}
					
					if(b.getCellValue(row + 1, col) != '-' && b.getCellValue(row + 1, col) == ' ') {
						if(b.getCellValue(row + 2, col) != '-' && b.getCellValue(row + 2, col) == 'S') {
							rowIndexOfMove = row + 1;
							colIndexOfMove = col;
							letter = "O";  
									
						}
					}
					
					if(b.getCellValue(row + 1, col - 1) != '-' && b.getCellValue(row + 1, col - 1) == ' ') {
						if(b.getCellValue(row + 2, col - 2) != '-' && b.getCellValue(row + 2, col - 2) == 'S') {
							rowIndexOfMove = row + 1;
							colIndexOfMove = col - 1;
							letter = "O";
									
						}
					} 
					
					if(b.getCellValue(row, col - 1) != '-' && b.getCellValue(row, col - 1) == ' ') {
						if(b.getCellValue(row, col - 2) != '-' && b.getCellValue(row, col - 2) == 'S') {
							rowIndexOfMove = row;
							colIndexOfMove = col - 1;
							letter = "O";
									
						}
					}
					
					if(b.getCellValue(row - 1, col - 1) != '-' && b.getCellValue(row - 1, col - 1) == ' ') {
						if(b.getCellValue(row - 2, col - 2) != '-' && b.getCellValue(row - 2, col - 2) == 'S') {
							rowIndexOfMove = row - 1;
							colIndexOfMove = col - 1;
							letter = "O";
									
						}
					}
					
				}else if(b.getCellValue(row, col) == 'O') { 
					
					if((b.getCellValue(row - 1, col) != '-' && b.getCellValue(row - 1, col) == 'S') 
						&& (b.getCellValue(row + 1, col) != '-' && b.getCellValue(row + 1, col) == ' ')){
						rowIndexOfMove = row + 1;
						colIndexOfMove = col;
						letter = "S";
						
					}else if((b.getCellValue(row - 1, col) != '-' && b.getCellValue(row - 1, col) == ' ') 
						&& (b.getCellValue(row + 1, col) != '-' && b.getCellValue(row + 1, col) == 'S')) {
						rowIndexOfMove = row - 1;
						colIndexOfMove = col;
						letter = "S";
					}
					
					
					if((b.getCellValue(row - 1, col + 1) != '-' && b.getCellValue(row - 1, col + 1) == 'S')
						&&(b.getCellValue(row + 1, col - 1) != '-' && b.getCellValue(row + 1, col - 1) == ' ')){
						rowIndexOfMove = row + 1;
						colIndexOfMove = col - 1;
						letter = "S";
						
					}else if((b.getCellValue(row - 1, col + 1) != '-' && b.getCellValue(row - 1, col + 1) == ' ')
						&&(b.getCellValue(row + 1, col - 1) != '-' && b.getCellValue(row + 1, col - 1) == 'S')) {
						rowIndexOfMove = row - 1;
						colIndexOfMove = col + 1;
						letter = "S";
					}
					
					
					if((b.getCellValue(row, col + 1) != '-' && b.getCellValue(row, col + 1) == 'S')
						&&(b.getCellValue(row, col - 1) != '-' && b.getCellValue(row, col - 1) == ' ')){
						rowIndexOfMove = row;
						colIndexOfMove = col - 1;
						letter = "S";
						
					}else if((b.getCellValue(row, col + 1) != '-' && b.getCellValue(row, col + 1) == ' ')
						&&(b.getCellValue(row, col - 1) != '-' && b.getCellValue(row, col - 1) == 'S')) {
						rowIndexOfMove = row;
						colIndexOfMove = col + 1;
						letter = "S";
					}
					
					
					if((b.getCellValue(row + 1, col + 1) != '-' && b.getCellValue(row + 1, col + 1) == 'S')
						&&(b.getCellValue(row - 1, col - 1) != '-' && b.getCellValue(row - 1, col - 1) == ' ')){
						rowIndexOfMove = row - 1;
						colIndexOfMove = col - 1;
						letter = "S";
						
					}else if((b.getCellValue(row + 1, col + 1) != '-' && b.getCellValue(row + 1, col + 1) == ' ')
						&&(b.getCellValue(row - 1, col - 1) != '-' && b.getCellValue(row - 1, col - 1) == 'S')) {
						rowIndexOfMove = row + 1;
						colIndexOfMove = col + 1;
						letter = "S";
					}
				}
			}
			
			//If we found a move then go ahead and break out of the loop
			if(rowIndexOfMove != -1 && colIndexOfMove != -1) {
				break;
			}
		}
		
		
		
		//If the computer wasn't able to find a move, then just make a random move
		if(rowIndexOfMove == -1 && colIndexOfMove == -1) { 
			return randomlyMakeAMove(b, gameBoard);
		}else {
			recentlyPlacedPiece = letter.charAt(0);
			recentRowIndexMove = rowIndexOfMove;
			recentColIndexMove = colIndexOfMove;

			b.makeMove(rowIndexOfMove, colIndexOfMove, letter.charAt(0));
			for(Node button : gameBoard.getChildren()) {
				if(GridPane.getRowIndex(button) == rowIndexOfMove && GridPane.getColumnIndex(button) == colIndexOfMove) {
					button.setDisable(true);
					((Button) button).setText(letter);
					button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-opacity: 1;");  
					
					
					
					return (Button) button; 
				}
			}
		}
		return null;
		
	}
	
	public Button randomlyMakeAMove(Board b, GridPane gameBoard) {
		String letter;
		int randIntForLetter = rand.nextInt(2);
		if(randIntForLetter == 0) { 
			letter = "S";
		}else {
			letter = "O";
		}
		
		int randRowIndex = rand.nextInt(b.getBoardSize());
		int randColumnIndex = rand.nextInt(b.getBoardSize());
		
		while(b.getCellValue(randRowIndex, randColumnIndex) != ' ') {
			 randRowIndex = rand.nextInt(b.getBoardSize());
			 randColumnIndex = rand.nextInt(b.getBoardSize()); 
		}
		
		recentlyPlacedPiece = letter.charAt(0);
		recentRowIndexMove = randRowIndex;
		recentColIndexMove = randColumnIndex;
		b.makeMove(randRowIndex, randColumnIndex, letter.charAt(0)); 

		for(Node button : gameBoard.getChildren()) {
			if(GridPane.getRowIndex(button) == randRowIndex && GridPane.getColumnIndex(button) == randColumnIndex) {
				placePieceOnGUIBoard((Button) button, letter); 
				
				return (Button) button; 
			}
		}
		return null;
		
		
	}
	
	public char getRecentlyPlacedPiece() {
		return recentlyPlacedPiece; 
	}
	
	public int getRecentRowIndexMove() {
		return recentRowIndexMove;
	}
	
	public int getRecentColIndexMove() {
		return recentColIndexMove;
	}
	

}

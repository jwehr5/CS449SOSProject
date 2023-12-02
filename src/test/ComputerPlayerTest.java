package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import application.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComputerPlayerTest {
	
	Board b1;
	Board b2;
	GridPane gameBoard;
	ComputerPlayer bcp;
	ComputerPlayer rcp;
	
	@BeforeEach
	public void initialize() {
		b1 = new SimpleGameBoard(4);
		b2 = new GeneralGameBoard(5);
		gameBoard = new GridPane();
		
		bcp = new ComputerPlayer("Blue", "Computer");
		rcp = new ComputerPlayer("Red", "Computer");
		
	}
	
	/*
	 * For each test, the first two moves will be manually made in a way where the third can complete and SOS sequence. 
	 * The Third move will be done by the computer to see if it can accurately identify the SOS sequence that can be made.
	 */

	@Test
	public void firstComputerMoveTest(){
		b1.makeMove(1, 1, 'O');
		b1.makeMove(1, 2, 'S');
		
		//Make the computer player play
		bcp.makeAStrategicMove(b1, gameBoard);
		assertEquals('S', b1.getCellValue(1, 0));		
	}
	
	@Test
	public void secondComputerMoveTest() {
		b1.makeMove(0, 2, 'S');
		b1.makeMove(1, 1, 'O');
		
		bcp.makeAStrategicMove(b1, gameBoard);
		assertEquals('S', b1.getCellValue(2, 0));

	}
	
	
	@Test
	public void thirdComputerMoveTest() {
		b1.makeMove(1, 1, 'S');
		b1.makeMove(3, 3, 'S');
		
		bcp.makeAStrategicMove(b1, gameBoard);
		assertEquals('O', b1.getCellValue(2, 2));
		
	}
	
	@Test
	public void fourthComputerMoveTest() {
		b2.makeMove(2, 1, 'S');
		b2.makeMove(0, 1, 'S');
		b2.makeMove(1, 3, 'S');
		
		rcp.makeAStrategicMove(b2, gameBoard);
		assertEquals('O', b2.getCellValue(1, 1));

	}
	
	@Test
	public void fifthComputerMoveTest() {
		b2.makeMove(3, 2, 'S');
		b2.makeMove(2, 2, 'O');
		b2.makeMove(0, 2, 'O');
		
		rcp.makeAStrategicMove(b2, gameBoard);
		assertEquals('S', b2.getCellValue(1, 2));
		
	}
	
	@Test
	public void sixthComputerMoveTest() {
		b2.makeMove(2, 1, 'S');
		b2.makeMove(2, 3, 'S');
		b2.makeMove(1, 1, 'S');
		
		rcp.makeAStrategicMove(b2, gameBoard);
		assertEquals('O', b2.getCellValue(2, 2)); 

	}
	
	//The following tests will be for testing random moves
	@Test 
	public void firstRandomMoveTest() {
		b1.makeMove(1, 1, 'S');
		b1.makeMove(2, 3, 'S');
		
		bcp.makeAStrategicMove(b1, gameBoard);
		
		//Verify that a piece was actually placed somewhere on the board
		boolean foundPlacedPiece = false;
		for(int i = 0; i < b1.getBoardSize(); i++) {
			for(int j = 0; j < b1.getBoardSize(); j++) {
				if((b1.getCellValue(i, j) == 'S' && ((i != 1 || j != 1) && (i != 2 || j != 3)))){
					foundPlacedPiece = true;
				}else if(b1.getCellValue(i, j) == 'O' && ((i != 1 || j != 1) && (i != 2 || j != 3))){
					foundPlacedPiece = true; 
				}
			}
		}
		
		assertTrue(foundPlacedPiece); 
		
	}
	
	@Test
	public void secondRandomMoveTest() {
		b2.makeMove(1, 1, 'S');
		b2.makeMove(2, 1, 'S');
		
		bcp.makeAStrategicMove(b2, gameBoard);
		
		//Verify that a piece was actually placed somewhere on the board
		boolean foundPlacedPiece = false;
		for(int i = 0; i < b2.getBoardSize(); i++) {
			for(int j = 0; j < b2.getBoardSize(); j++) {
				if((b2.getCellValue(i, j) == 'S' && ((i != 1 || j != 1) && (i != 2 || j != 1)))){
					foundPlacedPiece = true;
				}else if(b2.getCellValue(i, j) == 'O' && ((i != 1 || j != 1) && (i != 2 || j != 1))){
					foundPlacedPiece = true; 
				}
			}
		}
		
		assertTrue(foundPlacedPiece);
	}
	
	@Test
	public void firstComputerVsComputerTest() {
		System.out.println("First computer vs computer test");
		
		bcp.makeAStrategicMove(b1, gameBoard);
		while(!b1.isGameOver()) {
			
			if( b1.getCurrentPlayer() == "Blue") {
				bcp.makeAStrategicMove(b1, gameBoard);
				
			}else if(b1.getCurrentPlayer() == "Red") {
				rcp.makeAStrategicMove(b1, gameBoard);
			}
		}
		
		assertTrue(b1.isGameOver());
		System.out.println(b1.getWinner());
		System.out.println(b1.isDrawGame());
		if(!b1.isDrawGame()) {
			assertTrue(b1.getWinner() == "Blue" || b1.getWinner() == "Red");
		}else {
			assertTrue(b1.isDrawGame()); 
		}
	}
	
	@Test
	public void secondComputerVsComputerTest() {
		System.out.println("Second computer vs computer test");
		
		bcp.makeAStrategicMove(b2, gameBoard);
		while(!b2.isGameOver()) {
			
			System.out.println(b2.getCurrentPlayer());
			if(b2.getCurrentPlayer() == "Blue") {
				bcp.makeAStrategicMove(b2, gameBoard);
				
			}else if(b2.getCurrentPlayer() == "Red") {
				rcp.makeAStrategicMove(b2, gameBoard);
			}
		}
		
		assertTrue(b2.isGameOver());
		System.out.println(b2.getWinner());
		System.out.println(b2.isDrawGame());
		System.out.println(((GeneralGameBoard) b2).getBluePoints());
		System.out.println(((GeneralGameBoard) b2).getRedPoints());
		if(!b2.isDrawGame()) {
			assertTrue(b2.getWinner() == "Blue" || b2.getWinner() == "Red");
		}else {
			assertTrue(b2.isDrawGame());
		}
	}
	
	

}

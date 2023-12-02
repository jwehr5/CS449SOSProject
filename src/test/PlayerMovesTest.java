package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Board;
import application.GeneralGameBoard;
import application.Main;
import application.SimpleGameBoard;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

class PlayerMovesTest {
	
	private Board b1;
	private Board b2;
	

	@BeforeEach
	public void setUp() {
		b1 = new SimpleGameBoard(6);
		b2 = new GeneralGameBoard(6);
		
	}
	
	
	@Test
	public void firstValidMoveTest(){
		b1.makeMove(3, 3, 'S');
		assertEquals("Red", b1.getCurrentPlayer());
		assertEquals('S', b1.getCellValue(3, 3));
		
		b1.makeMove(2, 1, 'S');
		assertEquals("Blue", b1.getCurrentPlayer());
		assertEquals('S', b1.getCellValue(2, 1));
	}
	
	@Test
	public void firstInvalidMoveTest() {
		b1.makeMove(4, 1,'O');
		b1.makeMove(4, 1, 'S');
		assertEquals("Red", b1.getCurrentPlayer());
		assertEquals('O', b1.getCellValue(4, 1));	
		
	}
	
	@Test
	public void firstInvalidRowAndColumnIndexTest() {
		b1.makeMove(7, 7, 'O');
		assertEquals("Blue", b1.getCurrentPlayer());
		assertEquals('-', b1.getCellValue(7, 7));
		
	}
	
	@Test
	public void secondValidMoveTest(){
		b2.makeMove(0, 0, 'O');
		assertEquals("Red", b2.getCurrentPlayer());
		assertEquals('O', b2.getCellValue(0, 0));
		b2.makeMove(5, 5, 'S');
		assertEquals("Blue", b2.getCurrentPlayer());
		assertEquals('S', b2.getCellValue(5, 5));
	}
	
	@Test
	public void secondInvalidMoveTest() {
		b2.makeMove(2, 2,'S');
		b2.makeMove(2, 2, 'O');
		assertEquals("Red", b2.getCurrentPlayer());
		assertEquals('S', b2.getCellValue(2, 2));	
		
	}
	
	@Test
	public void secondInvalidRowAndColumnIndexTest() {
		b2.makeMove(-1, 6, 'O');
		assertEquals("Blue", b2.getCurrentPlayer());
		assertEquals('-', b2.getCellValue(-1, 6));
		
	}
	
	@Test
	public void creatingAnSOSSequenceTest() {
		b2.makeMove(2, 2, 'S');
		b2.makeMove(2, 1, 'O');
		b2.makeMove(2, 0, 'S');
		
		assertEquals("Blue", b2.getCurrentPlayer());
		assertEquals(1, ((GeneralGameBoard) b2).getBluePoints());
	}
	
}

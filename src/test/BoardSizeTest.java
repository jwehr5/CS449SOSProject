package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import application.Board;
import application.GeneralGameBoard;
import application.SimpleGameBoard;

class BoardSizeTest {
	

	@Test
	public void firstValidBoardSizeTest() {
		Board b = new SimpleGameBoard(7);
		assertEquals(7, b.getBoardSize());
	}
	
	@Test
	public void secondValidBoardSizeTest() {
		Board b = new GeneralGameBoard(12);
		assertEquals(12, b.getBoardSize());
	}
	
	@Test
	public void firstInvalidBoardSizeTest() {
		Board b = new SimpleGameBoard(13);
		assertEquals(3, b.getBoardSize());
	}
	
	@Test
	public void secondInvalidBoardSizeTest() {
		Board b = new GeneralGameBoard(2);
		assertEquals(3, b.getBoardSize());
	}
	
	@Test
	public void noGivenBoardSizeTest() {
		Board b = new SimpleGameBoard();
		assertEquals(3, b.getBoardSize());
	}

}

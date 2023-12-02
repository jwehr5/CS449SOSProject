package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import application.GeneralGameBoard;
import application.Board;

class GeneralGameTest {

	@Test
	public void blueWinsGeneralGameTest() {
		Board b = new GeneralGameBoard(6);
		
		//Blue's turn
		b.makeMove(3, 2, 'S');
		
		//Red's Turn
		b.makeMove(2, 3, 'O');
		
		//Blue's Turn
		b.makeMove(1, 4, 'S');
		
		//Should be Blue's turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 4, 'S');
		
		//Red's Turn
		b.makeMove(5, 2, 'O');
		
		//Blue's Turn
		b.makeMove(2, 2, 'S');
		
		//Blue's Turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(4, 3, 'S');
		
		//Red's Turn
		b.makeMove(4, 5, 'S');
		
		//Blue's Turn
		b.makeMove(4,4,'O');
		
		//Blue's Turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(3, 4, 'S');
		
		//Red's Turn
		b.makeMove(3, 3, 'S');
		
		//Blue's Turn
		b.makeMove(0, 0, 'S');
		
		//Red's Turn
		b.makeMove(5, 5, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(1, 3, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(1, 0, 'O');
		
		//Blue's Turn
		b.makeMove(1, 1, 'O');
		
		//Blue goes again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 0, 'S');
		
		//Check the score at this point
		assertEquals(5, ((GeneralGameBoard) b).getBluePoints());
		assertEquals(2, ((GeneralGameBoard) b).getRedPoints());
		
		//Blue Goes again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(5, 0, 'O');
		
		//Red's Turn
		b.makeMove(4, 0, 'O');
		
		//Blue's Turn
		b.makeMove(3, 0, 'S');
		
		//Red's Turn
		b.makeMove(0, 5, 'O');
		
		//Blue's Turn
		b.makeMove(0, 4, 'S');
		
		//Red's Turn
		b.makeMove(1, 5, 'S');
		
		//Blue's Turn
		b.makeMove(2, 1, 'O');
		
		//Blue's Turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(5, 4, 'S');
		
		//Blue's Turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(5, 1, 'O');
		
		//Red's Turn
		b.makeMove(5,3, 'S');
		
		//Blue's Turn
		b.makeMove(3, 5, 'S');
		
		//Blue's Turn again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(4,1,'O');
		
		//Red's Turn
		b.makeMove(2, 5, 'O');
		
		//Red goes again
		b.makeMove(4, 2, 'O');
		
		//Blue's Turn
		b.makeMove(3, 1, 'S');
		
		//Blue goes again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(0, 1, 'S');
		
		//Red's Turn
		b.makeMove(1, 2, 'O');
		
		//Blue's Turn
		b.makeMove(0, 3, 'S');
		
		//Red's Turn (Final Turn)
		b.makeMove(0, 2, 'S');
		
		assertEquals(9, ((GeneralGameBoard) b).getBluePoints());
		assertEquals(5, ((GeneralGameBoard) b).getRedPoints());
		assertTrue(b.isGameOver());
		assertEquals("Blue", b.getWinner());
		
		
	}
	
	@Test
	public void redWinsGeneralGameTest() {
		Board b = new GeneralGameBoard(5);
		
		//Blue's Turn
		b.makeMove(0, 1, 'S');
		
		//Red's Turn
		b.makeMove(0, 3, 'S');
		
		//Blue's Turn
		b.makeMove(1, 2, 'O');
		
		//Red's Turn
		b.makeMove(2, 2, 'S');
		
		//Blue's Turn
		b.makeMove(2, 4, 'S');
		
		//Red's Turn
		b.makeMove(4, 0, 'O');
		
		//Blue's Turn
		b.makeMove(1, 3, 'O');
		
		//Red's Turn
		b.makeMove(0, 2, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(0, 4, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(4, 4, 'O');
		
		//Blue's Turn
		b.makeMove(3, 0,'S');
		
		//Red's Turn
		b.makeMove(2, 0, 'S');
		
		//Blue's Turn
		b.makeMove(3, 1, 'O');
		
		//Red's Turn
		b.makeMove(3, 2, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(4, 1, 'O');
		
		//Blue's Turn
		b.makeMove(0, 0, 'O');
		
		//Red's Turn
		b.makeMove(1, 0, 'S');
		
		//Blue's Turn
		b.makeMove(4, 3, 'O');
		
		//Red's Turn
		b.makeMove(3, 3, 'S');
		
		//Blue's Turn
		b.makeMove(3, 4, 'O');
		
		//Red's Turn
		b.makeMove(2, 3, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(2, 1, 'O');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(4, 2, 'O');
		
		//Blue's Turn
		b.makeMove(1, 1, 'O');
		
		//Blue goes again (Final Turn)
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(1, 4, 'O');
		
		assertEquals(2, ((GeneralGameBoard) b).getBluePoints());
		assertEquals(8, ((GeneralGameBoard) b).getRedPoints());
		assertTrue(b.isGameOver());
		assertEquals("Red", b.getWinner());
		
	}
	
	@Test
	public void firstDrawInAGeneralGameTest() {
		Board b = new GeneralGameBoard(4);
		
		//Blue's Turn
		b.makeMove(0, 3, 'S');
		
		//Red's Turn
		b.makeMove(1, 2, 'O');
		
		//Blue's Turn
		b.makeMove(2, 1, 'S');
		
		//Blue goes again
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(0, 0, 'S');
		
		//Red's Turn
		b.makeMove(1, 0, 'O');
		
		//Blue's Turn
		b.makeMove(3, 0, 'S');
		
		//Red's Turn
		b.makeMove(2, 0, 'S');
		
		//Red goes again
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(3, 3, 'O');
		
		//Blue's Turn
		b.makeMove(0, 1, 'O');
		
		//Red's Turn
		b.makeMove(3, 2, 'S');
		
		//Blue's Turn
		b.makeMove(3, 1, 'S');
		
		//Red's Turn
		b.makeMove(1, 1, 'S');
		
		//Blue's Turn
		b.makeMove(2, 2, 'S');
		
		//Red's Turn
		b.makeMove(0, 2, 'O');
		
		//Blue's Turn
		b.makeMove(1, 3, 'O');
		
		//Red's Turn (Final Turn)
		b.makeMove(2, 3, 'O');
		
		assertEquals(1, ((GeneralGameBoard) b).getBluePoints());
		assertEquals(1, ((GeneralGameBoard) b).getRedPoints());
		assertTrue(b.isGameOver());
		assertTrue(b.isDrawGame());
		
		
	}
	
	@Test
	public void secondDrawInAGeneralGameTest(){
		Board b = new GeneralGameBoard(3);
		
		//Each player alternates placing an 'S' on the board
		b.makeMove(0, 0, 'S');
		b.makeMove(0, 1, 'S');
		b.makeMove(0, 2, 'S');
		b.makeMove(1, 0, 'S');
		b.makeMove(1, 1, 'S');
		b.makeMove(1, 2, 'S');
		b.makeMove(2, 0, 'S');
		b.makeMove(2, 1, 'S');
		b.makeMove(2, 2, 'S');
		
		assertEquals(0, ((GeneralGameBoard) b).getBluePoints());
		assertEquals(0, ((GeneralGameBoard) b).getRedPoints());
		assertTrue(b.isGameOver());
		assertTrue(b.isDrawGame());
		
	}
	
	

}

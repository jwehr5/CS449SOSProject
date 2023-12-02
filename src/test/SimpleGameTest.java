package test;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;
import application.SimpleGameBoard;
import application.Board;


class SimpleGameTest {

	@Test
	public void firstBlueWinsSimpleGameTest() { 
		Board b = new SimpleGameBoard(6);
		//Blue makes this move.
		b.makeMove(3, 2, 'S');
		
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(1, 4, 'S');
		
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 3, 'O');
		
		//The game should now be over.
		assertTrue(b.isGameOver());
		assertEquals("Blue", b.getWinner());
		
		
	}
	
	@Test
	public void secondBlueWinsSimpleGameTest() {
		Board b = new SimpleGameBoard(4);
		//Blue makes this move
		b.makeMove(2, 2, 'O');
		
		//Red player's turn
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(3, 3, 'S');
		
		//Blue player's turn
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(1, 1, 'S');
		
		//The Game Should be over now
		assertTrue(b.isGameOver());
		assertEquals("Blue", b.getWinner());
		
	}
	
	
	@Test
	public void firstRedWinsSimpleGame() {
		Board b = new SimpleGameBoard(7);
		//Blue makes this move.
		b.makeMove(5, 2, 'O');
				
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(5, 1, 'S');
				
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 2, 'O');
		
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(5, 3, 'S');
				
		//The game should now be over.
		assertTrue(b.isGameOver());
		assertEquals("Red", b.getWinner());
		
	}
	
	@Test
	public void secondRedWinsSimpleGameTest() {
		Board b = new SimpleGameBoard(8);
		//Blue makes this move
		b.makeMove(4, 2, 'S');
		
		//Red player's turn
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(2, 2, 'S');
		
		//Blue player's turn
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(4, 5, 'O');
		
		//Red player's turn
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(3, 2, 'O');
		
		//Game should be over now
		assertTrue(b.isGameOver());
		assertEquals("Red", b.getWinner());
		
		
		
	}
	
	
	@Test
	public void drawInASimpleGameTest() {
		Board b = new SimpleGameBoard(3);
		//Blue makes this move.
		b.makeMove(0, 0, 'S');
						
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(0, 1, 'S');
						
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(0, 2, 'S');
				
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(1, 0, 'S');
		
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(1, 1, 'S');
		
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(1, 2, 'S');
		
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 0, 'S');
		
		//It should be the Red player's turn.
		assertEquals("Red", b.getCurrentPlayer());
		b.makeMove(2, 1, 'S');
		
		//It should be the Blue player's turn.
		assertEquals("Blue", b.getCurrentPlayer());
		b.makeMove(2, 2, 'S');
		
		//The game should now be over.
		assertTrue(b.isGameOver() && b.isDrawGame());
		assertEquals("", b.getWinner());
	} 
	
}

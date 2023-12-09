package application;

import java.io.FileWriter;
import java.io.IOException;

public class Recorder {
	
	private FileWriter recording;
	
	public Recorder() throws IOException {
		//Initialize the file on which the game will be recorded on when the player chooses to record the game.
		recording = new FileWriter("moves.txt");
	}
	
	public void writeTitleOfGame(boolean isSimpleGame, int boardSize) throws IOException {
		
		if(isSimpleGame) {
			recording.write("Starting a Simple Game with a Board size of " + boardSize);
			recording.write(System.getProperty("line.separator"));
		}else {
			recording.write("Starting a General Game with a Board size of " + boardSize);
			recording.write(System.getProperty("line.separator"));
		}
	}
	
	public void writeMove(Player currentPlayer, char playedPiece, int row, int col) throws IOException {
		recording.write(currentPlayer.getPlayerColor() + " places an '" + playedPiece + "' at cell (" + row + "," + col + ")");
		recording.write(System.getProperty("line.separator"));
		
	}
	
	public void writeConclusion(boolean isDrawGame, String winner) throws IOException {
		if(!isDrawGame) {
			recording.write("Game is finished with " + winner + " being the winner");
			recording.write(System.getProperty("line.separator"));
			recording.write(System.getProperty("line.separator"));
		}else {
			recording.write("Game has finished in a draw");
			recording.write(System.getProperty("line.separator"));
			recording.write(System.getProperty("line.separator"));
		}
		
	}
	
	
	public void closeFile() throws IOException {
		recording.close();
	}

}

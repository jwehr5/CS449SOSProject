package application;

import javafx.scene.control.Button;

public class Player {
	private String playerColor;
	private String playerType;
	
	public Player(String playerColor, String playerType) {
		this.playerColor = playerColor;
		this.playerType = playerType;
		
	}
	
	public void placePieceOnGUIBoard(Button button, String piece) {
		button.setDisable(true);
		button.setText(piece);
		button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-opacity: 1;"); 
	}
	
	public String getPlayerColor() {
		return playerColor;
	}
	
	public String getPlayerType() {
		return playerType;
	}

}

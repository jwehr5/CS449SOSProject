package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable{
	
	//Choice box that lets the user select how big the board should be.
	@FXML
	private ChoiceBox<Integer> boardSizes;
	private Integer[] sizes = {3,4,5,6,7,8,9,10,11,12};
	
	//This is the game board where the game will take place.
	@FXML
	private GridPane gameBoardGUI;
	
	//Radio buttons that let the user select a simple or general game
	@FXML
	private RadioButton simpleGame;
	@FXML
	private RadioButton generalGame;
	
	//Radio buttons that lets the Blue and Red player choose their piece.
	@FXML
	private RadioButton bluePlayer_S;
	@FXML
	private RadioButton bluePlayer_O;
	@FXML
	private RadioButton redPlayer_S;
	@FXML
	private RadioButton redPlayer_O;
	
	@FXML
	private Text bluePlayerLabel;
	@FXML
	private Text redPlayerLabel;
	
	//Button that starts the game.
	@FXML
	private Button startGame;
	
	//Button to restart the game
	@FXML
	private Button restartButton;
	
	//Text that the displays who is the current player.
	@FXML
	private Text currentPlayer;
	
	//This is the size of the board that the user chooses.
	private int size;
	private final int DEFAULT_BOARD_SIZE = 3;
	
	//Booleans that determine if a simple or general game is being played.
	private boolean simpleGameIsSelected = false;
	private boolean generalGameIsSelected = false; 
	
	//Text fields for displaying each player's score in a General Game.
	@FXML
	private Text bluePlayerScore;
	@FXML
	private Text redPlayerScore;
	
	//Radio Buttons for showing the type of player (human or computer) for each player
	@FXML
	private RadioButton bluePlayerHuman;
	@FXML
	private RadioButton bluePlayerComputer;
	@FXML
	private RadioButton redPlayerHuman;
	@FXML
	private RadioButton redPlayerComputer;
	
	
	//The Board class will be used to represent the game logic.
	private Board gameBoardLogic;
	
	//These will be represent the players of the game
	private Player bluePlayer;
	private Player redPlayer;
	
	//When playing against a computer player the Timeline will be used to slowly show the computer's moves
	Timeline timeline;
	
	//CheckBox that allows the player to record the game
	@FXML
	private CheckBox recordGame;
	
	private Recorder recording;
	
	// This method runs when the GUI application is first loaded up and initializes certain GUI elements. 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Set the color for the player labels
		bluePlayerLabel.setFill(Color.BLUE);
		redPlayerLabel.setFill(Color.RED);
		
		//Initialize the choice box that lets the user select the board size and set the default value to 3.
		boardSizes.getItems().addAll(sizes);
		boardSizes.setValue(3);
		boardSizes.setOnAction(this::setBoardSize);
		size = 3;
		
		//Initialize the default board and set the row and column index for each cell.
		gameBoardGUI.getChildren().clear();
		initializeBoard(DEFAULT_BOARD_SIZE);
		
		
		//If the player chooses to record the game, then this will allow us to create and to write to a file.
		try {
			 recording = new Recorder();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//This method will initialize the GUI gameboard to the desired size.
	private void initializeBoard(int boardSize) {
		
		//Initialize the game board. Each cell will contain a button that the player will click on when placing their piece.
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Button b = new Button();
				gameBoardGUI.add(b,i,j);
				b.setPrefWidth(50);
				b.setPrefHeight(50);
				b.setStyle("-fx-border-color: black;");
				b.setOnAction(arg01 -> {
					try {
						placePieces(arg01);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});  
			}
		}
		
	}
	
	//This method sets the board size to what the user selects from the choice box.
	public void setBoardSize(ActionEvent e) {
		size = boardSizes.getValue();
		
		//Clear the existing game board so that a new one can be initialized to the new size.
		gameBoardGUI.getChildren().clear();
		
		initializeBoard(size);
		
	}
	
	//Method that determines which game mode is selected
	public void setGameMode(ActionEvent e) {
		if(simpleGame.isSelected()) {
			simpleGameIsSelected = true;
			generalGameIsSelected = false;
			
		}else if(generalGame.isSelected()) {
			generalGameIsSelected = true;
			simpleGameIsSelected = false;
			
		}
		
		//The start game button will be enabled if a player type for both the Red and Blue player has been selected.
		if((bluePlayerHuman.isSelected() || bluePlayerComputer.isSelected()) && (redPlayerHuman.isSelected() || redPlayerComputer.isSelected())) {
			startGame.setDisable(false);
		}
			
	}
	
	public void selectPlayerType(ActionEvent e) {
		if((bluePlayerHuman.isSelected() || bluePlayerComputer.isSelected()) && (redPlayerHuman.isSelected() || redPlayerComputer.isSelected()) 
			&& (simpleGame.isSelected() || generalGame.isSelected())) {
			startGame.setDisable(false);
		}
		
	}
	
	//Method that starts a new game.
	public void startGameInMainWindow(ActionEvent e) throws IOException {
		
		restartButton.setDisable(false);
		
		//Initialize the board class to the size that the user selected.
		if(simpleGameIsSelected) {
			gameBoardLogic = new SimpleGameBoard(size);
			
			//If the game is being recorded then write the type of game that is being played.
			if(recordGame.isSelected()) {
				recording.writeTitleOfGame(simpleGameIsSelected, size);
				
			}
			
			
			
		}else if(generalGameIsSelected) {
			gameBoardLogic = new GeneralGameBoard(size);
			bluePlayerScore.setText("Score: 0");
			redPlayerScore.setText("Score: 0");
			
			//If the game is being recorded then write the type of game that is being played.
			if(recordGame.isSelected()) { 
				recording.writeTitleOfGame(simpleGameIsSelected, size);
				
			} 
			
		}
		
		//Initiate the players
		if(bluePlayerHuman.isSelected()) {
			bluePlayer = new Player("Blue", "Human");
		}else {
			bluePlayer = new ComputerPlayer("Blue", "Computer");
		}
		
		if(redPlayerHuman.isSelected()) {
			redPlayer = new Player("Red", "Human");
		}else {
			redPlayer = new ComputerPlayer("Red", "Computer");
		}
		
		//Once a game has started the user will not be able to choose a game mode, board size or to record the game.
		startGame.setDisable(true);
		simpleGame.setDisable(true);
		generalGame.setDisable(true);
		boardSizes.setDisable(true);
		bluePlayerHuman.setDisable(true);
		bluePlayerComputer.setDisable(true);
		redPlayerHuman.setDisable(true);
		redPlayerComputer.setDisable(true);
		recordGame.setDisable(true);
		
		//The Blue player will go first.
		currentPlayer.setText("Current Player: Blue");
		currentPlayer.setFill(Color.BLUE);
		bluePlayer_S.setDisable(false);
		bluePlayer_O.setDisable(false);
		
		//If the blue player is a computer then go ahead and kick off the turn
		if(bluePlayer.getPlayerType() == "Computer") {
			Button playedButton = ((ComputerPlayer) bluePlayer).makeAStrategicMove(gameBoardLogic, gameBoardGUI);
			validateSolutionGUI(playedButton, ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece());
			
			//If the game is being recorded, then write out the move that was made.
			if(recordGame.isSelected()) {
				recording.writeMove(bluePlayer, ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece(), ((ComputerPlayer) bluePlayer).getRecentRowIndexMove(), 
								    ((ComputerPlayer) bluePlayer).getRecentColIndexMove());
				
			}
			
			checkForGameOverGUI();
			
			 
			
		} 
		
	} 
	
	
	//Method that will restart the game
	public void restartGame(ActionEvent e) { 
		//Stop the current game
		simpleGameIsSelected = false;
		generalGameIsSelected = false;
		
		//Re-enable the game mode buttons, the choice box for choosing the board size, and the start game button.
		simpleGame.setDisable(false);
		simpleGame.setSelected(false);
		
		generalGame.setDisable(false);
		generalGame.setSelected(false);
		
		boardSizes.setDisable(false);
		startGame.setDisable(true);
		
		//Disable the buttons for the Red and Blue players
		bluePlayer_S.setDisable(true);
		bluePlayer_S.setSelected(false);
		
		bluePlayer_O.setDisable(true);
		bluePlayer_O.setSelected(false);
		
		redPlayer_S.setDisable(true);
		redPlayer_S.setSelected(false);
		
		redPlayer_O.setDisable(true);
		redPlayer_O.setSelected(false);
		
		currentPlayer.setText("Current Player: ");
		
		//Enable the human/computer buttons
		bluePlayerHuman.setDisable(false);
		bluePlayerHuman.setSelected(false);
		bluePlayerComputer.setDisable(false);
		bluePlayerComputer.setSelected(false);
		
		redPlayerHuman.setDisable(false);
		redPlayerHuman.setSelected(false);
		redPlayerComputer.setDisable(false);
		redPlayerComputer.setSelected(false);
		
		recordGame.setDisable(false);
		recordGame.setSelected(false);
		
		bluePlayerScore.setText(" ");
		redPlayerScore.setText(" ");
		
		//Clear the board and then re-initialize it to the default 3x3 board.
		gameBoardGUI.getChildren().clear();
		gameBoardLogic.clearBoard();
		size = 3;
		boardSizes.setValue(3);
		initializeBoard(DEFAULT_BOARD_SIZE);
		
		//Disable the restart button.
		restartButton.setDisable(true);
		if(timeline != null) {
			timeline.stop(); 
		}
		
	} 
	
	//Method that is responsible for placing the pieces on the board.
	public void placePieces(ActionEvent e) throws IOException { 
		//This is the button that the player clicks on.
		Button pressedButton = (Button) e.getSource();
		
		//When the player makes their move, set the color of the cells back to the default color, gray, to avoid having a "messy" board.
		decolorSquares();
		
		//Check to see if the game has started and that its the Blue player's turn.
		if(!gameBoardLogic.isGameOver() && gameBoardLogic.getCurrentPlayer() == "Blue") {
		
			//If the Blue Player selected S, then place an S at the cell that they clicked on.
			if(bluePlayer_S.isSelected()) {
				
				processTurnGUI(bluePlayer, 'S', pressedButton);
				
			//If the Blue Player selected O, then place an S at the cell that they clicked on.
			}else if(bluePlayer_O.isSelected()) {
				
				processTurnGUI(bluePlayer, 'O', pressedButton);
				
			} 
			 
		//Check to see if the game has started and that its the Red player's turn.
		}else if(!gameBoardLogic.isGameOver() && gameBoardLogic.getCurrentPlayer() == "Red") {
			
			//If the Red Player selected S, then place an S at the cell that they clicked on.
			if(redPlayer_S.isSelected()) {
			
				processTurnGUI(redPlayer, 'S', pressedButton);
				
			//If the Red Player selected O, then place an O at the cell that they clicked on.
			}else if(redPlayer_O.isSelected()) {
				
				processTurnGUI(redPlayer, 'O', pressedButton);
			}
			 
		}
	}
	
	private void processTurnGUI(Player currentPlayer, char playedPiece, Button pressedButton) throws IOException {
		//Place the piece on the GUI board
		currentPlayer.placePieceOnGUIBoard(pressedButton, Character.toString(playedPiece));

		//Make the corresponding move in the Board class placing the played piece at the right row and column.
		gameBoardLogic.makeMove(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), playedPiece); 
		
		//If the game is being recorded, then write out the move that was made.
		if(recordGame.isSelected()) {
			recording.writeMove(currentPlayer, playedPiece, GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton));
		}
		
		//Validate the solution by coloring in the appropriate cells
		validateSolutionGUI(pressedButton, playedPiece);
		
		//Check for a game over
		checkForGameOverGUI();  
		
	}
	
	//This method will check if any solutions were found and activate the coloring of cells
	public void validateSolutionGUI(Button pressedButton, char playedPiece) {
		//If direction is not equal to -1, then that means a solution was found.
		if(gameBoardLogic.solutions.size() != 0) {
			//System.out.println("Valid SOS sequence");
			
			//Color the corresponding squares where the SOS sequence happened for each solution.
			for(int i = 0; i < gameBoardLogic.solutions.size(); i++) {
				
				colorSquares(pressedButton, gameBoardLogic.solutions.get(i), playedPiece , gameBoardLogic.getCurrentPlayer());
				
			} 
			
			//If a general game is being played, update the Blue or Red player's score.
			if(generalGameIsSelected && gameBoardLogic.getCurrentPlayer() == "Blue") {
				bluePlayerScore.setText("Score: " + ((GeneralGameBoard) gameBoardLogic).getBluePoints());
			}else if(generalGameIsSelected && gameBoardLogic.getCurrentPlayer() == "Red") {
				redPlayerScore.setText("Score: " + ((GeneralGameBoard) gameBoardLogic).getRedPoints()); 
			}
			
			
		} 
		
	}
	
	//This method will end the game if the game is over
	public void checkForGameOverGUI() throws IOException {
		//If the game is over, disable the buttons for both players and display who won
		if(gameBoardLogic.isGameOver() && !gameBoardLogic.isDrawGame()) { 
			
			bluePlayer_S.setDisable(true);
			bluePlayer_O.setDisable(true);
			redPlayer_S.setDisable(true);
			redPlayer_O.setDisable(true); 
			
			//If a general game is being played, then whoever scored the most points wins the game.
			if(generalGameIsSelected) {
				if(((GeneralGameBoard) gameBoardLogic).getBluePoints() > ((GeneralGameBoard) gameBoardLogic).getRedPoints()) {
					currentPlayer.setText("Blue has won the game" + "\n Click Restart to initiate a new game");
					currentPlayer.setFill(Color.BLUE);
				}else {
					currentPlayer.setText("Red has won the game" + "\n Click Restart to initiate a new game");
					currentPlayer.setFill(Color.RED);
				}
			}else {
				currentPlayer.setText(gameBoardLogic.getCurrentPlayer() + " has won the game" + "\n Click Restart to initiate a new game");
				if(gameBoardLogic.getCurrentPlayer() == "Blue") {
					currentPlayer.setFill(Color.BLUE);
				}else {
					currentPlayer.setFill(Color.RED); 
				}
			}
			
			//If the game is being recorded, then write out who won the game.
			if(recordGame.isSelected()) {
				recording.writeConclusion(gameBoardLogic.isDrawGame(), gameBoardLogic.getWinner());
			}
			
		
			
			
		//If its a draw game, then display that the game ended in a draw.	
		}else if(gameBoardLogic.isGameOver() && gameBoardLogic.isDrawGame()){  
			
			bluePlayer_S.setDisable(true);
			bluePlayer_O.setDisable(true);
			redPlayer_S.setDisable(true);
			redPlayer_O.setDisable(true); 
			currentPlayer.setText("Draw Game" + "\n Click Restart to initiate a new game");
			currentPlayer.setFill(Color.BLACK);
			
			//If the game is being recorded, then write out who won the game.
			if(recordGame.isSelected()) {
				recording.writeConclusion(gameBoardLogic.isDrawGame(), " ");
			}
			
			
			
		//It will now be the Red player's turn so disable the Blue Player's Buttons and enable the Red Player's.
		}else if(!gameBoardLogic.isGameOver() && gameBoardLogic.getCurrentPlayer() == "Red") { 
			
			bluePlayer_S.setDisable(true);
			bluePlayer_O.setDisable(true);
			redPlayer_S.setDisable(false);
			redPlayer_O.setDisable(false);
			currentPlayer.setText("Current Player: Red");
			currentPlayer.setFill(Color.RED); 
			
			//If the blue player is a computer, activate the turn then switch to Red's turn
			if(redPlayer.getPlayerType() == "Computer") {
				redPlayer_S.setDisable(true);
				redPlayer_O.setDisable(true);
				
				switchComputerPlayer(redPlayer);  
				
				
			}
		
		//It will now be the Blue player's turn so disable the Red Player's Buttons and enable the Blue Player's.
		}else if(!gameBoardLogic.isGameOver() && gameBoardLogic.getCurrentPlayer() == "Blue") {
			redPlayer_S.setDisable(true);
			redPlayer_O.setDisable(true);
			bluePlayer_S.setDisable(false);
			bluePlayer_O.setDisable(false);
			currentPlayer.setText("Current Player: Blue");
			currentPlayer.setFill(Color.BLUE);
			
			//If the blue player is a computer, activate the turn then switch the Red's turn
			if(bluePlayer.getPlayerType() == "Computer") {
				bluePlayer_S.setDisable(true);
				bluePlayer_O.setDisable(true);
				
				switchComputerPlayer(bluePlayer);
				
			}
			
		}
		
		
	}
	
	private void switchComputerPlayer(Player computerPlayer) { 
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { 

		    @Override
		    public void handle(ActionEvent event) {
		    	decolorSquares();
		    	Button playedButton = ((ComputerPlayer) computerPlayer).makeAStrategicMove(gameBoardLogic, gameBoardGUI);
				validateSolutionGUI(playedButton, ((ComputerPlayer) computerPlayer).getRecentlyPlacedPiece());
				
				//If the game is being recorded, then write out the move that was made.
				if(recordGame.isSelected()) {
					try {
						recording.writeMove(computerPlayer, ((ComputerPlayer) computerPlayer).getRecentlyPlacedPiece(), ((ComputerPlayer) computerPlayer).getRecentRowIndexMove(), 
										   ((ComputerPlayer) computerPlayer).getRecentColIndexMove());
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} 
				
				try {
					checkForGameOverGUI();
				} catch (IOException e) {
					e.printStackTrace();
				}
		       
		    }
		}));
		
		timeline.play();
		
	}
	
	//Method for coloring squares where a corresponding SOS sequence happened.
	public void colorSquares(Button pressedButton, Directions direction, char playedPiece, String currentPlayer) {
		pressedButton.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
							   + "-fx-opacity: 1;" + "-fx-text-fill: white;");
		
		int row = GridPane.getRowIndex(pressedButton);
		int col = GridPane.getColumnIndex(pressedButton);
		
		for(Node button : gameBoardGUI.getChildren()) {
			if(direction == Directions.TOP && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col) ||
			   GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
						        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.TOP_RIGHT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.RIGHT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.BOTTOM_RIGHT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.BOTTOM && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col) ||
				GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.BOTTOM_LEFT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.LEFT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
		
			}else if(direction == Directions.TOP_LEFT && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.TOP && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col) ||
				GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.TOP_RIGHT && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.RIGHT && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == Directions.BOTTOM_RIGHT && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
			} 
		} 
		
	}
	
	//Method for setting the buttons to the default color
	public void decolorSquares() {
		for(Node button : gameBoardGUI.getChildren()) {
			button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-opacity: 1;" + "-fx-text-fill: black;");
		}
	}
	
	//Closes the file for recording moves. This gets called after the application has closed.
	public void closeRecorder() throws IOException {
		recording.closeFile();
	}
	

}

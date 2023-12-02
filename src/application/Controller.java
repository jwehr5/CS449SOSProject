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
	private GridPane gameBoard;
	
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
	
	//Boolean that keeps track if the game has started or not.
	private boolean gameHasStarted = false;
	
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
	private Board b;
	
	//These will be represent the players of the game
	private Player bluePlayer;
	private Player redPlayer;
	
	//When playing against a computer player the Timeline will be used to slowly show the computer's moves
	Timeline timeline;
	
	//CheckBox that allows the player to record the game
	@FXML
	private CheckBox recordGame;
	
	//If the player chooses to record the game, then this will allow us to create and to write to a file.
	FileWriter recording;
	
	
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
		gameBoard.getChildren().clear();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				Button b = new Button();
				gameBoard.add(b,i,j);
				b.setPrefWidth(50);
				b.setPrefHeight(50);
				b.setStyle("-fx-border-color: black;");
				b.setOnAction(arg01 -> {
					try {
						placePieces(arg01);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});  
			}
		}
		
		//Initialize the file on which the game will be recorded on when the player chooses to record the game.
		try {
			recording = new FileWriter("moves.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//This method sets the board size to what the user selects from the choice box.
	public void setBoardSize(ActionEvent e) {
		size = boardSizes.getValue();
		
		//Clear the existing game board so that a new one can be initialized to the new size.
		gameBoard.getChildren().clear();
		
		//Initialize the new game board. Each cell will contain a button that the player will click on when placing their piece.
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				Button b = new Button();
				gameBoard.add(b,i,j);
				b.setPrefWidth(50);
				b.setPrefHeight(50);
				b.setStyle("-fx-border-color: black;");
				b.setOnAction(arg0 -> {
					try {
						placePieces(arg0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				
			}
			
		}
		
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
		System.out.println("First print statement in startGame");
		
		restartButton.setDisable(false);
		
		//Initialize the board class to the size that the user selected.
		if(simpleGameIsSelected) {
			System.out.println("When starting a new game, board size is: " + size); 
			b = new SimpleGameBoard(size);
			
			//If the game is being recorded then write the type of game that is being played.
			if(recordGame.isSelected()) {
				recording.write("Starting a Simple Game with a board size of " + b.getBoardSize());
				recording.write(System.getProperty("line.separator"));
				
			}
			
			
			
		}else if(generalGameIsSelected) {
			b = new GeneralGameBoard(size);
			bluePlayerScore.setText("Score: 0");
			redPlayerScore.setText("Score: 0");
			
			//If the game is being recorded then write the type of game that is being played.
			if(recordGame.isSelected()) { 
				recording.write("Starting a General Game with a board size of " + b.getBoardSize());
				recording.write(System.getProperty("line.separator"));
				
				
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
			Button playedButton = ((ComputerPlayer) bluePlayer).makeAStrategicMove(b, gameBoard);
			validateSolutionGUI(b, playedButton, ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece());
			
			//If the game is being recorded, then write out the move that was made.
			if(recordGame.isSelected()) {
				recording.write("Blue places an '" + ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece() + "' at cell ("
						+ ((ComputerPlayer) bluePlayer).getRecentRowIndexMove() + "," + ((ComputerPlayer) bluePlayer).getRecentColIndexMove() + ")");
				recording.write(System.getProperty("line.separator"));
			}
			
			checkForGameOverGUI(b);
			
			 
			
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
		gameBoard.getChildren().clear();
		b.clearBoard();
		size = 3;
		boardSizes.setValue(3);
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				Button b = new Button();
				gameBoard.add(b,i,j);
				b.setPrefWidth(50);
				b.setPrefHeight(50);
				b.setStyle("-fx-border-color: black;");
				b.setOnAction(arg0 -> {
					try {
						placePieces(arg0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				
			}
			
		}
		
		
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
		if(!b.isGameOver() && b.getCurrentPlayer() == "Blue") {
		
			//If the Blue Player selected S, then place an S at the cell that they clicked on.
			if(bluePlayer_S.isSelected()) {
				//Place the piece on the GUI board
				bluePlayer.placePieceOnGUIBoard(pressedButton, "S");

				//Make the corresponding move in the Board class placing an S at the right row and column.
				b.makeMove(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), 'S'); 
				
				//If the game is being recorded, then write out the move that was made.
				if(recordGame.isSelected()) {
					recording.write("Blue places an 'S' at cell (" + GridPane.getRowIndex(pressedButton) + ","
									+ GridPane.getColumnIndex(pressedButton) + ")");
					recording.write(System.getProperty("line.separator"));
				}
				
				//Validate the solution by coloring in the appropriate cells
				validateSolutionGUI(b, pressedButton, 'S');
				
				//Check for a game over
				checkForGameOverGUI(b);
				
				
			//If the Blue Player selected O, then place an S at the cell that they clicked on.
			}else if(bluePlayer_O.isSelected()) {
				//Place the piece on the GUI board
				bluePlayer.placePieceOnGUIBoard(pressedButton, "O");
				
				//Make the corresponding move in the Board class placing an O at the right row and column.
				b.makeMove(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), 'O');
				
				//If the game is being recorded, then write out the move that was made.
				if(recordGame.isSelected()) {
					recording.write("Blue places an 'O' at cell (" + GridPane.getRowIndex(pressedButton) + ","
									+ GridPane.getColumnIndex(pressedButton) + ")");
					recording.write(System.getProperty("line.separator"));
				}
				
				//Validate the solution by coloring in the appropriate cells
				validateSolutionGUI(b, pressedButton, 'O');
				
				//Check for a game over
				checkForGameOverGUI(b);
				
				
			} 
			 
		//Check to see if the game has started and that its the Red player's turn.
		}else if(!b.isGameOver() && b.getCurrentPlayer() == "Red") {
			
			//If the Red Player selected S, then place an S at the cell that they clicked on.
			if(redPlayer_S.isSelected()) {
				//Place the piece on the GUI board
				redPlayer.placePieceOnGUIBoard(pressedButton, "S");
				
				//Make the corresponding move in the Board class placing an S at the right row and column.
				b.makeMove(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), 'S');
				
				//If the game is being recorded, then write out the move that was made.
				if(recordGame.isSelected()) {
					recording.write("Red places an 'S' at cell (" + GridPane.getRowIndex(pressedButton) + ","
									+ GridPane.getColumnIndex(pressedButton) + ")");
					recording.write(System.getProperty("line.separator"));
				}
				
				//Validate the solution by coloring in the appropriate cells
				validateSolutionGUI(b, pressedButton, 'S');
				
				//Check for a game over
				checkForGameOverGUI(b);
				
				
			//If the Red Player selected O, then place an O at the cell that they clicked on.
			}else if(redPlayer_O.isSelected()) {
				//Place the piece on the GUI board
				redPlayer.placePieceOnGUIBoard(pressedButton, "O");
				
				//Make the corresponding move in the Board class placing an O at the right row and column.
				b.makeMove(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), 'O');
				
				//If the game is being recorded, then write out the move that was made.
				if(recordGame.isSelected()) {
					recording.write("Red places an 'O' at cell (" + GridPane.getRowIndex(pressedButton) + ","
									+ GridPane.getColumnIndex(pressedButton) + ")");
					recording.write(System.getProperty("line.separator"));
				}
				
				//Validate the solution by coloring in the appropriate cells
				validateSolutionGUI(b, pressedButton, 'O');
				
				//Check for a game over
				checkForGameOverGUI(b);
				
			}
			 
		}
	}
	
	//This method will check if any solutions were found and activate the coloring of cells
	public void validateSolutionGUI(Board b, Button pressedButton, char playedPiece) {
		//If direction is not equal to -1, then that means a solution was found.
		if(b.solutions.size() != 0) {
			//System.out.println("Valid SOS sequence");
			
			//Color the corresponding squares where the SOS sequence happened for each solution.
			for(int i = 0; i < b.solutions.size(); i++) {
				
				colorSquares(GridPane.getRowIndex(pressedButton), GridPane.getColumnIndex(pressedButton), 
						b.solutions.get(i), playedPiece , pressedButton, b.getCurrentPlayer());
				
			}
			
			//If a general game is being played, update the Blue or Red player's score.
			if(generalGameIsSelected && b.getCurrentPlayer() == "Blue") {
				bluePlayerScore.setText("Score: " + ((GeneralGameBoard) b).getBluePoints());
			}else if(generalGameIsSelected && b.getCurrentPlayer() == "Red") {
				redPlayerScore.setText("Score: " + ((GeneralGameBoard) b).getRedPoints()); 
			}
			
			
		}
		
	}
	
	//This method will end the game if the game is over
	public void checkForGameOverGUI(Board b) throws IOException {
		//If the game is over, disable the buttons for both players and display who won
		if(b.isGameOver() && !b.isDrawGame()) { 
			
			bluePlayer_S.setDisable(true);
			bluePlayer_O.setDisable(true);
			redPlayer_S.setDisable(true);
			redPlayer_O.setDisable(true); 
			
			//If a general game is being played, then whoever scored the most points wins the game.
			if(generalGameIsSelected) {
				if(((GeneralGameBoard) b).getBluePoints() > ((GeneralGameBoard) b).getRedPoints()) {
					currentPlayer.setText("Blue has won the game" + "\n Click Restart to initiate a new game");
					currentPlayer.setFill(Color.BLUE);
				}else {
					currentPlayer.setText("Red has won the game" + "\n Click Restart to initiate a new game");
					currentPlayer.setFill(Color.RED);
				}
			}else {
				currentPlayer.setText(b.getCurrentPlayer() + " has won the game" + "\n Click Restart to initiate a new game");
				if(b.getCurrentPlayer() == "Blue") {
					currentPlayer.setFill(Color.BLUE);
				}else {
					currentPlayer.setFill(Color.RED);
				}
			}
			
			//If the game is being recorded, then write out who won the game.
			if(recordGame.isSelected()) {
				recording.write("Game is finished with " + b.getWinner() + " being the winner");
				recording.write(System.getProperty("line.separator"));
				recording.write(System.getProperty("line.separator")); 
			}
			
		
			
			
		//If its a draw game, then display that the game ended in a draw.	
		}else if(b.isGameOver() && b.isDrawGame()){  
			
			bluePlayer_S.setDisable(true);
			bluePlayer_O.setDisable(true);
			redPlayer_S.setDisable(true);
			redPlayer_O.setDisable(true); 
			currentPlayer.setText("Draw Game" + "\n Click Restart to initiate a new game");
			currentPlayer.setFill(Color.BLACK);
			
			//If the game is being recorded, then write out who won the game.
			if(recordGame.isSelected()) {
				recording.write("Game is finished with " + b.getWinner() + " being the winner");
				recording.write(System.getProperty("line.separator"));
				recording.write(System.getProperty("line.separator"));
			}
			
			
			
		//It will now be the Red player's turn so disable the Blue Player's Buttons and enable the Red Player's.
		}else if(!b.isGameOver() && b.getCurrentPlayer() == "Red") { 
			
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
				
				 timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { 

				    @Override
				    public void handle(ActionEvent event) {
				    	decolorSquares();
				    	Button playedButton = ((ComputerPlayer) redPlayer).makeAStrategicMove(b, gameBoard);
						validateSolutionGUI(b, playedButton, ((ComputerPlayer) redPlayer).getRecentlyPlacedPiece());
						
						//If the game is being recorded, then write out the move that was made.
						if(recordGame.isSelected()) { 
							try {
								recording.write("Red places an '" + ((ComputerPlayer) redPlayer).getRecentlyPlacedPiece() + "' at cell ("
										+ ((ComputerPlayer) redPlayer).getRecentRowIndexMove() + "," + ((ComputerPlayer) redPlayer).getRecentColIndexMove() + ")");
								recording.write(System.getProperty("line.separator"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						
						System.out.println("Calling checkForGameOverGUI As a red Computer Player");
						try {
							checkForGameOverGUI(b);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
				       
				    }
				}));
				
				timeline.play();  
				
				
				
			}
		
		//It will now be the Blue player's turn so disable the Red Player's Buttons and enable the Blue Player's.
		}else if(!b.isGameOver() && b.getCurrentPlayer() == "Blue") {
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
				
				 timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { 

				    @Override
				    public void handle(ActionEvent event) {
				    	decolorSquares();
				    	Button playedButton = ((ComputerPlayer) bluePlayer).makeAStrategicMove(b, gameBoard);
						validateSolutionGUI(b, playedButton, ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece());
						
						//If the game is being recorded, then write out the move that was made.
						if(recordGame.isSelected()) {
							try {
								recording.write("Blue places an '" + ((ComputerPlayer) bluePlayer).getRecentlyPlacedPiece() + "' at cell ("
										+ ((ComputerPlayer) bluePlayer).getRecentRowIndexMove() + "," + ((ComputerPlayer) bluePlayer).getRecentColIndexMove() + ")");
								recording.write(System.getProperty("line.separator"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						
						System.out.println("Calling checkForGameOverGUI As a blue Computer Player");
						try {
							checkForGameOverGUI(b);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				       
				    }
				}));
				
				timeline.play();
				
			}
			
		}
		
		
		
	}
	
	//Method for coloring squares where a corresponding SOS sequence happened.
	public void colorSquares(int row, int col, int direction, char playedPiece, Button pressedButton, String currentPlayer) {
		pressedButton.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
							   + "-fx-opacity: 1;" + "-fx-text-fill: white;");
		
		for(Node button : gameBoard.getChildren()) {
			if(direction == 0 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col) ||
			   GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
						        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 1 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 2 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 3 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col + 1) ||
			   GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col + 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 4 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col) ||
				GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 5 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row + 2 && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 6 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
		
			}else if(direction == 7 && playedPiece == 'S' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col - 1) ||
				GridPane.getRowIndex(button) == row - 2 && GridPane.getColumnIndex(button) == col - 2)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 0 && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col) ||
				GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 1 && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 2 && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
				
			}else if(direction == 3 && playedPiece == 'O' && ((GridPane.getRowIndex(button) == row + 1 && GridPane.getColumnIndex(button) == col + 1) ||
				GridPane.getRowIndex(button) == row - 1 && GridPane.getColumnIndex(button) == col - 1)) {
				button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-background-color: " + currentPlayer + ";" 
				        + "-fx-opacity: 1;" + "-fx-text-fill: white;");
			}
		} 
		
	}
	
	//Method for setting the buttons to the default color
	public void decolorSquares() {
		for(Node button : gameBoard.getChildren()) {
			button.setStyle("-fx-font-size: 20px;" + "-fx-border-color: black;" + "-fx-opacity: 1;" + "-fx-text-fill: black;");
		}
	}
	

}

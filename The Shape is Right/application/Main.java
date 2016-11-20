package application;
	
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class Main extends Application {
	
	public int N; //number of shapes
	public int minN = 3; //minimum number of shapes
	public int maxN = 7; //maximum number of shapes
    public static final int MAX_TRIALS = 3; //maximum number of trials
    public int trial = 1; //current trial number
	public int score; //current score
	public ObservableList<String> shapeInput;
	public ObservableList<String> colorInput;
	public Random shapeRand;
	public Random colorRand;
	ArrayList<String> combinationDisplayKey = new ArrayList<String>();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			root.setGridLinesVisible( false );
			root.getStyleClass().add("graytheme");
			root.getRowConstraints().add( new RowConstraints(300) ); //split main GridPane in half
			root.getRowConstraints().add( new RowConstraints(300) );

			/*
			 * Primary control pane where all user interaction will take place
			 */
			GridPane controlBox = new GridPane();
			controlBox.setGridLinesVisible( false ); //visible grid lines before final version
			controlBox.getStyleClass().add("gridtheme");
			controlBox.setMinSize(1200.0, 275.0);
			controlBox.setMaxSize(1200.0, 275.0);
			controlBox.setPrefSize(1200.0, 275.0);
			controlBox.getRowConstraints().add( new RowConstraints(70) );
			controlBox.getRowConstraints().add( new RowConstraints(70) );
			controlBox.getRowConstraints().add( new RowConstraints(70) );
			controlBox.getRowConstraints().add( new RowConstraints(70) );
			controlBox.getColumnConstraints().add( new ColumnConstraints(150) );
			controlBox.getColumnConstraints().add( new ColumnConstraints(150) );
			controlBox.getColumnConstraints().add( new ColumnConstraints(150) );

            //closeButton
			Button closeButton = new Button();
			closeButton.setText( "Close" );
			closeButton.getStyleClass().add("buttontheme");
			
			//submitButton
			Button submitButton = new Button();
			submitButton.setText( "Submit" );
			submitButton.getStyleClass().add("buttontheme");

            //guessButton
            Button guessButton = new Button();
            guessButton.setText("Guess!");
            guessButton.setVisible(false);
            guessButton.getStyleClass().add("buttontheme");
            
            Button restartButton = new Button();
            restartButton.setText("Restart");
            restartButton.setVisible(false);
            restartButton.getStyleClass().add("buttontheme");
            
            //scoreLabel
            Label scoreLabel = new Label("Score: " + score + "/" + 3*N);
			
			ArrayList<ComboBox> guessInputFields = new ArrayList<ComboBox>();
			ArrayList<Shape> shapeDisplay = new ArrayList<Shape>();
			
			/*
			 * Creation of selectN ComboBox populated with odd values from
			 * minN to maxN
			 */
			ArrayList<Integer> choiceArrayList = new ArrayList<Integer>();
			for ( int i = minN; i <= maxN; i+=2 ){
				choiceArrayList.add( i );
			}
			ObservableList<Integer> choiceObservList = FXCollections.observableArrayList(choiceArrayList);
			ComboBox<Integer> selectN = new ComboBox<Integer>();
			selectN.setItems( choiceObservList );
			selectN.setPromptText( "Number of Shapes:" );

			/*
			 * Creation of shapeList ListView populated with Strings representing
			 * possible shapes. Final decision on how to implement shapes is yet to
			 * be made. These Strings are currently simply place-holders for GUI
			 * construction.
			 */
			ObservableList<String> shapeArrayList = FXCollections.observableArrayList("Circle", "Rectangle", "Triangle", "Hexagon");
			ListView<String> shapeList = new ListView<String>();
			shapeList.setItems( shapeArrayList );
			shapeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/*
			 * Creation of colorList ListView populated with Strings representing
			 * possible colors. Final decision on how to implement colors is yet to
			 * be made. These Strings are currently simply place-holders for GUI
			 * construction.
			 */
			ObservableList<String> colorArrayList = FXCollections.observableArrayList("Red", "Green", "Blue", "Yellow");
			ListView<String> colorList = new ListView<String>();
			colorList.setItems( colorArrayList );
			colorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			//Add controls to controlBox
			controlBox.getChildren().add( closeButton );
            controlBox.getChildren().add( guessButton );
            controlBox.getChildren().add( submitButton );
			controlBox.getChildren().add( selectN );
			controlBox.getChildren().add( shapeList );
			controlBox.getChildren().add( colorList );
			controlBox.getChildren().add( scoreLabel );
			controlBox.getChildren().add( restartButton );
			
			//Assigning nodes to grid positions
			GridPane.setConstraints( closeButton, 1, 3 );
            GridPane.setConstraints( guessButton, 3, 2 );
            GridPane.setConstraints( submitButton, 1, 2 );
			GridPane.setConstraints( selectN, 1, 0 );
			GridPane.setConstraints( shapeList, 0, 1 , 1, 2);
			GridPane.setConstraints( colorList, 2, 1 , 1, 2);
			
			for ( int i = 0; i < guessInputFields.size(); i++ ){
				controlBox.getChildren().add( guessInputFields.get( i ) );
				GridPane.setConstraints( guessInputFields.get( i ), i, 0);
				guessInputFields.get( i ).setVisible( false );
			}
			
			//setHalignment and setValignments for controls in the buttonBox GridPane
			GridPane.setHalignment( closeButton, HPos.CENTER );
            GridPane.setHalignment( guessButton, HPos.CENTER );
            GridPane.setHalignment( submitButton, HPos.CENTER );
			GridPane.setHalignment(selectN, HPos.CENTER);
			GridPane.setValignment( shapeList, VPos.CENTER );
			GridPane.setValignment(colorList, VPos.CENTER);
			
			root.add( controlBox, 0, 1 );

            /*
                GridPane to show shapes after hitting the submitButton and guessing.
            */
            GridPane displayBox = new GridPane();
            displayBox.setGridLinesVisible(false); //visible grid lines before final version
            displayBox.getStyleClass().add("gridtheme");
            displayBox.setMinSize(1200.0, 275.0);
            displayBox.setMaxSize(1200.0, 275.0);
            displayBox.setPrefSize(1200.0, 275.0);
            displayBox.getRowConstraints().add( new RowConstraints(275) );
            displayBox.getColumnConstraints().add( new ColumnConstraints(150) );
            displayBox.getColumnConstraints().add( new ColumnConstraints(150) );
            displayBox.getColumnConstraints().add(new ColumnConstraints(150));

            primaryStage.setTitle("The Shape is Right!");

            root.add( displayBox, 0, 0 );

            ArrayList<Text> shapeMasks = new ArrayList<Text>();
            // Set up masks for shapes '?'
            for(int i = 0; i < 7; i++) {
                Text temp = new Text("?");
                temp.setFont(new Font(100));
                GridPane.setConstraints(temp, i, 0);
                GridPane.setHalignment(temp, HPos.CENTER);
                shapeMasks.add(temp);
            }

            // Set up the scene, style it
            Scene scene = new Scene( root, 1200, 570 );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//EventHandler for shapeList ListView to get shape choices from user.
			shapeList.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						public void onChanged
						( ListChangeListener.Change<? extends String> c){
							shapeInput = shapeList.getSelectionModel().getSelectedItems() ;
						}	
					});

			//EventHandler for colorList ListView to get color choices from user.
			colorList.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						public void onChanged
						( ListChangeListener.Change<? extends String> c){
							colorInput = colorList.getSelectionModel().getSelectedItems() ;
						}	
					});

			//EventHandler for submitButton Button to gather game setup info from user.
			submitButton.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ArrayList<String> combinationDisplay = new ArrayList<String>();
                    ObservableList<String> combinationComboBoxDisplay;

					shapeList.setVisible( false );
					colorList.setVisible( false );
					selectN.setVisible( false );
					submitButton.setVisible( false );
                    guessButton.setVisible(true);
                    restartButton.setVisible(true);

					shapeRand = new Random();
					colorRand = new Random();

					scoreLabel.setText("Score: " + score + "/" + 3*N);
					
                    for(int i = 0; i < N - 3; i++) {
                        controlBox.getColumnConstraints().add( new ColumnConstraints(150) );
                        displayBox.getColumnConstraints().add( new ColumnConstraints(150) );
                    }

                    for(int i = 0; i < N; i++) {
                        // get a random shape, add it to array
                    	String shape = shapeInput.get(shapeRand.nextInt(shapeInput.size()));
                        shapeDisplay.add(buildCardObject(shape, i));
                        // fill that shape with random color
                        String color = colorInput.get(colorRand.nextInt(colorInput.size()));
                        shapeDisplay.get(i).setFill(buildColorEnum(color));
                        // set a white stroke to make it more visible
                        shapeDisplay.get(i).setStroke(Color.WHITE);
                        GridPane.setConstraints(shapeDisplay.get(i), i, 0);
                        displayBox.getChildren().add(shapeMasks.get(i));
                        //displayBox.getChildren().add(shapeDisplay.get(i));
                        // add the combination to a list of all combinations
                        combinationDisplay.add(color + " " + shape);
                    }

                    GridPane.setConstraints(closeButton, 3, 3);
                    GridPane.setConstraints(restartButton, 2, 3);

                    //Creates an ArrayList to store a copy of the original shape ordering.
                    combinationDisplayKey.addAll( combinationDisplay );
                                  
                    // initialize list of options
                    Collections.shuffle(combinationDisplay, new Random());
                    combinationComboBoxDisplay = FXCollections.observableArrayList(combinationDisplay);
                    
                    // add ComboBoxes to GUI
                    for(int i = 0; i < N; i++) {
                        guessInputFields.add( new ComboBox<String>(combinationComboBoxDisplay) );
                        GridPane.setConstraints(guessInputFields.get(i), i, 1 );
                        GridPane.setHalignment(guessInputFields.get(i), HPos.CENTER);                  
                        controlBox.getChildren().add( guessInputFields.get(i) );
                        guessInputFields.get(i).setVisible(true);
                    }
                    
                    
				}
			});

			//EventHandler for selectN ComboBox to get N value from user.
			selectN.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					N = selectN.getValue();
				}
			});
			

            //EventHandler for guessButton Button to process user input for guesses.
            guessButton.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle( ActionEvent event ) {
                    try {
                    	      
                    	String answer;
                    	String guess;
                    	ArrayList<Integer> correctGuesses = new ArrayList<Integer>();
                    	
                    	trial++;
                    	
                    	//incomplete implementation
                    	if (trial > MAX_TRIALS) {
                    		guessButton.setDisable(true);
                    		scoreLabel.setText("Game over.\nFinal Score: " + score + "/" + 3*N);
                    		
                    	}
                    	
                    	
                    	for (int i = 0; i < N; i++) {
                    		answer = combinationDisplayKey.get(i);
                    		guess = (String) guessInputFields.get(i).getValue();
                    		
                    		if (answer.equals(guess)){
                    			
                    			score++;
                                scoreLabel.setText("Score: " + score + "/" + 3*N);
                                correctGuesses.add(i);
                                                            
                    		}
                    	}
                    	
                    	
                    	for (int i = 0; i < correctGuesses.size(); i++) {
                    		int guessNum = correctGuesses.get(i);
                    		int newCol = GridPane.getColumnIndex(displayBox.getChildren().get(guessNum));
                    		int newRow = GridPane.getRowIndex(displayBox.getChildren().get(guessNum));
                    		
                			FadeTransition fadeOut = new FadeTransition();
                            fadeOut.setDuration(new Duration(1000));
                            fadeOut.setNode(displayBox.getChildren().get(guessNum));
                            fadeOut.setFromValue(1.0);
                            fadeOut.setToValue(0.0);
                            fadeOut.play();
                    		
                    		Shape correctShape = shapeDisplay.get(guessNum);
                    		
                    		try {
                    			displayBox.getChildren().add(correctShape);
                    		} catch (Exception e){
                    			// e.printStackTrace(System.err);
                    		}
                    		
                    		System.out.println(newCol + ", " + newRow);
                    		GridPane.setConstraints(correctShape, newCol, newRow);
                    		FadeTransition fadeIn = new FadeTransition();
                            fadeIn.setDuration(new Duration(1000));
                            fadeIn.setNode(correctShape);
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();
                    	}
                    	
                    } catch ( Exception e ) {
                        e.printStackTrace(System.err);
                    }
                }
            });
			
            
            
            
			//EventHandler for closeButton Button to gracefully exit program.
			closeButton.setOnAction( new EventHandler<ActionEvent>() {
				@Override
				public void handle( ActionEvent event ) {
					try {
						primaryStage.close();
						Platform.exit(); 		
					} catch ( Exception e ) {
						e.printStackTrace(System.err);
					}
				}
			});

			primaryStage.setScene( scene );
			primaryStage.setResizable( false );
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    public static Shape buildCardObject(String selection, int index) {
        String selectedShape = selection.toLowerCase();

        switch (selectedShape) {
            case "rectangle":
                return new Rectangle(150, 30);
            case "circle":
                return new Circle(75);
            case "triangle": {
                Triangle temp = new Triangle(75, 100, 75 - index * 75, 60);
                return temp.getTriangle();
            }
            case "hexagon": {
                Hexagon temp = new Hexagon(150, 100, index * 150, 50);
                return temp.getHexagon();
            }
        }
        return null; // you should never hit this
    }

    public static Color buildColorEnum(String selection) {
        String selectedColor = selection.toLowerCase();

        switch (selectedColor) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
        }
        return null; //you should never hit this
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}

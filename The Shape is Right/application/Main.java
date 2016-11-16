package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Main extends Application {
	
	public int N;
	public int minN = 3;
	public int maxN = 7;
	public int score;
	public ObservableList<String> shapeInput;
	public ObservableList<String> colorInput;
	public Random shapeRand;
	public Random colorRand;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = new GridPane();
			root.setGridLinesVisible( true );
			root.getStyleClass().add("graytheme");
			root.getRowConstraints().add( new RowConstraints(300) ); //split main GridPane in half
			root.getRowConstraints().add( new RowConstraints(300) );
			
						
			/*
			 * Primary control pane where all user interaction will take place
			 */
			GridPane controlBox = new GridPane();
			controlBox.setGridLinesVisible( true ); //visible grid lines before final version
			controlBox.getStyleClass().add("gridtheme");
			controlBox.setMinSize(600.0, 300.0);
			controlBox.setMaxSize(600.0, 300.0);
			controlBox.setPrefSize(600.0, 300.0);
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
			
			
			Button submitButton = new Button();
			submitButton.setText( "Submit" );
			submitButton.getStyleClass().add("buttontheme");
			
			ArrayList<ComboBox> guessInputFields = new ArrayList<ComboBox>();
			ArrayList<Shape> shapeDisplay = new ArrayList<Shape>();
			
			Map shapes = new HashMap();
			shapes.put( "Circle", new Circle() );
			shapes.put( "Rectangle", new Rectangle() );
			shapes.put( "Circle", new Circle() );
			shapes.put( "Circle", new Circle() );
			
			
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
			controlBox.getChildren().add( submitButton );
			controlBox.getChildren().add( selectN );
			controlBox.getChildren().add( shapeList );
			controlBox.getChildren().add( colorList );
			
			
			//Assigning nodes to grid positions
			GridPane.setConstraints( closeButton, 1, 3 );
			GridPane.setConstraints( submitButton, 1, 2 );
			GridPane.setConstraints( selectN, 1, 0 );
			GridPane.setConstraints( shapeList, 0, 1 , 1, 2);
			GridPane.setConstraints( colorList, 2, 1 , 1, 2);
			
			
			//setHalignment and setValignments for controls in the buttonBox GridPane
			GridPane.setHalignment( closeButton, HPos.CENTER );
			GridPane.setHalignment( submitButton, HPos.CENTER );
			GridPane.setHalignment( selectN, HPos.CENTER );
			GridPane.setValignment( shapeList, VPos.CENTER );
			GridPane.setValignment( colorList, VPos.CENTER );
			
			
			root.add( controlBox, 0, 1 );
			
			
			Scene scene = new Scene( root, 600, 600 );
			scene.getStylesheets().add( getClass().getResource("application.css").toExternalForm() );
			
			
			shapeList.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						public void onChanged
						( ListChangeListener.Change<? extends String> c){
							shapeInput = shapeList.getSelectionModel().getSelectedItems() ;
						}	
					});
			
			
			colorList.getSelectionModel().getSelectedItems().addListener(
					new ListChangeListener<String>() {
						public void onChanged
						( ListChangeListener.Change<? extends String> c){
							colorInput = colorList.getSelectionModel().getSelectedItems() ;
						}	
					});
			
			
			submitButton.setOnAction( new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					shapeList.setVisible( false );
					colorList.setVisible( false );				
					selectN.setVisible( false );				
					submitButton.setVisible( false );		
					
					shapeRand = new Random( shapeInput.size() );
					colorRand = new Random( colorInput.size() );

                    for(int i = 0; i < N; i++) {
                        // get a random shape, add it to array
                        shapeDisplay.add(buildCardObject(shapeInput.get(shapeRand.nextInt())));
                        // fill that shape with random color
                        shapeDisplay.get(i).setFill(buildColorEnum(colorInput.get(colorRand.nextInt())));
                        // set a white stroke to make it more visible
                        shapeDisplay.get(i).setStroke(Color.WHITE);
                    }
					
					for ( int i = 0; i < N; i++ ) {
						guessInputFields.add( new ComboBox<String>() );
					}
														
				}
				
			});


			selectN.setOnAction( new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					N = selectN.getValue();
				}
				
			});
			
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

    public static Shape buildCardObject(String selection) {
        String selectedShape = selection.toLowerCase();

        if(selectedShape == "rectangle") {
            return new Rectangle();
        } else if(selectedShape == "circle") {
            return new Circle();
        } else if(selectedShape == "triangle") {
            Triangle temp = new Triangle();
            return temp.getTriangle();
        } else if(selectedShape == "hexagon") {
            Hexagon temp = new Hexagon();
            return temp.getHexagon();
        }
        return null; // you should never hit this
    }

    public static Color buildColorEnum(String selection) {
        String selectedColor = selection.toLowerCase();

        if(selectedColor == "red") {
            return Color.RED;
        } else if(selectedColor == "green") {
            return Color.GREEN;
        } else if(selectedColor == "blue") {
            return Color.BLUE;
        } else if(selectedColor == "yellow") {
            return Color.YELLOW;
        }
        return null; //you should never hit this
    }
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
}

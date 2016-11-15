package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class Main extends Application {
	
	public int N;
	public int minN = 3;
	public int maxN = 7;
	
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
			//controlBox.setGridLinesVisible( true ); //disable grid lines before final version
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
			
			
			
			//quitButton
			Button quitButton = new Button();
			quitButton.setText( "Quit" );
			quitButton.getStyleClass().add("buttontheme");
			
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
			controlBox.getChildren().add( quitButton );
			controlBox.getChildren().add( selectN );
			controlBox.getChildren().add( shapeList );
			controlBox.getChildren().add( colorList );
			
			
			//Assigning nodes to grid positions
			GridPane.setConstraints( quitButton, 1, 3 );
			GridPane.setConstraints( selectN, 1, 0 );
			GridPane.setConstraints( shapeList, 0, 1 , 1, 2);
			GridPane.setConstraints( colorList, 2, 1 , 1, 2);
			
			
			//setHalignment and setValignments for controls in the buttonBox GridPane
			GridPane.setHalignment( quitButton, HPos.CENTER );
			GridPane.setHalignment( selectN, HPos.CENTER );
			GridPane.setValignment( shapeList, VPos.CENTER );
			GridPane.setValignment( colorList, VPos.CENTER );
			
			
			root.add( controlBox, 0, 1 );
			
			
			Scene scene = new Scene( root, 600, 600 );
			scene.getStylesheets().add( getClass().getResource("application.css").toExternalForm() );
			
			
			quitButton.setOnAction( new EventHandler<ActionEvent>() {
				
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
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
}

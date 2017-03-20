package userinterface;

import java.awt.Font;

import com.sun.javafx.geom.Shape;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class UserInterface {
	
	private final InterfaceActions interfaceActions;
	
	private Stage primaryStage;
	private Button helloButton;
	private Sphere sphere;
	
	public UserInterface(InterfaceActions interfaceActions, Stage primaryStage) {
		this.interfaceActions = interfaceActions;
		this.primaryStage = primaryStage;
	}
	
	public void setDefaultTitle() {
        this.primaryStage.setTitle("Capstone Project - Rory S.");
	}
	
	public void setTitle(String string) {
        this.primaryStage.setTitle(string);
	}
	
	public void setupInterface() {
		this.setDefaultTitle();
		this.setupButtons();
		this.setupPane();
	}
	
	private void setupButtons() {
		this.helloButton = new Button();
        this.helloButton.setText("Say 'Hello World'");
        this.helloButton.setMinSize(200, 200);
        this.helloButton.setRotate(10);
        
        this.sphere = new Sphere();
        
       this.interfaceActions.registerAllActions(this.helloButton);
	}
	
	private void setupPane() {
        Group root = new Group();
        //root.getChildren().add(this.helloButton);
        root.getChildren().add(this.sphere);
        //this.primaryStage.setScene(new Scene(root, 1920, 920));
        this.primaryStage.setScene(new Scene(root, 1620, 720));
	}
	
}

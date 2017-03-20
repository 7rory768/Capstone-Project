package userinterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class InterfaceActions {
	
	public void registerHelloWorldAction(final Button helloWorldButton) {
		helloWorldButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				System.out.println("Hello world!");
				helloWorldButton.setRotate(helloWorldButton.getRotate() + 10);
			}});
	}
	
	public void registerAllActions(Button helloWorldButton) {
		this.registerHelloWorldAction(helloWorldButton);
	}

}

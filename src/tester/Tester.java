package tester;

import managers.LightManager;
import managers.PrismManager;
import userinterface.UserInterface;

public class Tester {

	private static final PrismManager prismManager = new PrismManager();
	private static final LightManager lightManager = new LightManager();
	private static final UserInterface userInterface = new UserInterface(Tester.prismManager);

	public static void setupInterface() {
		Tester.userInterface.setupInterface();
	}
	
	public static void main(String[] args) {
		Tester.setupInterface();
	}

}
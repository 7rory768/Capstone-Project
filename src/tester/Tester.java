package tester;

import managers.LightManager;
import managers.PrismManager;
import userinterface.InstructionFrame;
import userinterface.InterfaceActions;
import userinterface.KeyboardListener;
import userinterface.UserInterface;

public class Tester {

	private static final PrismManager prismManager = new PrismManager();
	private static final LightManager lightManager = new LightManager();
	private static final KeyboardListener keyboardListener = new KeyboardListener();
    private static final UserInterface userInterface = new UserInterface();
    private static final InterfaceActions interfaceActions = Tester.userInterface.getInterfaceActions();
    private static final InstructionFrame instructionFrame = new InstructionFrame(Tester.userInterface, Tester.interfaceActions);

	public static void setupInterface() {
        Tester.userInterface.setupInterface();
        Tester.instructionFrame.setup();
        Tester.interfaceActions.registerEvents();
	    Tester.instructionFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Tester.setupInterface();
	}

	public static PrismManager getPrismManager() {
		return Tester.prismManager;
	}

	public static KeyboardListener getKeyboardListener() {
		return Tester.keyboardListener;
	}

    public static InstructionFrame getInstructionFrame() {
	    return Tester.instructionFrame;
    }

}
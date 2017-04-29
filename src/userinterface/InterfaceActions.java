package userinterface;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InterfaceActions {

    public ChangeListener getHeadingChangeListener() {
        return new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                UserInterface.repaint();
            }
        };
    }

    public ChangeListener getPitchChangeListener() {
        return new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                UserInterface.repaint();
            }
        };
    }

}

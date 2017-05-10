package userinterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InterfaceActions {

	private final UserInterface userInterface;
	
	private Component lastSelected;

	public InterfaceActions(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	private void registerSlideEvent(JSlider jSlider) {
		jSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				UserInterface.repaint();
			}
		});
	}

	private void registerAddEvent(final JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLastSelected(button);
			}
		});
	}
	
	private void setLastSelected(JButton button) {
		if (lastSelected != null) {
			lastSelected.setBackground(null);
			lastSelected = null;
		}
		button.setBackground(Color.CYAN);
		lastSelected = button;
	}

	public void registerEvents() {
		this.registerSlideEvent(this.userInterface.getHeadingSlider());
		this.registerSlideEvent(this.userInterface.getPitchSlider());
		this.registerAddEvent(this.userInterface.getAddButton());
	}
}

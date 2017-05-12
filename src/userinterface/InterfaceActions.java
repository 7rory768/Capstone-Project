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
				hideOldComponents();
				setLastSelected(button);
				showNewComponents();
			}
		});
	}
	
	private boolean isLastSelected(JButton button) {
		return this.lastSelected != null && this.lastSelected.equals(button);
	}

	private void setLastSelected(JButton button) {
		boolean sameComponent = false;
		if (this.lastSelected != null) {
			sameComponent = this.lastSelected.equals(button);
			this.lastSelected.setBackground(null);
			this.lastSelected = null;
		}
		if (!sameComponent) {
			button.setBackground(Color.CYAN);
			this.lastSelected = button;
		}
	}
	
	public void hideOldComponents() {
		userInterface.getSelectPrismLabel().setVisible(false);
		userInterface.getCubeButton().setVisible(false);
		userInterface.getEquilaterialButton().setVisible(false);
		userInterface.getPentagonalButton().setVisible(false);
	}
	
	public void showNewComponents() {
		Component newComponent = this.lastSelected;
		if (newComponent.equals(this.userInterface.getAddButton())) {
			userInterface.getSelectPrismLabel().setVisible(true);
			userInterface.getCubeButton().setVisible(true);
			userInterface.getEquilaterialButton().setVisible(true);
			userInterface.getPentagonalButton().setVisible(true);
		}
	}

	public void registerEvents() {
		this.registerSlideEvent(this.userInterface.getHeadingSlider());
		this.registerSlideEvent(this.userInterface.getPitchSlider());
		this.registerAddEvent(this.userInterface.getAddButton());
	}
}

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

	private void registerButtonEvent(final JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (button.equals(userInterface.getCreateButton())) {
					String length, radius, height, xOrigin, yOrigin;
					Color color;
					// get values and create prism
				}
				setLastSelected(button);
				hideOldComponents();
				showNewComponents();
			}
		});
	}

	private void setLastSelected(JButton button) {
		boolean sameComponent = false;
		if (this.lastSelected != null) {
			sameComponent = this.lastSelected.equals(button);
			this.lastSelected.setBackground(null);

			if (sameComponent && (this.lastSelected.equals(this.userInterface.getCubeButton())
					|| this.lastSelected
							.equals(this.userInterface.getEquilateralButton())
					|| this.lastSelected.equals(this.userInterface.getPentagonalButton()))) {
				this.lastSelected = this.userInterface.getAddButton();
				this.lastSelected.setBackground(Color.CYAN);
			} else {
				this.lastSelected = null;
			}
		}
		if (!sameComponent) {
			button.setBackground(Color.CYAN);
			this.lastSelected = button;
		}
	}

	private void hideOldComponents() {
		this.userInterface.getSelectPrismLabel().setVisible(false);
		this.userInterface.getCubeButton().setVisible(false);
		this.userInterface.getEquilateralButton().setVisible(false);
		this.userInterface.getPentagonalButton().setVisible(false);
		this.userInterface.getColorList().setVisible(false);
		this.userInterface.getOriginLabel().setVisible(false);
		this.userInterface.getXLabel().setVisible(false);
		this.userInterface.getYLabel().setVisible(false);
		this.userInterface.getXOriginField().setVisible(false);
		this.userInterface.getYOriginField().setVisible(false);
		this.userInterface.getLengthLabel().setVisible(false);
		this.userInterface.getLengthField().setVisible(false);
		this.userInterface.getRadiusLabel().setVisible(false);
		this.userInterface.getRadiusField().setVisible(false);
		this.userInterface.getHeightLabel().setVisible(false);
		this.userInterface.getHeightField().setVisible(false);
		this.userInterface.getCreateButton().setVisible(false);
	}

	private void showNewComponents() {
		if (this.lastSelected != null) {
			Component newComponent = this.lastSelected;
			if (!newComponent.equals(this.userInterface.getRemoveButton())) {
				this.userInterface.getSelectPrismLabel().setVisible(true);
			}

			if (newComponent.equals(this.userInterface.getAddButton())) {
				this.showPrismButtons();
			} else if (newComponent.equals(this.userInterface.getCubeButton())) {
				this.showPrismButtons();
				this.showPrismCreationComponents();

				this.userInterface.getLengthLabel().setVisible(true);
				this.userInterface.getLengthField().setVisible(true);
			} else if (newComponent.equals(this.userInterface.getEquilateralButton())) {
				this.showPrismButtons();
				this.showPrismCreationComponents();

				this.userInterface.getLengthLabel().setVisible(true);
				this.userInterface.getLengthField().setVisible(true);
			} else if (newComponent.equals(this.userInterface.getPentagonalButton())) {
				this.showPrismButtons();
				this.showPrismCreationComponents();

				this.userInterface.getRadiusLabel().setVisible(true);
				this.userInterface.getRadiusField().setVisible(true);
				this.userInterface.getHeightLabel().setVisible(true);
				this.userInterface.getHeightField().setVisible(true);
			}
		}
	}

	private void showPrismButtons() {
		this.userInterface.getCubeButton().setVisible(true);
		this.userInterface.getEquilateralButton().setVisible(true);
		this.userInterface.getPentagonalButton().setVisible(true);
	}

	private void showPrismCreationComponents() {
		this.userInterface.getColorList().setVisible(true);
		this.userInterface.getOriginLabel().setVisible(true);
		this.userInterface.getXLabel().setVisible(true);
		this.userInterface.getYLabel().setVisible(true);
		this.userInterface.getXOriginField().setVisible(true);
		this.userInterface.getYOriginField().setVisible(true);
		this.userInterface.getCreateButton().setVisible(true);
	}

	public void registerEvents() {
		this.registerSlideEvent(this.userInterface.getHeadingSlider());
		this.registerSlideEvent(this.userInterface.getPitchSlider());
		this.registerButtonEvent(this.userInterface.getAddButton());
		this.registerButtonEvent(this.userInterface.getRemoveButton());
		this.registerButtonEvent(this.userInterface.getCubeButton());
		this.registerButtonEvent(this.userInterface.getEquilateralButton());
		this.registerButtonEvent(this.userInterface.getPentagonalButton());
		this.registerButtonEvent(this.userInterface.getCreateButton());
	}
}

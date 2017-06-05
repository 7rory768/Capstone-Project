package userinterface;

import managers.PrismManager;
import prisms.Prism;
import shapes.Shape;
import util.Matrix3;
import util.Vertex;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrismMouseListener implements MouseListener {

	private final UserInterface userInterface;
	private final PrismManager prismManager;
	private final InterfaceActions interfaceActions;

	private final Timer timer = new Timer();

	private boolean mouseDown = false;
	private Prism movingPrism = null;
	private TimerTask dragTask = null;
	private int oldScreenX;
	private int oldScreenY;

	public PrismMouseListener(final UserInterface userInterface, PrismManager prismManager, InterfaceActions interfaceActions, final KeyboardListener keyboardListener) {
		this.prismManager = prismManager;
		this.userInterface = userInterface;
		this.interfaceActions = interfaceActions;
		this.dragTask = new TimerTask() {
			@Override
			public void run() {
				if (mouseDown) {
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					int xChange = mousePoint.x - oldScreenX;
					int yChange = mousePoint.y - oldScreenY;
					
					if (keyboardListener.holdingCtrl()) {
						oldScreenX = mousePoint.x;
						oldScreenY = mousePoint.y;
						
						Vertex gridOrigin = userInterface.getGridOrigin();
						
//						int oldXPlaces = String.valueOf((int) Math.abs(gridOrigin.getX())).length();
//						int oldYPlaces = String.valueOf((int) Math.abs(gridOrigin.getY())).length();
						gridOrigin.setX(gridOrigin.getX() + xChange);
						gridOrigin.setY(gridOrigin.getY() + yChange);
//						int newXPlaces = String.valueOf((int) Math.abs(gridOrigin.getX())).length();
//						int newYPlaces = String.valueOf((int) Math.abs(gridOrigin.getY())).length();

						userInterface.getXCordLabel().setText("" + (int) gridOrigin.getX());
						userInterface.getYCordLabel().setText("" + (int) gridOrigin.getY());

/*						System.out.println("oldxPlaces: " + oldXPlaces);
						System.out.println("newXPlaces: " + newXPlaces);
						System.out.println("--------------------------------");

						Insets xInsets = userInterface.getXCordLabel().getInsets();
						Insets yInsets = userInterface.getYCordLabel().getInsets();
						userInterface.getPaintPanel().remove(userInterface.getXCordLabel());
						userInterface.getPaintPanel().remove(userInterface.getYCordLabel());

						// INSETS: TOP, LEFT, BOTTOM, RIGHT
						GridBagConstraints constraints = new GridBagConstraints();

						constraints.fill = GridBagConstraints.HORIZONTAL;
						if (oldXPlaces > newXPlaces) {
							xInsets.set(xInsets.top, xInsets.left + -50, xInsets.bottom, xInsets.right);
						} else if (oldXPlaces < newXPlaces) {
							yInsets.set(yInsets.top, yInsets.left, yInsets.bottom, yInsets.right + -50);
						}

						constraints.insets = xInsets;
						userInterface.getPaintPanel().add(userInterface.getXCordLabel(), constraints);

						if (oldYPlaces > newYPlaces) {
							yInsets.set(yInsets.top, yInsets.left + -50, yInsets.bottom, yInsets.right);
						} else if (oldYPlaces < newYPlaces) {
							yInsets.set(yInsets.top, yInsets.left, yInsets.bottom, yInsets.right + -50);
						}

						constraints.gridy = 1;
						constraints.insets = yInsets;
						userInterface.getPaintPanel().add(userInterface.getYCordLabel(), constraints);*/

					} else if (movingPrism != null) {
						Vertex origin = movingPrism.getOrigin();
						if (xChange + origin.getX() < UserInterface.getWidth() / 2 && xChange + origin.getX() >= 0) {
							oldScreenX = mousePoint.x;
							origin.setX(origin.getX() + xChange);
							userInterface.getXOriginField().setText(" " + (int) origin.getX());
						}
						if (yChange + origin.getY() < UserInterface.getHeight() - 35 && yChange + origin.getY() >= 0) {
							oldScreenY = mousePoint.y;
							origin.setY(origin.getY() + yChange);
							userInterface.getYOriginField().setText(" " + (int) origin.getY());
						}
					} else {
						return;
					}
					UserInterface.repaint();
				}
			}
		};
		this.timer.schedule(this.dragTask, 20, 20);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			this.mouseDown = true;
			this.oldScreenX = e.getXOnScreen();
			this.oldScreenY = e.getYOnScreen();
			Prism selectedPrism = this.prismManager.getSelectedPrism();

			double xGridOrigin = this.userInterface.getGridOrigin().getX();
			double yGridOrigin = this.userInterface.getGridOrigin().getY();

			for (Prism prism : this.prismManager.getPrisms()) {
				Matrix3 transform = this.userInterface.getTransform(prism);

				Vertex origin = prism.getOrigin();
				double xOrigin = origin.getX();
				double yOrigin = origin.getY();

				for (Shape shape : prism.getShapes()) {
					List<Vertex> vertexList = new ArrayList<Vertex>();

					for (Vertex vertex : shape.getVertices()) {
						vertexList.add(transform.transform(vertex));
					}

					Path2D path = new Path2D.Double();
					for (int i = 0; i < vertexList.size(); i++) {
						Vertex vertex = vertexList.get(i);
						vertex.setX(vertex.getX() + xOrigin + xGridOrigin);
						vertex.setY(vertex.getY() + yOrigin + yGridOrigin);
						if (i == 0) {
							path.moveTo(vertex.getX(), vertex.getY());
						} else {
							path.lineTo(vertex.getX(), vertex.getY());
						}
					}
					path.closePath();

					if (path.contains(e.getX(), e.getY())) {
						if (selectedPrism == null || !selectedPrism.equals(prism)) {
							this.prismManager.setSelectedPrism(prism);
							this.userInterface.getHeadingSlider().setValue(prism.getHeadingValue());
							this.userInterface.getPitchSlider().setValue(prism.getPitchValue());
							this.userInterface.getHeadingSlider().setVisible(true);
							this.userInterface.getPitchSlider().setVisible(true);
							this.userInterface.getRemoveButton().setVisible(true);
							this.interfaceActions.switchToPrismSelected();

							this.movingPrism = prism;
						} else if (selectedPrism != null) {
							this.movingPrism = prism;
						}
						return;
					}
				}
			}

			if (selectedPrism != null) {
				this.interfaceActions.hideOldComponents();
				UserInterface.repaint();
			}
			this.interfaceActions.switchToNoPrismSelected();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.movingPrism != null) {
			this.movingPrism = null;
		}

		if (e.getButton() == MouseEvent.BUTTON1) {
			this.mouseDown = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

}

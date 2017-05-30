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

	private Prism movingPrism = null;
	private TimerTask dragTask = null;
	private int oldScreenX;
	private int oldScreenY;

	public PrismMouseListener(final UserInterface userInterface, PrismManager prismManager, InterfaceActions interfaceActions) {
		this.prismManager = prismManager;
		this.userInterface = userInterface;
		this.interfaceActions = interfaceActions;
		this.dragTask = new TimerTask() {
			@Override
			public void run() {
				if (movingPrism != null) {
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					Vertex origin = movingPrism.getOrigin();
					int xChange = mousePoint.x - oldScreenX;
					int yChange = mousePoint.y - oldScreenY;
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
					UserInterface.repaint();
				}
			}
		};
		this.timer.schedule(this.dragTask, 30, 30);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Prism selectedPrism = this.prismManager.getSelectedPrism();

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
					vertex.setX(vertex.getX() + xOrigin);
					vertex.setY(vertex.getY() + yOrigin);
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
						this.oldScreenX = e.getXOnScreen();
						this.oldScreenY = e.getYOnScreen();
					} else if (selectedPrism != null) {
						this.movingPrism = prism;
						this.oldScreenX = e.getXOnScreen();
						this.oldScreenY = e.getYOnScreen();
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

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.movingPrism != null) {
			this.movingPrism = null;
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

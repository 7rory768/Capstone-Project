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

	private final Timer timer = new Timer();

	private Prism movingPrism = null;
	private TimerTask dragTask = null;
	private int oldScreenX;
	private int oldScreenY;

	public PrismMouseListener(UserInterface userInterface, PrismManager prismManager) {
		this.prismManager = prismManager;
		this.userInterface = userInterface;
		this.dragTask = new TimerTask() {
			@Override
			public void run() {
				if (movingPrism != null) {
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					int newMouseX = mousePoint.x;
					int newMouseY = mousePoint.y;
					int xChange = newMouseX - oldScreenX;
					int yChange = newMouseY - oldScreenY;
					oldScreenX = newMouseX;
					oldScreenY = newMouseY;
					Vertex origin = movingPrism.getOrigin();
					origin.setX(origin.getX() + xChange);
					origin.setY(origin.getY() + yChange);
					UserInterface.repaint();
				}
			}
		};
		this.timer.schedule(this.dragTask, 30, 30);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		Prism selectedPrism = prismManager.getSelectedPrism();

		for (Prism prism : this.prismManager.getPrisms()) {
			Matrix3 transform = this.userInterface.getTransform(prism);

			Vertex origin = prism.getOrigin();
			double xOrigin = origin.getX();
			double yOrigin = origin.getY();

			for (Shape shape : prism.getShapes()) {
				List<Vertex> vertexList = new ArrayList();

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
						if (selectedPrism != null) {
							selectedPrism.setColor(selectedPrism.getColor().brighter());
						}
						this.prismManager.setSelectedPrism(prism);
						this.userInterface.getHeadingSlider().setValue(prism.getHeadingValue());
						this.userInterface.getPitchSlider().setValue(prism.getPitchValue());
						this.userInterface.getHeadingSlider().setVisible(true);
						this.userInterface.getPitchSlider().setVisible(true);
						this.userInterface.getRemoveButton().setVisible(true);
						this.userInterface.getInterfaceActions().switchToPrismSelected();
						prism.setColor(prism.getColor().darker());
						UserInterface.repaint();
						
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
			selectedPrism.setColor(selectedPrism.getColor().brighter());
			UserInterface.repaint();
		}
		this.userInterface.getRemoveButton().setVisible(false);
		this.prismManager.setSelectedPrism(null);
		this.userInterface.getHeadingSlider().setVisible(false);
		this.userInterface.getPitchSlider().setVisible(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (movingPrism != null) {
			this.movingPrism = null;
		}
	}

}

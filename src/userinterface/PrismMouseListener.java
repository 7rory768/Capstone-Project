package userinterface;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.util.Timer;
import java.util.TimerTask;

import prisms.Prism;
import shapes.Shape;
import shapes.Square;
import util.Matrix3;
import util.PrismType;
import util.Vertex;

import managers.PrismManager;

public class PrismMouseListener implements MouseListener {

	private final UserInterface userInterface;
	private final PrismManager prismManager;
	
	private final Timer timer = new Timer();
	
	private Prism movingPrism = null;
	private TimerTask dragTask = null;
	private int oldX;
	private int oldY;
	private int oldScreenX;
	private int oldScreenY;
	
	public PrismMouseListener(UserInterface userInterface, PrismManager prismManager) {
		this.prismManager = prismManager;
		this.userInterface = userInterface;
		this.dragTask = new TimerTask() {
		    @Override
		    public void run() {
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
				movingPrism.setOrigin(origin);
				UserInterface.repaint();
		    }
		};
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Matrix3 transform = this.userInterface.getTransform();
		for (Prism prism : this.prismManager.getPrisms()) {
			Vertex origin = prism.getOrigin();
			double xOrigin = origin.getX();
			double yOrigin = origin.getY();
			if (prism.getType() == PrismType.CUBE) {
				for (Shape shape : prism.getShapes()) {
					Square square = (Square) shape;
					
					Vertex v1 = transform.transform(square.getVertex1());
					Vertex v2 = transform.transform(square.getVertex2());
					Vertex v3 = transform.transform(square.getVertex3());
					Vertex v4 = transform.transform(square.getVertex4());

					v1.setX(v1.getX() + xOrigin);
					v1.setY(v1.getY() + yOrigin);
					v2.setX(v2.getX() + xOrigin);
					v2.setY(v2.getY() + yOrigin);
					v3.setX(v3.getX() + xOrigin);
					v3.setY(v3.getY() + yOrigin);
					v4.setX(v4.getX() + xOrigin);
					v4.setY(v4.getY() + yOrigin);
					
					Path2D path = new Path2D.Double();
					path.moveTo(v1.getX(), v1.getY());
					path.lineTo(v2.getX(), v2.getY());
					path.lineTo(v3.getX(), v3.getY());
					path.lineTo(v4.getX(), v4.getY());
					path.lineTo(v1.getX(), v1.getY());
					path.closePath();
					
					if (path.contains(e.getX(), e.getY())) {
						System.out.println("Clicked: Cube");
						this.prismManager.setSelectedPrism(prism);
						if (movingPrism == null) {
							this.movingPrism = prism;
							this.oldX = e.getX();
							this.oldY = e.getY();
							this.oldScreenX = e.getXOnScreen();
							this.oldScreenY = e.getYOnScreen();
							timer.schedule(dragTask, 1000, 1000);
						} else {
							System.out.print("press");
						}
						return;
					}
				}
			}
		}
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (movingPrism != null) {
			
			this.movingPrism = null;
		}
	}

}

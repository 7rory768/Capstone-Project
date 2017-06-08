package userinterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

	private boolean holdingCtrl = false;

	@Override
	public void keyPressed(KeyEvent e) {
		this.holdingCtrl = e.isControlDown();
		System.out.println("key press");
		System.out.println("ctrl? " + e.isControlDown());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.holdingCtrl = e.isControlDown();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean holdingCtrl() {
		return this.holdingCtrl;
	}

}
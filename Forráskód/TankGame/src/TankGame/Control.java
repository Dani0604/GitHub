/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TankGame;

import java.util.ArrayList;

/**
 *
 * @author Predi
 */

class Control {

	private GUI gui;
	public ArrayList<Element> elements;
	public ArrayList<Player> players;
	private static final int T = 50;
	public boolean turnLeft;
	public boolean turnRight;

	public class PeriodicControl extends Thread {
		@Override
		public void run() {
			while (true) {
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p.turnLeft)
						p.tank.rotate(-Math.PI / 1000 * T);
					if (p.turnRight)
						p.tank.rotate(Math.PI / 1000 * T);
					if (p.shoot) {
						p.tank.nextBullet.shoot(elements);
					}

				}
				for (int i = 0; i < elements.size(); i++) {
					Element e = elements.get(i);
					e.move(T);
					if(e.deleteElement)
						elements.remove(i);
				}

				try {
					Thread.sleep(T);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Control() {
		elements = new ArrayList<Element>();
		players = new ArrayList<Player>();
		newElement(300, 300, 0);
		Player p = new Player((Tank) elements.get(0));
		players.add(p);
		Thread t = new PeriodicControl();
		t.start();
	}

	private Element newElement(int x, int y, double o) {
		Element e = new Tank(x, y, o);
		elements.add(e);
		return e;
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

}

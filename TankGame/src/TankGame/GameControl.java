package TankGame;


import java.util.ArrayList;

/**
 *
 * @author Predi
 */

class GameControl {

	private GUI gui;
	public ArrayList<Element> elements;
	public ArrayList<Player> players;
	private static final int T = 20;
	

	public class PeriodicControl extends Thread {
		@Override
		public void run() {
			while (true) {
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p.controls.turnLeft)
						p.tank.rotate(-Math.PI / 1000 * T);
					if (p.controls.turnRight)
						p.tank.rotate(Math.PI / 1000 * T);
					if (p.controls.shoot) {
						p.tank.nextBullet.shoot(elements);
					}

				}
				for (int i = 0; i < elements.size(); i++) {
					Element e1 = elements.get(i);
					e1.move(T);
					for (int j = i+1; j < elements.size(); j++) {
						Element e2 = elements.get(j);
						e1.collisionDetection(e2);
						e2.collisionDetection(e1);	
						if(e2.deleteElement){
							e2.delete();
							elements.remove(j);
						}
					}
					
					if(e1.deleteElement){
						e1.delete();
						elements.remove(i);
					}
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

	GameControl() {
		elements = new ArrayList<Element>();
		players = new ArrayList<Player>();
		newElement(300, 300, 0);
		newElement(300, 600, 0);
		Player p = new Player((Tank) elements.get(0));
		players.add(p);
		p = new Player((Tank) elements.get(1));
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

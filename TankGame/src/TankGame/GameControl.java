package TankGame;


import java.util.ArrayList;

/**
 *
 * @author Predi
 */

class GameControl {

	
	public MainControl mctrl;
	public ArrayList<Player> players;
	private static final double T = 5; 
	

	public class PeriodicControl extends Thread {
		@Override
		public void run() {
			while (true) {
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p.controls.turnLeft)
						p.tank.rotate(-Math.PI*1.2/ 1000 * T);
					if (p.controls.turnRight)
						p.tank.rotate(Math.PI*1.2 / 1000 * T);
					if (p.controls.shoot && !p.controls.shoot_old) {
						p.tank.nextBullet.shoot(mctrl.elements);
					}
					p.controls.shoot_old = p.controls.shoot;
				}
				for (int i = 0; i < mctrl.elements.size(); i++) {
					Element e1 = mctrl.elements.get(i);
					e1.move(T);
					e1.wallCollision(mctrl.map);
					for (int j = i+1; j < mctrl.elements.size(); j++) {
						Element e2 = mctrl.elements.get(j);
						e1.collisionDetection(e2);
						e2.collisionDetection(e1);	
						if(e2.deleteElement){
							e2.delete();
							mctrl.elements.remove(j);
						}
					}
					
					if(e1.deleteElement){
						e1.delete();
						mctrl.elements.remove(i);
					}
				}

				try {
					Thread.sleep((int)T);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	GameControl(MainControl mc) {
		mctrl = mc;
		players = new ArrayList<Player>();
		Player p = new Player((Tank) mctrl.elements.get(0));
		players.add(p);
		p = new Player((Tank) mctrl.elements.get(1));
		players.add(p);
		Thread t = new PeriodicControl();
		t.start();
	}

	

}

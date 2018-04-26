package TankGame;


import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import TankGame.Element.Type;
import TankGame.Player.Controls;

/**
 *
 * @author Predi
 */

class GameControl {


	public MainControl mctrl;
	public CopyOnWriteArrayList<Player> players;
	private static final double T = 5; 
	private static final double GAME_END_WAIT_TIME = 3000;
	public CopyOnWriteArrayList<Element> elements;
	public Map map;
	public SerialServer server;
	public GameState gameState;
	
	
	
	private void newMatch(){
		map = new Map();
		elements = new CopyOnWriteArrayList<Element>();
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			p.tank = null;
			players.set(i,p);
		}
	}
	
	public void startServer(){
		if(server != null){
			server.disconnect();
		}
		server = new SerialServer(this);
 		server.connect("localhost");
	}
	
	private class PeriodicControl extends Thread {
		@Override
		public void run() {
			double prevTime = System.nanoTime();
			double currentTime;
			double waitTime = GAME_END_WAIT_TIME;
			while (true) {
				currentTime = System.nanoTime();
				double deltaT = (currentTime - prevTime)/1000000;
				
				//System.out.println(deltaT);
				prevTime = currentTime;
				
				//System.out.println(players.size());
				//System.out.println(elements.size());
				//Játékosoktól érkező vezérlések
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p.tank == null){
						p.tank = new Tank(map);
						p.tank.player = p;
						elements.add(p.tank);
					}
					if (p.controls.turnLeft)
						p.tank.rotate(-Math.PI*1.2/ 1000 * deltaT);
					if (p.controls.turnRight)
						p.tank.rotate(Math.PI*1.2 / 1000 * deltaT);
					if (p.controls.shoot && !p.controls.shoot_old) {
						p.tank.nextBullet.shoot(elements);
					}
					p.controls.shoot_old = p.controls.shoot;
				}

				//Mozgatások, ütközések
				for (int i = 0; i < elements.size(); i++) {
					Element e1 = elements.get(i);
					e1.move(T);
					e1.wallCollision(map);
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
				
				int tankNum = 0;
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).getType() == Type.TANK) tankNum++;
				}

				if (tankNum <= 1){
					waitTime -= deltaT;
				}
				else{
					waitTime = GAME_END_WAIT_TIME;
				} 
				
				if (waitTime <= 0){
					newMatch();
				}
				gameState = new GameState();
				gameState.elements = new CopyOnWriteArrayList<Element>(elements);
				gameState.map = new ArrayList<Rectangle>(map.lines);
				gameState.players = new CopyOnWriteArrayList<Player>(players);
				
				try {
					Thread.sleep((int)T);
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*Element newElement(int x, int y, double o) {
		Element e = new Tank(x, y, o);
		elements.add(e);
		return e;
	}*/
	
	
	GameControl(MainControl mc) {
		mctrl = mc;
		startServer();
		map = new Map();
		elements = new CopyOnWriteArrayList<Element>();
		//newElement(300, 300, 0);
		players = new CopyOnWriteArrayList<Player>();
		//Player p = new Player((Tank) elements.get(0));
		//players.add(p);
		Thread t = new PeriodicControl();
		gameState = new GameState();
		t.start();
	}
	
	void playerReceived(Player _player) {
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p.name.equals(_player.name)){
				//System.out.println(_player.controls.moveForward);
				//System.out.println(i);
				p.controls.moveForward = _player.controls.moveForward;
				p.controls.shoot = _player.controls.shoot;
				p.controls.turnLeft = _player.controls.turnLeft;
				p.controls.turnRight = _player.controls.turnRight;
				players.set(i, p);
				return;
			}
		}
		//System.out.println('a');
		players.add(_player);
	}
}

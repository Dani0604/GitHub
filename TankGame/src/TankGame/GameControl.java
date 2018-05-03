package TankGame;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Network.SerialServer;
import TankGame.Element.Type;

/**
 *
 * @author Predi
 */

public class GameControl {


	public StateMachine SM;
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
	
	public void closeServer(){
		server.disconnect();
	}
	public boolean currentStateIsGame(){
		return SM.currentState == State.Game;
	}
	
	private class PeriodicControl extends Thread {
		@Override
		public void run() {
			double prevTime = System.nanoTime();
			double currentTime;
			double waitTime = GAME_END_WAIT_TIME;
			while (true) {
				if (currentStateIsGame()){
				currentTime = System.nanoTime();
				double deltaT = (currentTime - prevTime)/1000000;
				
				//System.out.println(deltaT);
				prevTime = currentTime;
				
				//System.out.println(players.size());
				//System.out.println(elements.size());
				//Játékosoktól érkezõ vezérlések
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
				}
				gameState = new GameState();
				gameState.state = SM.currentState;
				gameState.elements = elements == null ? null : new CopyOnWriteArrayList<Element>(elements);
				gameState.map = map == null || map.lines == null ? null : new ArrayList<Rectangle>(map.lines);
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
	
	
	GameControl(StateMachine sm) {
		SM = sm;
		players = new CopyOnWriteArrayList<Player>();
		gameState = new GameState();
		gameState.state = SM.currentState;
		startServer();
		Thread t = new PeriodicControl();
		t.start();
	}
	
	void startGame(){
		map = new Map();
		elements = new CopyOnWriteArrayList<Element>();
	}
	
	
	public void playerReceived(Player _player) {
		int i;
		for (i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p.settings.ID == _player.settings.ID){
				//System.out.println(_player.controls.moveForward);
				//System.out.println(i);
				p.controls.moveForward = _player.controls.moveForward;
				p.controls.moveBackward = _player.controls.moveBackward;
				p.controls.shoot = _player.controls.shoot;
				p.controls.turnLeft = _player.controls.turnLeft;
				p.controls.turnRight = _player.controls.turnRight;
				players.set(i, p);
				return;
			}
		}
		//System.out.println('a');
		if (_player.settings.name.equals("Default"))
			_player.settings.name = "Player" + i;
		players.add(_player);
	}
}

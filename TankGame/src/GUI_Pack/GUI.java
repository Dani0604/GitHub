/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Pack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Network.SerialClient;
import TankGame.Element;
import TankGame.GameState;
import TankGame.Map;
import TankGame.Player;
import TankGame.State;
import TankGame.StateEventListener;
import TankGame.StateMachine;


/**
 *
 * @author Predi
 */

public class GUI implements StateEventListener {
	private JFrame frmWorkOfTanks;
	
	private StateMachine SM;
	private DrawPanel drawPanel;
	private Player player;
	private CopyOnWriteArrayList<Element> elements;
	public 	CopyOnWriteArrayList<Player> players;
	private Map map;
	private SerialClient client;
	private String Serverip;
	
	
	public MainMenu mainmenu;
	public HostGame hostGame;
	public JoinGame joinGame;
	public Lobby lobby;
	

	public class PeriodicDrawer extends Thread {
		@Override
		public void run() {
			double old_time = 0;
			while (true) {
				drawPanel.repaint();
				try {
					Thread.sleep(10);
					double new_time;
					new_time = System.currentTimeMillis();
					double delta = new_time - old_time;
					//double fps = 1 / (delta / 1000);
					old_time = new_time;
					// System.out.println(fps);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	public class PeriodicPlayerUpdater extends Thread {
		@Override
		public void run() {
			client.connect(Serverip);
			while (true) {
				try {
					Thread.sleep(5);
					//System.out.println(player.controls.moveForward);
					send(player);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public class DrawPanel extends JPanel implements KeyListener {
		private static final long serialVersionUID = 1L;

		DrawPanel(){
			setDoubleBuffered(true);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (map != null)
				map.draw(g);
			if (elements != null){
				for (Element e : elements) {
					e.draw(g);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		//TODO általános billentyûkombinációk
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				player.controls.moveForward = false;
				break;
			case KeyEvent.VK_DOWN:
				// handle down
				player.controls.moveBackward = false;
				break;
			case KeyEvent.VK_LEFT:
				player.controls.turnLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				// handle right
				player.controls.turnRight = false;
				break;
			case KeyEvent.VK_SPACE:
				player.controls.shoot = false;
				break;
			}
		}

		@Override
		//TODO általános billentyûkombinációk
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				player.controls.moveForward = true;
				break;
			case KeyEvent.VK_DOWN:
				// handle down
				player.controls.moveBackward = true;
				break;
			case KeyEvent.VK_LEFT:
				player.controls.turnLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				// handle right
				player.controls.turnRight = true;
				break;
			case KeyEvent.VK_SPACE:
				player.controls.shoot = true;
				break;
			}
		}
	}


	public GUI(StateMachine sm) {
		SM = sm;
		map = new Map();
		player = new Player(null);
		mainmenu = new MainMenu(this);
		hostGame = new HostGame(this);
		joinGame = new JoinGame(this);
		lobby = new Lobby(this);
		client = new SerialClient(this);

		mainmenu.frm.setVisible(true);
		hostGame.frm.setVisible(false);
		joinGame.frm.setVisible(false);
		}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/*public void playerReceived(Player _player) {
		setPlayer(_player);
	}*/

	public void gameStateReceived(GameState gamestate){
		if (gamestate.state == State.Game){
			if (gamestate.elements != null)
				this.elements = gamestate.elements;
			else 
				this.elements = new CopyOnWriteArrayList<Element>();
			this.map.lines = new ArrayList<Rectangle>(gamestate.map);
		}
			players = gamestate.players;
			DefaultListModel<String> model = new DefaultListModel<String>();
		if(players != null && players.size() != 0){
			for (int i = 0; i < gamestate.players.size(); i++) {
				Player p = gamestate.players.get(i);
				model.addElement(p.settings.name);
				if (p.settings.ID == player.settings.ID){
					player.settings.name = p.settings.name;
					player.tank = p.tank;
				}
			}
			lobby.list.setModel(model);
		}
	}

	public void startClient(String ip){
		if(client != null){
			client.disconnect();
		}

		client = new SerialClient(this);
		Serverip = ip;

		Thread networkthread = new PeriodicPlayerUpdater();
		networkthread.start();
	}

	public void closeClient(){
		client.disconnect();
	}

	public void startGame(){
		client.startGame();
	}

	public void send(Player _player){
		if(_player != null && client != null){
			client.send(_player);
		}
	}
	public void setFrmWorkOfTanks(boolean b) {
		this.frmWorkOfTanks.setVisible(b);
	}
	
	public void setPlayerColor(Color color) {
		player.settings.color = color;
	}

	//Join game ablakból lehetséges szerverek felderítése
	public ArrayList<String> discoverGames() {
		ArrayList<String> ips = new ArrayList<String>();
		if(client!= null){
			ips = client.discoverServers();
		}
		else{
			System.out.println("Client:");
			System.out.println(client);
		}
		return ips;
	}
	
	@Override
	public State onEventHostGame() {
		this.SM.onEventHostGame();
		return this.SM.currentState;
	}

	@Override
	public State onEventJoinGame() {
		this.SM.onEventJoinGame();
		return this.SM.currentState;
	}


	@Override
	public State onEventStartGame() {
		this.SM.onEventStartGame();
		return this.SM.currentState;
	}

	@Override
	public State onEventCancel() {
		this.SM.onEventCancel();
		return this.SM.currentState;
	}

	@Override
	public State onEventExit() {
		this.SM.onEventExit();
		return this.SM.currentState;
	}
}

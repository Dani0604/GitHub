/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import TankGame.GUI.PeriodicPlayerUpdater;


/**
 *
 * @author Predi
 */

public class GUI extends JFrame {

	private MainControl mctrl;
	private DrawPanel drawPanel;
	private Player player;
	private CopyOnWriteArrayList<Element> elements;
	private Map map;
	private SerialClient client;
	private String Serverip;
	
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
					double fps = 1 / (delta / 1000);
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


	GUI(MainControl mc, boolean _is_server) {
		super("Tanks");
		mctrl = mc;
		mctrl.is_server = _is_server;
		map = new Map();
		player = new Player(null);

		setSize(1024, 1024);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		//MENÜ
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenuItem start = new JMenuItem("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				//start gomb funkciója...
			}
		});
		file.add(start);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(exit);

		JMenu network = new JMenu("Network");
		JMenuItem server = new JMenuItem("Server");
		server.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mctrl.stateMachine.onEventHostGame(mctrl);
			}
		});
		JMenuItem client = new JMenuItem("Client");
		client.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				//startClient("192.168.0.104");
				mctrl.stateMachine.onEventJoinGame(mctrl);
			}
		});
		network.add(client);
		network.add(server);

		menuBar.add(file);
		menuBar.add(network);
		setJMenuBar(menuBar);

		drawPanel = new DrawPanel();
		drawPanel.setBounds(0, 0, 800, 800);
		//drawPanel.setBorder(BorderFactory.createTitledBorder("Draw"));
		addKeyListener(drawPanel);

		add(drawPanel);

		JPanel p = new JPanel();
		JTextField Name = new JTextField(10);

		p.add(new JLabel("Name:"));
		p.add(Name);

		int input = JOptionPane.showConfirmDialog(null, p, "Name : ", JOptionPane.OK_CANCEL_OPTION);
		if(input == JOptionPane.OK_OPTION)
		{
			player.name = Name.getText();
		}
		Thread t = new PeriodicDrawer();
		t.start();

		setVisible(true);
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
		if (gamestate.elements != null)
			this.elements = gamestate.elements;
		else 
			this.elements = new CopyOnWriteArrayList<Element>();
		this.map.lines = new ArrayList<Rectangle>(gamestate.map);
		for (int i = 0; i < gamestate.players.size(); i++) {
			Player p = gamestate.players.get(i);
			if (p.name.equals(player.name)){
				player.tank = p.tank;
			}
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

	public void send(Player _player){
		if(_player != null && client != null){
			client.send(_player);
		}
	}
}

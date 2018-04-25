/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Predi
 */

public class GUI extends JFrame {

	private MainControl mctrl;
	private DrawPanel drawPanel;
	private Player player;
	
	public class PeriodicDrawer extends Thread {
		@Override
		public void run() {
			double old_time = 0;
			while (true) {
				drawPanel.repaint();
				try {
					Thread.sleep(5);
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
			while (true) {
				try {
					Thread.sleep(2500);
					mctrl.send(player);
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
			mctrl.map.draw(g);
			for (Element e : mctrl.elements) {
				e.draw(g);
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
				mctrl.startServer();
			}
		});
		JMenuItem client = new JMenuItem("Client");
		client.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				mctrl.startClient();
				Thread networkthread = new PeriodicPlayerUpdater();
				networkthread.start();
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

}

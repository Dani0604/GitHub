package Network;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

import GUI_Pack.GUI;
import TankGame.GameState;
import TankGame.Player;


public class SerialClient extends Network {


	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private GUI gui;


	private class ReceiverThread implements Runnable {

		public void run() {
			/*try {
				System.out.println("Terkep fogadasa.");
				ArrayList<Rectangle> received = (ArrayList<Rectangle>) in.readObject();
				gui.mapReceived(received);
				System.out.println("Map received.");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			}
			 */
			GameState received;
			try {
				System.out.println("Tankok fogadasa.");
				while (true) {
					received = (GameState) in.readUnshared();
					gui.gameStateReceived(received);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			} finally {
				disconnect();
			}

			try {
				System.out.println("Terkep fogadasa.");

				System.out.println("Map received.");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			}
		}
	}

	@Override
	public void connect(String ip) {
		disconnect();
		try {
			socket = new Socket(ip, 10007);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection. ");
			JOptionPane.showMessageDialog(null, "Cannot connect to server!");
		}
	}

	public SerialClient(GUI gui){
		this.gui = gui;
	}
	
	public void startGame(){
		
	}

	public void send(Player _player) {
		if (out == null)
			return;
		try {
			out.reset();
			out.writeUnshared(_player);
			out.flush();
		//	System.out.println("Sending player to server.");
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			
		}
	}

	@Override
	public void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Error while closing conn.");
		}
	}
}

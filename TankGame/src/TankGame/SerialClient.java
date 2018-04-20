package TankGame;

import java.awt.Point;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class SerialClient extends Network {

	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	private class ReceiverThread implements Runnable {

		public void run() {
			System.out.println("Waiting for players...");
			try {
				Thread.sleep(50);
				while (true) {
					Player received = (Player) in.readObject();
					System.out.println(received.tank.position);
					mctrl.playerReceived(received);
				}
			} catch (Exception ex) { //itt l�p ki hib�val
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
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

	public SerialClient(MainControl c){
		super(c);
	}
	
	@Override
	void send(Player _player) {
		if (out == null)
			return;
		//System.out.println("Sending player:" + _player.tank.position + "to Server");
		try {
			out.writeObject(_player);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}

	@Override
	void disconnect() {
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

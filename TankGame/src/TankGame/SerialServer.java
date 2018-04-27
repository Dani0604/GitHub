package TankGame;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialServer extends Network {


	private ServerSocket serverSocket = null;
	private ArrayList<Socket> clientSocket = null;
	private ArrayList<ObjectOutputStream> out = null;
	private ArrayList<ObjectInputStream> in = null;
	private ArrayList<Thread> rec = null;
	private PeriodicControl pc;
	private WaitForClientThread wc;
	GameControl gctrl;

	SerialServer(GameControl c) {
		gctrl = c;
		pc = new PeriodicControl();
		pc.start();
		clientSocket = new ArrayList<Socket>();
		out = new ArrayList<ObjectOutputStream>();
		in = new ArrayList<ObjectInputStream>();
		rec = new ArrayList<Thread>();
	}

	private class PeriodicControl extends Thread {
		@Override
		public void run() {
			while(true){
				send(gctrl.gameState);
				try {
					Thread.sleep(5);
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class WaitForClientThread implements Runnable{


		public void run() {
			try {
				//while(true){
				System.out.println("Waiting for Client");
				clientSocket.add(serverSocket.accept());
				System.out.println("Client connected.");
				System.out.println("Waiting for Client");
				clientSocket.add(serverSocket.accept());
				System.out.println("Client connected.");
				

				for (int i = 0; i < clientSocket.size(); i++) { 
					out.add(new ObjectOutputStream(clientSocket.get(i).getOutputStream()));
					in.add(new ObjectInputStream(clientSocket.get(i).getInputStream()));
					out.get(i).flush();
					rec.add(new Thread(new ReceiverThread(i)));
					rec.get(i).start();
				}
				//}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				disconnect();
				return;
			}
		}
	}
	private class ReceiverThread implements Runnable {

		int num;

		public ReceiverThread(int n){
			num = n;
		}
		public void run() {

			try {
				while (true) {
					//System.out.println("Server received an object.");
					Player received = (Player) in.get(num).readUnshared();
					//System.out.println(received.controls.moveForward);
					gctrl.playerReceived(received);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Client disconnected!");					
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			serverSocket = new ServerSocket(10007);
			Thread wc = new Thread(new WaitForClientThread());
			wc.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007.");
		}
	}

	@Override
	void disconnect() {
		try {
			for (int i = 0; i < out.size(); i++) { 
				if (out.get(i) != null)
					out.get(i).close();
			}
			for (int i = 0; i < in.size(); i++) { 
				if (in.get(i) != null)
					in.get(i).close();
			}
			for (int i = 0; i < clientSocket.size(); i++) { 
				if (clientSocket.get(i) != null)
					clientSocket.get(i).close();
			}
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 *A kliens csatlakozása után elküldi neki a térképet.
	 * @author Gyozo
	 */
	/*void sendMap(){
		if (out == null)
			return;
		//System.out.println("Sending map to Client");
		try {
			out.writeObject(gctrl.map.lines);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}*/

	public void send(GameState gameState){
		if (out == null)
			return;
		//System.out.println("Sending elements to Client");
		try {
			for (int i = 0; i < out.size(); i++) { 
				out.get(i).reset();
				out.get(i).writeUnshared(gameState);
				out.get(i).flush();
			}

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Send error.");
		}
	}

}

package TankGame;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public State state;
	public CopyOnWriteArrayList<Element> elements;
	public CopyOnWriteArrayList<Player> players;
	public ArrayList<Rectangle> map;

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(state);
		stream.writeObject(elements);
		stream.writeObject(players);
		stream.writeObject(map);
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		state = (State)stream.readObject();
		elements = (CopyOnWriteArrayList<Element>)stream.readObject();
		players = (CopyOnWriteArrayList<Player>) stream.readObject();
		map = (ArrayList<Rectangle>) stream.readObject();
	}	

}
package TankGame;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.awt.geom.Area;

abstract public class Element implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4617701502825832245L;
	protected Point2D position; // kirajzolás poziciója
	protected Point2D prevPos;
	protected double orientation; // orientáció megadása radiánban
	protected double velocity; // elem mozgásának sebessége
	protected Area area;
	
	
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(position);
		stream.writeObject(orientation);		
	}
	
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		position = (Point2D) stream.readObject();
		orientation = (double)stream.readObject();		
	}	
	
	
	//protected Semaphore s;
	protected enum Type {
		WALL,
		TANK,
		BULLET,
		POWERUP
	}
	public boolean deleteElement;
	
	protected Map map;

	abstract public void draw(Graphics g);

	abstract public void move(double T);

	abstract protected Type getType();
	
	abstract public void delete();
	
	abstract public void wallCollision(Map map);
	
	public void rotate(double angle) {
		this.orientation = this.orientation + angle;
	}

	abstract protected void collisionDetection(Element e);

}

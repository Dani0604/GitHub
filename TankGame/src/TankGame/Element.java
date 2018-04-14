package TankGame;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.concurrent.Semaphore;
import java.awt.geom.Area;

abstract public class Element implements Serializable {
	protected Point2D position; // kirajzolás poziciója
	protected double orientation; // orientáció megadása radiánban
	protected double velocity; // elem mozgásának sebessége
	protected Area area;
	protected Semaphore s;
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

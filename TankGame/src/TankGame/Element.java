package TankGame;

import java.awt.Graphics;
import java.awt.Point;

abstract public class Element {
	protected Point position; // kirajzolás poziciója
	protected double orientation; // orientáció megadása radiánban
	protected double velocity; // elem mozgásának sebessége

	public boolean deleteElement;
	
	protected Map map;

	abstract public void draw(Graphics g);

	abstract public void move(int T);

	public void rotate(double angle) {
		this.orientation = this.orientation + angle;
	}

	abstract public void collisionDetection();

}

package TankGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Bullet extends Element {

	private static final double VELOCITY = 310;
	private static final double MAX_MOVE = 400; 
	private double actMove = MAX_MOVE;
	private static final int DIAMETER = 5;
	private Tank t;
	protected static final int MAX_BULLETNUM = 5;

	
	public Bullet(Tank t) {
		this.t = t;
		velocity = VELOCITY;
		  
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
		g2d.fill(circle);
	}

	@Override
	public void move(int T) {
		// TODO Auto-generated method stub
		int dx = (int) (velocity * T / 1000 * Math.cos(orientation));
		int dy = (int) (velocity * T / 1000 * Math.sin(orientation));
		position.translate(dx, dy);
		actMove = actMove - Math.sqrt(dx*dx+dy*dy);
		if (actMove < 0){
			deleteElement = true;
			t.bulletCounter--;
		}
	}

	@Override
	public void collisionDetection() {
		// TODO Auto-generated method stub

	}

	public void shoot(ArrayList<Element> elements) {
		if (t.bulletCounter < MAX_BULLETNUM) {
			t.bulletCounter++;
			position = new Point(t.position);
			position.translate((int) ((Tank.LENGTH / 2 + DIAMETER) * Math.cos(t.orientation)),
					(int) ((Tank.LENGTH / 2 + DIAMETER) * Math.sin(t.orientation)));
			orientation = t.orientation;
			elements.add(this);
			t.nextBullet = new Bullet(t);
		}
		t.player.shoot = false;
	}

	protected void finalize() {
		
	}

}

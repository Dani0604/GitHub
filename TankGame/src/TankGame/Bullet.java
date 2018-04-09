package TankGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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
		double dx = (velocity * T / 1000 * Math.cos(orientation));
		double dy = (velocity * T / 1000 * Math.sin(orientation));
		position.setLocation(position.getX() + dx,position.getY() + dy);
		actMove = actMove - Math.sqrt(dx*dx+dy*dy);
		if (actMove < 0){
			deleteElement = true;
		}
		Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
		area = new Area(circle);
	}

	@Override
	protected void collisionDetection(Element e) {
		// TODO Auto-generated method stub
		switch (e.getType()){
		case TANK:
			Area buff = new Area(area);
			buff.intersect(e.area);
			if (!buff.isEmpty()) deleteElement = true;
			break;
		default:
			break;
		}
		
	}

	public void shoot(ArrayList<Element> elements) {
		if (t.bulletCounter < MAX_BULLETNUM) {
			t.bulletCounter++;
			position = new Point2D.Double();
			position.setLocation(t.position.getX() + ((Tank.LENGTH / 2 + DIAMETER*3) * Math.cos(t.orientation)),
					t.position.getY() + ((Tank.LENGTH / 2 + DIAMETER*5) * Math.sin(t.orientation)));
			orientation = t.orientation;
			Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
			area = new Area(circle);
			elements.add(this);
			t.nextBullet = new Bullet(t);
		}
	}


	@Override
	protected Type getType() {
		return Type.BULLET;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		t.bulletCounter--;
	}

}

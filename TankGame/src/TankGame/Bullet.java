package TankGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class Bullet extends Element implements Serializable {

	private static final double VELOCITY = 210;
	private static final double MAX_MOVE = 2000; 
	private double actMove = MAX_MOVE;
	private static final int DIAMETER = 5;
	private Tank t;
	protected static final int MAX_BULLETNUM = 5;
	private int lastColl;

	public Bullet(Tank t) {  
		this.t = t;
		velocity = VELOCITY;
		lastColl = -1;
		s = new Semaphore(1);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		try {
			s.acquire();
			Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
			g2d.fill(circle);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			s.release();
		}			
		
	}

	@Override
	public void move(double T) {
		// TODO Auto-generated method stub
		double dx = (velocity * T / 1000 * Math.cos(orientation));
		double dy = (velocity * T / 1000 * Math.sin(orientation));
		position.setLocation(position.getX() + dx,position.getY() + dy);
		actMove = actMove - Math.sqrt(dx*dx+dy*dy);
		if (actMove < 0){
			deleteElement = true;
		}
		try {
			s.acquire();
			Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
			//area = new Area(circle);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			s.release();
		}			
		
		
	}

	@Override
	protected void collisionDetection(Element e) {
		// TODO Auto-generated method stub
		switch (e.getType()){
		case TANK:
			//Area a = new Area(area);
			//a.intersect(e.area);
			//if (!a.isEmpty()) deleteElement = true;
			break;
		default:
			break;
		}

	}

	public void shoot(CopyOnWriteArrayList<Element> elements) {
		if (t.bulletCounter < MAX_BULLETNUM) {
			t.bulletCounter++;
			position = new Point2D.Double();
			position.setLocation(t.position.getX() + ((Tank.LENGTH / 2 + DIAMETER*3) * Math.cos(t.orientation)),
					t.position.getY() + ((Tank.LENGTH / 2 + DIAMETER*5) * Math.sin(t.orientation)));
			orientation = t.orientation;
			Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
			//area = new Area(circle);
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

	@Override
	public void wallCollision(Map map){
		for (int i = 0; i < map.lines.size(); i++) { 
			if (lastColl != i){
				Area a = new Area(map.areas.get(i));
				//a.intersect(area);
				//if(!a.isEmpty()){
					//Függõleges fal
				//	Rectangle l = map.lines.get(i);
				//	if (l.getHeight() > l.getWidth()){
				//		orientation = Math.PI-orientation;
				//	}
					//Vizszintes fal 
				//	else{
				//		orientation = 2*Math.PI-orientation;
				//	}
				//	lastColl = i;
				//}
			}
		}
	}
}

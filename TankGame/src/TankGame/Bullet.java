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
	private static final int DIAMETER = 7;
	private Point2D prevPos;
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
		prevPos.setLocation(position);
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
			prevPos = new Point2D.Double();
			position.setLocation(t.position.getX() + ((Tank.LENGTH / 2 + DIAMETER*3) * Math.cos(t.orientation)),
					t.position.getY() + ((Tank.LENGTH / 2 + DIAMETER*5) * Math.sin(t.orientation)));
			prevPos.setLocation(position);
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
		Ellipse2D.Double circle = new Ellipse2D.Double(position.getX(), position.getY(), DIAMETER, DIAMETER);
		//Rectangle r = circle.getBounds();

		Ellipse2D.Double prevCircle = new Ellipse2D.Double(prevPos.getX(), prevPos.getY(), DIAMETER, DIAMETER);
		Rectangle prevr = prevCircle.getBounds();
		for (int i = 0; i < Map.lines.size(); i++) { 
			Area a = new Area(circle);
			Rectangle l = Map.lines.get(i);
			a.intersect(new Area(l));
			if(!a.isEmpty()){
				double dx = 0;
				double dy = 0;
				

				//balról ütközés
				if (prevCircle.getMaxX() < l.getMinX() && circle.getMaxX() >= l.getMinX()){
					orientation = Math.PI-orientation;
					dx = -Math.abs(Math.abs((position.getX()-l.getX()))-(double)DIAMETER);
				}
				//jobbról ütközés
				if (prevCircle.getMinX() > l.getMaxX() && circle.getMinX() <= l.getMaxX()){
					orientation = Math.PI-orientation;
					dx = Math.abs(Math.abs((position.getX()-(l.getMaxX()))));
				}
				//fentrõl ütközés
				if (prevCircle.getMaxY() < l.getMinY() && circle.getMaxY() >= l.getMinY()){
					orientation = 2*Math.PI-orientation;
					dy = -Math.abs(Math.abs((position.getY()-l.getY()))-(double)DIAMETER);
				}
				//lentrõl ütközés
				if (prevCircle.getMinY() > l.getMaxY() && circle.getMinY() <= l.getMaxY()){
					orientation = 2*Math.PI-orientation;
					dy = Math.abs(Math.abs((position.getY()-(l.getMaxY())))) ;
				}
				/*double w = 0.5 * ((double)r.getWidth() + (double)l.getWidth());
				double h = 0.5 *((double)r.getHeight() + (double)l.getHeight());
				double ddx = r.getCenterX() - l.getCenterX();
				double ddy = r.getCenterY() - l.getCenterY();

				double wy = w * ddy;
				double hx = h * ddx;

  				if (wy > hx){
					if (wy > -hx){
						collision at the bottom 
						orientation = 2*Math.PI-orientation;
						dy = Math.abs(Math.abs((position.getY()-(l.getY()+l.getHeight())))-(double)r.getHeight()/2.0) + 1;
					}

					else{
						on the left 
						orientation = Math.PI-orientation;
						dx = -Math.abs(Math.abs((position.getX()-l.getX()))-(double)r.getWidth()/2.0) - 1;
					}
				}

				else{
					if (wy > -hx){
						 on the right 
						orientation = Math.PI-orientation;
						dx = Math.abs(Math.abs((position.getX()-(l.getX()+l.getWidth())))-(double)r.getWidth()/2.0) +1;
					}

					else{
						 at the top 
						orientation = 2*Math.PI-orientation;
						dy = -Math.abs(Math.abs((position.getY()-l.getY()))-(double)r.getHeight()/2.0) -1;

					}
				}
				 */
				position.setLocation(position.getX()+dx,position.getY()+dy);
				break;
			}
		}
	}
}

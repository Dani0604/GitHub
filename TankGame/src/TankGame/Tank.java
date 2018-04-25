package TankGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.concurrent.Semaphore;

import TankGame.Element.Type;

public class Tank extends Element implements Serializable {

	static final int LENGTH = 30;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 200;
	private int health;
	private Color color;
	private Polygon poly;
	protected Player player;
	Bullet nextBullet;
	public int bulletCounter = 0;




	public Tank(int x, int y, double o) {
		// TODO
		position = new Point2D.Double(x, y);
		orientation = o;
		velocity = VELOCITY;
		nextBullet = new Bullet(this);
		s = new Semaphore(1);


		int xPoly[] = { 1, 1, 1, 1 };
		int yPoly[] = { 1, 1, 1, 1 };
		int signs[][] = { { 1, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 } };
		for (int i = 0; i < 4; i++) {
			xPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.cos(orientation)
					- signs[i][1] * WIDTH / 2 * Math.sin(orientation)) + position.getX());
			yPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.sin(orientation)
					+ signs[i][1] * WIDTH / 2 * Math.cos(orientation)) + position.getY());
		}
		poly = new Polygon(xPoly, yPoly, xPoly.length);
		//area = new Area(poly);
	}

	@Override
	public void move(double T) {
		// TODO Auto-generated method stub
		if (getPlayer().controls.moveForward) {
			double dx = (velocity * T / 1000.0 * Math.cos(orientation));
			double dy = (velocity * T / 1000.0 * Math.sin(orientation));
			position.setLocation(position.getX() + dx,position.getY() + dy);
		}
		int xPoly[] = { 1, 1, 1, 1 };
		int yPoly[] = { 1, 1, 1, 1 };
		int signs[][] = { { 1, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 } };
		for (int i = 0; i < 4; i++) {
			xPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.cos(orientation)
					- signs[i][1] * WIDTH / 2 * Math.sin(orientation)) + position.getX());
			yPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.sin(orientation)
					+ signs[i][1] * WIDTH / 2 * Math.cos(orientation)) + position.getY());
		}
		try {
			s.acquire();
			poly = new Polygon(xPoly, yPoly, xPoly.length);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			s.release();
		}			
		//area = new Area(poly);
	}

	@Override
	protected void collisionDetection(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		if (g != null) {
			try {
				s.acquire();
				g.drawPolygon(poly);
				//g.drawRect((int)poly.getBounds().getX(), (int)poly.getBounds().getY(), (int)poly.getBounds().getWidth(), (int)poly.getBounds().getHeight());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				s.release();
			}			
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	protected Type getType() {
		return Type.TANK;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void wallCollision(Map map){
		Rectangle r = poly.getBounds();
		for (int i = 0; i < Map.lines.size(); i++) { 
			Area a = new Area(poly);
			a.intersect(new Area(Map.lines.get(i)));
			if(!a.isEmpty()){
				double dx = 0;
				double dy = 0;
				Rectangle l = Map.lines.get(i);
				double w = 0.5 * (r.getWidth() + l.getWidth());
				double h = 0.5 * (r.getHeight() + l.getHeight());
				double ddx = r.getCenterX() - l.getCenterX();
				double ddy = r.getCenterY() - l.getCenterY();


				/* collision! */
				double wy = w * ddy;
				double hx = h * ddx;

				if (wy > hx){
					if (wy > -hx){
						/* collision at the top */
						dy = Math.abs(Math.abs((position.getY()-(l.getY()+l.getHeight())))-(double)r.getHeight()/2.0);
					}

					else{
						/* on the left */
						dx = -Math.abs(Math.abs((position.getX()-l.getX()))-(double)r.getWidth()/2.0);
					}
				}

				else{
					if (wy > -hx){
						/* on the right */
						dx = Math.abs(Math.abs((position.getX()-(l.getX()+l.getWidth())))-(double)r.getWidth()/2.0);
					}

					else{
						/* at the bottom */
						dy = -Math.abs(Math.abs((position.getY()-l.getY()))-(double)r.getHeight()/2.0);
						
					}
				}
				position.setLocation(position.getX() + dx,position.getY() + dy);
			}


		}
	}
}

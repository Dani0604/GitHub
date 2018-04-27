package TankGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;

public class Tank extends Element implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int LENGTH = 22;
	private static final int WIDTH = 15;
	private static final int VELOCITY = 200;
	private static final double BACKWARD_SPEED_MULTIPLIER = 0.75;
	private int health;
	private Color color;
	private Polygon poly;
	protected Player player;
	Bullet nextBullet;
	public int bulletCounter = 0;

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(color);
		stream.writeObject(poly);	
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		color = (Color) stream.readObject();
		poly = (Polygon)stream.readObject();		
	}	


	public Tank(Map map) {

		do {
			position = new Point2D.Double(Math.random()*map.MapWidth, Math.random()*map.MapHeight);
			orientation = Math.random()*Math.PI;
			poly = getPoly();
			area = new Area(poly);
		}
		while(is_wallCollision(map));
		velocity = VELOCITY;
		nextBullet = new Bullet(this);
		health = 1;
	}
	


	private Polygon getPoly(){
		int xPoly[] = { 1, 1, 1, 1 };
		int yPoly[] = { 1, 1, 1, 1 };
		int signs[][] = { { 1, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 } };
		for (int i = 0; i < 4; i++) {
			xPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.cos(orientation)
					- signs[i][1] * WIDTH / 2 * Math.sin(orientation)) + position.getX());
			yPoly[i] = (int) ((signs[i][0] * LENGTH / 2 * Math.sin(orientation)
					+ signs[i][1] * WIDTH / 2 * Math.cos(orientation)) + position.getY());
		}

		return new Polygon(xPoly, yPoly, xPoly.length);
	}
	public Tank(Tank tank) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(double T) {
		// TODO Auto-generated method stub
		if (getPlayer().controls.moveForward) {
			double dx = (velocity * T / 1000.0 * Math.cos(orientation));
			double dy = (velocity * T / 1000.0 * Math.sin(orientation));
			position.setLocation(position.getX() + dx,position.getY() + dy);
		}
		if (getPlayer().controls.moveBackward) {
			double dx = -(velocity*BACKWARD_SPEED_MULTIPLIER * T / 1000.0 * Math.cos(orientation));
			double dy = -(velocity*BACKWARD_SPEED_MULTIPLIER * T / 1000.0 * Math.sin(orientation));
			position.setLocation(position.getX() + dx,position.getY() + dy);
		}
		poly = getPoly();
		area = new Area(poly);
	}

	@Override
	protected void collisionDetection(Element e) {
		// TODO Auto-generated method stub

		switch (e.getType()){
		case BULLET:
			Area a = new Area(area);
			a.intersect(e.area);
			if (!a.isEmpty()) health--; 
			break;
		default:
			break;
		}
		if (health <= 0)
			deleteElement = true;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		if (g != null) {
			Graphics2D g2d = (Graphics2D)g;
			Stroke s = g2d.getStroke();
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.black);
			g2d.drawPolygon(poly);
			g2d.setStroke(s);
			g2d.setColor(Color.red);
			g2d.fillPolygon(poly);
			g2d.setColor(Color.black);
			
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

	private boolean is_wallCollision(Map map){

		for (int i = 0; i < map.lines.size(); i++) { 
			Area a = new Area(poly);
			a.intersect(new Area(map.lines.get(i)));
			if(!a.isEmpty()) return true;
		}
		return false;
	}

	@Override
	public void wallCollision(Map map){
		Rectangle r = poly.getBounds();

		for (int i = 0; i < map.lines.size(); i++) { 
			Area a = new Area(poly);
			a.intersect(new Area(map.lines.get(i)));

			if(!a.isEmpty()){
				double dx = 0;
				double dy = 0;
				Rectangle l = map.lines.get(i);
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


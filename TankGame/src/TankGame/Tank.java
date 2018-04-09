package TankGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;

import TankGame.Element.Type;

public class Tank extends Element {

	static final int LENGTH = 30;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 300;
	private int health;
	private Color color;
	private Polygon poly;
	protected Player player;
	Bullet nextBullet;
	public int bulletCounter = 0;

	public Tank(int x, int y, double o) {
		// TODO
		position = new Point(x, y);
		orientation = o;
		velocity = VELOCITY;
		nextBullet = new Bullet(this);
		
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
		area = new Area(poly);
	}

	@Override
	public void move(int T) {
		// TODO Auto-generated method stub
		if (getPlayer().controls.moveForward) {
			double dx = (velocity * T / 1000 * Math.cos(orientation));
			double dy = (velocity * T / 1000 * Math.sin(orientation));
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
		poly = new Polygon(xPoly, yPoly, xPoly.length);
		area = new Area(poly);
	}

	@Override
	protected void collisionDetection(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	
		if (g != null) {
			g.drawPolygon(poly);
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

}

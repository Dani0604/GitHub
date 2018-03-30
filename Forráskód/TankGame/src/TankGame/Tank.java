package TankGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Tank extends Element {

	static final int LENGTH = 30;
	private static final int WIDTH = 20;
	private static final int VELOCITY = 300;
	private int health;
	private Color color;
	protected Player player;
	Bullet nextBullet;
	public int bulletCounter = 0;

	public Tank(int x, int y, double o) {
		// TODO
		position = new Point(x, y);
		orientation = o;
		velocity = VELOCITY;
		nextBullet = new Bullet(this);
	}

	@Override
	public void move(int T) {
		// TODO Auto-generated method stub
		if (getPlayer().moveForward) {
			int dx = (int) (velocity * T / 1000 * Math.cos(orientation));
			int dy = (int) (velocity * T / 1000 * Math.sin(orientation));
			position.translate(dx, dy);
		}
	}

	@Override
	public void collisionDetection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int xPoly[] = { 1, 1, 1, 1 };
		int yPoly[] = { 1, 1, 1, 1 };
		int signs[][] = { { 1, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 } };
		for (int i = 0; i < 4; i++) {
			xPoly[i] = (int) (signs[i][0] * LENGTH / 2 * Math.cos(orientation)
					- signs[i][1] * WIDTH / 2 * Math.sin(orientation)) + position.x;
			yPoly[i] = (int) (signs[i][0] * LENGTH / 2 * Math.sin(orientation)
					+ signs[i][1] * WIDTH / 2 * Math.cos(orientation)) + position.y;
		}

		if (g != null) {
			g.drawPolygon(xPoly, yPoly, xPoly.length);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}

package TankGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Map implements Serializable{
	public static ArrayList<Rectangle> lines;
	public static ArrayList<Area> areas;
	public final static int MapHeight = 800;
	public final static int MapWidth = 800;
	private static int ColumnNum = 10;
	private static int RowNum = 10;

	MazeGenerator mazegenerator;
	
	
	Map(){
		lines = new ArrayList<Rectangle>();
		areas = new ArrayList<Area>();
		Rectangle l1 = new Rectangle(MapWidth-5,0,5,MapHeight);
		lines.add(l1);
		areas.add(new Area(l1) );
		l1 = new Rectangle(0,MapHeight-5,MapWidth,5);
		lines.add(l1);
		areas.add(new Area(l1) );
		mazegenerator = new MazeGenerator(ColumnNum,RowNum);
	}

	void draw(Graphics g){
		for (int i = 0; i < lines.size(); i++) { 
			Rectangle r = lines.get(i);
			g.fillRect((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
		}
	}

	public static class MazeGenerator {
		private final int x;
		private final int y;
		private final int[][] maze;

		public MazeGenerator(int x, int y) {
			this.x = x;
			this.y = y;
			maze = new int[this.x][this.y];
			generateMaze(0, 0);
			save();

		}

		public void save() {
			for (int i = 0; i < y; i++) {
				// save the north edge
				Rectangle l1 = null;
				for (int j = 0; j < x; j++) {
					if ((maze[j][i] & 1) == 0 && Math.random() < 0.95 || i == 0){
						int lx = (int) (l1 == null ? j*MapWidth/ColumnNum : l1.getX());
						int ly = (int) (l1 == null ? i*MapHeight/RowNum : l1.getY());
						int dx = (int) (l1 == null ? MapWidth/ColumnNum+5 : l1.getWidth() + MapWidth/ColumnNum);
						l1 = new Rectangle(lx,ly,dx,5);
					}
					else{
						if (l1 != null){
							lines.add(l1);
							areas.add(new Area(l1) );
							l1 = null;
						}
					}
					//System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
				}
				if (l1 != null){
					lines.add(l1);
					areas.add(new Area(l1) );
					l1 = null;
				}
			}
				//System.out.println("+");
				// save the west edge
			for (int j = 0; j < x; j++) {
				Rectangle l1 = null;
				for (int i = 0; i < y; i++) {
					if (((maze[j][i] & 8) == 0  &&  Math.random() < 0.95) || j == 0){
						int lx = (int) (l1 == null ? j*MapWidth/ColumnNum : l1.getX());
						int ly = (int) (l1 == null ? i*MapHeight/RowNum : l1.getY());
						int dy = (int) (l1 == null ? MapHeight/RowNum+5: l1.getHeight() + MapHeight/RowNum);
						l1 = new Rectangle(lx,ly,5,dy);
					}
					else{
						if (l1 != null){
							lines.add(l1);
							areas.add(new Area(l1) );
							l1 = null;
						}
					}
				}
				if (l1 != null){
					lines.add(l1);
					areas.add(new Area(l1) );
					l1 = null;
				}
				//System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
			}
			//System.out.println("|");

			// save the bottom line
			for (int j = 0; j < x; j++) {
				//System.out.print("+---");
			}
			//System.out.println("+");
		}

		private void generateMaze(int cx, int cy) {
			DIR[] dirs = DIR.values();
			Collections.shuffle(Arrays.asList(dirs));
			for (DIR dir : dirs) {
				int nx = cx + dir.dx;
				int ny = cy + dir.dy;
				if (between(nx, x) && between(ny, y)
						&& (maze[nx][ny] == 0)) {
					maze[cx][cy] |= dir.bit;
					maze[nx][ny] |= dir.opposite.bit;
					generateMaze(nx, ny);
				}
			}
		}

		private static boolean between(int v, int upper) {
			return (v >= 0) && (v < upper);
		}

		private enum DIR {
			N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
			private final int bit;
			private final int dx;
			private final int dy;
			private DIR opposite;

			// use the static initializer to resolve forward references
			static {
				N.opposite = S;
				S.opposite = N;
				E.opposite = W;
				W.opposite = E;
			}

			private DIR(int bit, int dx, int dy) {
				this.bit = bit;
				this.dx = dx;
				this.dy = dy;
			}
		};
	}

}

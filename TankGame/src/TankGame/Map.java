package TankGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Map {
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
				for (int j = 0; j < x; j++) {
					if ((maze[j][i] & 1) == 0){
					Rectangle l1 = new Rectangle(j*MapWidth/ColumnNum,i*MapHeight/RowNum,MapWidth/ColumnNum,5);
					lines.add(l1);
					areas.add(new Area(l1) );
					}
					System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
				}
				System.out.println("+");
				// save the west edge
				for (int j = 0; j < x; j++) {
					if ((maze[j][i] & 8) == 0){
						Rectangle l1 = new Rectangle(j*MapWidth/ColumnNum,i*MapHeight/RowNum,5,MapHeight/RowNum);
						lines.add(l1);
						areas.add(new Area(l1) );
					}
						System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
				}
				System.out.println("|");
			}
			// save the bottom line
			for (int j = 0; j < x; j++) {
				System.out.print("+---");
			}
			System.out.println("+");
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

package TankGame;

import java.util.ArrayList;

public class Player {
	
	public boolean turnLeft;
	public boolean turnRight;
	public boolean moveForward;
	public boolean shoot;
	
	public Tank tank;

	public Player(Tank t) {
		this.tank = t;
		t.setPlayer(this);
	}
	

	
}

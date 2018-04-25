package TankGame;

import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Serializable  {

	public class Controls implements Serializable
	{
		public boolean turnLeft;
		public boolean turnLeft_old;

		public boolean turnRight;
		public boolean turnRight_old;

		public boolean moveForward;
		public boolean moveForward_old;

		public boolean shoot;
		public boolean shoot_old;
	};

	public Controls controls;
	public Tank tank;

	public Player(Tank t) {
		this.tank = t;
		this.controls = new Controls();
		if (t != null)
			t.setPlayer(this);
	}



}

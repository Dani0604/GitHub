package TankGame;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Serializable  {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public class Controls implements Serializable 
	{
		/**
		 * 
		 */
		//private static final long serialVersionUID = 2L;
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
	public String name;

	public Player(Tank t) {
		this.tank = t;
		this.controls = new Controls();
		if (t != null)
			t.setPlayer(this);
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(controls);
		stream.writeObject(name);
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		controls = (Controls) stream.readObject();
		name = (String)stream.readObject();		
	}	

}

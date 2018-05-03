package TankGame;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;


public class Player implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public class Controls implements Serializable 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public boolean turnLeft;
		public boolean turnLeft_old;

		public boolean turnRight;
		public boolean turnRight_old;

		public boolean moveForward;
		public boolean moveForward_old;
		
		public boolean moveBackward;
		public boolean moveBackward_old;

		public boolean shoot;
		public boolean shoot_old;

	};
	
	public class Settings implements Serializable 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7425231142562328797L;
		public int ID;
		public String name;
		public Color color;
		
		Settings(){
			name = "Default";
			color = Color.WHITE;
			ID = (int)(Math.random()*100000);
		}

	};

	public Controls controls;
	public Tank tank;
	public Settings settings;
	
	public Player(Tank t) {
		this.tank = t;
		this.controls = new Controls();
		settings = new Settings();
		
		if (t != null)
			t.setPlayer(this);
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(settings);
		stream.writeObject(controls);
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		settings = (Settings) stream.readObject();
		controls = (Controls) stream.readObject();
	}	

}

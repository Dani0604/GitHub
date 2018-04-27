package TankGame;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import TankGame.GUI.PeriodicPlayerUpdater;

public class MainControl {

	public GUI gui;
	private Network net;
	public boolean is_server;
	public GameControl gctrl;
	public StateMachine stateMachine;

	MainControl(){	
		stateMachine = new StateMachine();
	}
	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}
}

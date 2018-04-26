package TankGame;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainControl {
	
	private GUI gui;
	private Network net;
	public boolean is_server;
	public GameControl gctrl;
	
	MainControl(){	
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}
	
}

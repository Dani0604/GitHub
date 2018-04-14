package TankGame;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainControl {
	public CopyOnWriteArrayList<Element> elements;
	private GUI gui;
	public Map map;
	
	MainControl(){
		elements = new CopyOnWriteArrayList<Element>();
		newElement(300, 300, 0);
		newElement(300, 600, 0);
	}
	
	Element newElement(int x, int y, double o) {
		Element e = new Tank(x, y, o);
		elements.add(e);
		return e;
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}
}

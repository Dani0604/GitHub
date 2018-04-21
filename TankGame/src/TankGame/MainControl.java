package TankGame;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainControl {
	public CopyOnWriteArrayList<Element> elements;
	private GUI gui;
	private Network net;
	public boolean is_server;
	public Map map;
	
	MainControl(){
		elements = new CopyOnWriteArrayList<Element>();
		newElement(300, 300, 0);
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
	
	public void startServer(){
		if(net != null){
			net.disconnect();
		}
		net = new SerialServer(this);
		net.connect("localhost");
	}
	
	public void startClient(){
		if(net != null){
			net.disconnect();
		}
		net = new SerialClient(this);
		net.connect("localhost");
	}
	
	public void send(Player _player){
		if(_player != null && net != null){
			net.send(_player);
		}
	}
	
	void playerReceived(Player _player) {
		if (gui == null)
			return;
		//gui.setPlayer(_player);
	}

	void mapReceived(ArrayList<Rectangle> _r) {
		this.map.lines = _r;
	}
}

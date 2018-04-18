/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame;

import java.awt.Point;

/**
 *
 * @author Predi
 */
abstract class Network {

	protected MainControl mctrl;
	
	public Network(MainControl c){
		mctrl = c;
	}
	
	abstract void connect(String ip);

	abstract void disconnect();

	abstract void send(Point p);
}

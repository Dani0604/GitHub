/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

/**
 *
 * @author Predi
 */
abstract class Network {

	//protected MainControl mctrl;
	
	public Network(){
	}
	
	abstract public void connect(String ip);

	abstract public void disconnect();

	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TankGame;

/**
 *
 * @author Predi
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		
		MainControl mc1 = new MainControl();
		mc1.map = new Map();
		GameControl c = new GameControl(mc1);
		GUI g1 = new GUI(mc1, true);
		g1.setPlayer(c.players.get(0));
		mc1.setGui(g1);
	}
}

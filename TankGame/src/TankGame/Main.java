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
		Control c = new Control();
		GUI g = new GUI(c);
		g.setPlayer(c.players.get(0));
		c.setGui(g);

	}
}

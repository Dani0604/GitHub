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
		GameControl c = new GameControl();
		GUI g1 = new GUI(c);
		GUI g2 = new GUI(c);
		g1.setPlayer(c.players.get(0));
		c.setGui(g1);
		
	}
}

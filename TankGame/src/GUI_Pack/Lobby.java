package GUI_Pack;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import GUI_Pack.GUI;

public class Lobby {

	public JFrame frm;
	private GUI gui;
	public JList<String> list;
	public JButton btnStart;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby window = new Lobby(null);
					window.frm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lobby(GUI gui){
		this.gui= gui;
		frm = new JFrame();
		frm.setTitle("Lobby");
		frm.setBounds(100, 100, 612, 460);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		frm.setVisible(false);

		JButton btnNewButton = new JButton("Main Menu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnNewButton.setBounds(25, 340, 165, 50);
		frm.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Ready");
		btnNewButton_1.setBounds(215, 340, 165, 50);
		frm.getContentPane().add(btnNewButton_1);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.onEventStartGame();
			}
		});
		btnStart.setBounds(405, 340, 165, 50);
		frm.getContentPane().add(btnStart);
		

		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(25, 25, 56, 16);
		frm.getContentPane().add(lblPlayers);

		list = new JList<String>();
		list.setFont(new Font("Tahoma", Font.PLAIN, 17));
		list.setModel(new AbstractListModel<String>() {

			@Override
			public String getElementAt(int arg0) {
				return gui.players.get(arg0).settings.name;
				
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				if (gui.players == null)
					return 0;
				else 
					return gui.players.size();
			}

		});
		list.setBounds(25, 50, 120, 277);
		frm.getContentPane().add(list);

		JPanel panel = new JPanel();
		panel.setBounds(145, 55, 16, 16);
		panel.setBackground(Color.WHITE);
		frm.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(145, 78, 16, 16);
		panel_1.setBackground(Color.BLACK);
		frm.getContentPane().add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(145, 101, 16, 16);
		panel_2.setBackground(Color.ORANGE);
		frm.getContentPane().add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(145, 124, 16, 16);
		panel_3.setBackground(Color.GREEN);
		frm.getContentPane().add(panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(145, 147, 16, 16);
		panel_4.setBackground(Color.BLUE);
		frm.getContentPane().add(panel_4);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(145, 170, 16, 16);
		frm.getContentPane().add(panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(145, 193, 16, 16);
		frm.getContentPane().add(panel_6);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(145, 216, 16, 16);
		frm.getContentPane().add(panel_7);

		JPanel panel_8 = new JPanel();
		panel_8.setBounds(396, 25, 25, 25);
		frm.getContentPane().add(panel_8);

		JLabel lblSelectColor = new JLabel("Select Color");
		lblSelectColor.setBounds(315, 25, 81, 16);
		frm.getContentPane().add(lblSelectColor);

		JRadioButton rdbtnRed = new JRadioButton("Red");
		rdbtnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.RED);
				gui.setPlayerColor(Color.RED);
			}
		});
		rdbtnRed.setBounds(321, 55, 127, 25);
		frm.getContentPane().add(rdbtnRed);

		JRadioButton rdbtnBlue = new JRadioButton("Blue");
		rdbtnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.BLUE);
				gui.setPlayerColor(Color.BLUE);
			}
		});
		rdbtnBlue.setBounds(321, 78, 127, 25);
		frm.getContentPane().add(rdbtnBlue);

		JRadioButton rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.GREEN);
				gui.setPlayerColor(Color.GREEN);
			}
		});
		rdbtnGreen.setBounds(321, 100, 127, 25);
		frm.getContentPane().add(rdbtnGreen);

		JRadioButton rdbtnYellow = new JRadioButton("Yellow");
		rdbtnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.YELLOW);
				gui.setPlayerColor(Color.YELLOW);
			}
		});
		rdbtnYellow.setBounds(321, 123, 127, 25);
		frm.getContentPane().add(rdbtnYellow);

		JRadioButton rdbtnOrange = new JRadioButton("Orange");
		rdbtnOrange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.ORANGE);
				gui.setPlayerColor(Color.ORANGE);
			}
		});
		rdbtnOrange.setBounds(321, 146, 127, 25);
		frm.getContentPane().add(rdbtnOrange);

		JRadioButton rdbtnPurple = new JRadioButton("Purple");
		rdbtnPurple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.MAGENTA);
				gui.setPlayerColor(Color.MAGENTA);
			}
		});
		rdbtnPurple.setBounds(321, 169, 127, 25);
		frm.getContentPane().add(rdbtnPurple);

		JRadioButton rdbtnBlack = new JRadioButton("Black");
		rdbtnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.BLACK);
				gui.setPlayerColor(Color.BLACK);
			}
		});
		rdbtnBlack.setBounds(321, 192, 127, 25);
		frm.getContentPane().add(rdbtnBlack);

		JRadioButton rdbtnWhite = new JRadioButton("White");
		rdbtnWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_8.setVisible(true);
				panel_8.setBackground(Color.WHITE);
				gui.setPlayerColor(Color.WHITE);
			}
		});
		rdbtnWhite.setBounds(321, 215, 127, 25);
		frm.getContentPane().add(rdbtnWhite);

		ButtonGroup group=new ButtonGroup();
		group.add(rdbtnRed);
		group.add(rdbtnBlue);
		group.add(rdbtnGreen);
		group.add(rdbtnYellow);
		group.add(rdbtnOrange);
		group.add(rdbtnPurple);
		group.add(rdbtnBlack);
		group.add(rdbtnWhite);

		JLabel lblReady = new JLabel("Ready");
		lblReady.setBounds(197, 25, 56, 16);
		frm.getContentPane().add(lblReady);
		/*rdbtnRed.addActionListener(this);
		blue.addActionListener(this);
		green.addActionListener(this);*/
	}


}

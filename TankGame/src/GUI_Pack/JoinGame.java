package GUI_Pack;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.AbstractListModel;
import javax.swing.JButton;

public class JoinGame {
	public JTextField textField_2;
	public JFrame frm;
	public JPanel panel;
	private GUI gui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinGame window = new JoinGame(null);
					window.frm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JoinGame(GUI gui) {
		this.gui = gui;
		frm = new JFrame();
		frm.setTitle("Join Game");
		frm.setBounds(100, 100, 612, 460);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		frm.setVisible(true);

		panel = new JPanel();
		frm.getContentPane().add(panel, "name_364531217708078");
		panel.setLayout(null);
		panel.setVisible(true);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(340, 320, 165, 50);
		frm.add(btnRefresh);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(26, 26, 56, 16);
		frm.add(lblName);

		JLabel lblIpAddress = new JLabel("IP address:");
		lblIpAddress.setBounds(26, 285, 66, 16);
		frm.add(lblIpAddress);

		/////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//GameNames={"WoW", "AoH", "OP"};
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"WoW", "AoH", "OP", "NomenEstOmen", "Gamers", "Popcorn", "VIK", "KiKKer's", "Last but not least"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setFont(new Font("Tahoma", Font.PLAIN, 17));
		list.setToolTipText("Name of Games:");
		list.setBounds(26, 55, 239, 217);
		frm.add(list);

		textField_2 = new JTextField();
		textField_2.setBounds(88, 282, 219, 22);
		// Listen for changes in the text
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				//gui.joinGame(textField_2.getText());
			}
		});
		frm.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(100, 320, 165, 50);
		frm.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Join Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.onEventStartGame();
			}
		});
		btnNewButton_1.setBounds(394, 55, 97, 25);
		frm.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Join Game");
		btnNewButton_2.setBounds(394, 79, 97, 25);
		frm.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Join Game");
		btnNewButton_3.setBounds(394, 103, 97, 25);
		frm.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Join Game");
		btnNewButton_4.setBounds(394, 127, 97, 25);
		frm.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Join Game");
		btnNewButton_5.setBounds(394, 151, 97, 25);
		frm.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("Join Game");
		btnNewButton_6.setBounds(394, 175, 97, 25);
		frm.add(btnNewButton_6);

		JButton btnNewButton_7 = new JButton("Join Game");
		btnNewButton_7.setBounds(394, 199, 97, 25);
		frm.add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("Join Game");
		btnNewButton_8.setBounds(394, 223, 97, 25);
		frm.add(btnNewButton_8);

		JButton btnNewButton_9 = new JButton("Join Game");
		btnNewButton_9.setBounds(394, 247, 97, 25);
		frm.add(btnNewButton_9);


	}
}

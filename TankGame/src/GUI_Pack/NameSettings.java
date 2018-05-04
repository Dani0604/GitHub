package GUI_Pack;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import javax.swing.*;

public class NameSettings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JFrame frame;
	private String name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NameSettings dialog = new NameSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Create the dialog.
	 */
	public NameSettings() {
		frame=new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Name Settings");
		frame.setBounds(100, 100, 360, 200);
		frame.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblYourName = new JLabel("Your Name:");
			lblYourName.setBounds(12, 35, 75, 25);
			contentPanel.add(lblYourName);
		}
		{
			textField = new JTextField();
			textField.setBounds(83, 35, 190, 24);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name=textField.getText();
				JOptionPane.showMessageDialog(null, "Hello "+name+"!");
				frame.dispose();
			}
		});
		btnNewButton.setBounds(113, 100, 100, 40);
		contentPanel.add(btnNewButton);
	}
	
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NameSettings window = new NameSettings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
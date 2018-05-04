package GUI_Pack;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class KeySettings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JFrame frame;
	private String Up="";
	private String Down="";
	private String Right="";
	private String Left="";
	private String Shoot="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KeySettings dialog = new KeySettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Create the dialog.
	 */
	public KeySettings() {
		frame=new JFrame();
		frame.setTitle("Key Settings");
		frame.setBounds(100, 100, 320, 320);
		frame.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPleaseEnterThe = new JLabel("Please enter the suitable boardkey");
			lblPleaseEnterThe.setBounds(12, 13, 214, 25);
			contentPanel.add(lblPleaseEnterThe);
		}
		{
			JLabel lblUp = new JLabel("Up:");
			lblUp.setBounds(12, 51, 25, 25);
			contentPanel.add(lblUp);
		}
		{
			JLabel lblDown = new JLabel("Down:");
			lblDown.setBounds(12, 90, 37, 25);
			contentPanel.add(lblDown);
		}
		{
			JLabel lblRight = new JLabel("Right:");
			lblRight.setBounds(12, 136, 37, 25);
			contentPanel.add(lblRight);
		}
		{
			JLabel lblLeft = new JLabel("Left:");
			lblLeft.setBounds(12, 174, 37, 25);
			contentPanel.add(lblLeft);
		}
		{
			JLabel lblShoot = new JLabel("Shoot:");
			lblShoot.setBounds(12, 212, 45, 26);
			contentPanel.add(lblShoot);
		}
		{
			textField = new JTextField();
			textField.setBounds(36, 51, 25, 22);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(52, 91, 25, 22);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			textField_2 = new JTextField();
			textField_2.setBounds(49, 137, 25, 22);
			contentPanel.add(textField_2);
			textField_2.setColumns(10);
		}
		{
			textField_3 = new JTextField();
			textField_3.setBounds(42, 175, 25, 22);
			contentPanel.add(textField_3);
			textField_3.setColumns(10);
		}
		{
			textField_4 = new JTextField();
			textField_4.setBounds(52, 214, 25, 22);
			contentPanel.add(textField_4);
			textField_4.setColumns(10);
		}
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Up=textField.getText();
				if(Up.length()!=1)
					JOptionPane.showMessageDialog(null, "Wrong input! Please enter only one key!");
				Down=textField_1.getText();
				if(Down.length()!=1)
					JOptionPane.showMessageDialog(null, "Wrong input! Please enter only one key!");
				Right=textField_2.getText();
				if(Right.length()!=1)
					JOptionPane.showMessageDialog(null, "Wrong input! Please enter only one key!");
				Left=textField_3.getText();
				if(Left.length()!=1)
					JOptionPane.showMessageDialog(null, "Wrong input! Please enter only one key!");
				Shoot=textField_4.getText();
				if(Shoot.length()!=1)
					JOptionPane.showMessageDialog(null, "Wrong input! Please enter only one key!");
				if(Up.length()+Down.length()+Right.length()+Left.length()+Shoot.length()==5)
					frame.dispose();
			}
		});
		btnNewButton.setBounds(127, 205, 100, 40);
		contentPanel.add(btnNewButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeySettings window = new KeySettings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

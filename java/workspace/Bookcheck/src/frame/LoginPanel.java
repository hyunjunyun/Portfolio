package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Dbset;

public class LoginPanel extends JFrame implements ActionListener {
	private JPanel background = new JPanel();
	private JButton lgnbtn;
	private JTextField idField;
	private JTextField pwField;
	private Dbset ds = new Dbset();

	public LoginPanel() {
		createUI();
		actionadd();
	}

	public void createUI() {

		lgnbtn = new JButton("Login");
		lgnbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));

		lgnbtn.setBounds(209, 210, 141, 51);
		getContentPane().add(background);
		ImagePanel loginPanel = new ImagePanel(
				new ImageIcon("C:\\hjun\\code\\java\\workspace\\Bookcheck\\Image\\lgnimg.png").getImage());
		background = loginPanel;
		setSize(new Dimension(500, 279));
		setPreferredSize(loginPanel.getDim());
		setContentPane(background);
		loginPanel.setLayout(null);
		background.add(lgnbtn);

		JButton btnResister = new JButton("Resister");
		btnResister.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnResister.setBounds(95, 210, 99, 51);
		loginPanel.add(btnResister);

		idField = new JTextField();
		idField.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		idField.setBounds(124, 81, 246, 42);
		loginPanel.add(idField);
		idField.setColumns(10);

		pwField = new JTextField();
		pwField.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		pwField.setColumns(10);
		pwField.setBounds(124, 144, 246, 42);
		loginPanel.add(pwField);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnExit.setBounds(362, 210, 67, 51);
		loginPanel.add(btnExit);

		JLabel lgnLabel = new JLabel("Book management program");
		lgnLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
		lgnLabel.setBounds(50, 10, 391, 51);
		loginPanel.add(lgnLabel);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblId.setBounds(89, 94, 57, 15);
		loginPanel.add(lblId);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(35, 157, 73, 15);
		loginPanel.add(lblPassword);

		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(700, 350);
		setSize(500, 330);
		setVisible(true);
	}

	public void actionadd() {
		lgnbtn.addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(lgnbtn)) {
			
		}
	}
}

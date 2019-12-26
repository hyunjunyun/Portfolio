import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;

public class App implements ActionListener {

	private final String ID = "1";
	private final String PASS = "1";
	private JFrame frame;
	private JTextField idField;
	private JTextField pwField;
	private JButton loginBtn;
	private JButton tranBtn;
	private JButton sumBtn;
	private JPanel currPanel;
	private JLabel lblType;
	private JLabel lblAmounnt;
	private JLabel lblNote;
	private JTextField nameInput;
	private JTextField amtInput;
	private JButton btnReset;
	private JTextField searchInput;
	private JTable valueTable;
	private ImagePanel sumPanel;
	private ImagePanel tranPanel;
	private ImagePanel loginPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame
		TableData td = new TableData();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		loginPanel = new ImagePanel(
				new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\theme.jpg").getImage());
		currPanel = loginPanel;
		frame.setSize(loginPanel.getDim());
		frame.setPreferredSize(loginPanel.getDim());
		frame.getContentPane().add(loginPanel);

		// Field

		idField = new JTextField();
		idField.setFont(new Font("±¼¸²", Font.PLAIN, 25));
		idField.setBounds(1223, 311, 296, 43);
		loginPanel.add(idField);
		idField.setColumns(10);
		idField.setBorder(null);

		pwField = new JPasswordField();
		pwField.setFont(new Font("±¼¸²", Font.PLAIN, 25));
		pwField.setColumns(10);
		pwField.setBounds(1223, 390, 296, 43);
		loginPanel.add(pwField);
		pwField.setBorder(null);

		JCheckBox checkRE = new JCheckBox("Remember me?");
		checkRE.setBounds(1189, 437, 120, 23);
		loginPanel.add(checkRE);

		// Summary Button
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		loginBtn.setBorder(null);
		loginBtn.setBounds(1183, 467, 350, 40);
		loginBtn.setIcon(new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\button.jpg"));
		loginPanel.add(loginBtn);
		loginBtn.setPressedIcon(
				new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\btnClicked.jpg"));
		sumPanel = new ImagePanel(
				new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\Activation.jpg").getImage());
		frame.getContentPane().add(sumPanel);
		tranPanel = new ImagePanel(
				new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\Activation.jpg").getImage());
		frame.getContentPane().add(tranPanel);
		sumPanel.setVisible(false);

		tranBtn = new JButton("");
		tranBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currPanel.setVisible(false);
				tranPanel.setVisible(true);
				currPanel = tranPanel;
			}

		});
		tranBtn.setBounds(25, 162, 270, 50);
		tranBtn.setIcon(new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\activationBtn.jpg"));
		sumPanel.add(tranBtn);
		tranBtn.setBorder(null);

		searchInput = new JTextField();
		searchInput.setFont(new Font("±¼¸²", Font.PLAIN, 22));
		searchInput.setBounds(390, 95, 1100, 40);
		sumPanel.add(searchInput);
		searchInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Search:");
		lblNewLabel.setFont(new Font("±¼¸²", Font.PLAIN, 22));
		lblNewLabel.setBounds(310, 100, 70, 30);
		sumPanel.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(390, 162, 1100, 450);
		sumPanel.add(panel);

		valueTable = new JTable(td);
		valueTable.setBounds(12, 10, 1075, 430);
		valueTable.setRowHeight(30);
		valueTable.setFont(new Font("Sansserif", Font.BOLD, 15));
		valueTable.setPreferredScrollableViewportSize(new Dimension(1075, 430));
		valueTable.setOpaque(false);

		JTableHeader header = valueTable.getTableHeader();
		header.setBackground(new Color(92, 100, 100));
		header.setForeground(new Color(255, 255, 255));
		header.setFont(new Font("Sansserif", Font.BOLD, 20));
		searchInput.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String search = searchInput.getText();
				TableRowSorter<AbstractTableModel> trs = new TableRowSorter<>(td);
				valueTable.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter(search));
			}
		});
		panel.add(new JScrollPane(valueTable));

		tranPanel.setVisible(false);

		sumBtn = new JButton("");
		sumBtn.setBounds(25, 110, 270, 50);
		sumBtn.setIcon(new ImageIcon("C:\\hjun\\code\\java\\workspace\\Accountingapp\\imgsrc\\Summery.PNG"));
		sumBtn.setBorder(null);
		sumBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currPanel.setVisible(false);
				sumPanel.setVisible(true);
				currPanel = sumPanel;
			}
		});
		tranPanel.add(sumBtn);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblName.setBounds(353, 110, 89, 40);
		tranPanel.add(lblName);

		lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblType.setBounds(353, 186, 89, 40);
		tranPanel.add(lblType);

		lblAmounnt = new JLabel("Amount");
		lblAmounnt.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblAmounnt.setBounds(352, 260, 146, 40);
		tranPanel.add(lblAmounnt);

		lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblNote.setBounds(353, 337, 89, 40);
		tranPanel.add(lblNote);

		nameInput = new JTextField();
		nameInput.setFont(new Font("±¼¸²", Font.PLAIN, 33));
		nameInput.setBounds(492, 110, 794, 43);
		tranPanel.add(nameInput);
		nameInput.setColumns(10);

		String[] typeArr = new String[] { "Do not choose", "Deposit", "Withdraw" };
		JComboBox typeInput = new JComboBox(typeArr);
		typeInput.setFont(new Font("±¼¸²", Font.PLAIN, 33));
		typeInput.setBounds(492, 186, 794, 39);
		typeInput.setBackground(Color.white);
		tranPanel.add(typeInput);

		amtInput = new JTextField();
		amtInput.setFont(new Font("±¼¸²", Font.PLAIN, 33));
		amtInput.setColumns(10);
		amtInput.setBounds(492, 260, 794, 43);
		tranPanel.add(amtInput);

		JTextArea inputArea = new JTextArea();
		inputArea.setFont(new Font("Courier New", Font.PLAIN, 26));
		inputArea.setBounds(492, 341, 794, 206);
		tranPanel.add(inputArea);
		inputArea.setBorder(BorderFactory.createLineBorder(Color.gray));

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean fileExists = new File("./data.csv").exists();
					FileWriter fw = new FileWriter("./data.csv", true);
					if (!fileExists) {
						fw.append("Name, Type, Amount, Note\n");
					}
					String name = nameInput.getText();
					String type = (String) typeInput.getSelectedItem();
					String amount = amtInput.getText();
					String note = inputArea.getText();
					fw.append(name + ',' + type + ',' + amount + ',' + note + '\n');
					fw.close();
					JOptionPane.showMessageDialog(null, "Complete");
					td.refresh();
					nameInput.setText("");
					typeInput.setSelectedIndex(0);
					amtInput.setText("");
					inputArea.setText("");
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "error");
				}
			}
		});
		btnSubmit.setBounds(624, 557, 241, 44);
		tranPanel.add(btnSubmit);

		btnReset = new JButton("Reset");
		btnReset.setBounds(922, 557, 241, 44);
		tranPanel.add(btnReset);

		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ebj = e.getSource();
		if (ebj.equals(loginBtn)) {
			if (ID.equals(idField.getText()) && PASS.equals(pwField.getText())) {
				JOptionPane.showMessageDialog(null, "Login Success");
				currPanel.setVisible(false);
				sumPanel.setVisible(true);
				currPanel = sumPanel;
			} else {
				JOptionPane.showMessageDialog(null, "Login Failed");
			}
		}

	}
}

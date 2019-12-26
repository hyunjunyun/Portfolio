package stdre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Recal {

	int cnt = 1;
// ArrayList<Student> arr = new ArrayList<Student>();
	String[] inputRow = new String[4];
// Object[] data;
	private JFrame frame;
	private JLabel nLabel;
	private JTextField nText;
	private String[] heading = new String[] { "Name", " Std ID", "Avg" };
	private JLabel sLabel;
	private JTextField sText;
	Student stC;

	int i = 0;

	private JLabel kLabel;
	private JTextField kText;

	private JLabel eLabel;
	private JTextField eText;
	Object[][] data = new Object[][] {

	};;
	private JLabel mLabel;
	private JTextField mText;
	private JButton subBtn;
	private JButton remBtn;
	private JButton endBtn;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Recal window = new Recal();
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
	public Recal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Grade Calculator");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mpl = new JPanel();
		mpl.setBorder(null);
		mpl.setBounds(0, 0, 300, 300);
		mpl.setBackground(Color.WHITE);
		frame.getContentPane().add(mpl);
		mpl.setLayout(null);

		nLabel = new JLabel("Name");
		nLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		nLabel.setBounds(370, 70, 60, 20);
		mpl.add(nLabel);

		nText = new JTextField();
		nText.setBackground(Color.LIGHT_GRAY);
		nText.setBorder(null);
		nText.setBounds(430, 70, 130, 20);
		nText.addFocusListener(null);
		mpl.add(nText);

		sLabel = new JLabel("Std ID");
		sLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		sLabel.setBounds(580, 70, 60, 20);
		mpl.add(sLabel);

		sText = new JTextField();
		sText.setBackground(Color.LIGHT_GRAY);
		sText.setBorder(null);
		sText.setBounds(640, 70, 130, 20);
		mpl.add(sText);

		kLabel = new JLabel("Kor");
		kLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		kLabel.setBounds(370, 100, 40, 20);
		mpl.add(kLabel);

		kText = new JTextField();
		kText.setBackground(Color.LIGHT_GRAY);
		kText.setBorder(null);
		kText.setBounds(430, 100, 130, 20);
		mpl.add(kText);

		eLabel = new JLabel("Eng");
		eLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		eLabel.setBounds(590, 100, 40, 20);
		mpl.add(eLabel);

		eText = new JTextField();
		eText.setBackground(Color.LIGHT_GRAY);
		eText.setBorder(null);
		eText.setBounds(640, 100, 130, 20);
		mpl.add(eText);

		mLabel = new JLabel("Math");
		mLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		mLabel.setBounds(370, 130, 40, 20);
		mpl.add(mLabel);

		mText = new JTextField();
		mText.setBackground(Color.LIGHT_GRAY);
		mText.setBorder(null);
		mText.setBounds(430, 130, 130, 20);
		mpl.add(mText);

		JLabel tTitle = new JLabel("Student Table");
		tTitle.setBounds(90, 10, 175, 30);
		mpl.add(tTitle);
		tTitle.setForeground(new Color(153, 51, 0));
		tTitle.setBackground(new Color(204, 255, 255));
		tTitle.setFont(new Font("±¼¸²", Font.PLAIN, 25));

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setForeground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBounds(20, 45, 300, 400);
		mpl.add(panel);
		DefaultTableModel model = new DefaultTableModel(data, heading) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		JTable jTable = new JTable(model);
		jTable.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		jTable.setPreferredScrollableViewportSize(new Dimension(300, 500));

		jTable.setFillsViewportHeight(true);
		jTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBounds(0, 0, 300, 400);
		panel.add(scrollPane);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
//Add
		subBtn = new JButton("Add");
		subBtn.setBackground(Color.BLACK);
		subBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));

		subBtn.setForeground(new Color(255, 255, 255));
		subBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double sum;
				double avg;
				try {
					sum = Double.valueOf(Integer.parseInt(kText.getText()))
							+ Double.valueOf(Integer.parseInt(eText.getText()))
							+ Double.valueOf(Integer.parseInt(mText.getText()));
					avg = sum / 3;
					inputRow[0] = nText.getText();
					inputRow[1] = sText.getText();
					inputRow[2] = Double.toString(avg);
					model.addRow(inputRow);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter input box", "Error", JOptionPane.ERROR_MESSAGE);
				}

				nText.setText("");
				sText.setText("");
				kText.setText("");
				mText.setText("");
				eText.setText("");
			}
		});
		subBtn.setBounds(420, 180, 80, 40);
		subBtn.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mpl.add(subBtn);
//remove
		remBtn = new JButton("Remove");
		remBtn.setBackground(Color.BLACK);
		remBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		remBtn.setForeground(new Color(255, 255, 255));
		remBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
				int n = jTable.getSelectedRow();
				if (n >= 0 && n < jTable.getRowCount()) {
// JOptionPane.showMessageDialog(null, "Remove complete");
					model.removeRow(n);
				}

			}
		});
//reset
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBackground(Color.DARK_GRAY);
		resetBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		resetBtn.setForeground(new Color(255, 255, 255));
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
				for (int i = model.getRowCount() - 1; i >= 0; i--) {
					model.removeRow(i);
				}

			}
		});
		resetBtn.setBounds(600, 430, 60, 30);
		resetBtn.setBorder(null);
		mpl.add(resetBtn);
		remBtn.setBounds(510, 180, 80, 40);
		remBtn.setBorder(null);
		mpl.add(remBtn);

//exit
		endBtn = new JButton("Exit");
		endBtn.setBackground(Color.BLACK);
		endBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		endBtn.setForeground(new Color(255, 255, 255));
		endBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
				frame.dispose();
			}
		});
		endBtn.setBounds(670, 430, 100, 30);
		endBtn.setBorder(null);
		mpl.add(endBtn);

//save
		JButton svBtn = new JButton("Save");
		svBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame parentFrame = new JFrame();

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				}
			}
		});

		svBtn.setForeground(Color.WHITE);
		svBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		svBtn.setBorder(null);
		svBtn.setBackground(Color.BLACK);
		svBtn.setBounds(690, 180, 80, 40);
		mpl.add(svBtn);

		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			JFileChooser j = new JFileChooser();

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileNameExtensionFilter filter = new FileNameExtensionFilter("txt & xlsx files", "txt", "xlsx");
					j.setFileFilter(filter);

					int ret = j.showOpenDialog(null);
					if (ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);

						return;
					}

					String filePath = j.getSelectedFile().getPath();
					File file = new File(filePath);
					try {
						BufferedReader br = new BufferedReader(new FileReader(file));
// String[] clnm = firstLine.split(" ");
// model.setColumnIdentifiers(clnm);

						Object[] tableLines = br.lines().toArray();
						for (int i = 0; i < tableLines.length; i++) {
							String line = tableLines[i].toString();
							String[] dataRow = line.split(" ");
							model.addRow(dataRow);
						}
					} catch (Exception e2) {
// TODO: handle exception
					}

				} catch (Exception e2) {
// TODO: handle exception
				}

			}
		});
		btnImport.setForeground(Color.WHITE);
		btnImport.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnImport.setBorder(null);
		btnImport.setBackground(Color.BLACK);
		btnImport.setBounds(600, 180, 80, 40);
		mpl.add(btnImport);

// title

	}
}

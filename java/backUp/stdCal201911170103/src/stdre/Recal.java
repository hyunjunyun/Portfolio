package stdre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class Recal {

	int cnt=0;
//	ArrayList<Student> arr = new ArrayList<Student>();
	ArrayList<Student> arr = new ArrayList<Student>();
	ArrayList<String> narr = new ArrayList<String>();
	ArrayList<String> sarr = new ArrayList<String>();
	ArrayList<Integer> aarr = new ArrayList<Integer>();
//	Object[] data;
	private JFrame frame;
	private JLabel nLabel;
	private JTextField nText;
	private String[] heading = new String[] { "Rank", "Name", " Std ID", "Avr" };
	private JLabel sLabel;
	private JTextField sText;
	Student stC;

	int i = 0;

	private JTable jTable;
	private JLabel kLabel;
	private JTextField kText;

	private JLabel eLabel;
	private JTextField eText;
	Object[][] data;
	private JLabel mLabel;
	private JTextField mText;
	private JButton subBtn;
	private JButton resBtn;
	private JButton endBtn;
	private JTable table;
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
		frame.setSize(300, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mpl = new JPanel();
		mpl.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(135, 206, 235), new Color(127, 255, 212),
				new Color(152, 251, 152), new Color(100, 149, 237)));
		mpl.setBounds(0, 0, 300, 300);
		mpl.setBackground(Color.WHITE);
		frame.getContentPane().add(mpl);
		mpl.setLayout(null);

		nLabel = new JLabel("Name");
		nLabel.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		nLabel.setBounds(40, 25, 60, 20);
		mpl.add(nLabel);

		nText = new JTextField();
		nText.setBackground(Color.LIGHT_GRAY);
		nText.setBorder(null);
		nText.setBounds(100, 25, 130, 20);
		nText.addFocusListener(null);
		mpl.add(nText);

		sLabel = new JLabel("Std ID");
		sLabel.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		sLabel.setBounds(40, 60, 60, 20);
		mpl.add(sLabel);

		sText = new JTextField();
		sText.setBackground(Color.LIGHT_GRAY);
		sText.setBorder(null);
		sText.setBounds(100, 60, 130, 20);
		mpl.add(sText);

		kLabel = new JLabel("Kor");
		kLabel.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		kLabel.setBounds(40, 95, 40, 20);
		mpl.add(kLabel);

		kText = new JTextField();
		kText.setBackground(Color.LIGHT_GRAY);
		kText.setBorder(null);
		kText.setBounds(100, 95, 130, 20);
		mpl.add(kText);

		eLabel = new JLabel("Eng");
		eLabel.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		eLabel.setBounds(40, 130, 40, 20);
		mpl.add(eLabel);

		eText = new JTextField();
		eText.setBackground(Color.LIGHT_GRAY);
		eText.setBorder(null);
		eText.setBounds(100, 130, 130, 20);
		mpl.add(eText);

		mLabel = new JLabel("Math");
		mLabel.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		mLabel.setBounds(40, 165, 40, 20);
		mpl.add(mLabel);

		mText = new JTextField();
		mText.setBackground(Color.LIGHT_GRAY);
		mText.setBorder(null);
		mText.setBounds(100, 165, 130, 20);
		mpl.add(mText);

		JLabel tTitle = new JLabel("Top Result");
		tTitle.setBounds(80, 280, 150, 30);
		mpl.add(tTitle);
		tTitle.setForeground(new Color(153, 51, 0));
		tTitle.setBackground(new Color(204, 255, 255));
		tTitle.setFont(new Font("±º∏≤", Font.PLAIN, 25));
		// suBtitle
		JLabel trank = new JLabel("Rank");
		trank.setBounds(30, 320, 50, 20);
		mpl.add(trank);
		trank.setFont(new Font("±º∏≤", Font.PLAIN, 16));
		JLabel tname = new JLabel("Name");
		tname.setBounds(90, 320, 50, 20);
		mpl.add(tname);
		tname.setFont(new Font("±º∏≤", Font.PLAIN, 16));

		JLabel tstd = new JLabel("Student ID");
		tstd.setBounds(150, 320, 80, 20);
		mpl.add(tstd);
		tstd.setFont(new Font("±º∏≤", Font.PLAIN, 16));

		JLabel tavg = new JLabel("Avg");
		tavg.setBounds(235, 320, 40, 19);
		mpl.add(tavg);
		tavg.setFont(new Font("±º∏≤", Font.PLAIN, 16));

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setForeground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(135, 206, 250), new Color(176, 196, 222),
				new Color(173, 255, 47), new Color(127, 255, 212)));
		panel.setBounds(0, 350, 292, 421);
		mpl.add(panel);

		subBtn = new JButton("Input");
		subBtn.setBackground(Color.BLACK);
		subBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		subBtn.setForeground(new Color(255, 255, 255));
		subBtn.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name2 = nText.getText();
				String std2 = sText.getText();
				int kor2 = Integer.parseInt(kText.getText());
				int math2 = Integer.parseInt(mText.getText());
				int eng2 = Integer.parseInt(eText.getText());
				stC = new Student(name2, std2, kor2, math2, eng2);
				narr.add(stC.getName());
				sarr.add(stC.getStd());
				aarr.add(stC.getAvg());
				
				System.out.println(arr.size());

				// ≈ÿΩ∫∆Æ« µÂ √ ±‚»≠
				nText.setText("");
				sText.setText("");
				kText.setText("");
				mText.setText("");
				eText.setText("");
			}
		});
		subBtn.setBounds(20, 220, 70, 50);
		subBtn.setBorder(null);
		mpl.add(subBtn);
		
		resBtn = new JButton("Result");
		resBtn.setBackground(Color.BLACK);
		resBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		resBtn.setForeground(new Color(255, 255, 255));
		resBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Result");

				data = new Object[narr.size()][4];
				for (int i = 0; i < narr.size(); i++) {
					data[i][0] = i+1;
					data[i][1] = narr.get(i);
					data[i][2] = sarr.get(i);
					data[i][3]= aarr.get(i);
				}
				jTable = new JTable(data, heading);
				jTable.setBounds(0, 0, 300, 300);
				//jTable.setPreferredScrollableViewportSize(new Dimension(0, 0));
//				jTable.setFillsViewportHeight(true);
				panel.add(jTable);
				
			}
		});
		resBtn.setBounds(100, 220, 90, 50);
		resBtn.setBorder(null);
		mpl.add(resBtn);
		
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
		endBtn.setBounds(200, 220, 60, 50);
		endBtn.setBorder(null);
		mpl.add(endBtn);

		// title

		// frame.pack();
	}
}

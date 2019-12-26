package stdre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class Recal {

	int cnt=1;
//	ArrayList<Student> arr = new ArrayList<Student>();
	String[] inputRow = new String[4];
//	Object[] data;
	private JFrame frame;
	private JLabel nLabel;
	private JTextField nText;
	private String[] heading = new String[] {"Name", " Std ID", "Avg" };
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
		frame.setSize(300, 520);
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
		nLabel.setBounds(40, 25, 60, 20);
		mpl.add(nLabel);

		nText = new JTextField();
		nText.setBackground(Color.LIGHT_GRAY);
		nText.setBorder(null);
		nText.setBounds(100, 25, 130, 20);
		nText.addFocusListener(null);
		mpl.add(nText);

		sLabel = new JLabel("Std ID");
		sLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		sLabel.setBounds(40, 60, 60, 20);
		mpl.add(sLabel);

		sText = new JTextField();
		sText.setBackground(Color.LIGHT_GRAY);
		sText.setBorder(null);
		sText.setBounds(100, 60, 130, 20);
		mpl.add(sText);

		kLabel = new JLabel("Kor");
		kLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		kLabel.setBounds(40, 95, 40, 20);
		mpl.add(kLabel);

		kText = new JTextField();
		kText.setBackground(Color.LIGHT_GRAY);
		kText.setBorder(null);
		kText.setBounds(100, 95, 130, 20);
		mpl.add(kText);

		eLabel = new JLabel("Eng");
		eLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		eLabel.setBounds(40, 130, 40, 20);
		mpl.add(eLabel);

		eText = new JTextField();
		eText.setBackground(Color.LIGHT_GRAY);
		eText.setBorder(null);
		eText.setBounds(100, 130, 130, 20);
		mpl.add(eText);

		mLabel = new JLabel("Math");
		mLabel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
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
		tTitle.setFont(new Font("±¼¸²", Font.PLAIN, 25));

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setForeground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBounds(0, 310, 300, 100);
		mpl.add(panel);
		
		
		DefaultTableModel model = new DefaultTableModel(data,heading) {
			public boolean isCellEditable(int i, int c){ 
				return false ;
			}
		};
		JTable jTable= new JTable(model);
		jTable.setPreferredScrollableViewportSize(new Dimension(200, 100));

		jTable.setFillsViewportHeight(true);
		jTable.setAutoCreateRowSorter (true);
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setSize(300, 100);
		scrollPane.setBorder(null);
		panel.add(scrollPane,BorderLayout.CENTER);
		
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
					sum = Double.valueOf(Integer.parseInt(kText.getText()))+
							Double.valueOf(Integer.parseInt(eText.getText()))+
							Double.valueOf(Integer.parseInt(mText.getText()));
					avg = sum/3;
					inputRow[0] = nText.getText();
					inputRow[1] = sText.getText();
					inputRow[2] = Double.toString(avg);
					model.addRow(inputRow);
				} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please enter input box","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				 nText.setText("");
				 sText.setText("");
				 kText.setText("");
				 mText.setText("");
				 eText.setText("");
			}
		});
		subBtn.setBounds(13, 220, 60, 40);
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
				if(n>=0 && n< jTable.getRowCount()) {
//					JOptionPane.showMessageDialog(null, "Remove complete");
					model.removeRow(n);
				}
					
			}
		});
		//reset
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBackground(Color.BLACK);
		resetBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		resetBtn.setForeground(new Color(255, 255, 255));
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for( int i = model.getRowCount() - 1; i >= 0; i-- )
				{
				    model.removeRow(i);
				}
					
			}
		});
		resetBtn.setBounds(10, 450, 60, 30);
		resetBtn.setBorder(null);
		mpl.add(resetBtn);
		remBtn.setBounds(83, 220, 60, 40);
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
		endBtn.setBounds(220, 450, 60, 30);
		endBtn.setBorder(null);
		mpl.add(endBtn);
		
		//save
		JButton svBtn = new JButton("Save");
		svBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filePath = "C:\\hjun\\code\\java\\appfilespce\\table.txt";
				File file = new File(filePath);
				try {
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
		            JOptionPane.showMessageDialog(null, "Save completed");
		            for(int i = 0; i < jTable.getRowCount(); i++){//rows
		                for(int j = 0; j < jTable.getColumnCount(); j++){//columns
		                    bw.write(jTable.getValueAt(i, j).toString()+" ");
		                }
		                bw.newLine();
		            }
		            
		            bw.close();
		            fw.close();
		            
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		svBtn.setForeground(Color.WHITE);
		svBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));
		svBtn.setBorder(null);
		svBtn.setBackground(Color.BLACK);
		svBtn.setBounds(223, 220, 60, 40);
		mpl.add(svBtn);
		
		JButton btnImport = new JButton("Import");
		btnImport.setForeground(Color.WHITE);
		btnImport.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnImport.setBorder(null);
		btnImport.setBackground(Color.BLACK);
		btnImport.setBounds(155, 220, 60, 40);
		mpl.add(btnImport);
		
		// title

	}
}

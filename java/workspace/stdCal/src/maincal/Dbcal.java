package maincal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

class Dbcal extends JFrame implements ActionListener {
	JPanel tablePanel;
	JPanel inputPanel;
	JPanel sidePanel;
	JPanel grid;
	Dbset ds = new Dbset();

	JButton addBtn = new JButton("Add");
	JButton remBtn = new JButton("Remove");
	JButton resetBtn = new JButton("Reset");

	JLabel nl = new JLabel("Name:");
	JTextField nf = new JTextField();
	JLabel il = new JLabel("Std Id: ");
	JTextField sf = new JTextField();
	JLabel al = new JLabel("Avg: ");
	JTextField af = new JTextField();

	JTable table = new JTable();
	private String[] head = new String[] { "StdId", "Name", "Avg" };
	DefaultTableModel model;
	Object[][] data = new Object[][] {};
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu = new JMenu("File");
	private final JMenuItem saveItem = new JMenuItem("Save");
	private final JMenuItem importItem = new JMenuItem("Import");
	private final JMenuItem downItem = new JMenuItem("Download");
	private final JButton exit = new JButton("Exit");

	public Dbcal() {
		super("STD record Calculator");
		createUI();
		createMenu();

	}

	public static void main(String[] args) {
		new Dbcal();
	}

	public void setRefresh() {
		Dbset da = new Dbset();
		da.userRefresh(model);
	}

	private void insertValue() {
		Dbadd dda = getViewData();
		Dbset value = new Dbset();
		value.insertTablevalue(dda);
		sf.setText("");
		nf.setText("");
		af.setText("");
	}

	public Dbadd getViewData() {
		Dbadd da = new Dbadd();
		String std = sf.getText();
		String name = nf.getText();
		int avg = Integer.parseInt(af.getText());

		da.setStd(std);
		da.setName(name);
		da.setAvg(avg);

		return da;
	}

	public void createMenu() {
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);

		menuBar.add(menu);

		menu.add(saveItem);

		menu.add(importItem);

		menu.add(downItem);
	}

	public void fileImport() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt & csv", "txt","prn");
		chooser.addChoosableFileFilter(filter);
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);

			return;

		}
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			Object[] tableLines = br.lines().toArray();
			for (int i = 0; i < tableLines.length; i++) {
				String line = tableLines[i].toString();
				String[] dataRow = line.split(",");
				Dbadd da = new Dbadd();
				if(i==0) {
					continue;
				}
				da.setStd(dataRow[0]);
				da.setName(dataRow[1]);
				da.setAvg(Integer.parseInt(dataRow[2]));
				ds.insertTablevalue(da);
				model.addRow(dataRow);
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "retry plz", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fileSave() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt & prn", "txt","csv");
		chooser.addChoosableFileFilter(filter);
		int ret = chooser.showSaveDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);

			return;

		}
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		try {
			FileWriter prn = new FileWriter(file);
			for (int i = 0; i < model.getColumnCount(); i++) {
				prn.write(model.getColumnName(i) + ",");
			}

			prn.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					prn.write(model.getValueAt(i, j).toString() + ",");
				}
				prn.write("\n");
			}
			prn.close();
			JOptionPane.showMessageDialog(null, "Download complete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Download failed","Error",JOptionPane.ERROR_MESSAGE);
		}

	}

	public void createUI() {
		Container mainContainer = this.getContentPane();

		model = new DefaultTableModel(data, head);
		JTable table = new JTable(model);
		table.setBackground(new Color(240, 255, 240));
		table.getRowSelectionAllowed();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(scrollPane);

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(4, 1, 5, 5));
		mainContainer.setLayout(new BorderLayout(8, 6));
		mainContainer.setBackground(Color.white);

		grid.add(remBtn);

		grid.add(resetBtn);

		sidePanel = new JPanel();
		sidePanel.setLayout(new FlowLayout(5));
		sidePanel.add(grid);
		grid.add(exit);
		exit.addActionListener(this);
		mainContainer.add(sidePanel, BorderLayout.WEST);
		mainContainer.add(tablePanel, BorderLayout.CENTER);

		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(3));
		inputPanel.add(il);
		inputPanel.add(sf);
		sf.setColumns(10);
		inputPanel.add(nl);
		inputPanel.add(nf);
		nf.setColumns(10);
		mainContainer.add(inputPanel, BorderLayout.SOUTH);
		inputPanel.add(al);
		inputPanel.add(af);
		af.setColumns(10);
		inputPanel.add(addBtn);

		addBtn.addActionListener(this);
		remBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		saveItem.addActionListener(this);
		importItem.addActionListener(this);
		downItem.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				nf.requestFocus();
			}
		});
		setRefresh();

		setBackground(Color.BLACK);
		setLocation(620, 300);
		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		// TODO Auto-generated method stub
		if (src == addBtn) {
			insertValue();
			setRefresh();
		} else if (src == remBtn) {
			Dbset ds = new Dbset();
			int row = table.getSelectedRow();
			String std = model.getValueAt(row, 0).toString();

			ds.deleteValue(std);
			setRefresh();
		} else if (src == resetBtn) {
			ds.allDelete();
			setRefresh();
		} else if (src == saveItem) {
			int result = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?(종료됩니다)", "Save", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				dispose();
			}
		} else if (src == importItem) {
			fileImport();
		} else if (src == downItem) {
			fileSave();
		} else {
			int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까 (저장되지 않습니다)", "Exit", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				ds.allDelete();
				dispose();
			}
		}
	}
}

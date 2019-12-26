import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tableprc {
	private static JButton subBtn;
	ArrayList<String> narr = new ArrayList<String>();
	ArrayList<String> sarr = new ArrayList<String>();
	ArrayList<Integer> aarr = new ArrayList<Integer>();
	static int i = 0;

	public static void main(String[] args) {
		String[] inputRow = new String[4];

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		String[] headings = new String[] { "id", "Name", "Country" };
		Object[][] data = new Object[][] { { "1", "da", "ko" }, { "2", "ka", "fr" }, { "3", "lo", "jp" }, };

		DefaultTableModel model = new DefaultTableModel(data, headings);
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(700, 600));

		table.setFillsViewportHeight(true);

		panel.add(new JScrollPane(table));

		frame.add(panel);

		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		subBtn = new JButton("Input");
		subBtn.setBackground(Color.BLACK);
		subBtn.setFont(new Font("Gadugi", Font.PLAIN, 14));

		subBtn.setForeground(new Color(255, 255, 255));
		subBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				i++;
				inputRow[0] = Integer.toString(i);
				inputRow[1] = Integer.toString(i);
				inputRow[2] = Integer.toString(i);
				inputRow[3] = Integer.toString(i);
				table.getAutoscrolls();
				model.addRow(inputRow);

// 텍스트필드 초기화
// nText.setText("");
// sText.setText("");
// kText.setText("");
// mText.setText("");
// eText.setText("");
			}
		});
		subBtn.setBounds(20, 220, 70, 50);
		subBtn.setBorder(null);
		panel.add(subBtn);
	}
}

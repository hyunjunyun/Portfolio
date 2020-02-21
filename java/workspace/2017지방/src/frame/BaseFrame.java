package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class BaseFrame extends JFrame {

	static Connection con;
	static Statement stmt;

	static String userId;
	static String userName;
	
	static DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
	
	static {
		userId = "user01";
		userName = "강**";
		
		rend.setHorizontalAlignment(0);
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/sw3_Setting?serverTimezone=UTC","root","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(int w, int h, String title, int c) {
		setDefaultCloseOperation(c);
		setTitle(title);
		setSize(w, h);
		setLocationRelativeTo(null);
	}

	public static void eMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "메시지", JOptionPane.WARNING_MESSAGE);
	}

	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "웹 페이지 메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel setLabel(JLabel lb, Font font) {
		lb.setFont(font);
		return lb;
	}

	public static JPanel setPanel(LayoutManager lay, JComponent... comp) {
		var jp = new JPanel();
		jp.setLayout(lay);

		for (JComponent C : comp) {
			jp.add(C);
		}
		return jp;
	}
	
	public static JPanel setPanel(LayoutManager lay, Border b ,JComponent... comp) {
		var jp = new JPanel();
		jp.setLayout(lay);
		for (JComponent C : comp) {
			jp.add(C);
		}
		jp.setBorder(b);
		return jp;
	}

	public static <T extends JComponent> T setComp(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		return comp;
	}

	public static <T extends JComponent> T setComp(T comp, int x, int y) {
		comp.setPreferredSize(new Dimension(x, y));
		return comp;
	}

	public static JButton setBtn(String t, ActionListener e) {
		var btn = new JButton(t);
		btn.addActionListener(e);
		return btn;
	}

	public static JToggleButton setTBtn(String t, ActionListener e) {
		var btn = new JToggleButton(t);
		btn.addActionListener(e);
		return btn;
	}

	public void openPanel(JPanel offP, JPanel onP) {
		offP.setVisible(false);
		onP.setVisible(true);
	}

	public Integer toInt(String t) {
		return Integer.parseInt(t);
	}

	public String toStr(Integer t) {
		return Integer.toString(t);
	}
	
	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}

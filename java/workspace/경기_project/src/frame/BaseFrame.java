package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

public class BaseFrame extends JFrame {

	static Connection con;
	static Statement stmt;

	static int userNo;
	static String userName;

	static DefaultTableCellRenderer rend = new DefaultTableCellRenderer();

	static {

		userNo = 1;
		userName = "신은수";

		rend.setHorizontalAlignment(0);

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/hospital?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(int w, int h, String t, int close) {
		setSize(w, h);
		setTitle(t);
		setDefaultCloseOperation(close);
		setLocationRelativeTo(null);
	}

	public static void eMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "메시지", JOptionPane.ERROR_MESSAGE);
	}

	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel setLabel(JLabel jl, Font font) {
		jl.setFont(font);
		return jl;
	}

	public static <T extends JComponent> T setComp(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		return comp;
	}

	public static <T extends JComponent> T setComp(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h));
		return comp;
	}

	public static void setComp(JPanel jp, JComponent comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		comp.setPreferredSize(new Dimension(w, h));
		jp.add(comp);
	}

	public static JButton setBtn(String t, ActionListener e) {
		var btn = new JButton(t);
		btn.addActionListener(e);
		return btn;
	}

	public static JButton setBtnM(String t, ActionListener e,Color c) {
		var btn = new JButton(t);
		btn.addActionListener(e);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setForeground(c);
		return btn;
	}

	public static ImageIcon addImage(int w, int h, String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

}

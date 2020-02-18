package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class BaseFrame extends JFrame {
	public static Connection con;
	public static Statement stmt;

	public static DefaultTableCellRenderer rend = new DefaultTableCellRenderer();

	static {

		rend.setHorizontalAlignment(0);

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/MealSetting?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(int w, int h, String title) {
		JPanel jp = new JPanel();
		setSize(w, h);
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void eMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "message", JOptionPane.ERROR_MESSAGE);
	}

	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "message", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel setLabel(JLabel jl, Font font) {
		jl.setFont(font);
		return jl;
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
	
	public static JButton setBtnM(JButton btn, Font font, ActionListener e) {
		btn.setMargin(new Insets(10, 10, 10, 10));
		btn.addActionListener(e);
		return btn;
		
	}
	

	public static JButton setBtn(JButton btn, ActionListener e) {
		btn.addActionListener(e);
		return btn;
	}

	public static ImageIcon setIcon(String path, int w, int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

	public void setBorder(int x, int y, int w, int h) {
		JPanel jp = new JPanel();
		jp.setBorder(BorderFactory.createEmptyBorder(x, y, w, h));
	}
	
//	public JComponent setEmptyBorder(int x, int y, int w, int h, JComponent...com) {
//		JPanel jp = new JPanel();
//		jp.setBorder(BorderFactory.createEmptyBorder(x,y,w,h));
//		
//		for (JComponent comp : com) {
//			jp.add(comp);
//		}
//		return jp;
//	}
	
	public static JPanel setPanel(LayoutManager lay, JComponent...com) {
		var jp = new JPanel(lay);
		for (JComponent comp : com) {
			jp.add(comp);
		}
		return jp;
	}
	
	public static JComponent setEmpty(JPanel jp, int x, int y, int w, int h) {
		jp.setBorder(BorderFactory.createEmptyBorder(x,y,w,h));
		return jp;
	}

}

package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
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
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Toolkit;

public class FrameBase extends JFrame {
	public static Connection con;
	public static Statement stmt;

	public static int userNo;
	public static String userName;
	public static String userGrade;
	public static int userPoint;

	static DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	static {
		userNo = 1;
		userName = "이기민";
		userGrade = "일반";
		userPoint = 14400;

		rightRenderer.setHorizontalAlignment(4);
		centerRenderer.setHorizontalAlignment(0);

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public FrameBase(int w, int h, String title) {
		setTitle(title);
		setSize(w, h);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void eMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "메세지", JOptionPane.ERROR_MESSAGE);
	}

	public static void iMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "메세지", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel createLabel(JLabel comp, Font font) {
		comp.setFont(font);
		return comp;

	}

	public static <T extends JComponent> T createComp(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		return comp;
	}

	public static <T extends JComponent> T createComp(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}

	public static JButton createButton(String text, ActionListener e) {
		var btn = new JButton(text);
		btn.addActionListener(e);
		return btn;
	}

	public static JButton createButtonWithoutMargin(String text, ActionListener e) {
		var btn = new JButton(text);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.addActionListener(e);
		return btn;
	}

	public static ImageIcon getImage(String path, int w, int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}

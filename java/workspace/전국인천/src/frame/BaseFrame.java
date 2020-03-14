package frame;

import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BaseFrame extends JFrame {

	static Connection con;
	static Statement stmt;

	static String user1Name;
	static String user2Name;

	static {
		user1Name="바드";
		user2Name="피즈";
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/omok?serverTimezone=UTC","user","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public BaseFrame(int w, int h, String t, int n) {
		setSize(w, h);
		setTitle(t);
		setDefaultCloseOperation(n);
		setLocationRelativeTo(null);
	}
	
	public static void eMsg(String t) {
		JOptionPane.showMessageDialog(null, t,"메시지",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t,"메시지",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static JLabel setLabel(JLabel lb, Font font) {
		lb.setFont(font);
		return lb;
	}
	
	public static <T extends JComponent> T setComp(T comp, int x, int y, int w, int h) {
		comp.setBounds(x,y,w,h);
		return comp;
	}
	
	public static <T extends JComponent> T setComp(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}
	
	public static JButton setBtn(String t, ActionListener l) {
		var btn = new JButton(t);
		btn.addActionListener(l);
		return btn;
	}
	
	public static ImageIcon getImage(String path, int w,int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
	public void openFrame(JFrame f) {
		dispose();
		f.setVisible(true);
	}
}

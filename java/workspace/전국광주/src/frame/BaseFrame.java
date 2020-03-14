package frame;

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
import javax.swing.table.DefaultTableCellRenderer;

public class BaseFrame extends JFrame{ 

	static Connection con;
	static Statement stmt;
	
	
	static String userNo;
	static String userName;
	
	static DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
	
	static {
		userNo = "1";
		userName="유민우";
		
		rend.setHorizontalAlignment(0);
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/company?serverTimezone=UTC","user","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BaseFrame(int w, int h, String t, int s) {
		setSize(w,h);
		setTitle(t);
		setDefaultCloseOperation(s);
		setLocationRelativeTo(null);
	}
	
	public static void eMsg(String t) {
		JOptionPane.showMessageDialog(null, t,"메시지",JOptionPane.WARNING_MESSAGE);
	}
	
	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t,"메시지",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static JLabel setLabel(JLabel jl, Font font) {
		jl.setFont(font);
		return jl;
	}
	
	public static <T extends JComponent> T setComp(T comp, int x, int y, int w,int h) {
		comp.setBounds(x,y,w,h);
		return comp;
	}
	
	public static <T extends JComponent> T setComp(T comp,int w,int h) {
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}
	
	public static JButton setBtn(String t, ActionListener e) {
		var btn = new JButton(t);
		btn.addActionListener(e);
		return btn;
	}
	
	public static JButton setBtnM(String t, ActionListener e) {
		var btn = new JButton(t);
		btn.addActionListener(e);
		btn.setMargin(new Insets(0,0,0,0));
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

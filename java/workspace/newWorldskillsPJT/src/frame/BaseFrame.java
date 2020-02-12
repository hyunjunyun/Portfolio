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
	static Connection con = null;
	static Statement stmt = null;
	
	static int userNo;
	static String userName;
	static String userGrade;
	static int userPoint;
	
	static DefaultTableCellRenderer Rrend = new DefaultTableCellRenderer();
	static DefaultTableCellRenderer Crend = new DefaultTableCellRenderer();
	
	static {
		userNo = 1;
		userName = "이기민";
		userGrade = "일반";
		userPoint = 14210;
		
		Rrend.setHorizontalAlignment(4);
		Crend.setHorizontalAlignment(0);
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC","user","1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public BaseFrame(int w, int h, String title) {
		setSize(w,h);
		setLocationRelativeTo(null);
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void eMsg(String text) {
		JOptionPane.showMessageDialog(null, text,"메시지",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void iMsg(String text) {
		JOptionPane.showMessageDialog(null, text,"메시지",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static JLabel setLabel(JLabel label, Font font) {
		label.setFont(font);
		return label;
	}
	
	public static <T extends JComponent> T setComp(T comp, int x, int y, int w, int h){
		comp.setBounds(x,y,w,h);
		return comp;
	}
	
	public static <T extends JComponent> T setComp(T comp, int w, int h){
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}
	
	public static JButton setBtn(String text, ActionListener e) {
		var btn = new JButton(text);
		btn.addActionListener(e);
		return btn;
	}
	
	public static JButton setBtnM(String text, ActionListener e) {
		var btn = new JButton(text);
		btn.addActionListener(e);
		btn.setMargin(new Insets(0,0,0,0));
		return btn;
	}
	
	public static ImageIcon getImage(String path, int w, int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
	public void openFrame(JFrame f) {
		dispose();
		f.setVisible(true);
	}
}

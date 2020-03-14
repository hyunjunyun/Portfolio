import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public abstract class BaseFrame extends JFrame {
	JButton jb[] = new JButton[10];
	JComboBox jcom[] = new JComboBox[10];
	JTextField jtf[] = new JTextField[10];
	JPasswordField jpf[] = new JPasswordField[10];
	JLabel jl[] = new JLabel[10];
	JPanel jp[] = new JPanel[10];

	static Connection con;
	static Statement stmt;
	
	static int userNo;
	static String userName;
	static String userAdress;	
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/market?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
			
			userNo = 1;
			userName = "윤현준";
			userAdress = "기능반";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(int w, int h, String title, LayoutManager lay, int de) {
		setSize(w, h);
		setTitle(title);
		setLayout(lay);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(de);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				openJFrame();
			}
		});
	}

	abstract void openJFrame();
	
	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
	int toInt(Object integer) {
		return Integer.parseInt(integer.toString());
	}

	static void eMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	static void iMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	static ImageIcon addImage(String path, int w, int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}
	
	public static JLabel setLabel(JLabel jl, Font font,Color c) {
		jl.setFont(font);
		jl.setForeground(c);
		return jl;
	}
	
	public static <T extends JComponent> T setComp(T comp, int x, int y,int w, int h) {
		comp.setBounds(x,y,w,h);
		return comp;
	}
	
	public static <T extends JComponent> T setComp(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w,h));
		return comp;
	}
	
	public static JButton setBtn(String t, ActionListener action) {
		var btn = new JButton(t);
		btn.addActionListener(action);
		return btn;
	}
	
	public static JButton setBtnM(String t, ActionListener action) {
		var btn = new JButton(t);
		btn.addActionListener(action);
		btn.setMargin(new Insets(0,0,0,0));
		return btn;
	}
	
	void setComponents(JPanel jp, JComponent comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w,h));
		jp.add(comp);
	}//패널에 add

	void setComponents(JPanel jp,JComponent jcom, int x, int y, int w, int h) {
		jcom.setBounds(x, y, w, h);
		jp.add(jcom);
	}//frame에 add
}

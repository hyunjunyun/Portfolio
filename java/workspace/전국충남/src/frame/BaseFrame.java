package frame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BaseFrame extends JFrame {
	
	static int userNo;
	static String userName;
	
	static {
		userNo = 1;
		userName = "신은서";
	}
	public BaseFrame(int w, int h, String t, int d) {
		setTitle(t);
		setSize(w, h);
		setDefaultCloseOperation(d);
		setLocationRelativeTo(null);
	}

	public static void eMsg(String t, String nt) {
		JOptionPane.showMessageDialog(null, t, nt, JOptionPane.ERROR_MESSAGE);
	}

	public static void iMsg(String t) {
		JOptionPane.showMessageDialog(null, t, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	public static JLabel setLabel(JLabel lb, Font font) {
		lb.setFont(font);
		return lb;
	}

	public static <T extends JComponent> T setComp(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		return comp;
	}

	public static <T extends JComponent> T setComp(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h));
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
		btn.setMargin(new Insets(0, 0, 0, 0));
		return btn;
	}

	public static ImageIcon setImage(int w, int h, String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}
}

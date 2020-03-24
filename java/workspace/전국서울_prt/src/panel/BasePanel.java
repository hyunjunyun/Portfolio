package panel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BasePanel extends JPanel{
	
	public BasePanel(int w, int h) {
		setPreferredSize(new Dimension(w, h));
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

	public static JButton setBtn(String t, ActionListener l) {
		var btn = new JButton(t);
		btn.addActionListener(l);
		return btn;
	}
	
	public static JButton setBtnM(String t, ActionListener l) {
		var btn = new JButton(t);
		btn.addActionListener(l);
		btn.setMargin(new Insets(0,0,0,0));
		return btn;
	}

	public static ImageIcon getImage(int w, int h, String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void openPanel(JPanel p) {
		setVisible(false);
		p.setVisible(true);
	}

}

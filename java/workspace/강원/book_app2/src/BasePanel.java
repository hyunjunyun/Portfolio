import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public abstract class BasePanel extends JPanel{
	
	String err = "";
	LineBorder black = new LineBorder(Color.black);
	
	MainFrame main;
	
	public BasePanel(String name, int w, int h , LayoutManager lay,MainFrame main) {
		setName(name);
		setPreferredSize(new Dimension(w, h));
		setLayout(lay);
		this.main = main;
		
	}
	abstract void addComponents();
	abstract void addListeners();
	
	void setComponents(JComponent com, int x, int y , int w, int h) {
		com.setBounds(x, y, w, h);
		com.setPreferredSize(new Dimension(w, h));
		add(com);
	}
	
	void setComponents(JPanel jp ,JComponent com, int x, int y , int w, int h) {
		com.setBounds(x, y, w, h);
		com.setPreferredSize(new Dimension(w, h));
		jp.add(com);
	}
	
	int toINt(Object obj) {
		return Integer.parseInt(obj.toString());
	}
	
	int toINt(String obj) {
		return Integer.parseInt(obj.toString());
	}
	
	void infoMsg(String msg, String title) {
		JOptionPane
		.showMessageDialog(null, msg,title,JOptionPane.INFORMATION_MESSAGE);
	}
	
	void errMsg(String msg, String title) {
		JOptionPane
		.showMessageDialog(null, msg,title,JOptionPane.ERROR_MESSAGE);
	}
	
	Image readImage(String path, int w, int h) {
		Image im = null;
		try {
			im = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 im = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		 return im;
	}
	
	boolean chkErr() {
		if(!err.equals(""))return false;
		return true;
	}
	
	JButton cB(String name, ActionListener e) {
		JButton jb = new JButton(name);
		jb.addActionListener(e);
		return jb;
	}
	
	JButton cB(String name, Image im, ActionListener e) {
		JButton jb = new JButton(name);
		jb.setIcon(new ImageIcon(im));
		jb.addActionListener(e);
		jb.setHorizontalTextPosition(JButton.CENTER);
		jb.setVerticalTextPosition(JButton.BOTTOM);
		return jb;
	}
	
	JLabel addJl(String str, int size, int sort) {
		JLabel jl  = new JLabel(str,sort);
		jl.setFont(new Font("", Font.BOLD, size));
		return jl;
	}
	
	
	Image readIcon(String name, int w, int h) {
		Image im = null;
		
		try {
			im = ImageIO.read(new File("./지급자료/icon_list/" + name +".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		im = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return im;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(0, 0, getWidth()-5, getHeight()-5, 15, 15);
	}
	
	boolean valDate(String date) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		sim.setLenient(false);
		try {
			sim.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}

import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.image.ImageConsumer;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public abstract  class BaseFrame extends JFrame{
	
	public BaseFrame(String title, int w, int h ,LayoutManager lay) {		
		setTitle(title);
		setSize(w, h);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(lay);
	}	
	abstract void addComponents();	
	abstract void addListeners();
	
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
	
	JButton cB(String name, ActionListener e) {
		JButton jb = new JButton(name);
		jb.addActionListener(e);
		return jb;
	}
	
	JButton cB(String name, Image im, ActionListener e) {
		JButton jb = new JButton(name);
		jb.setIcon(new ImageIcon(im));
		jb.addActionListener(e);
		return jb;
	}
	

}

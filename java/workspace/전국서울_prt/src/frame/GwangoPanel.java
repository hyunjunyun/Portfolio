package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GwangoPanel extends BasePanel{
	static short w=370,h=240;
	JLabel img = setComp(new JLabel(), w, h-80);
	String index="";
	public GwangoPanel(String name, int grade, int price, int i) {
		super(w, h);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		var sp = setComp(new JPanel(), 370, 80);
		sp.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 2, Color.black));
		img.setIcon(getImage(w, h-80, "./ImageFiles/hotel_image/"+i+".jpg"));;
		for (int j = 0; j < grade; j++) {
			index += "★";
		}
		sp.add(setComp(new JLabel("이름 : "+name,2), 280, 20));
		sp.add(setComp(new JLabel("성급 : "+index,2),150, 20));
		sp.add(new JLabel("1박당 가격 : "+String.format("%,d", price)));
		i++;
		add(img);
		add(sp,BorderLayout.SOUTH);
	}

}

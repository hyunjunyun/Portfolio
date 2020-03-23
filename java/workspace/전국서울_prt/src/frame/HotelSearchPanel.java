package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class HotelSearchPanel extends BasePanel{

	JPanel np = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel cp = new JPanel(new GridLayout(0,2));
	JPanel cp_d = new JPanel();
	///
	JTextField sf = setComp(new JTextField(), 350, 20);
	JComboBox<String> 
	
	UserData ud;
	
	public HotelSearchPanel(UserData ud) {
		super(750, 650);
		this.ud = ud;
		
		
		np.add(setComp(setLabel(new JLabel("호텔이름",2), new Font("Gothic",1,18)),500,25));
		add(np,BorderLayout.NORTH);
		add(cp,BorderLayout.CENTER);
		np.setBorder(new LineBorder(Color.black));
		cp.setBorder(new LineBorder(Color.black));
		setBorder(new LineBorder(Color.black));
		
	}

}

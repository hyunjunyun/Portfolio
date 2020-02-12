import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends BaseFrame {

	JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel sou = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	JLabel sL[] = new JLabel[5];
	JLabel aL[] = new JLabel[5];
	ArrayList<JPanel> arr = new ArrayList<JPanel>();
	static String aur = "", id = "", no = "";
	int position = -1;
	JButton jb[] = new JButton[2];
	
	
	
	public MainFrame() {
		super("자바문고", 1400, 800, new BorderLayout());
		setIconImage(readIcon("main_icon", 40, 40));
		addComponents();
		addListeners();
		setVisible(true);
	}

	void addComponents() {
		add(top, BorderLayout.NORTH);
		add(sou, BorderLayout.CENTER);
		top.setPreferredSize(new Dimension(1200, 45));
		top.add(jb[0] = cB("뒤로", readIcon("previous_icon", 25, 25), e -> setPosition(-1)));
		top.add(jb[1] =cB("앞으로", readIcon("next_icon", 25, 25), e -> setPosition(1)));

		for (int i = 0; i < 5; i++) {
			top.add(sL[i] = new JLabel(""));
			top.add(aL[i] = new JLabel(""));
		}
		setPanel(new LoginPanel(this));

	}
	
	void setPanel(JPanel jp) {
		sou.removeAll();
		sou.add(jp,c);
		arr.add(jp);
		chkList();
		setPosition(arr.size()-position-1);
		sou.revalidate();
		sou.repaint();
	}
	
	void chkList(){
		if(arr.size()>5)
			arr.remove(0);
		for(int i = 0; i  < arr.size();i++)
			sL[i].setText(arr.get(i).getName());
		for(int i = 0; i  < arr.size()-1;i++)
			aL[i].setText(">");
	}
	
	void moveP(JPanel jp) {
		sou.removeAll();
		sou.add(jp,c);
		sou.revalidate();
		sou.repaint();
	}
	

	void setPosition(int num) {

			position+=num;
			jb[0].setEnabled(true);
			jb[1].setEnabled(true);
			if(position==0)
				jb[0].setEnabled(false);
			if(position==arr.size()-1)
				jb[1].setEnabled(false);
			
			for(int i = 0 ; i <arr.size();i++)
				sL[i].setForeground(Color.black);
			sL[position].setForeground(Color.blue);
			moveP(arr.get(position));
	}
	
	void clear() {
		arr.clear();
		chkList();
	}

	void addListeners() {

	}

}

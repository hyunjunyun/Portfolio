package 대전;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeLetterForm extends BaseFrame {

	JLabel jl = new JLabel("1踰� �뵒�옄�씤", JLabel.CENTER);
	JButton jb[] = new JButton[3];
	JLabel imageL = new JLabel();
	int num = 1;
	JTextField text[] = new JTextField[2];
	String add, date;
	JPanel inner;

	public ChangeLetterForm(String add, String date) {
		super("泥�泥⑹옣 �뵒�옄�씤 怨좊Ⅴ湲�", 300, 500, null);
		this.add = add;
		this.date = date;
		addComponents();
		addListeners();
		setVisible(true);
	}

	@Override
	void addComponents() {
		setComponents(jl, 0, 0, 290, 15);

		inner = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(readImage("./吏�湲됱옄猷�/泥�泥⑹옣/泥�泥⑹옣" + num + ".jpg", 285, 400), 0, 0, 285, 400, null);
			}
		};

		setComponents(inner, 0, 15, 285, 400);
		setComponents(inner, new JLabel(add), 100, 240, 190, 20);
		setComponents(inner, new JLabel(date), 100, 270, 190, 20);
		setComponents(inner, text[0] = new JTextField(), 85, 130, 50, 20);
		setComponents(inner, text[1] = new JTextField(), 160, 130, 50, 20);
		addLong(0, 420, 290, 50, FlowLayout.CENTER, jb[0] = new JButton("��"), jb[1] = new JButton("寃곗젙"),
				jb[2] = new JButton("�뼳"));

	}

	void addListeners() {
		for (int i = 0; i < 3; i++) {
			jb[i].addActionListener(e -> {
				if (e.getSource().equals(jb[0])) {
					num = num -1;
					if(num==0)num=3;
					inner.repaint();
				} else if (e.getSource().equals(jb[1])) {
					complete();
				} else if (e.getSource().equals(jb[2])) {
					num = num +1;
					if(num==4)num=1;
					inner.repaint();
				}
			});
		}
	}

	void complete() {
		err = "";
		if(text[0].getText().equals("") || text[1].getText().equals(""))
			err="�씠由꾩쓣 �엯�젰�빐二쇱꽭�슂.";
		if(chkErr()==false)return;
		
		infoMSg("�뵒�옄�씤 " + num +"踰덉쑝濡� 寃곗죲�릺�뿀�뒿�땲�떎.");
		dispose();
	}
	
}

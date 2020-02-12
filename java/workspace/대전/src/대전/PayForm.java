package 대전;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class PayForm extends BaseFrame{
	

	
	JPanel middle = new JPanel(new GridLayout(0, 1));
	JLabel l;
	JLabel imageL = new JLabel();
	ArrayList<String> array;
	int reservationCode;
	JButton jb[] = new JButton[2];
	boolean goBefore = true;
	BufferedImage buf = null;
	public PayForm(ArrayList<String> arry,int code) {
		super("寃곗젣�븯湲�", 350, 600, new FlowLayout(FlowLayout.CENTER,5,5));
		reservationCode = code;
		this.array = arry;
		addComponents();
		addListeners();
		setVisible(true);
	}

	@Override
	void addComponents() {
		setComponents(l = new JLabel("寃곗젣 �꽌鍮꾩뒪",JLabel.CENTER), 0, 0, 335, 40);
		l.setBackground(Color.red);
		l.setForeground(Color.white);
		l.setOpaque(true);
		l.setFont(new Font("", Font.BOLD, 30));
		l.setBorder(new LineBorder(Color.black));
		setComponents(middle, 5, 45, 320, 250);
		String str[] = {"�썾�뵫��紐�","���궗�슜猷�","�떇�궗鍮꾩슜","�씤�썝�닔","�븿踰붿젣�옉","泥�泥⑹옣","�뱶�젅�뒪","珥� 湲덉븸"};
		JPanel p;
		getAllPrice();
		for(int i = 0 ; i <str.length;i++) {
			middle.add(p = new JPanel(new FlowLayout(FlowLayout.LEFT)));
			setComponents(p, new JLabel(str[i],JLabel.LEFT), 0, 0, 130, 20);
			p.add(new JLabel(array.get(i)+""));
			p.setBorder(new LineBorder(Color.black));
			if(i%2==0)
				p.setBackground(Color.white);
		}
		add(new JLabel("�궗�씤��"));
		setComponents(imageL, 320, 0, 320, 200);
		imageL.setBorder(new LineBorder(Color.black));
		add(jb[0] = new JButton("寃곗젣"));
		add(jb[1] = new JButton("痍⑥냼"));
	}
	
	void getAllPrice() {
		int num[] = {100000,150000,200000};
		int price = 0;
		for(int i = 4; i < 7; i++)
			if(array.get(i).equals("�떊泥�"))
				price+=num[i-4];
		price+=toInt(array.get(2)) * toInt(array.get(3));
		price+=toInt(array.get(1));
		
		array.add(price+"�썝");
	}

	void readImage(BufferedImage buf) {
		this.buf = buf;
		imageL.setIcon(new ImageIcon(buf));
	}
	
	@Override
	void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				if(goBefore)
					new ReservationCheckForm(reservationCode);
			}
		});
		
		imageL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				clickM();
			}
		});
		
		for(int i = 0 ; i  <2; i++) {
			jb[i].addActionListener(e->{
				if(e.getSource().equals(jb[0])) {
					complete();
				}
				else if(e.getSource().equals(jb[1])) {
					dispose();
				}
			});
		}
	}
	
	void clickM() {
		new SignForm(this);
	}
	
	void complete() {
		err="";
		if(buf==null)
			err="�궗�씤�쓣 �빐二쇱꽭�슂.";
		if(chkErr()==false)return;
		
		
		DBSetting.execute("update reservation set pay = 1 where reservation_code = " + reservationCode);
		infoMSg("寃곗젣媛� �셿猷뚮릺�뿀�뒿�땲�떎.");
		goBefore =false;
		dispose();
		new MainForm();
		
	}
	

}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class SearchPanel extends BasePanel{
	
	JComboBox com[] = new JComboBox[3];
	JTextField text= new JTextField(30);
	
	JPanel left =new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel right = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
	JPanel inner = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
	JScrollPane jsc = new JScrollPane(left);
	JScrollPane jsc2 = new JScrollPane(inner);
	
	JButton jb;
	
	String basicQ = "select b.id,b.sub_category_id,b.name,b.image,stock,format(b.price,'#,##0'),b.author,b.intro,b.numpages,b.isbn,date_format(b.created_by,'%x년 %m월 %d일'), b.hashtag from book as b";
	ArrayList<BuyList> arr = new ArrayList<SearchPanel.BuyList>();
	public SearchPanel(MainFrame main) {
		super("도서검색", 1350, 600, new FlowLayout(FlowLayout.CENTER), main);
		addComponents();
		addListeners();
		readData(basicQ);
		
	}

	@Override
	void addComponents() {
		
		for(int i = 0 ; i <3; i++) {
			add(com[i] = new JComboBox());
			if(i!=0)
				com[i].setVisible(false);
		}
		add(text);
		add(jb = cB("검색", e->search()));
		jb.setIcon(new ImageIcon(readImage("./지급자료/icon_list/search_icon.png", 20, 20)));
		setComponents(new JLabel(""), 0, 0, 1100, 1);
		setComponents(jsc, 0, 0, 1100, 500);
		setComponents(right, 0, 0, 230, 500);
		setComponents(right, addJl("-구매 목록 -", 20, JLabel.CENTER), 0, 0, 230, 50);
		setComponents(right, jsc2, 0, 0, 230, 420);
		setComponents(right, cB("구매하기", e->complete()), 0, 0, 230, 30);
		right.setBorder(black);
		add(cB("메인으로", e->goMain()));
		
		jsc.setBorder(black);
		jsc2.setBorder(black);
		readData(basicQ);
		
		String str[] = "통합검색,분류검색,태그검색".split(",");
		for (int i = 0; i < str.length; i++) {
			com[0].addItem(str[i]);
		}
		com[1].addItem("전체");
		com[2].addItem("전체");
		try {
			ResultSet rs =DBSetting.stmt.executeQuery("select name from main_category");
			while(rs.next())
				com[1].addItem(rs.getString(1));
			rs.close();
			rs = DBSetting.stmt.executeQuery("select name from sub_category");
			while(rs.next())
				com[2].addItem(rs.getString(1));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	void complete() {
	
		if(arr.size()==0) {
			errMsg("1권 이상의 도서를 구매해야 합니다.", "오류");
			return;
		}
		
		int count = 0;
		for(int i = 0 ; i <arr.size();i++) {
			count+=arr.get(i).getCount();
			DBSetting.execute("insert into order_log values(0," + arr.get(i).getBookId() +", " + MainFrame.no +" , " + arr.get(i).getCount() +", now()"
					+ ")");
			DBSetting.execute("update book set stock =stock - " + arr.get(i).getCount()+" where id = " + arr.get(i).getBookId());
		}
		infoMsg("총 " + count +"권의 주문이 완료되었습니다!", "구매 완료");
		main.setPanel(new MainPanel(main));
		
		
		
	}
	
	void readData(String query) {
		System.out.println(query);
		int count = 0;
		left.removeAll();
		try {
			ResultSet rs= DBSetting.stmt.executeQuery(query);
			while(rs.next()) {
				
				count++;
				JPanel p = new JPanel();
				JPanel pl = new JPanel(new FlowLayout(FlowLayout.LEFT,5,2));
				JPanel ps = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
				ps.setBorder(black);
				p.setBorder(black);
				p.setPreferredSize(new Dimension(345, 200));
				pl.setPreferredSize(new Dimension(230, 160));
				ps.setPreferredSize(new Dimension(330, 20));
				p.setBackground(Color.white);
				pl.setOpaque(false);
				ps.setOpaque(false);
				
				var imgStream =  rs.getBinaryStream(4);
				ImageIcon imc = new ImageIcon(imgStream.readAllBytes());
				Image im = imc.getImage();
				im = im.getScaledInstance(100, 160, Image.SCALE_SMOOTH);
				JLabel jl,bigL;
				bigL = new JLabel(new ImageIcon(im.getScaledInstance(200, 360, Image.SCALE_SMOOTH)));
				
				JTextArea area = new JTextArea();
				area.setLineWrap(true);
				area.setText(rs.getString("intro"));
				JScrollPane jsc = new JScrollPane(area);
				JSpinner sp;
				SpinnerNumberModel mo;
				JButton jb;
				int stock = rs.getInt("stock");
				if(stock!=0)
					mo = new SpinnerNumberModel(1, 1, rs.getInt("stock"), 1);
				else
					mo = new SpinnerNumberModel(0,0,0,0);
			
				p.add(jl = new JLabel(new ImageIcon(im)));
				jl.setBorder(black);
				jl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						JOptionPane.showMessageDialog(null, bigL,"크게 보기",JOptionPane.PLAIN_MESSAGE);
					}
				});
				jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
				p.add(pl);
				p.add(ps);
				
				setComponents(pl, addJl(rs.getString(3), 14, JLabel.LEFT), 0, 0, 220, 25);
				setComponents(pl, new JLabel(rs.getString(7)),0, 0, 220, 15);
				setComponents(pl, new JLabel(rs.getString(11)),0, 0, 220, 15);
				setComponents(pl, new JLabel(rs.getString(10)),0, 0, 220, 15);
				setComponents(pl, jsc,0, 0, 220, 50);
				jsc.setBorder(black);
				setComponents(pl, new JLabel(""),0, 0, 40, 20);
				setComponents(pl, sp = new JSpinner(mo),0, 0, 60, 20);
				sp.setBorder(black);
				String name = rs.getString("name");
				pl.add(jb = cB("담기", e->addList(name,toINt(sp.getValue()))));
				p.add(pl);
				p.add(ps);
				
				if(stock==0) {
					jb.setEnabled(false);
					sp.setEnabled(false);
				}
				
				for(String str : rs.getString("hashtag").split(",")) {
					ps.add(jl=  new JLabel(str));
					jl.setForeground(Color.blue);
					jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
					jl.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent e) {
							super.mouseEntered(e);
							JLabel l =(JLabel) e.getSource();
							l.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
						};
						public void mouseClicked(MouseEvent e) {
							super.mouseClicked(e);
							JLabel l =(JLabel) e.getSource();
							com[0].setSelectedIndex(2);
							text.setText(l.getText().replace("#", ""));
							search();
						};
						public void mouseExited(MouseEvent e) {
							super.mouseExited(e);
							JLabel l =(JLabel) e.getSource();
							l.setBorder(null);
						};
					});
				}
				left.add(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		left.setPreferredSize(new Dimension(805, 205 * count /3 + ((count%3==0)?0:1)));
		left.revalidate();
		left.repaint();
	}
	
	void addList(String name,int count) {
		for(int i = 0 ; i <arr.size();i++)
			if(arr.get(i).name.equals(name)) {
				errMsg("이미 추가된 도서입니다!", "확인");
				return;
			}
		arr.add(new BuyList(name, count));
		updateList();
		
	}
	
	void goMain() {
		main.setPanel(new MainPanel(main));
	}
	
	void search() {
		
		if(com[0].getSelectedIndex()==0) {
			readData(basicQ +" where b.name like '%" + text.getText() +"%'");
		}
		else if(com[0].getSelectedIndex()==2) {
			readData(basicQ +" where b.hashtag like '%" + text.getText() +"%'");
		}
		else if(com[0].getSelectedIndex()==1) {
			String mC = (com[1].getSelectedItem().equals("전체"))?"":"sC.main_category_id = " + DBSetting.getOneResult("select id from main_category where name = '" + com[1].getSelectedItem() +"'");
			String sC = (com[2].getSelectedItem().equals("전체"))?"":"b.sub_category_id = " + DBSetting.getOneResult("select id from sub_category where name = '" + com[2].getSelectedItem() +"'");
			String s = (!mC.equals("") && !sC.equals(""))?mC +" and " + sC : mC  +sC;
			s = (!s.equals(""))?"where " + s:"";
			readData(basicQ +" inner join sub_category as sC on sC.id = b.sub_category_id " + s);
		}
		
	
	}

	@Override
	void addListeners() {
		com[0].addActionListener(e->{
			text.setEditable(true);
			com[1].setVisible(false);
			com[2].setVisible(false);
			if(com[0].getSelectedIndex()==1) {
				com[1].setVisible(true);
				com[2].setVisible(true);
				text.setEditable(false);
			}
		});
		com[1].addActionListener(e->{
			com[2].removeAllItems();
			if(com[1].getSelectedIndex()==0)
				com[2].addItem("전체");
				String str= (com[1].getSelectedIndex()==0)?"":"where main_category_id = " + com[1].getSelectedIndex();
				try {
					ResultSet rs= DBSetting.stmt.executeQuery("select name from sub_category " + str);
					while(rs.next())
						com[2].addItem(rs.getString(1));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
		});
	}
	
	void updateList() {
		inner.removeAll();
		
		for(int i = 0 ; i < arr.size();i++) {
			inner.add(arr.get(i));
		}
		inner.revalidate();
		inner.repaint();
		inner.setPreferredSize(new Dimension(210, arr.size() * 40));
	}
	


	
	
	class BuyList extends JPanel{
		String name;
		int count;
		public BuyList(String name, int count) {
			this.name = name;
			this.count = count;
			setPreferredSize(new Dimension(210, 30));
			setBorder(black);
			setLayout(new BorderLayout());
			JPanel l = new JPanel();
			add(l,BorderLayout.WEST);
			l.setPreferredSize(new Dimension(160, 30));
			String str = name;
			if(name.length()>10)
				str= name.substring(0,10) +"...";
			l.add(new JLabel(str));
			l.add(new JLabel(count+"권"));
			add(cB("X",e->{
				arr.remove(this);
				updateList();
			}),BorderLayout.EAST);
		}
		
		int getBookId() {
			return toINt(DBSetting.getOneResult("select id from book where name = '" + name +"'"));
		}
		int getCount() {
			return count;
		}
	}

}

package 대전;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IngiChartForm extends BaseFrame {

	JComboBox com = new JComboBox();
	JPanel imageP = new JPanel();
	boolean chk = true;
	ArrayList value = new ArrayList();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<Color> color = new ArrayList<Color>();
	ArrayList nowAngle = new ArrayList();
	ArrayList angle = new ArrayList();
	ArrayList arc = new ArrayList();
	int index = 0, cindex = 0;
	boolean clicked = false;
	JPanel otherP = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
	int beidx;
	Color beCol;

	String col[] = { "이름", "주소", "홀사용료" };

	DefaultTableModel dtm = addDTM(col);
	JTable jt = addJT(dtm);
	JScrollPane jsc;

	public IngiChartForm() {
		super("인기 차트", 300, 600, null);
		addComponents();
		addListeners();
		setVisible(true);
		readData();

	}

	@Override
	void addComponents() {
		setComponents(com, 0, 0, 280, 30);
		com.addItem("인기 웨딩 종류");
		com.addItem("인기 식사 종류");
		setComponents(imageP, 0, 40, 280, 300);
		setComponents(otherP, 0, 350, 280, 300);
		setComponents(jsc = new JScrollPane(jt), 330, 35, 230, 400);

	}

	void readData() {
		setSize(300, 600);
		imageP.removeAll();
		otherP.removeAll();
		new SChart().start();
		JPanel jp;
		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (clicked == false) {
					for (int i = 0; i < arc.size(); i++) {
						g.setColor(color.get(i));
						g.fillArc(10, 0, 250, 250, toInt(arc.get(i)), toInt(nowAngle.get(i)));
					}
				} else if (clicked) {
					for (int i = 0; i < arc.size(); i++) {
						g.setColor(color.get(i));
						g.fillArc(10, 0, 250, 250, toInt(arc.get(i)), toInt(angle.get(i)));
					}
				}
				g.setColor(Color.white);
				g.fillArc(85, 75, 100, 100, 0, 360);
			}
		};

		jp.setPreferredSize(new Dimension(280, 400));
		imageP.add(jp);
		imageP.revalidate();
		imageP.repaint();

	}

	void readTableData(String index) {
		dtm.setRowCount(0);
		String query1 = "", query2 = "";
		if (com.getSelectedIndex() == 0) {
			query1 = "select weddingtype_index from weddingtype where weddingtype_name= '" + name.get(toInt(index))
					+ "'";
			query2 = "select hall.weddinghall_name,hall.weddinghall_address, format(hall.weddinghall_fee,'#,##0') from weddinghall as hall inner join weddinghall_weddingtype as typ on typ.weddinghall_index = hall.weddinghall_index where typ.weddingtype_index = ";
		} else {
			query1 = "select mealtype_index from mealtype where mealtype_name= '" + name.get(toInt(index)) + "'";
			query2 = "select hall.weddinghall_name,hall.weddinghall_address, format(hall.weddinghall_fee,'#,##0') from weddinghall as hall inner join weddinghall_mealtype as typ on typ.weddinghall_index = hall.weddinghall_index where typ.mealtype_index = ";
		}
		System.out.println(query2);

		setSize(600, 600);
		String typeIndex = DBSetting.getOneResult(query1);
		try {
			ResultSet rs = DBSetting.stmt.executeQuery(query2 + typeIndex);
			while (rs.next()) {
				Object row[] = { rs.getString(1), rs.getString(2), rs.getString(3) + "원" };
				dtm.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	void addListeners() {

//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosed(WindowEvent e) {
//				super.windowClosed(e);
//				new MainForm();
//			}
//		});

		com.addActionListener(e -> {
			readData();
		});
	}

	class SChart extends Thread {

		public void run() {
			otherP.removeAll();
			clicked = false;
			angle.clear();
			nowAngle.clear();
			arc.clear();
			name.clear();
			color.clear();
			value.clear();
			String query = "";
			if (com.getSelectedIndex() == 0)
				query = "select typ2.weddingtype_name,count(r.weddingtype_index) as cnt from reservation as r inner join weddingtype as typ2 on typ2.weddingtype_index = r.weddingtype_index where r.pay = 1 group by typ2.weddingtype_name order by cnt desc";
			else if (com.getSelectedIndex() == 1)
				query = "select typ2.mealtype_name,count(r.mealtype_index) as cnt from reservation as r inner join mealtype as typ2 on typ2.mealtype_index = r.mealtype_index where r.pay = 1 group by typ2.mealtype_name order by cnt desc";
			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					name.add(rs.getString(1));
					value.add(rs.getInt(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Color col[] = { Color.black, Color.blue, Color.cyan, Color.DARK_GRAY, Color.gray, Color.green, Color.pink,
					Color.orange, Color.LIGHT_GRAY, Color.red, Color.yellow };
			int num2[] = new int[name.size()];
			for (int i = 0; i < num2.length; i++) {
				Random r = new Random();
				num2[i] = r.nextInt(10);
				for (int j = 0; j < i; j++)
					if (num2[i] == num2[j])
						i--;
			}
			for (int i = 0; i < num2.length; i++)
				color.add(col[num2[i]]);

			int max = 0;
			int num = 0;
			for (int i = 0; i < value.size(); i++)
				max += toInt(value.get(i));
			for (int i = 0; i < value.size(); i++) {
				arc.add(num);
				num += (int) ((float) toInt(value.get(i)) / max * 365);
				angle.add((int) ((float) toInt(value.get(i)) / max * 365));
				nowAngle.add(1);
			}

			JLabel jl;
			for (int i = 0; i < name.size(); i++) {
				addNull(otherP, 50, 10);
				otherP.add(jl = new JLabel());
				jl.setPreferredSize(new Dimension(10, 10));
				addNull(otherP, 10, 10);
				otherP.add(new JLabel(name.get(i) + " : " + value.get(i) + "개"));
				addNull(otherP, 300, 2);
				jl.setBackground(color.get(i));
				jl.setOpaque(true);
				jl.setName(i + "");
				jl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						JLabel l = (JLabel) e.getSource();
						color.set(toInt(l.getName()), Color.magenta);
						l.setBackground(Color.magenta);
						readTableData(l.getName());
						clicked = true;
						repaint();
						revalidate();
					}
				});

			}
			otherP.revalidate();
			otherP.repaint();
			int ei = 0;
			int et = 0;
			boolean chk = true;
			for (int i = 0; i < angle.size(); i++) {
				while (chk) {
					chk = false;
					for (int z = 0; z < angle.size(); z++) {
						index = z;
						if (!nowAngle.get(z).equals(angle.get(z))) {
							nowAngle.set(z, toInt(nowAngle.get(z)) + 1);
							chk = true;
						}
						revalidate();
						repaint();
					}
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}

	public static void main(String[] args) {
		new IngiChartForm().setVisible(true);
	}
}

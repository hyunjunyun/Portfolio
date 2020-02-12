import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChartPanel extends BasePanel{
	
	
	
	public ChartPanel(MainFrame main) {
		super("설문결과", 1100, 480, new FlowLayout(FlowLayout.CENTER), main);
		addComponents();
		addListeners();
	}

	@Override
	void addComponents() {
		add(addJl("고객 서비스 설문결과", 20, JLabel.CENTER));
		JPanel t=  new JPanel();
		setComponents(t, 0, 0, 1080, 200);
		
		int total[] = new int[5];
		
		try {
			ResultSet rs= DBSetting.stmt.executeQuery("select c.group,count(if(r.rating=1,1,null)),count(if(r.rating=2,1,null)),count(if(r.rating=3,1,null)),count(if(r.rating=4,1,null)),count(if(r.rating=5,1,null)) from survey_results as r inner join survey_category as c on c.id = r.survey_category_id group by c.group order by c.group");
			while(rs.next()) {
				setComponents(new Chart(rs.getString(1), new int[] {rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)}), 0, 0, 355, 200);
				for(int i = 2; i <=6; i++)
					total[i-2] += rs.getInt(i);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setComponents(t, new Chart("전체 결과", total), 0, 0, 1075, 195);
		
		add(cB("메인으로", e->goMain()));
	}
	
	void goMain() {
		main.setPanel(new MainPanel(main));
	}

	@Override
	void addListeners() {
		// TODO Auto-generated method stub
		
	}
	
	class Chart extends JPanel{
		String name;
		int data[];
		public Chart(String name, int data[]) {
			this.name= name + " 항목";
			this.data = data;
			setBorder(black);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int w = getWidth()/5;
			int h = getHeight();
			int max = data[0] + data[1] + data[2] + data[3] + data[4];
			int value = (h-30)/max;
			int len,cWidth =25;
			Color col[] = {new Color(255, 129, 129),new Color(255, 195, 129),new Color(201, 201, 201),new Color(143, 255, 194),new Color(0, 176, 80)};
			String str[] = "매우 불만족, 불만족, 보통, 만족, 매우 만족".split(",");
			
			for(int i = 0 ; i < str.length;i++) {
				g.setColor(Color.black);
				len = str[i].length() * 8;
				String label = String.format("%.2f%% (%d건)", 100 * ((float)data[i]/max),data[i]);
				g.drawString(str[i], w * i + (w-len)/2, 180);
				g.drawString(label, w * i + (w - cWidth)/2 - 25, 150 - value * data[i]);
				g.setColor(col[i]);
				g.fillRect(w*i + (w- cWidth)/2	, 160 - value * data[i], cWidth, value * data[i]);
				
			}
		
		
		}
		
	}

}

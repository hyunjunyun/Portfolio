package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ReportPage.Chart;

public class ReportPage extends PageBase {

	private static final Color[] color = { new Color(255, 129, 129), new Color(255, 195, 129), new Color(201, 201, 201), new Color(143, 255, 194), new Color(0, 176, 80) };
	
	class Chart extends JPanel {
		int[] data;
		
		class ChartPainting extends JPanel {
			
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				int hGap = getWidth() / data.length;
				int margin = 30;
				int height = getHeight() - margin - 10;
				int total = Arrays.stream(data).sum();
				int barWidth = 30;
				
				for (int i = 0; i < data.length; i++) {
					float p = (float)data[i] / total;					
					
					if (total == 0) {
						p = 0;
					}
					
					int barStart = (int)(height * (1f - p));
					int barHeight = (int)(height * p);
					
					g2d.setColor(Color.BLACK);
					var textBounds = g2d.getFontMetrics().getStringBounds(surveyColumns[i], g);
					String label = String.format("%.2f%% (%d건)", p * 100, data[i]);
					var labelBounds = g2d.getFontMetrics().getStringBounds(label, g);
					
					g2d.drawString(surveyColumns[i], hGap * i + (hGap - (int)textBounds.getWidth()) / 2, height + 25);
					g2d.drawString(label, hGap * i + (hGap - (int)labelBounds.getWidth()) / 2, barStart - 5);
										
					if (total > 0) {
						g2d.setColor(color[i]);
						g2d.fillRect(hGap * i + (hGap - barWidth) / 2, barStart, barWidth, barHeight);
					}
				}
			}
			
		}
		
		public Chart(String title, int[] data) {
			setLayout(new BorderLayout());
			setBorder(blackBorder);
			
			this.data = data;
			
			add(createLabel(new JLabel(title, 0), 20, Color.black), "North");
			add(new ChartPainting());
		}
	}
	
	public ReportPage() {
		super(1200, 500, "설문결과");
		
		setLayout(new BorderLayout());
		
		var sp = new JPanel();
		var cp = new JPanel(new GridLayout(2, 1, 0, 10));
		var c_sp = new JPanel(new GridLayout(1, 3, 10, 0));
		
		cp.setOpaque(false);
		sp.setOpaque(false);
		c_sp.setOpaque(false);
		sp.add(createButton("메인으로", e -> movePage(new MainPage())));
		
		int[] total = new int[5];
		
		try (var rs = stmt.executeQuery("SELECT c.group, \r\n" + 
				"COUNT(IF(r.rating = 1, 1, null)) AS r1, \r\n" + 
				"COUNT(IF(r.rating = 2, 1, null)) AS r2, \r\n" + 
				"COUNT(IF(r.rating = 3, 1, null)) AS r3, \r\n" + 
				"COUNT(IF(r.rating = 4, 1, null)) AS r4, \r\n" + 
				"COUNT(IF(r.rating = 5, 1, null)) AS r5\r\n" + 
				"FROM bookdb.survey_category c\r\n" + 
				"LEFT JOIN survey_results r ON r.survey_category_id = c.id\r\n" + 
				"GROUP BY c.group " + 
				"ORDER BY c.group;")) {
			
			while (rs.next()) {
				c_sp.add(new Chart(rs.getString(1) + " 항목", new int[] { rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6) }));
				
				for (int i = 0; i < total.length; i++) {
					total[i] += rs.getInt(i + 2);
				}
				
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		cp.add(new Chart("전체 결과 항목", total));
		cp.add(c_sp);
		
		add(cp);
		add(createLabel(new JLabel("고객 서비스 설문결과", 0), 20, Color.BLACK), "North");
		add(sp, "South");
	}

	public static void main(String[] args) {
		movePage(new ReportPage());
		mainFrame.setVisible(true);
	}

}

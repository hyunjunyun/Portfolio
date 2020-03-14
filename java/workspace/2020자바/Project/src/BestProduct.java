import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BestProduct extends BaseFrame {
	Color col[] = { Color.blue, Color.red, Color.cyan, Color.orange, Color.green };
	String str[] = { "전체", "채소", "과일", "정육", "해산물", "가공식품", "유제품", "생활용품", "주방용품" };
	String sql;

	public BestProduct() {
		super(400, 600, "인기 상품", new BorderLayout(), 2);
		setDesign();
		setAction();
		setVisible(true);
	}

	private void setAction() {
		jcom[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setChart();
			}
		});

	}

	void setDesign() {
		add(jp[2] = new JPanel(), BorderLayout.NORTH);
		add(jp[1] = new JPanel(new GridLayout(0, 5)), BorderLayout.SOUTH);
		jp[1].setPreferredSize(new Dimension(0, 50));
		jp[2].add(jl[0] = new JLabel("Best 상품", JLabel.CENTER));
		jp[2].add(jcom[0] = new JComboBox<String>(str));
		jl[0].setFont(new Font("", Font.BOLD, 20));
		for (int i = 0; i < 5; i++) {
			jp[1].add(jl[i] = new JLabel("", JLabel.CENTER));
		}
		setChart();
	}

	private void setChart() {
		jp[0] = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				try (var rs = stmt.executeQuery(
						"select pl.p_name ,sum(pu_count) as c from purchaselist as pl inner join product as pd on pl.p_no = pd.p_no where p_category like '%%' group by pl.p_no order by c desc limit 5;")) {
					int i = 0;
					int max = 0;
					while (rs.next()) {
						if (i == 0) {
							max = 450 / rs.getInt(2);

						}
						jl[i].setText(rs.getString(1));
						g2d.setStroke(new BasicStroke(2));
						g2d.setColor(Color.black);
						g2d.drawString(rs.getString(2) + "개", 26 + (i * 76), 20);
						g2d.drawRect(25 + (i * 76), 490 - (max * rs.getInt(2)), 30, max * rs.getInt(2));
						g2d.setColor(col[i]);
						g2d.fillRect(26 + (i * 76), 491 - (max * rs.getInt(2)), 28, max * rs.getInt(2));
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};

		add(jp[0], BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	void openJFrame() {
		new ProductForm();
	}
}

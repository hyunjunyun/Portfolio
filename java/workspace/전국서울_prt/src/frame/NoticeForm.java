package frame;

import static panel.BasePanel.setLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import data.UserData;
import db.CM;
import panel.BasePanel;

public class NoticeForm extends BaseFrame {

	JPanel jp = new JPanel();
	UserData ud;

	public NoticeForm(UserData ud) {
		super(400, 310, "", 2);
		this.ud = ud;
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		var jsc = new JScrollPane(jp);
		jsc.setPreferredSize(new Dimension(400, 300));
		jp.setPreferredSize(new Dimension(400, 150));
		setUndecorated(true);
		add(jsc);
		if (refresh() == 0) {
			setBackground(Color.white);
			add(setLabel(new JLabel("알림 없음"), new Font("Gothic", 1, 22)));
		}
	}

	public int refresh() {
		int cnt = 0;

		try (var rs = CM
				.setData("select * from notice as n inner join hotel as h on n.h_index=h.h_index where u_index='"
						+ ud.getNo() + "';")) {
			while (rs.next()) {
				jp.add(new InnerPanel(rs.getString(7), rs.getString(4), rs.getString(3), rs.getInt(1)));
				cnt++;
			}
			System.out.println(cnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	class InnerPanel extends BasePanel {
		int index;

		public InnerPanel(String name, String time, String text, int index) {
			super(400, 130);
			this.index = index;
			var np = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 380, 20);
			setLayout(new BorderLayout());

			np.add(setComp(new JLabel("호텔 : " + name), 200, 20));
			np.add(new JLabel(time));
			np.add(setBtnM("x", this::deleteAction));

			add(setComp(new JTextArea(text), 380, 80));
			add(np, BorderLayout.NORTH);
			setBorder(new LineBorder(Color.black));
		}

		public void deleteAction(ActionEvent e) {
			jp.removeAll();
			try (var pst = CM.con.prepareStatement("delete from notice where n_index = ?")) {
				pst.setObject(1, index);
				pst.execute();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			refresh();
		}
	}
}

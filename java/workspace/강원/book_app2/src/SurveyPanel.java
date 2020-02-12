import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SurveyPanel extends BasePanel {

	JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
	ArrayList<SurveyItem> arr = new ArrayList<SurveyPanel.SurveyItem>();
	JButton jb;

	public SurveyPanel(MainFrame main) {
		super("설문조사", 700, 700, new FlowLayout(FlowLayout.CENTER), main);
		addComponents();
		addListeners();
		readData();
	}

	@Override
	void addComponents() {
		add(addJl("고객 만족도 조사", 20, JLabel.CENTER));
		setComponents(jp, 0, 0, 690, 600);
		add(jb = cB("제출", e -> complete()));
		add(cB("메인으로", e -> main()));

	}

	void readData() {
		String str = "";
		int count = 0;

		try {
			ResultSet rs = DBSetting.stmt.executeQuery("select * from survey_category");
			while (rs.next()) {
				count++;
				if (!str.equals(rs.getString(2)))
					setComponents(jp, addJl(rs.getString(2), 20, JLabel.LEFT), 0, 0, 680, 30);
				arr.add(new SurveyItem(rs.getInt(1), count + "." + rs.getString(3),
						!DBSetting.getOneResult("select rating from survey_results where member_id = " + MainFrame.no)
								.equals("")));
				setComponents(jp, arr.get(count - 1), 0, 0, 680, 30);
				str = rs.getString(2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (arr.get(1).chk==true) {
			infoMsg("이미 설문조사를 응했습니다.", "확인");
		jb.setEnabled(false);
		}

	}

	void complete() {
		for(int i = 0 ; i  <arr.size();i++) {
			if(arr.get(i).getValue()==0) {
				errMsg("체크하지 않은 항목이 있습니다!", "확인");
				return;
			}
		}
		infoMsg("설문에 응해주셔서 감사합니다.", "안내");
	
		for(int i = 0 ; i <arr.size();i++)
			DBSetting.execute("insert into survey_results values(0, " + MainFrame.no +", " + arr.get(i).id +" , " + arr.get(i).getValue() 
					+ ")");
		main.setPanel(new MainPanel(main));
		
	}

	void main() {
		main.setPanel(new MainPanel(main));
	}

	@Override
	void addListeners() {

	}

	class SurveyItem extends JPanel {
		int id;
		String name;
		boolean chk;
		JRadioButton jrb[] = new JRadioButton[5];

		SurveyItem(int id, String name, boolean chk) {
			setComponents(this, new JLabel(name), 0, 0, 300, 30);
			this.id = id;
			this.name = name;
			this.chk = chk;
			ButtonGroup bg = new ButtonGroup();
			String str[] = "매우 불만족,불만족,보통,만족,매우 만족".split(",");
			for (int i = 0; i < 5; i++) {
				add(jrb[i] = new JRadioButton(str[i]));
				bg.add(jrb[i]);
				jrb[i].setName((i + 1) + "");
			}
			if (chk) {
				for (int i = 0; i < 5; i++) {
					jrb[i].setEnabled(false);
				}
				int num = toINt(DBSetting.getOneResult("select rating from survey_results where member_id = "
						+ MainFrame.no + " and survey_category_id = " + id));
				jrb[num - 1].setSelected(true);
			}

		}

		int getValue() {
			int num = 0;
			for (int i = 0; i < 5; i++) {
				if (jrb[i].isSelected())
					num = toINt(jrb[i].getName());
			}
			return num;
		}

	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SurveyPage extends PageBase {

	JPanel surveyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
	JButton submit = createButton("제출", e -> submit());
	
	class SurveyItem extends JPanel {
		int rating = -1;
		int id;
		ButtonGroup grp = new ButtonGroup();
		JRadioButton[] radio = new JRadioButton[surveyColumns.length];
		
		public SurveyItem(int id, String text, int rating) {
			this.id = id;
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			setPreferredSize(new Dimension(730, 30));
			
			add(createComp(new JLabel(id + ". " + text), 300, 20));
			
			for (int i = 0; i < surveyColumns.length; i++) {
				radio[i] = new JRadioButton(surveyColumns[i]);
				grp.add(radio[i]);
				add(radio[i]);
				
				if (rating > 0) {
					radio[i].setEnabled(false);
				}
				
				if ((rating - 1) == i) {
					radio[i].setSelected(true);
				}
			}
		}
		
		public int getRating() {
			for (int i = 0; i < surveyColumns.length; i++) {
				if (radio[i].isSelected()) {
					return i + 1;
				}
			}
			
			return 0;
		}
		
	}
	
	public SurveyPage() {
		super(750, 700, "설문조사");
		
		setLayout(new BorderLayout());
		
		var sp = new JPanel();

		sp.setOpaque(false);
		sp.add(submit);
		sp.add(createButton("메인으로", e -> movePage(new MainPage())));
		
		add(createLabel(new JLabel("고객 만족도 조사", 0), 20, Color.BLACK), "North");
		add(sp, "South");
		add(surveyPanel);
		
		init();
	}
	
	private void submit() {
		ArrayList<SurveyItem> surveyList = new ArrayList<SurveyPage.SurveyItem>();
		
		for (var comp : surveyPanel.getComponents()) {
			if (comp instanceof SurveyItem) {
				SurveyItem survey = (SurveyItem)comp;
				
				surveyList.add(survey);
				
				if (survey.getRating() == 0) {
					eMsg("체크하지 않은 항목이 있습니다!", "확인");
					return;
				}
			}
		}
		
		try (var pst = con.prepareStatement("INSERT INTO survey_results VALUES(0, ?, ?, ?)")) {
			
			pst.setInt(1, memberNo);
			
			for (var survey : surveyList) {
				
				for (int i = 0; i < surveyColumns.length; i++) {
					survey.radio[i].setEnabled(false);
				}
				
				pst.setInt(2, survey.id);
				pst.setInt(3, survey.getRating());
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		iMsg("설문에 응해주셔서 감사합니다.", "안내");
		
		submit.setEnabled(false);
		movePage(new MainPage());
		
	}
	
	private void init() {
		
		try (var rs = stmt.executeQuery("SELECT *, IFNULL((SELECT rating FROM survey_results sr WHERE sr.survey_category_id = sc.id AND sr.member_id = " + memberNo + "), 0) FROM bookdb.survey_category sc")) {
			String tmp = "";
			boolean flag = false;
			
			while (rs.next()) {
				
				if (tmp.equals(rs.getString(2)) == false) {
					tmp = rs.getString(2);
					surveyPanel.add(createLabel(new JLabel(tmp), 20, Color.BLACK));
				}
				
				surveyPanel.add(new SurveyItem(rs.getInt(1), rs.getString(3), rs.getInt(4)));
				
				if (rs.getInt(4) != 0) {
					flag = true;
				}
			}
			
			if (flag) {
				iMsg("이미 설문 조사를 응했습니다.", "확인");
				submit.setEnabled(false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		setSession(6);
		movePage(new SurveyPage());
		mainFrame.setVisible(true);
	}

}

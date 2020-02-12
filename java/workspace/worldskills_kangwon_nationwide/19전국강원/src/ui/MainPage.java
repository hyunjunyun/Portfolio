package ui;

import static ui.PageBase.getIcon;

public class MainPage extends PageBase {

	public MainPage() {
		super(500, 240, "����");
		
		if (authority.equals("STAFF")) {
			add(createComp(createButton("���� ���", getIcon("./�����ڷ�/icon_list/reporting_icon.png", 150, 150), e -> movePage(new ReportPage())), 470, 180));
			add(createComp(createButton("�α׾ƿ�", e -> {
				mainFrame.clearHistory();
				movePage(new LoginPage());
			}), 470, 30));

		} else {
			add(createComp(createButton("���� �˻�", getIcon("./�����ڷ�/icon_list/searching_book.png", 150, 150), e -> movePage(new SearchPage())), 230, 180));
			add(createComp(createButton("���� ����", getIcon("./�����ڷ�/icon_list/survey_icon.png", 150, 150), e -> movePage(new SurveyPage())), 230, 180));
			add(createComp(createButton("���� ���ų���", e -> movePage(new OrderLogPage())), 230, 30));
			add(createComp(createButton("�α׾ƿ�", e -> {
				mainFrame.clearHistory();
				movePage(new LoginPage());
			}), 230, 30));
		}
	}

	public static void main(String[] args) {
		setSession(10);
		movePage(new MainPage());
		mainFrame.setVisible(true);
	}

}

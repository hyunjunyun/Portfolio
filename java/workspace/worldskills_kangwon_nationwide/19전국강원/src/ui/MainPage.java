package ui;

import static ui.PageBase.getIcon;

public class MainPage extends PageBase {

	public MainPage() {
		super(500, 240, "메인");
		
		if (authority.equals("STAFF")) {
			add(createComp(createButton("설문 결과", getIcon("./지급자료/icon_list/reporting_icon.png", 150, 150), e -> movePage(new ReportPage())), 470, 180));
			add(createComp(createButton("로그아웃", e -> {
				mainFrame.clearHistory();
				movePage(new LoginPage());
			}), 470, 30));

		} else {
			add(createComp(createButton("도서 검색", getIcon("./지급자료/icon_list/searching_book.png", 150, 150), e -> movePage(new SearchPage())), 230, 180));
			add(createComp(createButton("설문 조사", getIcon("./지급자료/icon_list/survey_icon.png", 150, 150), e -> movePage(new SurveyPage())), 230, 180));
			add(createComp(createButton("나의 구매내역", e -> movePage(new OrderLogPage())), 230, 30));
			add(createComp(createButton("로그아웃", e -> {
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

import java.awt.FlowLayout;

public class MainPanel extends BasePanel{
	
	public MainPanel(MainFrame main) {
		super("메인", 600, 270, new FlowLayout(FlowLayout.CENTER,10,10), main);
		addComponents();
		addListeners();
				
	
	}

	@Override
	void addComponents() {
		
		if(MainFrame.aur.equals("USER")) {
			setComponents(cB("도서 검색", readIcon("searching_book", 150, 150), e->search()), 0, 0, 280, 200);
			setComponents(cB("설문 조사", readIcon("survey_icon", 150, 150), e->survey()), 0, 0, 280, 200);
			setComponents(cB("나의 구매내역", e->buyList()), 0, 0, 280,35);
			setComponents(cB("로그아웃", e->logout()), 0, 0, 280,35);
		}
		else {
			setComponents(cB("도서 검색", readIcon("reporting_icon", 150, 150), e->chart()), 0, 0, 570, 200);
			setComponents(cB("로그아웃", e->logout()), 0, 0, 570,35);
		}
		
	}
	
	void chart() {
		main.setPanel(new ChartPanel(main));
	}
	
	void search() {
		main.setPanel(new SearchPanel(main));
	
	}
	
	void survey() {
		main.setPanel(new SurveyPanel(main));
	}
	
	void buyList() {
		main.setPanel(new BuyListPanel(main));
	}
	
	void logout() {
		MainFrame.aur = "";
		MainFrame.id ="";
		MainFrame.no ="";
		main.clear();
		main.setPanel(new LoginPanel(main));
	}

	@Override
	void addListeners() {
		// TODO Auto-generated method stub
		
	}

}

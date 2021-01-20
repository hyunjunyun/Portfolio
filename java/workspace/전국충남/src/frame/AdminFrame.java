package frame;

import java.awt.GridLayout;

public class AdminFrame extends BaseFrame{
	public AdminFrame() {
		super(250, 250, "관리자", 2);
		setLayout(new GridLayout(0,1));
		add(setBtn("현황검색", e -> openFrame(new RSearchFrame())));
		add(setBtn("예약인원  Top5", e -> openFrame(new Top5Frame())));
		add(setBtn("영화목록 관리", e -> openFrame(new MovieListFrame())));
		add(setBtn("로그아웃", e -> dispose()));
	}
	
	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}

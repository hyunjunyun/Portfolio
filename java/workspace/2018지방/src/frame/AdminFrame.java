package frame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame {

	public AdminFrame() {
		super(550, 400, "관리");
		var jp = new JPanel();
		var np = new JPanel();
		var lbImg = setComp(new JLabel(setIcon("./지급자료/main.jpg", 533, 310)), 0, 0);

		np.add(setBtn("메뉴등록", e -> openFrame(new MenuAddFrame())));
		np.add(setBtn("메뉴등록", e -> openFrame(new MenuManageForm())));
		np.add(setBtn("메뉴등록", e -> openFrame(new PayListForm())));
		np.add(setBtn("메뉴등록", e -> openFrame(new MenuOrderlist())));
		np.add(setBtn("종료", e -> dispose()));

		add(np, BorderLayout.NORTH);
		add(lbImg, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}

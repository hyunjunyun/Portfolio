package frame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminFrame extends BaseFrame {

	public AdminFrame() {
		super(550, 400, "����");
		var jp = new JPanel();
		var np = new JPanel();
		var lbImg = setComp(new JLabel(setIcon("./�����ڷ�/main.jpg", 533, 310)), 0, 0);

		np.add(setBtn("�޴����", e -> openFrame(new MenuAddFrame())));
		np.add(setBtn("�޴����", e -> openFrame(new MenuManageForm())));
		np.add(setBtn("�޴����", e -> openFrame(new PayListForm())));
		np.add(setBtn("�޴����", e -> openFrame(new MenuOrderlist())));
		np.add(setBtn("����", e -> dispose()));

		add(np, BorderLayout.NORTH);
		add(lbImg, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}
}

package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuAddFrame extends BaseFrame {

	JComboBox<String> nbf = new JComboBox<String>("한식,중식,일식,양식".split(","));
	JTextField nf = new JTextField();
	JComboBox<Integer> pf = new JComboBox<Integer>();
	JComboBox<Integer> rpf = new JComboBox<Integer>();

	public MenuAddFrame() {
		super(350, 250, "신규 메뉴 등록");
		add(setEmpty(setPanel(new GridLayout(5, 2), new JLabel("종류", 2), nbf, new JLabel("메뉴명", 2), nf,
				new JLabel("가격:", 2), pf, new JLabel("조리가능수량", 2), rpf, setBtn("등록", this::submit),
				setBtn("닫기", t -> openFrame(new AdminFrame()))), 5, 0, 5, 0));

		for (int i = 1000; i <= 12000; i += 500) {
			pf.addItem(i);
		}

		for (int i = 0; i <= 50; i++) {
			rpf.addItem(i);
		}
	}

	public void submit(ActionEvent e) {
		String name = nf.getText();
		int type = 0;
		int price = Integer.parseInt(pf.getSelectedItem().toString());
		int count = Integer.parseInt(rpf.getSelectedItem().toString());

		if (name.isEmpty()) {
			eMsg("메뉴명을 입력해주세요.");
			return;
		}
		
		System.out.println(name);
		switch (nbf.getSelectedItem().toString()) {
		case "한식":
			type = 1;
			break;
		case "중식":
			type = 2;
			break;
		case "일식":
			type = 3;
			break;
		case "양식":
			type = 4;
			break;
		default:
			throw new IllegalArgumentException("Error");
		}
		
		try (var pst = con.prepareStatement("insert into `meal` values (0,?,?,?,?,0)")) {
			pst.setObject(1, type);
			pst.setObject(2, name);
			pst.setObject(3, price);
			pst.setObject(4, count);

			pst.execute();

			iMsg("메뉴가 등록되었습니다..");
			openFrame(new AdminFrame());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void title(int type) {
		
	}
	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}
}

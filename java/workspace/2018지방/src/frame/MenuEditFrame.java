package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuEditFrame extends BaseFrame {

	JComboBox<String> nbf = new JComboBox<String>("한식,중식,일식,양식".split(","));
	JTextField nf = new JTextField();
	JComboBox<Integer> pf = new JComboBox<Integer>();
	JComboBox<Integer> rpf = new JComboBox<Integer>();

	int curMealNo;

	public MenuEditFrame(String name) {
		super(350, 250, "메뉴 수정");

		setData(name);

		add(setEmpty(setPanel(new GridLayout(5, 2), new JLabel("종류", 2), nbf, new JLabel("메뉴명", 2), nf,
				new JLabel("가격:", 2), pf, new JLabel("조리가능수량", 2), rpf, setBtn("등록", this::submit),
				setBtn("닫기", t -> openFrame(new MenuManageForm()))), 5, 0, 5, 0));

	}

	public void setData(String name) {
		for (int i = 1000; i <= 12000; i += 500) {
			pf.addItem(i);
		}

		for (int i = 0; i <= 50; i++) {
			rpf.addItem(i);
		}

		try (var rs = stmt.executeQuery("select * from meal where mealName = '" + name + "'")) {
			rs.next();
			nbf.setSelectedIndex(rs.getInt(2)-1);
			nf.setText(name);
			pf.setSelectedItem(rs.getInt(4));
			rpf.setSelectedItem(rs.getInt(5));
			curMealNo = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void submit(ActionEvent e) {
		try (var pst = con.prepareStatement(
				"update meal set cuisineNo = ?, mealName = ?, price = ?, maxCount = ? where mealNo = ?")) {
			pst.setObject(1, nbf.getSelectedIndex()+1);
			pst.setObject(2, nf.getText());
			pst.setObject(3, pf.getSelectedItem());
			pst.setObject(4, rpf.getSelectedItem());
			pst.setObject(5, curMealNo);
			pst.execute();
			openFrame(new MenuManageForm());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}

package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Payform extends BaseFrame {

	JLabel lbt = setComp(setLabel(new JLabel("", 0), new Font("굴림", 1, 24)), 0, 30);
	JPanel wp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)), 460, 300);
	DefaultTableModel model = new DefaultTableModel("상품번호,품명,수량,금액".split(","), 0);
	JTable table = new JTable(model);
	JLabel amount = setComp(setLabel(new JLabel("", 4), new Font("굴림", 1, 16)), 100, 0, 180, 30);
	JTextField mtf = setComp(new JTextField(), 240, 23);
	JTextField ctf = setComp(new JTextField(), 60, 23);
	HashMap<String, JButton> hash = new HashMap<>();
	int curMenuNo;
	int curMenuPrice;
	int curMaxCount;
	int curType;

	public Payform(int type) {
		super(900, 400, "결제");
		JPanel jp = new JPanel(new BorderLayout());
		var ep = setComp(new JPanel(null), 405, 500);
		var ep_s = setComp(new JPanel(new FlowLayout()), 0, 240, 400, 100);
		var scp = setComp(new JScrollPane(table), 5, 35, 400, 200);

		ep.add(setComp(setLabel(new JLabel("총결제금액:", 2), new Font("굴림", 1, 16)), 5, 0, 100, 30));
		ep.add(amount);
		ep.add(scp);
		ep_s.add(new JLabel("선택품명:"));
		ep_s.add(mtf);
		ep_s.add(new JLabel("수량"));
		ep_s.add(ctf);
		ep_s.add(setComp(setBtn("입력", this::insert), 100, 35));
		ep_s.add(setComp(setBtn("결제", this::pay), 100, 35));
		ep_s.add(setComp(setBtn("닫기", e -> openFrame(new AddCouponForm())), 100, 35));

		mtf.setEditable(false);
		curType = type;

//		table.isCellEditable(row, column);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("a");
				}
			}
		});

		jp.add(lbt, BorderLayout.NORTH);
		jp.add(wp, BorderLayout.WEST);
		wp.add(setComp(new JLabel("  "), 480, 30));
		jp.add(ep, BorderLayout.EAST);
		ep.add(ep_s, BorderLayout.SOUTH);

		jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(jp);
		title(type);
		menuBtn(type);
	}


	public void insert(ActionEvent e) {
		String name = mtf.getText();
		if (name.isEmpty()) {
			eMsg("메뉴를 선택해 주세요.");
			return;
		}
		try {
			Integer cnt = Integer.parseInt(ctf.getText());
			if (cnt > curMaxCount) {
				eMsg("조리가능수량이 부족합니다.");
				return;
			}
			model.addRow(new Object[] { curMenuNo, name, curMenuPrice, curMenuPrice * cnt });
		} catch (NumberFormatException e2) {
			eMsg("수량을 입력해주세요.");
			return;
		}
		hash.get(name).setEnabled(false);
		;
		mtf.setText("");
		ctf.setText("");
	}

	public void pay(ActionEvent e) {

	}

	public void submit(String name, int no, int price, int MaxCount) {
		mtf.setText(name);
		curMenuNo = no;
		curMenuPrice = price;
		curMaxCount = MaxCount;
	}

	public void title(int type) {
		switch (type) {
		case 1:
			lbt.setText("한식");
			break;
		case 2:
			lbt.setText("중식");
			break;
		case 3:
			lbt.setText("일식");
			break;
		case 4:
			lbt.setText("양식");
			break;
		default:
			throw new IllegalArgumentException("Error: " + type);
		}
	}

	private void menuBtn(int type) {
		wp.removeAll();

		try (var rs = stmt
				.executeQuery("select * from meal where cuisineNo = " + type + " and todayMeal = 1 and maxCount > 0")) {
			while (rs.next()) {
				String name = rs.getString(3);
				int no = rs.getInt(1);
				int price = rs.getInt(4);
				int MaxCount = rs.getInt(5);
				JButton jb = new JButton(rs.getString(3));
				wp.add(setComp(setBtnM(jb, new Font("굴림", 0, 8), e -> submit(name, no, price, MaxCount)), 90, 50));
				hash.put(jb.getText(), jb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
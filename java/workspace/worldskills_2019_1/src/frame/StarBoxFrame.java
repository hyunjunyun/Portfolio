package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class StarBoxFrame extends FrameBase {

	private JLabel lbC = createComp(new JLabel("", 2), 0, 25);
	JPanel menuPanel = createComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 0, 0);
	JPanel dP = createComp(new JPanel(new BorderLayout()), 610, 130, 310, 210);
	JLabel lbDetailImg = createComp(new JLabel(), 0, 0, 100, 100);
	JTextField tfMenuName = createComp(new JTextField(), 175, 0, 130, 25);
	JTextField tfPrice = createComp(new JTextField(), 175, 35, 130, 25);
	JComboBox<Integer> cbCount = createComp(new JComboBox<>(), 175, 70, 130, 25);
	JComboBox<String> cbSize = createComp(new JComboBox<>(), 175, 105, 130, 25);
	JTextField tfAmount = createComp(new JTextField(), 175, 140, 130, 25);

	public int curMenuNo;
	public String curMenuGroup;

	public StarBoxFrame() {
		super(700, 600, "STARBOX");
		var northPanel = new JPanel(new BorderLayout());
		var centerPanel = new JPanel(null);
		var westPanel = createComp(new JPanel(), 55, 0);
		var north_southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		north_southPanel.add(createButton("���ų���", e -> openFrame(new OrderlistFrame())));// ���ų��� ��ư ���� ������ �������� ����
		north_southPanel.add(createButton("��ٱ���", e -> openFrame(new ShoppingFrame())));// ��ٱ��� ��ư ���� ���� �� �������� ����
		north_southPanel.add(createButton("�α��ǰ Top5", e -> openFrame(new Top5Frame())));// �α��ǰ ��ư ���� ���� �� �������� ������
		north_southPanel.add(createButton("Logout", e -> openFrame(new LoginFrame())));// �α׾ƿ� ��ư ���� ���� �� �α׾ƿ� �ȴ�
		northPanel.add(lbC);
		northPanel.add(north_southPanel, BorderLayout.SOUTH);

		updateLabel();


		var scp = createComp(new JScrollPane(menuPanel), 0, 0, 580, 480);

		lbDetailImg.setBorder(new LineBorder(Color.black));

		var d_C = new JPanel(null);
		var d_B = new JPanel();

		String[] labelTextList = { "�ֹ��޴� :", "���� :", "���� :", "������ :", "�ѱݾ� :" };

		for (int i = 0; i < labelTextList.length; i++) {
			d_C.add(createComp(new JLabel(labelTextList[i], JLabel.RIGHT), 105, i * 35, 60, 25));
		}

		tfMenuName.setEditable(false);
		tfPrice.setEditable(false);
		tfAmount.setEditable(false);

		dP.setVisible(false);
		d_C.add(lbDetailImg);
		d_C.add(tfMenuName);
		d_C.add(tfPrice);
		d_C.add(cbCount);
		d_C.add(cbSize);
		d_C.add(tfAmount);

		for (int i = 1; i <= 10; i++) {
			cbCount.addItem(i);
		}

		d_B.add(createButton("��ٱ��Ͽ� ���", this::clickCart));
		d_B.add(createButton("�����ϱ�", this::clickBuy));

		dP.add(d_C);
		dP.add(d_B, BorderLayout.SOUTH);
		dP.setVisible(false);

		cbCount.addActionListener(e -> tfAmount.setText("" + getAmount()));
		cbSize.addActionListener(e -> tfAmount.setText("" + getAmount()));

		centerPanel.add(scp);
		centerPanel.add(dP);

		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);

		updateLabel();
		clickG("����");

	}

	private void updateLabel() {
		lbC.setText(String.format("ȸ���� : %s / ȸ����� : %s / �� ���� ����Ʈ : %d", userName, userGrade, userPoint));
	}

	private int getAmount() {
		int price = Integer.parseInt(tfPrice.getText());
		int count = (Integer) cbCount.getSelectedItem();

		if (cbSize.getSelectedIndex() == 1) {
			price += 1000;
		}

		float discount = 0;

		if (userGrade.equals("Bronze")) {
			discount = 0.03f;
		} else if (userGrade.equals("silver")) {
			discount = 0.05f;
		} else if (userGrade.equals("Gold")) {
			discount = 0.1f;
		}

		return (int) ((price * count) * (1f - discount));
	}

	private void clickCart(ActionEvent e) {
		try (var pst = con.prepareStatement("insert into shopping values (0,?,?,?,?,?)")) {
			pst.setObject(1, curMenuNo);
			pst.setObject(2, tfPrice.getText());
			pst.setObject(3, cbCount.getSelectedItem());
			pst.setObject(4, cbSize.getSelectedItem());
			pst.setObject(5, getAmount());
			pst.execute();
			iMsg("��ٱ��Ͽ� ��ҽ��ϴ�");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void clickBuy(ActionEvent e) {
		int amount = getAmount();
		int answer = JOptionPane.NO_OPTION;
		String curGrade = userGrade;
		String newGrade = userGrade;
		if (userPoint > getAmount()) {
			answer = JOptionPane.showConfirmDialog(null,
					"ȸ������ �� ����Ʈ : " + userPoint + "\n" + "����Ʈ�� �����Ͻðڽ��ϱ�?\n(�ƴϿ��� Ŭ�� �� ���ݰ����� �˴ϴ�)", "�޼���",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		if (answer == JOptionPane.NO_OPTION) {
			try (var pst = con.prepareStatement("insert into orderlist values(0,CURDATE(),?,?,?,?,?,?,?)")) {
				pst.setObject(1, userNo);
				pst.setObject(2, curMenuNo);
				pst.setObject(3, curMenuGroup);
				pst.setObject(4, cbSize.getSelectedItem());
				pst.setObject(5, tfPrice.getText());
				pst.setObject(6, cbCount.getSelectedItem());
				pst.setObject(7, amount);
				pst.execute();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try (var pst = con.prepareStatement("select sum(o_amount) from orderlist where u_no = ? ")) {
				pst.setObject(1, userNo);

				var rs = pst.executeQuery();
				rs.next();
				int result = rs.getInt(1);

				if (result >= 800000) {
					newGrade = "Gold";
				} else if (result >= 500000) {
					newGrade = "Silver";
				} else if (result >= 300000) {
					newGrade = "Bronze";
				} else {
					newGrade = "�Ϲ�";
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			userPoint += amount * 0.05f;

			iMsg("���ŵǾ����ϴ�.");

			if (curGrade.equals(newGrade) == false) {
				// ��޾�
				iMsg("�����մϴ�!\nȸ���� ����� " + newGrade + "�� �±��ϼ̽��ϴ�.");
				userGrade = newGrade;
			}

		} else {
			userPoint -= amount;
			iMsg("����Ʈ�� ���� �Ϸ�Ǿ����ϴ�.\n���� ����Ʈ : " + userPoint);
		}
		
		try (var pst = con.prepareStatement("update user set u_point = ? u_grade = ? where u_no = ?")){
			pst.setObject(1, userPoint);
			pst.setObject(2, userGrade);
			pst.setObject(3, userNo);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		updateLabel();
	}

	private void collapse() {
		setSize(700, getHeight());
		dP.setVisible(false);
		setLocationRelativeTo(null);
	}

	private void clickG(String text) {

		try (var pst = con.prepareStatement("select * from menu where m_group = ?")) {
			pst.setObject(1, text);

			var rs = pst.executeQuery();

			int count = 0;

			menuPanel.removeAll();

			while (rs.next()) {
				menuPanel.add(new MenuPanel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
				count++;
			}

			int rows = count / 3;

			if (count % 3 != 0) {
				rows++;
			}

			menuPanel.setPreferredSize(new Dimension(570, 205 * rows));
			// Layout ������Ʈ
			menuPanel.revalidate();

			collapse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class MenuPanel extends JPanel {

		public MenuPanel(int menuNo, String group, String menuName, int price) {
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(180, 200));

			String path = "./DataFiles/�̹���/" + menuName + ".jpg";

			var lbImg = new JLabel(getImage(path, 180, 180));
			var lbText = new JLabel(menuName, 0);

			lbImg.setCursor(new Cursor(Cursor.HAND_CURSOR));

			lbImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					curMenuNo = menuNo;
					curMenuGroup = group;

					dP.setVisible(true);
					StarBoxFrame.this.setSize(700 + dP.getWidth() + 20, 600);
					setLocationRelativeTo(null);

					lbDetailImg.setIcon(getImage(path, lbDetailImg.getWidth(), lbDetailImg.getHeight()));
					tfMenuName.setText(menuName);
					tfPrice.setText("" + price);

					cbSize.removeAllItems();

					if (group.equals("��ǰ") == false) {
						cbSize.addItem("M");
						cbSize.addItem("L");
					}
					tfAmount.setText("" + getAmount());
				}
			});

			lbImg.setBorder(new LineBorder(Color.BLACK));

			add(lbImg);
			add(lbText, BorderLayout.SOUTH);
		}

	}

	public static void main(String[] args) {
		new StarBoxFrame().setVisible(true);
	}
}

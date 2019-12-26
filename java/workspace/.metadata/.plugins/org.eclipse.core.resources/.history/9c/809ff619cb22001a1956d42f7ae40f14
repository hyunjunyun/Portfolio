package frame;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class StarBoxFrame extends FrameBase {
	JLabel lbCaption = createComp(new JLabel("", 2), 0, 25);
	JPanel menuPanel = createComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 0, 0);
	JPanel detailPanel = createComp(new JPanel(new BorderLayout()), 610, 130, 310, 210);
	JLabel lbDetailImg = createComp(new JLabel(), 0, 0, 100, 100);
	JTextField tfMenuName = createComp(new JTextField(), 175, 0, 130, 25);
	JTextField tfPrice = createComp(new JTextField(), 175, 35, 130, 25);
	JComboBox<Integer> cbCount = createComp(new JComboBox<>(), 175, 70, 130, 25);
	JComboBox<String> cbSize = createComp(new JComboBox<>(), 175, 105, 130, 25);
	JTextField tfAmount = createComp(new JTextField(), 175, 140, 130, 25);
	
	String curGroup;
	int curMenuNo;
	
	public StarBoxFrame() {
		super(700, 600, "STARBOX");
		
		var northPanel = new JPanel(new BorderLayout());
		var westPanel = createComp(new JPanel(), 55, 0);
		var centerPanel = new JPanel(null);
		var north_southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		north_southPanel.add(createButton("구매내역", e -> openFrame(new OrderlistFrame())));
		north_southPanel.add(createButton("장바구니", e -> openFrame(new ShoppingFrame())));
		north_southPanel.add(createButton("인기상품 Top5", e -> openFrame(new Top5Frame())));
		north_southPanel.add(createButton("Logout", e -> openFrame(new LoginFrame())));
		
		northPanel.add(lbCaption);
		northPanel.add(north_southPanel, BorderLayout.SOUTH);
		
		for (String text : new String[] { "음료", "푸드", "상품" }) {
			westPanel.add(createComp(createButtonWithoutMargin(text, e -> clickGroup(text)), 55, 35));
		}
		
		var scrollPane = createComp(new JScrollPane(menuPanel), 0, 0, 590, 480);
		
		lbDetailImg.setBorder(new LineBorder(Color.BLACK));
		
		var detail_centerPanel = new JPanel(null);
		var detail_southPanel = new JPanel();
		
		String[] labelTextList = {"주문메뉴 :", "가격 :", "수량 :", "사이즈 :", "총금액 :"};
		
		for (int i = 0; i < labelTextList.length; i++) {
			detail_centerPanel.add(createComp(new JLabel(labelTextList[i], JLabel.RIGHT), 105, i * 35, 60, 25));
		}
		
		tfMenuName.setEditable(false);
		tfPrice.setEditable(false);
		tfAmount.setEditable(false);
		
		detail_centerPanel.add(lbDetailImg);
		detail_centerPanel.add(tfMenuName);
		detail_centerPanel.add(tfPrice);
		detail_centerPanel.add(cbCount);
		detail_centerPanel.add(cbSize);
		detail_centerPanel.add(tfAmount);
				
		for (int i = 1; i <= 10; i++) {
			cbCount.addItem(i);
		}
		
		cbCount.addActionListener(e -> tfAmount.setText("" + getAmount()));
		cbSize.addActionListener(e -> tfAmount.setText("" + getAmount()));
		
		detail_southPanel.add(createButton("장바구니에 담기", this::clickCart));
		detail_southPanel.add(createButton("구매하기", this::clickBuy));
		
		detailPanel.add(detail_centerPanel);
		detailPanel.add(detail_southPanel, BorderLayout.SOUTH);
		detailPanel.setVisible(false);
		
		centerPanel.add(scrollPane);
		centerPanel.add(detailPanel);
		
		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		
		updateCaption();
		clickGroup("음료");
	}
	
	private void updateCaption() {
		lbCaption.setText(String.format("  회원명 : %s / 회원등급 : %s / 총 누적 포인트 : %d", userName, userGrade, userPoint));
	}
	
	private void clickBuy(ActionEvent e) {
		int amount = getAmount();
		int answer = JOptionPane.NO_OPTION;
		String curGrade = userGrade;
		String newGrade = userGrade;
		
		if (userPoint >= amount) {
			answer = JOptionPane.showConfirmDialog(null, "회원님의 총 포인트 : " + userPoint + "\n" +
					"포인트로 결제하시겠습니까?" + 
					"(아니오를 클릭 시 현금결제가 됩니다)", "결제수단", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (answer == JOptionPane.NO_OPTION) {
			// 현금결제
			try (var pst = con.prepareStatement("INSERT INTO orderlist VALUES(0, CURDATE(), ?, ?, ?, ?, ?, ?, ?)")) {
				pst.setObject(1, userNo);
				pst.setObject(2, curMenuNo);
				pst.setObject(3, curGroup);
				pst.setObject(4, cbSize.getSelectedItem());
				pst.setObject(5, tfPrice.getText());
				pst.setObject(6, cbCount.getSelectedItem());
				pst.setObject(7, amount);
				
				pst.execute();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try (var pst = con.prepareStatement("SELECT SUM(o_amount) FROM orderlist WHERE u_no = " + userNo)) {
				var rs = pst.executeQuery();
				
				rs.next();
				
				int totalAmount = rs.getInt(1);
				
				if (totalAmount >= 800000) {
					newGrade = "Gold";
				} else if (totalAmount >= 500000) {
					newGrade = "Silver";
				} else if (totalAmount >= 300000) {
					newGrade = "Bronze";
				} else {
					newGrade = "일반";
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			userPoint += amount * 0.05f;
			
			iMsg("구매되었습니다.");
			
			if (curGrade.equals(newGrade) == false) {
				// 등급업
				iMsg("축하합니다!\n회원님 등급이 " + newGrade + "로 승급하셨습니다.");
				userGrade = newGrade;
			}
						
		} else {
			// 포인트 구매
			userPoint -= amount;
			iMsg("포인트로 결제 완료되었습니다.\n남은 포인트 : " + userPoint);
		}
		
		try (var pst = con.prepareStatement("UPDATE user SET u_point = ?, u_grade = ? WHERE u_no = ?")) {
			pst.setObject(1, userPoint);
			pst.setObject(2, newGrade);
			pst.setObject(3, userNo);
			
			pst.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		updateCaption();
	}
	
	private void clickCart(ActionEvent e) {
		
		try (var pst = con.prepareStatement("INSERT INTO shopping VALUES(0, ?, ?, ?, ?, ?)")) {
			
			pst.setObject(1, curMenuNo);
			pst.setObject(2, tfPrice.getText());
			pst.setObject(3, cbCount.getSelectedItem());
			pst.setObject(4, cbSize.getSelectedItem());
			pst.setObject(5, tfAmount.getText());
			
			pst.execute();
			
			iMsg("장바구니에 담았습니다.");
			collapse();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void collapse() {
		setSize(700, getHeight());
		detailPanel.setVisible(false);
		setLocationRelativeTo(null);
	}
	
	private class MenuPanel extends JPanel {
		
		public MenuPanel(int menuNo, String group, String menuName, int price) {
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(180, 200));
			
			String path = "./DataFiles/이미지/" + menuName + ".jpg";
			
			var lbImg = new JLabel(getImage(path, 180, 180));
			var lbText = new JLabel(menuName, 0);
			
			lbImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			lbImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					curMenuNo = menuNo;
					curGroup = group;
					
					detailPanel.setVisible(true);
					StarBoxFrame.this.setSize(700 + detailPanel.getWidth() + 20, 600);
					setLocationRelativeTo(null);
					detailPanel.setVisible(true);
					
					lbDetailImg.setIcon(getImage(path, lbDetailImg.getWidth(), lbDetailImg.getHeight()));
					tfMenuName.setText(menuName);
					tfPrice.setText("" + price);
					
					cbSize.removeAllItems();
										
					if (group.equals("상품") == false) {
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
	
	private int getAmount() {
		int price = Integer.parseInt(tfPrice.getText());
		int count = (Integer)cbCount.getSelectedItem();
		
		if (cbSize.getSelectedIndex() == 1) {
			price += 1000;
		}
		
		float discount = 0f;
		
		if (userGrade.equals("Bronze")) {
			discount = 0.03f;
		} else if (userGrade.equals("Silver")) {
			discount = 0.05f;
		} else if (userGrade.equals("Gold")) {
			discount = 0.1f;
		}
		
		return (int)((price * count) * (1f - discount));
	}
	
	private void clickGroup(String group) {
		
		try (var pst = con.prepareStatement("SELECT * FROM menu WHERE m_group = ?")) {
			
			pst.setObject(1, group);
			
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
			// Layout 업데이트
			menuPanel.revalidate();
			collapse();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new StarBoxFrame().setVisible(true);
	}
}

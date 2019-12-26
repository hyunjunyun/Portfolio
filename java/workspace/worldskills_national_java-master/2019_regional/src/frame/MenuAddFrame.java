package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;


public class MenuAddFrame extends FrameBase {

	MenuPanel menuPanel = new MenuPanel("사진등록");

	public MenuAddFrame() {
		super(330, 220, "메뉴추가");
		
		var southPanel = new JPanel();
		
		add(menuPanel);
		
		southPanel.add(createButton("등록", this::clickSubmit));
		southPanel.add(createButton("취소", e -> {
			dispose();
			new AdminMenuFrame().setVisible(true);
		}));
		
		add(southPanel, "South");	
	}
	
	private void clickSubmit(ActionEvent e) {

		if (menuPanel.cbGroup.getSelectedIndex() == 0 ||
				menuPanel.tfMenu.getText().length() == 0 ||
				menuPanel.tfPrice.getText().length() == 0 ||
				menuPanel.path == null) {
			eMsg("빈칸 존재");
			return;
		}
		
		try {
			int price = Integer.parseInt(menuPanel.tfPrice.getText());
			String menuName = menuPanel.tfMenu.getText();
			
			try (var pst = con.prepareStatement("SELECT * FROM menu WHERE m_name = ?")) {
				
				pst.setObject(1, menuName);
				
				var rs = pst.executeQuery();
				
				if (rs.next()) {
					eMsg("메뉴명이 존재!");
					return;
				}
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			try (var pst = con.prepareStatement("INSERT INTO menu VALUES(0, ?, ?, ?)")) {
				pst.setObject(1, menuPanel.cbGroup.getSelectedItem());
				pst.setObject(2, menuName);
				pst.setObject(3, price);
				
				pst.execute();
				
				Files.copy(Paths.get(menuPanel.path), Paths.get("./DataFiles/이미지/" + menuName + ".jpg"));
				
				iMsg("메뉴가 등록됨");
			} catch (SQLException e1) {

				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NumberFormatException e1) {
			eMsg("가격은 숫자로");
		}
		
	}
	
	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}

}

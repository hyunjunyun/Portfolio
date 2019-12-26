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

	MenuPanel menuPanel = new MenuPanel("�������");

	public MenuAddFrame() {
		super(330, 220, "�޴��߰�");
		
		var southPanel = new JPanel();
		
		add(menuPanel);
		
		southPanel.add(createButton("���", this::clickSubmit));
		southPanel.add(createButton("���", e -> {
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
			eMsg("��ĭ ����");
			return;
		}
		
		try {
			int price = Integer.parseInt(menuPanel.tfPrice.getText());
			String menuName = menuPanel.tfMenu.getText();
			
			try (var pst = con.prepareStatement("SELECT * FROM menu WHERE m_name = ?")) {
				
				pst.setObject(1, menuName);
				
				var rs = pst.executeQuery();
				
				if (rs.next()) {
					eMsg("�޴����� ����!");
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
				
				Files.copy(Paths.get(menuPanel.path), Paths.get("./DataFiles/�̹���/" + menuName + ".jpg"));
				
				iMsg("�޴��� ��ϵ�");
			} catch (SQLException e1) {

				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NumberFormatException e1) {
			eMsg("������ ���ڷ�");
		}
		
	}
	
	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}

}

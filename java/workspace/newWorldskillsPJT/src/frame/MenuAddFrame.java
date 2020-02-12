package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.swing.JPanel;

public class MenuAddFrame extends BaseFrame {

	MenuPanel menuPanel = new MenuPanel("사진등록");

	public MenuAddFrame() {

		super(340, 210, "메뉴추가");

		add(menuPanel);

		var sp = new JPanel();

		sp.add(setBtn("등록", this::clickSubmit));

		sp.add(setBtn("취소", e -> openFrame(new AdminFrame())));

		add(sp, BorderLayout.SOUTH);

	}

	public void clickSubmit(ActionEvent eve) {
		String name = menuPanel.tfMenu.getText();
		String price = menuPanel.tfPrice.getText();
		String group = (String) menuPanel.cbG.getSelectedItem();

		try {
			if (name.isEmpty() || price.isEmpty() || group == null || menuPanel.path == null) {
				eMsg("빈칸이 존재합니다.");
				return;
			}

			try (var rs = stmt.executeQuery("select m_name from menu where m_name = '" + name + "'")) {

				if (rs.next()) {
					eMsg("이미 존재하는 메뉴명입니다.");
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try (var pst = con.prepareStatement("insert into menu values(0,?,?,?)")) {
				pst.setObject(1, group);
				pst.setObject(2, name);
				pst.setObject(3, Integer.parseInt(price));

				pst.execute();
				
				Files.copy(Paths.get(menuPanel.path), Paths.get("C:\\hjun\\git\\Portfolio\\java\\workspace\\20200204\\src\\DateFiles\\이미지\\" + name + ".jpg"));

				iMsg("메뉴가 등록되었습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} catch (NumberFormatException e) {
			eMsg("가격은 숫자로 입력해주세요.");
		}

	}

	public static void main(String[] args) {

		new MenuAddFrame().setVisible(true);

	}

}
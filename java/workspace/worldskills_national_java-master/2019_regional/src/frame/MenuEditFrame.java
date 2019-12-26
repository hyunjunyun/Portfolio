package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MenuEditFrame extends FrameBase {
	
	JComboBox<String> cbGroup = new JComboBox<>("전체,음료,푸드,상품".split(","));
	JTextField tfSearch = new JTextField(20);
	MenuPanel menuPanel = new MenuPanel("사진선택");
	DefaultTableModel dtm = new DefaultTableModel();
	JTable table = new JTable(dtm);
	
	public MenuEditFrame() {
		super(710, 280, "메뉴 수정");
		
		var northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		var centerPanel = new JPanel(null);
		
		var renderer = new DefaultTableCellRenderer();
		
		renderer.setHorizontalAlignment(0);
		
		dtm.setColumnIdentifiers("번호,분류,메뉴명,가격".split(","));
		
		var scrollPane = createComp(new JScrollPane(table), 0, 0, 370, 200);
		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectTableRow();
			}
		});
		
		table.removeColumn(table.getColumn("번호"));
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		
		northPanel.add(new JLabel("검색"));
		northPanel.add(cbGroup);
		northPanel.add(tfSearch);
		northPanel.add(createButton("찾기", this::clickSearch));
		
		centerPanel.add(scrollPane);
		
		var menuFieldsPanel = createComp(new JPanel(new BorderLayout()), 375, 0, 360, 180);
		var menuFieldsSouthPanel = new JPanel();
		
		menuFieldsSouthPanel.add(createButton("삭제", this::clickDelete));
		menuFieldsSouthPanel.add(createButton("수정", this::clickUpdate));
		menuFieldsSouthPanel.add(createButton("취소", e -> {
			dispose();
			new AdminMenuFrame().setVisible(true);
		}));
		
		menuFieldsPanel.add(menuPanel);
		menuFieldsPanel.add(menuFieldsSouthPanel, "South");
		
		centerPanel.add(menuFieldsPanel);
		
		add(northPanel, "North");
		add(centerPanel);
		
		clickSearch(null);
	}
	
	private void clear() {
		menuPanel.cbGroup.setSelectedItem("");
		menuPanel.tfMenu.setText("");
		menuPanel.tfPrice.setText("");
		menuPanel.path = null;
		menuPanel.lbImg.setIcon(null);
	}
	
	private void clickDelete(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMsg("삭제 메뉴 선택");
			return;
		}
		
		try {
			stmt.execute("DELETE FROM menu WHERE m_no = " + dtm.getValueAt(row, 0));
			
			Files.delete(Paths.get(menuPanel.path));
			
			iMsg("삭제되었습니다.");
			
			clear();
			clickSearch(null);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void clickUpdate(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			eMsg("수정할 메뉴 선택");
			return;
		}
		
		String menuName = menuPanel.tfMenu.getText();
		
		if (menuName.isEmpty() ||
			menuPanel.tfPrice.getText().isEmpty() ||
			menuPanel.cbGroup.getSelectedItem().equals("") ||
			menuPanel.path == null) {
			eMsg("빈칸 존재");
			return;
		}
		
		int price = 0;
		
		try {
			price = Integer.parseInt(menuPanel.tfPrice.getText());
		} catch (NumberFormatException e1) {
			eMsg("가격은 숫자로");
			return;
		}
		
		try (var pst = con.prepareStatement("SELECT * FROM menu WHERE m_name = ? AND m_no != " + dtm.getValueAt(row, 0))) {
			
			pst.setObject(1, menuName);
			
			var rs = pst.executeQuery();
			
			if (rs.next()) {
				eMsg("메뉴명이 존재!");
				return;
			}
			
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		try (var pst = con.prepareStatement("UPDATE menu SET m_group = ?, m_name = ?, m_price = ? WHERE m_no = ?")) {
			
			pst.setObject(1, menuPanel.cbGroup.getSelectedItem());
			pst.setObject(2, menuName);
			pst.setObject(3, price);
			pst.setObject(4, dtm.getValueAt(row, 0));
			
			pst.execute();
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			Files.copy(Paths.get(menuPanel.path), Paths.get("./DataFiles/이미지/" + menuName + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		iMsg("수정되었습니다.");
		
		clear();
		clickSearch(null);
		
	}
	
	private void selectTableRow() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			return;
		}
		
		menuPanel.cbGroup.setSelectedItem(dtm.getValueAt(row, 1));
		menuPanel.tfMenu.setText((String)dtm.getValueAt(row, 2));
		menuPanel.tfPrice.setText(dtm.getValueAt(row, 3).toString());
		menuPanel.path = "./DataFiles/이미지/" + dtm.getValueAt(row, 2) + ".jpg";
		menuPanel.lbImg.setIcon(getImage(menuPanel.path, 100, 100));
	}
	
	private void clickSearch(ActionEvent e) {
		
		dtm.setRowCount(0);
		
		try (var pst = con.prepareStatement("SELECT * FROM menu WHERE m_group LIKE ? AND m_name LIKE ?")) {
			
			if (cbGroup.getSelectedIndex() == 0) {
				pst.setObject(1, "%");
			} else {
				pst.setObject(1, cbGroup.getSelectedItem());
			}
			
			pst.setObject(2, "%" + tfSearch.getText() + "%");
			
			var rs = pst.executeQuery();
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)
				});
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		new MenuEditFrame().setVisible(true);
	}

}

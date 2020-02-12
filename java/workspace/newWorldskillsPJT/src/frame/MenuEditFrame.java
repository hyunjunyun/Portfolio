package frame;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class MenuEditFrame extends BaseFrame {

	JComboBox<String> cbg = new JComboBox<String>("전체,음료,푸드,상품".split(","));
	JTextField sf = new JTextField(20);
	MenuPanel mp = new MenuPanel("사진 선택");
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);

	public MenuEditFrame() {
		super(710, 280, "메뉴 수정");

		var np = new JPanel(new FlowLayout(FlowLayout.LEFT));
		var cp = new JPanel(null);

		model.setColumnIdentifiers("번호,분류,상품,가격".split(","));
		
		var scp = setComp(new JScrollPane(table), 0,0,370,200);
		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectTableRow();
			}
		});
		
		table.removeColumn(table.getColumn(""));
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(0).setCellRenderer(Crend);
		table.getColumnModel().getColumn(2).setCellRenderer(Rrend);
	}
	
	public void selectTableRow() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			return;
		}
		
		
		mp.cbG.setSelectedItem(model.getValueAt(row, 1));
		mp.tfMenu.setText((String)model.getValueAt(row, 2));
		mp.tfPrice.setText((String)model.getValueAt(row, 3));
		mp.path = "경로" + model.getValueAt(row, 2) + ".jpg";
		mp.lbImg.setIcon(getImage(mp.path, 100,100));
	}
	
	public void delete(){
		
	}
	
	public void updateMenu() {
		
	}
	public static void main(String[] args) {
		new MenuEditFrame().setVisible(true);
	}
}

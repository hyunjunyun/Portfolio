package frame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Shopping extends BaseFrame{
	public Shopping() {
		super(700, 350, "장바구니");
		
		var model = new DefaultTableModel("n,메뉴명,가격,수량,사이즈,금액".split(","),0);
		var table = new JTable(model);
		var scp = new JScrollPane(table);
		
		add(scp);
	}
	
	public static void main(String[] args) {
		new Shopping().setVisible(true);
	}
}

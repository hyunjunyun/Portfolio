package frame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Shopping extends BaseFrame{
	public Shopping() {
		super(700, 350, "��ٱ���");
		
		var model = new DefaultTableModel("n,�޴���,����,����,������,�ݾ�".split(","),0);
		var table = new JTable(model);
		var scp = new JScrollPane(table);
		
		add(scp);
	}
	
	public static void main(String[] args) {
		new Shopping().setVisible(true);
	}
}

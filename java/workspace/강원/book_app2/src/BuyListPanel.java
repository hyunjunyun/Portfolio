import java.awt.FlowLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BuyListPanel extends BasePanel{
	
	String str[] = "주문일자,시간,제목,저자,수량".split(",");
	DefaultTableModel dtm = new DefaultTableModel(null,str) {
		public boolean isCellEditable(int row, int column) {return false;}
	};
	JTable jt = new JTable(dtm);
	JScrollPane jsc = new JScrollPane(jt);
	int offset = -15;
	boolean chk =false;
	
	public BuyListPanel(MainFrame main) {
		super("구매내역", 710, 250, new FlowLayout(FlowLayout.CENTER), main);
		addComponents();
		addListeners();
		readData();
	}

	@Override
	void addComponents() {
		add(addJl(MainFrame.id+"님 구매내역", 20, JLabel.CENTER));
		setComponents(jsc, 0, 0, 700, 180);
		int s[] = {100,100,300,200,100};
		for(int i = 0 ; i < jt.getColumnModel().getColumnCount();i++)
			jt.getColumnModel().getColumn(i).setPreferredWidth(s[i]);
		add(cB("메인으로", e->goMain()));
	}
	
	void goMain() {
		main.setPanel(new MainPanel(main));
	}
	
	void readData() {
		if(chk)return;
		offset+=15;
		int count = 0;
		try {
			ResultSet rs =DBSetting.stmt.executeQuery("select date(o.order_time) as d, time(o.order_time) as t,b.name,b.author,o.quantity from order_log as o inner join book as b on b.id = o.book_id where o.member_id = " + MainFrame.no +" order by d desc,t desc limit 15 offset " + offset);
			while(rs.next()) {
				count++;
				String str[] = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
				dtm.addRow(str);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count==0) {
			infoMsg("더 이상의 구매내역은 없습니다.", "안내");
			chk =true;
		}
		
	}

	@Override
	void addListeners() {
		
		var val = jsc.getVerticalScrollBar();
		val.addAdjustmentListener(new AdjustmentListener() {
			
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(val.getValue()!=0 && (val.getValue() + val.getHeight())==val.getMaximum()) {
					readData();
				}
			}
		});
	}

}

package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DiagnosisReserveFrame extends BaseFrame {

	DefaultTableModel model = new DefaultTableModel("����,�����,�ǻ�,���ᳯ¥,�ð�".split(","), 0);
	JTable table = new JTable(model);

	JTextField nbf = new JTextField(10);
	JTextField nf = new JTextField(10);
	public static JTextField df = new JTextField(10);
	JTextField mhf = new JTextField(10);

	JTextField[] tf = { nbf, nf, df, mhf };

	JComboBox<String> cbdp = setComp(new JComboBox<String>(",����,�����ܰ�,�Ȱ�,ġ��".split(",")), 105, 20);
	JComboBox<String> cbdc = setComp(new JComboBox<String>(), 105, 20);

	JComboBox<String> imgcb = new JComboBox<String>();
	JLabel img = setComp(new JLabel(), 100, 100);
	JCheckBox box = new JCheckBox();

	String curTime = "";
	String curDay = "";

	public DiagnosisReserveFrame() {
		super(450, 500, "���Ό��", 2);
		setLayout(new FlowLayout());
		var np = setComp(new JPanel(new FlowLayout(FlowLayout.CENTER)), 480, 85);
		var cp = setComp(new JPanel(new BorderLayout()), 430, 200);
		var sp = setComp(new JPanel(new FlowLayout(FlowLayout.LEFT)), 480, 120);
		var sp_s = setComp(new JPanel(new FlowLayout(FlowLayout.RIGHT)), 430, 50);
		var scp = setComp(new JScrollPane(table), cp.getWidth(), cp.getHeight());

		box.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn("����").setCellEditor(new DefaultCellEditor(box));
		sp.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		// northPanel design
		np.add(setComp(new JLabel("ȯ�ڹ�ȣ", 0), 100, 20));
		np.add(nbf);
		np.add(setComp(new JLabel("ȯ�ڸ�", 0), 100, 20));
		np.add(nf);
		np.add(setComp(new JLabel("�����", 0), 100, 20));
		np.add(cbdp);
		np.add(setComp(new JLabel("�ǻ�", 0), 100, 20));
		np.add(cbdc);
		np.add(setComp(new JLabel("��¥", 0), 100, 20));
		np.add(df);
		np.add(setComp(new JLabel("�����̷�", 0), 100, 20));
		np.add(mhf);

		cbdp.addActionListener(this::updateDoc);
		cbdp.addActionListener(this::checkUp);

		cbdc.addActionListener(this::uploadData);

		nbf.setText("" + userNo);
		nf.setText(userName);
		for (int i = 0; i < tf.length; i++) {
			tf[i].setEnabled(false);
			tf[i].setHorizontalAlignment(0);
		}
		nbf.setEnabled(false);
		nf.setEnabled(false);

		df.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openFrame(new CalendarForm());
			}
		});
		// centerPanel design
		table.getColumn("����").setPreferredWidth(30);
		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rend);
		}

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				curDay = (String) model.getValueAt(row, 4);
				curTime = (String) model.getValueAt(row, 5);

			}
		});

		cp.add(scp);
		cp.setBorder(new LineBorder(Color.black));

		// southPanel design
		sp.add(new JLabel("�˻�"));
		sp.add(imgcb);
		sp.add(img);
		sp_s.add(setBtn("����", this::reserve));
		sp_s.add(setBtn("�ݱ�", e -> dispose()));
		String[] list = "���þ���,CT�˻�,MRI�˻�,UBT�˻�,X-Ray�˻�,�����İ˻�".split(",");
		for (int i = 0; i < list.length; i++) {
			imgcb.addItem(list[i]);
		}

		imgcb.addActionListener(this::sImage);

		img.setBorder(new LineBorder(Color.black));
		add(np);
		add(cp);
		add(sp);
		add(sp_s);
	}

	// table data
	public void uploadData(ActionEvent e) {
		try (var pst = con.prepareStatement("select * from diagnosis where d_name = ?")) {
			pst.setObject(1, cbdc.getSelectedItem());
			var rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				model.addRow(
						new Object[] { false, rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4) });
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// update Doctor list
	public void updateDoc(ActionEvent e) {
		cbdc.removeAllItems();
		try (var pst = con.prepareStatement("select distinct d_name from diagnosis where d_section = ?;")) {
			pst.setObject(1, cbdp.getSelectedItem());
			var rs = pst.executeQuery();

			while (rs.next()) {
				cbdc.addItem(rs.getString(1));
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// addImage
	public void sImage(ActionEvent e) {
		img.setIcon(addImage(100, 100,
				"C:/hjun/git/Portfolio/java/workspace/���_project/�����ڷ�/�̹���/" + imgcb.getSelectedItem() + ".jpg"));
	}

	// click reserve button
	public void reserve(ActionEvent e) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date();
		Date d2 = null;
		try {
			d2 = format.parse(curDay);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (curTime.isEmpty() || curDay.isEmpty()) {
			eMsg("������ �ð��� �������ּ���.");
			return;
		}
		if (d1.compareTo(d2) > 0) {
			eMsg("���糯¥���� �������̰ų� �����ϰ� ������ ������ �� �����ϴ�.");
			return;
		}
		
		if (d1.getDate() > d2.getDate()) {
			eMsg("�������Ͽ� ���� �� ���Ḧ �� �� �����ϴ�.");
			return;
		}
		try (var pst = con.prepareStatement("insert into diagnosisreserve values(0,?,?,?,?,?,?,?)")) {
			pst.setObject(1, userNo);
			pst.setObject(2, cbdp.getSelectedItem());
			pst.setObject(3, cbdp.getSelectedItem());
			pst.setObject(4, curDay);
			pst.setObject(5, curTime);
			pst.setObject(6, curDay);
			pst.setObject(7, imgcb.getSelectedItem());

			pst.execute();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void checkUp(ActionEvent e) {

		try (var pst = con.prepareStatement(
				"select * from inpatientstate as ip inner join diagnosisreserve as dr on ip.p_no = dr.p_no where ip.p_no = ? and dr_section = ? limit 1")) {
			pst.setObject(1, userNo);
			pst.setObject(2, cbdp.getSelectedItem());
			var rs = pst.executeQuery();
			if (rs.next()) {
				mhf.setText("����");
			} else {
				mhf.setText("����");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent // ��������
		(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setBackground(Color.white);
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	public static void main(String[] args) {
		new DiagnosisReserveFrame().setVisible(true);
	}

}

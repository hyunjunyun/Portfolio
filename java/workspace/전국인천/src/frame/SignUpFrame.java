package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.FileFilter;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SignUpFrame extends BaseFrame {
	String[] list = "���̵�:,��й�ȣ:,��й�ȣ: ��Ȯ��:,�̸�:,��ȭ��ȣ:,�̸���:".split(",");
	JLabel icon = setComp(new JLabel(), 300, 220);
	JTextField[] tf = new JTextField[8];
	JComboBox<String> cbEm = new JComboBox<String>("naver.com,gmail.com,daum.net".split(","));
	int cnt = 0;
	String path = null;

	public SignUpFrame() {
		super(650, 330, "ȸ������", 2);
		var cp = new JPanel(null);
		var ep = setComp(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)), 300, 300);
		var sp = new JPanel();
		// cp
		for (int i = 0; i < list.length; i++) {
			cp.add(setComp(new JLabel(list[i]), 5, 10 + 35 * i, 100, 20));
		}
		for (int i = 0; i < tf.length; i++) {
			if (i == 3) {
				cp.add(setComp(tf[i] = new JTextField(), 110, 10 + i * 35, 100, 20));
			} else if (i > 3 && i < 7) {
				cp.add(setComp(tf[i] = new JTextField(), 110 + cnt * 60, 145, 50, 20));
				cnt++;
			} else if (i == 7) {
				cp.add(setComp(tf[i] = new JTextField(), 90, 185, 110, 20));
			} else {
				cp.add(setComp(tf[i] = new JTextField(), 110, 10 + i * 35, 150, 20));
			}
		}
		cp.add(setComp(new JLabel("-"), 160, 145, 10, 20));
		cp.add(setComp(new JLabel("-"), 220, 145, 10, 20));
		cp.add(setComp(new JLabel("@"), 205, 185, 20, 20));
		cp.add(setComp(cbEm, 225, 185, 90, 20));
		// ep
		ep.add(icon);
		icon.setBorder(new LineBorder(Color.black, 2));
		ep.add(setComp(setBtn("�������", this::addIcon), 300, 30));

		// sp
		sp.add(setBtn("ȸ������", this::submit));
		cp.setBorder(new LineBorder(Color.red));
		sp.setBorder(new LineBorder(Color.yellow));
		add(cp, BorderLayout.CENTER);
		add(ep, BorderLayout.EAST);
		add(sp, BorderLayout.SOUTH);

	}

	public void submit(ActionEvent e) {
		for (int i = 0; i < tf.length; i++) {
			if (tf[i].getText().isEmpty() || icon.getIcon() == null) {
				eMsg("������ �����մϴ�.");
				return;
			}
		}
		
		if (tf[1].getText().equals(tf[2].getText()) == false) {
			eMsg("��й�ȣ�� �ٸ��� �ʽ��ϴ�");
			return;
		}
		
		if (tf[1].getText().matches("(?=.*\\d)(?=.*[a-z])(?=*[])*") == false) {
			eMsg("��й�ȣ�� ��,Ư��,���ĺ� 1���� �̻�");
		}
		try (var pst = con.prepareStatement("select * from user where id=? or nickname=? or num=? or Email=?")) {
			pst.setObject(1, tf[0]);
			pst.setObject(2, tf[3]);
			pst.setObject(3, String.format("%s-%s-%s", tf[4], tf[5], tf[6]));
			pst.setObject(4, String.format("%s@%s", tf[7], cbEm.getSelectedItem()));

			var rs = pst.executeQuery();
			if (rs.next()) {
				eMsg("�ߺ��� �����Ͱ� �ֽ��ϴ�.");
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void addIcon(ActionEvent e) {
		var ch = new JFileChooser();
		var f = new FileNameExtensionFilter("JPG Image", "jpg");
		ch.setFileFilter(f);

		if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = ch.getSelectedFile().getAbsolutePath();

			icon.setIcon(getImage(path, 300, 220));
		}
	}

	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}

}

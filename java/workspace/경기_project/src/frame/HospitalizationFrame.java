package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import static frame.BaseFrame.setBtn;

public class HospitalizationFrame extends BaseFrame {

	JTextField hdf = new JTextField();
	JComboBox<Integer> pf = new JComboBox<Integer>(new Integer[] { 2, 4, 6 });
	JButton ltn = setBtn("<", this::left);
	JButton rtn = setBtn(">", this::right);

	int layer = 1;

	static JPanel cp = new JPanel();
	static JPanel cp_inner = new JPanel();

	public HospitalizationFrame() {
		super(400, 400, "입원", 2);
		var np = new JPanel(new GridLayout(1, 0));
		np.add(new JLabel("입원날짜", 0));
		np.add(hdf);
		hdf.setText("" + LocalDate.now());
		hdf.setEnabled(false);
		np.add(new JLabel("인실", 0));
		np.add(pf);
		np.add(setBtn("검색", this::search));

		pf.addActionListener(e -> addPanel());

		cp.add(ltn);
		cp.add(setComp(cp_inner, 250, 200));
		cp_inner.setBorder(new LineBorder(Color.black));
		cp.add(rtn);

		addPanel();
		add(cp, BorderLayout.CENTER);
		add(np, BorderLayout.NORTH);
	}

	public void search(ActionEvent e) {

	}

	public void left(ActionEvent e) {
		layer--;
		addPanel();
	}

	public void right(ActionEvent e) {
		layer++;
		addPanel();
	}

	public void submit(ActionEvent e) {

	}

	public void addPanel() {
		cp_inner.removeAll();
		for (int i = 1; i <= (Integer) pf.getSelectedItem(); i++) {
			cp_inner.add(new roomSelectPanel(i));
		}
		revalidate();
	}

	class roomSelectPanel extends JPanel {
		public roomSelectPanel(int i) {
			setLayout(new BorderLayout());
			if (pf.getSelectedItem().equals(4)) {
				HospitalizationFrame.this.setSize(600, 400);
				HospitalizationFrame.cp_inner.setPreferredSize(new Dimension(500,200));
			} else if (pf.getSelectedItem().equals(6)) {
				HospitalizationFrame.this.setSize(900, 400);
				HospitalizationFrame.cp_inner.setPreferredSize(new Dimension(700,200));
			} else {
				HospitalizationFrame.this.setSize(400, 400);
			}
			setPreferredSize(new Dimension(110, 180));
			var btn = new JButton("" + (layer * 100 + i));
			var lb = new JLabel((layer * 100 + i) + "호", 0);
			lb.setSize(110, 20);
			btn.setSize(110, 20);
			add(lb, BorderLayout.NORTH);
			add(btn, BorderLayout.CENTER);
			btn.addActionListener(this::selectNum);

		}

		public void selectNum(ActionEvent e) {
		}

	}

	public static void main(String[] args) {
		new HospitalizationFrame().setVisible(true);
	}

}

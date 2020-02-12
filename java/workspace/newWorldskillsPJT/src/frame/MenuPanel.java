package frame;

import static frame.BaseFrame.getImage;
import static frame.BaseFrame.setBtn;
import static frame.BaseFrame.setComp;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuPanel extends JPanel {

	public JComboBox<String> cbG = new JComboBox<String>(",음료,푸드,상품".split(","));
	public JTextField tfMenu = new JTextField();
	public JTextField tfPrice = new JTextField();
	public JLabel lbImg = new JLabel();
	public String path = null;

	public MenuPanel(String caption) {
		setLayout(null);

		var labels = "분류,메뉴명,가격".split(",");

		for (int i = 0; i < labels.length; i++) {
			add(setComp(new JLabel(labels[i]), 0, i * 45, 75, 25));
		}

		add(setComp(cbG, 75, 0, 50, 25));
		add(setComp(tfMenu, 75, 45, 130, 25));
		add(setComp(tfPrice, 75, 90, 130, 25));

		lbImg.setBorder(new LineBorder(Color.black));

		add(setComp(lbImg, 210, 10, 100, 100));
		add(setComp(setBtn(caption, this::clickBtn), 210, 110, 100, 25));
	}

	public void clickBtn(ActionEvent e) {
		var ch = new JFileChooser();
		var filter = new FileNameExtensionFilter("JPG Image", "JPG");
		ch.setFileFilter(filter);
		if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = ch.getSelectedFile().getAbsolutePath();
			
			lbImg.setIcon(getImage(path, 100, 100));
		}
	}

	public static void main(String[] args) {
		var frame = new JFrame();

		frame.add(new MenuPanel("사진선택"));
		frame.setSize(330, 200);
		frame.setDefaultCloseOperation(2);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}
}

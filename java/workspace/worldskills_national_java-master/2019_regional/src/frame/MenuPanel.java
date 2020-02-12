package frame;

import static frame.FrameBase.createButton;
import static frame.FrameBase.createComp;
import static frame.FrameBase.getImage;

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

	public JComboBox<String> cbGroup = new JComboBox<String>(",음료,푸드,상품".split(","));
	public JTextField tfMenu = new JTextField();
	public JTextField tfPrice = new JTextField();
	public JLabel lbImg = new JLabel();
	public String path = null;
	
	public MenuPanel(String caption) {
		setLayout(null);
		
		var labels = "분류,메뉴명,가격".split(",");
		
		for (int i = 0; i < labels.length; i++) {
			add(createComp(new JLabel(labels[i]), 0, i * 45, 76, 25));
		}
		
		add(createComp(cbGroup, 76, 10, 50, 25));
		add(createComp(tfMenu, 76, 55, 130, 25));
		add(createComp(tfPrice, 76, 105, 130, 25));
		
		lbImg.setBorder(new LineBorder(Color.BLACK));
		
		add(createComp(lbImg, 211, 10, 100, 100));
		add(createComp(
				createButton(caption, this::clickButton)
				, 211, 110, 100, 25));
	}
	
	private void clickButton(ActionEvent e) {
		var chooser = new JFileChooser();
		var filter = new FileNameExtensionFilter("JPG Image", "jpg");
		
		chooser.setFileFilter(filter);
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
			
			lbImg.setIcon(getImage(path, 100, 100));
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.add(new MenuPanel("사진선택"));
		frame.setSize(330, 200);
		frame.setDefaultCloseOperation(2);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}

}

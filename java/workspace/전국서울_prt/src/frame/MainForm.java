package frame;

import static frame.BasePanel.setComp;
import static frame.BasePanel.setLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainForm extends BaseFrame {

	JLabel lbal = setComp(setLabel(new JLabel("·Î±×ÀÎ"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbas = setComp(setLabel(new JLabel("È¸¿ø°¡ÀÔ"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbad = setComp(setLabel(new JLabel("Á¾·á"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);

	JLabel lbbLog = setComp(setLabel(new JLabel("·Î±×¾Æ¿ô"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbbMy = setComp(setLabel(new JLabel("¸¶ÀÌÆäÀÌÁö"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbbSear = setComp(setLabel(new JLabel("È£ÅÚ°Ë»ö"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbbMain = setComp(setLabel(new JLabel("¸ÞÀÎ"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);
	JLabel lbbDis = setComp(setLabel(new JLabel("Á¾·á"), new Font("¸¼Àº°íµñ", 1, 18)), 100, 40);

	UserData ud;
	JPanel jp;
	JPanel ep = setComp(new JPanel(), 120, getHeight());

	public MainForm(UserData ud) {
		super(895, 650, "Hotel", 2);
		this.ud = ud;
		
		ud.setUName("96k55");
		ud.setPhone("010-2057-2825");
		ud.setNo(29);
		jp = new MainPanel(ud);
		System.out.println(jp.getName());
		setEp();
		ep.setBackground(Color.white);
		add(ep, BorderLayout.WEST);
		add(jp, BorderLayout.CENTER);
	}

	public void setEp() {
		ep.removeAll();

		if (ud.getCheck() == false) {
			lbal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			lbas.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			ep.add(lbal);
			ep.add(lbas);
			ep.add(lbad);
			mouseClickD(lbal, new LoginModal(ud));
			mouseClickD(lbas, new SignUpModal());
			mouseClickD(lbad, null);
		} else {
			lbbLog.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			lbbMy.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			lbbSear.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			lbbMain.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			ep.add(lbbLog);
			ep.add(lbbMy);
			ep.add(lbbSear);
			ep.add(lbbMain);
			ep.add(lbbDis);
			mouseClickD(lbbLog, null);
			mouseClickP(lbbMy, jp, new MyPagePanel(ud));
			mouseClickP(lbbSear, jp, new HotelSearchPanel(ud));
			mouseClickP(lbbMain, jp, null);
			mouseClickD(lbbDis, null);
		}
	}

	public void mouseClickD(JLabel lb, JDialog dial) {
		lb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lb.setText("<html><font size='5' color='blue'><strong>" + lb.getText() + "</strong></font></html>");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
				try {
					dial.setVisible(true);
				} catch (Exception e2) {
					if (lb == lbbLog) {
						ud.setCheck(false);
						setEp();
						new MainForm(ud).setVisible(true);
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lb.setText("<html><font size='5' color='black'><strong>" + lb.getText() + "</strong></font></html>");
			}
		});
	}

	public void mouseClickP(JLabel lb, JPanel np, JPanel cp) {
		lb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lb.setText("<html><font size='5' color='blue'><strong>" + lb.getText() + "</strong></font></html>");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				jp.removeAll();
				if (lb == lbbMy) {
					jp.add(new MyPagePanel(ud));
				} else if (lb == lbbSear) {
					jp.add(new HotelSearchPanel(ud));
				} else if (lb == lbbMain) {
					jp.add(new MainForm(ud));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lb.setText("<html><font size='5' color='black'><strong>" + lb.getText() + "</strong></font></html>");
			}
		});
	}
}

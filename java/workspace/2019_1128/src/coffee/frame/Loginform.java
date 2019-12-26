package coffee.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Loginform extends JFrame implements ActionListener {

	private JPanel lpl;
	private JPanel tpl;
	private JPanel mpl;
	private JPanel bpl;

	private JPanel text;
	private JPanel text1;
	private JPanel text2;
	private JPanel lbpl;
	private JLabel tl;
	private JButton lb = new JButton();
	private JButton ob = new JButton("확인");
	private JButton rb = new JButton("회원가입");
	private JLabel il = new JLabel("ID:");
	private JLabel pl = new JLabel("PW:");
	private JTextField it = new JTextField();
	private JTextField pt = new JTextField();

	public Loginform() {
		createUI();
		createMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
// e.
	}

	public void createUI() {

		tpl = new JPanel(new FlowLayout(1));
		mpl = new JPanel(new BorderLayout());
		bpl = new JPanel(new FlowLayout(1));
		lbpl = new JPanel(new FlowLayout(10));
		text = new JPanel();
		text.setLayout(null);
		text1 = new JPanel();
		text2 = new JPanel();

		tl = new JLabel("STARBOX");
		tl.setFont(new Font("Arial Black", Font.BOLD, 25));
		il.setFont(new Font("굴림", Font.BOLD, 12));
///////////////////////////////

		text1.add(il);
		pl.setFont(new Font("굴림", Font.BOLD, 12));
		text2.add(pl);
		text1.add(it);
		text2.add(pt);
		pt.setColumns(17);
		it.setColumns(17);
		text.add(text1);
		text1.setBounds(60, 5, 230, 30);
		text.add(text2);
		text2.setBounds(55, 45, 230, 30);
//////////////////////////////
		tpl.add(tl);
		mpl.add(text, BorderLayout.CENTER);
		mpl.add(lbpl, BorderLayout.EAST);
		lb.setText("\uB85C\uADF8\uC778\r\n");
		lbpl.add(lb);
		lb.setPreferredSize(new Dimension(80, 80));

		bpl.add(rb);
		bpl.add(ob);

	}

	public void createMain() {
		Container mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout());
		mainContainer.add(tpl, BorderLayout.NORTH);
		mainContainer.add(bpl, BorderLayout.SOUTH);
		mainContainer.add(mpl, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setTitle("로그인");
		setSize(380, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
package practice;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class BaseFrame extends JFrame {
	String err = "";
	DecimalFormat dec = new DecimalFormat("00");

	static Connection con;
	static Statement stmt;

	static {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/wedding" + "?serverTimezone=UTC&useSSL=false&allowLoadLocalInfile=true",
					"root", "1234");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BaseFrame(String title, int w, int h, LayoutManager lay) {
		setTitle(title);
		setSize(w, h);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(lay);
	}

	abstract void addComponents();

	abstract void addListeners();

	boolean chkErr() {
		if (!err.equals("")) {
			errMSg(err);
			return false;
		}
		return true;
	}

	int toInt(Object obj) {
		if (obj.toString().equals(""))
			return -1;
		return Integer.parseInt(obj.toString());
	}

	int toInt(String obj) {
		if (obj.toString().equals(""))
			return -1;
		return Integer.parseInt(obj.toString());
	}

	void setComponents(JComponent com, int x, int y, int w, int h) {
		com.setBounds(x, y, w, h);
		com.setPreferredSize(new Dimension(w, h));
		add(com);
	}

	void setComponents(JPanel jp, JComponent com, int x, int y, int w, int h) {
		com.setBounds(x, y, w, h);
		com.setPreferredSize(new Dimension(w, h));
		jp.add(com);
	}

	void addNull(int w, int h) {
		JLabel jl = new JLabel();
		jl.setPreferredSize(new Dimension(w, h));
		add(jl);
	}

	void addNull(JPanel p, int w, int h) {
		JLabel jl = new JLabel();
		jl.setPreferredSize(new Dimension(w, h));
		p.add(jl);
	}

	void addLong(int x, int y, int w, int h, int sort, JComponent... components) {
		JPanel jp = new JPanel();
		setComponents(jp, x, y, w, h);
		for (JComponent jComponent : components) {
			jp.add(jComponent);
		}
	}

	void addLong(JPanel p, int x, int y, int w, int h, int sort, JComponent... components) {
		JPanel jp = new JPanel();
		setComponents(p, jp, x, y, w, h);
		for (JComponent jComponent : components) {
			jp.add(jComponent);
		}
	}

	void infoMSg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
	}

	void errMSg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "MESSAGE", JOptionPane.ERROR_MESSAGE);
	}

	String inputMsg(String msg) {
		Object op[] = { "�솗�씤", "痍⑥냼" };
		String str = "";
		str = JOptionPane.showInputDialog(null, msg, "입력", JOptionPane.QUESTION_MESSAGE);
		return str;
	}

	Image readImage(String path, int w, int h) {
		Image im = null;
		try {
			im = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		im = im.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return im;
	}

	DefaultTableModel addDTM(String str[]) {
		DefaultTableModel dtm = new DefaultTableModel(null, str) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return dtm;
	}

	JTable addJT(DefaultTableModel dtm) {
		JTable jt = new JTable(dtm);

		return jt;
	}

	void saveAsImage(Image im, String path, int w, int h) {
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = buf.createGraphics();
		g2d.drawImage(im, 0, 0, null);
		try {
			ImageIO.write(buf, "jpg", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	void openFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
	}

}

package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public abstract class PageBase extends JPanel {
	public static final MainFrame mainFrame = new MainFrame();
	public static final Font NAV_BOLD_FONT = new Font("맑은 고딕", 1, 11);
	public static final Stroke BASIC_STROKE = new BasicStroke(3f);
	public static final String[] surveyColumns = "매우 불만족,불만족,보통,만족,매우 만족".split(",");

	public static final Path APP_FOLDER = Paths.get(System.getenv("APPDATA") + "/bookapp");
	public static final Path BLOCK_TEXT = Paths.get(System.getenv("APPDATA") + "/bookapp/block");
	public static final Path SAVED_ID = Paths.get(System.getenv("APPDATA") + "/bookapp/saved");
	public static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static final Border blackBorder = new LineBorder(Color.BLACK);
	public static final Border underlineBorder = new MatteBorder(0, 0, 1, 0, Color.BLUE);
	public static Connection con;
	public static Statement stmt;

	public static int memberNo;
	public static String loginId;
	public static String authority;

	public String text;
	int width, height;

	static {

		ymd.setLenient(false);

		try {
			Files.createDirectories(APP_FOLDER);

			con = DriverManager.getConnection("jdbc:mysql://localhost/bookdb?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();

			setSession(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setSession(int id) {
		try (var rs = stmt.executeQuery("SELECT * FROM `member` WHERE id = " + id)) {

			if (rs.next()) {
				memberNo = rs.getInt(1);
				loginId = rs.getString(2);
				authority = rs.getString(5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getSavedTextOrEmpty(Path path) {

		if (Files.exists(path)) {
			try {
				return Files.readString(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	public static void writeText(Path path, String text) {

		try {
			Files.writeString(path, text, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void eMsg(String text, String title) {
		JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void iMsg(String text, String title) {
		JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static JButton createButton(String text, ActionListener action) {
		var btn = new JButton(text);

		btn.addActionListener(action);

		return btn;
	}

	public static JButton createButton(String text, ImageIcon icon, ActionListener action) {
		var btn = createButton(text, action);

		btn.setIcon(icon);
		btn.setHorizontalTextPosition(JButton.CENTER);
		btn.setVerticalTextPosition(JButton.BOTTOM);

		return btn;
	}

	public static ImageIcon getIcon(String path, int width, int height) {
		return new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public static ImageIcon getIcon(ImageIcon icon, int width, int height) {
		return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public static <T extends JComponent> T createComp(T comp, int width, int height) {
		comp.setPreferredSize(new Dimension(width, height));

		return comp;
	}

	public static <T extends JComponent> T createComp(T comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);

		return comp;
	}

	public static JLabel createLabel(JLabel lb, float size, Color color) {
		lb.setFont(NAV_BOLD_FONT.deriveFont(size));
		lb.setForeground(color);

		return lb;
	}

	public static JLabel createLabel(JLabel lb, Font font) {
		lb.setFont(font);

		return lb;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		RoundRectangle2D rect2d = new RoundRectangle2D.Float(1.5f, 1.5f, width - 3, height - 3, 15, 15);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.WHITE);

		g2d.fill(rect2d);

		g2d.setStroke(BASIC_STROKE);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.draw(rect2d);
	}

	public PageBase(int width, int height, String text) {
		this.width = width;
		this.height = height;
		this.text = text;

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setSize(width, height);
	}

	public static void movePage(PageBase page) {
		mainFrame.addPage(page);
	}

}
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import data.ComboItem;

public class SearchPage extends PageBase {
	private static final SimpleDateFormat bookSdf = new SimpleDateFormat("yyyy년 MM월 dd일 출간");
	private static final Font bookFont = new Font("맑은 고딕", 0, 10);
	private static final int BOOK_WIDTH = 350;
	private static final int BOOK_HEIGHT = 230;
	
	JComboBox<String> cbSearchCategory = new JComboBox<String>("통합검색,분류검색,태그검색".split(","));
	JComboBox<ComboItem> cbMainCategory = new JComboBox<>();
	JComboBox<ComboItem> cbSubCategory = new JComboBox<>();
	HashSet<Integer> cartSet = new HashSet<Integer>();
	
	JTextField tfSearch = new JTextField(30);
	JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
	
	class BookItem extends JPanel {
		
		BookItem(int id, String name, ImageIcon icon, int stock, int price, String author, String intro, int numpages, String isbn, Date created, String hashTag) {
			setPreferredSize(new Dimension(BOOK_WIDTH, BOOK_HEIGHT));
			setLayout(null);
			setBackground(Color.WHITE);
			setBorder(blackBorder);
			
			var desc = createComp(createLabel(new JLabel("크게 보기", 0), 10, Color.BLUE), 5, 5, 120, 190);
			var lbSmallImg = new JLabel(getIcon(icon, 120, 190));
			var lbBigImg = new JLabel(getIcon(icon, 240, 380));
			
			lbSmallImg.setBorder(blackBorder);
			lbBigImg.setBorder(blackBorder);
			
			desc.setVisible(false);
			add(desc);
			
			lbSmallImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					desc.setVisible(true);
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					desc.setVisible(false);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					JOptionPane.showMessageDialog(null, lbBigImg, "크게 보기", JOptionPane.PLAIN_MESSAGE);
				}
				
			});
			
			lbSmallImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
			desc.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			add(createComp(lbSmallImg, 5, 5, 120, 190));
			add(createComp(createLabel(new JLabel(name), 14, Color.BLACK), 130, 5, 215, 25));
			
			var tagPanel = createComp(new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3)), 5, 200, 340, 25);
			
			tagPanel.setBorder(blackBorder);
			tagPanel.setBackground(Color.WHITE);
			
			add(tagPanel);
			add(createComp(createLabel(new JLabel(author + " 지음 - 총 " + numpages + "쪽"), bookFont), 130, 30, 215, 25));
			add(createComp(createLabel(new JLabel(bookSdf.format(created)), bookFont), 130, 50, 215, 25));
			add(createComp(createLabel(new JLabel(isbn), bookFont), 130, 70, 215, 25));
			
			var textIntro = new JTextArea(intro);
			var textScroll = new JScrollPane(textIntro);
			
			textScroll.setBorder(blackBorder);
			textIntro.setLineWrap(true);
			
			var actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
			var spinnerModel = new SpinnerNumberModel(1, 1, 1, 1);
			var spinner = createComp(new JSpinner(spinnerModel), 50, 25);
			var btnCart = createButton("담기", e -> {
				
				if (cartSet.contains(id)) {
					eMsg("이미 추가된 도서입니다!", "확인");
					return;
				} 
				
				orderPanel.add(new OrderItem(id, name, (Integer)spinner.getValue()));
				updateOrderPanel();
			});
			
			if (stock == 0) {
				spinner.setEnabled(false);
				btnCart.setEnabled(false);
			} else {
				spinnerModel.setMaximum(stock);
			}
			
			spinner.setBorder(blackBorder);
			
			actionPanel.setOpaque(false);
			actionPanel.add(new JLabel(String.format("%,d원", price)));
			actionPanel.add(spinner);
			actionPanel.add(btnCart);
			
			add(createComp(textScroll, 130, 95, 215, 70));
			add(createComp(actionPanel, 130, 170, 215, 30));
			
			for (String tag : hashTag.split(",")) {
				var lbTag = createLabel(new JLabel(tag, 0), bookFont);
				
				lbTag.setForeground(Color.BLUE);
				lbTag.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
				lbTag.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						lbTag.setBorder(underlineBorder);
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						lbTag.setBorder(null);
					}
					
					public void mouseClicked(MouseEvent e) {
						cbSearchCategory.setSelectedIndex(2);
						tfSearch.setText(tag.substring(1));
						search();
					};
				});
				
				tagPanel.add(lbTag);
			}
		}
	}
	
	class OrderItem extends JPanel {
		int id;
		String name;
		int stock;
		
		OrderItem(int id, String name, int stock) {
			
			this.id = id;
			this.name = name;
			this.stock = stock;
			
			setLayout(new BorderLayout());
 			setBorder(blackBorder);
 			setPreferredSize(new Dimension(200, 30));
			
			var cp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			String txt = name.length() <= 10 ? name : name.substring(0, 10) + "...";
			
			cp.add(new JLabel(txt));
			cp.add(new JLabel(stock + "권"));
			
			cartSet.add(id);
			
			add(createButton("X", e -> {
				cartSet.remove(id);
				orderPanel.remove(OrderItem.this);
				updateOrderPanel();
			}), "East");
			add(cp);
		}
		
	}
	
	public SearchPage() {
		super(1350, 700, "도서검색");
		
		setLayout(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		var np = new JPanel();
		var sp = new JPanel();
		var cp = new JPanel(new BorderLayout());
		var ep = new JPanel(new BorderLayout(0, 5));
		var scroll = new JScrollPane(searchPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		var eScroll = new JScrollPane(orderPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll.setBorder(blackBorder);
		ep.setBorder(blackBorder);
		cp.add(scroll);
		np.setOpaque(false);
		sp.setOpaque(false);
		
		initCategory();
		
		ep.add(createLabel(new JLabel("- 구매 목록 -", 0), 20, Color.BLACK), "North");
		ep.add(eScroll);
		ep.add(createButton("구매 하기", e -> buy()), "South");
		
		np.add(cbSearchCategory);
		np.add(cbMainCategory);
		np.add(cbSubCategory);
		np.add(tfSearch);
		
		var btnSearch = createButton("검색", e -> search());
		
		btnSearch.setIcon(getIcon("./지급자료/icon_list/search_icon.png", 20, 20));
		
		np.add(btnSearch);
		sp.add(createButton("메인으로", e -> movePage(new MainPage())));
		
		add(np, "North");
		add(sp, "South");
		add(createComp(ep, 230, 0), "East");
		add(cp);
		
		search();
		initCombo();
		cbSearchCategory.addActionListener(e -> initCombo());
	}

	private void updateOrderPanel() {
		orderPanel.setPreferredSize(new Dimension(210, 40 * orderPanel.getComponentCount()));
		orderPanel.repaint();
		orderPanel.revalidate();
	}
	
	private void initCombo() {
		int id = cbSearchCategory.getSelectedIndex();
		
		cbMainCategory.setVisible(id == 1);
		cbSubCategory.setVisible(id == 1);
		
		tfSearch.setEditable(id != 1);
		if (id == 1) {
			tfSearch.setText("");			
		}
	}
	
	private void initCategory() {
		
		cbMainCategory.addActionListener(e -> {
			cbSubCategory.removeAllItems();
			cbSubCategory.addItem(new ComboItem(0, "전체"));
			
			int mId = ((ComboItem)cbMainCategory.getSelectedItem()).id;
			String sql = "SELECT * FROM sub_category";
			
			if (mId > 0) {
				sql += " WHERE main_category_id = " + mId;
			}
			
			try (var rsSub = stmt.executeQuery(sql)) {
				while (rsSub.next()) {
					cbSubCategory.addItem(new ComboItem(rsSub.getInt(1), rsSub.getString(3)));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		});
		
		cbMainCategory.addItem(new ComboItem(0, "전체"));
		
		try (var rsMain = stmt.executeQuery("SELECT * FROM main_category")) {
			while (rsMain.next()) {
				cbMainCategory.addItem(new ComboItem(rsMain.getInt(1), rsMain.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void search() {
		
		String sql = "SELECT b.id, b.image, b.sub_category_id, sc.main_category_id, b.name, stock, price, author, intro, numpages, isbn, created_by, hashtag FROM book b INNER JOIN sub_category sc ON b.sub_category_id = sc.id ";
		
		switch (cbSearchCategory.getSelectedIndex()) {
		case 0:
			sql += "WHERE b.name LIKE ? OR b.author LIKE ?";
			break;
		case 1:
			ComboItem sub = (ComboItem)cbSubCategory.getSelectedItem();
			ComboItem main = (ComboItem)cbMainCategory.getSelectedItem();
			
			if (sub.id != 0) {
				sql += "WHERE b.sub_category_id = " + sub.id;
			} else if (main.id != 0) {
				sql += "WHERE sc.main_category_id = " + main.id;
			}
			break;
		}
		
		try (var pst = con.prepareStatement(sql)) {
			
			if (cbSearchCategory.getSelectedIndex() == 0) {
				String key = "%" + tfSearch.getText() + "%";
				pst.setObject(1, key);
				pst.setObject(2, key);
			}
			
			var rs = pst.executeQuery();
			int count = 0;
			
			searchPanel.removeAll();
			
			while (rs.next()) {
				
				if (cbSearchCategory.getSelectedIndex() == 2) {
					String[] hashTag = rs.getString(13).split(",");
					boolean flag = false;
					
					for (String tag : hashTag) {
						if (tag.substring(1).contains(tfSearch.getText())) {
							flag = true;
							break;
						}
					}
					
					if (flag == false) {
						continue;
					}
				}
				
				var imgStream = rs.getBinaryStream(2);
				var icon = new ImageIcon(imgStream.readAllBytes());
				
				searchPanel.add(new BookItem(rs.getInt(1), 
						rs.getString(5), icon, rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11), rs.getDate(12), rs.getString(13)));
				
				count++;
			}
			
			int rows = count / 3;
			
			if (count % 3 != 0) {
				rows++;
			}
			
			searchPanel.setPreferredSize(new Dimension((BOOK_WIDTH + 10) * 3, rows * (BOOK_HEIGHT + 10)));
			searchPanel.repaint();
			searchPanel.revalidate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void buy() {
		
		if (cartSet.size() == 0) {
			eMsg("1권 이상의 도서를 구매해야 합니다.", "오류");
			return;
		}
		
		int count = 0;
		
		try (var pst = con.prepareStatement("INSERT INTO order_log VALUES(0, ?, ?, ?, now())");
				var pst2 = con.prepareStatement("UPDATE book SET stock = stock - ? WHERE id = ?")) {
			
			pst.setInt(2, memberNo);
			
			for (var comp : orderPanel.getComponents()) {
				var order = (OrderItem)comp;
				
				count += order.stock;
				
				pst.setInt(1, order.id);
				pst.setInt(3, order.stock);
				pst.executeUpdate();
				
				pst2.setInt(1, order.stock);
				pst2.setInt(2, order.id);
				pst2.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		orderPanel.removeAll();
		cartSet.clear();
		updateOrderPanel();
		
		iMsg("총 " + count + "권의 주문이 완료 되었습니다!", "구매 완료");
		search();
	}
	
	public static void main(String[] args) {
		setSession(10);
		movePage(new SearchPage());
		mainFrame.setVisible(true);
	}

}

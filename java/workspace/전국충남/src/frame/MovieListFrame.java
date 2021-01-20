package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import db.CM;

public class MovieListFrame extends BaseFrame {
	
	DefaultTreeModel tree_model;
	JTree tree;
	DefaultMutableTreeNode root;

	DefaultTableModel model = new DefaultTableModel("회원명,상영관,영화명,예약,예약,어른,청소년,소아,가격,인쇄".split(","),0);
	JTable table = new JTable(model);
	
	public MovieListFrame() {
		super(800, 500, "영화목록관리", 2);
		var tbSrc = setComp(new JScrollPane(), 500, 450);
		var bp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// define or create object nodes
		root = new DefaultMutableTreeNode("영화명");
		tree_model = new DefaultTreeModel(root);
		tree = new JTree(tree_model);
		var treeSrc = setComp(new JScrollPane(tree), 250, 450);
		

		
		tree.addTreeSelectionListener(new SelectionListener());
		
		try (var rs = CM.stmt.executeQuery("select * from movie;")){
			while(rs.next()) {
				root.add(new DefaultMutableTreeNode(rs.getString(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		bp.add(setBtn("jpg내보내기", e -> jpg()));
		bp.add(setBtn("리스트 지우기", e -> table.removeAll()));
		bp.add(setBtn("닫기", e->dispose()));
		
		add(treeSrc,BorderLayout.WEST);
		add(tbSrc);
		add(bp,BorderLayout.SOUTH);
	}
	
	public void jpg() {
		
	}

	public static void main(String[] args) {
		new MovieListFrame().setVisible(true);
	}

	DefaultMutableTreeNode selected_node;

	// code this outside the constructor
	class SelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent se) {
			// determines the selected node
			selected_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			String name = selected_node.toString();

			if (selected_node.isLeaf()) {
				setTable(name);
			}
		}
	}
	
	public void setTable(String name) {
		try (var pst = CM.con.prepareStatement("select * from movie where M_name = ?")){
			
		} catch (Exception e) {
		}
	}
}

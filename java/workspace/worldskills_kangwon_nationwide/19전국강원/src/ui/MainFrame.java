package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static ui.PageBase.*;
import ui.PageBase;

public class MainFrame extends JFrame {
    ArrayList<PageBase> pageList = new ArrayList<PageBase>();
    JButton prev = new JButton("뒤로", getIcon("./지급자료/icon_list/previous_icon.png", 20, 20));
    JButton next = new JButton("앞으로", getIcon("./지급자료/icon_list/next_icon.png", 20, 20));
    JPanel np = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    JPanel c = new JPanel(null);
    
    int pageIndex = -1;
    
    public void updateNavigator() {
        np.removeAll();
        
        np.add(prev);
        np.add(next);
        
        prev.setEnabled(pageIndex > 0);
        next.setEnabled(pageIndex < (pageList.size() - 1));
        
        for (int i = 0; i < pageList.size(); i++) {
            np.add(createLabel(new JLabel(pageList.get(i).text), 11, i == pageIndex ? Color.BLUE : Color.black ));
            
            if (i != pageList.size() - 1) {
                np.add(new JLabel(">"));
            }
            
        }
        
        c.removeAll();
        
        if (pageIndex >= 0) {
        	c.add(pageList.get(pageIndex));        	
        }
        np.repaint();
        np.revalidate();
        c.repaint();
        
        revalidate();
    }
    
    public void clearHistory() {
    	pageList.clear();
    	pageIndex = -1;
    	
    	updateNavigator();
    }
    
    @Override
    public void layout() {
        super.layout();
        
        if (pageIndex > -1) {
            var page = pageList.get(pageIndex);
            
            page.setLocation((c.getWidth() - page.getWidth()) / 2, (c.getHeight() - page.getHeight()) / 2);
        }
    }
    
    public MainFrame() {
        setSize(1500, 850);
        setTitle("자바문고");
        setDefaultCloseOperation(2);
        setIconImage(getToolkit().getImage("./지급자료/icon_list/main_icon.png"));
        setLocationRelativeTo(null);
        
        prev.addActionListener(e -> changePage(pageIndex - 1));
        next.addActionListener(e -> changePage(pageIndex + 1));
        
        prev.setEnabled(false);
        next.setEnabled(false);
        
        np.setBackground(Color.WHITE);
        np.add(prev);
        np.add(next);
        
        add(np, "North");
        add(c);
        
        addWindowStateListener(new WindowStateListener() {
            
            @Override
            public void windowStateChanged(WindowEvent e) {
                revalidate();
            }
        });
        
    }
    
    private void changePage(int ind) {
        pageIndex = ind;
        
        updateNavigator();
    }
    
    public void addPage(PageBase page) {
        if (pageList.size() == 5) {
            pageList.remove(0);
        }
        
        pageList.add(page);
        pageIndex = pageList.size() - 1;
        
        updateNavigator();
    }
    
    public static void main(String[] args) {
    	mainFrame.addPage(new LoginPage());
        mainFrame.setVisible(true);
    }
}
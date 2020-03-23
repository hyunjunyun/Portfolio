package com;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

public class MenuBuilderExampleMain {
    public static void main(String[] args) {
        setFonts();
        //to display menu selection
        JLabel selectionLabel = new JLabel();
        //creating menu bar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("File");//just an empty menu
        jMenuBar.add(menu);
        menu = buildExampleMenu(selectionLabel);
        jMenuBar.add(menu);

        JFrame frame = createFrame();
        frame.setLayout(new GridBagLayout());
        frame.add(selectionLabel);
        frame.setJMenuBar(jMenuBar);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JMenu buildExampleMenu(JLabel selectionLabel) {
        //just add some transparency
        Color color = UIManager.getColor("MenuItem.selectionBackground");
        UIManager.put("MenuItem.selectionBackground",
                new Color(color.getRed(), color.getGreen(), color.getBlue(), 135));

        //selecting a menu will update the JLabel
        ActionListener al = (ae) -> selectionLabel.setText(ae.getActionCommand());

        //building menu capable for searching
        JMenu exampleMenu = new JMenu("Example Menu");
        exampleMenu.setMnemonic(KeyEvent.VK_E);
        for (int i = 0; i < 30; i++) {
            //menu item must be HighlightedMenuItem subclass of JMenuItem
            HighlightedMenuItem menuItem = new HighlightedMenuItem();
            menuItem.setText(RandomUtil.getFullName());
            menuItem.addActionListener(al);
            exampleMenu.add(menuItem);
        }
        MenuSearchDecorator.decorate(exampleMenu);
        return exampleMenu;
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Menu Search Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 400));
        return frame;
    }

    private static void setFonts() {
        System.setProperty("swing.aatext", "true");
        System.setProperty("swing.plaf.metal.controlFont", "Tahoma-14");
        System.setProperty("swing.plaf.metal.userFont", "Tahoma-14");
    }
}
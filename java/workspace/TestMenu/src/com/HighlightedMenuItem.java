package com;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//very less code after moving logic up the hierarchy.
//also this design demonstrates how to do multiple
//inheritance by using Java 8 default methods
public class HighlightedMenuItem extends JMenuItem implements HighlightedComponent {
    private List<Rectangle2D> rectangles = new ArrayList<>();

    public HighlightedMenuItem() {
        setOpaque(false);
        //try with icon:
        //setIcon(UIManager.getIcon("OptionPane.questionIcon"));
    }

    @Override
    public Collection<Rectangle2D> getHighlightedRectangles() {
        return rectangles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        doHighlightPainting(g);
        super.paintComponent(g);

    }
}
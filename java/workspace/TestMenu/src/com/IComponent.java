package com;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

//interface representing a JComponent
public interface IComponent {

    FontMetrics getFontMetrics(Font f);

    Font getFont();

    int getHeight();

    int getWidth();

    Color getBackground();

    boolean isOpaque();

    Icon getIcon();

    Border getBorder();

    void repaint();
}
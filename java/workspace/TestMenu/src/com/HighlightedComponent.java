package com;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

//using Java 8 default methods to reuse the same logic for different components
public interface HighlightedComponent extends IText {

    public static Color colorHighlight = new Color(220, 220, 50);

    default void reset() {
        getHighlightedRectangles().clear();
        repaint();
    }

    default boolean highlightText(String textToHighlight) {
        if (textToHighlight == null) {
            return false;
        }
        reset();

        final String textToMatch = textToHighlight.toLowerCase().trim();
        if (textToMatch.length() == 0) {
            return false;
        }
        textToHighlight = textToHighlight.trim();

        final String labelText = getText().toLowerCase();
        if (labelText.contains(textToMatch)) {
            FontMetrics fm = getFontMetrics(getFont());
            float w = -1;
            final float h = fm.getHeight() - 1;
            int i = 0;
            while (true) {
                i = labelText.indexOf(textToMatch, i);
                if (i == -1) {
                    break;
                }
                if (w == -1) {
                    String matchingText = getText().substring(i,
                            i + textToHighlight.length());
                    w = fm.stringWidth(matchingText);
                }
                String preText = getText().substring(0, i);
                float x = fm.stringWidth(preText);
                int y = 0;

                //taking care of margins if there's border
                if (getBorder() != null) {
                    Insets borderInsets = getBorder().getBorderInsets((Component) this);
                    if (borderInsets != null) {
                        x += borderInsets.left;
                        y += borderInsets.top;
                    }
                }
                //taking care of margin if there's icon
                if (getIcon() != null) {//assuming LEFT_TO_RIGHT orientation
                    x += getIcon().getIconWidth();
                    if (getIcon().getIconHeight() > fm.getHeight()) {
                        y += 1 + (getIcon().getIconHeight() - fm.getHeight()) / 2;//vertical middle
                    }
                }

                //taking care of left margin for icon-text-gap
                int gap = getIconTextGap();
                //gap is on both sides of the icon
                x += getIcon() != null ? gap * 2 : gap;


                getHighlightedRectangles().add(new Rectangle2D.Float(x, y, w, h));
                i = i + textToMatch.length();
            }
            repaint();
            return true;
        }
        return false;
    }

    //to be called from subclass's paintComponent(g)
    default void doHighlightPainting(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (getHighlightedRectangles().size() > 0) {

            Graphics2D g2d = (Graphics2D) g;
            Color c = g2d.getColor();
            for (Rectangle2D rectangle : getHighlightedRectangles()) {
                g2d.setColor(colorHighlight);
                g2d.fill(rectangle);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.draw(rectangle);
            }
            g2d.setColor(c);
        }
    }

    //since interfaces cannot have instance variables ask subclass to provide one
    Collection<Rectangle2D> getHighlightedRectangles();
}
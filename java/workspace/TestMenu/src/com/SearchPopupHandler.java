package com;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Predicate;

//A reusable search handler which shows a search popup and
// uses in memory key inputs
public class SearchPopupHandler {
    private Popup searchPopup;
    private JLabel searchLabel;
    private JComponent userSearchComponent;
    private Predicate<String> userSearchHandler;
    private TextHandler textHandler = new TextHandler();
    private boolean hasMatches;

    public SearchPopupHandler(JComponent userSearchComponent, Predicate<String> userSearchHandler) {
        this.userSearchComponent = userSearchComponent;
        this.userSearchHandler = userSearchHandler;
    }

    public void init() {
        initSearchLabel();
    }

    //handles stuff related to popup text component
    public void handleKeyPressedEvent(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (!Character.isDefined(keyChar)) {
            return;
        }
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_DELETE:
                return;
            case KeyEvent.VK_ENTER:
                resetSearchPopup();
                return;
            case KeyEvent.VK_ESCAPE:
                if (resetSearchPopup()) {
                    e.consume();
                }
                return;
            case KeyEvent.VK_BACK_SPACE:
                textHandler.removeCharAtEnd();
                break;
            default:
                textHandler.add(keyChar);
        }

        if (!textHandler.text.isEmpty()) {
            showSearchPopup();
            performSearch();
        } else {
            resetSearchPopup();
        }
        e.consume();
    }

    public String getSearchText() {
        return searchLabel.getText();
    }

    private void initSearchLabel() {
        searchLabel = new JLabel();
        searchLabel.setOpaque(true);
        searchLabel.setFont(searchLabel.getFont().deriveFont(Font.PLAIN)
                                       .deriveFont(userSearchComponent.getFont().getSize()));
        searchLabel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.gray),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
    }

    private void showSearchPopup() {
        if (textHandler.getText().isEmpty()) {
            return;
        }
        if (searchPopup == null) {
            Point p = new Point(0, 0);
            SwingUtilities.convertPointToScreen(p, userSearchComponent);
            Dimension comboSize = userSearchComponent.getPreferredSize();
            int height = searchLabel.getFontMetrics(searchLabel.getFont()).getHeight();
            Insets borderInsets = searchLabel.getBorder().getBorderInsets(searchLabel);
            height += borderInsets.top + borderInsets.bottom;
            searchLabel.setPreferredSize(new Dimension(comboSize.width, height));
            searchPopup = PopupFactory.getSharedInstance().getPopup(userSearchComponent, searchLabel, p.x,
                    p.y - height);
        }
        searchPopup.show();
    }

    public boolean resetSearchPopup() {
        if (!textHandler.isEditing()) {
            return false;
        }
        if (searchPopup != null) {
            searchPopup.hide();
            searchPopup = null;
            searchLabel.setText("");
            textHandler.reset();
            userSearchHandler.test("");
            return true;
        }
        return false;
    }

    private void performSearch() {
        searchLabel.setText(textHandler.getText());
        if (userSearchHandler.test(textHandler.getText())) {
            searchLabel.setForeground(Color.blue);
            hasMatches = true;
        } else {
            //if no match then red font
            searchLabel.setForeground(Color.red);
            hasMatches = false;
        }
    }

    public boolean hasMatches() {
        return hasMatches;
    }

    private static class TextHandler {
        private String text = "";
        private boolean editing;

        public void add(char c) {
            text += c;
            editing = true;
        }

        public void removeCharAtEnd() {
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                editing = true;
            }
        }

        public void reset() {
            text = "";
            editing = false;
        }

        public String getText() {
            return text;
        }

        public boolean isEditing() {
            return editing;
        }
    }
}
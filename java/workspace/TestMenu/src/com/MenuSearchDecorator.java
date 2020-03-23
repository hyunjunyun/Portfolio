package com;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuSearchDecorator {
    private JMenu[] menus;

    public MenuSearchDecorator(JMenu[] menus) {
        this.menus = menus;
    }

    //the provided menus must add HighlightedMenuItem
    public static void decorate(JMenu... menus) {
        if (menus == null) {
            throw new IllegalArgumentException("menus cannot be null");
        }
        new MenuSearchDecorator(menus).init();
    }

    private void init() {
        for (JMenu menu : menus) {
            SearchPopupHandler searchPopupHandler =
                    new SearchPopupHandler(menu.getPopupMenu(), text -> performSearch(text, menu));
            searchPopupHandler.init();
            menu.addMenuKeyListener(createKeyListener(menu, searchPopupHandler));
            menu.getPopupMenu().addPopupMenuListener(createPopupMenuListener(menu, searchPopupHandler));
        }
    }

    private PopupMenuListener createPopupMenuListener(JMenu menu, SearchPopupHandler searchPopupHandler) {
        return new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                searchPopupHandler.resetSearchPopup();

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        };
    }

    private MenuKeyListener createKeyListener(JMenu menu, SearchPopupHandler searchPopupHandler) {

        return new MenuKeyListener() {

            @Override
            public void menuKeyTyped(MenuKeyEvent e) {
            }

            @Override
            public void menuKeyPressed(MenuKeyEvent e) {
                KeyEvent ke = new KeyEvent(menu, e.getID(),
                        e.getWhen(),
                        e.getModifiersEx(),
                        e.getKeyCode(),
                        e.getKeyChar(),
                        e.getKeyLocation());
                searchPopupHandler.handleKeyPressedEvent(ke);
                if (ke.isConsumed()) {
                    e.consume();
                    return;
                }

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
                    if (searchPopupHandler.hasMatches()) {
                        //if there're matches then up/down keys will jump to previous/next match
                        // this feature is similar to intellij project-explorer/file-dialog search
                        if (jumpToOtherMatch(menu, searchPopupHandler.getSearchText(), keyCode == KeyEvent.VK_DOWN)) {
                            e.consume();
                        }
                    }
                }
            }

            @Override
            public void menuKeyReleased(MenuKeyEvent e) {
            }
        };
    }

    private boolean jumpToOtherMatch(JMenu menu, String searchText, boolean forward) {
        MenuElement last = JMenuUtil.getCurrentSelection();

        if (last instanceof HighlightedMenuItem) {
            List<Component> menuComponents = new ArrayList<>(List.of(menu.getMenuComponents()));
            if (!forward) {
                //just reverse it, instead of doing reversed loop
                Collections.reverse(menuComponents);
            }
            int currentIndex = menuComponents.indexOf(last);

            int size = menuComponents.size();
            //find next match
            for (int i = currentIndex + 1; i < size; i++) {
                Component component = menuComponents.get(i);
                if (component instanceof HighlightedMenuItem) {
                    if (((HighlightedMenuItem) component).highlightText(searchText)) {
                        JMenuUtil.setCurrentSiblingSelection((MenuElement) component);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean performSearch(String text, JMenu menu) {
        boolean match = false;
        for (Component menuComponent : menu.getMenuComponents()) {
            if (menuComponent instanceof HighlightedMenuItem) {
                match |= ((HighlightedMenuItem) menuComponent).highlightText(text);
            }
        }
        return match;
    }
}
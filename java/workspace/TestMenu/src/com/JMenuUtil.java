package com;


import javax.swing.*;

public class JMenuUtil {

    public static MenuElement getCurrentSelection() {
        MenuSelectionManager manager = MenuSelectionManager.defaultManager();
        MenuElement[] selectedPath = manager.getSelectedPath();
        MenuElement lastSelected = selectedPath[selectedPath.length - 1];
        return lastSelected;
    }

    public static void setCurrentSiblingSelection(MenuElement component) {
        MenuSelectionManager manager = MenuSelectionManager.defaultManager();
        MenuElement[] selectedPath = manager.getSelectedPath();
        //getSelectedPath() above, always returns new, not a reference, so we can modify it
        selectedPath[selectedPath.length - 1] = component;
        manager.setSelectedPath(selectedPath);
    }
}
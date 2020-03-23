package com;

//an interface representing component which
// has text, e.g. JTextComponent, JLabel, JButton, JMenuItem etc
public interface IText extends IComponent{

    int getIconTextGap();

    String getText();
}
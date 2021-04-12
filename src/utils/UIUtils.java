package utils;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    private UIUtils(){}

    public static JMenuItem setMenuItemStyle(JMenuItem item){
        item.setBorder(BorderFactory.createEmptyBorder());
        item.setForeground(new Color(222, 222, 222));
        item.setBackground(new Color(24, 22, 22));
        item.setFont(FontUtils.loadFont(18f));
        return item;
    }

    public static JMenuItem setPopupMenuItemStyle(JMenuItem item) {
        item.setBorder(BorderFactory.createEmptyBorder());
        item.setForeground(new Color(255, 141, 0));
        item.setBackground(new Color(24, 22, 22));
        item.setFont(FontUtils.loadFont(18f));
        return item;
    }

}

package utils;

import commons.StaticConstants;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class FontUtils {
    private FontUtils(){};

    public static Font loadFont(float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, FontUtils.class.getResourceAsStream("/res/consola.ttf")).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {

        }
        return null;
    }
}

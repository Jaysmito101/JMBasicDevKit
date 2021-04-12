import commons.StaticConstants;
import ide.UI;
import utils.LoaderUtils;
import utils.StringUtils;

import java.util.jar.JarFile;

public class Main {
    public static void main(String[] args) {
        LoaderUtils.loadJars();
        UI ui = UI.getUI();
        ui.displayUI();
    }
}

package utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotUtils {
    private RobotUtils(){}

    public static boolean pressAndRelease(int keyCode){
        try{
            Robot robot = new Robot();
            robot.keyPress(keyCode);
            robot.delay(100);
            robot.keyRelease(keyCode);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public static boolean hitDownKey(){
        return pressAndRelease(KeyEvent.VK_DOWN);
    }

}

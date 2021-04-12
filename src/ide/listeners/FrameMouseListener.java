package ide.listeners;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class FrameMouseListener extends MouseAdapter {

    private JFrame frame;
    private JTextPane textPane;

    public FrameMouseListener(JFrame frame, JTextPane editorPane){
        this.frame = frame;
        this.textPane = editorPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("HI");

        if(!textPane.hasFocus() || !frame.hasFocus()){
            frame.requestFocusInWindow();
            frame.requestFocus();
            textPane.requestFocus();
            textPane.requestFocusInWindow();
        }
    }
}

package ide.listeners;

import ide.autocomplete.AutoCompleteManager;
import ide.projectmanager.ProjectManager;
import utils.RobotUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UIKeyListener extends KeyAdapter {
    private final ProjectManager projectManager;
    private JTextPane editorPane;
    private AutoCompleteManager autoCompleteManager;

    public UIKeyListener(JTextPane editorPane, AutoCompleteManager autoCompleteManager, ProjectManager projectManager){
        super();
        this.autoCompleteManager = autoCompleteManager;
        this.editorPane = editorPane;
        this.projectManager = projectManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.projectManager.updateCode(editorPane.getText());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && autoCompleteManager.isVisible()){
            autoCompleteManager.hide();
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN && autoCompleteManager.isVisible()){
            autoCompleteManager.setFocus();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        autoCompleteManager.applyAutoComplete(e);
    }
}

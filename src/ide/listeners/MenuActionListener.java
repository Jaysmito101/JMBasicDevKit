package ide.listeners;

import commons.StaticConstants;
import ide.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class MenuActionListener implements ActionListener {
    private UI frame;

    public  MenuActionListener(UI frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case "Open Project":{
                frame.showOpenProjectFileChooser();
                break;
            }
            case "Close Project":{
                frame.closeProject();
                break;
            }
            case "Run Code":{
                frame.runCode();
                break;
            }
            case "Build Project":{
                notImplemented();
                break;
            }
            case "Manual":{
                notImplemented();
                break;
            }
            case "About":{
                showAbout();
                break;
            }
            case "Open Website":{
                openWebsite();
                break;
            }
            case "Copyright":{
                showCopyright();
                break;
            }
            case "Create Project":{
                frame.showCreateProjectFileChooser();
                break;
            }
            case "Save Project":{
                frame.saveProject();
                break;
            }
            case "Launch Interpreter":{
                frame.launchInterpreter();
                break;
            }
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(frame, "This Application has been made by\nJaysmito Mukherjee\n\nThis is meant to be a\nComplete Development Kit\nfor the programming language\nJMBASIC which is also developed by\nJaysmito Mukherjee!", "Jaysmito Mukherjee", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openWebsite() {
        if(JOptionPane.showConfirmDialog(frame, "Open Website?", "Confirmation", JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try {
                Desktop.getDesktop().browse(new URI(StaticConstants.WEBSITE_URL));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(frame, "Failed to open website!\n\nPlease restart the application.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showCopyright() {
        JOptionPane.showMessageDialog(frame, StaticConstants.LICENSE, "Copyright", JOptionPane.INFORMATION_MESSAGE);
    }

    private void notImplemented() {
        JOptionPane.showMessageDialog(frame, "This feature has not yet been implemented!", "Not Implemented", JOptionPane.INFORMATION_MESSAGE);
    }
}

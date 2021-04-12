package ide.listeners;

import ide.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OpenProjectFileChooserActionListener implements ActionListener {
    private UI ui;

    public OpenProjectFileChooserActionListener(UI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case "ApproveSelection":{
                ui.openProject();
                break;
            }
            case "CancelSelection":{
                break;
            }
        }
    }
}

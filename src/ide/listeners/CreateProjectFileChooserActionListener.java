package ide.listeners;

import ide.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CreateProjectFileChooserActionListener implements ActionListener {
    private UI ui;

    public CreateProjectFileChooserActionListener(UI ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case "ApproveSelection":{
                ui.createProject();
                break;
            }
            case "CancelSelection":{
                break;
            }
        }
    }
}

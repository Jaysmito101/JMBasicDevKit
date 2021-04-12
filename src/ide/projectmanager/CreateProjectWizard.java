package ide.projectmanager;

import utils.FontUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateProjectWizard extends JPanel {

    public JTextField name, packageName, authorName, version;

    public CreateProjectWizard(){
        setLayout(new GridLayout(6, 2, 5, 5));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(9, 8, 8));
        JLabel tmp = new JLabel("Create");
        setStyle(tmp);
        add(tmp);
        JLabel tmp2 = new JLabel("Project");
        setStyle(tmp2);
        add(tmp2);
        add(setStyle(new JLabel(" ")));
        add(setStyle(new JLabel(" ")));
        add(setStyle(new JLabel("Project Name")));
        name = new JTextField();
        setStyle(name);
        add(name);
        add(setStyle(new JLabel("Package Name")));
        packageName = new JTextField();
        setStyle(packageName);
        add(packageName);
        add(setStyle(new JLabel("Author Name")));
        authorName = new JTextField();
        setStyle(authorName);
        add(authorName);
        add(setStyle(new JLabel("Project Version")));
        version = new JTextField();
        setStyle(version);
        add(version);
    }

    private static JComponent setStyle(JComponent component){
        component.setBorder(new EmptyBorder(2, 2, 2, 2));
        component.setBackground(new Color(24, 22, 22));
        component.setForeground(new Color(222, 222, 222));
        component.setFont(FontUtils.loadFont(18f));
        return component;
    }
}

package ide;


import commons.StaticConstants;
import ide.autocomplete.AutoCompleteManager;
import ide.listeners.*;
import ide.projectmanager.ProjectManager;
import ide.styling.JMBasicStylingDocument;
import jmbasic.interpreter.Interpreter;
import utils.FontUtils;
import utils.StringUtils;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public class UI extends JFrame {

    private UIKeyListener keyListener;
    private FrameMouseListener frameMouseListener;
    private MenuActionListener menuActionListener;
    private JTextPane editorPane;
    private JMBasicStylingDocument jmBasicStylingDocument;
    private AutoCompleteManager autoCompleteManager;
    private JFileChooser fileChooser;
    private boolean isMessageBeingShown;

    private static UI instance;
    private ProjectManager projectManager;

    public static UI getUI() {
        if (instance == null)
            instance = new UI();
        return instance;
    }


    private UI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        UIManager.put("OptionPane.background", new Color(9, 8, 8));
        UIManager.put("Panel.background", new Color(9, 8, 8));
        UIManager.put("Button.background", new Color(64, 54, 54));
        UIManager.put("Button.foreground", new Color(222, 222, 222));
        UIManager.put("ComboBox.background", new Color(64, 54, 54));
        UIManager.put("ComboBox.foreground", new Color(222, 222, 222));
        UIManager.put("TextField.background", new Color(64, 54, 54));
        UIManager.put("TextField.foreground", new Color(222, 222, 222));
        UIManager.put("Spinner.background", new Color(9, 8, 8));
        UIManager.put("Spinner.foreground", new Color(222, 222, 222));
        UIManager.put("SplitPane.background", new Color(9, 8, 8));
        UIManager.put("TabbedPane.background", new Color(9, 8, 8));
        UIManager.put("ScrollPane.background", new Color(9, 8, 8));
        jmBasicStylingDocument = new JMBasicStylingDocument();
        editorPane = new JTextPane(jmBasicStylingDocument);
        editorPane.setBackground(new Color(24, 22, 22));
        editorPane.setFont(FontUtils.loadFont(22f));
        editorPane.setCaretColor(new Color(222, 222, 222));
        editorPane.setForeground(new Color(222, 222, 222));
        editorPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        editorPane.getCaret().setBlinkRate(0);
        autoCompleteManager = new AutoCompleteManager(editorPane, this);
        loadMenu();
        initializeProjectManager();
        setBindings();
        this.keyListener = new UIKeyListener(editorPane, autoCompleteManager, projectManager);
        editorPane.addKeyListener(keyListener);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frameMouseListener = new FrameMouseListener(this, editorPane);
        this.addMouseListener(frameMouseListener);
        this.isMessageBeingShown = false;
        this.setLayout(new GridLayout(1, 1, 0, 0));
        this.add(scrollPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(StaticConstants.DEFAULT_IDE_TITLE);
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
    }

    private void setBindings() {
        InputMap iMap = editorPane.getInputMap();
        ActionMap aMap = editorPane.getActionMap();
        // SAVE SHORT CUT
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "ctrlS");
        aMap.put("ctrlS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProject();
            }
        });
        //
    }

    private void initializeProjectManager() {
        this.projectManager = new ProjectManager();
        if(!this.projectManager.isProjectOpen()){
            editorPane.setEnabled(false);
            editorPane.setText("Please create or open a project!");
        }
    }

    private void setKeyBinding(int keyCode, AbstractAction action) {
        int modifier = 0;
        switch (keyCode) {
            case KeyEvent.VK_CONTROL:
                modifier = InputEvent.CTRL_DOWN_MASK;
                break;
            case KeyEvent.VK_SHIFT:
                modifier = InputEvent.SHIFT_DOWN_MASK;
                break;
            case KeyEvent.VK_ALT:
                modifier = InputEvent.ALT_DOWN_MASK;
                break;

        }

        editorPane.getInputMap().put(KeyStroke.getKeyStroke(keyCode, modifier), keyCode);
        editorPane.getActionMap().put(keyCode, action);
    }

    private void loadMenu() {
        menuActionListener = new MenuActionListener(this);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(24, 22, 22));
        menuBar.setPreferredSize(new Dimension((int)menuBar.getPreferredSize().getWidth(), 25));

        // FILE
        JMenu file = new JMenu("File");
        UIUtils.setMenuItemStyle(file);
        JMenuItem saveProject = new JMenuItem("Save Project");
        UIUtils.setMenuItemStyle(saveProject);
        saveProject.addActionListener(menuActionListener);
        file.add(saveProject);
        JMenuItem create = new JMenuItem("Create Project");
        UIUtils.setMenuItemStyle(create);
        create.addActionListener(menuActionListener);
        file.add(create);
        JMenuItem open = new JMenuItem("Open Project");
        UIUtils.setMenuItemStyle(open);
        open.addActionListener(menuActionListener);
        file.add(open);
        JMenuItem closeProject = new JMenuItem("Close Project");
        UIUtils.setMenuItemStyle(closeProject);
        closeProject.addActionListener(menuActionListener);
        file.add(closeProject);
        JMenuItem exit = new JMenuItem("Exit");
        UIUtils.setMenuItemStyle(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        file.add(exit);
        menuBar.add(file);
        // RUN CODE
        JMenu run = new JMenu("Run");
        UIUtils.setMenuItemStyle(run);
        JMenuItem launchInterpreter = new JMenuItem("Launch Interpreter");
        UIUtils.setMenuItemStyle(launchInterpreter);
        launchInterpreter.addActionListener(menuActionListener);
        run.add(launchInterpreter);
        JMenuItem runCode = new JMenuItem("Run Code");
        UIUtils.setMenuItemStyle(runCode);
        runCode.addActionListener(menuActionListener);
        run.add(runCode);
        JMenuItem buildProject = new JMenuItem("Build Project");
        UIUtils.setMenuItemStyle(buildProject);
        buildProject.addActionListener(menuActionListener);
        run.add(buildProject);
        menuBar.add(run);
        // HELP
        JMenu abou = new JMenu("Help");
        UIUtils.setMenuItemStyle(abou);
        JMenuItem manual = new JMenuItem("Manual");
        UIUtils.setMenuItemStyle(manual);
        manual.addActionListener(menuActionListener);
        abou.add(manual);
        JMenuItem about = new JMenuItem("About");
        UIUtils.setMenuItemStyle(about);
        about.addActionListener(menuActionListener);
        abou.add(about);
        JMenuItem website = new JMenuItem("Open Website");
        UIUtils.setMenuItemStyle(website);
        website.addActionListener(menuActionListener);
        JMenuItem copyright = new JMenuItem("Copyright");
        UIUtils.setMenuItemStyle(copyright);
        copyright.addActionListener(menuActionListener);
        abou.add(copyright);
        abou.add(website);
        menuBar.add(abou);
        setJMenuBar(menuBar);
    }

    public void displayUI() {
        this.setVisible(true);
    }

    public void openProject(){
        if(!projectManager.openProject(fileChooser.getSelectedFile().getAbsolutePath())){
            JOptionPane.showMessageDialog(this, "Failed to Open Project", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            editorPane.setText(projectManager.getOpenedProject().getCode());
            editorPane.setEnabled(true);
            this.setTitle("[" + projectManager.getOpenedProject().getProperty("name") + "]" + StaticConstants.DEFAULT_IDE_TITLE);
            showMessage("Opened", 500);
        }
    }

    public void launchInterpreter(){
        try {
            if(System.getProperty("os.name").equals("Linux")) {
                File jarFile = File.createTempFile(StringUtils.generateRandomString(10), ".jar");
                OutputStream os = new FileOutputStream(jarFile);
                os.write(getClass().getResourceAsStream("/res/JMBASIC.jar").readAllBytes());
                os.close();
                String jarFileName = jarFile.getAbsolutePath();
                String command = "xterm -e java -jar " + jarFileName ;
                Runtime.getRuntime().exec(command.split(" "));
            }else{
                JOptionPane.showMessageDialog(this, "Live Interpreter is still not supported on Windows", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to start Interpreter\n\nPossible Resolutions:-\nInstall xterm\nInstall java\nSet java to path", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runCode(){
        try {
            clearScreen();
            Interpreter interpreter = Interpreter.createInterpreter(System.out, System.err, System.in);
            showMessage("Check terminal for output", 1000);
            if(!interpreter.exec(StringUtils.getAsStream(editorPane.getText())))
                showMessage("Error in code", 1000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void clearScreen(){
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveProject(){
        if(!projectManager.isProjectOpen()){
            JOptionPane.showMessageDialog(this, "First Create or Open a Project to Save", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!projectManager.saveProject()){
            JOptionPane.showMessageDialog(this, "Failed to Save Project", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            showMessage("Saved", 500);
        }
    }

    public void showCreateProjectFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose directory to create Project");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("Create Project");
        fileChooser.addActionListener(new CreateProjectFileChooserActionListener(this));
        fileChooser.showOpenDialog(this);
    }

    public void showOpenProjectFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose directory to open Project");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("Open Project");
        fileChooser.addActionListener(new OpenProjectFileChooserActionListener(this));
        fileChooser.showOpenDialog(this);
    }

    public void createProject(){
        if(!projectManager.createProject(fileChooser.getSelectedFile().getAbsolutePath())){
            JOptionPane.showMessageDialog(this, "Failed to Create Project", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            editorPane.setText("");
            editorPane.setEnabled(true);
            showMessage("Created " + projectManager.getOpenedProject().getProperty("name"), 500);
            this.setTitle("[" + projectManager.getOpenedProject().getProperty("name") + "]" + StaticConstants.DEFAULT_IDE_TITLE);
        }
    }


    public void closeProject(){
        if(!this.projectManager.isProjectOpen()){
            JOptionPane.showMessageDialog(this, "First Create or Open a Project to Close", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            this.projectManager.closeProject();
            if (!this.projectManager.isProjectOpen()) {
                editorPane.setEnabled(false);
                editorPane.setText("Please create or open a project!");
                this.setTitle(StaticConstants.DEFAULT_IDE_TITLE);
                showMessage("Closed", 500);
            }
        }
    }

    public synchronized void showMessage(String message, int duration){
        if(isMessageBeingShown)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isMessageBeingShown = true;
                JPopupMenu popupMenu = new JPopupMenu();
                JLabel label = new JLabel(message);
                label.setFont(FontUtils.loadFont(18));
                label.setForeground(new Color(222, 222, 222));
                label.setBorder(null);
                label.setBorder(new EmptyBorder(5, 5, 5, 5));
                popupMenu.add(label);
                popupMenu.setBackground(new Color(9, 8, 8));
                popupMenu.show(editorPane, editorPane.getWidth() - label.getWidth() - 100, 10);
                isMessageBeingShown = false;
            }
        }).start();
    }
}

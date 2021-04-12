package ide.projectmanager;

import ide.filehandling.FileHandler;
import utils.StringUtils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectManager {

    private Project openProject;

    public boolean isProjectOpen() {
        return isProjectOpen;
    }

    public void setProjectOpen(boolean projectOpen) {
        isProjectOpen = projectOpen;
    }

    private boolean isProjectOpen;

    public Project getOpenedProject() {
        return openProject;
    }

    public void setOpenedProject(Project openProject) {
        this.openProject = openProject;
    }

    @Override
    public String toString() {
        return "ProjectManager{" +
                "openProject=" + openProject +
                ", isProjectOpen=" + isProjectOpen +
                '}';
    }


    public ProjectManager() {
        this.isProjectOpen = false;
    }

    public boolean openProject(String path){
        try {
            return loadProject(new File(path, "project.jmbasicproj"));
        }catch (Exception ex){
            return false;
        }
    }

    private boolean loadProject(File projectFile) {
        if(!projectFile.exists() || !projectFile.isFile() || !projectFile.canRead() || !projectFile.canWrite())
            return false;
        String projectContent = FileHandler.loadFile(projectFile);
        if(projectContent == null)
            return false;
        Scanner scanner = new Scanner(projectContent);
        if (!scanner.nextLine().equals("JMBASIC.PROJECT.FILE"))
            return false;
        if (!scanner.nextLine().equals("@DEVELOPER_JAYSMITO_MUKHERJEE"))
            return false;
        String tmp;
        int numProperties = 0;
        try {
            if ((tmp = scanner.nextLine()).startsWith("PROPERTIES_")) {
                numProperties = Integer.parseInt(tmp.substring(11));
            }else
                return false;
        }catch (NumberFormatException nmfe){
            return false;
        }
        if(numProperties <= 0)
            return false;
        openProject = new Project();
        for (int i=0;i<numProperties;i++){
            if (!scanner.hasNextLine())
                return false;
            tmp = scanner.nextLine();
            if(tmp.length() == 0)
                continue;
            openProject.addProperty(tmp.substring(0, tmp.indexOf("\t")), tmp.substring(tmp.indexOf("\t")+1));
        }
        if (!scanner.nextLine().equals("PROPERTIES_END"))
            return false;
        int codeLength = 0;
        try {
            if ((tmp = scanner.nextLine()).startsWith("CODE_")){
                codeLength = Integer.parseInt(tmp.substring(5));
            }else
                return false;
        }catch (NumberFormatException nmfe){
            return false;
        }
        if (codeLength <= 0)
            return false;
        String codeRaw = scanner.nextLine();
        openProject.updateCode(StringUtils.prepareLoadedCode(codeRaw));
        if(!scanner.nextLine().equals("CODE_END"))
            return false;
        if (!scanner.nextLine().equals("FILE_END"))
            return false;
        openProject.setFile(projectFile);
        isProjectOpen = true;
        return true;
    }

    public boolean createProject(String dirPath){
        try {
            String name, author, version, packageName;
            CreateProjectWizard createProjectWizard = new CreateProjectWizard();
            if(JOptionPane.showConfirmDialog(new JFrame(), createProjectWizard, "Create Project", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
                name = createProjectWizard.name.getText();
                author = createProjectWizard.authorName.getText();
                version = createProjectWizard.version.getText();
                packageName = createProjectWizard.packageName.getText();
                if(name == null || author==null || version==null || packageName==null)
                    return false;
                if (name.length()==0 || author.length()==0||version.length()==0 || packageName.length()==0)
                    return false;
            }else{
                return false;
            }
            File baseDir = new File(dirPath);
            if(!baseDir.exists() || !baseDir.isDirectory() || !baseDir.canRead() || !baseDir.canWrite())
                return false;
            if(!writeExamples(baseDir))
                return false;
            File projectFile = new File(dirPath, "project.jmbasicproj");
            if(!projectFile.createNewFile())
                return false;
            openProject = new Project();
            openProject.addProperty("name", name);
            openProject.addProperty("author", author);
            openProject.addProperty("version", version);
            openProject.addProperty("package", packageName);
            openProject.setFile(projectFile);
            isProjectOpen = true;
            return writeProject(openProject);
        }catch (Exception ex){
            return false;
        }
    }

    public boolean saveProject(){
        return writeProject(openProject);
    }

    public void updateCode(String code){
        this.openProject.updateCode(code);
    }

    private boolean writeProject(Project project) {
        String projectFileContent = "";
        projectFileContent += "JMBASIC.PROJECT.FILE\n";
        projectFileContent += "@DEVELOPER_JAYSMITO_MUKHERJEE\n";
        projectFileContent += "PROPERTIES_" + project.getProjectProperties().size() + "\n";
        for(ProjectProperty property : project.getProjectProperties())
            projectFileContent += property.getName() + "\t" + property.getProperty() + "\n";
        projectFileContent += "PROPERTIES_END\n";
        projectFileContent += "CODE_" + project.getCode().length() + "\n";
        projectFileContent += StringUtils.prepareCodeToSave(project.getCode());
        projectFileContent += "\nCODE_END\n";
        projectFileContent += "FILE_END";

        return FileHandler.saveFile(project.getFile(), projectFileContent);
    }

    private boolean writeExamples(File baseDir) {
        // TODO: Make Write Examples
        return true;
    }

    public void closeProject(){
        this.isProjectOpen = false;
        this.openProject = null;
    }
}


package ide.projectmanager;

import java.io.File;
import java.util.ArrayList;

public class Project {
    private String code;
    private File file;
    private ArrayList<ProjectProperty> projectProperties;



    public Project(){
        this.code = "";
        this.projectProperties = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void addProperty(String propertyName, String propertyValue){
        this.projectProperties.add(new ProjectProperty(propertyName, propertyValue));
    }

    public void addProperty(ProjectProperty property){
        this.projectProperties.add(property);
    }

    public void updateCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<ProjectProperty> getProjectProperties() {
        return projectProperties;
    }

    public void setProjectProperties(ArrayList<ProjectProperty> projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Override
    public String toString() {
        return "Project{" +
                "code='" + code + '\'' +
                ", projectProperties=" + projectProperties +
                '}';
    }

    public String getProperty(String name) {
        for (ProjectProperty property:projectProperties){
            if (property.getName().equals("name"))
                return property.getProperty();
        }
        return null;
    }
}

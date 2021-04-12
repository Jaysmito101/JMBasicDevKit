package ide.projectmanager;

public class ProjectProperty {
    private String name;
    private String property;

    public ProjectProperty(String name, String property) {
        this.name = name;
        this.property = property;
    }

    public ProjectProperty() {
        this.name = "";
        this.property = "";
    }

    @Override
    public String toString() {
        return "ProjectProperty{" +
                "name='" + name + '\'' +
                ", property='" + property + '\'' +
                '}';
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

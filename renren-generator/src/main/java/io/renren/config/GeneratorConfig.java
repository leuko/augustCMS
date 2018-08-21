package io.renren.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smileMAC on 18/8/16.
 */
@Configuration
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfig {

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    private String project;

    private String moduleName;

    private String packageName;

    private String table;

    private String menuName;

    private List<String> columnList = new ArrayList<String>();

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}

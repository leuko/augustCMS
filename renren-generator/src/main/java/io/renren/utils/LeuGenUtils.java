/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.utils;

import io.renren.entity.ColumnEntity;
import io.renren.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Stream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class LeuGenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template_leu/Entity.java.vm");
        templates.add("template_leu/Dao.java.vm");
        templates.add("template_leu/Dao.xml.vm");
        templates.add("template_leu/Service.java.vm");
        templates.add("template_leu/ServiceImpl.java.vm");
        templates.add("template_leu/Controller.java.vm");
        templates.add("template_leu/table_menu.sql.vm");
        templates.add("template_leu/vue.vm");
//        templates.add("template_leu/list.js.vm");
        return templates;
    }


    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns,
                                     String project,
                                     String moduleName,
                                     String packageName,
                                     List<String> sqlColumns
    ) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        //表名转换成Java类名
        String className = tableToJava(table.get("className"), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            columnEntity.setAttrName(attrName);

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        String mainPath = config.getString("mainPath");
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", packageName);
        map.put("moduleName", moduleName);
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("sqlColumns", sqlColumns);
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String filePath = getFileName(template, tableEntity.getClassName(), project, packageName, moduleName);
                if (filePath == null) continue;
                System.err.println(filePath);
                FileOutputStream templateOutput = new FileOutputStream(filePath);
                IOUtils.write(sw.toString(), templateOutput , "UTF-8");
                IOUtils.closeQuietly(sw);
                templateOutput.close();
            } catch (Exception e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName()+"，原因："+e.getMessage());
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String project, String packageName, String moduleName) {

        String projectPath = System.getProperty("user.dir") + File.separator +
                project + File.separator + "src" + File.separator + "main" + File.separator;

        String packagePath = projectPath + "java" + File.separator;

        String vueTemplatePath = System.getProperty("user.dir") + File.separator +
                project + File.separator + "admin-template" + File.separator + "src" + File.separator + "components" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        packagePath += moduleName + File.separator;

        String resourcesPath = projectPath + "resources" + File.separator;


        if (template.contains("Controller.java.vm")) {
            new File(packagePath + "controller").mkdirs();
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.java.vm")) {
            new File(packagePath + "dao").mkdirs();
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Entity.java.vm")) {
            new File(packagePath + "entity").mkdirs();
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Service.java.vm")) {
            new File(packagePath + "service").mkdirs();
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            new File(packagePath + "service" + File.separator + "impl").mkdirs();
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Dao.xml.vm")) {
            new File(resourcesPath + "mapper" + File.separator + moduleName).mkdirs();
            return resourcesPath + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("table_menu.sql.vm")) {
            File migrationFile = new File(resourcesPath + "db" + File.separator + "migration");
            Integer version = getMigrationVersion(migrationFile) + 1;
            return resourcesPath + "db" + File.separator + "migration" + File.separator + "V" + version + "__create_table_" + className.toLowerCase() + "_menu.sql";
        }

        if(template.contains("vue.vm")) {
            return  vueTemplatePath + className.toLowerCase() + ".vue";
        }

//        if (template.contains("list.vue.vm")) {
//            new File(resourcesPath + "templates" + File.separator + "modules" + File.separator + moduleName).mkdirs();
//            return resourcesPath + "templates" + File.separator
//                    + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + ".html";
//        }

        return null;
    }

    public static Integer getMigrationVersion(File file) {
        String[] list = file.list();
        Stream<String> stream = Arrays.stream(list);
        Optional<Integer> first = stream.map(item -> Integer.valueOf(item.substring(1, item.indexOf('_'))))
                .sorted((v1, v2) -> v2 - v1).findFirst();
        return first.orElse(0);

    }
}

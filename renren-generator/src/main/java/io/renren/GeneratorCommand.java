package io.renren;

import io.renren.config.GeneratorConfig;
import io.renren.service.CustomGeneratorService;
import io.renren.utils.LeuGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GeneratorCommand {

//    @Autowired
//    private static GeneratorConfig generatorConfig;

    public static void main(String[] args) {


        SpringApplication springApplication = new SpringApplication(GeneratorApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
        CustomGeneratorService generatorService = context.getBean("customGeneratorService", CustomGeneratorService.class);
        GeneratorConfig generatorConfig = (GeneratorConfig) context.getBean("generatorConfig");
        // 配置项目名
        String project = generatorConfig.getProject();//"renren-admin";
        // 配置模块名
        String moduleName = generatorConfig.getModuleName();//"cms";
        // 配置包名
        String packageName =generatorConfig.getPackageName();//"io.renren.modules";
        // 配置表名
        String table =  generatorConfig.getTable();//"article";
        // 配置字段
        List<String> columns = generatorConfig.getColumnList();

        String menuName = generatorConfig.getMenuName();

        // 执行生成代码
        System.out.println(table);
        try {
            generatorService.generatorCode(project, moduleName, packageName, menuName, table, columns);
            System.out.println("代码生成成功！");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}

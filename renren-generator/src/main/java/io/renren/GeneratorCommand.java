package io.renren;

import io.renren.service.CustomGeneratorService;
import io.renren.utils.LeuGenUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
public class GeneratorCommand {

    public static void main(String[] args) {


        SpringApplication springApplication = new SpringApplication(GeneratorApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
        CustomGeneratorService generatorService = context.getBean("customGeneratorService", CustomGeneratorService.class);

        // 配置项目名
        String project = "renren-admin";
        // 配置模块名
        String moduleName = "cms";
        // 配置包名
        String packageName = "io.renren.modules";
        // 配置表名
        String table = "article";
        // 配置字段
        ArrayList<String> columns = new ArrayList<>();
        columns.add("`id` int(10) unsigned NOT NULL AUTO_INCREMENT , ");
        columns.add("`title` varchar(256) NULL , ");
        columns.add("`status` tinyint(2) NULL , ");
        columns.add("`updated_at` datetime NULL , ");
        columns.add("`created_at` datetime NULL , ");
        columns.add("PRIMARY KEY (`id`)");

        // 执行生成代码
        try {
            generatorService.generatorCode(project, moduleName, packageName, table, columns);
            System.out.println("代码生成成功！");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}

package io.renren;

import io.renren.service.CustomGeneratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GeneratorCommand {

    public static void main(String[] args) {


        SpringApplication springApplication = new SpringApplication(GeneratorApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
        CustomGeneratorService generatorService = context.getBean("customGeneratorService", CustomGeneratorService.class);

        String[] tables = new String[]{"articles"};
        String project = "renren-admin";
        String moduleName = "sys";
        generatorService.generatorCode(tables, project, moduleName);

    }
}

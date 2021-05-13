package com.example.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

/**
 * @author 李磊
 */
@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    private static final KieServices SERVICES = KieServices.Factory.get();
    private static final KieFileSystem FILE_SYSTEM = SERVICES.newKieFileSystem();

    static {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(CLASSPATH_URL_PREFIX + RULES_PATH + "*.drl");
            for (Resource file : resources) {
                FILE_SYSTEM.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public KieContainer kieContainer() {
        SERVICES.newKieBuilder(FILE_SYSTEM).buildAll();
        return SERVICES.newKieContainer(SERVICES.getRepository().getDefaultReleaseId());
    }
}
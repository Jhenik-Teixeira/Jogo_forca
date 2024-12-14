package br.edu.ifpi.jogoforca.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.io.IOException;

@Configuration
@Profile("dev")
public class LiveReloadConfig {

    public LiveReloadConfig(final TemplateEngine templateEngine) throws IOException {
        final ClassPathResource applicationProperties = new ClassPathResource("application-dev.properties");
        /**
         *  Essa parte do código está verificando e navegando pela estrutura de diretórios
         *  para encontrar um arquivo específico chamado mvnw, que é um "wrapper" do Maven
         */
        if (applicationProperties.isFile()) {
            File sourceRoot = applicationProperties.getFile().getParentFile();
            while (sourceRoot.listFiles((dir, name) -> name.equals("mvnw")).length != 1) {
                sourceRoot = sourceRoot.getParentFile();
            }

            /**
             * Esse trecho de código está configurando um FileTemplateResolver para o Thymeleaf, um mecanismo de
             * templates popular em aplicações Java, como aquelas que utilizam o Spring Framework. O objetivo é
             * configurar o Thymeleaf para buscar arquivos de templates HTML de forma correta dentro de um projeto
             */
            final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
            fileTemplateResolver.setPrefix(sourceRoot.getPath() + "/src/main/resources/templates/");
            fileTemplateResolver.setSuffix(".html");
            fileTemplateResolver.setCacheable(false);
            fileTemplateResolver.setCharacterEncoding("UTF-8");
            fileTemplateResolver.setCheckExistence(true);
            templateEngine.setTemplateResolver(fileTemplateResolver);

        }
    }
}

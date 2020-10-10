package team.smartworld.academy.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main class TodoList Application
 * SwaggerUI URL: See <a href=http://localhost:8080/swagger-ui.html>SwaggerUI</a>
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@SpringBootApplication
@EnableSwagger2
public class TodoListApplication {

    /**
     * Main method from start server
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    /**
     * Swagger2 UI
     *
     * @return Docked
     */
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("team.smartworld.academy.todolist")).build();
//    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}

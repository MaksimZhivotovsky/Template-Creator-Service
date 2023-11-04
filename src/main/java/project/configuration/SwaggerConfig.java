package project.configuration;

import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.server-port}")
    private int serverPort;

    @Value("${swagger.server-host}")
    private String serverHost;

    /**
     * Задает настройки запуска свагера
     *
     * @return инстанс настройки свагера
     */
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .host(serverHost + ":" + serverPort)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("project"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
////    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}

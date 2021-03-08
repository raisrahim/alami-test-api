package alami.test.api;

import alami.test.api.member.jpa.MemberRepository;
import alami.test.api.transaction.jpa.TransactionJpaRepository;
import alami.test.api.transaction.mongo.TransactionMongoRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 *
 * @author Muhammad Rais Rahim
 */
@EnableMongoRepositories(basePackageClasses = {TransactionMongoRepository.class})
@EnableJpaRepositories(basePackageClasses = {MemberRepository.class, TransactionJpaRepository.class})
@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        
        SpringApplication.run(App.class, args);
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedMethods("GET", "POST");
            }
        };
    }

    @Bean
    Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackageName()))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    NewTopic memberTopic() {

        return TopicBuilder.name("member").build();
    }

    @Bean
    NewTopic transactionTopic() {

        return TopicBuilder.name("transaction").build();
    }
}

package com.takeaway.gameofthree;

import java.util.Collections;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJms
@EnableSwagger2
@SpringBootApplication
public class GameofthreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameofthreeApplication.class, args);
	}
	
	@Bean // Serialize message content to json using ByteMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.BYTES);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(Predicates.not(PathSelectors.regex("/error.*")))
          .build()
          .tags(new Tag("gameofthree", "Game of three service"))
		  .genericModelSubstitutes(Optional.class)
          .apiInfo(apiInfo());                                           
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Game of Three API", 
	      "API responsible for providing the communication between players", 
	      "API 0.1v", 
	      null, 
	      new Contact("Fernando", null, "itfreitasfernando@gmail.com"), 
	      null, null, Collections.emptyList());
	}

}


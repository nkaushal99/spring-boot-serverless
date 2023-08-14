package com.nikhil;

import com.nikhil.post.JsonPlaceholderService;
import com.nikhil.post.PostController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@SpringBootApplication
// Use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "com.nikhil.post")
@Import({PostController.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  JsonPlaceholderService jsonPlaceholderService() {
    WebClient client = WebClient.builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .build();
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
    return factory.createClient(JsonPlaceholderService.class);
  }
}
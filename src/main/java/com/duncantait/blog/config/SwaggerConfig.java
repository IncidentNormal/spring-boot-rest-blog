package com.duncantait.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${info.build.version}")
  private String versionNumber;
  @Value("${info.build.projectName}")
  private String projectName;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(buildInfo(projectName, versionNumber));
  }

  private static Info buildInfo(final String projectName, final String versionNumber) {
    return new Info()
      .title(projectName)
      .version(versionNumber)
      .contact(new Contact().name("Duncan Tait"))
      .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"));
  }

}

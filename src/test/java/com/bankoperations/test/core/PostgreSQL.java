package com.bankoperations.test.core;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class PostgreSQL {
  public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14");

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + container.getJdbcUrl(),
          "spring.datasource.username=" + container.getUsername(),
          "spring.datasource.password=" + container.getPassword()
      ).applyTo(applicationContext);
    }
  }
}

package com.fossfloors.json.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fossfloors.json.util.ExcelJSONMapper;
import com.fossfloors.util.converter.ExcelToJsonConverter;

@Configuration
public class AppConfig {

  @Bean
  public ExcelToJsonConverter excelConverter() {
    return new ExcelToJsonConverter();
  }

  @Bean
  public ExcelJSONMapper jsonMapper() {
    return new ExcelJSONMapper();
  }

}

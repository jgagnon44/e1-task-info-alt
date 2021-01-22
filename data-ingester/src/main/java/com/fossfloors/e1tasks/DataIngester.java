package com.fossfloors.e1tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataIngester {

  public static void main(String[] args) {
    SpringApplication.run(DataIngester.class, args);
  }

  // @Bean
  // public TaskMasterService tmService(final TaskMasterRepository repo) {
  // return new TaskMasterService(repo);
  // }
  //
  // @Bean
  // public TaskRelationshipService trService(final TaskRelationshipRepository repo) {
  // return new TaskRelationshipService(repo);
  // }
  //
  // @Bean
  // public VariantDetailService vdService(final VariantDetailRepository repo) {
  // return new VariantDetailService(repo);
  // }

}

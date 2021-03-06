package com.fossfloors.e1tasks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.fossfloors.e1tasks.backend.util.DataIngester;

@Component
public class IngesterRunner implements CommandLineRunner {

  @Value("${ingest-sources.task-master-file}")
  private String                  tmFile;
  @Value("${ingest-sources.task-relationship-file}")
  private String                  trFile;
  @Value("${ingest-sources.variant-detail-file}")
  private String                  vdFile;

  private DataIngester            ingester;

  @Autowired
  private TaskMasterService       tmService;
  @Autowired
  private TaskRelationshipService trService;

  @PostConstruct
  private void init() {
    ingester = new DataIngester(tmFile, trFile, vdFile);
  }

  @Override
  public void run(String... args) throws Exception {
    ingester.ingestFiles();
    tmService.saveAll(ingester.getTaskMasterSet());
    trService.saveAll(ingester.getTaskRelationshipSet());
    // TODO save variant detail items
  }

}

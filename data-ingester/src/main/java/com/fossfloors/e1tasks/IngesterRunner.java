package com.fossfloors.e1tasks;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fossfloors.e1tasks.backend.beans.VariantID;
import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.fossfloors.e1tasks.backend.service.TaskService;
import com.fossfloors.e1tasks.backend.service.VariantDetailService;
import com.fossfloors.e1tasks.backend.util.DataIngester;
import com.fossfloors.e1tasks.backend.util.RelationshipsProcessor;

@Component
public class IngesterRunner implements CommandLineRunner {

  private static final Logger     logger = LoggerFactory.getLogger(IngesterRunner.class);

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
  @Autowired
  private VariantDetailService    vdService;
  @Autowired
  private TaskService             taskService;

  private RelationshipsProcessor  processor;

  @PostConstruct
  private void init() {
    ingester = new DataIngester(tmFile, trFile, vdFile);
    processor = new RelationshipsProcessor(tmService, trService, vdService);
  }

  @Override
  public void run(String... args) throws Exception {
    // Read and parse data from data files.
    ingester.ingestFiles();

    // Save data to database.
    logger.info("Saving ingested data");
    tmService.saveAll(ingester.getTaskMasterSet());
    trService.saveAll(ingester.getTaskRelationshipSet());
    vdService.saveAll(ingester.getVariantDetailSet());

    logger.info("Building master menus");
    Map<String, Set<Task>> masterMenus = processor.buildMasterMenus();

    // Save all master menus to database.
    masterMenus.forEach((taskView, taskSet) -> {
      logger.info("Saving master menu: task view = {}", taskView);
      taskService.saveAll(taskSet);
    });

    logger.info("Building variant menus");
    Map<VariantID, Set<Task>> variantMenus = processor.buildVariantMenus();

    // Save all variant menus to database.
    variantMenus.forEach((varID, taskSet) -> {
      logger.info("Saving variant menu: variant ID = {}", varID);
      taskService.saveAll(taskSet);
    });
  }

}

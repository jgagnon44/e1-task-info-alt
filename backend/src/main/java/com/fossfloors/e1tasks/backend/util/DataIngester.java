package com.fossfloors.e1tasks.backend.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskRelationship;
import com.fossfloors.e1tasks.backend.entity.VariantDetail;

public class DataIngester {

  private static final Logger      logger     = LoggerFactory.getLogger(DataIngester.class);

  private String                   tmFile;
  private String                   trFile;
  private String                   vdFile;

  private TaskMasterIngester       tmIngester = new TaskMasterIngester();
  private TaskRelationshipIngester trIngester = new TaskRelationshipIngester();
  private VariantDetailIngester    vdIngester = new VariantDetailIngester();

  private Set<TaskMaster>          taskMasterSet;
  private Set<TaskRelationship>    taskRelationshipSet;
  private Set<VariantDetail>       variantDetailSet;

  private Map<String, TaskMaster>  taskMasterMap;

  public DataIngester(String tmFile, String trFile, String vdFile) {
    this.tmFile = tmFile;
    this.trFile = trFile;
    this.vdFile = vdFile;
  }

  public Set<TaskMaster> getTaskMasterSet() {
    return taskMasterSet;
  }

  public Set<TaskRelationship> getTaskRelationshipSet() {
    return taskRelationshipSet;
  }

  public Set<VariantDetail> getVariantDetailSet() {
    return variantDetailSet;
  }

  public void ingestFiles() {
    try {
      logger.info("Ingesting TaskMaster data ...");
      taskMasterSet = tmIngester.ingestFile(tmFile);
      logger.info("{} items", taskMasterSet.size());

      logger.info("Ingesting TaskRelationship data ...");
      taskRelationshipSet = trIngester.ingestFile(trFile);
      logger.info("{} items", taskRelationshipSet.size());

      logger.info("Ingesting VariantDetail data ...");
      variantDetailSet = vdIngester.ingestFile(vdFile);
      logger.info("{} items", variantDetailSet.size());

      // Convert TaskMaster set to map, keyed by internalTaskID.
      taskMasterMap = taskMasterSet.stream()
          .collect(Collectors.toMap(TaskMaster::getInternalTaskID, e -> e));

      logger.info("taskMasterMap: size={}", taskMasterMap.size());
    } catch (InvalidFormatException | IOException e) {
      logger.error("Exception", e);
    }
  }

  public void processRelationships() {
    logger.info("Processing task relationships ... {} items", taskRelationshipSet.size());

    taskRelationshipSet.forEach(rel -> {
      TaskMaster parent = taskMasterMap.get(rel.getParentTaskID());
      TaskMaster child = taskMasterMap.get(rel.getChildTaskID());

      if (parent != null) {
        if (child != null) {
          parent.addChildTask(child);
        } else {
          logger.warn("parent with null child: pid: {}, cid: {}, parent: {}", rel.getParentTaskID(),
              rel.getChildTaskID(), parent.getName());
        }
      } else {
        if (child != null) {
          logger.warn("child with null parent: pid: {}, cid: {}, child: {}", rel.getParentTaskID(),
              rel.getChildTaskID(), child.getName());
        } else {
          logger.warn("both parent and child are null: pid: {}, cid: {}", rel.getParentTaskID(),
              rel.getChildTaskID());
        }
      }
    });
  }

}

package com.fossfloors.e1tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;

@Component
public class DumpTreeRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(DumpTreeRunner.class);

  private static int          level  = 0;

  @Autowired
  private TaskMasterService   tmService;

  @Override
  public void run(String... args) throws Exception {
    TaskMaster foss = tmService.findByInternalTaskID("1014");
    processNode(foss);
  }

  private void processNode(TaskMaster node) {
    level++;
    logger.info(buildSpacer() + node.dumpNode());

    node.getChildTasks().forEach(c -> {
      TaskMaster child = tmService.findByInternalTaskID(c.getInternalTaskID());
      processNode(child);
    });
    level--;
  }

  private String buildSpacer() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < level; i++) {
      sb.append("  ");
    }

    return sb.toString();
  }

}

package com.fossfloors.e1tasks.backend.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.beans.VariantID;
import com.fossfloors.e1tasks.backend.entity.Task;

public class TaskMenuPrintUtil {

  private static final Logger logger = LoggerFactory.getLogger(TaskMenuPrintUtil.class);

  private static int          level  = 0;

  public static void printMasterMenuMap(Map<String, Task> map) {
    logger.info("{} entries", map.size());

    map.forEach((viewID, task) -> {
      Path path = Path.of("master-menu-map_" + viewID + ".txt");
      BufferedWriter writer = null;

      try {
        writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
      } catch (IOException e) {
        logger.error("Exception", e);
      }

      logger.info("viewID: {}", viewID);

      try {
        writer.write("viewID: " + viewID + "\n");
      } catch (IOException e) {
        logger.error("Exception", e);
      }

      level = 0;
      printTask(task, writer);

      try {
        writer.close();
      } catch (IOException e) {
        logger.error("Exception", e);
      }
    });
  }

  public static void printMasterMenu(String viewID, Task task) {
    Path path = Path.of("master-menu_" + viewID + ".txt");
    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
    } catch (IOException e) {
      logger.error("Exception", e);
    }

    level = 0;
    printTask(task, writer);

    try {
      writer.close();
    } catch (IOException e) {
      logger.error("Exception", e);
    }
  }

  public static void printVariantMenuMap(Map<VariantID, Task> map) {
    logger.info("{} entries", map.size());

    map.forEach((varID, task) -> {
      Path path = Path
          .of("variant-menu-map_" + varID.getVariantName() + "-" + varID.getTaskView() + ".txt");
      BufferedWriter writer = null;

      try {
        writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
      } catch (IOException e) {
        logger.error("Exception", e);
      }

      logger.info("variantID: {}", varID);

      try {
        writer.write("variantID: " + varID.toString() + "\n");
      } catch (IOException e) {
        logger.error("Exception", e);
      }

      level = 0;
      printTask(task, writer);

      try {
        writer.close();
      } catch (IOException e) {
        logger.error("Exception", e);
      }
    });
  }

  public static void printVariantMenu(VariantID varID, Task task) {
    Path path = Path
        .of("variant-menu_" + varID.getVariantName() + "-" + varID.getTaskView() + ".txt");
    BufferedWriter writer = null;

    try {
      writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
    } catch (IOException e) {
      logger.error("Exception", e);
    }

    try {
      writer.write("variantID: " + varID.toString() + "\n");
    } catch (IOException e) {
      logger.error("Exception", e);
    }

    level = 0;
    printTask(task, writer);

    try {
      writer.close();
    } catch (IOException e) {
      logger.error("Exception", e);
    }
  }

  private static void printTask(Task task, BufferedWriter writer) {
    level++;

    try {
      writer.write(buildIndent() + task.dumpNode() + "\n");
    } catch (IOException e) {
      logger.error("Exception", e);
    }

    task.getToReferences().forEach(child -> {
      printTask(child, writer);
      level--;
    });

    try {
      writer.flush();
    } catch (IOException e) {
      logger.error("Exception", e);
    }
  }

  private static String buildIndent() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < level; i++) {
      sb.append("  ");
    }

    return sb.toString();
  }

}

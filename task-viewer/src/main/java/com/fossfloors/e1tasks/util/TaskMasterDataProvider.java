package com.fossfloors.e1tasks.util;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

public class TaskMasterDataProvider
    extends AbstractBackEndHierarchicalDataProvider<TaskMaster, Void> {

  private static final long       serialVersionUID = 1L;

  private static final Logger     logger           = LoggerFactory
      .getLogger(TaskMasterDataProvider.class);

  private final TaskMasterService service;

  public TaskMasterDataProvider(TaskMasterService service) {
    this.service = service;
  }

  @Override
  public int getChildCount(HierarchicalQuery<TaskMaster, Void> query) {
    logger.info("parent: {}", query.getParent());
    int count = 0;

    if (query.getParent() == null) {
      count = service.getTopLevelTasks().size();
    } else {
      count = service.getChildCount(query.getParent());
    }

    logger.info("child count: {}", count);
    return count;
  }

  @Override
  public boolean hasChildren(TaskMaster item) {
    logger.info("item: {}", item);
    return service.hasChildren(item);
  }

  @Override
  protected Stream<TaskMaster> fetchChildrenFromBackEnd(HierarchicalQuery<TaskMaster, Void> query) {
    logger.info("parent: {}", query.getParent());

    if (query.getParent() == null) {
      return service.getTopLevelTasks().stream();
    } else {
      return service.getChildren(query.getParent()).stream();
    }
  }

}

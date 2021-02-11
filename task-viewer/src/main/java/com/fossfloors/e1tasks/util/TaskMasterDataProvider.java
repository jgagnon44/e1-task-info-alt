package com.fossfloors.e1tasks.util;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

public class TaskMasterDataProvider
    extends AbstractBackEndHierarchicalDataProvider<TaskMaster, Void> {

  private static final long   serialVersionUID = 1L;

  private static final Logger logger           = LoggerFactory
      .getLogger(TaskMasterDataProvider.class);

  private TaskMasterService   service;

  public TaskMasterDataProvider(TaskMasterService service) {
    this.service = service;
  }

  @Override
  public int getChildCount(HierarchicalQuery<TaskMaster, Void> query) {
    if (query.getParent() != null) {
      logger.info("parent: {}", query.getParent().getInternalTaskID());
      int count = service.getChildCount(query.getParent());
      logger.info("child count: {}", count);
    } else {
      logger.error("parent is null");
    }
    return service.getChildCount(query.getParent());
  }

  @Override
  public boolean hasChildren(TaskMaster item) {
    logger.info("item: {}", item.getInternalTaskID());
    logger.info("has children: {}", service.hasChildren(item));
    return service.hasChildren(item);
  }

  @Override
  protected Stream<TaskMaster> fetchChildrenFromBackEnd(HierarchicalQuery<TaskMaster, Void> query) {
    logger.info("parent: {}", query.getParent().getInternalTaskID());
    List<TaskMaster> list = service.getChildren(query.getParent());
    logger.info("child count: {}", list.size());
    return service.getChildren(query.getParent()).stream();
  }

}

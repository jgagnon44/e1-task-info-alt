package com.fossfloors.e1tasks.util;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.service.MasterMenuService;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

public class MasterMenuDataProvider extends AbstractBackEndHierarchicalDataProvider<Task, Void> {

  private static final long       serialVersionUID = 1L;

  private static final Logger     logger           = LoggerFactory
      .getLogger(MasterMenuDataProvider.class);

  private final MasterMenuService service;

  private String                  taskView;

  public MasterMenuDataProvider(MasterMenuService service) {
    this.service = service;
  }

  public void setTaskView(String taskView) {
    this.taskView = taskView;
  }

  @Override
  public int getChildCount(HierarchicalQuery<Task, Void> query) {
    int count = 0;

    if (taskView != null) {
      if (query.getParent() == null) {
        count = service.getTopLevelTasks(taskView).size();
      } else {
        count = service.getChildCount(taskView, query.getParent());
      }
    }

    logger.info("parent: {}, child count: {}", query.getParent(), count);
    return count;
  }

  @Override
  public boolean hasChildren(Task item) {
    boolean hasChildren = false;

    if (taskView != null) {
      hasChildren = service.hasChildren(taskView, item);
    }

    logger.info("item: {}, hasChildren: {}", item, hasChildren);
    return hasChildren;
  }

  @Override
  public Stream<Task> fetchChildrenFromBackEnd(HierarchicalQuery<Task, Void> query) {
    logger.info("query: {}", query);
    logger.info("parent: {}", query.getParent());

    if (taskView != null) {
      if (query.getParent() == null) {
        return service.getTopLevelTasks(taskView).stream();
      } else {
        return service.getChildren(taskView, query.getParent()).stream();
      }
    }

    return null;
  }

}

package com.fossfloors.e1tasks.util;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.service.TasksService;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

public class TaskDataProvider extends AbstractBackEndHierarchicalDataProvider<Task, Void> {

  private static final long   serialVersionUID = 1L;

  private static final Logger logger           = LoggerFactory.getLogger(TaskDataProvider.class);

  private final TasksService  service;

  private String              variantName;

  public TaskDataProvider(TasksService service) {
    this.service = service;
  }

  public void setTaskVariant(String variantName) {
    this.variantName = variantName;
  }

  @Override
  public int getChildCount(HierarchicalQuery<Task, Void> query) {
    int count = 0;

    if (query.getParent() == null) {
      count = service.getTopLevelTasks(variantName).size();
    } else {
      count = service.getChildCount(variantName, query.getParent());
    }

    logger.info("parent: {}, child count: {}", query.getParent(), count);
    return count;
  }

  @Override
  public boolean hasChildren(Task item) {
    boolean hasChildren = service.hasChildren(variantName, item);
    logger.info("item: {}, hasChildren: {}", item, hasChildren);
    return hasChildren;
  }

  @Override
  protected Stream<Task> fetchChildrenFromBackEnd(HierarchicalQuery<Task, Void> query) {
    logger.info("parent: {}", query.getParent());

    if (query.getParent() == null) {
      return service.getTopLevelTasks(variantName).stream();
    } else {
      return service.getChildren(variantName, query.getParent()).stream();
    }
  }

}

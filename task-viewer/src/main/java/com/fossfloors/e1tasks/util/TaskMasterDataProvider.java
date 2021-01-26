package com.fossfloors.e1tasks.util;

import java.util.stream.Stream;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;

public class TaskMasterDataProvider
    extends AbstractBackEndHierarchicalDataProvider<TaskMaster, Void> {

  private TaskMasterService service;

  public TaskMasterDataProvider(TaskMasterService service) {
    this.service = service;
  }

  @Override
  public int getChildCount(HierarchicalQuery<TaskMaster, Void> query) {
    return service.getChildCount(query.getParent());
  }

  @Override
  public boolean hasChildren(TaskMaster item) {
    return service.hasChildren(item);
  }

  @Override
  protected Stream<TaskMaster> fetchChildrenFromBackEnd(HierarchicalQuery<TaskMaster, Void> query) {
    return service.getChildren(query.getParent()).stream();
  }

}

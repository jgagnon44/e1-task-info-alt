package com.fossfloors.e1tasks.ui;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.shared.Registration;

@CssImport("./styles/shared-styles.css")
@SuppressWarnings("serial")
public class TaskTreeView extends VerticalLayout {

  private static final Logger                        logger = LoggerFactory
      .getLogger(TaskTreeView.class);

  // private TreeGrid<TaskMaster> taskGrid;
  // private TreeData<TaskMaster> treeData;
  // private TreeDataProvider<TaskMaster> provider;
  private TreeGrid<TaskMaster>                       taskGrid;
  // private TreeData<E1Task2> treeData;
  // private TreeDataProvider<E1Task> provider;

  private HierarchicalDataProvider<TaskMaster, Void> provider;

  // private TaskMasterService taskMasterService;
  // private TaskRelationshipService taskRelationService;
  private TaskMasterService                          taskService;

  public TaskTreeView(TaskMasterService taskService) {
    this.taskService = taskService;

    setSizeFull();
    addClassName("task-tree-grid");

    configureView();
  }

  @Override
  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
      ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public void loadTasks(TopLevelTaskView.TaskSelectionEvent event) {
    if (event.getSelected() != null) {
      updateTree(event.getSelected());
    }
  }

  private void updateTree(TaskMaster task) {
    // treeData.clear();
    // treeData.addRootItems(task);

    // processChildTasks(task.getInternalTaskID(), task);

    // taskGrid.setItems(task);
    // provider.refreshItem(task);

    provider.refreshItem(task, true);
  }

  private void configureView() {
    taskGrid = new TreeGrid<>(TaskMaster.class);
    taskGrid.setColumns("name", "type"/* , "objectName", "formName", "version" */);
    taskGrid.setHierarchyColumn("name");
    taskGrid.getColumns().forEach(col -> {
      col.setAutoWidth(true);
      col.setResizable(true);
    });

    provider = new AbstractBackEndHierarchicalDataProvider<TaskMaster, Void>() {
      @Override
      public int getChildCount(HierarchicalQuery<TaskMaster, Void> query) {
        logger.info("parent: {}", query.getParent());
        return taskService.getChildCount(query.getParent());
      }

      @Override
      public boolean hasChildren(TaskMaster item) {
        logger.info("item: {}", item);
        return taskService.hasChildren(item);
      }

      @Override
      protected Stream<TaskMaster> fetchChildrenFromBackEnd(
          HierarchicalQuery<TaskMaster, Void> query) {
        logger.info("parent: {}", query.getParent());
        return taskService.getChildren(query.getParent()).stream();
      }
    };

    // treeData = new TreeData<>();
    // provider = new TreeDataProvider<>(treeData);
    taskGrid.setDataProvider(provider);

    taskGrid.asSingleSelect().addValueChangeListener(event -> {
      fireEvent(new TaskSelectionEvent(this, event.getValue()));
    });

    add(taskGrid);
  }

  public static class TaskSelectionEvent extends ComponentEvent<TaskTreeView> {
    private TaskMaster selected;

    public TaskSelectionEvent(TaskTreeView source, TaskMaster selected) {
      super(source, false);
      this.selected = selected;
    }

    public TaskMaster getSelected() {
      return selected;
    }
  }

}

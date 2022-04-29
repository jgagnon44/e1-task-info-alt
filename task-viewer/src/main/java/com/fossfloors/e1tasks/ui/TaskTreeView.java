package com.fossfloors.e1tasks.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.Task;
//import com.fossfloors.e1tasks.backend.entity.TaskMaster;
//import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TasksService;
import com.fossfloors.e1tasks.util.TaskDataProvider;
//import com.fossfloors.e1tasks.util.TaskMasterDataProvider;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.shared.Registration;

@Deprecated
@CssImport("./styles/shared-styles.css")
public class TaskTreeView extends VerticalLayout {

  private static final long   serialVersionUID = 1L;

  private static final Logger logger           = LoggerFactory.getLogger(TaskTreeView.class);

  // private TreeGrid<TaskMaster> taskGrid;
  private TreeGrid<Task>      taskGrid;

  // private TaskMasterDataProvider provider;
  private TaskDataProvider    provider;

  // private TaskMasterService taskService;
  private TasksService        tasksService;

  // public TaskTreeView(TaskMasterService taskService) {
  public TaskTreeView(TasksService tasksService) {
    this.tasksService = tasksService;

    setSizeFull();
    addClassName("task-tree-grid");

    configureView();
  }

  public void setTaskVariantName(TaskHierarchiesView.VariantSelectedEvent event) {
    if (event.getSelected() != null) {
      // TODO update tree with items for variant selected
      logger.info("variant: {}", event.getSelected());
      provider.setTaskVariant(event.getSelected());
      provider.refreshAll();
    }
  }

  @Override
  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
      ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  private void configureView() {
    taskGrid = new TreeGrid<>();
    taskGrid.addHierarchyColumn(Task::getName).setHeader("Name");
    taskGrid.addColumn(Task::getType).setHeader("Type");

    taskGrid.getColumns().forEach(col -> {
      col.setAutoWidth(true);
      col.setResizable(true);
    });

    // provider = new TaskMasterDataProvider(taskService);
    provider = new TaskDataProvider(tasksService);
    taskGrid.setDataProvider(provider);

    taskGrid.asSingleSelect().addValueChangeListener(event -> {
      fireEvent(new TaskSelectionEvent(this, event.getValue()));
    });

    add(taskGrid);
  }

  public static class TaskSelectionEvent extends ComponentEvent<TaskTreeView> {
    private static final long serialVersionUID = 1L;

    private Task              selected;

    public TaskSelectionEvent(TaskTreeView source, Task selected) {
      super(source, false);
      this.selected = selected;
    }

    public Task getSelected() {
      return selected;
    }
  }

}

package com.fossfloors.e1tasks.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.util.TaskMasterDataProvider;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.shared.Registration;

@CssImport("./styles/shared-styles.css")
@SuppressWarnings("serial")
public class TaskTreeView extends VerticalLayout {

  private static final Logger    logger = LoggerFactory.getLogger(TaskTreeView.class);

  private TreeGrid<TaskMaster>   taskGrid;

  private TaskMasterDataProvider provider;

  private TaskMasterService      taskService;

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

  public void loadTask(TopLevelTaskView.TaskSelectionEvent event) {
    if (event.getSelected() != null) {
      TaskMaster task = event.getSelected();
      taskGrid.setItems(task);
      provider.refreshItem(task, true);
    }
  }

  private void configureView() {
    taskGrid = new TreeGrid<>(TaskMaster.class);
    taskGrid.setColumns("name", "type"/* , "objectName", "formName", "version" */);
    taskGrid.setHierarchyColumn("name");
    taskGrid.getColumns().forEach(col -> {
      col.setAutoWidth(true);
      col.setResizable(true);
    });

    provider = new TaskMasterDataProvider(taskService);
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

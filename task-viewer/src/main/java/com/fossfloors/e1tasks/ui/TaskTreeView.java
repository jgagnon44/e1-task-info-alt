package com.fossfloors.e1tasks.ui;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskRelationship;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.shared.Registration;

@CssImport("./styles/shared-styles.css")
public class TaskTreeView extends VerticalLayout {

  private static final long            serialVersionUID = 1L;

  private static final Logger          logger           = LoggerFactory
      .getLogger(TaskTreeView.class);

  private TreeGrid<TaskMaster>         taskGrid;
  private TreeData<TaskMaster>         treeData;
  private TreeDataProvider<TaskMaster> provider;

  // private TaskMasterDataProvider provider;

  private TaskMasterService            taskService;
  private TaskRelationshipService      relationService;

  private Random                       random           = new Random(System.currentTimeMillis());

  public TaskTreeView(TaskMasterService taskService, TaskRelationshipService relationService) {
    this.taskService = taskService;
    this.relationService = relationService;

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
      // TaskMaster task = event.getSelected();
      // logger.info("loading task: {}", task.getInternalTaskID());
      // provider.refreshItem(task, true);
      // taskGrid.setItems(task);
      updateTree(event.getSelected());
    }
  }

  private void updateTree(TaskMaster task) {
    logger.info("task: {}, child count: {}", task.getInternalTaskID(), task.getChildTasks().size());

    task.getChildTasks().forEach(t -> logger.info("child: {}", t));

    treeData.clear();
    treeData.addRootItems(task);

    processChildTasks(task.getInternalTaskID(), task);

    provider.refreshAll();
  }

  private void processChildTasks(String parentTaskID, TaskMaster parentTask) {
    List<TaskRelationship> childRelations = relationService
        .getChildRelationsForParent(parentTaskID);

    childRelations.forEach(relation -> {
      TaskMaster childTask = taskService.getByInternalTaskID(parentTaskID);

      if (childTask != null) {
        if (treeData.contains(childTask)) {
          childTask.setSalt(random.nextLong());
        }

        treeData.addItem(parentTask, childTask);

        processChildTasks(childTask.getInternalTaskID(), childTask);
      }
    });
  }

  // private void processChildTasks(String parentTaskID, TaskMaster parentTask) {
  // parentTask.getChildTasks().forEach(child -> {
  // if (treeData.contains(child)) {
  // child.setSalt(random.nextLong());
  // }
  //
  // treeData.addItem(parentTask, child);
  //
  // processChildTasks(child.getInternalTaskID(), child);
  // });
  // }

  private void configureView() {
    taskGrid = new TreeGrid<>(TaskMaster.class);
    taskGrid.setColumns("name", "type");
    taskGrid.setHierarchyColumn("name");

    // taskGrid = new TreeGrid<>();
    // taskGrid.addHierarchyColumn(TaskMaster::getName).setHeader("Name");
    // taskGrid.addColumn(TaskMaster::getType).setHeader("Type");

    taskGrid.getColumns().forEach(col -> {
      col.setAutoWidth(true);
      col.setResizable(true);
    });

    treeData = new TreeData<>();
    provider = new TreeDataProvider<>(treeData);
    taskGrid.setDataProvider(provider);

    // provider = new TaskMasterDataProvider(taskService);
    // taskGrid.setDataProvider(provider);

    taskGrid.asSingleSelect().addValueChangeListener(event -> {
      fireEvent(new TaskSelectionEvent(this, event.getValue()));
    });

    add(taskGrid);
  }

  public static class TaskSelectionEvent extends ComponentEvent<TaskTreeView> {
    private static final long serialVersionUID = 1L;

    private TaskMaster        selected;

    public TaskSelectionEvent(TaskTreeView source, TaskMaster selected) {
      super(source, false);
      this.selected = selected;
    }

    public TaskMaster getSelected() {
      return selected;
    }
  }

}

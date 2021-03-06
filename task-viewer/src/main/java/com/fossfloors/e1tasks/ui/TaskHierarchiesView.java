package com.fossfloors.e1tasks.ui;

import javax.annotation.PostConstruct;

import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("tree")
@CssImport("./styles/shared-styles.css")
public class TaskHierarchiesView extends VerticalLayout {

  private static final long       serialVersionUID = 1L;

  private TopLevelTaskView        topLevelView;
  private TaskTreeView            taskTreeView;
  private TaskDetailView          taskDetailView;

  private TaskMasterService       taskService;
  private TaskRelationshipService taskRelationService;

  public TaskHierarchiesView(TaskMasterService taskService,
      TaskRelationshipService taskRelationService) {
    this.taskService = taskService;
    this.taskRelationService = taskRelationService;

    setSizeFull();
    addClassName("task-hierarchies-view");

    configureView();
  }

  @PostConstruct
  private void init() {
    topLevelView.loadTasks();
  }

  private void configureView() {
    add(configInfoPane(), configGridsLayout());

    topLevelView.addListener(TopLevelTaskView.TaskSelectionEvent.class, taskTreeView::loadTask);
    taskTreeView.addListener(TaskTreeView.TaskSelectionEvent.class, taskDetailView::loadTask);
  }

  private Component configInfoPane() {
    HorizontalLayout layout = new HorizontalLayout();

    TextField pt = new TextField("# parent tasks");
    pt.setReadOnly(true);
    pt.setValue(String.valueOf(taskRelationService.getParentTaskIDs().size()));

    TextField dpt = new TextField("# distinct parent tasks");
    dpt.setReadOnly(true);
    dpt.setValue(String.valueOf(taskRelationService.getDistinctParentTaskIDs().size()));

    TextField ct = new TextField("# child tasks");
    ct.setReadOnly(true);
    ct.setValue(String.valueOf(taskRelationService.getChildTaskIDs().size()));

    TextField dct = new TextField("# distinct child tasks");
    dct.setReadOnly(true);
    dct.setValue(String.valueOf(taskRelationService.getDistinctChildTaskIDs().size()));

    TextField tlt = new TextField("# top-level tasks");
    tlt.setReadOnly(true);
    tlt.setValue(String.valueOf(taskRelationService.getTopLevelParentTaskIDs().size()));

    layout.add(pt, dpt, ct, dct, tlt);
    return layout;
  }

  private Component configGridsLayout() {
    SplitLayout layout = new SplitLayout();
    layout.setSizeFull();

    topLevelView = new TopLevelTaskView(taskService);
    topLevelView.setWidth("25%");

    layout.addToPrimary(topLevelView);
    layout.addToSecondary(configTreeGridLayout());
    return layout;
  }

  private Component configTreeGridLayout() {
    SplitLayout layout = new SplitLayout();
    layout.setOrientation(Orientation.VERTICAL);
    layout.setSizeFull();

    taskTreeView = new TaskTreeView(taskService);
    taskTreeView.setHeight("70%");

    taskDetailView = new TaskDetailView();
    taskDetailView.setHeight("30%");

    layout.addToPrimary(taskTreeView);
    layout.addToSecondary(taskDetailView);
    return layout;
  }

}

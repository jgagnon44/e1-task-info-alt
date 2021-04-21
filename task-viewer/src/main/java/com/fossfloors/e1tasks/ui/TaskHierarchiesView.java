package com.fossfloors.e1tasks.ui;

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

  private TaskTreeView            taskTreeView;
  private TaskDetailView          taskDetailView;

  private TaskMasterService       taskService;
  private TaskRelationshipService relationService;

  public TaskHierarchiesView(TaskMasterService taskService,
      TaskRelationshipService relationService) {
    this.taskService = taskService;
    this.relationService = relationService;

    setSizeFull();
    addClassName("task-hierarchies-view");

    configureView();
  }

  private void configureView() {
    add(configInfoPane(), configTreeGridLayout());

    taskTreeView.addListener(TaskTreeView.TaskSelectionEvent.class, taskDetailView::loadTask);
  }

  private Component configInfoPane() {
    HorizontalLayout layout = new HorizontalLayout();

    TextField pt = new TextField("# parent tasks");
    pt.setReadOnly(true);
    pt.setValue(String.valueOf(relationService.getParentTaskIDs().size()));

    TextField dpt = new TextField("# distinct parent tasks");
    dpt.setReadOnly(true);
    dpt.setValue(String.valueOf(relationService.getDistinctParentTaskIDs().size()));

    TextField ct = new TextField("# child tasks");
    ct.setReadOnly(true);
    ct.setValue(String.valueOf(relationService.getChildTaskIDs().size()));

    TextField dct = new TextField("# distinct child tasks");
    dct.setReadOnly(true);
    dct.setValue(String.valueOf(relationService.getDistinctChildTaskIDs().size()));

    TextField tlt = new TextField("# top-level tasks");
    tlt.setReadOnly(true);
    tlt.setValue(String.valueOf(relationService.getTopLevelParentTaskIDs().size()));

    layout.add(pt, dpt, ct, dct, tlt);
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

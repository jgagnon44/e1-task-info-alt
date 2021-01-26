package com.fossfloors.e1tasks.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("tasks")
@CssImport("./styles/shared-styles.css")
@SuppressWarnings("serial")
public class TaskMasterListView extends VerticalLayout {

  private static final Logger logger = LoggerFactory.getLogger(TaskMasterListView.class);

  private TextField           numItems;
  private Grid<TaskMaster>    grid   = new Grid<>(TaskMaster.class, false);

  private TaskMasterService   taskService;

  public TaskMasterListView(TaskMasterService taskService) {
    this.taskService = taskService;
    addClassName("task-list-view");
    setSizeFull();
    configureView();
  }

  @PostConstruct
  public void init() {
    List<TaskMaster> items = taskService.findAll();
    numItems.setValue(String.valueOf(items.size()));
    grid.setItems(items);
  }

  private void configureView() {
    configureGrid();
    add(configInfoPane(), grid);
  }

  private Component configInfoPane() {
    HorizontalLayout layout = new HorizontalLayout();

    numItems = new TextField("# tasks");

    layout.add(numItems);
    return layout;
  }

  private void configureGrid() {
    grid.addClassName("tasks-grid");

    grid.setColumns("internalTaskID", "taskID", "name", "type", "objectName", "version",
        "formName");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
  }

}

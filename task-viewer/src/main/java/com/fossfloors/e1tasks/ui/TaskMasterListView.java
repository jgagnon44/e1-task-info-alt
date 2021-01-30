package com.fossfloors.e1tasks.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.beans.TaskFilter;
import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskType;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("tasks")
@CssImport("./styles/shared-styles.css")
@SuppressWarnings("serial")
public class TaskMasterListView extends VerticalLayout {

  private static final Logger logger = LoggerFactory.getLogger(TaskMasterListView.class);

  private TextField           totalItems;
  private TextField           filteredItems;

  private TextField           taskID;
  private TextField           name;
  private ComboBox<TaskType>  type;
  private TextField           objectName;
  private TextField           version;
  private TextField           formName;

  private Grid<TaskMaster>    grid   = new Grid<>(TaskMaster.class, false);

  private TaskMasterService   taskService;

  private Binder<TaskFilter>  binder;

  public TaskMasterListView(TaskMasterService taskService) {
    this.taskService = taskService;
    addClassName("task-list-view");
    setSizeFull();
    configureView();
  }

  @PostConstruct
  public void init() {
    totalItems.setValue(String.valueOf(taskService.findAll().size()));
    updateGrid();
  }

  private void updateGrid() {
    List<TaskMaster> items = taskService.filter(binder.getBean());
    grid.setItems(items);
    filteredItems.setValue(String.valueOf(items.size()));
  }

  private void configureView() {
    configureGrid();
    add(configInfoPane(), configFilterPane(), grid);
    bindFilterFields();
  }

  private Component configInfoPane() {
    HorizontalLayout layout = new HorizontalLayout();

    totalItems = new TextField("Total tasks");
    totalItems.setReadOnly(true);

    filteredItems = new TextField("Filtered tasks");
    filteredItems.setReadOnly(true);

    layout.add(totalItems, filteredItems);
    return layout;
  }

  private Component configFilterPane() {
    HorizontalLayout layout = new HorizontalLayout();

    taskID = new TextField("Task ID");
    name = new TextField("Name");
    type = new ComboBox<>("Type", TaskType.values());
    objectName = new TextField("Object Name");
    version = new TextField("Version");
    formName = new TextField("Form Name");

    layout.add(taskID, name, type, objectName, version, formName);
    return layout;
  }

  private void bindFilterFields() {
    binder = new Binder<>(TaskFilter.class);
    binder.setBean(new TaskFilter());
    binder.bindInstanceFields(this);
    binder.addValueChangeListener(event -> {
      updateGrid();
    });
  }

  private void configureGrid() {
    grid.addClassName("tasks-grid");

    grid.setColumns("internalTaskID", "taskID", "name", "type", "objectName", "version",
        "formName");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
  }

}

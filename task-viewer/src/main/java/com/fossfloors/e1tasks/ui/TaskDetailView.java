package com.fossfloors.e1tasks.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskType;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

@SuppressWarnings("serial")
public class TaskDetailView extends FormLayout {

  private static final Logger logger = LoggerFactory.getLogger(TaskDetailView.class);

  private TextField           internalTaskID;
  private TextField           taskID;
  private TextField           name;
  private TextField           type;
  private TextField           objectName;
  private TextField           formName;
  private TextField           version;

  private Binder<TaskMaster>  binder;

  public TaskDetailView() {
    configureView();
    bindData();
  }

  public void loadTask(TaskTreeView.TaskSelectionEvent event) {
    if (event.getSelected() != null) {
      binder.setBean(event.getSelected());
    }
  }

  private void configureView() {
    setResponsiveSteps(new ResponsiveStep("25em", 1), new ResponsiveStep("25em", 2),
        new ResponsiveStep("25em", 3));

    internalTaskID = new TextField("Internal Task ID");
    internalTaskID.setReadOnly(true);

    taskID = new TextField("Task ID");
    taskID.setReadOnly(true);

    name = new TextField("Name");
    name.setReadOnly(true);

    type = new TextField("Type");
    type.setReadOnly(true);

    objectName = new TextField("Object Name");
    objectName.setReadOnly(true);

    formName = new TextField("Form Name");
    formName.setReadOnly(true);

    version = new TextField("Version");
    version.setReadOnly(true);

    add(internalTaskID, taskID, name, type, objectName, formName, version);

    setColspan(internalTaskID, 2);
    setColspan(name, 2);
  }

  private void bindData() {
    binder = new Binder<>(TaskMaster.class);
    binder.setBean(new TaskMaster());

    // binder.bindInstanceFields(this);

    binder.forField(internalTaskID).bind("internalTaskID");
    binder.forField(taskID).bind("taskID");
    binder.forField(name).bind("name");
    // binder.forField(type).withConverter(new TaskTypeConverter1()).bind("type");
    binder.forField(type).bind("type");
    binder.forField(objectName).bind("objectName");
    binder.forField(formName).bind("formName");
    binder.forField(version).bind("version");
  }

  // private static class TaskTypeConverter1 implements Converter<String, TaskType> {
  //
  // @Override
  // public Result<TaskType> convertToModel(String value, ValueContext context) {
  // logger.info("convertToModel: " + value);
  // return Result.ok(TaskType.fromCodeValue(value));
  // }
  //
  // @Override
  // public String convertToPresentation(TaskType value, ValueContext context) {
  // if (value != null) {
  // logger.info("convertToPresentation: " + value.toString());
  // return value.getCodeValue();
  // } else {
  // logger.info("convertToPresentation: null");
  // return "";
  // }
  // }
  //
  // }

}

package com.fossfloors.e1tasks.ui;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskType;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class TaskDetailView extends FormLayout {

  private static final long  serialVersionUID = 1L;

  private TextField          internalTaskID;
  private TextField          taskID;
  private TextField          name;
  private TextField          type;
  private TextField          objectName;
  private TextField          formName;
  private TextField          version;

  private Binder<TaskMaster> binder;

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

    binder.forField(internalTaskID).bind("internalTaskID");
    binder.forField(taskID).bind("taskID");
    binder.forField(name).bind("name");
    binder.forField(type).withConverter(new TaskTypeConverter()).bind("type");
    binder.forField(objectName).bind("objectName");
    binder.forField(formName).bind("formName");
    binder.forField(version).bind("version");
  }

  private static class TaskTypeConverter implements Converter<String, TaskType> {
    private static final long serialVersionUID = 1L;

    @Override
    public Result<TaskType> convertToModel(String value, ValueContext context) {
      TaskType type = TaskType.fromCodeValue(value);
      return type != null ? Result.ok(type) : Result.error("Unknown task type: " + value);
    }

    @Override
    public String convertToPresentation(TaskType value, ValueContext context) {
      return value != null ? value.name() : "";
    }
  }

}

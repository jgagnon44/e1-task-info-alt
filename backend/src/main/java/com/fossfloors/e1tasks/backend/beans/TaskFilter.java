package com.fossfloors.e1tasks.backend.beans;

import com.fossfloors.e1tasks.backend.entity.TaskType;

public class TaskFilter {

  private String   taskID;
  private String   name;
  private TaskType type = TaskType.ALL;
  private String   objectName;
  private String   version;
  private String   formName;

  public String getTaskID() {
    return taskID;
  }

  public void setTaskID(String taskID) {
    this.taskID = taskID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TaskType getType() {
    return type;
  }

  public void setType(TaskType type) {
    this.type = type;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getFormName() {
    return formName;
  }

  public void setFormName(String formName) {
    this.formName = formName;
  }

}

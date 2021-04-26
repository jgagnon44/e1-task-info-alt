package com.fossfloors.e1tasks.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TaskMaster extends AbstractEntity {

  private String          internalTaskID;
  private String          taskID;
  private String          name;
  private TaskType        type;
  private String          objectName;
  private String          version;
  private String          formName;
  private boolean         active;
  private boolean         required;
  private String          taskViewLink;
  private String          parentTaskLink;
  private String          childTaskLink;

  @ManyToOne(fetch = FetchType.LAZY)
  private TaskMaster      parentTask;

  @OneToMany(mappedBy = "parentTask")
  private Set<TaskMaster> childTasks = new HashSet<>();

  public String getInternalTaskID() {
    return internalTaskID;
  }

  public void setInternalTaskID(String internalTaskID) {
    this.internalTaskID = internalTaskID;
  }

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

  public String getObjectName() {
    return objectName;
  }

  public TaskType getType() {
    return type;
  }

  public void setType(TaskType type) {
    this.type = type;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getTaskViewLink() {
    return taskViewLink;
  }

  public void setTaskViewLink(String taskViewLink) {
    this.taskViewLink = taskViewLink;
  }

  public String getParentTaskLink() {
    return parentTaskLink;
  }

  public void setParentTaskLink(String parentTaskLink) {
    this.parentTaskLink = parentTaskLink;
  }

  public String getChildTaskLink() {
    return childTaskLink;
  }

  public void setChildTaskLink(String childTaskLink) {
    this.childTaskLink = childTaskLink;
  }

  public TaskMaster getParentTask() {
    return parentTask;
  }

  public void setParentTask(TaskMaster parentTask) {
    this.parentTask = parentTask;
  }

  public TaskMaster addChildTask(TaskMaster task) {
    task.parentTask = this;
    childTasks.add(task);
    return task;
  }

  public Set<TaskMaster> getChildTasks() {
    return childTasks;
  }

  @Override
  public String toString() {
    String parentStr = parentTask != null ? "not null" : "null";
    return "TaskMaster [internalTaskID=" + internalTaskID + ", taskID=" + taskID + ", name=" + name
        + ", type=" + type + ", objectName=" + objectName + ", version=" + version + ", formName="
        + formName + ", active=" + active + ", required=" + required + ", taskViewLink="
        + taskViewLink + ", parentTaskLink=" + parentTaskLink + ", childTaskLink=" + childTaskLink
        + ", parent=" + parentStr + ", children=" + childTasks.size() + "]";
  }

  public String dumpNode() {
    String parentStr = parentTask != null ? "not null" : "null";
    return "TaskMaster [internalTaskID=" + internalTaskID + ", name=" + name + ", type=" + type
        + ", parent=" + parentStr + ", children=" + childTasks.size() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (active ? 1231 : 1237);
    result = prime * result + ((childTaskLink == null) ? 0 : childTaskLink.hashCode());
    result = prime * result + ((formName == null) ? 0 : formName.hashCode());
    result = prime * result + ((internalTaskID == null) ? 0 : internalTaskID.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((objectName == null) ? 0 : objectName.hashCode());
    result = prime * result + ((parentTaskLink == null) ? 0 : parentTaskLink.hashCode());
    result = prime * result + (required ? 1231 : 1237);
    result = prime * result + ((taskID == null) ? 0 : taskID.hashCode());
    result = prime * result + ((taskViewLink == null) ? 0 : taskViewLink.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((version == null) ? 0 : version.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    TaskMaster other = (TaskMaster) obj;
    if (active != other.active)
      return false;
    if (childTaskLink == null) {
      if (other.childTaskLink != null)
        return false;
    } else if (!childTaskLink.equals(other.childTaskLink))
      return false;
    if (formName == null) {
      if (other.formName != null)
        return false;
    } else if (!formName.equals(other.formName))
      return false;
    if (internalTaskID == null) {
      if (other.internalTaskID != null)
        return false;
    } else if (!internalTaskID.equals(other.internalTaskID))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (objectName == null) {
      if (other.objectName != null)
        return false;
    } else if (!objectName.equals(other.objectName))
      return false;
    if (parentTaskLink == null) {
      if (other.parentTaskLink != null)
        return false;
    } else if (!parentTaskLink.equals(other.parentTaskLink))
      return false;
    if (required != other.required)
      return false;
    if (taskID == null) {
      if (other.taskID != null)
        return false;
    } else if (!taskID.equals(other.taskID))
      return false;
    if (taskViewLink == null) {
      if (other.taskViewLink != null)
        return false;
    } else if (!taskViewLink.equals(other.taskViewLink))
      return false;
    if (type != other.type)
      return false;
    if (version == null) {
      if (other.version != null)
        return false;
    } else if (!version.equals(other.version))
      return false;
    return true;
  }

}

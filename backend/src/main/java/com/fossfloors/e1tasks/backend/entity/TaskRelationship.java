package com.fossfloors.e1tasks.backend.entity;

import javax.persistence.Entity;

@Entity
public class TaskRelationship extends AbstractEntity {

  private String  taskView;
  private String  parentTaskID;
  private String  childTaskID;
  private int     presentationSeq;
  private int     parentChildRelation;
  private boolean active;
  private boolean required;
  private String  taskViewLink;
  private String  parentTaskLink;
  private String  childTaskLink;
  private String  variantLink;

  public String getTaskView() {
    return taskView;
  }

  public void setTaskView(String taskView) {
    this.taskView = taskView;
  }

  public String getParentTaskID() {
    return parentTaskID;
  }

  public void setParentTaskID(String parentTaskID) {
    this.parentTaskID = parentTaskID;
  }

  public String getChildTaskID() {
    return childTaskID;
  }

  public void setChildTaskID(String childTaskID) {
    this.childTaskID = childTaskID;
  }

  public int getPresentationSeq() {
    return presentationSeq;
  }

  public void setPresentationSeq(int presentationSeq) {
    this.presentationSeq = presentationSeq;
  }

  public int getParentChildRelation() {
    return parentChildRelation;
  }

  public void setParentChildRelation(int parentChildRelation) {
    this.parentChildRelation = parentChildRelation;
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

  public String getVariantLink() {
    return variantLink;
  }

  public void setVariantLink(String variantLink) {
    this.variantLink = variantLink;
  }

  @Override
  public String toString() {
    return "TaskRelationship [taskView=" + taskView + ", parentTaskID=" + parentTaskID
        + ", childTaskID=" + childTaskID + ", presentationSeq=" + presentationSeq
        + ", parentChildRelation=" + parentChildRelation + ", active=" + active + ", required="
        + required + ", taskViewLink=" + taskViewLink + ", parentTaskLink=" + parentTaskLink
        + ", childTaskLink=" + childTaskLink + ", variantLink=" + variantLink + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (active ? 1231 : 1237);
    result = prime * result + ((childTaskID == null) ? 0 : childTaskID.hashCode());
    result = prime * result + ((childTaskLink == null) ? 0 : childTaskLink.hashCode());
    result = prime * result + parentChildRelation;
    result = prime * result + ((parentTaskID == null) ? 0 : parentTaskID.hashCode());
    result = prime * result + ((parentTaskLink == null) ? 0 : parentTaskLink.hashCode());
    result = prime * result + presentationSeq;
    result = prime * result + (required ? 1231 : 1237);
    result = prime * result + ((taskView == null) ? 0 : taskView.hashCode());
    result = prime * result + ((taskViewLink == null) ? 0 : taskViewLink.hashCode());
    result = prime * result + ((variantLink == null) ? 0 : variantLink.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TaskRelationship other = (TaskRelationship) obj;
    if (active != other.active)
      return false;
    if (childTaskID == null) {
      if (other.childTaskID != null)
        return false;
    } else if (!childTaskID.equals(other.childTaskID))
      return false;
    if (childTaskLink == null) {
      if (other.childTaskLink != null)
        return false;
    } else if (!childTaskLink.equals(other.childTaskLink))
      return false;
    if (parentChildRelation != other.parentChildRelation)
      return false;
    if (parentTaskID == null) {
      if (other.parentTaskID != null)
        return false;
    } else if (!parentTaskID.equals(other.parentTaskID))
      return false;
    if (parentTaskLink == null) {
      if (other.parentTaskLink != null)
        return false;
    } else if (!parentTaskLink.equals(other.parentTaskLink))
      return false;
    if (presentationSeq != other.presentationSeq)
      return false;
    if (required != other.required)
      return false;
    if (taskView == null) {
      if (other.taskView != null)
        return false;
    } else if (!taskView.equals(other.taskView))
      return false;
    if (taskViewLink == null) {
      if (other.taskViewLink != null)
        return false;
    } else if (!taskViewLink.equals(other.taskViewLink))
      return false;
    if (variantLink == null) {
      if (other.variantLink != null)
        return false;
    } else if (!variantLink.equals(other.variantLink))
      return false;
    return true;
  }

}

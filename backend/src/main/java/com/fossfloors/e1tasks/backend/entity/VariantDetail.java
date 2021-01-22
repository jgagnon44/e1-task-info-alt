package com.fossfloors.e1tasks.backend.entity;

import javax.persistence.Entity;

//@Entity
public class VariantDetail extends AbstractEntity {

  private String  variantName;
  private String  taskView;
  private String  parentTaskID;
  private String  childTaskID;
  private boolean active;

  public String getVariantName() {
    return variantName;
  }

  public void setVariantName(String variantName) {
    this.variantName = variantName;
  }

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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "VariantDetail [variantName=" + variantName + ", taskView=" + taskView
        + ", parentTaskID=" + parentTaskID + ", childTaskID=" + childTaskID + ", active=" + active
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (active ? 1231 : 1237);
    result = prime * result + ((childTaskID == null) ? 0 : childTaskID.hashCode());
    result = prime * result + ((parentTaskID == null) ? 0 : parentTaskID.hashCode());
    result = prime * result + ((taskView == null) ? 0 : taskView.hashCode());
    result = prime * result + ((variantName == null) ? 0 : variantName.hashCode());
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
    VariantDetail other = (VariantDetail) obj;
    if (active != other.active)
      return false;
    if (childTaskID == null) {
      if (other.childTaskID != null)
        return false;
    } else if (!childTaskID.equals(other.childTaskID))
      return false;
    if (parentTaskID == null) {
      if (other.parentTaskID != null)
        return false;
    } else if (!parentTaskID.equals(other.parentTaskID))
      return false;
    if (taskView == null) {
      if (other.taskView != null)
        return false;
    } else if (!taskView.equals(other.taskView))
      return false;
    if (variantName == null) {
      if (other.variantName != null)
        return false;
    } else if (!variantName.equals(other.variantName))
      return false;
    return true;
  }

}

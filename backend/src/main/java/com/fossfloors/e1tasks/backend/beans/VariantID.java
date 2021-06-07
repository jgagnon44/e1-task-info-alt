package com.fossfloors.e1tasks.backend.beans;

public class VariantID {

  private String variantName;
  private String taskView;

  public VariantID(String variantName, String taskView) {
    this.variantName = variantName;
    this.taskView = taskView;
  }

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

  @Override
  public String toString() {
    return "VariantID [variantName=" + variantName + ", taskView=" + taskView + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    VariantID other = (VariantID) obj;
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

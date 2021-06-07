package com.fossfloors.e1tasks.backend.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

//@Entity
public class Task extends TaskMaster {

  private String    variantName;

  // @ManyToMany(cascade = CascadeType.ALL)
  private Set<Task> toReferences   = new LinkedHashSet<>();

  // @ManyToMany(mappedBy = "toReferences")
  private Set<Task> fromReferences = new LinkedHashSet<>();

  /**
   * Default constructor.
   */
  public Task() {
  }

  /**
   * Copy constructor.
   * 
   * @param master - object to copy from.
   */
  public Task(TaskMaster master) {
    this.internalTaskID = master.internalTaskID;
    this.taskID = master.taskID;
    this.name = master.name;
    this.type = master.type;
    this.objectName = master.objectName;
    this.version = master.version;
    this.formName = master.formName;
    this.active = master.active;
    this.required = master.required;
    this.taskViewLink = master.taskViewLink;
    this.parentTaskLink = master.parentTaskLink;
    this.childTaskLink = master.childTaskLink;
  }

  public String getVariantName() {
    return variantName;
  }

  public void setVariantName(String variantName) {
    this.variantName = variantName;
  }

  public Set<Task> getToReferences() {
    return toReferences;
  }

  public Set<Task> getFromReferences() {
    return fromReferences;
  }

  public Task addToReference(Task task) {
    task.fromReferences.add(this);
    this.toReferences.add(task);
    return task;
  }

  @Override
  public String toString() {
    return "Task [variantName=" + variantName + ", toReferences=" + toReferences.size()
        + ", fromReferences=" + fromReferences.size() + ", internalTaskID=" + internalTaskID
        + ", taskID=" + taskID + ", name=" + name + ", type=" + type + ", objectName=" + objectName
        + ", version=" + version + ", formName=" + formName + ", active=" + active + ", required="
        + required + ", taskViewLink=" + taskViewLink + ", parentTaskLink=" + parentTaskLink
        + ", childTaskLink=" + childTaskLink + "]";
  }

  public String dumpNode() {
    return "Task [variantName=" + variantName + ", toReferences=" + toReferences.size()
        + ", fromReferences=" + fromReferences.size() + ", internalTaskID=" + internalTaskID
        + ", name=" + name + ", type=" + type + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((variantName == null) ? 0 : variantName.hashCode());
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
    Task other = (Task) obj;
    if (variantName == null) {
      if (other.variantName != null)
        return false;
    } else if (!variantName.equals(other.variantName))
      return false;
    return true;
  }

}

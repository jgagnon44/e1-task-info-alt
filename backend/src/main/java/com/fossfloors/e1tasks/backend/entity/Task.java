package com.fossfloors.e1tasks.backend.entity;

public class Task extends TaskMaster {

  private String variant;

  public Task() {
    this.variant = "DEFAULT";
  }

  public Task(String variant) {
    this.variant = variant;
  }

  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }

  @Override
  public String toString() {
    return "Task [variant=" + variant + ", " + super.toString() + "]";
  }

  @Override
  public String dumpNode() {
    return "Task [variant=" + variant + ", " + super.dumpNode() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((variant == null) ? 0 : variant.hashCode());
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
    if (variant == null) {
      if (other.variant != null)
        return false;
    } else if (!variant.equals(other.variant))
      return false;
    return true;
  }

}

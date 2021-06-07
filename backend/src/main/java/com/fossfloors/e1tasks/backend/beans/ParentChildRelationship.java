package com.fossfloors.e1tasks.backend.beans;

public class ParentChildRelationship {

  private String parent;
  private String child;

  public ParentChildRelationship(String parent, String child) {
    this.parent = parent;
    this.child = child;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getChild() {
    return child;
  }

  public void setChild(String child) {
    this.child = child;
  }

  @Override
  public String toString() {
    return "ParentChildRelationship [parent=" + parent + ", child=" + child + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((child == null) ? 0 : child.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
    ParentChildRelationship other = (ParentChildRelationship) obj;
    if (child == null) {
      if (other.child != null)
        return false;
    } else if (!child.equals(other.child))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    return true;
  }

}

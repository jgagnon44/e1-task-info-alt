package com.fossfloors.e1tasks.beans;

import java.util.HashSet;
import java.util.Set;

public abstract class TreeNode<T> {

  protected TreeNode<T>      parent   = null;
  protected Set<TreeNode<T>> children = new HashSet<>();
  protected T                payload  = null;

  protected TreeNode() {
  }

  protected TreeNode(T payload) {
    this.payload = payload;
  }

  public void addChild(TreeNode<T> node) {
    node.parent = this;
    children.add(node);
  }

  public TreeNode<T> getParent() {
    return parent;
  }

  public Set<TreeNode<T>> getChildren() {
    return children;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public boolean isRoot() {
    return parent == null;
  }

  public boolean hasChildren() {
    return !children.isEmpty();
  }

}

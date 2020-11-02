package com.fossfloors.json.util;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in a tree data structure. Each node has one or no parent
 * and can have any number of child nodes.
 * 
 * @param <T> The node "payload".
 */
public class TreeNode<T> {

  private T                 payload  = null;
  private TreeNode<T>       parent   = null;
  private List<TreeNode<T>> children = new LinkedList<>();

  public TreeNode() {
  }

  public TreeNode(T payload) {
    this.payload = payload;
  }

  public void addChild(TreeNode<T> child) {
    if (child != null) {
      child.setParent(this);
      children.add(child);
    }
  }

  public void removeChild(TreeNode<T> child) {
    if (child != null) {
      children.remove(child);
    }
  }

  public boolean isRoot() {
    return parent == null;
  }

  public boolean isLeaf() {
    return children.isEmpty();
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public List<TreeNode<T>> getChildren() {
    return children;
  }

  public TreeNode<T> getParent() {
    return parent;
  }

  private void setParent(TreeNode<T> parent) {
    this.parent = parent;
  }

}

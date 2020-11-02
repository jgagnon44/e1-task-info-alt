package com.fossfloors.json.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Stack;

public class PrintUtil {

  private static final int      INDENT      = 4;
  private static final int      START_LEVEL = 0;
  private static final int      MAX_LEVELS  = 20;

  private static final String   SPACER;
  private static final String[] SPACERS;

  private static Stack<Integer> levelStack  = new Stack<>();

  static {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < INDENT; i++) {
      sb.append(" ");
    }

    SPACER = sb.toString();
    SPACERS = new String[MAX_LEVELS];

    for (int i = 0; i < MAX_LEVELS; i++) {
      sb = new StringBuilder();

      for (int j = 0; j < i; j++) {
        sb.append(SPACER);
      }

      SPACERS[i] = sb.toString();
    }
  }

  public static void printItemMap(final Map<Object, List<Row>> map) {
    for (Entry<Object, List<Row>> entry : map.entrySet()) {
      System.out.println("Item: " + entry.getKey());
      entry.getValue().stream().forEach(row -> System.out.println(row));
    }
  }

  public static void printItemTree(final TreeNode<ItemData> root) {
    levelStack.clear();
    levelStack.push(START_LEVEL);
    processNode(root);
  }

  private static void processNode(final TreeNode<ItemData> node) {
    printNodePayload(node);
    int indentLevel = levelStack.peek();

    if (!node.isLeaf()) {
      System.out.println(SPACERS[indentLevel] + "  " + "Child Items: " + node.getChildren().size());

      levelStack.push(levelStack.peek() + 1);

      for (TreeNode<ItemData> child : node.getChildren()) {
        processNode(child);
      }

      levelStack.pop();
    } else {
      System.out.println(SPACERS[indentLevel] + "  " + "Child Items: " + node.getChildren().size());
    }
  }

  private static void printNodePayload(final TreeNode<ItemData> node) {
    int indentLevel = levelStack.peek();
    ItemData data = node.getPayload();

    System.out.println(SPACERS[indentLevel] + "Item: " + data.getItem());
    System.out.println(SPACERS[indentLevel] + "  " + "BOM:");

    if (data.getBomData() != null) {
      System.out.println(SPACERS[indentLevel + 1] + data.getBomData());
    } else {
      System.out.println(SPACERS[indentLevel + 1] + "<no data>");
    }

    System.out.println(SPACERS[indentLevel] + "  " + "Routing:");

    if (data.getRoutingData() != null) {
      for (Row row : data.getRoutingData()) {
        System.out.println(SPACERS[indentLevel + 1] + row);
      }
    } else {
      System.out.println(SPACERS[indentLevel + 1] + "<no data>");
    }
  }

}

package com.fossfloors.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fossfloors.json.util.ExcelJSONMapper;
import com.fossfloors.json.util.ItemData;
import com.fossfloors.json.util.PrintUtil;
import com.fossfloors.json.util.Row;
import com.fossfloors.json.util.TreeNode;
import com.fossfloors.util.converter.ExcelToJsonConverter;

@SpringBootApplication
public class Bom_Routing_Tree {

  private Map<Object, List<Row>> bomMap;
  private Map<Object, List<Row>> routingMap;

  @Autowired
  private ExcelToJsonConverter   excelConverter;
  @Autowired
  private ExcelJSONMapper        jsonMapper;

  @PostConstruct
  public void process()
      throws JsonGenerationException, JsonMappingException, InvalidFormatException, IOException {
    String bomJSON = excelConverter.convert("PDM_BOM.xlsx").toJson();
    String routingJSON = excelConverter.convert("PDM_Routing.xlsx").toJson();

    bomMap = jsonMapper.convertToMapGroupedByColumn(bomJSON, 0, "PARENT ITEM NUMBER");
    // PrintUtil.printItemMap(bomMap);
    routingMap = jsonMapper.convertToMapGroupedByColumn(routingJSON, 0, "ITEM NUMBER");
    // PrintUtil.printItemMap(routingMap);

    Map<Object, TreeNode<ItemData>> itemTreeMap = buildItemMap();

    /*
     * Print some randomly selected data
     */

    TreeNode<ItemData> node = itemTreeMap.get("7PD4N5916PKB");
    PrintUtil.printItemTree(node);
  }

  private Map<Object, TreeNode<ItemData>> buildItemMap() {
    Map<Object, TreeNode<ItemData>> itemMap = new HashMap<>();

    // Loop through the items in BOM to construct a hierarchy of items and their
    // dependencies.
    for (Object parentItem : bomMap.keySet()) {
      ItemData data = new ItemData();
      data.setItem(parentItem);

      // Get routing information associated with the item (may not be any).
      List<Row> parentRoutes = routingMap.get(parentItem);
      data.setRoutingData(parentRoutes);

      TreeNode<ItemData> node = new TreeNode<>(data);

      // Get item child items and construct dependency hierarchy.
      List<Row> childItems = bomMap.get(parentItem);
      processItems(node, childItems);

      // Insert constructed hierarchy into item map, keyed by parent item.
      itemMap.put(parentItem, node);
      // PrintUtil.printItemTree(node);
    }

    return itemMap;
  }

  private void processItems(TreeNode<ItemData> parentNode, List<Row> items) {
    // Loop through items to construct dependency hierarchy.
    for (Row itemRow : items) {
      // Get child item number.
      String childItem = (String) itemRow.getItemValue("2ND ITEM NUMBER");

      ItemData data = new ItemData();
      data.setItem(childItem);
      // Set child item BOM information.
      data.setBomData(itemRow);

      // Get child item routing information.
      List<Row> childRoutes = routingMap.get(childItem);
      data.setRoutingData(childRoutes);

      TreeNode<ItemData> node = new TreeNode<>(data);
      // Add child to parent.
      parentNode.addChild(node);

      // Get the child's dependencies (if any) and process them.
      List<Row> childItems = bomMap.get(childItem);

      if (childItems != null) {
        processItems(node, childItems);
      }
    }
  }

  public static void main(String[] args)
      throws JsonGenerationException, JsonMappingException, InvalidFormatException, IOException {
    SpringApplication.run(Bom_Routing_Tree.class, args);
  }

}

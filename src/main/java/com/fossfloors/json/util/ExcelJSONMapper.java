package com.fossfloors.json.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ExcelJSONMapper {

  /**
   * Convert specified JSON string to a list of Row objects, representing rows
   * from an Excel spreadsheet.
   * 
   * @param jsonString - JSON string containing data extracted from Excel file.
   * @param sheetIndex - worksheet index (starts at 0).
   * @return List of <code>Row</code> objects, representing the cells for each
   *         row.
   * @throws IOException
   */
  public List<Row> convertToList(String jsonString, int sheetIndex) throws IOException {
    // Extract the data element from the JSON file.
    ArrayNode dataNode = readDataArray(jsonString, sheetIndex);

    // Convert the data rows to a list of row objects.
    List<Row> rowList = convertArrayToRowList(dataNode);
    return rowList;
  }

  /**
   * Convert JSON data read from the specified file to a list of Row objects,
   * representing rows from an Excel spreadsheet.
   * 
   * @param jsonFile   - JSON file containing data extracted from Excel file.
   * @param sheetIndex - worksheet index (starts at 0).
   * @return List of <code>Row</code> objects, representing the cells for each
   *         row.
   * @throws IOException
   */
  public List<Row> convertToList(File jsonFile, int sheetIndex) throws IOException {
    // Extract the data element from the JSON file.
    ArrayNode dataNode = readDataArray(jsonFile, sheetIndex);

    // Convert the data rows to a list of row objects.
    List<Row> rowList = convertArrayToRowList(dataNode);
    return rowList;
  }

  /**
   * Convert JSON string to a map of lists of Row objects, grouped by the set of
   * values identified by the specified column. The Row objects represent rows
   * from an Excel spreadsheet.
   * 
   * @param jsonString - JSON string containing data extracted from Excel file.
   * @param sheetIndex - worksheet index (starts at 0).
   * @param columnName - column identifier to group rows by.
   * @return Map (keyed by <code>columnName</code> data) of lists of
   *         <code>Row</code> objects, representing the cells for each row.
   * @throws IOException
   */
  public Map<Object, List<Row>> convertToMapGroupedByColumn(String jsonString, int sheetIndex,
      String columnName) throws IOException {
    // Extract the data element from the JSON file.
    ArrayNode dataNode = readDataArray(jsonString, sheetIndex);

    // Convert the data rows to a list of row objects.
    List<Row> rowList = convertArrayToRowList(dataNode);

    // Group the rows by the specified column.
    Map<Object, List<Row>> dataMap = rowList.stream()
        .collect(Collectors.groupingBy(e -> e.getItemValue(columnName)));
    return dataMap;
  }

  /**
   * Convert JSON data read from the specified file to a map of lists of Row
   * objects, grouped by the set of values identified by the specified column. The
   * Row objects represent rows from an Excel spreadsheet.
   * 
   * @param jsonFile   - JSON file containing data extracted from Excel file.
   * @param sheetIndex - worksheet index (starts at 0).
   * @param columnName - column identifier to group rows by.
   * @return Map (keyed by <code>columnName</code> data) of lists of
   *         <code>Row</code> objects, representing the cells for each row.
   * @throws IOException
   */
  public Map<Object, List<Row>> convertToMapGroupedByColumn(File jsonFile, int sheetIndex,
      String columnName) throws IOException {
    // Extract the data element from the JSON file.
    ArrayNode dataNode = readDataArray(jsonFile, sheetIndex);

    // Convert the data rows to a list of row objects.
    List<Row> rowList = convertArrayToRowList(dataNode);

    // Group the rows by the specified column.
    Map<Object, List<Row>> dataMap = rowList.stream()
        .collect(Collectors.groupingBy(e -> e.getItemValue(columnName)));
    return dataMap;
  }

  /*
   * Extract and return a JSON array node, representing the rows of data read from
   * the specified sheet from an Excel file.
   */
  private ArrayNode readDataArray(String jsonString, int sheetIndex) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    // Set to format JSON.
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    // Read in JSON node hierarchy and return node at specified path.
    JsonNode root = mapper.readTree(jsonString);
    return (ArrayNode) root.at("/sheets/" + sheetIndex + "/data");
  }

  /*
   * Extract and return a JSON array node, representing the rows of data read from
   * the specified sheet from an Excel file.
   */
  private ArrayNode readDataArray(File jsonFile, int sheetIndex) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    // Set to format JSON.
    mapper.enable(SerializationFeature.INDENT_OUTPUT);

    // Read in JSON node hierarchy and return node at specified path.
    JsonNode root = mapper.readTree(jsonFile);
    return (ArrayNode) root.at("/sheets/" + sheetIndex + "/data");
  }

  /*
   * Convert a JSON array node object to a list of Row objects.
   */
  private List<Row> convertArrayToRowList(ArrayNode arrayNode) {
    // Get headers (first row).
    ArrayNode headers = (ArrayNode) arrayNode.get(0);

    Iterator<JsonNode> iterator = arrayNode.iterator();
    // Skip headers row so that it's not included in result.
    iterator.next();

    List<Row> result = new LinkedList<>();

    // Loop through the data arrays to build a list of row objects containing the
    // row data (cells).
    while (iterator.hasNext()) {
      ArrayNode rowNode = (ArrayNode) iterator.next();
      Row row = new Row();

      // Loop to map the row cells to column headers.
      for (int i = 0; i < headers.size(); i++) {
        row.addItem(headers.get(i).textValue(), getValueObject(rowNode.get(i)));
      }

      result.add(row);
    }

    return result;
  }

  /*
   * Return the cell value based on the JSON node type.
   */
  private Object getValueObject(JsonNode node) {
    if (node != null) {
      switch (node.getNodeType()) {
        case NULL:
          return null;

        case NUMBER:
          return node.numberValue();

        case STRING:
          return node.textValue();

        default:
          return null;
      }
    } else {
      return null;
    }
  }

}

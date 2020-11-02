package com.fossfloors.json.experimental;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fossfloors.json.util.ExcelJSONMapper;
import com.fossfloors.json.util.Row;

public class SpELTester {

  public static void main(String[] args) throws IOException {
    ExcelJSONMapper mapper = new ExcelJSONMapper();

    Map<Object, List<Row>> map = mapper.convertToMapGroupedByColumn(new File("PDM_BOM.json"), 0,
        "PARENT ITEM NUMBER");

    ExpressionParser parser = new SpelExpressionParser();

    /*
     * Get a map entry (a list of Row objects) - context on map
     */

    EvaluationContext mapContext = new StandardEvaluationContext(map);
    Expression mapExpression1 = parser.parseExpression("['7PD4N5916PKB']");

    System.out.println("Get List<Row>");

    List<Row> itemList = mapExpression1.getValue(mapContext, List.class);
    System.out.println(itemList.getClass() + " :: " + itemList.size());

    for (Row r : itemList) {
      System.out.println(r.getClass() + " :: " + r);
    }

    /*
     * Get a list item - context on list
     */

    System.out.println("Get itemList[2]");

    EvaluationContext listContext = new StandardEvaluationContext(itemList);
    Expression listExpression = parser.parseExpression("[2]");

    Object obj = listExpression.getValue(listContext);
    System.out.println(obj.getClass() + " :: " + obj);

    /*
     * Get a list item - context on map
     */

    System.out.println("Get ['7PD4N5916PKB'][2]");

    Expression mapExpression2 = parser.parseExpression("['7PD4N5916PKB'][2]");

    Row row = mapExpression2.getValue(mapContext, Row.class);
    System.out.println(row.getClass() + " :: " + row);

    /*
     * Get map values from list(s) in the outer map - context on outer map
     */

    System.out.println("Get ['7PD4N5916PKB'][2].cellMap['QUANTITY REQUIRED']");

    Expression mapExpression3 = parser
        .parseExpression("['7PD4N5916PKB'][2].cellMap['QUANTITY REQUIRED']");
    Object item2_QtyReq = mapExpression3.getValue(mapContext);
    System.out.println(item2_QtyReq.getClass() + " :: " + item2_QtyReq);

    System.out.println("Get ['7PD4N5916PKB'][3].cellMap['2ND ITEM NUMBER']");

    Expression mapExpression4 = parser
        .parseExpression("['7PD4N5916PKB'][3].cellMap['2ND ITEM NUMBER']");
    Object item3_2ndItem = mapExpression4.getValue(mapContext);
    System.out.println(item3_2ndItem.getClass() + " :: " + item3_2ndItem);
  }

}

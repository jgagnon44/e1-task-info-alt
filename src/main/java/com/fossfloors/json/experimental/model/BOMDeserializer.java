package com.fossfloors.json.experimental.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BOMDeserializer extends StdDeserializer<BOM> {

  public BOMDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public BOM deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);

    BOM result = new BOM();
    result.setBranch((String) getValue(node.get(0)));
    result.setParentItemNumber((String) getValue(node.get(1)));
    result.setSecondItemNumber((String) getValue(node.get(2)));
    result.setQuantityRequired((Double) getValue(node.get(3)));
    result.setUnitOfMeasure((String) getValue(node.get(4)));
    result.setIssueTypeCode((String) getValue(node.get(5)));
    result.setLineType((String) getValue(node.get(6)));
    result.setStockType((String) getValue(node.get(7)));
    result.setTypeBom((String) getValue(node.get(8)));
    result.setLineNumber((Double) getValue(node.get(9)));
    result.setOperatingSequence((Double) getValue(node.get(10)));
    result.setEffectiveFromDate((String) getValue(node.get(11)));
    result.setEffectiveThruDate((String) getValue(node.get(12)));
    result.setDrawingNumber((String) getValue(node.get(13)));
    result.setUnitCost((Double) getValue(node.get(14)));
    result.setScrapPercent((Double) getValue(node.get(15)));

    return result;
  }

  private Object getValue(JsonNode node) {
    switch (node.getNodeType()) {
      case NULL:
        return null;

      case NUMBER:
        switch (node.numberType()) {
          case DOUBLE:
            return node.asDouble();
          case FLOAT:
            return node.floatValue();
          case INT:
            return node.asInt();
          case LONG:
            return node.asLong();
          default:
            return null;
        }

      case STRING:
        return node.asText();

      default:
        return null;
    }
  }

}

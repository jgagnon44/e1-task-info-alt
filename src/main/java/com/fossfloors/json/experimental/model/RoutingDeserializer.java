package com.fossfloors.json.experimental.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class RoutingDeserializer extends StdDeserializer<Routing> {

  public RoutingDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Routing deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);

    Routing result = new Routing();
    result.setBranch((String) getValue(node.get(0)));
    result.setItemNumber((String) getValue(node.get(1)));
    result.setWorkCenter((String) getValue(node.get(2)));
    result.setSequenceNumber((Double) getValue(node.get(3)));
    result.setDescription((String) getValue(node.get(4)));
    result.setSetupLaborStandard((Double) getValue(node.get(5)));
    result.setStandardRunLabor((Double) getValue(node.get(6)));
    result.setStandardRunMachine((Double) getValue(node.get(7)));
    result.setRoutingType((String) getValue(node.get(8)));
    result.setEffectiveFromDate((String) getValue(node.get(9)));
    result.setEffectiveThruDate((String) getValue(node.get(10)));
    result.setTypeOperation((String) getValue(node.get(11)));
    result.setStandardMoveHours((Double) getValue(node.get(12)));
    result.setStandardQueueHours((Double) getValue(node.get(13)));
    result.setTimeBasisCode((Double) getValue(node.get(14)));
    result.setCrewSize((Double) getValue(node.get(15)));
    result.setCumulativePlanYield((Double) getValue(node.get(16)));
    result.setResourceUnits((Double) getValue(node.get(17)));
    result.setCapacityUom((String) getValue(node.get(18)));
    result.setConsumingLocation((String) getValue(node.get(19)));

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

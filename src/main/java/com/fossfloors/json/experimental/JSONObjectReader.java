package com.fossfloors.json.experimental;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fossfloors.json.experimental.model.BOM;
import com.fossfloors.json.experimental.model.BOMDeserializer;
import com.fossfloors.json.experimental.model.Routing;
import com.fossfloors.json.experimental.model.RoutingDeserializer;

public class JSONObjectReader {

  public static void main(String[] args)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    SimpleModule module = new SimpleModule();
    module.addDeserializer(BOM.class, new BOMDeserializer(BOM.class));
    module.addDeserializer(Routing.class, new RoutingDeserializer(Routing.class));
    mapper.registerModule(module);

    JsonNode root = mapper.readTree(new File("PDM_BOM.json"));
    JsonNode dataNode = root.at("/sheets/0/data");

    for (int i = 1; i < dataNode.size(); i++) {
      JsonNode rowNode = dataNode.get(i);
      BOM bom = mapper.readValue(mapper.writeValueAsString(rowNode), BOM.class);
    }
  }

}

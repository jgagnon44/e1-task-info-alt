package com.fossfloors.json.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents a row of data (a collection of name/value pairs).
 */
public class Row {

  private LinkedHashMap<String, Object> itemMap = new LinkedHashMap<>();

  public Row() {
  }

  public Row(Row other) {
    this.itemMap.putAll(other.getItemMap());
  }

  public LinkedHashMap<String, Object> getItemMap() {
    return itemMap;
  }

  public void addItem(String key, Object value) {
    itemMap.put(key, value);
  }

  public Object getItemValue(String key) {
    return itemMap.get(key);
  }

  public Set<String> getKeys() {
    return itemMap.keySet();
  }

  public Collection<Object> getValues() {
    return itemMap.values();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Entry<String, Object> entry : itemMap.entrySet()) {
      sb.append(" " + entry.getKey() + "=" + entry.getValue() + ",");
    }

    String result = sb.toString();
    return result.substring(0, result.length() - 1).trim();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((itemMap == null) ? 0 : itemMap.hashCode());
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
    Row other = (Row) obj;
    if (itemMap == null) {
      if (other.itemMap != null)
        return false;
    } else if (!itemMap.equals(other.itemMap))
      return false;
    return true;
  }

}

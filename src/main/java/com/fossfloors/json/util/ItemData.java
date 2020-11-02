package com.fossfloors.json.util;

import java.util.List;

public class ItemData {

  private Object    item;
  private Row       bomData;
  private List<Row> routingData; // TODO - do we need a list?

  public Object getItem() {
    return item;
  }

  public void setItem(Object item) {
    this.item = item;
  }

  public Row getBomData() {
    return bomData;
  }

  public void setBomData(Row bomData) {
    this.bomData = bomData;
  }

  public List<Row> getRoutingData() {
    return routingData;
  }

  public void setRoutingData(List<Row> routingData) {
    this.routingData = routingData;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ItemData:\n");
    sb.append("  Item: " + item + "\n");
    sb.append("  BOM: " + bomData + "\n");
    sb.append("  Routing: " + routingData);
    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bomData == null) ? 0 : bomData.hashCode());
    result = prime * result + ((item == null) ? 0 : item.hashCode());
    result = prime * result + ((routingData == null) ? 0 : routingData.hashCode());
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
    ItemData other = (ItemData) obj;
    if (bomData == null) {
      if (other.bomData != null)
        return false;
    } else if (!bomData.equals(other.bomData))
      return false;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item))
      return false;
    if (routingData == null) {
      if (other.routingData != null)
        return false;
    } else if (!routingData.equals(other.routingData))
      return false;
    return true;
  }

}

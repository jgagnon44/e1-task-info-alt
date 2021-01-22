package com.fossfloors.e1tasks.backend.util;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.VariantDetail;

public class VariantDetailIngester implements ExcelFileIngester<VariantDetail> {

  @Override
  public VariantDetail processRow(List<Object> row) {
    VariantDetail obj = new VariantDetail();
    obj.setVariantName((String) row.get(0));
    obj.setTaskView((String) row.get(1));
    obj.setParentTaskID((String) row.get(4));
    obj.setChildTaskID((String) row.get(5));
    obj.setActive(((String) row.get(6)).equals("Y"));
    return obj;
  }

}

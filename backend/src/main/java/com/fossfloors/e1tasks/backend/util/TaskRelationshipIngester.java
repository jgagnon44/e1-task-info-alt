package com.fossfloors.e1tasks.backend.util;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.TaskRelationship;

public class TaskRelationshipIngester implements ExcelFileIngester<TaskRelationship> {

  @Override
  public TaskRelationship processRow(List<Object> row) {
    TaskRelationship obj = new TaskRelationship();
    obj.setTaskView((String) row.get(0));
    obj.setParentTaskID((String) row.get(3));
    obj.setChildTaskID((String) row.get(4));
    obj.setPresentationSeq(doubleToInt((double) row.get(5)));
    obj.setParentChildRelation(doubleToInt((double) row.get(7)));
    obj.setActive(((String) row.get(11)).equals("Y"));
    obj.setRequired(((String) row.get(12)).equals("Y"));
    obj.setTaskViewLink((String) row.get(16));
    obj.setParentTaskLink((String) row.get(17));
    obj.setChildTaskLink((String) row.get(18));
    obj.setVariantLink((String) row.get(33));
    return obj;
  }

  private int doubleToInt(double value) {
    return (int) value;
  }

}

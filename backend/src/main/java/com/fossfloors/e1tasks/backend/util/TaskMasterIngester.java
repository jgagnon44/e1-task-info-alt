package com.fossfloors.e1tasks.backend.util;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskType;

public class TaskMasterIngester implements ExcelFileIngester<TaskMaster> {

  @Override
  public TaskMaster processRow(List<Object> row) {
    TaskMaster obj = new TaskMaster();
    obj.setInternalTaskID((String) row.get(0));
    obj.setTaskID((String) row.get(1));
    obj.setName((String) row.get(2));
    // obj.setType((TaskType) row.get(3));
    obj.setType((String) row.get(3));
    obj.setObjectName((String) row.get(5));
    obj.setVersion((String) row.get(6));
    obj.setFormName((String) row.get(7));
    obj.setActive(((String) row.get(23)).equals("Y"));
    obj.setRequired(((String) row.get(24)).equals("Y"));
    obj.setTaskViewLink((String) row.get(27));
    obj.setParentTaskLink((String) row.get(28));
    obj.setChildTaskLink((String) row.get(29));
    return obj;
  }

}

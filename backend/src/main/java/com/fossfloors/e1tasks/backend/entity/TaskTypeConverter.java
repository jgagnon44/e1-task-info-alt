package com.fossfloors.e1tasks.backend.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskTypeConverter implements AttributeConverter<TaskType, String> {

  @Override
  public String convertToDatabaseColumn(TaskType type) {
    return type != null ? type.getCodeValue() : null;
  }

  @Override
  public TaskType convertToEntityAttribute(String dbData) {
    return TaskType.fromCodeValue(dbData);
  }

}

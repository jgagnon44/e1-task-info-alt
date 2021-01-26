package com.fossfloors.e1tasks.backend.entity;

public enum TaskType {
  TASK_VIEW("00"), INTERACTIVE("01"), BATCH("02"), FOLDER("07"), URL("08"), USER_DEFINED("11");

  private String codeValue;

  private TaskType(String codeValue) {
    this.codeValue = codeValue;
  }

  public String getCodeValue() {
    return codeValue;
  }

  public static TaskType fromCodeValue(String value) {
    if (value == null) {
      return null;
    }

    switch (value) {
      case "00":
        return TASK_VIEW;
      case "01":
        return INTERACTIVE;
      case "02":
        return BATCH;
      case "07":
        return FOLDER;
      case "08":
        return URL;
      case "11":
        return USER_DEFINED;
      default:
        return null;
    }
  }

}
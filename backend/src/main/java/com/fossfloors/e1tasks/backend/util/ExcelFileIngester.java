package com.fossfloors.e1tasks.backend.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fossfloors.e1tasks.backend.entity.AbstractEntity;
import com.fossfloors.exceljson.converter.ExcelToJsonConverter;
import com.fossfloors.exceljson.pojo.ExcelWorkbook;
import com.fossfloors.exceljson.pojo.ExcelWorksheet;

public interface ExcelFileIngester<T extends AbstractEntity> {

  default Set<T> ingestFile(String file) throws InvalidFormatException, IOException {
    Set<T> result = new HashSet<>();
    ExcelToJsonConverter converter = new ExcelToJsonConverter();

    ExcelWorkbook wb = converter.convert(file);
    List<ExcelWorksheet> sheets = (List<ExcelWorksheet>) wb.getSheets();
    ExcelWorksheet sheet = sheets.get(0);
    List<List<Object>> data = sheet.getData();

    // Loop to process each row as an entity. Skip the first row as it is just header information.
    for (int i = 1; i < data.size(); i++) {
      T entity = processRow(data.get(i));
      result.add(entity);
    }

    return result;
  }

  T processRow(List<Object> row);

}

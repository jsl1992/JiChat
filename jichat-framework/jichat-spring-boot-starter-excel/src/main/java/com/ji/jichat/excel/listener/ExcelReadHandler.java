package com.ji.jichat.excel.listener;



import com.ji.jichat.excel.dto.ExcelErrorField;

import java.util.List;

public interface ExcelReadHandler<T> {

    void onSuccess(int sheetIndex, int rowIndex, T entity);

    void onError(int sheetIndex, int rowIndex, List<ExcelErrorField> errorFields);
}

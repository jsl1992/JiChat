package com.ji.jichat.excel.listener;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Maps;
import com.ji.jichat.excel.dto.ExcelImportResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * excel导入通用监听类
 *
 * @author jishenglong on 2019/12/9 11:06
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ExcelListener extends AnalysisEventListener {


    private ExcelImportResult result;

    private List<String> notAllowedRepeatProperties;

    private Map<String, Object> repeatMap = Maps.newHashMap();

    public ExcelListener(ExcelImportResult result, String[] notAllowedRepeatProperties) {
        this.result = result;
        this.notAllowedRepeatProperties = new ArrayList<>(Arrays.asList(notAllowedRepeatProperties));
    }

    /**
     * 根据属性名获取该类此属性的值
     *
     * @param fieldName 对象属性名
     * @param object    目标对象
     * @return Object 返回对象
     */
    private static Object getValueByFieldName(String fieldName, Object object) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter);
            return method.invoke(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        final boolean checkValue = checkValue(object, context);
        if (checkValue) {
            result.getSuccessList().add(object);
        }
    }

    /**
     * 根据业务自行实现该方法
     */
    private boolean checkValue(Object object, AnalysisContext context) {
        boolean checkFlag = true;
        if (object == null) {
            return true;
        }
        // 获取当前 Excel 行的索引
        final Integer rowIndex = context.readRowHolder().getRowIndex();
//         // 获取对象的所有字段
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (null != excelProperty) {
                final String[] value = excelProperty.value();
                String columnName = value[0];
                //校验是否重复
                checkFlag = checkValueIsRepeat(object, checkFlag, rowIndex, field, columnName);
                checkFlag = checkValueByAnnotation(object, checkFlag, rowIndex, field, columnName);
            }

        }
        return checkFlag;
    }

    /**
     * 校验值是否重复
     *
     * @param object     对象
     * @param checkFlag  校验结果
     * @param rowIndex   行数
     * @param field      对象属性
     * @param columnName 导入excel列名
     * @return boolean 校验结果
     * @author jishenglong on 2019/12/14 14:53
     **/
    private boolean checkValueIsRepeat(Object object, boolean checkFlag, Integer rowIndex, Field field, String columnName) {
        if (!this.notAllowedRepeatProperties.isEmpty()) {
            final String fieldName = field.getName();
            final boolean containsFlag = this.getNotAllowedRepeatProperties().contains(fieldName);
            if (containsFlag) {
                //当前字段包含不允许重复校验
                final Object fieldValue = getValueByFieldName(fieldName, object);
                if (fieldValue != null) {
                    //只对不为空的字段校验
                    final boolean containsKey = repeatMap.containsKey(fieldName + fieldValue);
                    if (containsKey) {
                        final String msg = String.format("第[%s]行【%s】:%s和其他行数据重复", rowIndex, columnName, fieldValue);
                        result.getFailList().add(msg);
                        checkFlag = false;
                    }
                    repeatMap.put(fieldName + fieldValue, fieldValue);
                }

            }
        }
        return checkFlag;
    }

    /**
     * 校验含有注解字段的值是否合法
     *
     * @param object     对象
     * @param checkFlag  校验结果
     * @param rowIndex   行数
     * @param field      对象属性
     * @param columnName 导入excel列名
     * @return boolean
     * @author jishenglong on 2019/12/14 14:34
     **/
    private boolean checkValueByAnnotation(Object object, boolean checkFlag, Integer rowIndex, Field field, String columnName) {
        final Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if (annotation instanceof NotNull) {
                final Object fieldValue = getValueByFieldName(field.getName(), object);
                if (fieldValue == null) {
                    final String msg = String.format("第[%s]行【%s】不能为空", rowIndex, columnName);
                    result.getFailList().add(msg);
                    checkFlag = false;
                }
            }

            if (annotation instanceof Min) {
                final Min min = (Min) annotation;
                final long minValue = min.value();
                final Object fieldValue = getValueByFieldName(field.getName(), object);
                if (fieldValue == null || Long.valueOf(fieldValue + "") < minValue) {
                    final String msg = String.format("第[%s]行【%s】不能小于%s", rowIndex, columnName, minValue);
                    result.getFailList().add(msg);
                    checkFlag = false;
                }
            }

            if (annotation instanceof Max) {
                final Max max = (Max) annotation;
                final long maxValue = max.value();
                final Object fieldValue = getValueByFieldName(field.getName(), object);
                if (fieldValue == null || Long.valueOf(fieldValue + "") > maxValue) {
                    final String msg = String.format("第[%s]行【%s】不能大于%s", rowIndex, columnName, maxValue);
                    result.getFailList().add(msg);
                    checkFlag = false;
                }
            }


        }
        return checkFlag;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /*
            datas.clear();
            解析结束销毁不用的资源
         */
    }


}
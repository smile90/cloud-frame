package com.frame.mybatis.search;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询数据
 * @author duancq
 * 2016年8月27日 下午1:19:43
 */
@Data
public class SearchData {

	/** 查询的字段名称 */
	private String name;

	/** 查询条件的类别 */
	private SearchType searchType;

	/** 查询的字段类别 */
	private ValueType valueType;

	/** 查询的字段值 */
	private String value;

	public SearchData() {}

	public SearchData(String name, SearchType searchType, ValueType valueType, String value) {
		this.name = name;
		this.searchType = searchType;
		this.valueType = valueType;
		this.value = value;
	}

    public Object getRealValue() {
        switch (valueType) {
            case INTEGER:
                return Integer.valueOf(value);
            case LONG:
                return Long.valueOf(value);
            case FLOAT:
                return Float.valueOf(value);
            case DOUBLE:
                return Double.valueOf(value);
            case STRING:
                return value;
            case DATE:
                return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            case DATETIME:
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            case LIST_INTEGER: {
                return Arrays.stream(value.split(","))
                        .map(v -> Integer.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case LIST_LONG: {
                return Arrays.stream(value.split(","))
                        .map(v -> Long.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case LIST_FLOAT: {
                return Arrays.stream(value.split(","))
                        .map(v -> Float.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case LIST_DOUBLE: {
                return Arrays.stream(value.split(","))
                        .map(v -> Double.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case LIST_STRING: {
                return Arrays.stream(value.split(","))
                        .map(v -> v.trim())
                        .collect(Collectors.toList());
            }

            default:
                return value;
        }
    }

}

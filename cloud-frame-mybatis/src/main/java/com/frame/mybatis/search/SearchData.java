package com.frame.mybatis.search;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.StringUtils;

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
        switch (searchType) {
            case IN:
                return more();
            case NOT_IN:
                return more();
            default:
                return one();
        }
    }

    public Object one() {
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
            default:
                return value;
        }
    }

    public List<?> more() {
        switch (valueType) {
            case INTEGER: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> Integer.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case LONG: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> Long.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case FLOAT: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> Float.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case DOUBLE: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> Double.valueOf(v.trim()))
                        .collect(Collectors.toList());
            }
            case STRING: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> v.trim())
                        .collect(Collectors.toList());
            }
            case DATE: {
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> LocalDateTime.parse(v.trim(), DateTimeFormatter.ISO_LOCAL_DATE))
                        .collect(Collectors.toList());
            }
            case DATETIME:
                return Arrays.stream(value.split(","))
                        .filter(v -> StringUtils.hasText(v))
                        .map(v -> LocalDateTime.parse(v.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .collect(Collectors.toList());
            default:
                return Lists.newArrayList(value);
        }
    }

}

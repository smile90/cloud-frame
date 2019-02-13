package com.frame.mybatis.search;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frame.mybatis.enums.MybatisMsgResult;
import com.frame.mybatis.exception.SearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class SearchBuilder<T> {

    private QueryWrapper<T> wrapper = new QueryWrapper<>();

    public SearchBuilder() {}

    public SearchBuilder<T> build(String name, SearchType searchType, ValueType valueType, String value) {
        return build(new SearchData(name, searchType, valueType, value));
    }

    public SearchBuilder<T> build(SearchData searchData) {
        // 为空，直接跳过，什么也不做
        if (searchData == null
                || !StringUtils.hasText(searchData.getName())
                || searchData.getSearchType() == null
                || searchData.getValueType() == null) {
            log.warn("searchData param is null. name:{},searchType:{},valueType:{},value:{}", searchData.getName(), searchData.getSearchType(), searchData.getValueType(), searchData.getValue());
            return this;
        }
        if(!StringUtils.hasText(searchData.getValue())) {
            return this;
        }
        switch (searchData.getSearchType()) {
            case EQ: {
                wrapper.eq(searchData.getName(), searchData.getRealValue());
                break;
            }
            case NE: {
                wrapper.ne(searchData.getName(), searchData.getRealValue());
                break;
            }
            case LT: {
                wrapper.lt(searchData.getName(), searchData.getRealValue());
                break;
            }
            case LE: {
                wrapper.le(searchData.getName(), searchData.getRealValue());
                break;
            }
            case GT: {
                wrapper.gt(searchData.getName(), searchData.getRealValue());
                break;
            }
            case GE: {
                wrapper.ge(searchData.getName(), searchData.getRealValue());
                break;
            }
            case IN: {
                wrapper.in(searchData.getName(), (List) searchData.getRealValue());
                break;
            }
            case NOT_IN: {
                wrapper.notIn(searchData.getName(), (List) searchData.getRealValue());
                break;
            }
            case LIKE: {
                wrapper.like(searchData.getName(), searchData.getRealValue());
                break;
            }
            default:
                throw new SearchException(MybatisMsgResult.SEARCH_BUILDER_ERROR.getCode(),
                        String.format("build search error. searchData:%s", searchData),
                        MybatisMsgResult.SEARCH_BUILDER_ERROR.getShowMsg());
        }
        return this;
    }

    public QueryWrapper<T> build() {
        return wrapper;
    }
}

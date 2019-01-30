package com.frame.mybatis.search;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frame.mybatis.enums.MybatisMsgResult;
import com.frame.mybatis.exception.SearchException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.StringUtils;

@Slf4j
public class SearchBuilder<T> {

    private QueryWrapper<T> entityWrapper = new QueryWrapper<>();

    public SearchBuilder() {}

    public SearchBuilder<T> build(String name, SearchType searchType, ValueType valueType, String value) {
        // 为空，直接跳过，什么也不做
        if (!StringUtils.hasText(name) || searchType == null || valueType == null || !StringUtils.hasText(value)) {
            return this;
        } else {
            return build(new SearchData(name, searchType, valueType, value));
        }
    }

    public SearchBuilder<T> build(SearchData searchData) {
        // 为空，直接跳过，什么也不做
        if (searchData == null
                || !StringUtils.hasText(searchData.getName())
                || searchData.getSearchType() == null
                || searchData.getValueType() == null
                || !StringUtils.hasText(searchData.getValue())) {
            return this;
        }
        switch (searchData.getSearchType()) {
            case EQ: {
                entityWrapper.eq(searchData.getName(), searchData.getRealValue());
                break;
            }
            case NE: {
                entityWrapper.ne(searchData.getName(), searchData.getRealValue());
                break;
            }
            case LT: {
                entityWrapper.lt(searchData.getName(), searchData.getRealValue());
                break;
            }
            case LE: {
                entityWrapper.le(searchData.getName(), searchData.getRealValue());
                break;
            }
            case GT: {
                entityWrapper.gt(searchData.getName(), searchData.getRealValue());
                break;
            }
            case GE: {
                entityWrapper.ge(searchData.getName(), searchData.getRealValue());
                break;
            }
            case IN: {
                entityWrapper.in(searchData.getName(), searchData.getRealValue());
                break;
            }
            case LIKE: {
                entityWrapper.like(searchData.getName(), searchData.getRealValue());
                break;
            }
            default:
                throw new SearchException(MybatisMsgResult.SEARCH_BUILDER_ERROR.getCode(),
                        String.format("build search error. searchData:%s", searchData),
                        MybatisMsgResult.SEARCH_BUILDER_ERROR.getShowMsg());
        }
        return this;
    }

    public Wrapper<T> build() {
        return entityWrapper;
    }
}

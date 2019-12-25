package com.frame.boot.sharding;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * 按照(按月)日期分表
 * @author: duanchangqing90
 * @date: 12/11/19
 */
@Slf4j
public class DateMonthShardingAlgorithm implements PreciseShardingAlgorithm, RangeShardingAlgorithm {

    @Override
    public String doSharding(Collection targetNames, PreciseShardingValue value) {
        log.info("collection:" + JSON.toJSONString(targetNames) + ",PreciseShardingValue:" + JSON.toJSONString(value));
        return "test_201901";
    }

    @Override
    public Collection<String> doSharding(Collection targetNames, RangeShardingValue value) {
        log.info("collection:" + JSON.toJSONString(targetNames) + ",RangeShardingValue:" + JSON.toJSONString(value));
        return null;
    }
}

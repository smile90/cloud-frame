package com.frame;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SentinelConfig {

//    @Bean
//    public Converter<String, List<FlowRule>> convert() {
//        return source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>(){});
//    }

//    @Bean
//    public Converter myParser() {
//        return new JsonFlowRuleListParser();
//    }
}

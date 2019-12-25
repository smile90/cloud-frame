package com.frame.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试入口
 * @author: smile
 * @date: 2019/04/09
 */
@Slf4j
public class MainTest {
    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        String json = "{\"orderStatus\":\"SUCCESS\",\"productBodyCode\":\"OFFLINE\",\"productType\":\"JRF\",\"cycle\":null,\"orderNo\":\"SP190831N9242797727\",\"transOrderNo\":\"V_DFE6A34BF0A66258190831\",\"systemFlowId\":\"BMLUPGUGJGH2MCSB7DM0\",\"completeTime\":1567213641000,\"amount\":586.34,\"customerFee\":1.17,\"bankCost\":0.0,\"customerNo\":\"8613947295\",\"agentNo\":\"8620841866\",\"fundChannelCode\":\"POS_KYTJYL_00001\",\"fundOrganizationCode\":\"KAYOU\",\"fundBodyCode\":\"PAY\",\"costAmount\":0.0,\"commissionAmount\":1.17,\"description\":null,\"status\":\"NORMAL\",\"countDate\":1567180800000,\"businessCode\":null,\"dockingOrganization\":\"29000000\",\"expansionInstitution\":null,\"commBussType\":null,\"activityCode\":null,\"activityName\":null,\"posSn\":null,\"signOrganization\":\"KAYOU\",\"canalCode\":\"KAYOU\",\"customerBrand\":\"29000000\",\"productNo\":\"A20140218002\",\"ydxAgentCommission\":0.17,\"ydxAgentCost\":0.83}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject.getDate("completeTime"));
        System.out.println(jsonObject.getDate("countDate"));
    }

    public static void test() {
        for(int i = 0;;i++) {
            List<TestString> test = new ArrayList<>();
            for (int a = 0; a <= 10000; a++) {
                test.add(new TestString(i + "," + a + "," + "测试文本。。。。。。。。。。。。。。"));
            }
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                log.error(null, e);
            }

            ThreadExecutorTemplate.execute("test", 5, 10, 20, test, s -> {
                log.info("content:{}", s);
            });
        }
    }
}

@Data
@AllArgsConstructor
class TestString {
    private String test;
}

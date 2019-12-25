package com.frame.producer;

import org.apache.rocketmq.common.message.MessageExt;

public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("customerAppServiceGroup");
        // Specify name server addresses.
//        producer.setNamesrvAddr("172.20.6.49:9876;172.20.6.50:9876");
        producer.setNamesrvAddr("10.10.129.43:9876");
        producer.setInstanceName("customerAppService");
        producer.setMaxMessageSize(131072);
        //Launch the instance.
        producer.setVipChannelEnabled(false);
        producer.start();

        //Create a message instance, specifying topic, tag and message body.
        String msgStr = "{\"activateCode\":\"6BGP94\",\"phone\":\"13739659524\",\"customerName\":\"韩鑫鑫\",\"customerNo\":\"86261619426\"}";
        MessageExt msg = new MessageExt();
        msg.setFlag(1);
        msg.setMsgId("test");
        msg.setKeys("test");
        msg.setTopic("used_activationcode_topic");
        msg.setBody(msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
//{"activateCode":"6BGP94","phone":"13739659524","customerName":"韩鑫鑫","customerNo":"86261619426"}

        //Call send message to deliver message to one of brokers.
        System.out.printf("%s%n", msg);
        producer.send(msg);

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
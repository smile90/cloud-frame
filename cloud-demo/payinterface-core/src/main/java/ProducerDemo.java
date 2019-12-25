import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class ProducerDemo {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer();
		// 测试环境a
		producer.setProducerGroup("customerAppServiceGroup");
		producer.setNamesrvAddr("172.20.6.49:9876;172.20.6.50:9876");
		producer.setInstanceName("customerAppService");
		producer.setMaxMessageSize(1000000);
		// producer.setNamesrvAddr("172.28.14.53:9876");
		producer.start();

		Message message = new Message("used_activationcode_topic",
				"*",
				"6BGP94",
				new String("{\"activateCode\":\"6BGP94\",\"phone\":\"13739659524\",\"customerName\":\"韩鑫鑫\",\"customerNo\":\"86261619426\"}").getBytes("UTF-8"));
		message.setFlag(1);
		SendResult result = producer.send(message);
		System.out.println("消息id为:  " + result.getMsgId() + "  发送状态为:" + result.getSendStatus());

	}
}
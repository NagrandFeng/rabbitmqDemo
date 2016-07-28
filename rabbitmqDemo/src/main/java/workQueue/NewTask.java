package workQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * 发送端
 */
public class NewTask {
    //队列名称
    private final static String QUEUE_NAME = "workqueue_persistence";

    public static void main(String[] args) throws Exception {
        //创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列
        boolean durable = true;// 1、设置队列持久化
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
//      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 7; i >0; i--) {
            String dots = "";
            for (int j = 0; j <= i; j++) {
                dots += ".";
            }
            String message = "helloworld" + dots + dots.length();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//            channel.basicPublish("logs", "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        //关闭频道和资源
        channel.close();
        connection.close();

    }
}

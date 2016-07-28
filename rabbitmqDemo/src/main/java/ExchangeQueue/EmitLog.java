package ExchangeQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/12.
 */
public class EmitLog {
    public static final String EXCHANGE_QUEUE="ex_log";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_QUEUE,"fanout");
        String message = new Date().toLocaleString()+" : log something";
        // 往转发器上发送消息
        channel.basicPublish(EXCHANGE_QUEUE, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

}

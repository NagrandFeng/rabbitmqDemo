package DirectExchangeQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.*;

/**
 * Created by Administrator on 2016/7/12.
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "ex_logs_direct";
    private static final String[] SEVERITIES = {"info", "warning", "error"};

    public static void main(String[] argv) throws Exception {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明转发器的类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //发送6条消息
        for (int i = 0; i < 6; i++) {
            int ranVal = getSeverity();
            String severity=SEVERITIES[ranVal];
            String message=severity + "_log : "+new Date().toLocaleString()+" ";
            switch (ranVal){
                case 0:message+="this is info log";break;
                case 1:message+="this is warning log";break;
                case 2:message+="this is error log";break;
            }
//            String message = severity + "_log : " + UUID.randomUUID().toString();
            // 发布消息至转发器，指定routingkey
            channel.basicPublish(EXCHANGE_NAME, severity, null, message
                    .getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    /**
     * 随机产生一种日志类型
     *
     * @return
     */
    private static int getSeverity() {
        Random random = new Random();
        int ranVal = random.nextInt(3);
        return ranVal;
    }

   /* private static Map<Integer,String> getSeverity2() {
        Map<Integer,String> result=new HashMap<Integer, String>();
        Random random = new Random();
        int ranVal = random.nextInt(3);
        result.put(ranVal,SEVERITIES[ranVal]);
        return result;
    }
    private static String getMessage(int ranVal,String severity){
        String log_info=severity+"_log : ";
        switch (ranVal){
            case 0:log_info+="this is info log";break;
            case 1:log_info+="this is warning log";break;
            case 2:log_info+="this is error log";break;
        }
        return log_info;
    }
*/
}

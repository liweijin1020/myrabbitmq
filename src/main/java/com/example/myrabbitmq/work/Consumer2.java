package com.example.myrabbitmq.work;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work工作模式：
 * 消费者1：从队列中获取消息
 * @author norhtking
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2 创建通道
        Channel channel = connection.createChannel();
        // 3 声明队列
        channel.queueDeclare(Producer.QUEUE_NAME,false,false,false,null);
        // 4 设置每次可以获取多少个信息
        channel.basicQos(1);
        // 5 创建消费者
        DefaultConsumer comsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("路由key为：" + envelope.getRoutingKey());
//                System.out.println("交换机为：" + envelope.getExchange());
//                System.out.println("消息id为：" + envelope.getDeliveryTag());
                System.out.println("接收消息为：" + new String(body,"utf-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // 6 监听消息
        /**
         * 参数1：队列名
         * 参数2：是否自动确认，设置为true表示消息接收到自动向MQ回复收到，MQ将消息从队列中删除，false则需要手动确认。
         * 参数3：消息的消费者
         */
        channel.basicConsume(Producer.QUEUE_NAME,true,comsumer);
    }
}

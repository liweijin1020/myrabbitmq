package com.example.myrabbitmq.simple;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者从队列中获取消息
 * @author norhtking
 */
public class Recv {
    private final static String QUEUE_NAME = "q_test_01";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2 创建通道
        Channel channel = connection.createChannel();
        // 3 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 4 创建消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为："+ envelope.getRoutingKey());
                System.out.println("交换机为："+ envelope.getRoutingKey());
                System.out.println("消息ID为："+ envelope.getRoutingKey());
                System.out.println("接受到的消息为：" + new String(body,"utf8"));
            }
        };
        // 5 监听队列
        /**
         * 参数1：队列名
         * 参数2：是否自动确认，设置true表示消息接收到自动向MQ回复接收到了，MQ将消息从队列删除，false则需要手动确认
         * 参数3：消息的消费者
         */
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

    /**
     * 已过时
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    private static void recv1() throws IOException, TimeoutException, InterruptedException {
        // 1 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2 从连接中创建通道
        Channel channel = connection.createChannel();
        // 3 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 4 定义队列的消费者(已过时)
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 5 监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}

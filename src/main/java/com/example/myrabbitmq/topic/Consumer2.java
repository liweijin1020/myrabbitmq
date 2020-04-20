package com.example.myrabbitmq.topic;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式（通配符模式 topic）
 * 消费者2
 * @author norhtking
 */
public class Consumer2 {
    /**交换机*/
    public static final String EXCHANGE_NAME = "topic_exchange";
    /**队列*/
    public static final String QUEUE_NAME = "topic_queue_name2";
    /**路由key*/
    public static final String ROUTE_KEY = "goods.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        Channel channel = connection.createChannel();
        /**3声明交换机*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        /**4声明队列*/
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        /**5绑定队列到交换机*/
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTE_KEY);
        /**6同一时刻服务器只会发送一条消息给消费者*/
        channel.basicQos(1);
        /**7创建消费者*/
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /**接收消息*/
                System.out.println("消费者1接收的消息为：" + new String(body,"UTF-8") + "\t路由key：" + envelope.getRoutingKey());
            }
        };
        /**8监听队列*/
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}

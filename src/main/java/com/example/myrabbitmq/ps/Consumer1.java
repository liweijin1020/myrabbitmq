package com.example.myrabbitmq.ps;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式：
 * 消费者1：使用发布与订阅模式，接收消息
 * @author norhtking
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        Channel channel = connection.createChannel();
        /**3声明交换机*/
        channel.exchangeDeclare(Producer.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        /**4声明队列*/
        channel.queueDeclare(Producer.QUEUE_NAME1,true,false,false,null);
        /**5队列绑定到交换机上
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key，fanout类型不用设置路由key
         * */
        channel.queueBind(Producer.QUEUE_NAME1,Producer.EXCHANGE_NAME,"");
        /**6创建消费者*/
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /**接收消息*/
                System.out.println("消费者1接收到的消息为:"+new String(body,"utf-8"));
            }
        };
        /**7监听队列
         * 参数1：队列名称
         * 参数2：是否自动确认，设置true表示消息接收到自动向MQ回复接收到了，MQ将消息从队列删除，false则需要手动确认
         * 参数3：接收消息的消费者
         * */
        channel.basicConsume(Producer.QUEUE_NAME1,true,consumer);
    }
}

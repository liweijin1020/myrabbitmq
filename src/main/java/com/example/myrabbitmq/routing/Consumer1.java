package com.example.myrabbitmq.routing;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式（direct）：
 * 消费者1
 * @author norhtking
 */
public class Consumer1 {
    /**队列名称*/
    public static final String QUEUE_NAME = "direct_queue_name1";
    /**交换机*/
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    /**路由key*/
    public static final String ROUTE_KEY_DELETE = "delete";
    public static final String ROUTE_KEY_INSERT = "insert";
    public static final String ROUTE_KEY_UPDATE = "update";
    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        final Channel channel = connection.createChannel();
        /**3声明交换机*/
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
        /**4声明队列
         * 参数1：队列名称
         * 参数2：是否持久化
         * 参数3：是否独占本资源
         * 参数4：不用时是否自动删除
         * 参数5：其他参数
         * */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        /**5绑定队列
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key
         * */
        channel.queueBind(QUEUE_NAME,DIRECT_EXCHANGE_NAME,ROUTE_KEY_DELETE);
        channel.queueBind(QUEUE_NAME,DIRECT_EXCHANGE_NAME,ROUTE_KEY_INSERT);
        /**6同一时刻服务器只会发一条消息给消费者*/
        channel.basicQos(1);
        /**7创建消费者*/
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收的消息为：" + new String(body,"utf-8"));
            }
        };
        /**8监听队列
         * 参数1：队列名称
         * 参数2：是否自动确认
         * 参数3：消费者
         * */
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}

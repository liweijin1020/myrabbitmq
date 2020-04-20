package com.example.myrabbitmq.routing;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式（direct）：
 * 生产者将消息发送到队列，并设置路由key
 * @author norhtking
 */
public class Producer {
    /**交换机*/
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    /**队列名称*/
    public static final String QUEUE_NAME = "direct_queue_name1";
    /**路由key*/
    public static final String ROUTE_KEY = "delete";
    public static final String ROUTE_KEY1 = "insert";
    public static final String ROUTE_KEY2 = "update";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        Channel channel = connection.createChannel();
        /**3声明交换机
         * 参数1：交换机名称
         * 参数2：交换机类型
         * */
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        /**4发送消息*/
        String message = "删除商品";
        /***
         * 参数1：交换机名称
         * 参数2：路由key
         * 参数3：其他参数
         * 参数4：消息内容
         */
        channel.basicPublish(DIRECT_EXCHANGE_NAME,ROUTE_KEY,null,message.getBytes());
        channel.basicPublish(DIRECT_EXCHANGE_NAME,ROUTE_KEY1,null,message.getBytes());
        channel.basicPublish(DIRECT_EXCHANGE_NAME,ROUTE_KEY2,null,message.getBytes());
        System.out.println("[x] Sent '"+ROUTE_KEY+"':'" + message);
        /**5关闭资源*/
        channel.close();
        connection.close();
    }
}

package com.example.myrabbitmq.topic;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式（通配符模式 topic）
 * 生产者
 * @author norhtking
 */
public class Producer {
    /**交换机名称*/
    public static final String EXCHANGE_NAME = "topic_exchange";
    /**路由key*/
    public static final String ROUTE_KEY_GOODS_ADD = "goods.add";
    public static final String ROUTE_KEY_GOODS_UPDATE = "goods.update";
    public static final String ROUTE_KEY_GOODS_DELETE = "goods.delete";
    public static final String ROUTE_KEY_GOODS_DELETE_ID = "goods.delete.id";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        Channel channel = connection.createChannel();
        /**3声明交换机*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        /**4发送消息*/
        String message = "商品信息";
        channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY_GOODS_ADD,null,message.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY_GOODS_UPDATE,null,message.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY_GOODS_DELETE,null,message.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY_GOODS_DELETE_ID,null,message.getBytes("UTF-8"));
        System.out.println("[x] Sent message:" + message);
        /**关闭资源*/
        channel.close();
        connection.close();
    }
}

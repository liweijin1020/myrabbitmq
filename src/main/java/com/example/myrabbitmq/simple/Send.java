package com.example.myrabbitmq.simple;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者：消息发送到队列
 * @author norhtking
 */
public class Send {
    private final static String QUEUE_NAME = "q_test_01";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2 通过连接创建通道
        Channel channel = connection.createChannel();
        /** 3 声明（创建）队列
         * 参数1：队列名称
         * 参数2：是否定义持久化
         * 参数3：是否独占本连接
         * 参数4：是否不用时自动删除
         * 参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 4 发送消息内容
        String message = "Hell World!";
        /**
         * 参数1：交换机名称，空串使用迷人交换机
         * 参数2：路由key，简单模式中，可以使用队列名称
         * 参数3：消息其他属性
         * 参数4：消息内容
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("[x] Send '" + message+ "'");

        // 5 关闭连接
        channel.close();
        connection.close();
    }
}

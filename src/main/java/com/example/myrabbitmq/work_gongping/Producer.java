package com.example.myrabbitmq.work_gongping;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work工作模式：公平分发
 * @author norhtking
 */
public class Producer {
    public static final String QUEUE_NAME = "work_gongping_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1 获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2 创建通道
        Channel channel = connection.createChannel();
        // 3 声明队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化
         * 参数3：是否独占本连接
         * 参数4：是否不用时自动删除
         * 参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 4 发送消息
        for (int i = 0; i < 100; i++) {
            String message = "" + i;
            /**
             * 参数1：交换机名称，空字符串表示默认交换机
             * 参数2：路由key，简单模式可以是队列名称
             * 参数3：其他属性
             * 参数4：消息内容
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("已发送消息：" + message);
        }
        // 5 关闭资源
        channel.close();
        connection.close();
    }
}

package com.example.myrabbitmq.ps;

import com.example.myrabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式：生产者
 * 交换机：fanout
 * @author norhtking
 */
public class Producer {

    /** 交换机 */
    public static final String EXCHANGE_NAME = "fanout_exchange";
    /** 队列名称 */
    public static final String QUEUE_NAME1 = "fanout_queue1";
    public static final String QUEUE_NAME2 = "fanout_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**1获取连接*/
        Connection connection = ConnectionUtil.getConnection();
        /**2创建通道*/
        Channel channel = connection.createChannel();
        /**
         * 3声明交换机
         * 参数1：交换机名称
         * 参数2：交换机类型(fanout,direct,topic)
         * */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        /**
         * 4声明队列
         * 参数1：队列名称
         * 参数2：是否持久化
         * 参数3：是否独占本连接
         * 参数4：是否不用时自动删除
         * 参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
        /**5发送消息*/
        for (int i = 0; i < 10; i++) {
            String message = "发布与订阅---"+ i;
            /**
             * 参数1：交换机名称
             * 参数2：路由key，简单模式中，可以使用队列名称，这里不需要设置路由key
             * 参数3：消息其他属性
             * 参数4：消息内容
             */
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("已发送消息："+message);
        }
        /**6关闭资源*/
        channel.close();
        connection.close();
    }
}

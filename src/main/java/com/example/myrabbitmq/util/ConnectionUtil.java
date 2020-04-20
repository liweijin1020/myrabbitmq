package com.example.myrabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 连接工具类
 * @author norhtking
 */
public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        // 1 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2 设置服务地址
        factory.setHost("192.168.10.129");
        // 3 设置端口
        factory.setPort(5672);
        // 4 设置登录信息，用户名、密码、vhost
        factory.setUsername("weijin.li");
        factory.setPassword("weijin.li");
        factory.setVirtualHost("testhost");
        // 5 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}

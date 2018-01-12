package com.zhuhai.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * Date: 2018/1/12
 * Time: 14:24
 *
 * @author: zhuhai
 */
public class TopicProduder {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "topic-message";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建目标地址
        Destination destination = session.createTopic(TOPIC_NAME);
        //6.创建生产者
        MessageProducer producer = session.createProducer(destination);
        //7.创建消息
        for (int i = 0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage("Hello, Topic Message!" + i);
            //8.发送消息
            producer.send(textMessage);
        }

        //9.释放资源
        producer.close();
        session.close();
        connection.close();
    }
}

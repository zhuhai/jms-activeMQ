package com.zhuhai.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 2018/1/12
 * Time: 17:03
 *
 * @author: zhuhai
 */
public class RetryQueueConsumer {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "queue-message";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        //自定义重试机制
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //重试次数
        redeliveryPolicy.setMaximumRedeliveries(5);
        //第一次重试的延迟时间
        redeliveryPolicy.setInitialRedeliveryDelay(3000L);
        //每次重试的延迟时间
        //redeliveryPolicy.setRedeliveryDelay(3000);
        //启用指数倍数递增的方式增加延迟时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //递增倍数，默认为5
        redeliveryPolicy.setBackOffMultiplier(2);
        //最大延迟时间，当重试时间间隔大于最大延迟时间时，以后每次重连时间都为最大延迟时间
        redeliveryPolicy.setMaximumRedeliveryDelay(10000L);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    if ("Hello,Queue Message!5".equals(textMessage.getText())) {
                        System.out.println("系统时间：" + new Date());
                        System.out.println(10/0);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}


package com.zhuhai.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/15
 * Time: 21:43
 *
 * @author: hai
 */
@Component
public class MyJmsListener {

    @JmsListener(destination = "message-queue", containerFactory = "queueListenerContainerFactory")
    public void getMessageFromQueue(String message) {
        System.out.println(message);
    }

    /*@JmsListener(destination = "message-topic", containerFactory = "topicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        System.out.println(message);
    }*/

    @JmsListener(destination = "message-topic", containerFactory = "topicListenerContainerFactory")
    public void getMessageFromTopic(Message message, Session session) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(textMessage.getText());
            if (1 == 1) {
                throw new RuntimeException("故意的异常");
            }
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

}

package com.zhuhai.jms.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/14
 * Time: 22:53
 *
 * @author: hai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringJmsTest {

    @Resource
    private JmsTemplate jmsQueueTemplate;
    @Resource
    private JmsTemplate jmsTopicTemplate;

    @Test
    public void sendMessageToQueue() throws IOException {
        jmsQueueTemplate.send("message-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("Hello, spring queue message");
                return textMessage;
            }
        });
        System.in.read();
    }

    @Test
    public void sendMessageToTopic() {
        jmsTopicTemplate.send("message-topic", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("Hello, spring topic message");
                return textMessage;
            }
        });
    }
}

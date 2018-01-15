package com.zhuhai.jms;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * Date: 2018/1/15
 * Time: 17:05
 *
 * @author: zhuhai
 */
@Component
public class RetryQueueMessageListener implements SessionAwareMessageListener {

    @Override
    public void onMessage(Message message, Session session) {

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

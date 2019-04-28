package com.demo.test;

import static org.junit.Assert.*;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class MessageBrokerTestCase {

  @Test
  public void test()throws Exception{
    ActiveMQConnectionFactory conFactory=new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
    conFactory.setAlwaysSessionAsync(true);
    Connection con=conFactory.createConnection();
    con.start();
   
    Session session=con.createSession(true,Session.CLIENT_ACKNOWLEDGE);
    
    Topic topic=session.createTopic("floor1");
    MessageProducer producer=session.createProducer(topic);
    producer.send( session.createTextMessage("hello"));
    System.out.println("message sended");
    producer.close();
    session.close();
    
    
    
    System.in.read();
    con.close();
  }
  @Test
  public void consumer()throws Exception{
    ActiveMQConnectionFactory conFactory=new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
    conFactory.setAlwaysSessionAsync(true);
    Connection con=conFactory.createConnection();
    con.start();
    Session session=con.createSession(true,Session.CLIENT_ACKNOWLEDGE);
    Topic topic=session.createTopic("floor1");
   MessageConsumer consumer= session.createConsumer(topic);
   consumer.setMessageListener(new MessageListener() {
    
    @Override
    public void onMessage(Message message) {
      String val;
      try {
        val = ((TextMessage)message).getText();
        System.out.println(val);
      } catch (JMSException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     
    }
  });
    
    
    
    
    
    System.in.read();
    con.close();
  }
  
}

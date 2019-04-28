package com.demo.config;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class BeanConfiguration {

  @Bean
 public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewSolver = new InternalResourceViewResolver();
    viewSolver.setPrefix("WEB-INF/views/");
    viewSolver.setSuffix(".jsp");
    return viewSolver;
  }
  @Bean
  public ActiveMQConnectionFactory activeMqConnectionFactory() {
    ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory();
    factory.setBrokerURL("tcp://localhost:61616");
    factory.setAlwaysSessionAsync(true);
    return factory;
  }
  @Bean
  public Session session(ActiveMQConnectionFactory factory) {
    try {
      Connection connection=factory.createConnection();
      connection.start();
      return connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    } catch (JMSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }return null;
    
  }
  
}

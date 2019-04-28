package com.demo.controller;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestHandler implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Autowired
  private Session session;

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @GetMapping(value = "/")
  public String indexPage() {
    return "index";
  }

  @PostMapping(value = "/login")
  public ModelAndView loginHandler(@RequestParam("user") String user,
      @RequestParam("password") String password) {
    System.out.println("user=" + user + " password=" + password);
    return new ModelAndView("floorpage");
  }

  @GetMapping(value = "receiveMsg")
  @ResponseBody
  public String receiveMessage(@RequestParam("topic") String topic,
      @RequestParam("message") String message) {
    System.out.println("topic=" + topic + " " + "Message=" + message);
    postMessageInBroker(message);
    return "success";
  }

  private void postMessageInBroker(String message) {

    try {
      Topic topic = session.createTopic("floor1");
      TextMessage txtMsg = session.createTextMessage(message);
      MessageProducer producer = session.createProducer(topic);
      producer.send(txtMsg);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }



}

package com.frognet.test.jms;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SimpleMessageSendandReceiveDemo {
	public static final String user = "252";
	public static final String password = "YWRtaW47NDc4MDJlM2FhYmY3ZWY2OTFmNWNmMmM3ODBmZTU2YmY";
	public static final String url = "tcp://172.16.214.12:61668";
	public static final String queueName = "com.telenavsoftware.queue.chatRecordQueue";
	public static final String messageBody = "{\"type\":\"11\", \"message\":\"hello\", \"sender\":\"1\", \"receiver\":\"548\" }";
	public static final boolean transacted = false;
	public static final boolean persistent = false;
	
    public static void main(String[] args){
    	Connection connection = null;
    	Session session = null;
    	
    	try{
    		// create the connection
    	    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();
            
            // create the session
            session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            
            // create the producer
            MessageProducer producer = session.createProducer(destination);
            if (persistent){
            	producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            }else{
            	producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
            
            // create text message
            Message message = session.createTextMessage(messageBody);
            
            // send the message
            producer.send(message);
            System.out.println("Send message: " + ((TextMessage)message).getText());
            
//            // create the consumer
//            MessageConsumer consumer = session.createConsumer(destination);
//            // blocking till receive the message
//            Message recvMessage = consumer.receive();
//            System.out.println("Receive message: " + ((TextMessage)recvMessage).getText());
            
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			// close session and connection
    		    if (session != null){
    			    session.close();
    		    }
    		    if (connection != null){
    			    connection.close();
    		    }
    		}catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
}

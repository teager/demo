package com.frognet.test.jms;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class SendActiveMQ {

	public static void main(String[] args) {
		sendStompMessage();
	}
	

	private static void sendStompMessage() {
		try {
			StompConnection connection = new StompConnection();
			connection.open("172.16.214.12", 61669);

			connection.connect("252", "YWRtaW47NDc4MDJlM2FhYmY3ZWY2OTFmNWNmMmM3ODBmZTU2YmY");
			StompFrame connect = connection.receive();
			if (!connect.getAction().equals(Stomp.Responses.CONNECTED)) {
				throw new Exception("Not connected");
			}

			connection.send("com.telenavsoftware.queue.chatRecordQueue",
					"{\"type\":\"11\", \"message\":\"hello\", \"sender\":\"123\", \"receiver\":\"548\" }");
					// connection.begin("tx1");
					// connection.send("com.telenavsoftware.queue.chatRecordQueue",
					// "message1", "tx1", null);
					// connection.send("/queue/test", "message2", "tx1", null);
					// connection.commit("tx1");

			// connection.subscribe("/queue/test",
			// Subscribe.AckModeValues.CLIENT);

			// connection.begin("tx2");
			//
			// StompFrame message = connection.receive();
			// System.out.println(message.getBody());
			// connection.ack(message, "tx2");
			//
			// message = connection.receive();
			// System.out.println(message.getBody());
			// connection.ack(message, "tx2");
			//
			// connection.commit("tx2");

			connection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

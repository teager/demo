package com.frognet.test.push;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.exceptions.InvalidSSLConfig;

public class IOSPush {

	public static void main(String[] args) {
		pushNotification("hello");
	}
	
	private static void pushNotification(String message) {
		try {
//			java.lang.System.setProperty(
//			          "jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			
//			  java.lang.System.setProperty(
//			          "jdk.tls.client.protocols", "SSLv3");
			//System.setProperty("https.protocols", "TLSv1.0,TLSv1.1,TLSv1.2");
			System.out.println("send push message:" + message);
			Resource res = new ClassPathResource("production.p12");
			String payload = APNS.newPayload().sound("default").badge(1).alertBody(message).build();
			
			ApnsService service = APNS.newService().withCert(res.getInputStream(), "12345678")
					.withProductionDestination().asPool(2).build();
					//.withGatewayDestination("gateway.push.apple.com", 2195)
					//.withFeedbackDestination("feedback.push.apple.com", 2196).asPool(5).build();
			service.start();
			//service.testConnection();
						  //"7f37defda25287197ab51ea5ac08469a77e67ad2129dd1d058a8a8ed5dcc3571"
			//service.push("54f43557d60ea0374e7703307765979415f4755d", payload);
				
			service.testConnection();
			//service.push("7fcea81dd99a83f431af98f12ba04e21cf664f5fe817e85441f392cc668f9d6c", payload); //ddy 548
			//service.push("9c7b20490c1241b1d23e8e0429fe87c9dff5f6d03f9a5e750da2434e94cdb8be", payload); // dds
			
			// service.push("b4e91c6f0281582773870da8d9d3d9e01a281dc8674307a9c4c07250c6e34be3",
		
			service.stop();
			System.out.println("send push finished");
		} catch (InvalidSSLConfig e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.frognet.test.push;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerConfiguration;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SSLContextUtil;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

public class IOSPusher {
	public static void main(String[] args) {
		try {
			final PushManager<SimpleApnsPushNotification> pushManager = new PushManager<SimpleApnsPushNotification>(
					ApnsEnvironment.getProductionEnvironment(),
					SSLContextUtil.createDefaultSSLContext("c:\\production.p12", "12345678"), null, // Optional:
																									// custom
																									// event
																									// loop
																									// group
					null, // Optional: custom ExecutorService for calling
							// listeners
					null, // Optional: custom BlockingQueue implementation
					new PushManagerConfiguration(), "ExamplePushManager");

			pushManager.start();

//			final byte[] token = TokenUtil.tokenStringToByteArray(
//					"7fcea81dd99a83f431af98f12ba04e21cf664f5fe817e85441f392cc668f9d6c");
			 String deviceToken = "7fcea81dd99a83f431af98f12ba04e21cf664f5fe817e85441f392cc668f9d6c";

			final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

			payloadBuilder.setAlertBody("Ring ring, Neo.");
			payloadBuilder.setSoundFileName("ring-ring.aiff");

			final String payload = payloadBuilder.buildWithDefaultMaximumLength();
			//pushManager.
			pushManager.getQueue().put(new SimpleApnsPushNotification(deviceToken.getBytes(), payload));

			pushManager.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

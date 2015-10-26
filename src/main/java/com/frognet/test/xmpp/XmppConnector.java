package com.frognet.test.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * @author ftao
 */
public class XmppConnector {

	public static String USERNAME = "1";
	public static String PASSWORD = "343b1c4a3ea721b2d640fc8700db0f36";

	public static String USERNAME_GREE2 = "2";
	public static String HOST = "172.16.214.5";
	public static String DOMAIN = "dev.doudouy.com";
	public static int PORT = 5222;

	public static void main( String[] args ) {
		XMPPTCPConnection mConnection;
		 
		XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		config.setUsernameAndPassword(USERNAME + "@" + DOMAIN, PASSWORD);
		config.setServiceName(DOMAIN);
		config.setHost(HOST);
		config.setPort(PORT);
		config.setDebuggerEnabled(true);
		
		
		mConnection = new XMPPTCPConnection(config.build());
//        mConnection.setUseStreamManagement(true);
//        mConnection.setUseStreamManagementResumption(true);
//        mConnection.setPacketReplyTimeout(10000);

        try {
            mConnection.connect();
            mConnection.login();
        } catch (Exception  e) {
            e.printStackTrace();
        }
	}

}

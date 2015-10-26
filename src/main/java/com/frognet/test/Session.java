package com.frognet.test;

import org.apache.commons.codec.binary.Base64;

/**
 * Hello world!
 *
 */
public class Session 
{
    public static void main( String[] args ){
    	
    	System.out.println(createSessionToken());
    }
    
   
    public static String createSessionToken() {
		return Base64.encodeBase64URLSafeString(("qqaffbbcf932e3973233b0deefd74aeaa4" + ";" + "49f24b3064c7a3f524187b28d33acf8a" + ";" + "5").getBytes());
	}
    
}

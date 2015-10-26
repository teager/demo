package com.frognet.test;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.frognet.test.utils.JsonUtils;

import net.sf.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	for(Long uid = 1006945L; uid < 2000000; ++uid){
    		try {
    			JSONObject object = JsonUtils.get("http://info.xiangyue001.com/?c=info&a=getOther&uid=" + uid + "&is_record_visit=1");
    			Thread.sleep(500);
				if(object != null){
					String s = object.getString("s");
					if(s != null && "1".equals(s)){
						JSONObject d = object.getJSONObject("d");
						if(d != null){
							JSONObject data = d.getJSONObject("data");
							if(data != null){
								String sex = data.getString("sex");
								String avatar = data.getString("avatar");
								String otherPhotos = data.getString("other_photos");
								if(otherPhotos.length() > 10){
									String[] photos = otherPhotos.split(" ");
									if(photos.length > 6){
										if(avatar.length() > 5){
											downloadFromUrl(uid, avatar, "d:/");
										}
										for(String photoUrl: photos){
											downloadFromUrl(uid, photoUrl, "d:/");
										}
									}
								}
								 System.out.println( "Hello World!" + otherPhotos );
							}
						}
					}
					//List<String> photoUrls = getUserPhotos(object);
				}
    			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        System.out.println( "Hello World!" );
    }
    
    public static String downloadFromUrl(Long uid, String url,String dir) {  
    	  
        try {  
            URL httpurl = new URL(url);  
            String fileName = getFileNameFromUrl(uid, url);  
            System.out.println(fileName); 
            
            File f = new File(dir + "autoDownloadPhoto/" + uid + "/" + fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(Long uid, String url){  
        String name = new Long(System.currentTimeMillis()).toString() + ".X";  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1);  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }  
}

/*package WXmessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;



public class SendWXMessage {
	public static void main(String[] args) {
		String token=new SendWXMessage().getAccessToken();
		System.out.println(token);
	}
	 
	public String getAccessToken() {
		 
		 String appID="wx77fb9e5a383e07a7";
		 String appScret="0a1cf2571a58d6e9b71dd9b628011ca4";
		 String message ="";
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
		+ appScret;
		
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream(); 
			int size = is.available(); 
			byte[] b = new byte[size];
			is.read(b);
			message = new String(b, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject json = JSONObject.fromObject(message);
		return json.getString("access_token");
	}
}
*/
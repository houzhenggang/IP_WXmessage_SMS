package WXmessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;


public class SendWX {

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm =  new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }  ;
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			// conn.setRequestProperty("content-type",
			// "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("连接超时："+ce);
		} catch (Exception e) {
			System.out.println("https请求异常："+ e);
		}
		return jsonObject;
	}
	public static boolean sendTemplateMsg(String token,Template template){  
        boolean flag=false;  
          
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";  
        requestUrl=requestUrl.replace("ACCESS_TOKEN", token);  
      
        JSONObject jsonResult=SendWX.httpsRequest(requestUrl, "POST", template.toJSON());  
        if(jsonResult!=null){  
            int errorCode=jsonResult.getInt("errcode");  
            String errorMessage=jsonResult.getString("errmsg");  
            if(errorCode==0){  
                flag=true;  
            }else{  
                System.out.println("模板消息发送失败:"+errorCode+","+errorMessage);  
                flag=false;  
            }  
        }  
        return flag;  
}  
	 
		public  static String getAccessToken() {
			 
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
			if(json.containsKey("access_token")){
			return json.getString("access_token");}
			else{
				return null;
			}
		}
//	public static WeiXinOauth2Token getOauth2AccessToken(String appId,
//			String appSecret, String code) {
//		String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
//		WeiXinOauth2Token wat = new WeiXinOauth2Token();
//		String requestUrl = oauth2Url.replace("APPID", appId)
//				.replace("SECRET", appSecret).replace("CODE", code);
//		JSONObject jsonObject = SendWX
//				.httpsRequest(requestUrl, "GET", null);
//		if (null != jsonObject) {
//			try {
//				wat = new WeiXinOauth2Token();
//				wat.setAccessToken(jsonObject.getString("access_token"));
//				wat.setExpiresIn(jsonObject.getInt("expires_in"));
//				wat.setRefeshToken(jsonObject.getString("refresh_token"));
//				wat.setOpenId(jsonObject.getString("openid"));
//				wat.setScope(jsonObject.getString("scope"));
//			} catch (Exception e) {
//				wat = null;
//				String errorCode = jsonObject.getString("errcode");
//				String errorMsg = jsonObject.getString("errmsg");
//				System.out.println("获取网页授权凭证失败 "+ errorCode+errorMsg);
//			}
//
//		}
//		return wat;
//	}
}

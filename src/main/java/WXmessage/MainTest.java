package WXmessage;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	public static void main(String[] args) {
		Template tem=new Template();  
		//模板ID
		tem.setTemplateId("ynsHeAUTLFZtDucEBZxvrgYndJ87xaTNhUXI2hIF2GQ");  
		tem.setTopColor("#00DD00");  
		//用户openid
		tem.setToUser("ouzabtxFra6Q5u7-3O-o3X4kfv2U");  
		tem.setUrl("");  
		List<TemplateParam> paras=new ArrayList<TemplateParam>();  
		paras.add(new TemplateParam("first","2017-01-06测试发送模板消息","#FF3333"));  
		paras.add(new TemplateParam("patientName","测试001","#0044BB"));  
		paras.add(new TemplateParam("patientSex","火烧牛干巴","#0044BB"));  
		paras.add(new TemplateParam("hospitalName","火烧牛干巴","#0044BB")); 
		paras.add(new TemplateParam("department","火烧牛干巴","#0044BB")); 
		paras.add(new TemplateParam("doctor","火烧牛干巴","#0044BB")); 
		paras.add(new TemplateParam("seq","20170106001","#0044BB")); 
		paras.add(new TemplateParam("remark","感谢你对我们的支持!!!!","#AAAAAA"));  
		tem.setTemplateParamList(paras);  
		String token=SendWX.getAccessToken();
		boolean result=false;
		if(token!=null){
			result=SendWX.sendTemplateMsg(token,tem);  
		}
		System.out.println(result);
	}
}

package controller;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.Record;

import common.sms.RestTest;
import common.util.Util;

public class MessageController extends Controller{

	public void sendSms(String mobile) throws JSONException{

		String accountSid="1848ff0c5b1dbff41f2574e62211f7de";
		String token="2c54394e4b61d482b0a9c4a22a925e2d";
		String appId="70300fdb820949c4916859d6cc18081f";
		String templateId="7689";
		//String to=getPara("mobile");
		String to=mobile;
		
		String smsCode = Util.getRandomNum(100000, 999999) + "";
			
			
		
		Record toSix = new Record().set("yzCode", smsCode).set("mobile", to);

		String para=smsCode + ",3";
		boolean json = true;
		
		String result = RestTest.testTemplateSMS(json, accountSid,token,appId, templateId,to,para);
		System.out.println(result);
		renderJson(new Record().set("info", "success"));
		/*getSession().setAttribute("smsCode", toSix);
		
		String para=smsCode + ",3";
		boolean json = true;
		String result = RestTest.testTemplateSMS(json, accountSid,token,appId, templateId,to,para);
		JSONObject json2= new JSONObject(result);  
		JSONObject jsonArray=json2.getJSONObject("resp");
		   String respCode=(String) jsonArray.get("respCode");
		   if(respCode.equals("000000")){
			renderJson(new Record().set("info", "success"));
		}else{
			renderJson(new Record().set("info", "error"));
		}*/
	}
	
	public static void main(String[] args) throws Exception {
		
		MessageController messageController = new MessageController();
		messageController.sendSms("18352838151");
	}
	
}

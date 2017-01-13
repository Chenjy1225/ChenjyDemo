package controller;

import java.util.List;

import org.json.JSONException;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import common.sms.RestTest;
import common.util.Util;
import service.HelloService;
import util.Page;

import com.jfinal.core.Controller;

public class HelloController extends Controller {

	public void addUser() {

		String userName = getPara("userName");
		String password = getPara("password");
		Record user = new Record().set("name", userName).set("password", password);

		HelloService.addUser(user);
		renderText("��ӳɹ�");
	}

	public void deleteUser() {

		String userName = getPara("userName");
		String password = getPara("password");

		HelloService.deleteUser(userName, password);
		renderText("ɾ���ɹ�");
	}
	
	public void findUser() {

		String userName = getPara("userName");

		Record record = HelloService.findUser(userName);
		renderText(""+record.get("password")+"");
	}
	
	public void updateUser() {

		String userName = getPara("userName");
		String password = getPara("password");

		HelloService.updateUser(userName, password);
		renderText("�޸ĳɹ�");
	}
	
	public void IofoList(){

		// ��ȡҳ�����  < pageIndex >ҳ������ֵ
		int pageIndex = getParaToInt("pageIndex");

		// ��ȡ������
		int count = HelloService.getCount();

		Page page = new Page(Page.pageSize, pageIndex, count);
		
		List<Record> list = HelloService.get(page);
		Record pageInfo = new Record();

		pageInfo.set("count", page.getCount()).set("pageIndex", page.getPageIndex()).set("pageCount", page.getPageCount());

		// ���� JSON  <list>��ǰҳ���list;
		// <pageInfo>ҳ����Ϣ: <count>list����,<pageCount>ҳ������,<pageIndex>��ǰҳ������ 
		renderJson(new Record().set("list", list).set("pageInfo", pageInfo));

		}
	public void sendSms() throws JSONException{

		String accountSid="1848ff0c5b1dbff41f2574e62211f7de";
		String token="2c54394e4b61d482b0a9c4a22a925e2d";
		String appId="70300fdb820949c4916859d6cc18081f";
		String templateId="7689";
		String to=getPara("mobile");
		
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
}
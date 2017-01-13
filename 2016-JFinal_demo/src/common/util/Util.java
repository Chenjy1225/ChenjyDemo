package common.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.Record;
import com.npt.util.common.FileUtil;

import common.sms.RestTest;

public class Util {

	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	 }
	
	/**
	 * 生成随机数
	 * */
	public static int getRandomNum (int smallest, int largest){
		int random = 0;
		double f = Math.random()*(largest-smallest+1)+smallest;
		random = (int)Math.floor(f);
		return random;
	}
	
	/**
	 * @param email
	 * @return 是否是邮箱
	 */
	public static boolean isEmail(String email){
		Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		if(email == null || email.equals("")){
			return false;
		}
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * @param tableName
	 * @param columnName
	 * @return 获取字典表中的值
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject getTableColumnDict(String tableName, String columnName){
		List<Record> list = (List<Record>)JFinal.me().getServletContext().getAttribute("tableDict");
		String jsonString = null;
		for(Record dict : list){
			String tableName2 = dict.getStr("tableName");
			String columnName2 = dict.getStr("columnName");
			if(tableName2.equals(tableName) && columnName2.equals(columnName)){
				jsonString = dict.getStr("meaning");
				break;
			}
		}
		if(jsonString == null){
			return null;
		}else{
			return JSON.parseObject(jsonString);
		}
	}
	
	/**
	 * @param templateId
	 * @param to
	 * @param para
	 * @return 发送短信
	 */
	public static String sensSms(String templateId, String to, String para){
		Map<String, String> smsMap = FileUtil.readConfig("sms");
		String accountSid=smsMap.get("accountSid");
		String token=smsMap.get("token");
		String appId=smsMap.get("appId");
		String result = RestTest.testTemplateSMS(true, accountSid,token,appId, templateId,to,para);
		return result;
	}
	
	/**
	 * @param request
	 * @return 获取公网IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	 }
	
	public static void main(String[] args) {
		System.out.println(getRandomNum(100000, 999999));
		JSONObject o = JSON.parseObject("{-1:'不限',1:'3万以下',2:'5-8万',3:'8-15万',4:'15-30万',5:'30万以上'}");
		System.out.println(o.get("1"));
	}
}

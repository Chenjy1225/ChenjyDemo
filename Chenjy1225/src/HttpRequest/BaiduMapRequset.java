package HttpRequest;

import com.alibaba.fastjson.JSONObject;
import HttpRequest.HttpRequest;

public class BaiduMapRequset {

	public static void main(String[] args) {

		String responseData = HttpRequest.sendGet("http://api.map.baidu.com/geocoder/v2/",
				"callback=renderReverse&location=39.915,116.404&output=json&pois=1&ak=你的ak密钥");
		System.out.println(responseData);

		int start = responseData.indexOf("(") + 1;
		responseData = responseData.substring(start, responseData.lastIndexOf(")"));
		JSONObject jsonobject = new JSONObject();  
		System.out.println(jsonobject.parseObject(responseData).getJSONObject("result").getJSONObject("addressComponent").get("city"));
		

	}

}

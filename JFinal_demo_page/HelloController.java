package controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import service.HelloService;
import util.Page;

import com.jfinal.core.Controller;

public class HelloController extends Controller {

	public void addUser() {

		String userName = getPara("userName");
		String password = getPara("password");
		Record user = new Record().set("name", userName).set("password", password);

		HelloService.addUser(user);
		renderText("添加成功");
	}

	public void deleteUser() {

		String userName = getPara("userName");
		String password = getPara("password");

		HelloService.deleteUser(userName, password);
		renderText("删除成功");
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
		renderText("修改成功");
	}
	
	public void IofoList(){

		// 获取页面参数  < pageIndex >页面索引值
		int pageIndex = getParaToInt("pageIndex");

		// 获取总数量
		int count = HelloService.getCount();

		Page page = new Page(Page.pageSize, pageIndex, count);
		
		List<Record> list = HelloService.get(page);
		Record pageInfo = new Record();

		pageInfo.set("count", page.getCount()).set("pageIndex", page.getPageIndex()).set("pageCount", page.getPageCount());

		// 返回 JSON  <list>当前页面的list;
		// <pageInfo>页面信息: <count>list总数,<pageCount>页面总数,<pageIndex>当前页面索引 
		renderJson(new Record().set("list", list).set("pageInfo", pageInfo));

		}
	
}
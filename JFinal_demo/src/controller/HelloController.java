package controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import service.HelloService;

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
	
}
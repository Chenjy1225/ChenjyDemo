package service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class HelloService {

	
	public static void addUser(Record record){	
		Db.save("zt_user", record);
		
	}
	
	public static void deleteUser(String userName,String password){
		Db.update("delete from zt_user where name = "+userName+" and password = "+password+" ");
		
	}
	public static Record findUser(String userName){
		
		return Db.findFirst("select password from zt_user where name = "+userName+" ");
	}
	
	public static void updateUser(String userName,String password){
		Db.update("update zt_user set password = "+password+" where name = "+userName+" ");
		
	}
	
	
}

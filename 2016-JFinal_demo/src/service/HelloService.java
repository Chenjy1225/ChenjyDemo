package service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import util.Page;

public class HelloService {
	
     static int count = 0;
    
     /// <summary>
	 ///  获取总数量
	 /// </summary>

	public static int getCount(){
		
		String sql="select count(*) as count from zt_case_img";
		
	    count = Integer.parseInt(Db.findFirst(sql).getLong("count").toString());
	    
	    return count;
	}
	
    /// <summary>
	///  获取当前页面列表
	/// </summary>	  
	public static List<Record> get(Page page) {

	       List<Record> list = null;
	       
	       int start = (page.getPageIndex() - 1) * page.getPageSize();
	       
	       int pageSize = page.getPageSize();

	       String sql = "select * from zt_case_img limit "+start+","+pageSize+" ";
	       
	       list = Db.find(sql);
	       return list;
	    }
	
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

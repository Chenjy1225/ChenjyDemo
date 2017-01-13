package service;

import com.jfinal.plugin.activerecord.Db;

public class InportExcelService {

	public static void delAll(){
		
		Db.update("delete from zt_case_img");
	}
	
	public static void addSql(String sqlValues){
		
		String sql = "insert into zt_case_img"
				  + " (caseId,imgId)"
				  + " values " + sqlValues;
		Db.update(sql);
	}
}

package service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ExportExcelService {

	public List<Record> getList(){
		
		String sql = "select * from zt_case_img";
		
		return Db.find(sql);
	}
	
}

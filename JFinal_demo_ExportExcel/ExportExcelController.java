package controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import common.util.XLSFileKit;
import service.ExportExcelService;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;


public class ExportExcelController extends Controller{

	ExportExcelService exportExcelService = new ExportExcelService();
	/**
	 * 导出表格
	 */
	public void exportOutExcel(){
		String sheetName = getPara("sheetName");		
		
		String fileName = new Date().getTime() + "_" + UUID.randomUUID().toString() + ".xls";
		String filePath = getRequest().getRealPath("/") + "/file/export/";
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		String relativePath = "/file/export/" + fileName;
		filePath += fileName;
		XLSFileKit xlsFileKit = new XLSFileKit(filePath);
		List<List<Object>> content = new ArrayList<List<Object>>();
		List<String> title = new ArrayList<String>();
		
		List<Record> datas = exportExcelService.getList();
		title.add("序号");
		title.add("id");
		title.add("caseId");
		title.add("imgId");
		int i = 0;
		OK:
		while(true){
			if(datas.size() < (i + 1)){
				break OK;
			}
			int index = i + 1;
			List<Object> row = new ArrayList<Object>(); 
			row.add(index + "");
			row.add(null==datas.get(i).get("id")?"":datas.get(i).get("id"));
			row.add(null==datas.get(i).get("caseId")?"":datas.get(i).get("caseId"));
			row.add(null==datas.get(i).get("imgId")?"":datas.get(i).get("imgId"));
			content.add(row);
			i ++;
		}
		xlsFileKit.addSheet(content, sheetName, title);
		xlsFileKit.save();
		renderJson(new Record().set("relativePath", relativePath));
	}
	
}

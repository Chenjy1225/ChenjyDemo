package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

import service.InportExcelService;
import util.ReadExcel;


public class InportExcelController  extends Controller{

	/**
	 * #文件上传大小限制 10 * 1024 * 1024 = 10M
	 */
	public static final String config_maxPostSize = "10485760";
	/**
	 * 文件上传根路径
	 */
	public static final String config_fileUploadRoot = "/file/import/";
	
	InportExcelService inportExcelService = new InportExcelService();
	
	@Before(Tx.class)
	public void exportInExcel() throws FileNotFoundException, InvalidFormatException, IOException{
		String choose = getPara("flag");//是否清空全部插入 1：是，0 追加
		StringBuilder savePathStr = new StringBuilder(PathKit.getWebRootPath()
				+config_fileUploadRoot);
		File savePath = new File(savePathStr.toString());
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String flag ="1";
		try{
			// 保存文件
			List<UploadFile> files = getFiles(savePath.getPath(),
					Integer.parseInt(config_maxPostSize),
					"UTF-8");
			String fileRoot = files.get(0).getFileName();
			
			// 读取Excel
			ReadExcel readExcel = new ReadExcel();
			Map<String,Object> obj = readExcel.read(savePath.getPath()+"/"+fileRoot);
			for(String key:obj.keySet()){
				if("error".equals(key)){
					
				}else{
					List<List<Object>> list = readExcel.readExcel((Workbook)obj.get(key));
					String sql = dataListToSql(list,choose);
					if(sql.startsWith("表格")){
						flag=sql;
					}else{
						if("1".equals(choose)){
							inportExcelService.delAll();
							inportExcelService.addSql(sql);
						}else{
							inportExcelService.addSql(sql);
						}
						
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			flag="出现错误";
		}
		
		setAttr("flag", flag);
		renderJson();
	}
	
	private String dataListToSql(List<List<Object>> dataLst,String choose){
		String sql ="";
		//判断表格列是否匹配
		List<Object> head= dataLst.get(0);
		if(head.size()==4){
			if("序号".equals(head.get(0)) 
				&&"id".equals(head.get(1))
				&&"caseId".equals(head.get(2))
				&&"imgId".equals(head.get(3))){
			}else{
				return "表格列显示有误！";
			}
		}else{
			return "表格模板不正确！";
		}
		//组装sql
		for(int i=1;i<dataLst.size();i++){
			sql += "(";
			List<Object> list = dataLst.get(i);
			for(int j = 2;j < 4;j++){
				if(j == 2 || j == 3){
					try{
						sql+=Integer.parseInt(list.get(j).toString().split("\\.")[0])+",";
					}catch(Exception e){
						e.printStackTrace();
						return "列数输入内容应为数字！";
					}
				}else{
					sql+="'"+list.get(j)+"',";
				}
				
			}
			sql = sql.substring(0,sql.length()-1)+"),";
		}
		if(!"".equals(sql)){
			sql=sql.substring(0,sql.length()-1);
		}
		return sql;
	}
	
}

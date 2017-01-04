package common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSFileKit {

	private HSSFWorkbook workBook;
	private String filePath;
	public XLSFileKit(String filePath){
		this.filePath=filePath;
		this.workBook=new HSSFWorkbook();
	}
	
	/**
	 * 添加sheet
	 * @param content 数据
	 * @param sheetName sheet名称
	 * @param title 标题
	 */
	public <T> void addSheet(List<List<T>> content,String sheetName,List<String> title){
		HSSFSheet sheet=this.workBook.createSheet(sheetName);
    	HSSFRow row=null;
    	HSSFCell cell=null;
    	int i=0,j=0;
    	row=sheet.createRow(0);
    	for(;j<title.size();j++){//添加标题
    		cell=row.createCell(j);
			cell.setCellValue(title.get(j));
    	}
    	i=1;
    	for(List<T> rowContent:content){
    		row=sheet.createRow(i);
    		j=0;
    		for(Object cellContent:rowContent){
    			cell=row.createCell(j);
    			cell.setCellValue(cellContent.toString());
    			j++;
    		}
    		i++;
    	}
	}
	
	/**
	 * 保存
	 * @return
	 */
	public boolean save(){
		try {
			FileOutputStream fos=new FileOutputStream(this.filePath);
			this.workBook.write(fos);
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
	}
}

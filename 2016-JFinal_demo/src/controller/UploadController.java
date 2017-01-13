package controller;

import java.io.File;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class UploadController extends Controller {

	  /**
	   * #文件上传大小限制 10 * 1024 * 1024 = 10M
	   */
	  public static final String config_maxPostSize = "10485760";
	  /**
	   * 文件上传根路径 
	   */
	 public static final String config_fileUploadRoot = "/upload/";

public void upload() {	
		
		  /**
		   * 文件上传根路径  :我这里的PathKit.getWebRootPath()：G:\eclipse-WorkSpace\JFinal_demo\WebRoot
		   */
		StringBuilder savePathStr = new StringBuilder(PathKit.getWebRootPath()+config_fileUploadRoot);
		File savePath = new File(savePathStr.toString());
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String fileRoot="";
		try{
			// 保存文件
			List<UploadFile> files = getFiles(savePath.getPath(),Integer.parseInt(config_maxPostSize),"UTF-8");
			
			fileRoot = config_fileUploadRoot+files.get(0).getFileName();
		}catch(Exception e){
			e.printStackTrace();
		}
		setAttr("fileRoot", fileRoot);
		renderJson();

	}

}

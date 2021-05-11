package com.nanny.biz.impl.admin;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.admin.getFileListBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

/**
 *  批量上传图片（5000张）
 *  逻辑层
 * @author lpc
 *
 */
@Service
@Transactional
public class getFileListBizImpl implements getFileListBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
	
	private HttpServletRequest request;
	


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public JSONObject listPath(File path) throws IOException {

		System.out.println("upload starting.....\n");
		
		  File files[] = path.listFiles(); //获得目录下所有第一层文件
		 
		  for (int i = 0; i < files.length; i++)
		  {
          /*  String  firstID= files[i].getName();	 //遍历第一层
*/		       if (files[i].isDirectory()){  //用递归列出第二层子目录		      
		           File filessec[] = files[i].listFiles(); //获得目录下所有第二层文件
		           for(int i1=0;i1<filessec.length;i1++){		        	
                       String  secondID=filessec[i1].getName();//遍历第二层
                       if (filessec[i1].isDirectory()){  //用递归列出第三层子目录	
                    	   File filesthi[] = filessec[i1].listFiles(); //获得目录下所有第二层文件     
                    	   for(int i2=0;i2< filesthi.length;i2++){
	                    		 String   name =filesthi[i2].getName();
	                    		 int dot = name.lastIndexOf('.');  
	                    		String proCode= UUID.randomUUID().toString();
	                    	
	                    		String createTimeName=TimeUtil.getNowDate("yyyyMMddHHmmssSSS");
	                    		
	                    		System.out.println("now createfile "+createTimeName+".jpg ....\n");
	                    		
	                    		//延迟防止文件名重复
	                    		int delay=0;
	                    		while(delay<10000)
	                    			delay++;
	                    		
	                    		String createTime=TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss");
	                    		String cover ="/nanny/upload/img/"+createTimeName+".jpg";
	                    		sSavePath(createTimeName,filesthi[i2]);
                    		    String sql ="INSERT INTO shop_product" +
                    		    "(name,proCode,price,disPrice,costPrice,inventory,cover,buyCount,viewCount,typeID,brandID,shopID,isUse,isCommission,createTime)" +
                    		    " values(?,?,1,0,1,100000,?,0,0,?,0,0,0,1,?)";
                    		    Object[] param ={name.substring(0, dot),proCode.replace("-","" ),cover,secondID,createTime};
                    		    sshUtilDao.updateObjectBySQL(sql,  param);
                    	   }
                       }
				  }
       	      }
          }  
		  System.out.println("done!!!\n");
		  return null;
	  }

	private void sSavePath(String createTimeName,File file) throws IOException {
		//1.获得项目真实路径
		 String sRootPath=request.getSession().getServletContext().getRealPath("");
		 String sBasePath=sRootPath+"\\upload\\img\\";//获得存储根路径
		/* String sBasePath="D:\\MyEclipse\\Workspaces\\MyEclipse 10\\nanny\\WebRoot\\upload\\img\\";//获得存储根路径
*/		 //2.设置存储地址
		 String sContentType ="jpg";
		 String sSavePath=sBasePath+createTimeName+"."+sContentType;//保存物理路径
		 //3.输出文件
         FileInputStream input=new FileInputStream(file);
        
		 commonFileOutPut(input, sSavePath);			
		 input.close();
	}
	private void commonFileOutPut(InputStream input,String outputPath){
		try{
			FileOutputStream fos=new FileOutputStream(new File(outputPath));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = input.read(buffer)) > 0)
				fos.write(buffer, 0, len);
			fos.flush();
	    	fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

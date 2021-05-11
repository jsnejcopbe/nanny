package com.nanny.controller.admin;

import java.io.File;
import java.io.IOException;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.admin.getFileListBiz;
/**
 *  批量上传图片（5000张）
 *  控制层
 * @author lpc
 *
 */

@Controller
public class GetFileListController {
		
   @Resource
     private getFileListBiz getfilelistBiz;
	
    @RequestMapping("5a.html")
	public ModelAndView getlist(HttpServletRequest request,HttpServletResponse response)throws IOException{
        
      //得到本地图片的路径
     File path = new File("D:/b/");

     getfilelistBiz.setRequest(request);
     
     getfilelistBiz.listPath(path);
	return null;


     
    }
}

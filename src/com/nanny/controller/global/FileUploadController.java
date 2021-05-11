package com.nanny.controller.global;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.util.CreateQrCodeUtill;
import com.zhuoan.util.TimeUtil;

/**
 * 文件上传控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class FileUploadController {
	
	/**
	 * @param root 要存放的第二根路径
	 * @param file 
	 * @param request
	 * @param type 是否再以xxx分类
	 * @return
	 */
	@RequestMapping("/upload/{root}.html")
	@ResponseBody
	public String file_upload(@PathVariable() String root,@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request){
		if(root == null && file == null)return "";
		String type = request.getParameter("type");
			   type = type == null ? "" : type;
		File filesave = BasisUtil.filesave(request,root, new String[]{type,file.getOriginalFilename()},0);
		try {
			file.transferTo(filesave);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		sb.append(basePath+"upload");
		sb.append("/");
		sb.append(root);
		if(!"".equals(type)){
			sb.append("/");
			sb.append(type);
		}
		sb.append("/");
		sb.append(filesave.getName());
		Map<String, String> map = new HashMap<String, String>();
		map.put("file_name", filesave.getName());
		map.put("file_url", sb.toString());
		return JsonUtil.getJson(map);
	}
	
	@SuppressWarnings("null")
	@RequestMapping("/image/TwoCode")
	public void two_dimension_code(String data,HttpServletRequest request,HttpServletResponse response){
		if(data == null && data.trim().isEmpty())new IllegalAccessError("url不能为空");
		try {
			String path = request.getContextPath();
			if(data.indexOf("http")==-1){
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				path = basePath + data;
			}else
				path=data;
			
			CreateQrCodeUtill.createQRCode(path, 200, 200, response.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片上传
	 * @return
	 */
	@RequestMapping("upload/images")
	public void imgUpload(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		JSONObject oRtnMsg=new JSONObject();
		try{
			//0.輸出圖片
			JSONObject sNetPath=inputImg(request, response, file);
	    	//2.返回消息
	    	oRtnMsg.element("path", "/nanny/upload/img/"+sNetPath.getString("url"));
	    	oRtnMsg.element("msg", "上传成功");
		}catch(Exception e){
			e.printStackTrace();
			oRtnMsg.element("msg", "上传失败");
		}
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			out =response.getWriter();
			out.write(oRtnMsg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{out.close();}
	}
	
	/**
	 * 文本編輯器上傳圖片
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping("upload/um_images")
	public void baiduUpload(@RequestParam(value = "upfile", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response)
	{
		try{
			JSONObject result=inputImg(request, response, file);
			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/plain");
			response.getWriter().println(result.toString());
		}catch(Exception e){e.printStackTrace();}		
	}
	
	private JSONObject inputImg(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws IOException{
		//0.获得项目真实路径
		String sRootPath=request.getSession().getServletContext().getRealPath("");
		String sBasePath=sRootPath+"/upload/img/";//获得存储根路径
//		String sBasePath="/diskplugin/wwwroot/nanny/upload/img/";//获得存储根路径
		//1.获得当前月份，创建文件夹
		String sNowMonth=TimeUtil.getNowDate("yyyyMM");
		File OutfileFolder=new File(sBasePath+"/"+sNowMonth);
		if (!OutfileFolder.exists() && !OutfileFolder.isDirectory())
			OutfileFolder.mkdir();
		//2.设置存储地址
		String sContentType=(file.getOriginalFilename()).split("\\.")[1];
		String sNowDate=TimeUtil.getNowDate("yyyyMMddHHmmSSss");
		String sSavePath=sBasePath+"/"+sNowMonth+"/"+sNowDate+"."+sContentType;//保存物理路径
//		String sNetPath="/upload/img/"+sNowMonth+"/"+sNowDate+"."+sContentType;//访问地址
		//3.输出文件
		String isCover=request.getParameter("isCover");
		InputStream input=file.getInputStream();
		if(isCover!=null)
			imgCut(input, sContentType, sSavePath);
		else
			commonFileOutPut(input, sContentType, sSavePath);			
    	input.close();
    	//4.組織json
    	JSONObject result=new JSONObject();
    	result.element("name", sNowDate+"."+sContentType);
    	result.element("originalName", file.getOriginalFilename());
    	result.element("size", "");
    	result.element("state", "SUCCESS");
    	result.element("type", "."+(file.getOriginalFilename()).split("\\.")[1]);
    	result.element("url", sNowMonth+"/"+sNowDate+"."+sContentType);
    	return result;
	}
	
	/**
	 * 输出截取图片
	 * @param input
	 * @param sContentType
	 * @param outputPath
	 */
	private void imgCut(InputStream input,String sContentType,String outputPath){
		try{
			// 0.取得图片读入器
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(sContentType);
			ImageReader reader = readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(input);
			reader.setInput(iis, true);
			// 1.计算截取长宽
			int width=reader.getWidth(0);
			int height=(width/21)*9;
			// 2.设定截取范围
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(0, 0, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			// 3.输出截取图片
			Image image=bi.getScaledInstance(400, 173, Image.SCALE_DEFAULT);
			BufferedImage show_img =new BufferedImage(400, 173, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = show_img.createGraphics();
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);//抗锯齿
	        g.drawImage(image, 0, 0, null); // 绘制图像
	        g.dispose();
	        FileOutputStream out = new FileOutputStream(outputPath);
	        ImageIO.write(show_img, sContentType, out);
	        out.flush();
	        out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 通常文件输出
	 * @param input
	 * @param sContentType
	 * @param outputPath
	 */
	private void commonFileOutPut(InputStream input,String sContentType,String outputPath){
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

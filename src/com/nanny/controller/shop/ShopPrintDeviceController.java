package com.nanny.controller.shop;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.shop.ShopPrintDeviceBiz;
import com.nanny.dto.Dto;
import com.nanny.model.ShopPrintDevice;
import com.nanny.model.SysBanks;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.util.DateUtils;

@Controller
public class ShopPrintDeviceController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource(name="shopPrintDeviceBiz")
	private ShopPrintDeviceBiz spz;
	
	/**
	 * 跳转页
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("shop/shopPrint")
	public ModelAndView jumpPrintDevice (HttpServletRequest request,HttpServletResponse response)throws IOException{
		HttpSession session=request.getSession();
		JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		Long shopID =object.getLong("shopID");
		
		JSONArray print = spz.getPrintMsg(shopID);
		ModelAndView mav =new ModelAndView(Dto.getPagePath(type)+"/shopprinter");
		mav.addObject("print", print);
		return mav;
	} 
	
	/**
	 * 修改打印机信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/updatePrint")
	public void updatePrintDevice(HttpServletRequest request,HttpServletResponse response)throws IOException{
		// 获取信息
		HttpSession session=request.getSession();
		JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		Long shopID =object.getLong("shopID");
		
		String deType = request.getParameter("detype");
		String deNo = request.getParameter("deno");
		String deKey = request.getParameter("dekey");
		String masg;
		
		// 保存信息
		ShopPrintDevice spd = new ShopPrintDevice();
		if ("0".equals(deType)) {
			spd.setDeNo(deNo);
			spd.setDeKey(deKey);
			spd.setShopId(shopID);
			spd.setCreateTime(DateUtils.getTimestamp());
			masg = spz.updatePrintMsg(Integer.valueOf(deType),shopID,spd);
		}else {
			masg = spz.updateShopPrint(Integer.valueOf(deType), shopID);
		}
		
		
		JSONObject msg=new JSONObject();
		msg.element("msg", masg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	
}

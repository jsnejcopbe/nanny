package com.nanny.controller.shop;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nanny.biz.shop.MerchantsFans;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;

/**
 * @author syl
 * 商户粉丝
 */
@Controller
@RequestMapping("shop")
public class MerchantsFansController{
	@Resource
	private MerchantsFans mf;
	
	@RequestMapping("/fans/init")
	@ResponseBody
	public String init(HttpServletRequest request,String data) {
		Object s = request.getSession().getAttribute("temp_shopID");
		String shopID = s==null ? Dto.getShopID(request) : s.toString();
		Map<String, String> option = data == null ? null : JsonUtil.getMapStr(data); 
		return mf.fansinit(shopID,option);
	}
	
	@RequestMapping("/fans/getArea")
	@ResponseBody
	public String getArea(String type){
		return mf.getArea(type==null?"":type);
	}
}

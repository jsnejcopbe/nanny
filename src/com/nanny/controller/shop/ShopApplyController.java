package com.nanny.controller.shop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.shop.ShopBasis;
import com.nanny.biz.shop.ShopDispatchingBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysGlobalArea;
import com.nanny.model.SysShopApply;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.util.weixin.WeixinUserMsgPush;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**  商户
 * @author syl
 * @modify wph
 *
 */
@Controller
@RequestMapping("shop")
public class ShopApplyController {
	@Resource
	private ShopBasis shop;
	@Resource(name="shopDispatchingBiz")
	private ShopDispatchingBiz g_dispatchBiz;
	
	@ResponseBody
	@RequestMapping("/add")
	public String add(String data,HttpServletRequest request){
		String shopID=request.getParameter("shopID");
		
		if(data == null || data.trim().isEmpty())return Dto.NULL_ERROR;
		SysShopApply bean = (SysShopApply) JsonUtil.getBean(data, SysShopApply.class);
		HttpSession session = request.getSession();
		String guid = session.getAttribute(Dto.SHOP_ADD_TYPE)==null?"0":session.getAttribute(Dto.SHOP_ADD_TYPE).toString();
		if(!"0".equals(guid))
			bean.setMemo(guid);
		else if(shopID!=null)
			bean.setMemo("shop_"+shopID);
		return shop.apply(bean,session);
	}
	
	
	@RequestMapping("/baseinfo/init")
	@ResponseBody
	public String info_init(HttpServletRequest request) throws NumberFormatException, UnsupportedEncodingException{
		JSONObject login = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String id = login.getString("userID");
		String data=shop.baseinfo_init(id);
		
		//当店铺没有二维码时，尝试生成二维码
		JSONArray array=JSONArray.fromObject(data);
		JSONObject shopMsg=array.getJSONObject(0);
		if(!(shopMsg.containsKey("shop_des"))){
			String url=getQrcodeUrl(Long.valueOf(id));
			shopMsg.element("shop_des", url);
			shop.updateShopUrl(shopMsg.getLong("id"), url);
		}
		return array.toString();
	}
	
	//头像
	@RequestMapping("headimg")
	@ResponseBody
	public String change_headimg(String url,HttpServletRequest request){
		return shop.change_headimg(url,Dto.getShopID(request));
	}
	
	@RequestMapping("/notice/init")
	@ResponseBody
	public String notice_init(String id){
		return shop.notice_init(id);
	}
	
	@RequestMapping("/notice/edit")
	@ResponseBody
	public String notice_edit(String id,String data){
		return shop.notice_edit(id,data);
	}
	
	@RequestMapping("/runtime/init")
	@ResponseBody
	public String runtime_init(String id){
		return shop.runtime_init(id);
	}
	
	@RequestMapping("/runtime/edit")
	@ResponseBody
	public String runtime_edit(String id,String time1,String time2,String time_id){
		return shop.runtime_edit(id,time1,time2,time_id);
	}
	
	@RequestMapping("/runtime/remove")
	@ResponseBody
	public String runtime_remove(String time_id,HttpServletRequest request){
		return shop.runtime_remove(time_id,Dto.getShopID(request));
	}
	
	@RequestMapping("/delivery/add")
	@ResponseBody
	public String scopeAdd(String shopID,String ids){
		return shop.scopeAdd(shopID,JsonUtil.getList(ids));
	}
	
	@RequestMapping("/adress/add")
	@ResponseBody
	public String adressAdd(String data,String shop_id,HttpServletRequest request){
		/*JSONObject login = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String shop_id = login.getString("shopID");*/
		return shop.adressAdd((SysGlobalArea) JsonUtil.getBean(data, SysGlobalArea.class),shop_id);
	}
	
	@RequestMapping("/adress/edit")
	@ResponseBody
	public String adressEdit(String adressID,String shopID){
		return shop.adressEdit(adressID,shopID);
	}
	
	@RequestMapping("/adress/getAdress")
	@ResponseBody
	public String getAdress(){
		return shop.getAdress();
	}
	
	@RequestMapping("/adress/getProvince")
	@ResponseBody
	public String getProvince(){
		return shop.getProvince();
	}

	@RequestMapping("/adress/getCity")
	@ResponseBody
	public String getCity(String type){
		return shop.getCity(type);
	}
	
	@RequestMapping("/adress/getAdresslist")
	@ResponseBody
	public String getAdresslist(HttpServletRequest request,String data){
		Map<String, String> map = data == null ? null : JsonUtil.getMapStr(data.replace(" ", ""));
		JSONObject login = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String id = login.getString("shopID");
		return shop.getAdresslist(id,map);
	}

	@RequestMapping("/delivery/scopelist")
	@ResponseBody
	public String getScopelist(HttpServletRequest request,String data){
		Map<String, String> map = data == null ? null : JsonUtil.getMapStr(data);
		JSONObject login = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String id = login.getString("shopID");
		return shop.getScopelist(id,map);
	}
	
	@RequestMapping("/delivery/remove")
	@ResponseBody
	public String delRemove(String id,String adressID){
		return shop.delRemove(id,adressID);
	}
	
	@RequestMapping("/getAdresslist")
	@ResponseBody
	public String getShopAdress(String data){
		return shop.getShopAdress(data);
	}
	

	@RequestMapping("/adress/allAdresslist")
	@ResponseBody
	public String getAllAdresslist(){
		return shop.getAdresslist(null,null);
	}
	
	@RequestMapping("/price/change")
	@ResponseBody
	public String change_price(String type,String value,HttpServletRequest request){
		value = value == null?"0":value;
		if(!BasisUtil.isDouble(value))return "请输入正确的数";
		return shop.change_price(type,value,Dto.getShopID(request));
	}
	
/**********************************************************************新增*********************************************************/
	/**
	 * 前往起送价编辑页面
	 */
	@RequestMapping("/dispatchedit")
	public ModelAndView toDispacthingEdit(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得session中的用户信息
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		//1.获得商户地址配送信息
		JSONArray dispacthList=g_dispatchBiz.getShopDispatchByShopID(userMsg.getLong("shopID"));
		
		//2.返回数据
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/dispatchEdit");
		mav.addObject("dispacthList", dispacthList);
		return mav;
	}
	
	/**
	 * 更新起送价
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/updatedispatch")
	public void updateDispacthing(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONArray jsonData=JSONArray.fromObject(request.getParameter("jsonData"));
		//0.遍历添加数据
		for(int i=0;i<jsonData.size();i++)
		{
			JSONObject obj=jsonData.getJSONObject(i);
			g_dispatchBiz.updateShopDispatchByID(obj.getLong("dptID"), obj.getDouble("dptPrice"));
		}
		//1.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
	
	
	
	/**
	 * 店铺电话修改
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updshoptel")
	public void updshoptel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		//0.获得session中的用户信息
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String tel=request.getParameter("memo");
		
		String msgg=shop.updateshoptel(tel, userMsg.getInt("shopID"));
		
		JSONObject msg=new JSONObject();
		msg.element("msg", msgg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 创建商户专用推广二维码链接
	 * @param userID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getQrcodeUrl(Long userID) throws UnsupportedEncodingException{
		//0.拼接数据
		JSONObject data=new JSONObject();
		data.element("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject passParam=new JSONObject();
		passParam.element("scene", (new JSONObject()).element("scene_str", userID.toString()));
		data.element("action_info", passParam);
		
		//1.获得ticket
		JSONObject ticket=WeixinUserMsgPush.getTicket(data);
		if(ticket.containsKey("errcode"))
		{
			System.out.println("create qrcode failed,error msg is"+ticket.getString("errmsg"));
			return null;
		}else
			return ticket.getString("url");
	}
}

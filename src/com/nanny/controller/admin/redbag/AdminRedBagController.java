package com.nanny.controller.admin.redbag;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.admin.AdminRedBagRecBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysRedbagRec;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.WeiXinRedBagUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 系统红包管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class AdminRedBagController {

	//载入资源
	@Resource(name="adminRedBagRecBiz")
	private AdminRedBagRecBiz g_RedBagBiz;
	
	/**
	 * 管理员派发红包
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("admin/sendnormalredbag")
	public void adminSendRedBag(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		System.out.println("============ into redbag send method ===============\n");
		//0.获得传入参数
		String money=request.getParameter("totalMoney");
		String wishing=request.getParameter("wishing");
		String actName=request.getParameter("actName");
		String remark=request.getParameter("remark");
		int sendNum=Integer.valueOf(request.getParameter("num"));
		int redBagType=Integer.valueOf(request.getParameter("type"));
		JSONArray openIdList=JSONArray.fromObject(request.getParameter("openIdList"));
		
		//1.遍历红包发放请求
		for(int i=0;i<openIdList.size();i++)
		{
			JSONObject tmpObj=openIdList.getJSONObject(i);
			
			//2.组织接口请求数据
			JSONObject sendParam=createBaseData(tmpObj.getString("openID"), money, String.valueOf(sendNum), wishing, actName, remark);
			if(redBagType==0)
				sendParam.element("client_ip", Dto.getLocalIp(request));
			else
				sendParam.element("amt_type", "ALL_RAND");
			String data=WeiXinRedBagUtil.createXmlData(sendParam);
			System.out.println("============ created send param is:"+data+" ===============\n");
			
			//3.调用接口发送红包
			Map<String,Object> result=WeiXinRedBagUtil.postDataToInterface(data, redBagType,request);
			JSONObject backMsg=JSONObject.fromObject(result);
			System.out.println("============ the result is:"+backMsg.toString()+" ===============\n");
		
			//4.保存红包发放记录
			addRecToTable(backMsg, actName, money, redBagType, tmpObj.getLong("userID"),sendNum);
		}
		
		//5.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "处理完成");
		Dto.printMsg(response, msg.toString());
	}

/********************************************************************************************************************************************/	
	
	/**
	 * 添加红包发放记录
	 * @param backMsg
	 * @param actName
	 * @param money
	 * @param type
	 * @param userID
	 */
	private void addRecToTable(JSONObject backMsg,String actName,String money,int type,Long userID,int sendNum){
		SysRedbagRec bean=new SysRedbagRec();
		bean.setActName(actName);
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss")));
		if(backMsg.containsKey("err_code_des"))
			bean.setMemo(backMsg.getString("err_code_des"));
		bean.setMoney(Double.valueOf(money));
		bean.setNum(sendNum);
		bean.setResult(backMsg.getString("return_msg"));
		bean.setType(type==0?"普通红包":"裂变红包");
		bean.setUserId(userID);
		
		g_RedBagBiz.addRec(bean);
	}
	
	/**
	 * 创建基础数据
	 * @param openID
	 * @param money
	 * @param num
	 * @param wishing
	 * @param actName
	 * @param remark
	 * @return
	 */
	private JSONObject createBaseData(String openID,String money,String num,
									  String wishing,String actName,String remark)
	{
		JSONObject sendParam=new JSONObject();
		sendParam.element("re_openid", openID);
		sendParam.element("total_amount", money);
		sendParam.element("total_num", num);
		sendParam.element("wishing", wishing);
		sendParam.element("act_name", actName);
		sendParam.element("remark", remark);
		return sendParam;
	}
}

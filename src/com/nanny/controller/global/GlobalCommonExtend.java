package com.nanny.controller.global;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.SysGlaAreaBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.weixin.TenXunMapUtil;

/**
 * global控制层通用父类
 * @author wph
 *
 */
@Controller
public class GlobalCommonExtend {
	
	@Resource(name="sysGlaAreaBiz")
	private SysGlaAreaBiz g_SysAreaBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	
	/**
	 * 辅助方法 根据wifi经纬度获得最近的地址ID
	 * @param lonStr
	 * @param city
	 * @return
	 */
	protected JSONObject helpGetPostion(String lonStr,String city){
		JSONObject msg=new JSONObject();
		//1.坐标转换
		String[] param={lonStr};
		JSONArray location=translate(param);
		if(location!=null)
			lonStr=location.getString(0);
		
		//2.获得当前位置附近的地区
		JSONObject posData=TenXunMapUtil.getMyPosByLon(lonStr);
		
		if(posData.getInt("status")!=0)
			msg.element("msg", "定位失败，请您手动输入地址");
		else{
			//3.查询符合结果的小区
			JSONArray poiList=posData.getJSONObject("result").getJSONArray("pois");
			JSONArray comuList=new JSONArray();
			if(poiList.size()>0)
				comuList=g_SysAreaBiz.getSysAreaByPoiData(poiList, city);
			if(comuList.size()==0)
				msg.element("msg", "没有找到适合地址，请您手动输入");
			else{
				JSONObject comuData=getMostRecentPos(comuList, lonStr);
				msg.element("addID", comuData.getLong("id"));
			}
		}
		return msg;
	}
	
	protected JSONObject helpGetPosAll(String lonStr,String city)
	{
		//1.坐标转换
		String[] param={lonStr};
		JSONArray location=translate(param);
		if(location!=null)
			lonStr=location.getString(0);
		
		//2.获得当前位置附近的地区
		JSONObject posData=TenXunMapUtil.getMyPosByLon(lonStr);
		
		if(posData.getInt("status")==0){
			//3.查询符合结果的小区
			JSONArray poiList=posData.getJSONObject("result").getJSONArray("pois");
			JSONArray comuList=new JSONArray();
			if(poiList.size()>0)
				comuList=g_SysAreaBiz.getSysAreaByPoiData(poiList, city);
			
			System.out.println("comuList:"+comuList.toString()+"\n");
			
			if(comuList.size()==0)
				return null;
			else{
				comuList=getRecentShopList(comuList, lonStr);
				JSONObject rtnObj=new JSONObject();
				rtnObj.element("listData", comuList);
				rtnObj.element("nowPos", poiList.getJSONObject(0).getString("address"));
				return rtnObj;
			}
		}else
			return null;
	}
	
	/**
	 * 坐标转换
	 * @param param
	 * @return
	 */
	private JSONArray translate(String[] param){
		JSONObject obj=TenXunMapUtil.transLan(param, 1);
		if(obj.getInt("status")!=0)
			return null;
		else{
			JSONArray rtnArray=new JSONArray();
			JSONArray result= obj.getJSONArray("locations");
			for(int i=0;i<result.size();i++)
			{
				JSONObject tmpObj=result.getJSONObject(i);
				rtnArray.add(tmpObj.getString("lat")+","+tmpObj.getString("lng"));
			}
			return rtnArray;
		}
	}
	
	/**
	 * 筛选出距离最近的小区
	 * @param posList
	 * @param lonStr
	 * @return
	 */
	private JSONObject getMostRecentPos(JSONArray posList,String lonStr)
	{
		JSONObject backPos=new JSONObject();//返回的地址
		Double checkDistance=null;//当前最近的距离
		
		//0.获得当前经纬度的数值
		Double latValue=Double.valueOf(lonStr.split(",")[0]);//纬度
		Double lonValue=Double.valueOf(lonStr.split(",")[1]);
		
		//1.计算地址合集中各点与当前位置的距离
		for(int i=0;i<posList.size();i++)
		{
			JSONObject tmpObj=posList.getJSONObject(i);
			Double latVal=tmpObj.getDouble("lat");
			Double lonVal=tmpObj.getDouble("lon");
			Double dsitance=MathDelUtil.getDistanceOfTwoPos(latValue, lonValue, latVal, lonVal);
			if(checkDistance==null)
				checkDistance=dsitance;
			
			if(dsitance<=checkDistance){
				backPos=tmpObj;
				checkDistance=dsitance;
			}
		}
		
		//2.返回结果
		return backPos;
	}
	
	/**
	 * 获得指定位置店铺列表
	 * @param posList
	 * @param lonStr
	 * @return
	 */
	private JSONArray getRecentShopList(JSONArray posList,String lonStr)
	{
		
		//0.获得当前经纬度的数值
		Double latValue=Double.valueOf(lonStr.split(",")[0]);//纬度
		Double lonValue=Double.valueOf(lonStr.split(",")[1]);
		
		//1.计算地址合集中各点与当前位置的距离
		for(int i=0;i<posList.size();i++)
		{
			JSONObject tmpObj=posList.getJSONObject(i);
			Double latVal=tmpObj.getDouble("lat");
			Double lonVal=tmpObj.getDouble("lon");
			Double dsitance=MathDelUtil.getDistanceOfTwoPos(latValue, lonValue, latVal, lonVal);
		
			JSONArray shopList=g_ShopMsgBiz.getShopIDListByUserAdd(tmpObj.getLong("id"));
			tmpObj.element("shopList", shopList);
			tmpObj.element("dsitance", MathDelUtil.halfUp(dsitance/1000));
		}
		
		//2.数据排序
		MathDelUtil.arraySort(posList, "dsitance");
		
		return posList;
	}
}

package com.nanny.controller.admin;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nanny.biz.admin.ExpandList;
import com.nanny.model.BaseSaleman;
import com.nanny.util.JsonUtil;

/**
 * @author syl
 * 平台推广
 */
@Controller
@RequestMapping("admin")
public class ExpandListController {
	@Resource
	private ExpandList exp;
	
	@RequestMapping("/expandlist/init")
	@ResponseBody
	public String init(String data){
		Map<String, String> map = JsonUtil.getMapStr(data);
		return exp.init(map);
	}
	
	@RequestMapping("/expandlist/add")
	@ResponseBody
	public String add(BaseSaleman bs){
		return exp.add(bs);
	}
	
	@RequestMapping("/expandlist/find")
	@ResponseBody
	public String add(String id){
		return exp.find(id);
	}
}

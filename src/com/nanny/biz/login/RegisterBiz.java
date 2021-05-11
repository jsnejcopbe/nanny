package com.nanny.biz.login;

import com.nanny.model.BaseUsers;

import net.sf.json.JSONObject;

/**
 * 登陆数据层
 * @author lpc
 *
 */

public interface RegisterBiz {
		
		/**
		 * 添加新用户
		 * @param bean
		 * @return
		 */
		public JSONObject addNewUser(BaseUsers bean);
}

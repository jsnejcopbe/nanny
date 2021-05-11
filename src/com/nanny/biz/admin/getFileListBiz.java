package com.nanny.biz.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public interface getFileListBiz {

     
     public JSONObject listPath(File path) throws IOException;
     
     public void setRequest(HttpServletRequest request);
}

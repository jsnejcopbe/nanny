<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn">
<head>
<title>写信</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/wap2875ca.css">

</head>
<body>

<div class="compose_topBar"> <a class="qm_btn" type="submit"  >取消</a>
  <div class="func_growSpace"></div>
  <div class="compose_mailType qm_dropdownMenuGroup"> <a href="javascript:;" class="compose_mailType_text" >邮件<span class="qm_icon qm_icon_SelArrow"></span></a> </div>
  <input class="qm_btn qm_btn_Blue" type="submit" value="发送" name="RedirectY29tcG9zZV9zZW5kP21vYmlsZXNlbmQ9MSZzPQ__" tabindex="32006">
</div>
<div>
  <div class="compose_form_itemWrap">
    <div class="compose_form_item">
      <label for="showto" class="compose_form_item_label">收件人：</label>
    </div>
    <div class="compose_form_itemWrap">
      <div class="compose_form_item">
        <div class="compose_form_item_cnt">
          <textarea id="origin" style="display:none;"></textarea>
          <textarea placeholder="请输入邮件内容..." accesskey="" id="content" name="" class="compose_form_item_textarea" tabindex=""></textarea>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
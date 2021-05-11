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
<title>查看信件</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, user-scalable=yes, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/work/mobile/css/wap2ab915.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/work/mobile/css/wap2875ca.css">

</head>
<!--[if lt IE 10]><script>
(function() {
var a = ['header','footer','section','aside','article','nav','hgroup','figure','figcaption','time','mark','output','meter'];
for(var i = 0, m = a.length; i < m; i++) {document.createElement(a[i]);}
})();
</script><link rel="stylesheet" type="text/css" href="https://res.mail.qq.com/wapmail/zh_CN/htmledition/style/mobile/wap_ie2ab915.css" /><![endif]-->

</head>
<body>
<div class="qm_frame readmail">
  <section class="">
    <div id="ct">
      <div class="qm_module module_readmail">
        <div class="qm_tipsAbs" mod="hide" style="display:none;"> </div>
        <div id="mod_readmail">
          <div class="">
            <div style="height:44px;background-color:#f3f3f3" class="qm_toolbar_left"> 
             <a style="margin-left:5px;" class="m-goback" href="/nanny/shop/shopMessage.html"><i class="iconfont"></i></a>
            <a class="qm_btnIcon" style="margin-left:77%;" href="/nanny/shop/shopMessagewriter.html"><span class="qm_icon qm_icon_Compose"></span></a>
          </div>

          <div class="mailcontent">
            <div class="readmail_item readmail_item_typeNormal">
              <div un="mail" class="readmail_item_normalPage">
                <div class="readmail_item_head">
                  <div class="readmail_item_head_contact">
                    <div class="readmail_item_contactAbbreviations" id="hidedetail">
                      <div class="readmail_item_from func_ellipsis">发信人：
                      <c:if test="${list.nickName=='null'}">系统</c:if>
                    
                       <c:if test="${list.nickName!='null'}">  ${list.nickName}</c:if></div>
                      <span class="readmail_item_date">${list.createTime}</span></div>
                  </div>
                </div>
              </div>
              
               <div class="readmail_item_contentNormal qmbox">
                  <div>
                           <span style="line-height: 1.5;">${list.mailCon}</span></div>
                </div>
          
              <div class="qm_cntEditZone">
               
               <c:if test="${list.userID!='0'}">
                <div class="qm_form_item">
                  <div class="qm_form_item_cnt">
                    <textarea placeholder="回复" accesskey="q" class="qm_formText qm_formText_Textarea" name="content" id="content" cols="30" rows="10"></textarea>
                  </div>
                </div>
                <div class="qm_form_item">
                  <div class="qm_form_item_cnt">
                    <div class="qm_btnGroup func_flexJustifyRight">
                      <button class="qm_btn qm_btn_Blue"  onclick="javascript:msgview( ${list.userID});">发送</button>
                    </div>
                  </div>
                </div>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </section>
</div>
<script type="text/javascript" src="/nanny/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/nanny/js/layer/layer.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/work/mobile/js/shopmsg.js"></script>
</body>
</html>
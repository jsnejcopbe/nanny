<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn">
<head>
<title>站内信</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/work/mobile/css/wap2875ca.css">

</head>
<body style="background-color: #f1f1f1;">
	<div class="qm_frame mail_list">
		<div class="qm_toolbar" >

			<c:if test="${sid=='null'}">

				<a style="margin-left:5px;" class="m-goback"
					href="${pageContext.request.contextPath}/users/userIndex.html"><i
					class="iconfont"></i> </a>
			</c:if>
			<c:if test="${sid!='null'}">
				<a class="m-goback"
					href="${pageContext.request.contextPath}/shop/shopIndex.html"><i
					class="iconfont">&#xe600;</i>
				</a>
			</c:if> 

			<h1 class="qm_toolbarTitle"
				style="margin-left:auto;margin-right:auto;">站内信</h1>
		</div>
		<div id="ct" style="margin-top:44px;">
			<div class="qm_module module_mail_list" style="padding-top: 5px;">
				<div id="mod_list">
					<div class="readmail_list" style="background-color:#f1f1f1;">
						<!-- 页面主题内容 开始-->

						<c:forEach items="${mesg}" var="mesg">
							<div id="" class="maillist_listItem" style="border-top-right-radius:20px;;background-color:#fff;margin-top:10px;">
								<label class="maillist_listItemLeft"></label> <a
									class="maillist_listItemRight" tabindex="2" href=" /nanny/shop/shopmsgview-${mesg.id}.html">
									<div class="maillist_listItemLineFirst">
										<div class="maillist_listItem_title func_ellipsis">
											
  											<c:if test="${loginUser.userID==mesg.userID}">${mesg.oname}</c:if>
  											
											<c:if test="${loginUser.userID==mesg.otherSide}">
											<c:if test="${mesg.userID=='0'}">系统</c:if>
											<c:if test="${mesg.userID!='0'}">${mesg.uname}</c:if>
											</c:if>
  											
										</div>
										<div class="func_growSpace"></div>
										<span class="maillist_listItem_time">${mesg.createTime}</span>
									</div>
									<div class="maillist_listItemLineThird">
										<div style="margin-top:5px;"
											class="maillist_listItem_abstract func_ellipsis">
											<c:if test="${ mesg.memo=='null'}">
												<c:set var="testStr" value="${mesg.mailCon}" />
												<c:choose>
													<c:when test="${fn:length(testStr) > 20}">
														<c:out value="${fn:substring(testStr, 0,18)}....." />
													</c:when>
													<c:otherwise>
														<c:out value="${testStr}" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${ mesg.memo!='null'}">
												<span style="color: red;">${mesg.memo}</span>
											</c:if>
										</div>
										<div style="margin-top:5px;text-align:right;color: #795548;"
											class="maillist_listItem_abstract func_ellipsis">${mesg.stats}</div>
										<div class="maillist_listItem_mailtag"></div>
									</div> </a>
							</div>

						</c:forEach>




						<!-- 页面主题内容 结束-->
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="/nanny/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/nanny/js/layer/layer.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/work/mobile/js/ptloginout.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/work/mobile/js/aq_common.js"></script>
			<script type="text/javascript"
			src="${pageContext.request.contextPath}/work/mobile/js/shopmsg.js"></script>
</body>
</html>
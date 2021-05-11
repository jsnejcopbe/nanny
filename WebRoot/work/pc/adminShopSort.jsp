<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>店铺排序</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/work/pc/css/adminShopSort.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->	
		<jsp:include page="head.jsp" />	
		
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="menu.jsp" />
			
			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
							<li class="active">店铺推荐排列</li>
						</ul>
						<%--<h3 class="page-header"> </h3>--%>
					</div>
				</div>
				<!-- 主体内容 开始 -->	
				<div class="row">
					<div class="col-xs-8">
					<div class="panel panel-archon panel-todo">
							<div class="panel-body">
								<h2 class="pa-title">店铺推荐排列</h2>
								<div class="pa-search">
									<form id="queryForm" action="${pageContext.request.contextPath}/admin/shopsort.html<c:if test="${type!=null }">?type=${type}</c:if>" method="post">
			                            <div class="input-group">
				                            <input name="name" placeholder="查找商家或地址" class="input form-control" type="text" value="${name }">
				                            <span class="input-group-btn">
		                                        <a href="javascript:void(0)" class="btn btn btn-primary js-doquery"> <i class="iconfont-gl">&#x3432;</i> 搜索</a>
			                                </span>
		                                </div>
		                            </form>
		                        </div>
		                        <div class="con-list">
		                        	<ul class="nav nav-tabs">
		                                <li <c:if test="${type==null }">class="active"</c:if>>
		                                	<a id="tab-shop" class="tab-pane-btn" href="javascript:void(0)"><i class="iconfont-gl">&#xe62e;</i> 商户</a>
		                                </li>
		                                <li <c:if test="${type!=null }">class="active"</c:if>>
		                                	<a id="tab-add" class="tab-pane-btn" href="javascript:void(0)"><i class="iconfont-gl">&#xe60e;</i> 地址</a>
		                                </li>
		                            </ul>
		                            <div class="tab-content">
		                            
		                            	<c:if test="${type==null }">
		                            	<!-- 用户列表 -->
		                            	<div class="tab-pane <c:if test="${type==null }">active</c:if>">
		                            		<table class="table table-striped table-hover con-list">
		                            			<tbody>
		                            				<c:forEach items="${backData.data }" var="dl">
		                            				<tr>
		                            					<td class="con-img">
		                            						<img src="${dl.shop_icon }">
		                            					</td>
		                            					<td class="con-name">
		                            						<span>${dl.nickName }</span>
		                            					</td>
		                            					<td class="con-shop">${dl.shop_name }</td>
		                            					<td>
		                            						<i class="iconfont-gl">&#xe6d5;</i>&nbsp; <span>完成订单:</span>
		                            					</td>
		                            					<td>${dl.orderCount }</td>
		                            					<td>
		                            						<a href="javascript:void(0)" class="js-checkshop label label-primary" data-shop="${dl.shopID }">点击设置</a>
		                            					</td>
		                            				</tr>
		                            				</c:forEach>
		                            			</tbody>
		                            		</table>
		                            		<div class="btn-area pagin-area">
												<div class="paging_bootstrap pagination js-pagin"></div>
											</div>
		                            	</div>
		                            	</c:if>
		                            	
		                            	<c:if test="${type!=null }">
		                            	<!-- 地址列表 -->
		                            	<div class="tab-pane <c:if test="${type!=null }">active</c:if>">
		                            		<table class="table table-striped table-hover con-list">
		                            			<c:forEach items="${backData.data }" var="dl">
		                            			<tr>
		                            				<td class="con-name">
	                            						<span>${dl.addName }</span>
	                            					</td>
	                            					<td class="con-shop">${dl.detAdd }</td>
	                            					<td>${dl.city }</td>
	                            					<td>${dl.area }</td>
	                            					<td>
	                            						<a href="javascript:void(0)" class="js-checkshop label label-primary" data-add="${dl.adressID }">点击设置</a>
	                            					</td>
		                            			</tr>
		                            			</c:forEach>
		                            		</table>
		                            		<div class="btn-area pagin-area">
												<div class="paging_bootstrap pagination js-pagin"></div>
											</div>
		                            	</div>
		                            	</c:if>
		                            </div>
		                        </div>
							</div>
						</div>
					</div>
					
					<div class="col-xs-4">
						<div class="panel panel-archon">
							<div class="panel-body">
							</div>
							<div class="btn-area">
								<a class="btn btn-block btn-primary" href="javascript:saveOrderSet()">保存设置</a>
							</div>
						</div>
					</div>
				</div>			
			</div>
		</div>
	</div>
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">var BASEPATH="${pageContext.request.contextPath}";</script>
	<script type="text/javascript">
	new Pagin({
		    size :${backData.size},
		    perPage :5, 
		    total  :${backData.total},
		    nowPage:${backData.nowPage},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#queryForm").append('<input type="hidden" name="page" value="'+page+'">');
		    $("#queryForm").submit();
		}
	});
	</script>
	<script src="${pageContext.request.contextPath}/work/pc/js/adminShopSort.js"></script>
</body>
</html>

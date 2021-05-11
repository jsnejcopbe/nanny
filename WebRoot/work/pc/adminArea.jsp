<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>平台营业范围管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	  <!-- 样式加载 -->
	  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
	  <!-- 兼容性 -->
	  <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
	  <!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
	<style>
	a {
		cursor: pointer;
	}
	
	th{
		text-align: center;
	}
	
	tr{
		height: 45px !important;
	}
	
	td{
		text-align: center;
	}
	
	.mybtns{
		margin-left: 10px;
	}
	#shop_table thead tr th:nth-child(2){display: none;}
	#shop_table tbody tr td:nth-child(2){display: none;}
	.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
	.mail-options-nav{
		padding: 10px;
		border: 1px solid #B9C4D5;
		border-bottom:none!important;
		background-color: #F5F5F5;
	}
	</style>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>


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
							<li class="active">平台营业范围管理</li>
						</ul>
						
						<h3 class="page-header"> 平台营业范围管理</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form id="queryForm" action="${pageContext.request.contextPath}/admin/area-1.html" method="post">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="输入地址名称" type="text" name="areaName" value="${areaName }">
						                 <input id="province" type="hidden" value="${province }" name="province"/>
						                 <input id="city" type="hidden" value="${city }" name="city"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							<div class="pull-right" id="state_query">
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${province!='' && province!=null }">
									${province }
									</c:if>
									<c:if test="${province=='' || province==null }">
									请选择省份 
									</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="javascript:setArea('#province','')">全部</a></li>
										<c:forEach items="${provinceList }" var="pl">
										<li><a href="javascript:setArea('#province','${pl.province }')">${pl.province }</a></li>
										</c:forEach>
									</ul>
								</div>
								<div class="btn-group">
									<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<c:if test="${city!='' && city!=null }">
									${city }
									</c:if>
									<c:if test="${city=='' || city==null }">
									请选择城市 
									</c:if>
									<span class="caret"></span>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li><a href="javascript:setArea('#city','')">全部</a></li>
										<c:forEach items="${cityList }" var="cl">
										<li><a href="javascript:setArea('#city','${cl.city }')">${cl.city }</a></li>
										</c:forEach>
									</ul>
								</div>
								<a class="btn btn-info js-query" href="javascript:void(0)" data-toggle="modal" data-target="#mapModal"><i class="fa fa-plus"></i> 新增地址</a>
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							               <th>地址名</th>
							               <th>省份</th>
							               <th>城市</th>
							               <th>区</th>
							               <th>详细地址</th>
							               <th>坐标</th>
							               <th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${queryData.data }" var="da">
							           <tr class="map-con">
							           	   <td class="con-name">${da.addName }</td>
							           	   <td class="con-province">${da.province }</td>
							           	   <td class="con-city">${da.city }</td>
							           	   <td class="con-area">${da.area }</td>
							           	   <td class="con-add">${da.detAdd }</td>
							           	   <td class="con-pos">${da.lat },${da.lon }</td>
							           	   <td>
							           	   		<a class="btn btn-small btn-danger" href="javascript:void(0)">编辑地址</a>
							           	   </td>
							           	   <input class="con-id" type="hidden" value="${da.id }">
							           </tr>
							           </c:forEach>
							        </tbody>
								</table>
							</div>
							<div class="pagin-area" style="text-align: center;">
								<div class="paging_bootstrap pagination js-pagin" style="margin: 0px;"></div>
							</div>
						</div>
					</div>
				</div>
		 	</div>
		 </div>
		 <!-- 页面主体内容 结束 -->
	</div>

	<!-- 地址选择模态框 -->
	<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content" style="width: 1000px;">
		         <div class="modal-header">
		            <button type="button" class="close js-closemodal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="myModalLabel">
		               	<input id="addName" type="text" placeholder="请输入地址名称" class="form-control" style="display: inline-block;width: 200px;">
		            </h4>
		         </div>
		         <div class="modal-body" style="padding: 0px;">
		         	<iframe id="mapIframe" src="/nanny/positionpicker.jsp" style="width:100%; height:500px; border:none;"></iframe>
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-default js-closemodal">关闭
		            </button>
		            <a href="javascript:upMapData()" type="button" class="btn btn-primary">
		                     保存
		            </a>
		         </div>
		      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
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
	<script src="${pageContext.request.contextPath}/work/pc/js/adminArea.js"></script>
	<script type="text/javascript">
	new Pagin({
		    size :${queryData.size},
		    perPage :5, 
		    total  :${queryData.total},
		    nowPage:${queryData.nowPage},
		    dealFun:function(size,page){
		    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
		    $("#queryForm").attr("action","${pageContext.request.contextPath}/admin/area-"+page+".html");
		    $("#queryForm").submit();
		}
	});
	
	</script>
</body>
</html>

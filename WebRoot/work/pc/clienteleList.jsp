<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>客户管理</title>
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
	
	.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
	.mail-options-nav{
		padding: 10px;
		border: 1px solid #B9C4D5;
		border-bottom:none!important;
		background-color: #F5F5F5;
	}
	.used{color: #D9534F;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.unuse{color: #5BC0DE;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.used>i,.unuse>i{font-size: 18px;}
	
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
							<li class="active">平台客户管理</li>
						</ul>
						
						<h3 class="page-header">平台客户管理</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" >
								<form action="${pageContext.request.contextPath}/admin/clientele.html?pageIndex=1&pageSize=${page.pageSize}" method="post" id="uForm">
						                <%-- <span id="state_query">下单日期：</span>  
									 <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmin" name="logmin" value="${ lognmin }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									  -
									  <input type="text" class="ui-ipt" placeholder="请选择日期" id="logmax" name="logmax" value="${ logbmax }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						                 
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="联系电话" type="text" name="many" value="${name}" onkeyup="clearNum(this)">
						                 <input id="hide_type" type="hidden" value="${situation}" name="state"/> 
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						            </a>
					            --%><span>下单日期：</span> <input type="text"
										class="ui-ipt" placeholder="请选择日期" id="logmin" name="logmin"
										value="${ lognmin }" 
										onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
									- <input type="text" class="ui-ipt" placeholder="请选择日期"
										id="logmax" name="logmax" value="${ logbmax }"  
										onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
									
									<input class="ui-ipt" style="margin:0px;" size="20" placeholder="联系电话、昵称" type="text" name="many" value="${name}" >
									<!-- onkeyup="clearNum(this)" -->
									<button href="javascript:void(0)" class="btn btn-info"
										id="sq_submit" type="submit">查询</button>
					            
					            
					            </form>
							</div>
							<div class="pull-right" id="">
								<%--<a href="javascript:void(0)" class="btn bg-purple text-white" data-id="-1">全部状态</a>
								<a href="javascript:void(0)" class="btn btn-info" data-id="0">开启</a>
								<a href="javascript:void(0)" class="btn btn-success" data-id="1">关闭</a>--%>
								<a type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal" >批量发红包</a>
								</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							            	<th></th>
							             	<th>头像</th>
							               <th>用户名</th>
							               <th>联系电话</th>
							               <th>注册时间</th>
							               <th>余额</th>
							               <th>订单数</th>
							               <th>用户类型</th>
							               <th>推荐商户</th>
							               <%--<th>状态</th>
							               --%><th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${client.client}" var="bu">
							           <tr class="map-con">
							           <td> 
							           		<c:if test="${bu.originID=='null'}">
							           		</c:if>
							           		<c:if test="${bu.originID!='null'}">
							           					 <input type="checkbox"  name="cbids"  value="${bu.id}" id="cb_${bu.id}"/>
							           			</c:if>		
							           </td>
							            <td style="display: none;"> <input type="hidden" value="${bu.originID}" name="opid"> </td>
							           	   <td class="con-name">
							           	   	<img alt="" src="${bu.headImg }" width="50px">
							           	   </td>
							           	   <td class="con-city">${bu.nickName }</td>
							           	   <td class="con-area">${bu.tel }</td>
							           	   <td class="con-add">${bu.createTime }</td>
							           	   <td class="con-name">${bu.balance}</td>
							           	   <td class="con-name">${bu.orcount}</td>
							           	  
							           	 	<%--<td class="con-pos">${bu.status }</td>
							           	   <td>
							           	   <c:if test="${bu.situation==0 }">
							           	   		<a class="btn btn-small btn-danger" href="javascript:updbusin(${bu.id})">关闭小铺</a>           	   		
							           	   </c:if>
							           	   <c:if test="${bu.situation==1 }">
							           	   		<a class="btn btn-small btn-info" href="javascript:updbusin(${bu.id})">开启小铺</a>
							           	   </c:if>
							           	   <a class="btn btn-small btn-danger" href="${pageContext.request.contextPath}/shop/fans/findby_${bu.id}.html">店铺粉丝</a>
							           	   </td>--%>
							           		<td> 
							           			<c:if test="${bu.originID=='null'}">
							           					普通用户
							           			</c:if>
							           			<c:if test="${bu.originID!='null'}">
							           					微信用户
							           			</c:if>
							           		</td>
							           		<td><a href="javascript:opp(${bu.id });">${bu.shop_name } </a></td>
							           		<td>
							           		<c:if test="${bu.originID=='null'}">
							           		</c:if>
							           		<c:if test="${bu.originID!='null'}">
							           					 <a class="used js-remveSta"  href="javascript:usered(${bu.id })"><i class="iconfont-gl">&#xe60c;</i><br>红包</a>
							           			</c:if>	
							           			<a class="used" href="${pageContext.request.contextPath}/admin/userOrderList.html?uid=${bu.id}">
							           	  			<i class="iconfont-gl">&#xe615;</i><br>
							           	  		 订单
							           	  		 </a>
						           	   	        	   		
						           	   	  
							           		
							           		</td>
							           </tr>
							           <tr style="display:none"  id="sname_${bu.id }">
							           		<td style="text-align: left;padding: 15px; background-color:rgb(95, 184, 120);color: white;" >
							           		  <p><span >商铺：${bu.shop_name } </span></p>
							           		  <p><span>小区：${bu.addName }</span></p>
							           	  <p>地址：${bu.detAdd}</p>
							           	  </td>
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
		<!-- 模态框 -->
	<div class="modal fade edit-modal" id="myModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">红包信息</h4>
				</div>
				<form action="" method='post' id="updred">
					<div class="modal-body" id="" style="text-align: -moz-center;">
						<div class="panel-body">
							<div  class="form-horizontal">
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">活动名称</label>
									<div class="col-lg-10 col-md-9">
										<input class="form-control form-cascade-control" id=""
											name="actName" placeholder="请填写发放红包的活动名称" type="text"
											value="">

									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">红包金额</label>
									<div class="col-lg-10 col-md-9">
										<input class="form-control form-cascade-control" id=""
											name="totalMoney" placeholder="请填写红包金额" type="text" value=""
											onkeyup="clearNoNum(this)">

									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">红包祝愿</label>
									<div class="col-lg-10 col-md-9">
										<input class="form-control form-cascade-control" id=""
											name="wishing" placeholder="请填写红包祝愿" type="text" value="">

									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">红包类型</label>
									<div class="col-lg-4 col-md-5">

										<span><input name="redpa" type="radio" value="0" checked="checked"
											/>普通红包</span>
									</div>
									<div class="col-lg-4 col-md-5">
										<span><input name="redpa" type="radio" value="1"/>裂变红包</span>
									</div>


								</div>
								<div class="form-group" id="rnum">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">裂变人数</label>
									<div class="col-lg-10 col-md-9">
										<input class="form-control form-cascade-control" id=""
											name="num" placeholder="请填写人数" type="number" value="" onkeyup="this.value=this.value.replace(/\D/g,'')">

									</div>
								</div>
								</div>

							</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" onclick="redpack()">
							发发发发</button>
					</div>
				</form>
			</div>
		</div>

	</div>
	<!-- 加载js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/clientele.js"></script>
	<script type="text/javascript">
	new Pagin({
		size :${client.size},
    	perPage :5, 
    	total  :${client.total},
    	nowPage:${client.nowPage},
	    dealFun:function(size,page){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/clientele.html?pageIndex="+page+"&pageSize="+size);
	    $("#uForm").submit();
	}
});

new g_fnImgCheck();

	</script>
</body>
</html>

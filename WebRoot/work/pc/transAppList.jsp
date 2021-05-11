<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>提现记录</title>

<meta name="renderer" content="webkit">
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
</head>
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
.ui-ipt{
	display: inline-block!important;
	float: none!important;
	width: 150px!important;
}
.pagination>ul{margin: 0px;}

.mo-con{border: 1px solid #ddd;background-color: #fff;margin-top: 15px;}
.mo-con .con-item{border-bottom: 1px solid #ddd;padding-top: 10px;padding-bottom: 10px;}
.mo-con .con-item:LAST-CHILD{border: none!important;}
.mo-con .con-item>span{display: inline-block;}
.mo-con .con-item>span:FIRST-CHILD{padding: 0px;}
.mo-con .con-item>div input{border: none;}

.trans-btn{margin-top: 15px;background-color: #fff;border: 1px solid #ddd;}
.trans-btn>a{display: block;text-align: center;padding: 10px 0px;background-color: #E70012;color: #fff;}
</style>

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
							<li class="active">店铺提现记录</li>
						</ul>
						<h3 class="page-header"> <i class="iconfont-gl">&#xe615;</i> 店铺提现记录</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form id="queryForm" action="${pageContext.request.contextPath}/users/transrec.html" method="post">
		                  			<div class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input type="text" class="ui-ipt form-control" placeholder="起始日期" id="start" name="start" value="${ start }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						                 <span>-</span>
									  	 <input type="text" class="ui-ipt form-control" placeholder="结束日期" id="end" name="end" value="${ end }"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </div>
						            <input id="nextpage" type="hidden" name="page" value="${page }">
					            </form>
							</div>
							<div class="pull-right" id="state_query">
								<a class="btn btn-danger" href="javascript:showApp()">立即提现</a>
							</div>
						</div>
						
						<div class="panel com-pan">
							<div class="panel-body">
								<table class="table users-table table-condensed table-hover">
									<thead>
										<tr>
											<th>银行名称</th>
											<th>提现账号</th>
											<th>收款户名</th>
											<th>提现金额</th>
											<th>提现状态</th>
											<th>提现时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${data.data }" var="dl">
										<tr class="tr">
											<td>${dl.bankName }</td>
											<td>${dl.account }</td>
											<td>${dl.recName }</td>
											<td>${dl.money }</td>
											<td>${dl.statuTxt }</td>
											<td>${dl.createTime }</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<div class="btn-area pagin-area" style="text-align: center;padding: -20px 0px;">
								<div class="paging_bootstrap pagination js-pagin"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 模态框 -->
	<div class="modal fade" id="transferModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="myModalLabel">
	               	发起提现申请
	            </h4>
	         </div>
	         <div class="modal-body">
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 隐藏表单 -->
	<input id="canUserBalance" type="hidden" value="">
	<input id="forbidBalance" type="hidden" value="">
	<input id="userID" type="hidden" value="${userID }">
	<input id="accountID" type="hidden" value="">
	
	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	var BASEPATH = "${pageContext.request.contextPath}";
	new Pagin({
		    size :${size},
		    perPage :5, 
		    total  :${data.totalCount},
		    nowPage:${data.nowPage},
		    dealFun:function(size,page){
		    $("#nextpage").val(page);
		    $("#queryForm").submit();
		}
	});
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/js/transappList.js?v=0.02"></script>
</body>
</html>

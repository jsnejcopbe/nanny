<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE HTML>
<html>
<head>
<title>提现申请</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css"
	rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
<style>
a {
	cursor: pointer;
}

th {
	text-align: center;
}

tr {
	height: 45px !important;
	text-align: center !important;
}

td {
	text-align: center;
}

.mybtns {
	margin-left: 10px;
}

.com-pan {
	border: 1px solid;
	border: 1px solid #B9C4D5;
}

.mail-options-nav {
	padding: 10px;
	border: 1px solid #B9C4D5;
	border-bottom: none !important;
	background-color: #F5F5F5;
}
</style>
<script type="text/javascript">
	var path = "/nanny/";
</script>
<body>
	<div class="site-holder">
		<!-- 页面主体内容 开始 -->


		<!-- 头部内容 -->
		<jsp:include page="head.jsp" />
		<!-- 头部 结束 -->


		<div class="box-holder">
			<!-- 左侧菜单 -->


			<!-- 左侧菜单 -->

			<jsp:include page="menu.jsp" />

			<!-- 左侧菜单 结束-->


			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a>
							</li>
							<li class="active">提现申请</li>
						</ul>

						<h3 class="page-header">用户提现申请</h3>
					</div>
				</div>


				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query">
								<form method="post" id="uForm">
									<input id="hide_type1" type="hidden" value="${statu}"
										name="state" /> <span>日期：</span> <input type="text"
										class="ui-ipt" placeholder="请选择日期" id="logmin" name="logmin"
										value="${ lognmin }" 
										onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									- <input type="text" class="ui-ipt" placeholder="请选择日期"
										id="logmax" name="logmax" value="${ logbmax }"  
										onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									<input type="text" class="ui-ipt" placeholder="请输入查询的电话"
										id="tel" name="tel" value="${ tel }" >

									<button href="javascript:void(0)" class="btn btn-info"
										id="sq_submit-tel" type="submit">查询</button>										
								</form>
							</div>
							<div class="pull-right" id="state_query">
								<form action="${pageContext.request.contextPath}/cashapply.html?pageIndex=1&pageSize=${pageSize}"
									method="post" id="uForm">
									<input id="hide_type" type="hidden" value="${statu}"
										name="state" />
									<div class="btn-group">
										<button type="button" class="btn btn-primary dropdown-toggle"
											data-toggle="dropdown">
											 <c:if test="${ statu=='3' }"> 审核状态 </c:if>
											 <c:if test="${ statu=='2' }"> 未通过 </c:if>
											 <c:if test="${ statu=='0' }"> 待审核 </c:if> 
											 <c:if test="${ statu=='1' }"> 通过 </c:if>
											 <c:if test="${  statu!='0'&&statu!='1'&&statu!='2'&& statu!='3'}"> 审核状态</c:if>
															<span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="javascript:void(0)"   data-id="3" >全部</a>
											</li>
											<li><a href="javascript:void(0)"  data-id="1"  >已通过</a>
											</li>
											<li><a href="javascript:void(0)"   data-id="2" >未通过</a>
											</li>
											<li><a href="javascript:void(0)"   data-id="0" >待审核</a>
											</li>
										</ul>
									</div>
                                    <input type="hidden" class="ui-ipt "  id="logminbtn" name="logmin" value="${ lognmin }" >
								    <input type="hidden" class="ui-ipt " id="logmaxbtn" name="logmax" value="${ logbmax }"  >
									<input type="hidden" class="ui-ipt " id="telbtn" name="tel" value="${ tel }" >
                                    
									<input id="hide_type1" type="hidden" value="0" name="state" />
									<input id="hide_type2" type="hidden" value="1" name="state" />
								</form>

							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover"
									id="shop_table">
									<thead>
										<tr>
											<th></th>
											<th>提现用户</th>
											<th>商家名称</th>
											<th>提现金额</th>
											<th>提现银行</th>
											<th>收款人姓名</th>
											<th>收款人银行账号</th>
											<th>收款人联系方式</th>
											<th>创建时间</th>
											<th>申请状态</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list}" var="pl">
											<tr class="tr">
												<td style="vertical-align: middle;">
												<td style="vertical-align: middle;"><span>${pl.nickName}</span>
												</td>
												<td style="vertical-align: middle;">
													<span>
													<c:if test="${pl.shop_name == 'null' }">普通用户</c:if>
													<c:if test="${pl.shop_name != 'null' }">${pl.shop_name}</c:if>
													</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.money}</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.bankName}</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.accName}</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.account}</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.tel}</span>
												</td>
												<td style="vertical-align: middle;"><span id="">${pl.createTime}</span>
												</td>
												
												<td style="vertical-align: middle;"><label
													class="label bg-success" name="state"> <c:if
															test="${ pl.status=='2' }"> 未通过 </c:if> <c:if
															test="${ pl.status=='0' }"> 待审核 </c:if> <c:if
															test="${ pl.status=='1' }"> 通过 </c:if> </label>
												</td>
												
												<td style="vertical-align: middle;">
												<c:if test="${ pl.status=='0' }">
												<div class="btn-group">
														<button type="button"
															class="btn btn-primary dropdown-toggle"
															data-toggle="dropdown">
															操作 <span class="caret"></span>
														</button>
														<ul class="dropdown-menu"  id="${ pl.id}" role="menu">
															<li><a href="javascript:pass(${ pl.id});">通过</a>
															</li>
															<li><a href="javascript:notpass(${ pl.id});">未通过</a>
															</li>
														</ul>
													</div>
													</c:if>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="pagin-area" style="text-align: center;">
									<div class="paging_bootstrap pagination js-pagin"
										style="margin: 0px;"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 上一页 按钮 -->

				<!-- 页面主体内容 结束 -->

			</div>
			<!-- 隐藏表单 -->
			<input id="pageSize" type="hidden" value="${disData.size }">
			<!-- 加载js -->

			<script
				src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
			<script src="${pageContext.request.contextPath}/js/base.js"></script>
			<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
			<script
				src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/work/pc/js/adminCashapply.js"></script>
			<script
				src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
			<script
				src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
			<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
			<script type="text/javascript">

 new Pagin({
	size :${pageSize},
	perPage :5, 
	total  :${pageCount},
	nowPage:${pageIndex},
    dealFun:function(size,page){
    $("#uForm").attr("action","${pageContext.request.contextPath}/cashapply.html?pageIndex="+page+"&pageSize="+size);
    $("#uForm").submit();
}
}); 



function goToPage(page){
    $('body').load("getComments.do?page=" + page);
}
 
$("body").on("click","#state_query a",function(){
var id = $(this).attr("data-id");
var logminbtn = document.getElementById("logmin").value;
var logmaxbtn = document.getElementById("logmax").value;
var telbtn = document.getElementById("tel").value;
$("#logminbtn").val(logminbtn);
$("#logmaxbtn").val(logmaxbtn);
$("#telbtn").val(telbtn);
$("#hide_type").val(id);
$("#state_query").find("form").submit();


})

new g_fnImgCheck(); 

</script>
</body>
</html>

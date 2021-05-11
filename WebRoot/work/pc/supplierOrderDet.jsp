<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>

<title>订单详情</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
<!-- 兼容性 -->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
<![endif]-->

<style type="text/css">
.prodes>img{width: 150px;}
.prodes>span{vertical-align: top;}
@font-face {font-family: 'iconfont';
    src: url('/nanny/fonts/iconfont-gl.eot'); /* IE9*/
    src: url('/nanny/fonts/iconfont-gl.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
    url('/nanny/fonts/iconfont-gl.woff') format('woff'), /* chrome、firefox */
    url('/nanny/fonts/iconfont-gl.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
    url('/nanny/fonts/iconfont-gl.svg#iconfont') format('svg'); /* iOS 4.1- */
}
.iconfont-gl{
    font-family:"iconfont" !important;
    font-size:16px;font-style:normal;
    -webkit-font-smoothing: antialiased;
    -webkit-text-stroke-width: 0.2px;
    -moz-osx-font-smoothing: grayscale;
}
</style>

</head>

<body>
	<div class="site-holder">
		
		<jsp:include page="suphead.jsp" />
		<!-- 页面主体内容 结束 -->
		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="supmenu.jsp" />
			
			<div class="content">
				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/supplier/supplierIndex.html">首页</a></li>
							<li><a href="javascript:history.go(-1)">订单列表</a></li>
							<li class="active">订单详情</li>
						</ul>
						<div class="form-group hiddn-minibar pull-right or-opt" style="padding-right: 10px;">
                            <a class="btn btn-info" href="javascript:history.go(-1)">返回</a>
                        </div>
						<h3 class="page-header"><i class="iconfont-gl">&#xe615;</i> 订单详情</h3>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="panel">
							<div class="panel-heading ">
								<h3 class="panel-title">
									订单信息
								</h3>
							</div>
							<div class="panel-body" style="padding: 0px;">
								<ul class="list-group">
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe6d5;</i>
										&nbsp;
										<span>订单状态:</span>
										<span class="badge">
										<c:if test="${jsoShop.status==0 }">
											订单尚未确认
											</c:if>
											<c:if test="${jsoShop.status==1 }">
											订单已确认
											</c:if>
											
											<c:if test="${jsoShop.status==2 }">
											订单已取消
											</c:if>
										</span>
									</li>
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe62b;</i>
										&nbsp;
										<span>收货人:</span>
										<span style="float: right;">${jsoShop.shop_name }</span>
									</li>
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe633;</i>
										&nbsp;
										<span>联系电话:</span>
										<span style="float: right;">${jsoShop.memo }</span>
									</li>
									<li class="list-group-item clearfix">
										<i class="iconfont-gl">&#xe60e;</i>
										&nbsp;
										<span>收货地址:</span>
										<span style="float: right; width: 70%;">${jsoShop.detAdd }${jsoShop.addName }</span>
									</li>
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe630;</i>
										&nbsp;
										<span>下单时间:</span>
										<span style="float: right;">${jsoShop.createTime }</span>
									</li>
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe627;</i>
										&nbsp;
										<span>付款方式:</span>
										<span style="float: right;">
											<c:if test="${jso.memo!='null' }">
											货到付款
											</c:if>
											<c:if test="${jso.memo=='null' }">
											在线付款
											</c:if>
										</span>
									</li>
								</ul>
								
								<ul class="list-group">
									<li class="list-group-item">
										<i class="iconfont-gl">&#xe62c;</i>
										&nbsp;
										<span>订单留言:</span>
									</li>
									<li class="list-group-item">
										${jsoShop.shopmsg }
									</li>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-md-8">
						<div class="panel panel-cascade">
							<div class="panel-heading">
								<h3 class="panel-title">
									订单号:${jsoShop.orderCode}
									<span class="pull-right">
										<a href="#" class="panel-minimize"><i class="fa fa-chevron-up"></i></a>
										<a href="#" class="panel-close"><i class="fa fa-times"></i></a>
									</span>
								</h3>
							</div>
							
							
							<div class="panel-body">
								<table class="table users-table table-condensed table-hover">
									<thead>
										<tr>
											<th>商品名称</th>
											<th>单价(元)</th>
											<th>数量</th>
										</tr>
									</thead>
									
									<tbody>
										
										
										<c:forEach items="${supro}" var="supro"> 
							            <tr>
							            
							             	<td class="prodes">
							             		<img src="${supro.cover }">
							             		<span>${supro.name }</span>
							             	</td>
							             	<td>
							             		¥${supro.price }
							             	</td>
							             	<td>
							             		
							             			
							             		     X${supro.count} 
							             	
							             		
							             		    
							             	</td>
							            </tr>
							          </c:forEach>
							         
							         </tbody>
							         
							         
								</table>
							</div>
							
							<div class="panel-footer">
								<div class="opt-msg"> 合计<span>¥${jsoShop.totalPrice }</span>元(含配送费${jsoShop.fee }元)</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 页面主体 结束 -->
		
		<%-- <!-- 隐藏表单 -->
		<input id="status" type="hidden" value="${orderBase.status }">
		<input id="orderID" type="hidden" value="${orderBase.id }">
		<input id="totalprice" type="hidden" value=${orderBase.totalPrice }> --%>
		
		<!-- 载入js -->
		<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
		<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
		<script type="text/javascript">
		var BASEPATH="${pageContext.request.contextPath}";
		new g_fnImgCheck();
		</script>
		
	</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务推广</title>
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
<script src="${pageContext.request.contextPath}//js/html5shiv.js"></script>
<![endif]-->

<style>
#shop_table thead tr th:nth-child(2){display: none;}
#shop_table tbody tr td:nth-child(2){display: none;}
.com-pan{border: 1px solid;border: 1px solid #B9C4D5;}
.mail-options-nav{
padding: 10px;
border: 1px solid #B9C4D5;
border-bottom:none!important;
background-color: #F5F5F5;
}

#expand_query{
	margin: 0px;
}

#expand_query > form{
	margin: -10px;
}

.mybtns {
    margin-left: 10px;
}

</style>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
	var init_url = path+"admin/expandlist/init.html";
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
							<li class="active">业务推广</li>
						</ul>
						<h3 class="page-header">业务员管理</h3>
					</div>
				</div>
				
				<!-- 业务员列表 -->
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left">
							  	  <a id="btn_add" href="javascript:void(0)" class="btn btn-success settings-toggle"><i class="fa fa-plus"></i> 添加业务员</a>
							</div>
							<div class="pull-right" id="expand_query">
								<form>
									<a class="input-group demo-input-group" style="margin-right: 15px;"> <input
											class="form-control nav-input-search pull-right" style="margin: 0px;" size="20" placeholder="业务员编码,姓名,电话" type="text"
											name="many"> <span class="input-group-btn">
											<button class="btn btn-info" id="sq_submit" type="submit">查询</button>
										</span>
									</a>
								</form>
							</div>
						</div>
						<div class="panel">

							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="expand_table">
									<thead>
										<tr>
											<th data-auto="ture" class="visible-lg">序号</th>
											<th data-hide="id"></th>
											<th data-value="guid" class="visible-lg">业务员编码</th>
											<th data-value="name" class="visible-lg">业务员姓名</th>
											<th data-value="tel" class="visible-lg">联系电话</th>
											<th data-value="password" class="visible-lg">密码</th>
											<th data-value="salman_total" class="visible-lg">推广人数</th>
											<th data-btns="btns" class="visible-lg">操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 页面主体内容 结束 -->
	</div>

	<div class="modal fade" id="expand_href_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">推广链接</h4>
				</div>
				<div class="modal-body" id="content">
					<div style="text-align: center;">
						<img src="" title="快扫我"/>
					</div>
					<a class="btn btn-primary btn-block">下载</a>
					<p id="copy_herf"></p>
					<a id="copy_btn" href="javascript:;" data-clipboard-target="copy_herf">点击复制链接</a>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="exp_add_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Modal title</h4>
				</div>
				<div class="modal-body">
					<form action="" id="exp_add_form">
						<a class="input-group demo-input-group"> 
							<span class="input-group-addon">
								<i class="fa fa-user"></i>
							</span> 
							<input type="hidden" id="hide_id" name="id" value="" /> 
							<input class="form-control" id="name" placeholder="姓名" data-verify="required" name="name" type="text">
						</a> 
						<!-- <a class="input-group demo-input-group"> 
							<span class="input-group-addon">
								<i class="fa fa-envelope"></i>
							</span>
							<input class="form-control" id="mail" placeholder="邮箱" data-verify="email" name="mail" type="text">
						</a>
						<a class="input-group demo-input-group"> 
							<span class="input-group-addon">
								<i class="fa fa-qq"></i>
							</span>
							<input class="form-control" id="qq" placeholder="qq" data-verify="qq" name="qq" type="text">
						</a>  -->
						<a class="input-group demo-input-group"> 
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span> 
							<input class="form-control" id="phone" placeholder="联系电话" data-verify="phone" name="tel" type="text">
						</a>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/js/copy/ZeroClipboard.min.js"></script>

	<script src="${pageContext.request.contextPath}/work/pc/js/syl/expandList.js"></script>
</body>
</html>
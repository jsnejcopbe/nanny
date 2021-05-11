<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>商户信息</title>

<!-- 样式加载 -->
<link href="${pageContext.request.contextPath}/work/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">


<link href="${pageContext.request.contextPath}/css/timepicki.css" rel="stylesheet">
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>


<style>
.bc {
	border-bottom: 1px solid #dddddd;
}

dt {
	text-align: left !important;
}

a, a:HOVER, a:ACTIVE, a:VISITED {
	text-decoration: none;
	color: #3071A9;
	cursor: pointer;
}

textarea {
	resize: none;
	border: none;
}

textarea:HOVER {
	resize: none;
	border: 1px solid #ddd;
	border-left: 6px solid #5c7399;
	border-radius: 2px;
}

textarea:FOCUS {
	resize: none;
	border: 1px solid #5c7399;
	border-left: 6px solid #5c7399;
	border-radius: 2px;
}

.opentime {
	width: 45%;
	float: left;
}

.myinput {
	border: 1px solid #5c7399;
	border-left: 6px solid #5c7399;
	border-radius: 2px;
}

.pro_dd {
	margin-left: 0px !important;
	float: left;
	padding-left: 30px;
}

.pro_dl {
	width: 30%;
	float: left;
}

.pro_dl>dt {
	width: 30px;
}

.modal-dialog {
	margin-top: 5px;
	margin-bottom: 15px;
	width: 1000px;
}

#mapModal {
	margin-top: 0;
}

.modal-content {
	overflow-y: auto;
	height: 650px;
}

.shop_img {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	border:3px #428BCA solid;
}

.sele{
		
		width: 75%;
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857143;
		color: #555;
		background-color: #fff;
		background-image: none;
		border: 1px solid #ccc;
		border-radius: 4px;
}
</style>

<body>

	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="suphead.jsp" />
		<!-- 页面主体内容 结束 -->

		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="supmenu.jsp"/>

			<div class="content" style="height: 1100px">

				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/supplier/supplierIndex.html">首页</a></li>
							<li class="active">商户信息</li>
						</ul>
						<h3 class="page-header"></h3>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">

						<div class="panel-body ">
							<ul id="myTab" class="nav nav-tabs" style="margin-bottom: 20px;">
								<li class="active"><a href="#shop_info" data-toggle="tab">店铺基本信息</a></li>
								<li class=""><a href="#profile2" data-toggle="tab">配送地址设置</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<!--====================================================================================================================-->
								<div class="tab-pane fade active in" id="shop_info">
									<div class="panel panel-cascade">
										<div id="head_info" style="display: block;" class="panel-body">
											<div class="col-md-10">
												<p id="shop_name">${obj.supinfo[0].supplier_name }</p>
												<span id="hide_id" style="display: none;"></span>
												<p id="shop_address">地址:${obj.supinfo[0].supplier_des }</p>
											  	<button type="button" class="btn btn-info" data-toggle="modal"  data-target="#myPass"> 修改资料</button>
											 
				                             </div>
										
											<div class="col-md-2">
												<%-- <img id="fimg"  class="shop_img" src="${obj.supinfo[0].supplier_icon}"></img> --%>
													<c:if test="${obj.supinfo[0].supplier_icon=='null' || lobj.supinfo[0].supplier_icon==''}">
							            				<img id="fimg"  class="shop_img" src="${pageContext.request.contextPath}/images/defalut.jpg" class="user-avatar">  
							            			</c:if>
							            			<c:if test="${obj.supinfo[0].supplier_icon!='null' && obj.supinfo[0].supplier_icon !='' }">
							            				<img id="fimg"  class="shop_img" src="${obj.supinfo[0].supplier_icon}"></img> 
							            			</c:if>
												<%-- <label class="" for="head_file">
														<img id="fimg"  src="${obj.supinfo[0].supplier_icon}" ></img>
														<input type="file" id="head_file" name="file" accept="image/jpeg" style="position:absolute;clip:rect(0 0 0 0);">
												</label> --%>
												
											</div>
										</div>
									</div>

									<div class="panel panel-cascade ">
										<!-- <div class="panel panel-default" style="display: none;" id="Areass">
											<div class="panel-heading">修改地址</div>
											<div class="panel-body" id="">
												<div class="col-md-12" id="delivery">
													<div class="alert alert-info alert-dismissable">
														<p>没有找到您的地址？ 请新增地址</p>
													</div>

													<dl class="dl-horizontal">
														<dt style="width: 100px">小区搜索 :</dt>
														<dd>
															<div class="input-group margin-bottom-sm">
																<input class="community_search form-control form-cascade-control nav-input-search" placeholder="请输入地址" name="detAdd"
																	type="text">
																<span class="input-group-addon">
																	<i id="again_btn" class="fa fa-close" style="padding: 3px 0px; cursor: pointer;">清空</i>
																</span>
															</div>
														</dd>
													</dl>
													<dl class="dl-horizontal">
														<a class="btn btn-info" id="map_add" href="javascript:void(0)">新增地址</a>
														<a class="btn btn-primary" id="sava_add" href="javascript:void(0)">确定地址</a>
													</dl>
												</div>
											</div>
										</div> -->

										<div class="panel panel-primary" id="base_content">
											<div class="panel-heading">基本信息</div>
											<div class="panel-body">
												<div class="panel-body bc">
													<dl class="dl-horizontal">
														<dt>店铺电话</dt>
														<dd id="shop_phone">${obj.supinfo[0].tel }</dd>
													<!-- 	<dd id="shop_phone1" style="display: none;"></dd>
														<dd id="shop_phone2" style="display: none;"></dd>
														<a href="javascript:edit_tel();" class="btn btn-primary  pull-right" style="margin-right: -15px;margin-top:-25px;">编辑</a> -->
													</dl>
												</div>
												<!-- <div style="display:none;padding: 20px 20px 40px 20px;"  id="shop_tel" >
												
							           		 		 <p><span >电话1： </span><input type="text"  id="tel1" placeholder="请输入电话" maxlength="11" value=""></p>
							           		  		<p><span >电话2： </span><input type="text"  id="tel2"  placeholder="请输入电话"  maxlength="11" value=""></p>
							           	  			<a href="javascript:upd_tel();" class="btn btn-primary  pull-right" >保存</a>
							          			</div> -->
												<!-- <div id="shop_notice" class="panel-body bc">
													<p></p>
													<textarea style="width: 101%; min-height: 80px;" rows="" cols="">暂无公告 现在添加公告？</textarea>
													<a href="javascript:;" id="edit_shop" class="btn btn-primary  pull-right" style="margin-right: -15px;">修改</a>
												</div> -->
												<div  class="panel-body bc">
													<dl class="dl-horizontal">
														<dt>配送范围</dt>
														<c:forEach items="${obj.global}" var="og">
															<dd> ${og.province}&nbsp; &nbsp;${og.city}&nbsp;&nbsp; ${og.area}&nbsp; 
															<%-- <c:if test="${og.memo!='null'}">
															(${og.memo})
															</c:if> --%>
															</dd>
														</c:forEach>
												
													</dl>
												</div>

											</div>
										</div>
									</div>
								</div>
								<!--====================================================================================================================-->
								<div class="tab-pane fade " id="profile2">
									<div class="col-md-12 panel panel-cascade">
										<!-- <div class="panel-heading"></div> -->
										<div class="panel panel-primary">
										
											<div class="panel-body bc">
												<div class="col-md-9 input-group pull-left">
													<div class="col-md-4">
													<span style="margin-right: 5px;">省:</span><select class="sele" id="province" name="province">
															</select></div>
													<div class="col-md-4" >
													<span style="margin-right: 5px;">市:</span><select class="sele" id="city" name="city">
																</select></div>
													<div class="col-md-4">
													<span style="margin-right: 5px;">县/区:</span><select class="sele"  id="area" name="area">
																</select></div>
												</div>
												<div class="col-md-2 input-group pull-right">
													<a  href="javascript:addglo();" class="btn btn-primary">添加</a> 
												</div>
												</div>
											<div id="onetable"  class=" panel-body bc">
													<table class="table users-table table-condensed table-hover" id="adress_table">
														<thead>
																<tr>
																	<th data-value="province">省份</th>
																	<th data-value="city">城市</th>
																	<th data-value="area">县区</th>
																	<!-- <th data-value="memo">备注</th> -->
																	<th data-btns="btns">操作</th>
																</tr>
														</thead>
														<tbody>
															<c:forEach items="${obj.global}" var="og">
																<tr>
																	<td>${og.province}</td>
																	<td>${og.city}</td>
																	<td>${og.area}</td>
																	<%-- <td>${og.memo}</td> --%>
																	<td><a href="javascript:delglo(${og.id});" class="btn btn-danger" >删除</a> </td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div> 
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myPass" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">资料修改</h4>
					
				</div>
				
					<form action="" id="editpass">
					<div class="modal-body">
						<div class="panel-body">

							<div id="pawork" class="form-horizontal">
								<div class="form-group">
									
									<div  class="col-lg-11 col-md-12" style="text-align: center;">
										<label class="" for="head_file"><c:if test="${obj.supinfo[0].supplier_icon=='null' || lobj.supinfo[0].supplier_icon==''}">
							
							            			<img id="shop_img"  class="shop_img" src="${pageContext.request.contextPath}/images/defalut.jpg" class="user-avatar">  
							            			</c:if>
							
							            			<c:if test="${obj.supinfo[0].supplier_icon!='null' && obj.supinfo[0].supplier_icon !='' }">
							            				<img id="shop_img"  class="shop_img" src="${obj.supinfo[0].supplier_icon}"></img> 
							            			</c:if></label>
										<input type="file" id="head_file" name="file" accept="image/jpeg" style="position:absolute;clip:rect(0 0 0 0);">
											
													<c:if test="${obj.supinfo[0].supplier_icon=='null' || lobj.supinfo[0].supplier_icon==''}">
													<input type="hidden" name="simg" id="simg" value="${pageContext.request.contextPath}/images/defalut.jpg">
							            			</c:if>
							
							            			<c:if test="${obj.supinfo[0].supplier_icon!='null' && obj.supinfo[0].supplier_icon !='' }">
							            				<input type="hidden" name="simg" id="simg" value="${obj.supinfo[0].supplier_icon}"> 
							            			</c:if>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">密码</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id=""
											name="pass1" placeholder="请填写密码" type="password" value="" style="width: auto;">
											
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">店铺名</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id=""
											name="sname"  type="text" value="${obj.supinfo[0].supplier_name }" style="width: auto;">
											
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">地址</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id=""
											name="des" placeholder="请填写店铺所在地址" type="text" value='<c:if test="${obj.supinfo[0].supplier_des !='null'}">${obj.supinfo[0].supplier_des }</c:if>' style="width: auto;">
											
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">QQ</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id=""
											name="des" placeholder="请填写QQ" type="text" value="<c:if test="${obj.supinfo[0].qq !='null'}">${obj.supinfo[0].qq}</c:if>" style="width: auto;">
											
									</div>
								</div>
								
							</div>
						</div>
				
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="updpass()">提交</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</form>
				</div>
				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->




	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>

	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>

	<script src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js"></script>
	<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/supplierinfo.js"></script>
	
	

</body>
</html>
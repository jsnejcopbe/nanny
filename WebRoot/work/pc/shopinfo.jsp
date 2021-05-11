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

#shop_img {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	border:3px #428BCA solid;
}
</style>

<body>

	<div class="site-holder">
		<!-- 页面主体内容 开始 -->
		<jsp:include page="head.jsp" />
		<!-- 页面主体内容 结束 -->

		<!-- 页面主体 开始 -->
		<div class="box-holder">
			<!-- 左侧菜单 -->
			<jsp:include page="menu.jsp" />

			<div class="content" style="height: 1100px">

				<div class="row">
					<div class="col-mod-12">
						<ul class="breadcrumb">
							<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
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
												<p id="shop_name"></p>
												<span id="hide_id" style="display: none;"></span>
												<p id="shop_address">地址:xxxxx</p>
											  	<button type="button" class="btn btn-info" data-toggle="modal"  data-target="#myPass"> 修改密码</button>
											     <c:if test="${loginUser.status==0 || loginUser.status==null}" var="condition" >
					                             <button type="button"   class="btn btn-danger "  onclick="closeshop()">	店铺歇业</button>
					                             </c:if>
					                             <c:if test="${loginUser.status==1}" var="condition" >
					                             <button type="button"   class="  btn btn-primary   btn-success btn-open"  onclick="openshop()">	店铺营业</button>
				                                 </c:if>
				                             </div>
											<!-- <a id="go_shop" href="javascript:;" class="btn btn-primary btn-lg pull-right">进入门店</a> -->
											<div class="col-md-2">
												<label class="" for="head_file"><img id="shop_img"  src="${pageContext.request.contextPath}/work/pc/images/defalut.jpg"></img></label>
												<input type="file" id="head_file" name="file" accept="image/jpeg" style="position:absolute;clip:rect(0 0 0 0);">
											</div>
										</div>
									</div>

									<div class="panel panel-cascade ">
										<div class="panel panel-default" style="display: none;" id="Areass">
											<div class="panel-heading">修改地址</div>
											<div class="panel-body" id="">
												<div class="col-md-12" id="delivery">
													<!-- <div class="alert alert-info alert-dismissable">
														<p>没有找到您的地址？ 请新增地址</p>
													</div> -->

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
														<!-- <a class="btn btn-info" id="map_add" href="javascript:void(0)">新增地址</a> -->
														<a class="btn btn-primary" id="sava_add" href="javascript:void(0)">确定地址</a>
													</dl>
												</div>
											</div>
										</div>

										<div class="panel panel-primary" id="base_content">
											<div class="panel-heading">基本信息</div>
											<div class="panel-body">
												<div class="panel-body bc">
													<dl class="dl-horizontal">
														<dt>店铺电话</dt>
														<dd id="shop_phone"></dd>
														<dd id="shop_phone1" style="display: none;"></dd>
														<dd id="shop_phone2" style="display: none;"></dd>
														<a href="javascript:edit_tel();" class="btn btn-primary  pull-right" style="margin-right: -15px;margin-top:-25px;">编辑</a>
													</dl>
												</div>
												<div style="display:none;padding: 20px 20px 40px 20px;"  id="shop_tel" >
												
							           		 		 <p><span >电话1： </span><input type="text"  id="tel1" placeholder="请输入电话" maxlength="11" value=""></p>
							           		  		<p><span >电话2： </span><input type="text"  id="tel2"  placeholder="请输入电话"  maxlength="11" value=""></p>
							           	  			<a href="javascript:upd_tel();" class="btn btn-primary  pull-right" >保存</a>
							          			</div>
												<div id="shop_notice" class="panel-body bc">
													<p></p>
													<textarea style="width: 101%; min-height: 80px;" rows="" cols="">暂无公告 现在添加公告？</textarea>
													<a href="javascript:;" id="edit_shop" class="btn btn-primary  pull-right" style="margin-right: -15px;">修改</a>
												</div>

												<div class="panel-body bc">
													<dl class="dl-horizontal">
														<dt>店铺二维码:</dt>
														<dd>
															<div id="two_code" style="width: 120px; height: 160px; float: left; text-align: center;">
																<img width="100%" height="120px" src="#" />
																<a><strong>【点击下载】</strong></a>
															</div>
															<div style="width: 80%; float: left;">
																<!-- <textarea style="margin-left: 10px; width: 100%; min-height: 120px;" rows="" cols="">
																吾问无为谓吾问无为谓呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜
																</textarea> -->
																<p style="padding-left: 10px; word-wrap: break-word; word-break: normal;">该二维码是您店铺的手机网址;</p>
															</div>
														</dd>
													</dl>

												</div>
											</div>
										</div>

										<div class="panel panel-warning" id="open_content">
											<div class="panel-heading">营业时间</div>
											<div class="panel-body" id="times">
												<form>
													<div class="col-md-12 runtime">
														<dl class="dl-horizontal opentime">
															<dt>起始营业时间</dt>
															<dd>
																<input type="hidden" id="time_id" />
																<input class="timepicker myinput" id="timepicker1" placeholder="暂无 请设置" type="text" name="timepicker1" />
															</dd>
														</dl>
														<dl class="dl-horizontal opentime">
															<dt>终止营业时间</dt>
															<dd>
																<input class="timepicker myinput" id="timepicker2" placeholder="暂无 请设置" type="text" name="timepicker1" />
															</dd>
														</dl>
														<a href="javascript:;"  class="btn btn-primary open_edit_shop">修改</a>
														<a href="javascript:;"  class="btn btn-danger pull-right open_remove_shop">删除</a>
													</div>
												</form>
												<div class="col-md-11" style="text-align: center;">
													<a id="plus_time"><i class="fa fa-plus fa-3x"></i></a>
												</div>
											</div>
										</div>
										
										<div class="panel panel-default" id="">
											<div class="panel-heading bg-seagreen" style="color: #fff">起送价格设置</div>
											<div class="panel-body" id="base_price">
												<dl class="dl-horizontal">
														<dt>配送费 </dt>
														<dd>
															<div class="col-md-3">
																<input id="price1" class="form-control form-cascade-control" value="0" placeholder="请设置配送费" type="text">
															</div>
															<a id="price1btn" href="javascript:;" class="btn btn-primary" >保存</a>
														</dd>
												</dl>
												<dl class="dl-horizontal" style="display: none;">
														<dt>起送价 </dt>
														<dd>
															<div class="col-md-3">
																<input id="price2" class="form-control form-cascade-control" value="0" placeholder="请设置配送费" type="text">
															</div>
															<a  id="price2btn" href="javascript:;" class="btn btn-primary" >保存</a>
														</dd>
												</dl>
												<ul class="list-group">
													<c:forEach items="${dispacthList }" var="dl">
													<li class="list-group-item clearfix da-li">
														<div class="col-md-6">
															${dl.addName }
														</div>
														<div class="col-md-6">
															<input class="form-control dptPrice" type="text" placeholder="请输入起送价格" value="${dl.fee }" onkeyup="this.value=this.value.replace(/\D/g,'')" >
														</div>
														<input class="dptID" type="hidden" value="${dl.id }">
													</li>
													</c:forEach>
													<li class="list-group-item" style="text-align: center;">
														<a class="btn btn-info" href="javascript:updateDptPrice()">保存设置</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!--====================================================================================================================-->
								<div class="tab-pane fade " id="profile2">
									<div class="col-md-12 panel panel-cascade">
										<!-- <div class="panel-heading"></div> -->
										<div class="panel panel-primary">
											<div class="panel-heading">
												<a class="btn btn-default" id="go_adress">添加配送地址</a> <a class="btn btn-default" style="display: none;" id="go_back">返回</a>
												<a class="btn btn-default" style="display: none;" id="add_adress">选择配送地址</a>
											</div>
											<div class="panel-body">

												<div id="onequery">
													<form action="${pageContext.request.contextPath}/shop/delivery/scopelist.html" style="margin-bottom: 20px;">
														<div class="input-group">
															<input name="address" class="form-control" id="write-label" placeholder="请输入小区名" type="text">
															<span class="input-group-btn">
																<button class="btn bg-primary text-white" type="submit" id="create-label">Go!</button>
															</span>
														</div>
													</form>
												</div>
												<div id="twoquery" style="display: none;">
													<form action="${pageContext.request.contextPath}/shop/adress/getAdresslist.html" style="margin-bottom: 20px;">
														<div class="input-group">
															<input name="address" class="form-control" id="write-label" placeholder="请输入小区名" type="text">
															<span class="input-group-btn">
																<button class="btn bg-primary text-white" type="submit" id="create-label">Go!</button>
															</span>
														</div>
													</form>
												</div>
												<div id="onetable">
													<table class="table users-table table-condensed table-hover" id="adress_table">
														<thead>
															<tr>
																<th data-hide="id"></th>
																<th data-value="province">省份</th>
																<th data-value="city">城市</th>
																<th data-value="area">区</th>
																<th data-value="detAdd">详细地址</th>
																<th data-value="addName">小区名</th>
																<th data-hide="lon"></th>
																<th data-hide="lat"></th>
																<th data-btns="btns">操作</th>
															</tr>
														</thead>
													</table>
												</div>

												<div id="twotable" style="display: none;">
													<table class="table users-table table-condensed table-hover" id="all_adress_table">
														<thead>
															<tr data-check="true">
																<th data-hide="id"></th>
																<th data-value="province">省份</th>
																<th data-value="city">城市</th>
																<th data-value="area">区</th>
																<th data-value="detAdd">详细地址</th>
																<th data-value="addName">小区名</th>
																<th data-hide="lon"></th>
																<th data-hide="lat"></th>
																<th data-btns="btns">操作</th>
															</tr>
														</thead>
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
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myPass" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">密码修改</h4>
					
				</div>
				
					<form action="" id="editpass">
					<div class="modal-body">
						<div class="panel-body">

							<div id="pawork" class="form-horizontal">
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">密码</label>
									<div class="col-lg-4 col-md-5">
										<input class="form-control form-cascade-control" id=""
											name="pass1" placeholder="请填写密码" type="text" value="" style="width: auto;">
											
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">验证码</label>
									<div class="col-lg-4 col-md-5">
										<input class="form-control form-cascade-control" id=""
											name="mocode" placeholder="验证码" type="text" value="" style="width: auto;">
											<br/>
											<button type="button" class="btn btn-default" id="yzm" onclick="codeChek()"> 获取验证码</button>
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


	<div class="modal fade" id="mapModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<!-- <h4 class="modal-title">Modal title</h4> -->
				</div>
				<div class="modal-body">
					<iframe id="mapIframe" src="/nanny/positionpicker.jsp" style="width: 100%; height: 450px; border: none;"></iframe>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="mapSave" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>

	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.nicescroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/proType.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/timepicki.js"></script>

	<script src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js"></script>
	<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/js/syl/basis.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/work/mobile/js/shopIndex.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/syl/shopinfo.js"></script>
	<script type="text/javascript">
	var wait=60;
	/**
	 * 发验证码
	 */
	function codeChek(){
		if (wait == 0) {  
			$("#yzm").removeClass("disabled");            
			$("#yzm").text("获取验证码");  
	        wait = 60;  
	    } 
		else if(wait == 60){
			$("#yzm").addClass("disabled", true);  
	    	$("#yzm").text("重新发送(" + wait + ")"); 
	    	wait--;
	    	var param={
	    			"sURL":path+"/obtainCode.html",
	    			"fnSuccess":function(rtnData){
	    				layer.msg("请等待,验证码会发送到您的手机");
	    			},
	    			"fnError":function(){
	    				layer.msg('发送失败,请联系管理员');
	    			}
	    		};
	    	new g_fnAjaxUpload(param);
	    	setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000); 
		} 
		else{ 
			$("#yzm").text("重新发送(" + wait + ")"); 
	        wait--;  
	        setTimeout(function() {  
	        	codeChek();  
	        },  
	        1000);  
	    }
	}
	
	
	
	
		function updpass() {
			layer.load(1, {
				shade : [ 0.8, '#333' ]
			});
			var data = $("#editpass").serialize();
			$.ajax({
				type : "post",
				url : path+"editPasswork.html",
				data : data,
				dataType : "text",
				success : function(msg) {
					var data = eval('(' + msg + ')');
					if(data.msg=="0"){
						layer.closeAll("loading");
						
						layer.msg("修改成功");
						$("#editpass").modal("hide");
						document.getElementById("editpass").reset();
					}else{
						layer.closeAll("loading");
						layer.msg("验证码错误");
					}
					//setTimeout(location.reload(true), 3000);
				},
				error : function() {
					layer.msg('操作有误!');
				}
			});
		}
		
		function updateDptPrice(){
			var data=createData();
			layer.load(1, {shade: [0.8,'#333']});
			var param={
				"sURL":path+"shop/updatedispatch.json",
				"Data":"jsonData="+JSON.stringify(data),
				"fnSuccess":function(data){
					layer.closeAll('loading');
					layer.msg(data.msg);
					location.href=path+"shop/shopInfo.html"
				},
				"fnError":function(){layer.closeAll('loading');layer.msg("设置失败");}
			};
			new g_fnAjaxUpload(param);
		}
		
		function createData(){
			var dataArray=new Array();
			$(".da-li").each(function(){
				var obj=new Object();
				var price=$(this).find(".dptPrice").val();
				obj.dptID=$(this).find(".dptID").val();
				obj.dptPrice=price==""?0:price;
				dataArray.push(obj);
			});
			return dataArray;
		}
		
		
	</script>
</body>
</html>
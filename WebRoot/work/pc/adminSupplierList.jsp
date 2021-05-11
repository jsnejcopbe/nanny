<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>商户管理</title>
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
		text-decoration: none;
	}
	
	a:HOVER{text-decoration: none;}
	
	th{
		text-align: center;
	}
	
	tr{
		height: 45px !important;
	}
	
	td{
		text-align: center;
	}
	
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
	.up_file{
	position:absolute!important;
	left: 100px!important;
    top: 50px!important;
    width: 300px!important;
    height: 200px!important;
	/* font-size: 100px!important; */
	z-index:999!important;
	opacity:0!important;
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0)!important;
	cursor: pointer;
	
	}
	.used{color: #D9534F;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.unuse{color: #5BC0DE;font-size: 12px;display: inline-block;text-align: center;margin-right: 3px;}
	.used>i,.unuse>i{font-size: 18px;}
	</style>
</head>

<script type="text/javascript">
	var path = "${pageContext.request.contextPath}";
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
							<li class="active">平台供货商管理</li>
						</ul>
						
						<h3 class="page-header">平台供货商管理</h3>
					</div>
				</div>
				
				<!-- 申请列表 -->
				<div class="row">
					<div class="col-md-12">
						<div class="mail-options-nav clearfix">
							<div class="pull-left" id="shop_query" style="margin-top: -15px;">
								<form action="${pageContext.request.contextPath}/admin/supplier.html?pageIndex=1&pageSize=10" method="post" id="sform">
		                  			<a class="input-group demo-input-group" style="margin-right: 15px;">
						                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="商铺名、手机号" type="text" name="supplierInfo" value="${supplierInfo}">
						                 <input id="hide_type" type="hidden" value="${status}" name="status"/>
						                 <span class="input-group-btn">
						                   <button class="btn btn-info" id="sq_submit" type="submit">查询</button>
						                 </span>
						            </a>
					            </form>
							</div>
							<div class="pull-right" id="state_query">
							<button type="button" class="btn btn-info" data-toggle="modal"  data-target="#myPass"> 添加供应商</button>
					
								<a href="javascript:${pageContext.request.contextPath}/admin/supplier.html" class="btn bg-purple text-white" >全部状态</a>
								<a href="javascript:${pageContext.request.contextPath}/admin/supplier.html?status=0" class="btn btn-info" data-id="0">开启</a>
								<a href="javascript:${pageContext.request.contextPath}/admin/supplier.html?status=1" class="btn btn-success" data-id="1">关闭</a>
							</div>
						</div>
						<div class="panel">
							<div class="panel-body com-pan">
								<table class="table users-table table-condensed table-hover" id="shop_table">
									<thead>
							            <tr >
							             	<th>商标</th>
							               <th>商铺名</th>
							               <th>用户名</th>
							               <th>联系电话</th>
							             <!--   <th>总营业额(含货到付款)</th> -->
							               <th>总订单数</th>
							               <th>创建时间</th>
							             <!--   <th>状态</th> -->
							               <th>操作</th>
							           </tr>
							        </thead>
							        <tbody>
							           <c:forEach items="${baseSupplierInfo }" var="bsi">
							           <tr class="map-con">
							           	   <td class="con-name">
							           	   <img alt="" src="${bsi.supplier_icon }" width="50px">
							           	   
							           	   </td>
							           	   <td class="con-name">${bsi.supplier_name}</td>
							           	   <td class="con-city">${bsi.tel }</td>
							           	   <td class="con-area">${bsi.tel }</td>
							           	  <!--  <td class="con-add"></td> -->
							           	 
							           	   <td class="con-add">${bsi.count }</td>
							           	 
							           	   <td class="con-add">${bsi.createTime }</td>
							           	 <%--   <td class="con-pos">${bsi.status }</td> --%>
							           	   <td>
							           	   <c:if test="${bsi.status==0 }">
							           	   		<a class="used" href="javascript:upstatus(${bsi.id},1)">
							           	   			<i class="iconfont-gl">&#xe62d;</i><br>
							           	   			关店
							           	   		</a>           	   		
							           	   </c:if>
							           	   <c:if test="${bsi.status==1 }">
							           	   		<a class="unuse" href="javascript:upstatus(${bsi.id},0)">
							           	   			<i class="iconfont-gl">&#xe62d;</i><br>
							           	   			开店
							           	   		</a>
							           	   </c:if>
							           
						
							           	    <a class="used" href="javascript:opp(${bsi.id})">
							           	   		<i class="iconfont-gl">&#xe60a;</i><br>
							           	   		详情
							           	   </a>
							           	   
							          
							           	  <%--  <a class="used" href="${pageContext.request.contextPath}/admin/order-1-${bu.id}.html"> --%>
							           	   <a class="used" href="${pageContext.request.contextPath}/admin/supplierOrder.html?supplierId=${bsi.id}">
							           	  		<i class="iconfont-gl">&#xe615;</i><br>
							           	  		 订单
							           	   </a>
						
							           	   </td>
							           	   
							           </tr>
							          <!--详情st -->  <tr style="display:none;"  id="sname_${bsi.id }">
							           		<td style="text-align: left;padding: 15px;width: 500px;" >
							           		<div>
							           		  <p><span >店标： </span>
							           		  <img alt="" src="${bsi.supplier_icon }" id="img_${bsi.id }"  width="300px" style="margin: auto;display: block;">
							           		 
							           		  <input class="up_file" type="file" id="file1_${bsi.id }" name="file" accept="image/*" > 
							           		  
							           		  </p>
							           		  		<input type="hidden" id="fi_${bsi.id }" value="" > 
							           		  
							           		  </div>
							           		  <div class="clearfix">
							           		  <div class="pull-left"><p><span >商铺：<input type="text" value="${bsi.supplier_name }"  id="shname_${bsi.id }" style="color: black; border: none;"></span></p>
							           		  
							           		  		<p><span>电话：<input type="text" value="${bsi.tel }"  id="shmemo_${bsi.id }" style="color: black;border: none; width: 200px;" maxlength="23"></span></p>
							           		  		<p><span style="display: block;float: left;">公告：</span>
							           		  		<textarea  id="shcon_${bsi.id }" style="color: black;">${bsi.memo }</textarea></p>
						
									           	 	  <p>地址：${bsi.supplier_des}</p>
									          </div>
							           		 
							           	  	</div>
							           		   <div style=" text-align: center;"> 
							           		  		 <a href="javascript:updetails(${bsi.id})" class="btn btn-info" >保存</a>
							           		   </div>
							           	  </td>
							           </tr> <!--详情en --> 
							           
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
					
	<!-- 添加模板 -->
	<div class="modal fade" id="myPass" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 500px;">
			<div class="modal-content" style="height: auto;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加供应商</h4>
					
				</div>
				
					<form action="" id="userform">
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
										class="col-lg-3 col-md-4 control-label">商铺名</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="username"
											name="username"  type="text" placeholder="请填写店铺名" style="width: auto;">
											
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">手机号</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="tel1"
											name="tel1"  type="text" placeholder="请填写手机号" style="width: auto;">
											
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">地址</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="des"
											name="des" placeholder="请填写店铺所在地址" type="text" value='<c:if test="${obj.supinfo[0].supplier_des !='null'}">${obj.supinfo[0].supplier_des }</c:if>' style="width: auto;">
											
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">密码</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="pass1"
											name="pass1" placeholder="请填写密码" type="password" value="" style="width: auto;">
											
									</div>
								</div>
								
								
								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-3 col-md-4 control-label">确认密码</label>
									<div class="col-lg-5 col-md-6">
										<input class="form-control form-cascade-control" id="pass2"
											name="pass2" placeholder="请填写密码" type="password" value="" style="width: auto;">
											
									</div>
								</div>
								
							</div>
						</div>
				
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="upFrom()">提交</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</form>
				</div>
				
			</div>

	<!-- 加载js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/business.js?v=0.01"></script>
    <script type="text/javascript" src="/nanny/work/pc/js/login.js"></script>

	
	<script type="text/javascript">
	

new Pagin({
		size :${baseSupplierPage.size},
    	perPage :5, 
    	total  :${baseSupplierPage.total},
    	nowPage:${baseSupplierPage.nowpage},
	    dealFun:function(size,nowPage){
	    $("#sform").attr("action","${pageContext.request.contextPath}/admin/supplier.html?pageIndex="+nowPage+"&pageSize="+size);
	    $("#sform").submit();
	}
});

	</script>
	<script type="text/javascript">
	
	 function upFrom() {
	if(validfn([ "isNotNull" ],"#username","请输入用户名")==false){
		return ;
	}
	else if (validfn([ "isNotNull", "isTel" ], "#tel1","手机格式有误，请重新输入") == false) {
		return;
	} 
	else if(validfn([ "isNotNull" ],"#des","请输入地址")==false){
		return ;
	}
	else if (validfn([ "isNotNull" ], "#pass1","密码格式有误，请重新输入") == false) {
		return;
	}else if (validfn([ "isNotNull" ], "#pass2","密码格式有误，请重新输入") == false) {
		
		return;
	}
	else if(userform.pass1.value!=userform.pass2.value){
	layer.msg("密码不一致，请重新输入");
	return ;
	}
	else {
	layer.load(1, {shade : [ 0.8, '#333' ]});
	var data = $("#userform").serialize();
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/admin/supplierAdd.json",
		data : data,
		dataType : "text",
		success : function(msg) {
			var data = eval('(' + msg + ')');
			
				layer.msg(data.msg);
				if(data.hasOwnProperty("success")){
					setTimeout("location.reload(true)", 1000);
				}
				layer.closeAll('loading');
			
		},
		error : function() {
			setTimeout("location.reload(true)", 3000);
			layer.msg(data.msg);
		}
	});
	}
} 

	
	 function upstatus(supplierId,status){
	
	var param={
			"sURL":path+"/admin/supplierConfirm.json",
			"Data":"supplierId="+supplierId+"&status="+status,
			"fnSuccess":function(data){
				layer.closeAll('loading');
				layer.msg(data.msg);
				
				setTimeout("location.reload(true)", 1000);
			},
			"fnError":function(){layer.closeAll('loading');layer.msg1("操作有误");}
		};
		new g_fnAjaxUpload(param);
	
	} 
	
	function updetails(id) {

	var shcon=$("#shcon_"+id).val();

		
	$.ajax({
		 type: "POST",
		 url: path+"/admin/supplierUpdate.html",
		 data: "supplierId="+id+"&shopcon="+shcon,
		 dataType: "json",
		 success: function(data){
				 layer.msg("操作成功");
				location.reload(true);
				 },
		 error: function(){layer.msg("操作失败");}
		});
}
	
	</script>
</body>
</html>

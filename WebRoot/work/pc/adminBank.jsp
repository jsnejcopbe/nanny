<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>转账银行管理</title>

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
<style type="text/css">
.up_file{
	position:absolute!important;
	left:0!important;
	top:0!important;
	width:90%!important;
	height:100%!important;
	/* font-size: 100px!important; */
	z-index:999!important;
	opacity:0!important;
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0)!important;
	cursor: pointer;
	
}
.up_btn{
	position: relative;
	display:block;
	width: 90%;
	margin-bottom: 10px;
	border-radius: 50%;
}

</style>	


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
							<li class="active">系统数据设置</li>
						</ul>

						<h3 class="page-header">提现银行管理</h3>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-archon panel-todo">
							<div class="panel-heading clearfix" style="margin-top: -20px;">
							
									<div class="panel-title " id="bank_name">
									<form action="${pageContext.request.contextPath}/admin/adminbank.html?pageIndex=1&pageSize=${page.pageSize}" method="post" id="uForm">
									<div class="pull-left" >
                 						<input type="text" class=" form-cascade-control" style="font: initial;" placeholder="请输入银行名称" id="bname" name="bname" value="${baname}">
                 						<input id="hide_type" type="hidden" value="${statu}" name="state"/>
                 						
									<button class="btn btn-info" id="sq_submit" type="submit">查询</button>
									</div>
									</form>
									
                				 <div class="pull-right" id="bank_sta">
                 					 <%--  <a href="javascript:void(0)" style="padding: 3px 10px ! important;"
										class="btn btn-warning " data-id="-1">全部
									</a> 
									  <a href="javascript:void(0)" style="padding: 3px 10px ! important;"
										class="btn btn-primary" data-id="0">开启
									</a> 
									<a href="javascript:void(0)" style="padding: 3px 10px ! important;"
										class="btn btn-danger " data-id="1">关闭
									</a> --%>
									<button 
											style="padding: 3px 10px ! important;"
											class="btn btn-success " onclick="editbank()">新增 </button>
									</div>
              							</div>
							</div>
							<div class="panel-body">
								<table class="todo-table table table-bordered">
									<tbody>
										<tr class="header-row">
											<th>
											</th>
											<th>银行名称</th>
											<th>状态</th>
											<th>操作</th>

										</tr>
										<c:forEach items="${bank}" var="bank">
										<tr>
											<td><img alt="" id="img_${bank.id}" src="${bank.icon}" width="60px;" height="50px;" class="img-circle">
											</td>
											<td><span class="description" id="name_${bank.id}">${bank.bankName}</span>
											</td>
											<td><span class="time badge bg-warning" id="sta_${bank.id}">${bank.stats}</span>
											</td>
											<td>
											<a class="btn btn-info btn-xs btn-delete" href="javascript:editbank(${bank.id},${bank.isUse});">
											<i class="fa fa-pencil-square-o"> 编辑</i></a>
											<%--<c:if test="${bank.isUse==0}">
											<a class="btn btn-danger btn-xs btn-delete"><i class="fa fa-globe"> 关闭</i>
											</a>
											</c:if>
											<c:if test="${bank.isUse==1}">
											<a class="btn btn-primary btn-xs btn-delete"><i class="fa fa-globe"> 开启</i>
											</a>
											</c:if>
											--%></td>
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
					<!-- / col-md-12 -->
				</div>
			</div>
		</div>
		<!-- 页面主体内容 结束 -->
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提现银行操作</h4>
				</div>
				<form action="" id="editba">
				<div class="modal-body">
						<div class="panel-body">

							<div id="sb" class="form-horizontal">
								<div class="form-group">
									<div class="col-lg-4 col-md-5"></div>
									<div class="col-lg-4 col-md-5">
										<a class="btn  up_btn" href="javascript:void(0)"> <img
											id="preshow" src="images/defalut.jpg"
											style="display: block;width:100px;height: 90px; "
											class="img-circle" name="img">
											<div id="imgPreview" style="width:100px;"></div> <input
											type="hidden" name="fi" id="fi"
											value="/nanny/images/defalut.jpg"> <input
											class="up_file" type="file" id="upfile" name="file"
											accept="image/*" > </a>
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">银行名称</label>
									<div class="col-lg-10 col-md-9">
										<input class="form-control form-cascade-control" id=""
											name="bmname" placeholder="请填写银行名称" type="text" value="">
											<input type="hidden" name="bid" value="">
									</div>
								</div>

								<div class="form-group">
									<label for="inputEmail1"
										class="col-lg-2 col-md-3 control-label">银行状态</label>
									<div class="col-lg-10 col-md-9">
										<select class="form-control" id="bt" name="bt">

											<option value="0">开启</option>
											<option value="1">关闭</option>

										</select>
									</div>

								</div>
							</div>
						</div>
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="updbanks()">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>

	<!-- 加载js -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
	<script src="${pageContext.request.contextPath}/js/base.js"></script>
	<script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script
		src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript">
	$(function(){
        $("body").on("change","#upfile",function(){
             
         var oParam={
                    "sURL":"/nanny/upload/images.json",
                    "sID":"upfile",
                    "contentType":"",
                    "fnSuccess":function(data){
                    $("#fi").val(data.path);
                    document.getElementById("preshow").src = data.path;	
                     alert("上传成功"); 
                    $(".fileinput-remove-button").click();
                    },
                    "fnError":function(){                   
                     alert("上传失败");
                     }
                 };
                     new g_fnFileUpload(oParam);
                        });
                       
         });
         
    
	
	
	
	

new Pagin({
	size :${page.pageSize},
	perPage :5, 
	total  :${page.totalCount},
	nowPage:${page.pageIndex},
    dealFun:function(size,page){
    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/adminbank.html?pageIndex="+page+"&pageSize="+size);
    $("#uForm").submit();
}
});

$("body").on("click","#bank_sta a",function(){
var id = $(this).attr("data-id");
$("#hide_type").val(id);
$("#bank_name").find("form").submit();
})



function editbank(id,isuser) {
		if(id!=null){
		var name= document.getElementById("name_"+id).textContent;
		var img= document.getElementById("img_"+id).src;
		
		$("input[name='bid']").val(id);
		 $("input[name='bmname']").val(name); 
		 $("input[name='fi']").val(img);
		 document.getElementById("preshow").src =img ;
		 $("#bt").val(isuser);
		 	}else{
		 		brand=null;
		 		$("input[name='bid']").val(null); 
		 		$("input[name='bmname']").val(null); 
				 $("input[name='fi']").val("/nanny/images/defalut.jpg");
				  document.getElementById("preshow").src ="/nanny/work/pc/images/defalut.jpg" ;	
		 	}
		 $("#myModal").modal();
		
}

function updbanks() {
	 var brand=$("input[name='bid']").val();
	var data=$("#editba").serialize();
	if(brand==""){
		var url="/nanny/admin/addbanks.html";
	}else{
		var url="/nanny/admin/updbanks.html";
	}
		$.ajax({
             type: "POST",
             url: url,
             data: data,
             dataType: "json",
             success: function(data){
            		 layer.msg("成功提交");
            		 location.reload(true);
            }
         });
	}

new g_fnImgCheck();
</script>

</body>
</html>

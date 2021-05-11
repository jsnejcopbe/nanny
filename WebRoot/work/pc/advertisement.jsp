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
<title>轮播广告位设置</title>

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
							<li class="active">广告设置</li>
						</ul>

						<h3 class="page-header">广告查看</h3>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-archon panel-todo">
							<div class="panel-heading clearfix" style="margin-top: -20px;">
							
									<div class="panel-title " id="bank_name">
									<form action="${pageContext.request.contextPath}/admin/adverits.html" method="post" id="uForm">
									<div class="pull-left" >
                 						<input type="text" class=" form-cascade-control" style="font: initial;" placeholder="请输入店铺名称" id="bname" name="bname" value="${bname}">
<!--                  						<input id="hide_type" type="hidden" value="${statu}" name="state"/> -->
                 						
									<button class="btn btn-info" id="sq_submit" type="submit">查询</button>
									</div>
									</form>
									
                				 <div class="pull-right" id="bank_sta">
                 					
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
											<th>编号
											</th>
											<th>图片</th>
											<th>跳转地址</th>
											<th>使用商家</th>
                                             <th>全局应用</th>
                                             <th>启用</th>
                                             <th>操作</th>
										</tr>
										
										
									<c:forEach items="${wheel}" var="w" varStatus="s">
									<tr>
										  <td style="width:50px;><span class="time badge bg-warning" "><c:out value="${s.index+1}" /></span>
											</td>
											<td  style="width:200px; height: 100px;">
										       <img alt="" src="${w.imgStc}" width="140px">
										        </td>
										
											<td ><span class="time badge bg-warning" ">${w.jumpSrc}</span></td>
											
											<c:if test="${w.isAllUser=='0'}">
											<td style="width:90px;><span class="time badge bg-warning" ">全部</span></td>
											</c:if>
											<c:if test="${w.isAllUser=='1'}">
											<td style="width:90px;><span class="time badge bg-warning" ">${w.shop_name}</span></td>
											</c:if>
											
											  <c:if test="${w.isAllUser=='0'}">
											<td style="width:90px;><span class="time badge bg-warning" ">是</span></td>
											</c:if>
											  <c:if test="${w.isAllUser=='1'}">
											<td style="width:90px;><span class="time badge bg-warning" ">否</span></td>
											</c:if>
											
											
											
											  <c:if test="${w.isUser=='0'}">
											<td style="width:90px;><span class="time badge bg-warning" ">是</span></td>
											  </c:if>
										    <c:if test="${w.isUser=='1'}">
											<td style="width:90px;><span class="time badge bg-warning" ">否</span></td>
											  </c:if>
											<td style="width:190px; ">
											<a class="btn btn-info btn-xs btn-delete" href="${pageContext.request.contextPath}/admin/adminadveredit.html?id=${w.id}"><i class="fa fa-pencil-square-o"> 编辑</i></a>
											<c:if test="${w.isUser=='0'}">
											<a class="btn btn-info btn-xs btn-delete" href="javascript:Disable(${w.id});" ><i class="fa fa-pencil-square-o"> 停用</i></a>
											</c:if>
											<c:if test="${w.isUser=='1'}">
											<a class="btn btn-info btn-xs btn-delete" href="javascript:Enable(${w.id});" ><i class="fa fa-pencil-square-o"> 启用</i></a>
											</c:if>
											<a class="btn btn-info btn-xs btn-delete" href="javascript:Delete(${w.id});"><i class="fa fa-pencil-square-o"> 删除</i></a>
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
					<h4 class="modal-title" id="myModalLabel">广告添加</h4>
				</div>
				<form action="" id="editba">
				<div class="modal-body">
						<div class="panel-body">

							<div id="sb" class="form-horizontal">
								<div class="form-group">
									<div class="col-lg-4 col-md-5"></div>
									<div class="col-lg-4 col-md-5">
										<a class="btn  up_btn" href="javascript:void(0)"> 
										<img id="preshow" src="images/defalut.jpg" style="display: block;width:100px;height: 90px; "  name="img">
											<div id="imgPreview" style="width:100px;"></div> 
											<input type="hidden" name="fi" id="fi" value="/nanny/images/defalut.jpg"> 
											<input class="up_file" type="file" id="upfile" name="file" accept="image/*" > 
											</a>
									</div>
								</div>
                                   <div class="form-group">
									<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">跳转地址</label>
									<div class="col-lg-10 col-md-9">
									<input type="text" class="form-control form-cascade-control" placeholder="跳转地址" name="jumpSrc" id="jumpSrc" value="">
										         			</div>
										         		</div>
									<div class="form-group ">
										<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">全局</label>
										<div class="col-lg-10 col-md-9">
                                        <input name="isUse" type="radio" value="0"  class="flat-input" id="qj" checked>&nbsp;是
										 <input name="isUse" type="radio" value="1" class="flat-input" id="qj2" >&nbsp;否
										         				
										         			</div>
										         		</div>
<!-- 										  <div class="form-group isShow" style="display:none;"> -->
<!-- 							 <label for="inputEmail1" class="col-lg-2 col-md-3 control-label">使用者</label> -->
<!-- 									<div class="col-lg-6 col-md-7"> -->
<!-- 										<select class="form-control" id="bank" name="bank"> -->
<!-- 										 <c:forEach items="${baseshop}" var="b"> -->
<!-- 											<option value="${b.id}">${b.shop_name}</option> -->
<!-- 											</c:forEach> -->
<!-- 											 </select> -->
<!-- 									       </div> -->
<!-- 									      </div> -->
									      
									      <div class="form-group" style="display:none;">
									      <div class="col-lg-6 col-md-7">
										<select class="form-control" id="bank" name="bank">
										 <c:forEach items="${baseshop}" var="b">
											<option value="${b.id}">${b.shop_name}</option>
											</c:forEach>
											 </select>
									       </div>
									      </div>
			
			                             <div class="form-group isShow">
			                             <input type="text" value="" id="siy" name="siy">
			                             </div>
			
										   	
										   <div class="form-group">
										<label for="inputEmail1" class="col-lg-2 col-md-3 control-label">是否启用</label>
										<div class="col-lg-10 col-md-9">
										 <input name="isCom" type="radio" value="0" class="flat-input" checked>&nbsp;是
										  <input name="isCom" type="radio" value="1" class="flat-input" >&nbsp;否
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
	<script >var BASEPATH="${pageContext.request.contextPath}"</script>
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
	<script src="${pageContext.request.contextPath}/work/pc/js/adminadverits.js"></script>
	<script type="text/javascript">	
	
new Pagin({
	size :${pageSize},
	perPage :5, 
	total  :${con},
	nowPage:${pageIndex},
    dealFun:function(size,page){
    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
    $("#uForm").attr("action","${pageContext.request.contextPath}/admin/adverits.html?pageIndex="+page+"&pageSize="+size);
    $("#uForm").submit();
}
});

</script>

</body>
</html>

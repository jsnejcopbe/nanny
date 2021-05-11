<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>店铺留言</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Loading Bootstrap -->
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<!-- Loading Stylesheets -->    
	<link href="${pageContext.request.contextPath}/work/pc/css/font-awesome.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css"> 

	
  </head>
  <body>
  	<div class="site-holder">
  		<!-- this is a dummy text -->
  		<!-- .navbar -->
  		

  			<jsp:include page="head.jsp" />
  			

  			<jsp:include page="menu.jsp" />
  				

  				<!-- .box-holder -->
  				<div class="box-holder">

  					<!-- .left-sidebar -->
  					
  					<!-- .content -->
  					<div class="content">
					

  						<div class="row">
  							<div class="col-mod-12">

  								<ul class="breadcrumb">
  									<li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
  									<li class="active">站内信</li>
  								</ul>

  								<%--<div class="form-group hiddn-minibar pull-right">
  									<input type="text" class="form-control form-cascade-control nav-input-search" size="20" placeholder="Search through site" />

  									<span class="input-icon fui-search"></span>
  								</div>--%>
  								
  								<h3 class="page-header"><i class="fa fa-envelope"></i> 店铺留言 </h3>

  							</div>
  						</div>

  						<!-- Mail Panel -->
  						<div class="row inbox">
  							
  							
  							<!-- Mail Right Sidebar -->
  							<div class="col-md-12  mail-right-box">
  								<div class="mail-options-nav">


  									<div class="btn-group mail-options" id="shop_query">
  										<%--<a href="" class="btn btn-success"><i class="fa fa-archive" ></i> Archive</a>
  										<a href="" class="btn btn-warning"><i class="fa fa-ban" ></i> Spam</a>
  										<a href="" class="btn btn-danger"><i class="fa fa-trash-o" ></i> Delete</a>
  										--%>
  										<form action="${pageContext.request.contextPath}/shop/shopMessage.html?pageIndex=1&pageSize=${page.pageSize}" method="post" id="uForm">
											 <input id="hide_type" type="hidden" value="${state}" name="state"/>
  											<a href="javascript:void(0)" class="btn btn-info text-white" data-id="-1" >全部</a>
											<a href="javascript:void(0)" style="margin-left: 10px;" class="btn bg-purple text-white" data-id="0">未读</a>
											<a href="javascript:void(0)" style="margin-left: 10px;" class="btn btn-success" data-id="1">已读</a>
											</form>
  									</div>

							

							      <div class="mail-pagination pull-right">
  										<%--<label class="label text-primary">1-60 ${page.pageIndex} of ${page.totalCount}</label>
  										
  										<a href="#" class="btn btn-default"><i class="fa fa-angle-double-left"></i></a>
  										<a href="#" class="btn btn-default"><i class="fa fa-angle-double-right"></i></a>
  										<a href="#" class="btn btn-info"><i class="fa fa-cogs"></i></a>
  										--%>
  										
  									</div>
  								</div>
  								<div class="mails">
  									<table class="table table-hover table-condensed">
  										<c:forEach items="${mesg}" var="mesg">
  										<tr>
  											<td class="subject" id="nm_${mesg.id}"> 
  											<c:if test="${loginUser.userID==mesg.userID}">${mesg.oname}</c:if>
  											
											<c:if test="${loginUser.userID==mesg.otherSide}">
											<c:if test="${mesg.userID=='0'}">系统</c:if>
											<c:if test="${mesg.userID!='0'}">${mesg.uname}</c:if>
											</c:if>
  											</td>
  											
  											<td class="body" >
  												<c:if test="${ mesg.memo=='null'}">
  													<c:set var="testStr" value="${mesg.mailCon}"/>
   														 <c:choose>  
        														<c:when test="${fn:length(testStr) > 120}">  
           															 <c:out value="${fn:substring(testStr, 0,120)}....." />  
       		 													</c:when>  
      													 		<c:otherwise>  
          															<c:out value="${testStr}"/>  
        														</c:otherwise>  
    													 </c:choose>  
  												</c:if>
  													 <c:if test="${ mesg.memo!='null'}">
  															 <p style="color: red;">${mesg.memo}</p>
    													</c:if>
  													</td>
  													<td style="display: none;" id="con_${mesg.id}">${mesg.mailCon}</td>
  													<td style="display: none;" id="uid_${mesg.id}">${mesg.userID }</td>
  													<td style="display: none;" id="me_${mesg.id}">${mesg.memo}</td>
  													
  											<td>
  											
  												<c:if test="${mesg.isRead==0}">
  													<label id="lab" class="label bg-purple" style="margin-right: 10px;">${mesg.stats}</label>
  												</c:if>
  												<c:if test="${mesg.isRead==1}">
  													<label id="lab" class="label btn-success" style="margin-right: 10px;">${mesg.stats}</label>
  												</c:if>
  											</td>
  											<td class="time" id="ct_${mesg.id}">${mesg.createTime}</td>
  											<td><a class="label btn-primary" data-toggle="modal"  data-target="#editModal"  onclick="viewre(${mesg.id})"  >查看</a></td>
  										</tr>
  										</c:forEach>	
  										</table>
										<div class="pagin-area" style="text-align: center;">
											<div class="paging_bootstrap pagination js-pagin" style="margin: 0px;"></div>
										</div>
  									</div>	
  								</div><!-- /Right Side mail bar -->
  								
  								
  								<!-- Modal -->
  						
  								<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  									<div class="modal-dialog">
  										<div class="modal-content">
  											<div class="modal-header bg-primary text-white">
  												<button type="button" class="close text-white" data-dismiss="modal" aria-hidden="true">&times;</button>
  												<h4 class="modal-title">站内信</h4>
  											</div>
  											<div class="modal-body">
  												<div class="icon-show">
  												 	<div  id="nm" style="color: #31338F;padding-bottom: 5px;font-style: oblique;font-size: 18px;font-weight: bolder"> </div>
  												 	
  												 	<div id="tet"> </div>
  												</div>
  											</div>
  											
  											<div class="modal-footer" id="tea">
  											    <input type="hidden" id="uid" value="">
  												<textarea class="form-control" rows="4" id="con"></textarea>
  												<br />
  												<a type="button" class="btn bg-primary text-white" href="javascript:reply();">回复 </a>
  											</div>
  										</div><!-- /.modal-content -->
  									</div><!-- /.modal-dialog -->
  								</div><!-- /.modal -->

  							</div>

  						</div> <!-- /.content -->

  					

</div> <!-- /.box-holder -->

</div>
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.imagesloaded.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
<script type="text/javascript">

new Pagin({
		size :${page.pageSize},
    	perPage :5, 
    	total  :${page.totalCount},
    	nowPage:${page.pageIndex},
	    dealFun:function(size,page){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#uForm").attr("action","${pageContext.request.contextPath}/shop/shopMessage.html?pageIndex="+page+"&pageSize="+size);
	    $("#uForm").submit();
	}
});

$("body").on("click","#shop_query a",function(){
	var id = $(this).attr("data-id");
	$("#hide_type").val(id);
	$("#shop_query").find("form").submit();
})

function viewre(id) {
	var nm=$("#nm_"+id).text();
	var ct=$("#ct_"+id).text();
	 var con=$("#con_"+id).text();
	 var uid=$("#uid_"+id).text();
	var me=$("#me_"+id).text();
	 	if(uid=="0"){
	 		document.getElementById("tea").style.display="none";
	 	}else{
	 		document.getElementById("tea").style.display="";
	 	}
	 	$("#nm").text(nm);
		$("#tet").text(con);
		$("#uid").val(uid);
		
		var upparam={
			"sURL":"/nanny/shop/updshopMessage.html",
			"Data":"id="+id,
			"fnSuccess":function(data){
				
			},
			"fnError"  :function(){
				layer.closeAll("loading");
				layer.msg("查看失败,请联系管理员");
			}
		};
		new g_fnAjaxUpload(upparam);
	}


function reply() {
	 var con=$("#con").val();
	 var uid=$("#uid").val();
	 if(con!=null&&con!=""){
	var upparam={
			"sURL":"/nanny/shop/saveMessage.html",
			"Data":"con="+con+"&uid="+uid,
			"fnSuccess":function(data){
				layer.closeAll("loading");
				layer.msg(data.msg);
				closeModal();
				location.reload(true);
			},
			"fnError"  :function(){
				layer.closeAll("loading");
				layer.msg("回复失败,请联系管理员");
			}
		};
		new g_fnAjaxUpload(upparam);
	}else{
		layer.msg("请填回复");}

}


function closeModal(){
	$("#editModal").modal("hide");
	clear();
}
</script>

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>欢迎登录掌上保姆管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

     
	  
	  <!-- 样式加载 -->
	  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/dataTables.bootstrap.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
	  <link href="${pageContext.request.contextPath}/work/pc/css/style.css" rel="stylesheet" type="text/css">
	  <link href="${pageContext.request.contextPath}/work/pc/css/brand.css" rel="stylesheet">
	  <!-- 兼容性 -->
	  <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
	  <!--[if lt IE 9]>
	  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
	  <![endif]-->
</head>

<body >
    <div class="site-holder">
        <!-- 页面主体内容 开始 -->
        <!-- 头部内容 -->
       		<jsp:include page="head.jsp"/>
        <!-- 头部 结束 -->

        <!-- 页面主体 开始 -->
        <div class="box-holder">

            <!-- 左侧菜单 -->
            	<jsp:include page="menu.jsp"/>
            <!-- 左侧菜单 结束-->

            <!-- 右侧内容页 -->
            <div class="content">
            	<!-- 复制内容 开始 -->
	   			  <div class="row">
		                 <div class="col-mod-12">
		
			                  <ul class="breadcrumb">
			                   <li><a href="${pageContext.request.contextPath}/index.html">首页</a></li>
			                   <li class="active">平台代理品牌</li>
			                 </ul>
			                 
			                 <div class="form-group hiddn-minibar pull-right" style="margin-top: -20px;">
			                	<form action="probrand.html?pageIndex=1&pageSize=${page.pageSize}" method='post' id="nform">
			                 	  <a class="input-group demo-input-group" style="margin-right: 15px;">
					                 <input class="form-control nav-input-search pull-right" style="margin:0px;" size="20" placeholder="请输入品牌名称" type="text" name="name" value="${name}">
					                 <span class="input-group-btn">
					                  <button class="btn btn-info" type="submit">查询</button>
					                </span>
					              </a>
					            </form>
			                  <!-- <input name="name" type="text" class="form-control form-cascade-control nav-input-search" size="20" placeholder="请输入品牌名称" />
			
			                  <span class="input-icon fui-search"></span> -->
			                </div>
			
			                <h3 class="page-header"><i class="fa fa-pagelines"></i> 代理品牌</h3>
		
		           	   </div>
		          </div>

		          <div class="row">
		          		<div class="col-md-12">
		           			<div class="panel panel-cascade">
		       			   		 <div class="panel-body gallery">
		          					<div class="controls">
							           <ul class="list-inline pull-left ">
								            <li class="filter" data-filter="mix"><a class="btn btn-primary" href="javascript:editbr()" ><i class="fa fa-plus"></i> 添加新品牌</a></li>
								            <li class="filter" data-filter="cats"><a class="btn btn-danger" href="#"><i class="fa fa-trash-o"></i> 批量删除</a></li>
							           </ul>
		        				   </div>
		        				   <!-- 品牌列表 -->
		       					   <div class="row">
		        				  	   <ul class="list-inline gallery-items" id="Grid">
								         <c:forEach items="${Probrand}" var="pb">
								          <li class="mix dogs " data-name="puffy">
								           	<div class="panel panel-cascade panel-gallery " id="edit">
								            	<div class="panel-heading" id="modal_${pb.id}">
								             	<h2 class="panel-title" id="name_${pb.id}">${pb.name}</h2>
								           		</div>
								           		<div class="panel-body nopadding">
								            		<img src="${pb.icon}" alt=""  id="img_${pb.id}" height="143px"> 
								            	</div>
								            	<div class="panel-footer btn_h">
								             		<h3>
								              			<a href="javascript:editbr(${pb.id})" class="btn btn-info text-white" ><i class="fa fa-edit"></i> 编辑品牌 </a> 
								              			<a href="javascript:void(0)" class="btn btn-danger text-white"><i class="fa fa-trash-o"></i> 删除品牌</a>
								            		</h3>
								            	</div>
								        	</div>
								      	  </li>
								      	  
								      	   </c:forEach>
										</ul>
									</div>
									<!-- 分页区 -->
								<div class="pagin-area ">
									<!-- <a class="btn btn-block btn-info text-white">加载更多.....</a> -->
									<div class="dataTables_paginate paging_bootstrap pagination js-pagin"></div>
								</div>
								</div> 
							</div>
							
						</div>
					</div>
					
					<!-- 模态框 -->
					<div class="modal fade edit-modal" id="brandEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" 
						               data-dismiss="modal" aria-hidden="true">
						                  &times;
						            </button>
						            <h4 class="modal-title" id="myModalLabel">品牌编辑</h4>
								</div>
								<form action="" method='post' id="editb">
								<div class="modal-body" id="upfile"   style="text-align: -moz-center;">
								
						            <img id="preshow" src="images/defalut.jpg" style="display: block;width:300px; " name="img">
						             <div id="imgPreview" style="width:100px;"></div>
									 <input type="hidden" name="fi" id="fi" value="${pageContext.request.contextPath}/images/defalut.jpg">
						            <a class="btn bg-primary up_btn" href="javascript:void(0)">
						             <input class="up_file" type="file" id="file1" name="file" accept="image/*" onchange='PreviewImage(this)'>
						           	 上传新商标
						           	 
						           	</a>
						            <input type="text" class="form-control" placeholder="请输入品牌名称" value=""  name="bname">
						         	<input name='id' type='hidden' id="brid" value="">
						         </div>
						         <div class="modal-footer">
						         	<button type="button" class="btn btn-default" 
              						 data-dismiss="modal">关闭	
              						 </button>
              						 <button type="button" class="btn btn-primary" onclick="upd()">
               							提交更改
           							 </button>
						         </div>
						         </form>
							</div>
						</div>
					</div>
					<!-- 复制内容 结束 -->
                <!-- / Users widget-->


                <div class="footer">
                    © 2015 <a href="javascript:void(0)">卓安软件科技有限公司</a>
                </div>

            </div>
            <!-- 右侧内容 结束 -->
            
            <div class="right-sidebar right-sidebar-hidden">
                <div class="right-sidebar-holder">

                    <!-- @Demo part only The part from here can be removed till end of the @demo  -->
                    <a href="screens.html" class="btn btn-danger btn-block">Logout </a>


                    <h4 class="page-header text-primary text-center">
  								Theme Options
  								<a  href="#"  class="theme-panel-close text-primary pull-right"><strong><i class="fa fa-times"></i></strong></a>
  							</h4>

                    <ul class="list-group theme-options">
                        <li class="list-group-item" href="#">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="fixedNavbar" value="">Fixed Top Navbar
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="fixedNavbarBottom" value="">Fixed Bottom Navbar
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="boxed" value="">Boxed Version
                                </label>
                            </div>

                            <div class="form-group backgroundImage hidden">
                                <label class="control-label">Paste Image Url and then hit enter</label>
                                <input type="text" class="form-control" id="backgroundImageUrl" />
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- /.right-sidebar-holder -->
            </div>
        </div>
    </div>
    <!-- 主页内容 结束 -->



    <!-- 加载js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/adutio.js"></script>
    
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.mixitup.min.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/lightbox-2.6.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/work/pc/js/gallery-custom.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/jquery.accordion.js"></script>
	<script src="${pageContext.request.contextPath}/work/pc/js/core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/work/pc/projs/probrand.js"></script>
<script type="text/javascript">

new Pagin({
	    size :${page.pageSize},
	    perPage :5, 
	    total  :${page.totalCount},
	    nowPage:${page.pageIndex},
	    dealFun:function(size,page){
	    /* location.href="userlist.do?pageIndex="+page+"&pageSize="+size; */
	    $("#nform").attr("action","probrand.html?pageIndex="+page+"&pageSize="+size);
	    $("#nform").submit();
	}
});
</script>
</body>

</html>
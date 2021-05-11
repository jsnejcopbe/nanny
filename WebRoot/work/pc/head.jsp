<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 头部内容 -->

<nav class="navbar" role="navigation">

    <!-- logo区 暂时不用-->
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><i class="fa fa-list btn-nav-toggle-responsive text-white"></i> <span class="logo">掌<strong>上保</strong>姆 <i class="fa fa-bookmark"></i></span></a>
    </div>

    <!-- 右侧工具列表 -->
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav user-menu navbar-right ">
            <li>
            	<a href="#" class="user dropdown-toggle" data-toggle="dropdown">
            		<span class="username">
            			<c:if test="${loginUser.headImg=='null' || loginUser.headImg=='' }">
            			<img src="${pageContext.request.contextPath}/images/defalut.jpg" class="user-avatar">  
            			</c:if>
            			<c:if test="${loginUser.headImg!='null' && loginUser.headImg!='' }">
            			<img src="${loginUser.headImg }" class="user-avatar" >  
            			</c:if>
            			${loginUser.nickName } 
            		</span></a>
                <ul class="dropdown-menu">
                    <%--<li><a href="#"><i class="fa fa-user"></i> 个人信息</a>
                    </li>
                    <li><a href="#"><i class="fa fa-envelope"></i> 站内信</a>
                    </li>
                    <li><a href="#"><i class="fa fa-cogs"></i> 设置</a>
                    </li>
                    <li class="divider"></li>--%>
                    <c:if test="${tmpadminobj!=null }">
	                    <li><a href="${pageContext.request.contextPath}/admin/backtoadminplat.html" class="text-danger"><i class="fa fa-lock"></i> 返回系统管理平台</a>
	                    </li>
                    </c:if>
                    <li><a href="${pageContext.request.contextPath}/loginout.html" class="text-danger"><i class="fa fa-lock"></i> 注销</a>
                    </li>
                </ul>
            </li>
            <!-- 未读信息 -->
            <%--<li><a href="#" class="settings dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i></a>
                <ul class="dropdown-menu inbox">
                    <li><a href="inbox.php" class="btn bg-primary">查看全部</a></li>
                </ul>
            </li>
            --%><!-- 通知 -->
            <%--<li>
            	<a href="#" class="settings dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell animated "></i></a>
            </li>
        --%></ul>
    </div>
</nav>
<!-- 头部 结束 -->

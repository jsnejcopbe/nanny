<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 左侧菜单 -->
<div class="left-sidebar">
    <div class="sidebar-holder">
    	<c:if test="${loginUser.isAdmin == '0' }">
	    	<ul class="nav  nav-list" id="shop">
				<li><a href="${pageContext.request.contextPath}/shop/shopIndex.html" data-original-title="Dashboard"><i class="iconfont-gl">&#xe60a;</i><span class="hidden-minibar bar-fir"> 首页</span></a>
	            </li>
	           
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar bar-fir">  商品管理 </span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            		&nbsp;<span class="badge bg-primary pull-right">${newshopCount}</span>
	            	</a>
	            	<ul>
	          		 	<li><a href="${pageContext.request.contextPath}/shop/productList.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar">本店商品管理 </span></a>
	            		</li>
	            		<li><a href="${pageContext.request.contextPath}/shop/goods.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar">平台图库 </span></a>
	            		</li>
	          		 	<%--<li><a href="${pageContext.request.contextPath}/shop/proapply.html" data-original-title="Top Navbar"><i class="fa fa-th-list"></i><span class="hidden-minibar">新增商品列表</span></a>--%>
	          		 	</li>
	          		 	<li><a href="${pageContext.request.contextPath}/shop/everydaypro.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe620;</i><span class="hidden-minibar">每日新商品</span>&nbsp;<span class="badge bg-primary pull-right">${newshopCount}</span></a></li>
	            		<%-- <li><a href="${pageContext.request.contextPath}/shop/classsort.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar">商店一级类目 </span></a> --%>
	            	
	            	</ul>
	            </li>
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe615;</i><span class="hidden-minibar bar-fir">  订单管理 </span>&nbsp;<span class="badge bg-primary js-menuodcount">${waitSendOrderCount }</span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
	          		 	<li><a href="${pageContext.request.contextPath}/shop/order-1.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe615;</i><span class="hidden-minibar">店铺订单</span>&nbsp;<span class="badge bg-primary pull-right js-menuodcount-sec">${waitSendOrderCount }</span></a>
	          		 	</li>
	          		 	<li><a href="${pageContext.request.contextPath}/shop/shopProductExchange.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar">积分商品兑换明细</span></a>
			            </li>
	          		 	<li><a href="${pageContext.request.contextPath}/shop/refund.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar">退款申请</span></a>
	          		 	</li>
	            	</ul>
	            </li>
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar bar-fir">  客户管理</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li>
			            	<a href="${pageContext.request.contextPath}/shop/fansinit.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar"> 店铺客户 </span></a>
			            </li>
			            <li>
			            	<a href="${pageContext.request.contextPath}/shop/groupmessage.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar"> 群发消息 </span></a>
			            </li>
			            <%--<li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe608;</i><span class="hidden-minibar"> 评价管理 </span></a>
			            </li>--%>
			        </ul>
	            </li>
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe603;</i><span class="hidden-minibar bar-fir">  财务明细 </span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="${pageContext.request.contextPath}/shop/shopAccount.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe603;</i><span class="hidden-minibar"> 资金明细 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/users/transrec.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar"> 资金提现 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/shop/shopCoupon.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe608;</i><span class="hidden-minibar"> 抵用券明细 </span></a>
			            </li>
			            <%-- <li><a href="${pageContext.request.contextPath}/shop/shopIntegralDetail.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar"> 积分明细 </span></a>
			            </li> --%>
			            
			        </ul>
	            </li>
	            <li>
	            	<a href="${pageContext.request.contextPath}/shop/jump_shopdate.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar bar-fir">店铺业务统计 </span></a>
			    </li>
	            <li>
	            	<a href="${pageContext.request.contextPath}/shop/shopInfo.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar bar-fir">店铺编辑 </span></a>
			    </li>
			  <%--   <li>
	            	<a href="${pageContext.request.contextPath}/shop/shopsup.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar">供应商 </span></a>
			    </li> --%>
	           <!--  <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar">  基础设置 </span><span class="pull-right">4</span>
	            	</a>
	            	<ul>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe630;</i><span class="hidden-minibar"> 营业时间设置 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe6d5;</i><span class="hidden-minibar"> 配送范围设置 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe613;</i><span class="hidden-minibar"> 店铺公告 </span></a>
			            </li>
			        </ul>
	            </li> -->
	            <li style="display: none;"><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe626;</i><span class="hidden-minibar bar-fir"> 橱窗位申请 </span></a>
			    </li>
			    <li><a href="${pageContext.request.contextPath}/shop/shopMessage.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe92e;</i><span class="hidden-minibar bar-fir"> 站内信 </span></a>
			    </li>
			    <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
						<i class="iconfont-gl">&#xe603;</i><span class="hidden-minibar bar-fir">  设置 </span><span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="${pageContext.request.contextPath}/shop/banklist.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe603;</i><span class="hidden-minibar"> 提现账户 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/shop/shopPrint.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe603;</i><span class="hidden-minibar"> 打印设备 </span></a>
			            </li>
			            <%--<li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe60c;</i><span class="hidden-minibar"> 佣金管理 </span></a>
			            </li>
			        --%></ul>
	            </li>
	            <li>
	            	<a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe6b4;</i><span class="hidden-minibar bar-fir"> 推荐朋友加入 </span></a>
	            	<a style="text-align: center;margin-top: 10px;display: block;">
	            		<img style="max-width: 100%;" src="${pageContext.request.contextPath}/image/TwoCode.html?data=<%=basePath %>shop/shop_exp_${loginUser.shopID }.html">
	            	</a>
			    </li>
	    	</ul>
    	</c:if>
    	<c:if test="${loginUser.isAdmin == '1' }">
	        <ul class="nav  nav-list" id="admin">
	            <!-- 菜单隐藏按钮 -->
	            <li class="nav-toggle">
	                <button class="btn  btn-nav-toggle text-primary"><i class="fa fa-angle-double-left toggle-left"></i> 
	                </button>
	            </li>
	
	            <li><a href="${pageContext.request.contextPath}/admin/adminIndex.html" data-original-title="Dashboard"><i class="iconfont-gl">&#xe60a;</i><span class="hidden-minibar bar-fir"> 首页</span></a>
	            </li>
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar bar-fir">  商品管理</span>&nbsp;<span class="badge bg-primary">${waitSendProApplyCount }</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="${pageContext.request.contextPath}/admin/protype.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 平台商品分类管理 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/probrand.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 平台代理品牌管理 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/productlist.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar"> 平台商品列表 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/adminapply.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar"> 新增商品申请 </span>&nbsp;<span class="badge bg-primary pull-right">${waitSendProApplyCount }</span></a>
			            </li>
			        </ul>
	            </li>
	            
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar bar-fir">  平台用户管理</span>&nbsp;<span class="badge bg-primary">${waitSendApplyCount }</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
	            		<%-- <li><a href="${pageContext.request.contextPath}/admin/supplier.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar">平台供货商管理</span></a> --%>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/business.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar"> 平台商户管理 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/clientele.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar"> 平台用户管理 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/applylist.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62e;</i><span class="hidden-minibar"> 商户申请列表 </span>&nbsp;<span class="badge bg-primary pull-right">${waitSendApplyCount }</span></a>
			            </li>
			        </ul>
	            </li>
	            
	            <li><a href="${pageContext.request.contextPath}/admin/expandlist.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar bar-fir"> 业务推广管理 </span></a></li>
			            
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe637;</i><span class="hidden-minibar bar-fir">  资金明细</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="${pageContext.request.contextPath}/admin/adminAccount.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe60c;</i><span class="hidden-minibar"> 平台资金明细 </span></a>
			            </li>
			             <li><a href="${pageContext.request.contextPath}/admin/IntegralDetail.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar"> 平台积分明细 </span></a>
			            </li>
			             <li><a href="${pageContext.request.contextPath}/admin/ProductExchangeDetail.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar"> 平台兑换明细 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/cashapply.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe627;</i><span class="hidden-minibar"> 平台用户提现申请 </span></a>
			            </li>
			             <li><a href="${pageContext.request.contextPath}/admin/adminRedpack.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe60c;</i><span class="hidden-minibar"> 红包发放记录 </span></a>
			             <li><a href="${pageContext.request.contextPath}/admin/coupon.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe608;</i><span class="hidden-minibar"> 抵用券明细 </span></a>
			            </li>
			        </ul>
	            </li>
	            
	            <li style="display: none;"><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe613;</i><span class="hidden-minibar bar-fir"> 公告管理 </span></a>
	            
	            <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar bar-fir">  系统数据设置</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="${pageContext.request.contextPath}/admin/area-1.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe60e;</i><span class="hidden-minibar"> 经营地址设置 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/adminbank.html" data-original-title="Top Navbar"><i class="fa fa-credit-card"></i><span class="hidden-minibar"> 转账银行设置 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/classsort.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar"> 商品类目排序 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/shopsort.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 店铺推荐排序 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/wxmenu.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62c;</i><span class="hidden-minibar"> 微信菜单设置 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/adverits.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62c;</i><span class="hidden-minibar"> 轮播广告位设置 </span></a>
			            </li>
			           <%--  <li><a href="${pageContext.request.contextPath}/admin/sysIntegralSetting.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar"> 系统积分设置 </span></a>
			            </li>
			            <li><a href="${pageContext.request.contextPath}/admin/returnPoint.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar"> 系统积分兑换</span></a> --%>
			            </li>
			            <%--<li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar"> 橱窗设置 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe626;</i><span class="hidden-minibar"> 橱窗申请 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 首页导航设置 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe67b;</i><span class="hidden-minibar"> 支付方式设置 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe628;</i><span class="hidden-minibar"> 积分商城地址 </span></a>
			            </li>
			            <li><a href="javascript:void(0)" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe642;</i><span class="hidden-minibar"> SEO设置 </span></a>
			            </li>
			        --%>
			        </ul>
	            </li>
	            
	             <li class="submenu">
	            	<a class="dropdown" href="#" data-original-title="UI Elements">
	            		<i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar bar-fir">  直营商城管理</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span>
	            	</a>
	            	<ul>
			            <li><a href="/mall/admin/commodityshow.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe60e;</i><span class="hidden-minibar"> 商品展示 </span></a>
			            </li>
			            <li><a href="/mall/admin/ordermodule.html" data-original-title="Top Navbar"><i class="fa fa-credit-card"></i><span class="hidden-minibar"> 商品订单 </span></a>
			            </li>
			            <li><a href="/mall/admin/commodityType.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar"> 商品类型 </span></a>
			            </li>
			            <li><a href="/mall/admin/advertisement.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 海报广告 </span></a>
			            </li>
			              <li><a href="/mall/admin/icon.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 导航图标</span></a>
			            </li>
			            <li><a href="/mall/admin/activityList.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62c;</i><span class="hidden-minibar"> 活动展示 </span></a>
			            </li>
			            <li><a href="/mall/admin/winning.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe62c;</i><span class="hidden-minibar"> 中奖列表 </span></a>
			            </li>
			            </li>
			        </ul>
	            </li>
	            
<!-- 	             <li class="submenu"> -->
<!-- 	            	<a class="dropdown" href="#" data-original-title="UI Elements"> -->
<!-- 	            		<i class="iconfont-gl">&#xe606;</i><span class="hidden-minibar bar-fir">  广告设置</span> <span class="pull-right"><i class="iconfont-gl">&#xe601;</i></span> -->
<!-- 	            	</a> -->
<!-- 	            	<ul> -->
<!-- 			            <li><a href="${pageContext.request.contextPath}/admin/adverits.html" data-original-title="Top Navbar"><i class="iconfont-gl">&#xe604;</i><span class="hidden-minibar"> 广告展示 </span></a> -->
<!-- 			            </li> -->
			            
			            
			            
<!-- 			        </ul> -->
<!-- 	            </li> -->
	            
	        </ul>
        </c:if>
    </div>
</div>


<!-- 左侧菜单 结束-->

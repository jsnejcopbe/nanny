<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人主页</title>
  	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="renderer" content="webkit">
    <!-- 360浏览器指定默认极速模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <!-- 优先用Chrome渲染 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/salesman/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/salesman/theme.css">

</head>


<script type="text/javascript">
	var path = "${pageContext.request.contextPath}/";
</script>

<style>
                /*拨打电话弹出框 样式*/
                
                #j-popupTel .pop-phone-mask {
                    position: absolute;
                    left: 0;
                    top: 0;
                    bottom: 0;
                    right: 0;
                    z-index: 9999;
                    background: rgba(50, 50, 50, 0.5);
                }
                
                #j-popupTel .box {
                    width: 255px;
                    background-color: #fff;
                    border-radius: 5px;
                    position: relative;
                    height: 400px;
                }
                
                #j-popupTel .box .top-img {
                    width: 100%;
                    height: 120px;
                    margin-top: 10px;
                    background: url(http://7xiobb.com2.z0.glb.clouddn.com/phoneicon.png) no-repeat;
                    background-size: 45%;
                    background-position: center;
                }
                
                #j-popupTel .box ul {
                    padding: 0 15px;
                    position: absolute;
                    width: 100%;
                    top: 130px;
                    bottom: 44px;
                    overflow: auto;
                    padding-bottom: 10px;
                    background: #fff;
                }
                
                #j-popupTel .box ul li {
                    position: relative;
                }
                
                #j-popupTel .box ul li.none {
                    margin-top: 10px;
                    text-align: center;
                    color: #9f9f9f;
                }
                
                #j-popupTel .box ul li span {
                    display: inline-block;
                    margin: 10px 0 8px 15px;
                    color: #999999;
                }
                
                #j-popupTel .box ul li i {
                    position: absolute;
                    top: 44px;
                    right: 26px;
                    color: #23c768;
                }
                
                #j-popupTel .box ul li .numbox {
                    width: 227px;
                    height: 40px;
                    padding-left: 17px;
                    line-height: 40px;
                    border: 1px solid #999999;
                    border-radius: 25px;
                    color: #000;
                }
                
                #j-popupTel .box .close-btn {
                    width: 100%;
                    height: 44px;
                    border-top: 1px solid #e9e9e9;
                    color: #999999;
                    bottom: 0px;
                    position: absolute;
                    background: #fff;
                    border-radius: 0 0 5px 5px;
                }
                
                .div_img{
                	display: inline-block;
					margin-right: 15px;
					text-align: center;
					width: 80px;
                }
                
                .div_img > span{
                	display: block;
					width: 80px;
					overflow: hidden;
					white-space: nowrap;
					text-overflow: ellipsis;
                }
</style>

<body>  
    <div class="m-content m-screen">
        <div class="m-wrapbox" id="j-m-wrapbox">
            <div class="m-wrap">
                <div class="m-infobox">
                    <!-- 头像等 -->
                    <section class="m-infos">
                        <a href="#" class="m-face-box">
                            <div class="m-face iconfont"><img style="display: none;" src="#"
                                onerror="this.style.display='none'"></div>
                        </a>
                        <div class="m-infos-cont">
                            <div class="m-name ui-elli">
                            	<span>${salesman_info[0].name } </span>
                            </div>
                            <!--
	                            <div class="m-post ui-elli">
	                            	<span>技术部</span>&nbsp;&nbsp;<span>程序员</span>
	                            </div>
                             -->
                        </div>
                    </section>
                    <!-- 访客 -->
                    <section class="m-visitors" style="text-align: center;"> 
                        <i class="iconfont i-visitor"></i>
						<div class="m-num" style="text-align: center;">
						    <span>推荐店铺数 :</span>                	
  							${salesman_info[0].total }
                        </div>
                    </section>
                    <!-- section 访客 end -->
                    <i data-action="aside2" class="iconfont m-rside-btn"></i>
                </div>
                <!-- 个人信息  -->
                <div class="m-scroll">
                    <div class="m-modbox">
                        <div class="m-modbox-inner">
                            <section class="m-mod fixed">
                                <ul class="m-personalinfo fixed">
                                    <li class="fixed">
                                        <i data-action="dailog-qr" class="iconfont i-qrcode"></i>
                                        <a data-action="popUp" class="m-tel" href="javascript:void(0)">
                                            <i class="iconfont i-tel"></i>
                                            <span>${salesman_info[0].tel }</span>
                                        </a>
                                    </li>

                                    <li>
                                        <span class="m-tle">E-Mail：</span>
                                        <div class="cont">
                                            <div class="ui-elli num" style="">${salesman_info[0].mail }</div>
                                        </div>
                                    </li>

                                    <li>
                                        <span class="m-tle">QQ：</span>
                                        <div class="cont">
                                            <div class="ui-elli num" style="">${salesman_info[0].qq }</div>
                                        </div>
                                        <a style="display: none;" href="#" class="addfriends" data-action="dailog-addfriends" data-type="qq">加好友</a>
                                    </li>
                                </ul>
                            </section>
                            <!--  -->

                            <section class="m-mod fixed">
                                <div class="m-signature">
                                    <h3>推广店铺</h3>
                                    <div class="m-cont" style="min-height: 200px;overflow-y: auto;text-align: center;">
                                    	<c:forEach var="list" items="${shop_list }">
                                    		<div class="div_img">
                                    			<img class="user-avatar" src="${list.shop_icon }" data-id="${list.id }" style="width: 50px;height: 50px;border-radius: 50%;margin-right: 5px;margin-bottom: 5px; border:3px solid #1E90FF !important;" alt="">
                                    			<br>
                                    			<span>${list.shop_name }</span>
                                    		</div>
                                    	</c:forEach>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                </div> 
            </div> 
            <!-- 标签栏弹出层-微单页 -->
            <section style="max-height: 540px;" class="m-custommenu" id="j-custom1">

                <ul class="m-rootweb-list fixed"></ul>
            </section>
            <div id="j-custom-overlay1" class="m-custommenu-overlay" data-action="custom"></div>
            <!-- 标签栏弹出层-微链接 -->
            <section style="max-height: 540px;" class="m-custommenu" id="j-custom2">

                <ul class="m-custommenu-ul fixed">

                    <li>
                        <a href="http://mp.soqi.cn/s/12269" target="_blank">
                            <div class="m-svgbox">
                                <svg class="m-svgicon" x="0px" y="0px" width="1024" height="1024" viewBox="0 0 1024 1024">
                                    <g transform="translate(0, 850) scale(1, -1) scale(1, 1)">
                                        <path d="M511.999488 745.25997c-247.234871 0-447.659623-200.423729-447.659623-447.659623S264.763594-150.058253 511.999488-150.058253s447.659623 200.423729 447.659623 447.659623S759.234359 745.25997 511.999488 745.25997zM321.092507 659.541703c-16.501836-43.186591-29.783327-91.373057-39.551808-142.999878l-115.224278 0C204.47244 576.659017 257.87879 626.129732 321.092507 659.541703zM145.234267 479.087794l129.927141 0c-8.119935-53.422723-12.640901-109.912295-13.250792-167.882592L103.098611 311.205203C105.064382 371.356165 120.012839 428.21515 145.234267 479.087794zM103.558076 273.750149l158.515247 0c1.006933-54.379515 5.448082-107.368356 13.08911-157.63725L145.234267 116.112899C121.449561 164.087541 106.798886 217.386444 103.558076 273.750149zM166.316421 78.658869l115.224278 0c9.768481-51.625798 23.049972-99.812263 39.551808-142.999878C257.87879-30.929038 204.47244 18.541677 166.316421 78.658869zM493.271962-111.106102c-42.782386 1.927909-83.857896 10.407024-122.232902 24.489764-1.852184 4.0175-3.689019 8.086166-5.492084 12.267395-19.389606 44.948725-34.809807 96.719832-45.887097 153.007813l173.612083 0L493.271962-111.106102zM493.271962 116.112899 313.034994 116.112899c-7.879458 50.042743-12.468986 103.066377-13.505595 157.63725l193.742563 0L493.271962 116.112899zM493.271962 311.205203 299.369763 311.205203c0.62831 58.180074 5.294586 114.712625 13.666254 167.882592l180.236967 0L493.272985 311.205203zM493.271962 516.541825 319.659879 516.541825c11.07729 56.287981 26.497491 108.060111 45.887097 153.007813 1.803066 4.181229 3.6399 8.250918 5.492084 12.267395 38.375006 14.08274 79.450517 22.561855 122.232902 24.489764L493.271962 516.541825zM920.900366 311.205203 762.087337 311.205203c-0.610914 57.970296-5.130857 114.460892-13.250792 167.882592l129.927141 0C903.986137 428.21515 918.934594 371.356165 920.900366 311.205203zM857.682555 516.541825l-115.224278 0c-9.768481 51.626821-23.049972 99.813287-39.551808 142.999878C766.120187 626.129732 819.526537 576.659017 857.682555 516.541825zM530.727015 706.306796c42.782386-1.927909 83.857896-10.407024 122.232902-24.489764 1.852184-4.016477 3.689019-8.086166 5.492084-12.267395 19.389606-44.947701 34.809807-96.719832 45.887097-153.007813L530.727015 516.541825 530.727015 706.306796zM530.727015 479.087794l180.236967 0c8.371668-53.169967 13.037944-109.702518 13.665231-167.882592L530.727015 311.205203 530.727015 479.087794zM530.727015 273.750149l193.742563 0c-1.036609-54.570873-5.626137-107.594507-13.504572-157.63725L530.727015 116.112899 530.727015 273.750149zM652.959917-86.616338c-38.375006-14.083763-79.450517-22.561855-122.232902-24.489764L530.727015 78.658869l173.613106 0c-11.07729-56.287981-26.498514-108.060111-45.887097-153.007813C656.647913-78.530173 654.811078-82.598838 652.959917-86.616338zM702.906469-64.341009c16.501836 43.186591 29.783327 91.37408 39.551808 142.999878l115.224278 0C819.526537 18.541677 766.120187-30.929038 702.906469-64.341009zM878.763686 116.112899 748.836545 116.112899c7.640004 50.268894 12.082176 103.257735 13.08911 157.63725l158.515247 0C917.20009 217.386444 902.549415 164.087541 878.763686 116.112899z"></path>
                                    </g>
                                </svg>
                                <div class="m-tle ui-elli">我的V店</div>
                            </div>
                        </a>
                    </li>
                </ul>
            </section>
            <div id="j-custom-overlay2" class="m-custommenu-overlay" data-action="custom2"></div>
            <!-- 背景层 -->
            <section class="m-bgbox" style="background-image:url(http://7xixdl.com2.z0.glb.qiniucdn.com/OTg=.jpg)"></section>
            <!-- 弹出层  begin-->
  
            <!-- 弹出-二维码 begin -->
            <div class="m-qrpopmask" id="j-m-qrpop">
                <section class="m-qrpop" style="min-width:90%;">
                    <div class="tab-hd">
                        <span name="twoCode" class="item flex-center" data-type="1">推广链接</span>
                    </div>
                    <div id="j-link-qr" class="tab-cont" style="display: none;">
                        <input id="" value="" type="hidden">
                        <div class="tc">
                        	<img id="" style="width:80%;" src="${pageContext.request.contextPath}/image/TwoCode.html?data=shop/exp_${salesman_info[0].guid }.html">
                        </div>
                        <div class="tc mt-5">扫描二维码，即刻开店</div>

                    </div>

                    <div id="j-content-qr" class="tab-cont">
                        <input id="mpContentQRCodeUrl" value=""
                        type="hidden">
                        <div class=" tc">
                       	 	<img id="mpContentQRCode" style="width:80%;" src=""></div>
                        <div class="fs-16 tc mt-10">微信扫一扫，秒存名片信息</div>

                        <div class="tc mt-10">
                            <a class="btn-vcf" href="http://mp.soqi.cn/login/downLoadPhone.xhtml?id=CD69E33ED687F1717CB0BCD31B48A3E7">导入手机通讯录</a>
                        </div>

                    </div>
                    <div class="m-close" data-action="dailog-close"><span class="iconfont"></span></div>
                </section>
            </div>
            <!-- 弹出-二维码 end -->
             
            <div class="m-qrpopmask" id="j-m-addfriendsqrpop">
                <section class="m-qrpop" style="width: 80%;min-height: 30%;position: relative">
                    <span class="iconfont" id="closeaddfriendsqrpop" style="position:absolute;top:10px;right:10px;font-size:2rem;"></span>
                    <div class="m-popTit"><span class="iconfont question" data-flag="hide"></span>
                        <div class="box">
                            <div style="display: none;"><b></b>
                                <p>长按识别二维码</p>
                                <p>目前已支持微信、UC浏览器、QQ浏览器。</p>
                            </div>
                        </div>
                    </div>
                    <div class="tc addfriendsqrimg" style="padding: 10px 0 40px 0;"><img id="addfriendsqr" style="width:80%;" src=""></div>
                    <div class="loading-qr-addfriendsqr">
                        <div style="transform: scale(0.5) rotate(360deg);" class="loading-qr ">
                            <div class="bar1"></div>
                            <div class="bar2"></div>
                            <div class="bar3"></div>
                            <div class="bar4"></div>
                            <div class="bar5"></div>
                            <div class="bar6"></div>
                            <div class="bar7"></div>
                            <div class="bar8"></div>
                        </div>
                        <div style="text-align: center; padding-bottom: 20px;">二维码加载中...</div>
                    </div>
                </section>
            </div>
            <!-- qq 微信 微博 二维码  end-->

            <!-- Toast通知 begin -->
            <div class="ui-toast ui-toast-icon" id="j-toast-icon">
                <div class="toast-cont">
                    <i class="iconfont"></i>已收藏
                </div>
            </div>
            <!-- Toast通知 end -->
           
            <!-- ui-popup 拨号弹出 begin-->
            <section class="ui-popup-mask flex-center" id="j-popupTel">
                <div class="box">
                    <div class="top-img"></div>
                    <ul class="">
                        <li>
                            <span>手机</span>
                            <a class="m-link" href="tel:13959835683">
                                <div class="numbox">13959835683</div>
                                <i class="iconfont"></i>
                            </a>
                        </li>

                        <li>
                            <span>座机</span>
                            <a class="m-link" href="tel:13959835683">
                                <div class="numbox">13959835683</div>
                                <i class="iconfont"></i>
                            </a>
                        </li>

                    </ul>
                    <div class="close-btn flex-center" data-action="popUpClose">
                        <i class="iconfont"></i>
                    </div>
                </div>
            </section>
            <!-- ui-popup 拨号弹出 end-->
        </div>
        <!-- 右侧栏 --> 
        <aside class="m-rside" id="j-m-rside">
            <ul class="m-rside-ul">
                <li id="j-i-add-store">
                    <a href="${pageContext.request.contextPath}/salesman/jumpaudit.html" data-action="toastIcon">
                        <i class="iconfont i-sc" id="i-sc"></i>
                        <div class="m-tle">店铺审核</div>
                    </a>
                </li>

                <li id="j-i-add-swap">
                    <a href="${pageContext.request.contextPath}/salesman/arealist.html">
                        <i class="iconfont i-jh" id="i-jh"></i>
                        <div class="m-tle">地址设置</div>
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/salesman/password.html">
                        <i class="iconfont i-save"></i>
                        <div class="m-tle">密码修改</div>
                    </a>
                </li>
                
               <li>
               		<a class="menu-link" href="${pageContext.request.contextPath}/saleloginout.html">
               		<span>
               			<i class="iconfont">&#xe614;</i>
               		</span><div class="name">退出</div>
               		</a>
               	</li>

               <!--  <li>
                    <a href="#">
                        <i class="iconfont i-save"></i>
                        <div class="m-tle">保存通讯录</div>
                    </a>
                </li>
                
                <li id="share">
                    <a href="javascript:void(0)">
                        <i class="iconfont i-share"></i>
                        <div class="m-tle">分享朋友</div>
                    </a>
                </li>
                
                <li id="phoneDesktop">
                    <a href="javascript:void(0)">
                        <i class="iconfont i-phone"></i>
                        <div class="m-tle">手机桌面</div>
                    </a>
                </li>
                
                <li>
                    <a href="#">
                        <i class="iconfont i-newCard"></i>
                        <div class="m-tle">创建名片</div>
                    </a>
                </li>
                
                <li>
                    <a href="#">
                        <i class="iconfont i-question"></i>
                        <div class="m-tle">帮助</div>
                    </a>
                </li> -->
                <!-- 
                <li>
                    <a href="javascript:void(0)">
                    <i class="iconfont i-qrcode"></i>
                    <div class="m-tle">生成二维码</div>
                    </a>
                </li> -->
            </ul>
        </aside>
        <div class="m-side-overlay" data-action="aside-menu"></div>
        <!-- 左侧栏 -->

        <!-- 侧栏 -->
        <aside class="flexbox flex-vertical m-sidebox" id="j-m-aside" data-action="aside-menu2">
            <div class="flexbox flex-vertical m-side-nologin">
                <div class="m-flex">
                    <a class="facebox" href="#">
                        <i class="iconfont face"></i>
                        <div class="m-txt">点击登录</div>
                    </a>
                </div>
                <div class="m-reg">
                    <div class="m-txt">还没有搜企名片账号?</div>
                    <a class="m-btn" href="#">马上创建名片</a>
                </div>
            </div>
        </aside>
        <div class="m-side-overlay2" data-action="aside2"></div>
    </div>

    <div class="ui-toast" id="j-toast-default">
        <div class="toast-cont">默认的Toast通知</div>
    </div>
    <div class="ui-toast ui-toast-icon" id="j-toast-icon">
        <div class="toast-cont"><i class="iconfont"></i>图标通知</div>
    </div>
     
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/salesman/basic.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/salesman/themes_common.js"></script>
</body>
</html>
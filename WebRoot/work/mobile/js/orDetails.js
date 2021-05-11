//star
$(document).ready(function(){
    var stepW = 24;
    var description = new Array("很不满意|差得太离谱","不满意|部分有破损","一般|质量一般","满意|质量不错","非常满意|质量非常好");
    var stars = $("#star > li");
    var descriptionTemp;
    $("#showb").css("width",0);
    stars.each(function(i){
        $(stars[i]).click(function(e){
            var n = i+1;
            $("#showb").css({"width":stepW*n});
            descriptionTemp = description[i];
            $(this).find('a').blur();
            
            $("#startP").val(n);
            return stopDefault(e);
            return descriptionTemp;
        });
    });
    stars.each(function(i){
        $(stars[i]).hover(
            function(){
                $(".description").text(description[i]);
                
            },
            function(){
                if(descriptionTemp != null)
                    $(".description").text("当前您的评价为:"+descriptionTemp);
                else 
                    $(".description").text(" ");
            }
        );
    });
});
function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};





$(function () {
	 $("#file").on("change",function(){
	 	layer.load(1, { shade: [0.8,'#333']});
	 	var param={
	 		"sURL":"/nanny/upload/images.json",
	 		"sID":"file",
	 		"sContentType":"",
	 		"fnSuccess":function(data){
	 			$(".fm-right img").attr("src",data.path);
	 			layer.closeAll("loading");
	 			$("#logoSrc").val(data.path);
	 		},
	 		"fnError":function(){layer.closeAll("loading");layer.msg("上传失败,请联系管理员");}
	 	};
	 	new g_fnFileUpload(param);
	 });
});

 function discuss(){
	  //$("#dis1").show();
	  //$("#dis").hide();
	  $("#dis1").toggle();
	  $("#dis").toggle();
 }



function addDiscuss(shopID)
 {
 	  
 	  
 	  var con=$("#con").val();
 	  var score=$("#startP").val();
 	  if(con!=""&&score!=""){
 		layer.load(1, { shade: [0.8,'#333']});
 		  data="con="+con+"&score="+score+"&shopID="+shopID;
 		  $.ajax({
              type: "post",
              url: "/nanny/users/addDiscuss.html",
              data:data,
              dataType: "text",
              success: function (msg) {
                  layer.closeAll("loading");
              		//var data=eval('(' + msg + ')');
                  layer.msg("评论成功，您的评论将显示在商铺评论中");
                  
              },
              error: function () {
                  layer.msg('操作有误!');
              }
          });
 		  
 	  }else  {
 		layer.msg("评论信息不完整！");
 	  }

 }


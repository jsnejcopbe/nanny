
	
	$(function(){
		$("#rnum").hide();
	 $("input[name=redpa]").click(function(){
		 var re= $(this).val();
		 if(re==1){
			 $("#rnum").show();
		 }else{
			 $("#rnum").hide();
		 }

	 });
	});
	
	function clearNum(obj)
	{
	 //先把非数字的都替换掉，除了数字
	 obj.value = obj.value.replace(/[^\d]/g,"");
	}
	function opp(id){
	layer.open({
	    type: 1,
	    shade: false,
	    title: false, //不显示标题
	    content: $('#sname_'+id), //捕获的元素
	    cancel: function(index){
	        layer.close(index);
	        //this.content.show();
	        
	    }
	});
	}
	
	
	function usered(id) {
		 $("input[id='cb_"+id+"']").attr("checked","checked"); 
		 $('#myModal').modal('show');
	}
	
	function redpack() {
		var totalMoney =$("input[name=totalMoney]").val();
  		var redpa =$("input[name=redpa]:checked").val();
  		var num=$("input[name=num]").val();
  		
  		 if(totalMoney>=1&&totalMoney<=200&&redpa==0){
  			cbox();
  		 }else if(totalMoney>=3&&totalMoney<=4000&&redpa==1&&num>=3&&num<=20){
  			cbox();
  		 }else{
  			layer.msg("普通红包：金额不得少于  1  元并多于200元；<br/>裂变红包：裂变人数必须介于(包括)3到20之间，每个红包的平均金额必须在1.00-200.00元之间.");
  		 }
	}
	
	
	function cbox() {
		
		 layer.load(1, { shade: [0.8,'#333']});
		
		var uidList = new Array();
		  $("input[name=cbids]:checked").each(function(i){
			  var obj =new Object(); 
			  //$("#id").is(":checked")
			//$("input[name=opid]").val()
			   var opid=$(this).parents("tr").find("input[name=opid]").val();
			   
		          var uid=$(this).val();
			  		obj.userID=uid;
			  		obj.openID=opid;
		          uidList.push(obj);
		      });
		  		var actName =$("input[name=actName]").val();
		  		var totalMoney =$("input[name=totalMoney]").val();
		  		var wishing =$("input[name=wishing]").val();
		  		var redpa =$("input[name=redpa]:checked").val();
		  		var num;
		  		if(redpa=='0'){
		  			num=1;
		  		}else{
		  			 num =$("input[name=num]").val();
		  		}
		  		//$("#updred").serialize()
		  		 var remark='null';
		  		
		       data="actName="+actName+"&totalMoney="+totalMoney+"&wishing="+wishing+"&type="+redpa+"&num="+num+"&remark="+remark+"&openIdList="+JSON.stringify(uidList);
		      
		       $.ajax({
	             type: "POST",
	             url: "/nanny/admin/sendnormalredbag.json",
	             data: data,
	             dataType: "json",
	             success: function(data){
	            	 	layer.msg("成功发送");
	            		location.reload(true);
	            	
	             },
	             error:function(){
	            	 layer.msg("发送失败");
	            	 location.reload(true);
	             	}
	         });
		  		 
	}
	
	function clearNoNum(obj)
	{
	 //先把非数字的都替换掉，除了数字和.
	 obj.value = obj.value.replace(/[^\d.]/g,"");
	 //必须保证第一个为数字而不是.
	 obj.value = obj.value.replace(/^\./g,"");
	 //保证只有出现一个.而没有多个.
	 obj.value = obj.value.replace(/\.{2,}/g,".");
	 //保证.只出现一次，而不能出现两次以上
	 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}
	
	
	
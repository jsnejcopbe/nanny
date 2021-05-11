$(function(){
	var query = $("#shop_query");
	
	jQuery.getScript(path+"js/syl/verify.js");
	jQuery.getScript(path+"js/syl/basis.js");
	jQuery.getScript(path+"js/syl/jquery.form.js");
	jQuery.getScript(path+"js/syl/jquery.mypagin.js");
	jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
		
		data_load();
		
		$("#shop_table").on("click","[name='pass']",function(){
			var tr = $(this).parents("tr");
			var id = tr.find("#id").val();
			var tel = tr.find("#tel").text();
			//tr.find("[name='state']").removeClass("bg-warning").addClass("bg-success");
			state_change(id,$.trim(tel),1);
		})
		
		$("#shop_table").on("click","[name='nopass']",function(){
			var tr = $(this).parents("tr");
			//tr.find("[name='state']").removeClass("bg-warning").addClass("bg-danger");
			var id = tr.find("#id").val();
			
			state_change(id,$.trim(tel),2);
		})
		
//		$("#shop_table").on("click",".look_password",function(){
//			 var tr = $(this).parents("tr");
//			 var id = tr.find("#id").val();
//			 $.ajax({
//				 async:false,
//				 type:'post',
//				 url:path+'admin/get/password',
//				 data:{id:id}
//			 }).done(function(msg){
//				 
//			 })
//			 
//		})
		
		$("body").on("click","#state_query a",function(){
			var id = $(this).attr("data-id");
			//console.log(id);
			$("#hide_type").val(id);
			query.find("form").submit();
		})
		
		function state_change(id,tel,type){
			 layer.load(1, {shade: [0.8,'#333']});
			$.ajax({
				async:false,
				type:'post',
				url:path+'admin/list/change.html',
				data:{id:id,tel:tel === undefined ? "" : tel,type:type},
			}).done(function(msg){
				layer.closeAll("loading");
				if(msg == "exist"){
					layer.msg("该商店名称已经存在");
				}else if(msg == "exist1")layer.msg("该手机号码的店家已存在 不能通过");
				else {
					//alert(msg);
					data_load()
				};
			}).error(function(msg){
				layer.msg("提交失败");
				layer.closeAll("loading");
			})
		}
	})
	
	function data_load(){
		var a = function(){
			remove_btn();
		}
		
		function remove_btn(){
			$.each($("#shop_table tbody tr"),function(){
				var temp = $(this).find("[name='state']");
				if(temp.html() != '待审核'){
					$(this).find("[name='pass']").remove();
					$(this).find("[name='nopass']").remove();
				}
				if(temp.html() == '通过'){
					temp.removeClass("bg-warning").addClass("bg-success");
				}
				if(temp.html() == '未通过'){
					temp.removeClass("bg-warning").addClass("bg-danger");
					//$(this).find(".look_password").remove();
				}
				if(temp.html() == '待审核'){
					//$(this).find(".look_password").remove();
				}
			})
		}
		
		$("#hide_type").val("-1");
		
		$("#shop_table").mytable({
			url:path+'admin/list/init.html',
			querydiv:query,
			funs:a,
			diys:{state:'<label class="label bg-warning" name="state">Finance</label>'},
			btns:[ {
				value : "通过",
				isclass : 'btn btn-primary',
				icon : 'icon-plus',
				name : 'pass'
			}, {
				value : "不通过",
				isclass : 'btn btn-danger',
				icon : 'icon-nopass',
				name : 'nopass'
			}],
			autopagin:false,
			row:8
		})
	}
})
$(function(){
	var query = $("#expand_query");
		query.find("form").attr("action",init_url);
		
	jQuery.getScript(path+"js/syl/jquery.form.js");
	jQuery.getScript(path+"js/syl/verify.js");
	jQuery.getScript(path+"js/syl/basis.js");
	jQuery.getScript(path+"js/syl/jquery.mypagin.js");
	jQuery.getScript(path+"js/syl/jquery.mytable.js",function(){
		
		data_load();
		
		$("#btn_add").click(function(e){
			$("#exp_add_modal input").val("");
			$("#exp_add_modal h4").html("添加");
			$("#exp_add_modal").modal();
		})
		
		$("#exp_add_modal").on("click","[type='submit']",function(){
			var b = $("#exp_add_modal form").verifys({
				msg:["姓名不能为空","请填写正确的电话"],
				type:3
			})
			if(!b)return false;
			$("#exp_add_modal form").formSubmit({
				url:path+"admin/expandlist/add.html",
				dataType:"text",
				success:function(msg){
					if(msg == "exist"){
						layer.msg("联系电话已存在");
						return;
					}else if(msg){
						layer.msg("不能为空");
						return;
					}
					$("#exp_add_modal .close").click();
					
					data_load();
				}
			})
			return false;
		})
		
		$("#expand_table").on("click","[name='edit']",function(){
			var id = $(this).parents("tr").find("#id").val();
			$("#exp_add_modal input").val("");
			$("#exp_add_modal h4").html("修改");
			$.ajax({
				type:'post',
				url:path+'admin/expandlist/find.html',
				data:{id:id},
				dataType:'json'
			}).done(function(json){
				$("#exp_add_modal #hide_id").val(json[0].id);
				$("#exp_add_modal #name").val(json[0].name);
				$("#exp_add_modal #mail").val(json[0].mail);
				$("#exp_add_modal #qq").val(json[0].qq);
				$("#exp_add_modal #phone").val(json[0].tel);
				$("#exp_add_modal").modal();
			}).error(function(msg){
				alert("网络异常")
			})
		})
		
		$("#expand_table").on("click","[name='expand_href']",function(){
			var guid = $(this).parents("tr").find("#guid").html();
			$("#expand_href_modal #content").html();
			var host = window.location.host;
			var base_url = host+path;
			$("#expand_href_modal #content img").attr("src",path+"image/TwoCode.html?data=shop/exp_"+guid+".html");
			$("#copy_herf").html(base_url+"shop/exp_"+guid+".html");
			$("#expand_href_modal").modal();
		})
		
		// 定义一个新的复制对象
		var clip = new ZeroClipboard($("#copy_btn"), {
		  moviePath: path+"js/copy/ZeroClipboard.swf"
		} );

		// 复制内容到剪贴板成功后的操作
		clip.on( 'complete', function(client, args) {
		   alert("复制到剪切板成功");
		} );
	})
	
	function data_load(){
		$("#expand_table").mytable({
			url:init_url,
			querydiv:query,
			btns:[{
					value : "修改",
					isclass : 'btn btn-primary',
					icon : 'fa fa-edit',
					name : 'edit'
				},
				{
					value:"推广链接",
					isclass : 'btn btn-primary',
					icon : 'fa fa-chain',
					name : 'expand_href'
				}
			],
			row:8
		})
	}
})
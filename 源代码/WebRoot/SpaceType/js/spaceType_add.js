$(function () {
	$("#spaceType_spaceTypeName").validatebox({
		required : true, 
		missingMessage : '请输入场地类型',
	});

	$("#spaceType_price").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入价格(小时)',
		invalidMessage : '价格(小时)输入不对',
	});

	//单击添加按钮
	$("#spaceTypeAddButton").click(function () {
		//验证表单 
		if(!$("#spaceTypeAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#spaceTypeAddForm").form({
			    url:"SpaceType/add",
			    onSubmit: function(){
					if($("#spaceTypeAddForm").form("validate"))  { 
	                	$.messager.progress({
							text : "正在提交数据中...",
						}); 
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#spaceTypeAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#spaceTypeAddForm").submit();
		}
	});

	//单击清空按钮
	$("#spaceTypeClearButton").click(function () { 
		$("#spaceTypeAddForm").form("clear"); 
	});
});

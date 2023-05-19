$(function () {
	$.ajax({
		url : "SpaceType/" + $("#spaceType_spaceTypeId_edit").val() + "/update",
		type : "get",
		data : {
			//spaceTypeId : $("#spaceType_spaceTypeId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (spaceType, response, status) {
			$.messager.progress("close");
			if (spaceType) { 
				$("#spaceType_spaceTypeId_edit").val(spaceType.spaceTypeId);
				$("#spaceType_spaceTypeId_edit").validatebox({
					required : true,
					missingMessage : "请输入类型id",
					editable: false
				});
				$("#spaceType_spaceTypeName_edit").val(spaceType.spaceTypeName);
				$("#spaceType_spaceTypeName_edit").validatebox({
					required : true,
					missingMessage : "请输入场地类型",
				});
				$("#spaceType_price_edit").val(spaceType.price);
				$("#spaceType_price_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入价格(小时)",
					invalidMessage : "价格(小时)输入不对",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#spaceTypeModifyButton").click(function(){ 
		if ($("#spaceTypeEditForm").form("validate")) {
			$("#spaceTypeEditForm").form({
			    url:"SpaceType/" +  $("#spaceType_spaceTypeId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#spaceTypeEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#spaceTypeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});

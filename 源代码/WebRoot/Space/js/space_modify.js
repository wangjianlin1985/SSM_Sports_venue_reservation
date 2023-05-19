$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('space_spaceDesc_edit');
	var space_spaceDesc_edit = UE.getEditor('space_spaceDesc_edit'); //场地描述编辑器
	space_spaceDesc_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Space/" + $("#space_spaceNo_edit").val() + "/update",
		type : "get",
		data : {
			//spaceNo : $("#space_spaceNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (space, response, status) {
			$.messager.progress("close");
			if (space) { 
				$("#space_spaceNo_edit").val(space.spaceNo);
				$("#space_spaceNo_edit").validatebox({
					required : true,
					missingMessage : "请输入场地号",
					editable: false
				});
				$("#space_spaceTypeObj_spaceTypeId_edit").combobox({
					url:"SpaceType/listAll",
					valueField:"spaceTypeId",
					textField:"spaceTypeName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#space_spaceTypeObj_spaceTypeId_edit").combobox("select", space.spaceTypeObjPri);
						//var data = $("#space_spaceTypeObj_spaceTypeId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#space_spaceTypeObj_spaceTypeId_edit").combobox("select", data[0].spaceTypeId);
						//}
					}
				});
				$("#space_spacePhoto").val(space.spacePhoto);
				$("#space_spacePhotoImg").attr("src", space.spacePhoto);
				$("#space_spacePrice_edit").val(space.spacePrice);
				$("#space_spacePrice_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入价格(小时)",
					invalidMessage : "价格(小时)输入不对",
				});
				$("#space_floorNum_edit").val(space.floorNum);
				$("#space_floorNum_edit").validatebox({
					required : true,
					missingMessage : "请输入楼层",
				});
				$("#space_spaceState_edit").val(space.spaceState);
				$("#space_spaceState_edit").validatebox({
					required : true,
					missingMessage : "请输入占用状态",
				});
				space_spaceDesc_edit.setContent(space.spaceDesc);
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#spaceModifyButton").click(function(){ 
		if ($("#spaceEditForm").form("validate")) {
			$("#spaceEditForm").form({
			    url:"Space/" +  $("#space_spaceNo_edit").val() + "/update",
			    onSubmit: function(){
					if($("#spaceEditForm").form("validate"))  {
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
			$("#spaceEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});

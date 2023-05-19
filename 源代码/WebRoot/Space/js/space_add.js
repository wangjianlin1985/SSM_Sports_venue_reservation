$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('space_spaceDesc');
	var space_spaceDesc_editor = UE.getEditor('space_spaceDesc'); //场地描述编辑框
	$("#space_spaceNo").validatebox({
		required : true, 
		missingMessage : '请输入场地号',
	});

	$("#space_spaceTypeObj_spaceTypeId").combobox({
	    url:'SpaceType/listAll',
	    valueField: "spaceTypeId",
	    textField: "spaceTypeName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#space_spaceTypeObj_spaceTypeId").combobox("getData"); 
            if (data.length > 0) {
                $("#space_spaceTypeObj_spaceTypeId").combobox("select", data[0].spaceTypeId);
            }
        }
	});
	$("#space_spacePrice").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入价格(小时)',
		invalidMessage : '价格(小时)输入不对',
	});

	$("#space_floorNum").validatebox({
		required : true, 
		missingMessage : '请输入楼层',
	});

	$("#space_spaceState").validatebox({
		required : true, 
		missingMessage : '请输入占用状态',
	});

	//单击添加按钮
	$("#spaceAddButton").click(function () {
		if(space_spaceDesc_editor.getContent() == "") {
			alert("请输入场地描述");
			return;
		}
		//验证表单 
		if(!$("#spaceAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#spaceAddForm").form({
			    url:"Space/add",
			    onSubmit: function(){
					if($("#spaceAddForm").form("validate"))  { 
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
                        $("#spaceAddForm").form("clear");
                        space_spaceDesc_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#spaceAddForm").submit();
		}
	});

	//单击清空按钮
	$("#spaceClearButton").click(function () { 
		$("#spaceAddForm").form("clear"); 
	});
});

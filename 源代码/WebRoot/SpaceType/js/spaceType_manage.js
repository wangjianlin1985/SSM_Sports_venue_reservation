var spaceType_manage_tool = null; 
$(function () { 
	initSpaceTypeManageTool(); //建立SpaceType管理对象
	spaceType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#spaceType_manage").datagrid({
		url : 'SpaceType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "spaceTypeId",
		sortOrder : "desc",
		toolbar : "#spaceType_manage_tool",
		columns : [[
			{
				field : "spaceTypeId",
				title : "类型id",
				width : 70,
			},
			{
				field : "spaceTypeName",
				title : "场地类型",
				width : 140,
			},
			{
				field : "price",
				title : "价格(小时)",
				width : 70,
			},
		]],
	});

	$("#spaceTypeEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#spaceTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#spaceTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#spaceTypeEditForm").form({
						    url:"SpaceType/" + $("#spaceType_spaceTypeId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#spaceTypeEditDiv").dialog("close");
			                        spaceType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#spaceTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#spaceTypeEditDiv").dialog("close");
				$("#spaceTypeEditForm").form("reset"); 
			},
		}],
	});
});

function initSpaceTypeManageTool() {
	spaceType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#spaceType_manage").datagrid("reload");
		},
		redo : function () {
			$("#spaceType_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#spaceType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#spaceTypeQueryForm").form({
			    url:"SpaceType/OutToExcel",
			});
			//提交表单
			$("#spaceTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#spaceType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var spaceTypeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							spaceTypeIds.push(rows[i].spaceTypeId);
						}
						$.ajax({
							type : "POST",
							url : "SpaceType/deletes",
							data : {
								spaceTypeIds : spaceTypeIds.join(","),
							},
							beforeSend : function () {
								$("#spaceType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#spaceType_manage").datagrid("loaded");
									$("#spaceType_manage").datagrid("load");
									$("#spaceType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#spaceType_manage").datagrid("loaded");
									$("#spaceType_manage").datagrid("load");
									$("#spaceType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#spaceType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "SpaceType/" + rows[0].spaceTypeId +  "/update",
					type : "get",
					data : {
						//spaceTypeId : rows[0].spaceTypeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (spaceType, response, status) {
						$.messager.progress("close");
						if (spaceType) { 
							$("#spaceTypeEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}

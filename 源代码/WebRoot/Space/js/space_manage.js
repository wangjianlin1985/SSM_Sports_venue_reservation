var space_manage_tool = null; 
$(function () { 
	initSpaceManageTool(); //建立Space管理对象
	space_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#space_manage").datagrid({
		url : 'Space/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "spaceNo",
		sortOrder : "desc",
		toolbar : "#space_manage_tool",
		columns : [[
			{
				field : "spaceNo",
				title : "场地号",
				width : 140,
			},
			{
				field : "spaceTypeObj",
				title : "场地类型",
				width : 140,
			},
			{
				field : "spacePhoto",
				title : "场地图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "spacePrice",
				title : "价格(小时)",
				width : 70,
			},
			{
				field : "floorNum",
				title : "楼层",
				width : 140,
			},
			{
				field : "spaceState",
				title : "占用状态",
				width : 140,
			},
		]],
	});

	$("#spaceEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#spaceEditForm").form("validate")) {
					//验证表单 
					if(!$("#spaceEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#spaceEditForm").form({
						    url:"Space/" + $("#space_spaceNo_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#spaceEditDiv").dialog("close");
			                        space_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#spaceEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#spaceEditDiv").dialog("close");
				$("#spaceEditForm").form("reset"); 
			},
		}],
	});
});

function initSpaceManageTool() {
	space_manage_tool = {
		init: function() {
			$.ajax({
				url : "SpaceType/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#spaceTypeObj_spaceTypeId_query").combobox({ 
					    valueField:"spaceTypeId",
					    textField:"spaceTypeName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{spaceTypeId:0,spaceTypeName:"不限制"});
					$("#spaceTypeObj_spaceTypeId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#space_manage").datagrid("reload");
		},
		redo : function () {
			$("#space_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#space_manage").datagrid("options").queryParams;
			queryParams["spaceNo"] = $("#spaceNo").val();
			queryParams["spaceTypeObj.spaceTypeId"] = $("#spaceTypeObj_spaceTypeId_query").combobox("getValue");
			queryParams["spaceState"] = $("#spaceState").val();
			$("#space_manage").datagrid("options").queryParams=queryParams; 
			$("#space_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#spaceQueryForm").form({
			    url:"Space/OutToExcel",
			});
			//提交表单
			$("#spaceQueryForm").submit();
		},
		remove : function () {
			var rows = $("#space_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var spaceNos = [];
						for (var i = 0; i < rows.length; i ++) {
							spaceNos.push(rows[i].spaceNo);
						}
						$.ajax({
							type : "POST",
							url : "Space/deletes",
							data : {
								spaceNos : spaceNos.join(","),
							},
							beforeSend : function () {
								$("#space_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#space_manage").datagrid("loaded");
									$("#space_manage").datagrid("load");
									$("#space_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#space_manage").datagrid("loaded");
									$("#space_manage").datagrid("load");
									$("#space_manage").datagrid("unselectAll");
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
			var rows = $("#space_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Space/" + rows[0].spaceNo +  "/update",
					type : "get",
					data : {
						//spaceNo : rows[0].spaceNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (space, response, status) {
						$.messager.progress("close");
						if (space) { 
							$("#spaceEditDiv").dialog("open");
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
							space_spaceDesc_editor.setContent(space.spaceDesc, false);
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

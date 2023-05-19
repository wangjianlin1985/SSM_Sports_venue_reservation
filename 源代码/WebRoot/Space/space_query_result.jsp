<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/space.css" /> 

<div id="space_manage"></div>
<div id="space_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="space_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="space_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="space_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="space_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="space_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="spaceQueryForm" method="post">
			场地号：<input type="text" class="textbox" id="spaceNo" name="spaceNo" style="width:110px" />
			场地类型：<input class="textbox" type="text" id="spaceTypeObj_spaceTypeId_query" name="spaceTypeObj.spaceTypeId" style="width: auto"/>
			占用状态：<input type="text" class="textbox" id="spaceState" name="spaceState" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="space_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="spaceEditDiv">
	<form id="spaceEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">场地号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="space_spaceNo_edit" name="space.spaceNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">场地类型:</span>
			<span class="inputControl">
				<input class="textbox"  id="space_spaceTypeObj_spaceTypeId_edit" name="space.spaceTypeObj.spaceTypeId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">场地图片:</span>
			<span class="inputControl">
				<img id="space_spacePhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="space_spacePhoto" name="space.spacePhoto"/>
				<input id="spacePhotoFile" name="spacePhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">价格(小时):</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="space_spacePrice_edit" name="space.spacePrice" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">楼层:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="space_floorNum_edit" name="space.floorNum" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">占用状态:</span>
			<span class="inputControl">
				<select id="space_spaceState_edit" name="space.spaceState" style="width:200px">
					<option value="空闲中">空闲中</option>
					
					<option value="占用中">占用中</option>
				</select>
			</span>

		</div>
		<div>
			<span class="label">场地描述:</span>
			<span class="inputControl">
				<script name="space.spaceDesc" id="space_spaceDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var space_spaceDesc_editor = UE.getEditor('space_spaceDesc_edit'); //场地描述编辑器
</script>
<script type="text/javascript" src="Space/js/space_manage.js"></script> 

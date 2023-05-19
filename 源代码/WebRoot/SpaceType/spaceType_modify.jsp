<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/spaceType.css" />
<div id="spaceType_editDiv">
	<form id="spaceTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类型id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="spaceType_spaceTypeId_edit" name="spaceType.spaceTypeId" value="<%=request.getParameter("spaceTypeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">场地类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="spaceType_spaceTypeName_edit" name="spaceType.spaceTypeName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">价格(小时):</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="spaceType_price_edit" name="spaceType.price" style="width:80px" />

			</span>

		</div>
		<div class="operation">
			<a id="spaceTypeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/SpaceType/js/spaceType_modify.js"></script> 

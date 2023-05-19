<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/spaceType.css" />
<div id="spaceTypeAddDiv">
	<form id="spaceTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">场地类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="spaceType_spaceTypeName" name="spaceType.spaceTypeName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">价格(小时):</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="spaceType_price" name="spaceType.price" style="width:80px" />

			</span>

		</div>
		<div class="operation">
			<a id="spaceTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="spaceTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/SpaceType/js/spaceType_add.js"></script> 

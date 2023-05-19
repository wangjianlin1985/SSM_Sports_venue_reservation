<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.SpaceType" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    SpaceType spaceType = (SpaceType)request.getAttribute("spaceType");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改场地类型信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">场地类型信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="spaceTypeEditForm" id="spaceTypeEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="spaceType_spaceTypeId_edit" class="col-md-3 text-right">类型id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="spaceType_spaceTypeId_edit" name="spaceType.spaceTypeId" class="form-control" placeholder="请输入类型id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="spaceType_spaceTypeName_edit" class="col-md-3 text-right">场地类型:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="spaceType_spaceTypeName_edit" name="spaceType.spaceTypeName" class="form-control" placeholder="请输入场地类型">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="spaceType_price_edit" class="col-md-3 text-right">价格(小时):</label>
		  	 <div class="col-md-9">
			    <input type="text" id="spaceType_price_edit" name="spaceType.price" class="form-control" placeholder="请输入价格(小时)">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxSpaceTypeModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#spaceTypeEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改场地类型界面并初始化数据*/
function spaceTypeEdit(spaceTypeId) {
	$.ajax({
		url :  basePath + "SpaceType/" + spaceTypeId + "/update",
		type : "get",
		dataType: "json",
		success : function (spaceType, response, status) {
			if (spaceType) {
				$("#spaceType_spaceTypeId_edit").val(spaceType.spaceTypeId);
				$("#spaceType_spaceTypeName_edit").val(spaceType.spaceTypeName);
				$("#spaceType_price_edit").val(spaceType.price);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交场地类型信息表单给服务器端修改*/
function ajaxSpaceTypeModify() {
	$.ajax({
		url :  basePath + "SpaceType/" + $("#spaceType_spaceTypeId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#spaceTypeEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                location.href= basePath + "SpaceType/frontlist";
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    spaceTypeEdit("<%=request.getParameter("spaceTypeId")%>");
 })
 </script> 
</body>
</html>


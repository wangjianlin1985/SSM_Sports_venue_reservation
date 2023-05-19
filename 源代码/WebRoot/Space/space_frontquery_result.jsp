<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Space" %>
<%@ page import="com.chengxusheji.po.SpaceType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Space> spaceList = (List<Space>)request.getAttribute("spaceList");
    //获取所有的spaceTypeObj信息
    List<SpaceType> spaceTypeList = (List<SpaceType>)request.getAttribute("spaceTypeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String spaceNo = (String)request.getAttribute("spaceNo"); //场地号查询关键字
    SpaceType spaceTypeObj = (SpaceType)request.getAttribute("spaceTypeObj");
    String spaceState = (String)request.getAttribute("spaceState"); //占用状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>场地查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;background-image: url(<%= basePath%>/images/timg.jpg)">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Space/frontlist">场地信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Space/space_frontAdd.jsp" style="display:none;">添加场地</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<spaceList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Space space = spaceList.get(i); //获取到场地对象
            		String clearLeft = "";
            		if(i%1 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-9 bottom15">
			<div class="col-md-3 bottom15">
			  	<a  href="<%=basePath  %>Space/<%=space.getSpaceNo() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=space.getSpacePhoto()%>" 
			  	
			  	style="height:149px;width:140px;"/></a>
			  </div>
			  <div class="col-md-9 bottom15" >
			     <div class="showFields"style="width:650px;">
			     	<div class="field">
	            		场地号:<%=space.getSpaceNo() %>
			     	</div>
			     	<div class="field">
	            		场地类型:<%=space.getSpaceTypeObj().getSpaceTypeName() %>
			     	</div>
			     	<div class="field">
	            		价格(小时):<%=space.getSpacePrice() %>
			     	</div>
			     	<div class="field">
	            		楼层:<%=space.getFloorNum() %>
			     	</div>
			     	<div class="field">
	            		占用状态:<%=space.getSpaceState() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Space/<%=space.getSpaceNo() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="spaceEdit('<%=space.getSpaceNo() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="spaceDelete('<%=space.getSpaceNo() %>');" style="display:none;">删除</a>
			     </div>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>场地查询</h1>
		</div>
		<form name="spaceQueryForm" id="spaceQueryForm" action="<%=basePath %>Space/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="spaceNo">场地号:</label>
				<input type="text" id="spaceNo" name="spaceNo" value="<%=spaceNo %>" class="form-control" placeholder="请输入场地号">
			</div>
            <div class="form-group">
            	<label for="spaceTypeObj_spaceTypeId">场地类型：</label>
                <select id="spaceTypeObj_spaceTypeId" name="spaceTypeObj.spaceTypeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(SpaceType spaceTypeTemp:spaceTypeList) {
	 					String selected = "";
 					if(spaceTypeObj!=null && spaceTypeObj.getSpaceTypeId()!=null && spaceTypeObj.getSpaceTypeId().intValue()==spaceTypeTemp.getSpaceTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=spaceTypeTemp.getSpaceTypeId() %>" <%=selected %>><%=spaceTypeTemp.getSpaceTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="spaceState">占用状态:</label>
				<input type="text" id="spaceState" name="spaceState" value="<%=spaceState %>" class="form-control" placeholder="请输入占用状态">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="spaceEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;场地信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="spaceEditForm" id="spaceEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="space_spaceNo_edit" class="col-md-3 text-right">场地号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="space_spaceNo_edit" name="space.spaceNo" class="form-control" placeholder="请输入场地号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="space_spaceTypeObj_spaceTypeId_edit" class="col-md-3 text-right">场地类型:</label>
		  	 <div class="col-md-9">
			    <select id="space_spaceTypeObj_spaceTypeId_edit" name="space.spaceTypeObj.spaceTypeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="space_spacePhoto_edit" class="col-md-3 text-right">场地图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="space_spacePhotoImg" border="0px"/><br/>
			    <input type="hidden" id="space_spacePhoto" name="space.spacePhoto"/>
			    <input id="spacePhotoFile" name="spacePhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="space_spacePrice_edit" class="col-md-3 text-right">价格(小时):</label>
		  	 <div class="col-md-9">
			    <input type="text" id="space_spacePrice_edit" name="space.spacePrice" class="form-control" placeholder="请输入价格(小时)">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="space_floorNum_edit" class="col-md-3 text-right">楼层:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="space_floorNum_edit" name="space.floorNum" class="form-control" placeholder="请输入楼层">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="space_spaceState_edit" class="col-md-3 text-right">占用状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="space_spaceState_edit" name="space.spaceState" class="form-control" placeholder="请输入占用状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="space_spaceDesc_edit" class="col-md-3 text-right">场地描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="space.spaceDesc" id="space_spaceDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#spaceEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxSpaceModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var space_spaceDesc_edit = UE.getEditor('space_spaceDesc_edit'); //场地描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.spaceQueryForm.currentPage.value = currentPage;
    document.spaceQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.spaceQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.spaceQueryForm.currentPage.value = pageValue;
    documentspaceQueryForm.submit();
}

/*弹出修改场地界面并初始化数据*/
function spaceEdit(spaceNo) {
	$.ajax({
		url :  basePath + "Space/" + spaceNo + "/update",
		type : "get",
		dataType: "json",
		success : function (space, response, status) {
			if (space) {
				$("#space_spaceNo_edit").val(space.spaceNo);
				$.ajax({
					url: basePath + "SpaceType/listAll",
					type: "get",
					success: function(spaceTypes,response,status) { 
						$("#space_spaceTypeObj_spaceTypeId_edit").empty();
						var html="";
		        		$(spaceTypes).each(function(i,spaceType){
		        			html += "<option value='" + spaceType.spaceTypeId + "'>" + spaceType.spaceTypeName + "</option>";
		        		});
		        		$("#space_spaceTypeObj_spaceTypeId_edit").html(html);
		        		$("#space_spaceTypeObj_spaceTypeId_edit").val(space.spaceTypeObjPri);
					}
				});
				$("#space_spacePhoto").val(space.spacePhoto);
				$("#space_spacePhotoImg").attr("src", basePath +　space.spacePhoto);
				$("#space_spacePrice_edit").val(space.spacePrice);
				$("#space_floorNum_edit").val(space.floorNum);
				$("#space_spaceState_edit").val(space.spaceState);
				space_spaceDesc_edit.setContent(space.spaceDesc, false);
				$('#spaceEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除场地信息*/
function spaceDelete(spaceNo) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Space/deletes",
			data : {
				spaceNos : spaceNo,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#spaceQueryForm").submit();
					//location.href= basePath + "Space/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交场地信息表单给服务器端修改*/
function ajaxSpaceModify() {
	$.ajax({
		url :  basePath + "Space/" + $("#space_spaceNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#spaceEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#spaceQueryForm").submit();
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

})
</script>
</body>
</html>


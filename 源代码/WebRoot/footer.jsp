<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
<!--

-->
.szone-rule {
    width: 100%;
    background-color: #3C736B;
        
}
.dl{
width: 580px;
    margin: 0 auto;
    padding: 30px 0;
    color: #fff;
}
</style>
<!--footer-->
<footer>
    <div class="container szone-rule">
        <div class="row dl">
            <div class="col-md-12 ">
            
				<a href="<%=basePath%>login.jsp"style="color:#FFFFFF;">后台登录</a>
            </div>
        </div>
    </div>
</footer>
<!--footer--> 
<!--footer--> 

 


 
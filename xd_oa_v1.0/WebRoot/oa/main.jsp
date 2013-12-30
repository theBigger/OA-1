<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/WEB-INF/page/backend/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>fengjx OA办公平台</title>
	<jsp:include page="inc/easyui.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			fjx.closeProgress();
		});
	</script>
	
	
</head>
<body id="indexLayout" class="easyui-layout">
	<div href="<%=path %>/oa/view_north.action" data-options="region:'north',border:false" style="height:63px;background:#FFF;"></div>
	<div href="<%=path %>/oa/view_west.action" data-options="region:'west',split:true,title:'导航'" style="width:190px;overflow: hidden;"></div>
	<div href="<%=path %>/oa/view_center.action" data-options="region:'center',title:'当前登录：${sessionScope._const_usersessionkey_.userid}'"></div>
	<div href="<%=path %>/oa/view_east.action" data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
	<div href="<%=path %>/oa/view_south.action" data-options="region:'south',border:false" style="height:25px;padding:5px; text-align: center;">
	</div>
</body>
</html>
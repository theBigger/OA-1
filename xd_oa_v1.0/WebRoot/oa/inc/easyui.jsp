<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link id="easyuiTheme" href="<%=basePath%>res/framework/js/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>res/framework/js/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>res/css/base.css" rel="stylesheet" type="text/css" media="screen">

<script src="<%=basePath%>res/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/framework/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>res/framework/js/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>res/js/changeEasyuiTheme.js" type="text/javascript"></script>
<!-- -->
<script src="<%=basePath%>res/js/fjxUtils.js" type="text/javascript"></script>

<script type="text/javascript">
	//根路径径
	var basePath = '<%=basePath%>';
	$(function(){
		fjx.closeProgress();
	});
</script>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../inc/meta.jsp"></jsp:include>
	<jsp:include page="../inc/easyui.jsp"></jsp:include>
	<script type="text/javascript" src="oa/org/js/org.js"></script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="treegrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>
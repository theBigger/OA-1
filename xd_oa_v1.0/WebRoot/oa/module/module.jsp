<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/oa/org/";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc/meta.jsp"></jsp:include>
<jsp:include page="../inc/easyui.jsp"></jsp:include>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/interface/dict.js"></script>
<script src="oa/module/js/module.js" type="text/javascript" charset="UTF-8"></script>
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
	
	<div id="configDialog" style="display: none;overflow: hidden;">
		<form id="permissionForm" method="post">
			<table class="tableForm" style="width: 450px">
				<tr>
					<td>菜单名称</td>
					<td>
						<input id="module_id" name="module_id" type="hidden" value=""/>
						<input id="module_name" name="module_name" type="text" readonly="readonly" />
					</td>
					<td>操作权限</td>
					<td>
						<select class="easyui-combobox" name="state" style="width:100px;">
					        <option value="AL">查询</option>
					        <option value="AK">添加</option>
					        <option value="AK">修改</option>
					        <option value="AK">删除</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td>访问地址</td>
					<td>
						<input name="name" class="easyui-validatebox" required="true" />
					</td>
					<td>是否启用</td>
					<td>
						<input name="is_valid" type="checkbox" value="1" />
					</td>
				</tr>
				<table id="acl_datagrid"></table>
			</table>
		</form>
	</div>
	
	
	
</body>
</html>
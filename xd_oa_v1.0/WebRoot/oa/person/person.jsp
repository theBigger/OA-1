<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc/meta.jsp"></jsp:include>
<jsp:include page="../inc/easyui.jsp"></jsp:include>
<script src="oa/person/js/person.js" type="text/javascript" charset="UTF-8"></script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset>
				<legend>筛选</legend>
				<table class="tableForm">
					<tr>
						<td>所属机构:<input name="org" style="width: 150px;" />
						</td>
						<td>用户名称:<input name="name" style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<td>创建时间:<input name="createdatetimeStart" class="easyui-datetimebox" editable="false" style="width: 150px;" /></td>
						<td>------------<input name="createdatetimeEnd" class="easyui-datetimebox" editable="false" style="width: 150px;" />
						</td>
						<td><a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a><a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clearFun();" href="javascript:void(0);">清空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<div>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="append();" plain="true" href="javascript:void(0);">增加</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="remove();" plain="true" href="javascript:void(0);">删除</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="edit();" plain="true" href="javascript:void(0);">编辑</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editRole();" plain="true" href="javascript:void(0);">批量更改角色</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="datagrid.datagrid('unselectAll');" plain="true" href="javascript:void(0);">取消选中</a>
			</div>
		</div>
		<table id="datagrid"></table>
	</div>

	<div id="userDialog" style="display: none;overflow: hidden;">
		<form id="userForm" method="post">
			<table class="tableForm">
				<input name="id" type="hidden" value="" />
				<tr>
					<th>所属机构</th>
					<td><input id="org" name="org" readonly="readonly" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<th>姓名</th>
					<td><input name="name" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<th>性别</th>
					<td>
						<select name="sex" class="easyui-validatebox" required="true">
							<option>男</option>
							<option>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>年龄</th>
					<td><input name="age" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<th>电话</th>
					<td><input name="phone_no" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input name="address" type="text" /></td>
				</tr>
				<tr>
					<th>描述</th>
					<td><input name="desc" type="text" style="width: 156px;" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="userRoleDialog" style="display: none;overflow: hidden;">
		<form id="userRoleForm" method="post">
			<table class="tableForm">
				<input type="hidden" name="userIds" />
				<tr>
					<th>所属角色</th>
					<td><select name="roleId" style="width: 156px;"></select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 右键菜单 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>
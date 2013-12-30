<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc/meta.jsp"></jsp:include>
<%@ include file="../inc/easyui.jsp"  %>
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
		<div id="acl_toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset>
				<legend>筛选</legend>
				<table class="tableForm">
					<tr>
						<td>所属机构:<input name="org" />
						</td>
						<td>用户名称:<input name="name"/>
						</td>
						<td>
							<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a>
							<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clearFun();" href="javascript:void(0);">清空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<div>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="append();" plain="true" href="javascript:void(0);">增加</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="remove();" plain="true" href="javascript:void(0);">删除</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="edit();" plain="true" href="javascript:void(0);">编辑</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="cancelEdit();" plain="true" href="javascript:void(0);">取消编辑</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="save();" plain="true" href="javascript:void(0);">保存</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="config();" plain="true" href="javascript:void(0);">权限配置</a> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="datagrid.datagrid('unselectAll');" plain="true" href="javascript:void(0);">取消选中</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editRole();" plain="true" href="javascript:void(0);">刷新</a> 
			</div>
		</div>
		<table id="acl_datagrid"></table>
	</div>
	
	
	
</body>
</html>
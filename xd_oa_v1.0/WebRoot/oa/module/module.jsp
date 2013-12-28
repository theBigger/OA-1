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
		<script type="text/javascript">
	        var aclToolbar = [{
	            text:'Add',
	            iconCls:'icon-add',
	            handler:function(){alert('add')}
	        },{
	            text:'Cut',
	            iconCls:'icon-cut',
	            handler:function(){alert('cut')}
	        },'-',{
	            text:'Save',
	            iconCls:'icon-save',
	            handler:function(){alert('save')}
	        }];
	    </script>
		<table id="acl_datagrid"></table>
	</div>
	
	
	
</body>
</html>
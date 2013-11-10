<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc/meta.jsp"></jsp:include>
<jsp:include page="../inc/easyui.jsp"></jsp:include>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/interface/acl.js"></script>
<script src="oa/role/js/role.js" type="text/javascript" charset="UTF-8"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:true,border:false" style="width:200px">
			<table id="role-datagrid" class="easyui-datagrid" title="角色分配"
	            data-options="
	            	fit:true,
	            	url:'oa/role/role_roleList.action',
	            	method:'post',
	            	toolbar:toolbar,
	            	singleSelect: false,
	            	onClickRow: onClickRoleRow">
		        <thead>
		            <tr>
		                <th data-options="field:'id',checkbox:true">角色ID</th>
		                <th data-options="field:'name',width:162,formatter:formatRole">角色名称</th>
		            </tr>
		        </thead>
		    </table>

        </div>
        <div data-options="region:'center',border:false">
		    <table id="permission-treegrid">
		    </table>
		</div>
		
		<div id="roleDialog" style="display: none;overflow: hidden;width:250px;">
		<form id="roleForm" method="post">
			<table class="tableForm">
				<input type="hidden" name="role.id" value=""/>				
				<tr>
					<th>角色名称</th>
					<td><input name="role.name" class="easyui-validatebox" required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
		
		
    </div>
	
	
	
</body>
</html>
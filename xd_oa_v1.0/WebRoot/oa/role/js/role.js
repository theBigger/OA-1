
/**
 * 角色管理
 */

//查询权限的参数
var perm_params = {
	'role_id':''
};

//角色列表工具栏
var toolbar = [{
    text:'添加',
    iconCls:'icon-add',
    handler:function(){
    	appRole();
    }
},{
    text:'修改',
    iconCls:'icon-edit',
    handler:function(){
    	updatedRole();
    }
},{
    text:'删除',
    iconCls:'icon-remove',
    handler:function(){
		removeRole();
	}
}];

var role_datagrid;
//获取角色权限列表的参数对象（json）
var permi_datagrid;
//角色
var roleDialog;
//添加 / 修改角色
var roleForm;

$(function(){
	
	role_datagrid = $("#role-datagrid").datagrid();
	
	roleForm = $('#roleForm').form();
	roleDialog = $('#roleDialog').show().dialog({
			modal : true,
			buttons : [ {
				text : '确定',
				handler : function() {
					roleForm.form('submit', {
						url : basePath + 'oa/role/role_addOrUpdateRole.action',
						success : function(data) {
							roleDialog.dialog('close');
							if('success' == data){
								$.messager.alert(
									'提示',
									'用户编辑成功！',
									'info'
								);
							}else{
								$.messager.alert(
									'提示',
									'用户编辑失败！',
									'error'
								);
							}
							$("#role-datagrid").datagrid('reload');
						}
					});
				}
			} ]
		}).dialog('close');
	
	
	permi_datagrid = $("#permission-treegrid").treegrid({
		method:'get',
		url: basePath + 'oa/module/module_tree.action',
		queryParams:perm_params,
		fit : true,
		fitColumns : true,
		rownumbers : true,		//是否显示行号
		idField : 'id',
		treeField : 'text',
		frozenColumns : [ [ {
				field : 'id',
				title : '资源ID',
				hidden : true
			}, {
				field : 'text',
				title : '资源名称',
				width : 150
			} ] ],
			columns : [ [ {
				field : 'x1',
				title : '权限',
				width : 250,
				formatter : function(value,rowData, rowIndex) {
					var check_str = 
					'<input type="checkbox" id="'+rowData.id+'_R" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="1">查询 | '+
					'<input type="checkbox" id="'+rowData.id+'_C" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="0">添加 | '+
					'<input type="checkbox" id="'+rowData.id+'_U" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="2">修改 | '+
					'<input type="checkbox" id="'+rowData.id+'_D" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="3">删除';
					return check_str;
				}
			}, 
			/*{
				field : 'x2',
				title : '继承',
				width : 50,
				formatter : function(value,rowData, rowIndex) {
					return '<input type="checkbox" id="'+rowData.id+'_EXT" onclick="addOrUpdateExtends(this)" moduleId="'+rowData.id+'">';
				}
			},*/
			{
				field : 'x3',
				title : '启用 ',
				width : 100,
				formatter : function(value,rowData, rowIndex) {
					return '<input type="checkbox" id="'+rowData.id+'_USE" onclick="usePermission(this)" moduleId="'+rowData.id+'">';
				}
			}]],
		onBeforeLoad:function(){
			if(perm_params.role_id == ''){
				return false;
			}
			return true;
		},
		onLoadSuccess:function(){
			initAcl(perm_params.role_id);
		}
	});
});

//格式化角色列表名称，加入手型效果
function formatRole(val,row,rowIndex){
	return '<span style="cursor:pointer;">'+val+'</span>';
}


function initAcl (role_id){
	acl.searchAclRecord(
		"role",
		role_id,
		function(datas){
			for(var i=0; i < datas.length; i++){
				var moduleId = datas[i].moduleId;
				var cState = datas[i].cState;
				var rState = datas[i].rState;
				var uState = datas[i].uState;
				var dState = datas[i].dState;
				var extState = datas[i].extState;
				
				$("#"+moduleId+"_C").attr('checked', cState == 0 ? false : true);
				$("#"+moduleId+"_R").attr('checked', rState == 0 ? false : true);
				$("#"+moduleId+"_U").attr('checked', uState == 0 ? false : true);
				$("#"+moduleId+"_D").attr('checked', dState == 0 ? false : true);
				//$("#"+moduleId+"_EXT").attr('checked', extState == 0 ? false : true);
			}
		}
	);
}
//授权
function addOrUpdatePermission(field){
	dwr.engine.setAsync(false);
	
	var jq_field = $(field);
	//如果被选择上，则同时选择其"不继承"和"启用"checkbox
	if(field.checked){
		$("#"+jq_field.attr('moduleId')+"_USE").attr('checked',true);
	}

	acl.addOrUpdatePermission(
		"role",
		perm_params.role_id,
		jq_field.attr('moduleId'),
		jq_field.attr('permission'),
		field.checked
	);
}

//点击启用checkbox
function usePermission(field){
	//如果checkbox被选中，意味着需要更新ACL的状态
	//更新C/R/U/D以及Extends状态
	
	//设置为同步方式，以便DWR依次发出下列请求
	dwr.engine.setAsync(false);
	var jq_field = $(field);
	if(field.checked){
		addOrUpdatePermission($("#"+jq_field.attr('moduleId')+"_C")[0]);
		addOrUpdatePermission($("#"+jq_field.attr('moduleId')+"_R")[0]);
		addOrUpdatePermission($("#"+jq_field.attr('moduleId')+"_U")[0]);
		addOrUpdatePermission($("#"+jq_field.attr('moduleId')+"_D")[0]);
	}else{
		aclManager.delPermission(
			"role",
			perm_params.role_id,
			jq_field.attr('moduleId')	
		);
		$(field.moduleId+"_C").checked = false;
		$(field.moduleId+"_R").checked = false;
		$(field.moduleId+"_U").checked = false;
		$(field.moduleId+"_D").checked = false;
	}
}



//设置用户的继承特性
function addOrUpdateExtends(field){
	var jq_field = $(field);
	acl.addOrUpdateUserExtends(
		perm_params.role_id,
		jq_field.attr('moduleId'),
		!field.checked
	);
}


/**
 * 点击角色名称后加载该角色的权限列表
 * @param rowIndex	被点击行的索引，从 0 开始
 * @param rowData	被点击行对应的记录
 */
function onClickRoleRow(rowIndex, rowData){
	role_datagrid.datagrid("selectRow",rowIndex);
	perm_params = {'role_id':rowData.id};
	permi_datagrid.treegrid("reload").treegrid({
		title:rowData.name
	});
}

/**
 * 添加角色
 */
function appRole() {
	roleDialog.dialog('open').dialog({
		title : '添加角色'
	});
	roleForm.form('clear');
}
/**
 * 修改角色名称
 */
function updatedRole() {
	
	var rows = role_datagrid.datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {
			var names = [];
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			$.messager.show({
				msg : '只能择一个用户进行编辑！您已经选择了【' + names.join(',') + '】' + rows.length + '个用户',
				title : '提示'
			});
		} else if (rows.length == 1) {
			roleDialog.dialog('open').dialog({
				title : '修改角色'
			});
			roleForm.form('clear');
			roleForm.form('load', {
				'role.id' : rows[0].id,
				'role.name' : rows[0].name
			});
		}
}
/**
 * 删除角色
 */
function removeRole() {
	var rows = role_datagrid.datagrid('getSelections');
	if (rows.length > 0) {
		$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
			if (r) {
				var params = '';
				for ( var i = 0; i < rows.length; i++) {
					params += 'ids='+rows[i].id+'&';
				}
				$.ajax({
					url :  basePath + 'oa/role/role_deleteRole.action',
					data : params,
					cache : false,
					dataType : "text",
					success : function(res) {
						if('success' == res){
							$.messager.alert(
								'提示',
								'角色删除完成！',
								'info'
							);
						}else{
							$.messager.alert(
								'提示',
								'角色删除失败！',
								'error'
							);
						}
						$("#role-datagrid").datagrid('reload');
					}
				});
			}
		});
	} else {
		$.messager.alert('提示', '请选择要删除的记录！', 'warning');
	}
}


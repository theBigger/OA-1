
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
				width : 250
			} ] ],
			columns : [ [ {
				field : 'x1',
				title : '权限',
				width : 250,
				formatter : function(value,rowData, rowIndex) {
					var check_str = 
					'<input type="checkbox" id="'+rowData.id+'_C" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="0">C'+
					'<input type="checkbox" id="'+rowData.id+'_R" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="1">R'+
					'<input type="checkbox" id="'+rowData.id+'_U" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="2">U'+
					'<input type="checkbox" id="'+rowData.id+'_D" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'" permission="3">D';
					return check_str;
				}
			},  {
				field : 'x2',
				title : '继承',
				width : 50,
				formatter : function(value,rowData, rowIndex) {
					return '<input type="checkbox" id="'+rowData.id+'_EXT" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'">';
				}
			}, {
				field : 'x3',
				title : '启用',
				width : 50,
				formatter : function(value,rowData, rowIndex) {
					return '<input type="checkbox" id="'+rowData.id+'_USE" onclick="addOrUpdatePermission(this)" moduleId="'+rowData.id+'">';
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
				$("#"+moduleId+"_EXT").attr('checked', extState == 0 ? false : true);
			}
		}
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


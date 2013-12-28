/*
*模块管理
*/
var editRow;
var treegrid;
var configDialog;
var acl_datagrid;
var module_id;

var iconData = [ {
	iconcls : '',
	text : '默认图标'
}, {
	iconcls : 'icon-add',
	text : 'icon-add'
}, {
	iconcls : 'icon-edit',
	text : 'icon-edit'
}, {
	iconcls : 'icon-remove',
	text : 'icon-remove'
}, {
	iconcls : 'icon-save',
	text : 'icon-save'
}, {
	iconcls : 'icon-cut',
	text : 'icon-cut'
}, {
	iconcls : 'icon-ok',
	text : 'icon-ok'
}, {
	iconcls : 'icon-no',
	text : 'icon-no'
}, {
	iconcls : 'icon-cancel',
	text : 'icon-cancel'
}, {
	iconcls : 'icon-reload',
	text : 'icon-reload'
}, {
	iconcls : 'icon-search',
	text : 'icon-search'
}, {
	iconcls : 'icon-print',
	text : 'icon-print'
}, {
	iconcls : 'icon-help',
	text : 'icon-help'
}, {
	iconcls : 'icon-undo',
	text : 'icon-undo'
}, {
	iconcls : 'icon-redo',
	text : 'icon-redo'
}, {
	iconcls : 'icon-back',
	text : 'icon-back'
}, {
	iconcls : 'icon-sum',
	text : 'icon-sum'
}, {
	iconcls : 'icon-tip',
	text : 'icon-tip'
} ];
$(function() {

	treegrid = $('#treegrid').treegrid({
		url : 'oa/module/module_treeGrid.action',
		toolbar : [ {
			text : '展开',
			iconCls : 'icon-redo',
			handler : function() {
				var node = treegrid.treegrid('getSelected');
				if (node) {
					treegrid.treegrid('expandAll', node.id);
				} else {
					treegrid.treegrid('expandAll');
				}
			}
		}, '-', {
			text : '折叠',
			iconCls : 'icon-undo',
			handler : function() {
				var node = treegrid.treegrid('getSelected');
				if (node) {
					treegrid.treegrid('collapseAll', node.id);
				} else {
					treegrid.treegrid('collapseAll');
				}
			}
		}, '-', {
			text : '刷新',
			iconCls : 'icon-reload',
			handler : function() {
				editRow = undefined;
				treegrid.treegrid('reload');
			}
		}, '-', {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				append();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				remove();
			}
		}, '-', {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}, '-', {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				if (editRow) {
					treegrid.treegrid('endEdit', editRow.id);
				}
			}
		},'-', {
			text : '权限配置',
			iconCls : 'icon-edit',
			handler : function() {
				config();
			}
		}, '-', {
			text : '取消编辑',
			iconCls : 'icon-undo',
			handler : function() {
				if (editRow) {
					treegrid.treegrid('cancelEdit', editRow.id);
					editRow = undefined;
				}
			}
		}, '-', {
			text : '取消选中',
			iconCls : 'icon-undo',
			handler : function() {
				treegrid.treegrid('unselectAll');
			}
		}, '-' ],
		fit : true,
		fitColumns : true,
		rownumbers : true,		//是否显示行号
		idField : 'id',
		treeField : 'name',
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			hidden : true
		}, {
			field : 'name',
			title : '菜单名称',
			width : 200,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		} ] ],
		columns : [ [ {
			field : 'sn',
			title : '模块标识',
			width : 150,
			editor : {
				type : 'combobox',
				options : {
					valueField : 'iconcls',
					textField : 'text',
					panelHeight : '200',
					data : iconData,
					formatter : function(v) {
						return fjx.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.iconcls, v.text);
					}
				}
			}
		}, {
			field : 'url',
			title : '菜单地址',
			width : 200,
			editor : {
				type : 'text'
			}
		}, {
			field : 'order_no',
			title : '排序',
			width : 50,
			editor : {
				type : 'numberbox',
				options : {
					min : 0,
					max : 999,
					required : true
				}
			}
		}, {
			field : 'parent_id',
			title : '上级菜单',
			width : 200,
			formatter : function(value, rowData, rowIndex) {
				return rowData.parent_name;
			},
			editor : {
				type : 'combotree',
				options : {
					url : 'oa/module/module_tree.action',
					animate : false,
					lines : !fjx.isLessThanIe8(),		//Defines if to show tree lines.
					onLoadSuccess : function(node, data) {
						var t = $(this);
						if (data) {
							$(data).each(function(index, d) {
								if (this.state == 'closed') {
									t.tree('expandAll');
								}
							});
						}
					}
				}
			}
		}, {
			field : 'parent_name',
			title : '上级菜单',
			width : 80,
			hidden : true
		} ] ],
		onDblClickRow : function(row) {
			if (editRow) {
				$.messager.show({
					msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
					title : '提示'
				});
			} else {
				treegrid.treegrid('beginEdit', row.id);
				editRow = row;
			}
		},
		onAfterEdit : function(row, changes) {
			if (row.parentId != row.id) {
				$.ajax({
					url : 'menuController.do?edit',
					data : row,
					cache : false,
					dataType : "json",
					success : function(r) {
						if (r.success) {
							$.messager.show({
								msg : r.msg,
								title : '提示'
							});
							treegrid.treegrid('reload');
							parent.tree.tree('reload');
							editRow = undefined;
						} else {
							$.messager.show({
								msg : '保存菜单失败!',
								title : '提示'
							});
						}
					}
				});
			} else {
				$.messager.show({
					msg : '保存失败，上级菜单不可以是自己!',
					title : '提示'
				});
			}
		},
		onContextMenu : function(e, row) {
			e.preventDefault();
			$(this).treegrid('unselectAll');
			$(this).treegrid('select', row.id);
			$('#menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		},
		onLoadSuccess : function(row, data) {
			fjx.closeProgress();
		},
		onExpand : function(row) {
			treegrid.treegrid('unselectAll');
		},
		onCollapse : function(row) {
			treegrid.treegrid('unselectAll');
		}
	});
	
	configDialog = $('#configDialog').show().dialog({
		modal : true,
		buttons : [ {
			text : '确定',
			handler : function() {
				if (permissionForm.find('[name=id]').val() != '') {
					permissionForm.form('submit', {
						url : '',
						success : function(data) {
							userDialog.dialog('close');
							$.messager.show({
								msg : '用户编辑成功！',
								title : '提示'
							});
							datagrid.datagrid('reload');
						}
					});
				} else {
					permissionForm.form('submit', {
						url : '',
						success : function(data) {
							try {
								var d = $.parseJSON(data);
								if (d) {
									userDialog.dialog('close');
									$.messager.show({
										msg : '用户创建成功！',
										title : '提示'
									});
									datagrid.datagrid('reload');
								}
							} catch (e) {
								$.messager.show({
									msg : '用户名称已存在！',
									title : '提示'
								});
							}
						}
					});
				}
			}
		} ]
	}).dialog('close');
	
	
});

function edit() {
	if (editRow) {
		$.messager.show({
			msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
			title : '提示'
		});
	} else {
		var node = treegrid.treegrid('getSelected');
		if (node && node.id) {
			treegrid.treegrid('beginEdit', node.id);
			editRow = node;
		}
	}
}
function append() {
	if (editRow) {
		$.messager.show({
			msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
			title : '提示'
		});
	} else {
		var node = treegrid.treegrid('getSelected');
		var data = [ {
			id : fjx.UUID(),
			text : '新菜单',
			src : '',
			seq : 999,
			parentId : (node ? node.id : '')
		} ];
		var opts = {
			parent : data[0].parentId,
			data : data
		};
		$.ajax({
			url : 'menuController.do?add',
			data : data[0],
			cache : false,
			dataType : "json",
			success : function(r) {
				if (r.success) {
					treegrid.treegrid('append', opts);

					treegrid.treegrid('beginEdit', data[0].id);
					editRow = data[0];
				} else {
					$.messager.show({
						msg : '添加菜单失败!',
						title : '提示'
					});
				}
			}
		});
	}
}
function remove() {
	var node = treegrid.treegrid('getSelected');
	if (node) {
		if (node.id == '0') {
			$.messager.show({
				msg : '不能删除根节点!',
				title : '提示'
			});
			return;
		}
		$.messager.confirm('询问', '您确定要删除【' + node.text + '】菜单？', function(b) {
			if (b) {
				$.ajax({
					url : 'menuController.do?del',
					data : {
						id : node.id
					},
					cache : false,
					dataType : "json",
					success : function(r) {
						if (r.success) {
							treegrid.treegrid('reload');
							parent.tree.tree('reload');
							$.messager.show({
								msg : r.msg,
								title : '提示'
							});
							editRow = undefined;
						} else {
							$.messager.show({
								msg : '删除菜单失败!',
								title : '提示'
							});
						}
					}
				});
			}
		});
	}
}

//打开权限配置窗口
function config() {
	if (editRow) {
		$.messager.show({
			msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
			title : '提示'
		});
	} else {
		var node = treegrid.treegrid('getSelected');
		if (node && node.id) {
			$('#module_id').val(node.id);
			$('#module_name').val(node.name);
			load_acl_datagrid(node.id);
			configDialog.dialog({title: '【'+node.name+'】权限配置'}).dialog('open');
		}else{
			$.messager.show({
				msg : '请选择要设置权限的菜单！',
				title : '提示'
			});
		}
	}
}

function load_acl_datagrid(module_id){
	
	acl_datagrid = $('#datagrid').datagrid({
		url : basePath + 'oa/person/person_query_page.action',
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				append();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				remove();
			}
		}, '-', {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}, '-', {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				if (editRow) {
					acl_datagrid.datagrid('endEdit', editRow.id);
				}
			}
		},'-', {
			text : '权限配置',
			iconCls : 'icon-edit',
			handler : function() {
				config();
			}
		}, '-', {
			text : '取消编辑',
			iconCls : 'icon-undo',
			handler : function() {
				if (editRow) {
					acl_datagrid.datagrid('cancelEdit', editRow.id);
					editRow = undefined;
				}
			}
		}, '-', {
			text : '取消选中',
			iconCls : 'icon-undo',
			handler : function() {
				acl_datagrid.datagrid('unselectAll');
			}
		}, '-', {
			text : '刷新',
			iconCls : 'icon-reload',
			handler : function() {
				editRow = undefined;
				acl_datagrid.datagrid('reload');
			}
		}, '-'],
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40],
		fit : true,
		fitColumns : true,
		nowrap : false,
		border : false,
		idField : 'id',
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			checkbox : true
		}, {
			field : 'name',
			title : '用户名称',
			width : 100,
			sortable : true
		} ] ],
		columns : [ [ {
			field : 'sex',
			title : '性别',
			width : 100,
			formatter : function(value, rowData, rowIndex) {
				var sex = '女';
				if(value == '1'){
					sex = '男'
				}
				return sex;
			}
		}, {
			field : 'duty',
			title : '职务',
			width : 150,
			sortable : true
		}, {
			field : 'phone',
			title : '电话',
			width : 150,
			sortable : true
		}, {
			field : 'address',
			title : '地址',
			width : 150,
			sortable : true
		}, {
			field : 'org_name',
			title : '机构',
			width : 200
		}, {
			field : 'org_id',
			title : '机构',
			width : 200,
			hidden : true
		} ] ],
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
	
	
}

function onAclBeforeLoad(){
	alert(sss);
	return false;
}
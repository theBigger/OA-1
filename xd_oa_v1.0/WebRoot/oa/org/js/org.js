/**
 * 
 * @type 
 */

var editRow;
	var treegrid;
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
		fjx.closeProgress();
		treegrid = $('#treegrid').treegrid({
			url : 'org_treeGrid.action',
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
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : true,
			nowrap : true,			//True 就会把数据显示在一行里。
			animate : true,			//是否显示动画
			border : false,			
			cascadeCheck : true,	//是否级联检查
			rownumbers : true,		//是否显示行号
			dnd : true,				//是否支持拖拽
			idField : 'id',
			treeField : 'name',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			}, {
				field : 'name',
				title : '机构名称',
				width : 150,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [ {
				field : 'sn',
				title : '机构编号',
				width : 70,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			}, {
				field : 'description',
				title : '机构简介',
				width : 200,
				editor : {
					type : 'text'
				}
			}, {
				field : 'in_time',
				title : '成立时间',
				width : 150,
				editor : {
					type : 'datebox',
					options : {
						required : true
					}
				}
			}, {
				field : 'parent_id',
				title : '上级机构',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return rowData.parent_name;
				},
				editor : {
					type : 'combotree',
					options : {
						url : 'org_tree.action',
						animate : false,
						lines : !fjx.isLessThanIe8(),
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
			}] ],
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
				if (row.parent_id != row.id) {
					
					var data = {
						"org.id":row.id,
						"org.name" : row.name,
						"org.sn" : row.sn,
						"org.description" : row.description,
						"org.in_time" : row.in_time,
						"pid" : row.parent_id
					};
					$.ajax({
						url : 'org_save.action',
						data : data,
						cache : false,
						dataType : "text",
						success : function(r) {
							if (r = 'success') {
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
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});

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
			var data = [{
				name : '新菜单',
				sn : '',
				description : '',
				in_time : '',
				parent_id : (node ? node.id : '')
			}];
			var opts = {
				parent : data[0].parent_id,
				data : data
			};
			treegrid.treegrid('append', opts);
			treegrid.treegrid('beginEdit', data[0].id);
			editRow = data[0];
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
			$.messager.confirm('询问', '您确定要删除【' + node.name + '】机构？', function(b) {
				if (b) {
					$.ajax({
						url : 'org_delete.action',
						data : {
							id : node.id
						},
						cache : false,
						dataType : "text",
						success : function(r) {
							if (r = 'success') {
								treegrid.treegrid('reload');
								parent.tree.tree('reload');
								$.messager.show({
									msg : '删除菜单成功!',
									title : '提示'
								});
								editRow = undefined;
							} else {
								$.messager.alert({
									icon:'error',
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
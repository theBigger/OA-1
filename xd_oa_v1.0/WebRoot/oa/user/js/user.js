/**
 * 用户管理
 * 2013年11月1日
 * fengjx
 */

var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		userForm = $('#userForm').form();
		passwordInput = userForm.find('[name=password]').validatebox({
			required : true
		});

		$('[name=roleId]').combotree({
			url : 'roleController.do?tree',
			animate : false,
			lines : !fjx.isLessThanIe8(),
			checkbox : true,
			multiple : true,
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
		});

		userRoleDialog = $('#userRoleDialog').show().dialog({
			modal : true,
			title : '批量编辑用户角色',
			buttons : [ {
				text : '编辑',
				handler : function() {
					userRoleForm.submit();
				}
			} ]
		}).dialog('close');

		userRoleForm = $('#userRoleForm').form({
			url : 'userController.do?editUsersRole',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d) {
					userRoleDialog.dialog('close');
					$.messager.show({
						msg : '批量修改用户角色成功！',
						title : '提示'
					});
					datagrid.datagrid('reload');
				}
			}
		});

		userDialog = $('#userDialog').show().dialog({
			modal : true,
			title : '用户信息',
			buttons : [ {
				text : '确定',
				handler : function() {
					if (userForm.find('[name=id]').val() != '') {
						userForm.form('submit', {
							url : 'userController.do?edit',
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
						userForm.form('submit', {
							url : 'userController.do?add',
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

		datagrid = $('#datagrid').datagrid({
			url : 'oa/user/user_query_page.action',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30],
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
			},{
				field : 'name',
				title : '姓名',
				width : 100,
				sortable : true
			}, {
				field : 'username',
				title : '登陆账号',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'password',
				title : '密码',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '******';
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'expireTime',
				title : '失效时间',
				width : 150,
				sortable : true
			}, {
				field : 'x1',
				title : '状态',
				width : 200,
				formatter : function(value, rowData, rowIndex) {
					var str = '<a href="#" class="easyui-linkbutton">分配账号</a>';
					return str;
				}
			}] ],
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

	});

	function append() {
		userDialog.dialog('open');
		passwordInput.validatebox({
			required : true
		});
		userForm.find('[name=name]').removeAttr('readonly');
		userForm.form('clear');
	}

	function edit() {
		var rows = datagrid.datagrid('getSelections');
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
			passwordInput.validatebox({
				required : false
			});
			userForm.find('[name=name]').attr('readonly', 'readonly');
			userDialog.dialog('open');
			userForm.form('clear');
			userForm.form('load', {
				id : rows[0].id,
				name : rows[0].name,
				password : '',
				createdatetime : rows[0].createdatetime,
				modifydatetime : rows[0].modifydatetime,
				roleId : fjx.getList(rows[0].roleId)
			});
		}
	}

	function editRole() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			userRoleForm.find('input[name=userIds]').val(ids.join(','));
			userRoleDialog.dialog('open');
		} else {
			$.messager.alert('提示', '请选择要编辑的记录！', 'error');
		}
	}

	function remove() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : 'userController.do?del',
						data : {
							ids : ids.join(',')
						},
						cache : false,
						dataType : "json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							$.messager.show({
								title : '提示',
								msg : '删除成功！'
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}

	function searchFun() {
		datagrid.datagrid('load', {
			name : $('#toolbar input[name=name]').val(),
			createdatetimeStart : $('#toolbar input[comboname=createdatetimeStart]').datetimebox('getValue'),
			createdatetimeEnd : $('#toolbar input[comboname=createdatetimeEnd]').datetimebox('getValue'),
			modifydatetimeStart : $('#toolbar input[comboname=modifydatetimeStart]').datetimebox('getValue'),
			modifydatetimeEnd : $('#toolbar input[comboname=modifydatetimeEnd]').datetimebox('getValue')
		});
	}
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
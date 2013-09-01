<%@ page language="java" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" charset="UTF-8"><!--
	var tree ;
	$(function(){
		
		tree = $("#tree").tree({
			url: "<%=path%>/oa/config/menu.json",
			lines: true,
			dnd:true,
			animate:true,
			onClick: function(node){
				if (node.attributes && node.attributes.src && node.attributes.src != '') {
					var href;
					if (/^\//.test(node.attributes.src)) {/*以"/"符号开头的,说明是本项目地址*/
						href = "<%=path%>"+node.attributes.src;
						//$.messager.progress({
						//	text : '请求数据中，请稍等....',
						//	cancel: "取消",
						//	interval : 100
						//});
					} else {
						href = node.attributes.src;
					}
					addTab({
						src : href,
						title : node.text
					});
				} else {
					if(node.state == 'open'){
						tree.tree('collapse',node.target);
					}else{
						tree.tree('expand',node.target);
					}
				}
			},
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('collapseAll');
						}
					});
				}
			}
		});
		
	});
		
	function collapseAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('collapseAll', node.target);
		} else {
			tree.tree('collapseAll');
		}
	}
	function expandAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('expandAll', node.target);
		} else {
			tree.tree('expandAll');
		}
	}
</script>
<div class="easyui-panel" fit="true" border="false">
	<div class="easyui-accordion" fit="true" border="false">
		<div title="系统菜单" iconCls="icon-tip">
			<div class="easyui-layout" fit="true">
				<div region="north" border="false" style="overflow: hidden;">
					<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="expandAll();">展开</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="collapseAll();">折叠</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-reload" onclick="tree.tree('reload');">刷新</a>
					<hr style="border-color: #fff;" />
				</div>
				<div region="center" border="false">
					<ul id="tree" style="margin-top: 5px;">
				</div>
			</div>
		</div>
		<!-- 
		<div title="控制面板" href="test/easyuidemo.html"></div>
		 -->
	</div>
</div>
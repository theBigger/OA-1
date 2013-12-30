<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" charset="UTF-8">
	function logout(b) {
		window.location.href = '<%=path %>/store/store_loginOut.action';
	}
	function returnIndex(){
		window.location.href = '<%=path %>/hkunicom/jsp/portal/page_1.jsp';
	}
	
	//function userInfo() {
	//	addTabFun({
	//		src : 'userController.do?userInfo',
	//		title : '个人信息'
	//	});
	//}
</script>
<div>
	<img alt="" src="<%=path %>/res/images/fengjx.gif">
</div>

<div style="position: absolute; right: 0px; bottom: 0px; ">
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a>
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-back">操作</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="userInfo();">个人信息</div>
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 100px;">
			<div onclick="fjx.changeTheme('default');">default</div>
			<div onclick="fjx.changeTheme('bootstrap');">bootstrap</div>
			<div onclick="fjx.changeTheme('gray');">gray</div>
			<div onclick="fjx.changeTheme('metro');">metro</div>
			<div onclick="fjx.changeTheme('black');">black</div>
		</div>
	</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="loginAndRegDialog.dialog('open');">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logout();">重新登录</div>
	<div onclick="returnIndex();">返回首頁</div>
	<div onclick="logout(true);">退出系统</div>
</div>
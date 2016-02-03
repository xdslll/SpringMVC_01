<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if(request.getSession().getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
</head>
<body>
<div style="margin:5px 5px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
</div>
<table id="dg" title="用户列表" class="easyui-datagrid" style="width:100%;height:500px;"
       data-options="
                url: '<%=request.getContextPath()%>/user/get',
                method: 'get',
                idField: 'id',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: true,
                iconCls: 'icon-man',
                singleSelect: true,
                pagination: false
                ">
    <thead>
    <tr>
        <th data-options="field:'username'" width="20%">用户名</th>
        <th data-options="field:'name'" width="10%">姓名</th>
        <th data-options="field:'roleId'" width="10%">角色</th>
        <th data-options="field:'orgId'" width="30%">机构</th>
        <th data-options="field:'email'" width="30%">邮箱</th>
    </tr>
    </thead>
</table>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写用户信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>用户名:</label>
            <input name="username" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>密码:</label>
            <input name="password" class="easyui-textbox" required="true" type="password">
        </div>
        <div class="fitem">
            <label>姓名:</label>
            <input name="name" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>角色:</label>
            <input name="roleId" class="easyui-textbox" style="width: 90px;" id="role_id" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectRole();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>机构:</label>
            <input name="orgId" class="easyui-textbox" style="width: 90px;" id="org_id" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectOrg();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>邮箱:</label>
            <input name="email" class="easyui-textbox">
        </div>
        <div class="fitem">
            <input name="id" type="hidden">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<script>
    var url;

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增用户');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/user/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑用户');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/user/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该用户吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/user/delete?userId=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
                        if (result.errorMsg) {
                            $.messager.alert('操作失败', result, 'error');
                        } else {
                            $('#dg').datagrid('reload');
                        }
                    })
                }
            });
        }
    }

    //保存新增和编辑菜单的结果
    function save() {
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.alert('操作失败', result.errorMsg, 'error');
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    //显示选择父结点的对话框
    function showSelectRole() {
        $('#dlg_select_parent').dialog('open').dialog('center').dialog('setTitle','选择父节点');
    }

    //选择父结点
    function selectParent() {
        var row = $('#dlg_select_parent_grid').datagrid('getSelected');
        var id = row.id;
        $("#parent_id").textbox('setValue', id);
        $('#dlg_select_parent').dialog('close');
    }

    function showSelectOrg() {

    }
</script>
</body>
</html>

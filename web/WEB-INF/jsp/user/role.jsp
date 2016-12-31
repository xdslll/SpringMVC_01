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
<table id="dg" title="角色列表" class="easyui-datagrid" style="width:100%;height:500px;"
       data-options="
                url: '<%=request.getContextPath()%>/role/get',
                method: 'get',
                idField: 'id',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: true,
                iconCls: 'icon-ok',
                pagination: false,
                singleSelect: true,
                ">
    <thead>
    <tr>
        <th data-options="field:'id'" width="33%">ID</th>
        <th data-options="field:'name'" width="33%">角色名称</th>
        <th data-options="field:'areaName'" width="33%">区域名称</th>
    </tr>
    </thead>
</table>
<div id="dlg" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写角色信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>名称:</label>
            <input name="name" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>区域:</label>
            <input name="areaName" class="easyui-textbox" style="width: 90px;" id="area_name" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectArea();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>权限:</label>
            <ul id="menu" class="easyui-tree"
                data-options="
                    url: '<%=request.getContextPath()%>/menu/get',
                    method: 'get',
                    animate: true,
                    checkbox: true
                ">
            </ul>
        </div>
        <div class="fitem">
            <input name="id" type="hidden">
            <input name="menu_ids" type="hidden" id="menu_ids">
            <input name="areaId" type="hidden" id="area_id">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<!=============================
    展示区域窗口
==============================!>
<div id="dlg_select_area" class="easyui-dialog" style="width:400px;height:400px;"
     closed="true" buttons="#dlg_select_area_buttons">
    <table id="dg_select_area" class="easyui-datagrid" style="width:100%;height:100%;"
           data-options="
            url: '<%=request.getContextPath()%>/area/get3',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: false,
            toolbar: '#dlg_select_area_buttons',
            border: false,
            fit: true,
            nowrap: false,
            striped: true,
            ">
        <thead>
        <tr>
            <th data-options="field:'id'" width="50%">区域ID</th>
            <th data-options="field:'name'" width="50%">区域名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dlg_select_area_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="selectArea()" style="width:90px">选择</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_select_area').dialog('close')" style="width:90px">取消</a>
</div>
<script>
    var url;

    function selectArea() {
        var rows = $('#dg_select_area').datagrid('getSelections');
        if (rows) {
            var areaIds = '', areaNames = '';
            for(var i=0; i<rows.length; i++) {
                var row = rows[i];
                var areaId = row.id;
                var areaName = row.name;
                if (i == rows.length - 1) {
                    areaIds += areaId;
                    areaNames += areaName;
                } else {
                    areaIds += areaId + ",";
                    areaNames += areaName + ",";
                }
            }
            //$("#area_id").textbox('setValue', areaId);
            $("#area_id").val(areaIds);
            $("#area_name").textbox('setValue', areaNames);
            $('#dlg_select_area').dialog('close');
        }
    }

    //显示选择父结点的对话框
    function showSelectArea() {
        $('#dlg_select_area').dialog('open').dialog('center').dialog('setTitle','选择角色对应的区域');
        $("#area_id").css('display', 'none');
    }

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增角色');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/role/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑角色');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/role/update';
            var roleMenuUrl = '<%=request.getContextPath()%>/role_menu/get?roleId=' + row.id;
            $.get(roleMenuUrl, function(result) {
                var menus = eval('('+result+')');
                var children = $('#menu').tree('getChildren');
                for (var i = 0; i < children.length; i++) {
                    var menuId = children[i].id;
                    var menuId2 = menus[0].children[0].id;
                    for (var j = 0; j < menus.length; j++) {
                        var childrenMenu = menus[j].children;
                        for (var k = 0; k < childrenMenu.length; k++) {
                            if (childrenMenu[k].id == menuId) {
                                $('#menu').tree('check', children[i].target);
                            }
                        }
                    }
                }
            });
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该角色吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/role/delete?roleId=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
                        if (result.code != '0') {
                            $.messager.alert('操作失败', result.msg, 'error');
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
        var menu_ids = getSelectedMenu();
        $("#menu_ids").val(menu_ids);
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code != '0'){
                    $.messager.alert('操作失败', result.msg, 'error');
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    /**
     * 获取选中的菜单
     */
    function getSelectedMenu() {
        var nodes = $('#menu').tree('getChecked');
        var s = '';
        for(var i=0; i < nodes.length; i++){
            if (s != '') s += ',';
            s += nodes[i].id;
        }
        return s;
    }
</script>
</body>
</html>

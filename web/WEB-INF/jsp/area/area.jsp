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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body>
<div id="toolbar">
    <input class="easyui-textbox" data-options="prompt:'请输入区域名称'" style="width:25%;height:28px;" id="search_name">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchArea()"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-lock'" onclick="viewDlg()">查看已绑定门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-link'" onclick="bindDlg()">查看未绑定门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-link'" onclick="ifShow()">是否加入统计</a>
</div>
<!=============================
    区域信息列表
==============================!>
<div class="main_table">
    <table id="dg" title="区域列表" class="easyui-datagrid"
           data-options="
                    url: '<%=request.getContextPath()%>/area/get2',
                    method: 'get',
                    idField: 'id',
                    showFooter: true,
                    fitColumns: true,
                    collapsible: false,
                    animate: true,
                    rownumbers: true,
                    iconCls: 'icon-ok',
                    pagination: true,
                    singleSelect: true,
                    pageSize: 20,
                    pageList: [20, 40, 60],
                    toolbar: '#toolbar',
                    border: false,
                    fit: true,
                    nowrap: false,
                    striped: true,
                    ">
        <thead>
        <tr>
            <th data-options="field:'name'" width="50%">区域名称</th>
            <th data-options="field:'ifShowText'" width="50%">是否加入统计</th>
        </tr>
        </thead>
    </table>
</div>

<!=============================
    新增区域信息
==============================!>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写区域信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>区域名称:</label>
            <input name="name" class="easyui-textbox" required="true">
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
<!=============================
    已绑定部门信息
==============================!>
<div id="bind" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#bind-buttons">
    <table id="bind-dg" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/area/dept/bindList',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: false,
            toolbar: '#bind-toolbar',
            nowrap: false,
            striped: true,
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'code'" width="25%">门店编码</th>
            <th data-options="field:'name'" width="25%">门店名称</th>
            <th data-options="field:'areaName'" width="25%">所属区域</th>
            <th data-options="field:'resCount'" width="25%">累计市调数量</th>
        </tr></thead>
    </table>
</div>
<div id="bind-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" plain="true" onclick="unbindArea()">解绑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_in'" plain="true" onclick="javascript:$('#bind-dg').datagrid('checkAll')">全选</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_out'" plain="true" onclick="javascript:$('#bind-dg').datagrid('uncheckAll')">取消全选</a>
</div>
<div id="bind-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bind').dialog('close')" style="width:90px">关闭</a>
</div>
<!=============================
    绑定部门信息
==============================!>
<div id="bind2" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#bind-buttons2">
    <table id="bind-dg2" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/area/dept/unbindList',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: false,
            toolbar: '#bind-toolbar2',
            nowrap: false,
            striped: true,
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'code'" width="25%">门店编码</th>
            <th data-options="field:'name'" width="25%">门店名称</th>
            <th data-options="field:'areaName'" width="25%">所属区域</th>
            <th data-options="field:'resCount'" width="25%">累计市调数量</th>
        </tr></thead>
    </table>
</div>
<div id="bind-toolbar2">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-link'" plain="true" onclick="bindDept()">绑定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_in'" plain="true" onclick="javascript:$('#bind-dg2').datagrid('checkAll')">全选</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-arrow_out'" plain="true" onclick="javascript:$('#bind-dg2').datagrid('uncheckAll')">取消全选</a>
</div>
<div id="bind-buttons2">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bind2').dialog('close')" style="width:90px">关闭</a>
</div>
<script>

    var url;

    function checkAllDept() {

    }

    //显示绑定部门窗口
    function bindDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#bind2').dialog('open').dialog('center').dialog('setTitle', '未绑定区域门店信息');
            var dg = $('#bind-dg2');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.areaId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    }

    //显示已绑定部门窗口
    function viewDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#bind').dialog('open').dialog('center').dialog('setTitle', '区域门店信息');
            var dg = $('#bind-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.areaId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    }

    function bindDept() {
        var deptRows = $('#bind-dg2').datagrid('getSelections');
        var areaRow = $('#dg').datagrid('getSelected');
        if (deptRows) {
            var areaName = areaRow.name;
            var areaId = areaRow.id;
            $.messager.confirm('确定','您确定绑定[' + deptRows.length + ']家门店到[' + areaName + ']吗?',function(r){
                if (r){
                    for(var i=0; i<deptRows.length; i++){
                        var row = deptRows[i];
                        var deptId = row.id;
                        url = '<%=request.getContextPath()%>/area/dept/bind?deptId=' + deptId + "&areaId=" + areaId;
                        $.get(url, function(result) {
                            result = eval('('+result+')');
                            if (result.code != '0') {
                                $.messager.alert('操作失败', result.msg, 'error');
                            } else {
                                $('#bind-dg2').datagrid('reload');
                            }
                        });
                    }
                }
            });
        }
    }

    function unbindArea() {
        var deptRows = $('#bind-dg').datagrid('getSelections');
        var areaRow = $('#dg').datagrid('getSelected');
        if (deptRows) {
            var areaName = areaRow.name;
            $.messager.confirm('确定','您确定从[' + areaName + ']解绑[' + deptRows.length + ']家门店吗?',function(r){
                if (r){
                    for(var i=0; i<deptRows.length; i++){
                        var row = deptRows[i];
                        var deptId = row.id;
                        url = '<%=request.getContextPath()%>/area/dept/unbind?deptId=' + deptId;
                        $.get(url, function(result) {
                            result = eval('('+result+')');
                            if (result.code != '0') {
                                $.messager.alert('操作失败', result.msg, 'error');
                            } else {
                                $('#bind-dg').datagrid('reload');
                            }
                        });
                    }
                }
            });
        }
    }

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增区域');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/area/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑区域');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/area/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该区域吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/area/delete?id=" + row.id;
                    $.get(url, function(result) {
                        result = eval('('+result+')');
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

    function searchArea() {
        var dg = $('#dg');
        //设置关键字
        var queryParams = dg.datagrid('options').queryParams;
        queryParams.name = encodeURI($("#search_name").val());
        dg.datagrid('options').queryParams = queryParams;
        //设置起始页数
        var options = dg.datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        dg.datagrid('reload');
    }

    function ifShow() {
        var areaRow = $('#dg').datagrid('getSelected');
        var areaIfShow = areaRow.ifShow;
        var areaName = areaRow.name;
        var areaId = areaRow.id;
        var showText = '';
        if (areaIfShow == 0) {
            showText = '您是否确定将[' + areaName + ']区域取消统计?';
        } else {
            showText = '您是否确定将[' + areaName + ']区域j加入统计?';
        }
        if (areaRow) {
            $.messager.confirm('确定', showText, function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/area/changeShow?id=" + areaId + "&ifShow=" + areaIfShow;
                    $.get(url, function(result) {
                        result = eval('('+result+')');
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

</script>
</body>
</html>

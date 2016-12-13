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
    <%--<input class="easyui-textbox" data-options="prompt:'请输入竞争门店名称'" style="width:25%;height:28px;" id="search_name">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="search()"></a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
</div>
<!=============================
    竞争门店信息列表
==============================!>
<div class="main_table">
    <table id="dg" title="竞争门店列表" class="easyui-datagrid"
           data-options="
                    url: '<%=request.getContextPath()%>/comp/get',
                    method: 'get',
                    idField: 'id',
                    showFooter: true,
                    fitColumns: true,
                    collapsible: false,
                    animate: true,
                    rownumbers: true,
                    iconCls: 'icon-ok',
                    singleSelect: true,
                    toolbar: '#toolbar',
                    border: false,
                    fit: true,
                    nowrap: false,
                    striped: true,
                    ">
        <thead>
        <tr>
            <th data-options="field:'name'" width="50%">竞争门店名称</th>
            <th data-options="field:'cityName'" width="50%">竞争门店所在城市</th>
        </tr>
        </thead>
    </table>
</div>

<!=============================
    新增城市信息
==============================!>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写竞争门店信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>竞争门店名称:</label>
            <input name="name" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>门店所在城市:</label>
            <input name="cityName" class="easyui-textbox" required="true">
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
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增城市');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/comp/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑城市');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/comp/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该竞争门店吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/comp/delete?id=" + row.id;
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

    function search() {
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

</script>
</body>
</html>

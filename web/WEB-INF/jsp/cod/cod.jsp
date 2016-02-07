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
    <div style="margin: 5px 5px;">
        <input class="easyui-textbox" data-options="prompt:'请输入品名'" style="width:25%;height:28px;" id="search_name">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchCod()"></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-coins'" plain="true" onclick="showPriceDlg()">查看商品价格</a>
    </div>
    <!--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>-->
</div>
<!=============================
    商品信息列表
==============================!>
<div class="main_table">
    <table id="dg" title="商品列表" class="easyui-datagrid"
           data-options="
                    url: '<%=request.getContextPath()%>/cod/get',
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
                    pageSize: 15,
                    pageList: [10, 15, 30],
                    toolbar: '#toolbar',
                    border: false,
                    fit: true,
                    nowrap: false,
                    striped: true,
                    ">
        <thead>
        <tr>
            <th data-options="field:'categoryCode'" width="10%" sortable="true">品类编码</th>
            <th data-options="field:'categoryName'" width="10%">品类</th>
            <th data-options="field:'code'" width="10%">编码</th>
            <th data-options="field:'artNo'" width="10%">货号</th>
            <th data-options="field:'pName'" width="10%">品名</th>
            <th data-options="field:'pSize'" width="10%">规格</th>
            <th data-options="field:'unit'" width="5%">单位</th>
            <th data-options="field:'barCode'" width="10%">条形码</th>
            <th data-options="field:'createUsername'" width="10%">创建人</th>
            <th data-options="field:'createDt'" width="15%">创建时间</th>
        </tr>
        </thead>
    </table>
</div>

<!=============================
    商品价格信息
==============================!>
<div id="price" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#price-buttons">
    <table id="price-dg" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/cod/price/get',
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
            toolbar: '#price-toolbar',
            nowrap: false,
            striped: true,
            pageSize: 15,
            pageList: [10, 15, 30],
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'resName'" width="10%">市调名称</th>
            <th data-options="field:'comName'" width="10%">品名</th>
            <th data-options="field:'purchasePrice',align:'right',styler:cellStyler" width="10%">进价</th>
            <th data-options="field:'retailPrice',align:'right',styler:cellStyler" width="10%">零售价</th>
            <th data-options="field:'compName'" width="20%">所属门店</th>
            <th data-options="field:'missDesp',styler:cellStyler2" width="10%">缺货状态</th>
            <th data-options="field:'uploadName'" width="10%">上传人</th>
            <th data-options="field:'uploadDt'" width="20%">上传时间</th>
        </tr></thead>
    </table>
</div>
<div id="price-toolbar">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_go'" plain="true" onclick="checkPublish()">发布</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-2012080412301'" plain="true" onclick="checkCallback()">撤回</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showDeptDetail()">查看市调门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showCodDetail()">查看市调商品</a>--%>
</div>
<div id="price-buttons">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="showDept()" style="width:90px">确定</a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#price').dialog('close')" style="width:90px">关闭</a>
</div>
<script>
    var url;

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增机构');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/dept/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑部门');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/dept/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该机构吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/dept/delete?deptId=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
                        if (result.code != '0') {
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
                if (result.code != '0'){
                    $.messager.alert('操作失败', result.msg, 'error');
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    function searchCod() {
        var dg = $('#dg');
        //设置关键字
        var queryParams = dg.datagrid('options').queryParams;
        queryParams.pName = $("#search_name").val();
        dg.datagrid('options').queryParams = queryParams;
        //设置起始页数
        var options = dg.datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        dg.datagrid('reload');
    }

    function showPriceDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#price').dialog('open').dialog('center').dialog('setTitle', '市调商品信息');
            var dg = $('#price-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.comId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
        }
    }

    function cellStyler(value,row,index){
        return 'color:green;font-style:italic';
    }

    function cellStyler2(value,row,index){
        if (value == '有货') {
            return 'color:green;';
        } else {
            return 'color:red;';
        }
    }
</script>
</body>
</html>

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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body>
<!=============================
    市调清单列表
==============================!>
<div class="main_table">
    <table id="dg" title="区域市调统计" class="easyui-datagrid"
           data-options="
                url: '<%=request.getContextPath()%>/area/stats',
                method: 'get',
                idField: 'id',
                treeField: 'name',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: false,
                iconCls: 'icon-ok',
                fit: true,
                toolbar: '#toolbar',
                pageSize: 10,
                pageList: [10, 15, 20],
                pagination: false,
                singleSelect: true
                ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="15%">对应区域</th>
                <th data-options="field:'countByMonth'" width="15%">当月档期</th>
                <th data-options="field:'finishPercentByMonth'" width="15%">月度完成百分比</th>
                <th data-options="field:'countByYear'" width="15%">年度累计</th>
                <th data-options="field:'finishPercentByYear'" width="15%">年度完成百分比</th>
                <th data-options="field:'cci'" width="15%">CCI</th>
                <th data-options="field:'cost'" width="15%">投入</th>
            </tr>
        </thead>
    </table>
</div>
<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">

</body>
</html>
